package fr.dut.ptut2021.utils;

import android.content.Context;
import android.os.Build;
import android.speech.tts.TextToSpeech;

import java.util.Locale;

public class MyTextToSpeech {
    static TextToSpeech textToSpeech;

    public static void speachText(Context context, String text) {
        textToSpeech = new TextToSpeech(context, status -> {
            if(status != TextToSpeech.ERROR) {
                textToSpeech.setLanguage(Locale.FRANCE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                    if(text.equals("Trouve la lettre : Y") || text.equals("Trouve la lettre : y"))
                        textToSpeech.speak("Trouve la lettre : igrec", TextToSpeech.QUEUE_FLUSH, null, null);
                    textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
                }
                else
                    textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null);
            }
        });
    }
}
