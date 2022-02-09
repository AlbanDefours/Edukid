package fr.dut.ptut2021.utils;

import android.content.Context;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.speech.tts.Voice;

import java.util.Locale;

import fr.dut.ptut2021.game.PlayWithSound;

public class MyTextToSpeech {
    TextToSpeech textToSpeech;
    Voice voice = null;

    private static MyTextToSpeech instance;
    public static MyTextToSpeech getInstance() {
        if (instance == null) {
            instance = new MyTextToSpeech();
        }
        return instance;
    }

    public void speachText(Context context, String text) {
        if (text.contains("Y") || text.contains("y") && context.getClass() == PlayWithSound.class)
            text = "Trouve la lettre igrec";
        String finalText = text;
        textToSpeech = new TextToSpeech(context, status -> {
            if (status != TextToSpeech.ERROR) {
                textToSpeech.setLanguage(Locale.FRANCE);
                if (voice != null)
                    textToSpeech.setVoice(voice);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                    textToSpeech.speak(finalText, TextToSpeech.QUEUE_FLUSH, null, null);
                else
                    textToSpeech.speak(finalText, TextToSpeech.QUEUE_FLUSH, null);
            }
        });
    }

    public void initialiser(Context context) {
        textToSpeech = new TextToSpeech(context, status -> {
            if (status != TextToSpeech.ERROR) {
                textToSpeech.setLanguage(Locale.FRANCE);
                for (Voice tmpVoice : textToSpeech.getVoices()) {
                    if (tmpVoice.getName().equals("fr-fr-x-fra-network")) {
                        voice = tmpVoice;
                        textToSpeech.setVoice(tmpVoice);
                    }
                }
            }
        });
    }

    public void stop(Context context) {
        if (textToSpeech != null) {
            speachText(context, "");
        }
    }
}
