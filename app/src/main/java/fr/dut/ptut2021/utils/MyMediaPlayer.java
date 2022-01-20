package fr.dut.ptut2021.utils;

import android.content.Context;
import android.media.MediaPlayer;

public class MyMediaPlayer {
    static MediaPlayer ref;

    public static void playSound(Context context, int song) {
        ref = MediaPlayer.create(context, song);
        ref.start();
    }

    public static void stop(){
        if(ref != null)
            ref.stop();
    }
}