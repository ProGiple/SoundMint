package com.satespace.soundmint.musix.track;

import com.satespace.soundmint.App;
import com.satespace.soundmint.musix.playlist.PlaybackMode;
import com.satespace.soundmint.musix.playlist.Playlist;
import com.satespace.soundmint.util.Utils;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter @Setter
public class ActiveTrackEnvironment {
    private MediaPlayer mediaPlayer;
    private final PlaybackMode playbackMode;
    private Track activeTrack;
    private Playlist activePlaylist;

    // Очередь воспроизведения
    private Queue<Track> playbackQueue;
    // История воспроизведения для навигации назад
    private Deque<Track> playbackHistory;
    // Флаг, указывающий, что сейчас играет трек из очереди
    private boolean isPlayingFromQueue;

    public ActiveTrackEnvironment() {
        this.playbackMode = PlaybackMode.SEQUENTIAL;
        this.playbackQueue = new LinkedList<>();
        this.playbackHistory = new ArrayDeque<>();
        this.isPlayingFromQueue = false;
    }

    public void play(Track track, Playlist playlist) {
        if (this.activeTrack != null) {
            playbackHistory.push(activeTrack);
        }

        this.activeTrack = track;
        this.activePlaylist = playlist;
        this.isPlayingFromQueue = false;

        // Перестраиваем очередь на основе нового плейлиста и трека
        this.rebuildQueue();

        if (mediaPlayer != null) {
            if (this.isPlaying()) {
                mediaPlayer.stop();
            }
            mediaPlayer.dispose();
        }

        Media media = new Media(track.getFile().toURI().toString());
        this.mediaPlayer = new MediaPlayer(media);

        App.CONTROLLER.loadLabels();
        this.setupMediaPlayerListeners();
        mediaPlayer.play();

        App.CONTROLLER.getSwitchStatusMusicButton().updateImage(!this.isPlaying());
        App.CONTROLLER.getPreviousMusicButton().updateState();
        App.CONTROLLER.getNextMusicButton().updateState();

        App.CONTROLLER.getBit().initialize(true);
        App.CONTROLLER.getBit().show();
    }

    private void setupMediaPlayerListeners() {
        mediaPlayer.currentTimeProperty().addListener((obs, oldTime, newTime) -> {
            if (!this.isClear()) {
                Duration total = mediaPlayer.getTotalDuration();

                double progress;
                if (total != null && !total.isUnknown() && total.toMillis() > 0) {
                    progress = newTime.toMillis() / total.toMillis();
                    if (progress >= 0.9995) {
                        progress = -1;
                        onTrackEnd();
                    }
                }
                else progress = -1;

                App.CONTROLLER.getCurrentTimeLabel().setText(Utils.formatDuration(newTime));
                App.CONTROLLER.getTrackAudioBar().setProgress(progress, false, mediaPlayer);
            }
        });

        mediaPlayer.setAudioSpectrumInterval(0.05);
        mediaPlayer.setAudioSpectrumListener((timestamp, duration, magnitudes, phases) -> {
            double level = magnitudes[0];
            double scale = 1 + Math.max(0, level + 60) / 60;

            App.CONTROLLER.getBit().setScale(scale);
        });

    }

    /**
     * Перестраивает очередь воспроизведения на основе активного плейлиста и текущего трека
     */
    private void rebuildQueue() {
        playbackQueue.clear();

        if (activePlaylist == null || activeTrack == null) {
            return;
        }

        List<Track> tracks = activePlaylist.getTrackList();
        int currentIndex = tracks.indexOf(activeTrack);

        if (currentIndex == -1) {
            return;
        }

        switch (activePlaylist.getPlaybackMode()) {
            case SEQUENTIAL:
                for (int i = currentIndex + 1; i < tracks.size(); i++) {
                    playbackQueue.offer(tracks.get(i));
                }
                break;

            case REPEAT_ALL:
                for (int i = currentIndex + 1; i < tracks.size(); i++) {
                    playbackQueue.offer(tracks.get(i));
                }
                for (int i = 0; i <= currentIndex; i++) {
                    playbackQueue.offer(tracks.get(i));
                }
                break;

            case REPEAT_ONE:
                break;

            case SHUFFLE:
                List<Track> shuffled = new ArrayList<>(tracks);
                shuffled.remove(activeTrack);
                Collections.shuffle(shuffled);
                playbackQueue.addAll(shuffled);
                break;
        }
    }

    public void resume() {
        if (this.isPaused()) {
            mediaPlayer.play();
        }
        App.CONTROLLER.getNextMusicButton().updateState();
        App.CONTROLLER.getPreviousMusicButton().updateState();
        App.CONTROLLER.getSwitchStatusMusicButton().updateImage(!this.isPlaying());
    }

    public void pause() {
        mediaPlayer.pause();
        App.CONTROLLER.getNextMusicButton().updateState();
        App.CONTROLLER.getPreviousMusicButton().updateState();
        App.CONTROLLER.getSwitchStatusMusicButton().updateImage(!this.isPlaying());
    }

    public void playNext() throws NoSuchElementException {
        Track nextTrack = this.getNext();
        if (nextTrack == null) throw new NoSuchElementException();
        this.play(nextTrack, activePlaylist);
    }

    public void playPrevious() throws NoSuchElementException {
        Track previousTrack = this.getPrevious();
        if (previousTrack == null) throw new NoSuchElementException();
        this.play(previousTrack, activePlaylist);
    }

    public Track getNext() {
        if (!playbackQueue.isEmpty()) {
            Track next = playbackQueue.poll();
            isPlayingFromQueue = true;
            return next;
        }

        isPlayingFromQueue = false;
        return calculateNextTrack();
    }

    public Track getPrevious() {
        if (!playbackHistory.isEmpty()) {
            return playbackHistory.pop();
        }

        return calculatePreviousTrack();
    }

    /**
     * Вычисляет следующий трек на основе режима воспроизведения
     */
    private Track calculateNextTrack() {
        if (activePlaylist == null || activeTrack == null) {
            return null;
        }

        List<Track> tracks = activePlaylist.getTrackList();
        int currentIndex = tracks.indexOf(activeTrack);

        if (currentIndex == -1) {
            return null;
        }

        switch (activePlaylist.getPlaybackMode()) {
            case SEQUENTIAL:
                if (currentIndex < tracks.size() - 1) {
                    return tracks.get(currentIndex + 1);
                }
                return null;

            case REPEAT_ALL:
                int nextIndex = (currentIndex + 1) % tracks.size();
                return tracks.get(nextIndex);

            case REPEAT_ONE:
                return activeTrack;

            case SHUFFLE:
                if (tracks.size() > 1) {
                    // Исключаем текущий трек и выбираем случайный
                    List<Track> availableTracks = new ArrayList<>(tracks);
                    availableTracks.remove(activeTrack);
                    Random random = new Random();
                    return availableTracks.get(random.nextInt(availableTracks.size()));
                }
                return null;

            default:
                return null;
        }
    }

    /**
     * Вычисляет предыдущий трек на основе режима воспроизведения
     */
    private Track calculatePreviousTrack() {
        if (activePlaylist == null || activeTrack == null) {
            return null;
        }

        List<Track> tracks = activePlaylist.getTrackList();
        int currentIndex = tracks.indexOf(activeTrack);

        if (currentIndex == -1) {
            return null;
        }

        return switch (activePlaylist.getPlaybackMode()) {
            case SEQUENTIAL, SHUFFLE -> {
                if (currentIndex > 0) {
                    yield tracks.get(currentIndex - 1);
                }
                yield null;
            }
            case REPEAT_ALL -> {
                int prevIndex = (currentIndex - 1 + tracks.size()) % tracks.size();
                yield tracks.get(prevIndex);
            }
            case REPEAT_ONE -> activeTrack;
        };
    }

    /**
     * Добавляет трек в очередь воспроизведения
     */
    public void addToQueue(Track track) {
        if (track != null) {
            playbackQueue.offer(track);
        }
    }

    /**
     * Добавляет трек следующим в очереди
     */
    public void addNextInQueue(Track track) {
        if (track != null) {
            // Создаем временную очередь для вставки трека следующим
            Queue<Track> tempQueue = new LinkedList<>();
            tempQueue.offer(track);
            tempQueue.addAll(playbackQueue);
            playbackQueue.clear();
            playbackQueue.addAll(tempQueue);
        }
    }

    /**
     * Очищает очередь воспроизведения
     */
    public void clearQueue() {
        playbackQueue.clear();
        isPlayingFromQueue = false;
    }

    /**
     * Получает копию текущей очереди
     */
    public List<Track> getQueueSnapshot() {
        return new ArrayList<>(playbackQueue);
    }

    /**
     * Проверяет, есть ли треки в очереди
     */
    public boolean hasQueueTracks() {
        return !playbackQueue.isEmpty();
    }

    public void onTrackEnd() {
        mediaPlayer.stop();
        mediaPlayer.dispose();
        try {
            this.playNext();
        } catch (NoSuchElementException e) {
            App.CONTROLLER.getSwitchStatusMusicButton().updateImage(false);
        }
        App.CONTROLLER.loadLabels();
    }

    public boolean isPlaying() {
        return mediaPlayer != null && mediaPlayer.getStatus().equals(MediaPlayer.Status.PLAYING);
    }

    public boolean isPaused() {
        return mediaPlayer != null && mediaPlayer.getStatus().equals(MediaPlayer.Status.PAUSED);
    }

    public boolean isClear() {
        return mediaPlayer == null ||
                mediaPlayer.getStatus().equals(MediaPlayer.Status.UNKNOWN) ||
                mediaPlayer.getStatus().equals(MediaPlayer.Status.DISPOSED) ||
                mediaPlayer.getStatus().equals(MediaPlayer.Status.STOPPED);
    }
}