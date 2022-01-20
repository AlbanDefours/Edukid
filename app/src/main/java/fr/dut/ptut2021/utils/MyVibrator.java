package fr.dut.ptut2021.utils;

import android.content.Context;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;

public class MyVibrator {
    static Vibrator vibe;

    public static void vibrate(Context context, int duration) {
        if(vibe == null)
            vibe = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            vibe.vibrate(VibrationEffect.createOneShot(duration, VibrationEffect.DEFAULT_AMPLITUDE));
        else
            vibe.vibrate(duration);
    }
}
