package fr.dut.ptut2021.utils;

import android.content.Context;
import android.media.MediaPlayer;

import fr.dut.ptut2021.R;

public class MyMediaPlayer {

    static MediaPlayer ref;
    private static MyMediaPlayer ourInstance = new MyMediaPlayer();
    private Context appContext;

    private MyMediaPlayer() {}

    public static Context get() {
        return getInstance().getContext();
    }

    public static synchronized MyMediaPlayer getInstance() {
        return ourInstance;
    }

    public void init(Context context) {
        if (appContext == null) {
            this.appContext = context;
        }
    }

    private Context getContext() {
        return appContext;
    }

    public static MediaPlayer playSong() {
        if (ref == null)
            ref = MediaPlayer.create(get(), R.raw.three_stars);
        return ref;
    }
}
