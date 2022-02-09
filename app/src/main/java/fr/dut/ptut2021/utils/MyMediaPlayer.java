package fr.dut.ptut2021.utils;

import android.content.Context;
import android.media.MediaPlayer;

public class MyMediaPlayer {

    private MediaPlayer ref;
    private static MyMediaPlayer instance;

    public static MyMediaPlayer getInstance() {
        if (instance == null) {
            instance = new MyMediaPlayer();
        }
        return instance;
    }

    public void playSound(Context context, int song) {
        ref = MediaPlayer.create(context, song);
        ref.start();
    }

    public void stop(){
        if(ref != null) {
            ref.stop();
            ref = null;
        }
    }
}