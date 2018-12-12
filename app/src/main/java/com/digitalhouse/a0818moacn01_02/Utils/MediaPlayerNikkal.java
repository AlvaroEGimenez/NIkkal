package com.digitalhouse.a0818moacn01_02.Utils;

import android.media.MediaPlayer;

public class MediaPlayerNikkal {
    private static MediaPlayer mediaPlayer;
    private static MediaPlayerNikkal instance;

    public static MediaPlayerNikkal getInstance() {
        if (instance == null) {
            mediaPlayer = new MediaPlayer();
            instance = new MediaPlayerNikkal();
        }

        return instance;
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }
}
