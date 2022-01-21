package fr.dut.ptut2021.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import java.util.Locale;

import fr.dut.ptut2021.game.PlayWithSound;

public class MyTextToSpeech {
    static TextToSpeech textToSpeech;

    public static void speachText(Context context, String text) {
        if(text.contains("Y") || text.contains("y") && context.getClass() == PlayWithSound.class)
            text = "Trouve la lettre igrec";
        String finalText = text;
        textToSpeech = new TextToSpeech(context, status -> {
            if(status != TextToSpeech.ERROR) {
                textToSpeech.setLanguage(Locale.FRANCE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                    textToSpeech.speak(finalText, TextToSpeech.QUEUE_FLUSH, null, null);
                }
                else
                    textToSpeech.speak(finalText, TextToSpeech.QUEUE_FLUSH, null);
            }
        });
    }

    public static void stop(){
        if(textToSpeech != null){
            textToSpeech.shutdown();
        }
    }
}
