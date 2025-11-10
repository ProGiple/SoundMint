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

    private Queue<Track> playbackQueue;
    private Deque<Track> playbackHistory;

    public ActiveTrackEnvironment() {
        this.playbackMode = PlaybackMode.SEQUENTIAL;
        this.playbackQueue = new LinkedList<>();
        this.playbackHistory = new ArrayDeque<>();
    }


    /**
     * Запуск трека
     * @param track - новый трек для проигрывания
     * @param playlist - плейлист, из которого был запущен трек
     */
    public void play(Track track, Playlist playlist, boolean pushToHistory) {
        // Сохраняем текущий трек в историю перед сменой
        if (this.activeTrack != null && !this.activeTrack.equals(track)) {
            if (pushToHistory)
                playbackHistory.push(this.activeTrack);
            // Ограничиваем размер истории
            if (playbackHistory.size() > 100) {
                playbackHistory.removeLast();
            }
        }

        this.activeTrack = track;
        this.activePlaylist = playlist;

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
        setupMediaPlayerListeners();
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

    public void onNextClicked() {
        Track nextTrack = this.peekNext();
    }

    /**
     * Перестраивает очередь воспроизведения на основе активного плейлиста и текущего трека
     */
    private void rebuildQueue() {
        playbackQueue.clear();

        if (activePlaylist == null || activeTrack == null) return;


        List<Track> tracks = activePlaylist.getTrackList();
        int currentIndex = tracks.indexOf(activeTrack);

        if (currentIndex == -1) return;


        switch (activePlaylist.getPlaybackMode()) {
            case SEQUENTIAL:
                // Добавляем все треки после текущего
                for (int i = currentIndex + 1; i < tracks.size(); i++) {
                    playbackQueue.offer(tracks.get(i));
                }
                break;

            case REPEAT_ALL:
                // Добавляем все треки после текущего, затем все до текущего
                for (int i = currentIndex + 1; i < tracks.size(); i++) {
                    playbackQueue.offer(tracks.get(i));
                }
                for (int i = 0; i <= currentIndex; i++) {
                    playbackQueue.offer(tracks.get(i));
                }
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
        Track nextTrack = this.getAndRemoveNext();
        if (nextTrack == null) throw new NoSuchElementException();
        this.play(nextTrack, activePlaylist, true);
    }

    public void playPrevious() throws NoSuchElementException {
        Track previousTrack = getAndRemovePrevious();
        if (previousTrack == null) throw new NoSuchElementException();
        this.play(previousTrack, activePlaylist, false);
    }

    /**
     * Получает и удаляет следующий трек из соответствующей коллекции
     */
    private Track getAndRemoveNext() {
        // Сначала проверяем очередь
        if (!playbackQueue.isEmpty()) {
            return playbackQueue.poll();
        }

        // Если очередь пуста, получаем следующий из плейлиста
        Track nextTrack = this.calculateNextTrack();
        if (nextTrack != null && activePlaylist != null) {
            // Если следующий трек из плейлиста, добавляем текущий в историю
            if (activeTrack != null) {
                playbackHistory.push(activeTrack);
            }
        }

        return nextTrack;
    }

    /**
     * Получает и удаляет предыдущий трек из соответствующей коллекции
     */
    private Track getAndRemovePrevious() {
        // Сначала проверяем историю
        if (!playbackHistory.isEmpty()) {
            Track previous = playbackHistory.pop();

            if (activeTrack != null) {
                Queue<Track> tempQueue = new LinkedList<>();
                tempQueue.offer(activeTrack);
                tempQueue.addAll(playbackQueue);
                playbackQueue.clear();
                playbackQueue.addAll(tempQueue);
            }

            return previous;
        }

        Track previousTrack = calculatePreviousTrack();
        if (previousTrack != null && activeTrack != null) {
            Queue<Track> tempQueue = new LinkedList<>();
            tempQueue.offer(activeTrack);
            tempQueue.addAll(playbackQueue);
            playbackQueue.clear();
            playbackQueue.addAll(tempQueue);
        }

        return previousTrack;
    }

    /**
     * Просмотр следующего трека без изменения состояния
     */
    public Track peekNext() {
        if (!playbackQueue.isEmpty()) {
            return playbackQueue.peek();
        }
        return calculateNextTrack();
    }

    /**
     * Просмотр предыдущего трека без изменения состояния
     */
    public Track peekPrevious() {
        if (!playbackHistory.isEmpty()) {
            return playbackHistory.peek();
        }
        return calculatePreviousTrack();
    }

    /**
     * Старые методы для обратной совместимости
     */
    public Track getNext() {
        return peekNext();
    }

    public Track getPrevious() {
        return peekPrevious();
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

        return switch (activePlaylist.getPlaybackMode()) {
            case SEQUENTIAL -> {
                if (currentIndex < tracks.size() - 1) {
                    yield tracks.get(currentIndex + 1);
                }
                yield null;
            }
            case REPEAT_ALL -> {
                int nextIndex = (currentIndex + 1) % tracks.size();
                yield tracks.get(nextIndex);
            }
            case REPEAT_ONE -> activeTrack;
            case SHUFFLE -> {
                if (tracks.size() > 1) {
                    List<Track> availableTracks = new ArrayList<>(tracks);
                    availableTracks.remove(activeTrack);
                    if (availableTracks.isEmpty()) yield null;
                    Random random = new Random();
                    yield availableTracks.get(random.nextInt(availableTracks.size()));
                }
                yield null;
            }
        };
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
     * Обрабатывает завершение трека и автоматический переход к следующему
     */
    public void onTrackEnd() {

        mediaPlayer.stop();
        mediaPlayer.dispose();
        App.CONTROLLER.loadLabels();

        try {
            this.playNext();
        } catch (NoSuchElementException e) {
            App.CONTROLLER.getSwitchStatusMusicButton().updateImage(false);
        }
    }

    /**
     * Добавляет трек в очередь воспроизведения
     */
    public void addToQueue(Track track) {
        if (track != null && !track.equals(activeTrack)) {
            playbackQueue.offer(track);
        }
    }

    /**
     * Добавляет трек следующим в очереди
     */
    public void addNextInQueue(Track track) {
        if (track != null && !track.equals(activeTrack)) {
            Queue<Track> tempQueue = new LinkedList<>();
            tempQueue.offer(track);
            tempQueue.addAll(playbackQueue);
            playbackQueue.clear();
            playbackQueue.addAll(tempQueue);
        }
    }

    /**
     * Удаляет трек из очереди
     */
    public void removeFromQueue(Track track) {
        if (track != null) {
            playbackQueue.remove(track);
        }
    }

    /**
     * Очищает очередь воспроизведения
     */
    public void clearQueue() {
        playbackQueue.clear();
    }

    /**
     * Очищает историю воспроизведения
     */
    public void clearHistory() {
        playbackHistory.clear();
    }

    /**
     * Получает копию текущей очереди
     */
    public List<Track> getQueueSnapshot() {
        return new ArrayList<>(playbackQueue);
    }

    /**
     * Получает копию истории воспроизведения
     */
    public List<Track> getHistorySnapshot() {
        return new ArrayList<>(playbackHistory);
    }

    /**
     * Проверяет, есть ли треки в очереди
     */
    public boolean hasQueueTracks() {
        return !playbackQueue.isEmpty();
    }

    /**
     * Проверяет, есть ли треки в истории
     */
    public boolean hasHistoryTracks() {
        return !playbackHistory.isEmpty();
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