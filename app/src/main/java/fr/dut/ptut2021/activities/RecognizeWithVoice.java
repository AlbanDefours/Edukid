package fr.dut.ptut2021.activities;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Locale;

import fr.dut.ptut2021.R;

public class RecognizeWithVoice extends AppCompatActivity implements TextToSpeech.OnInitListener {

    private TextToSpeech textToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recognize_with_voice);

        textToSpeech = new TextToSpeech(this, this);

        new Handler().postDelayed(() -> {
            speak("La lettre : A !");
        }, 1000);

        findViewById(R.id.buttonAnswer1_recognizeVoice).setOnClickListener(v -> speak("Presque !!!!! Réssaye encore tu vas y arriver !"));
        findViewById(R.id.buttonAnswer2_recognizeVoice).setOnClickListener(v -> speak("Bravo ! Félicitations"));
        findViewById(R.id.buttonAnswer3_recognizeVoice).setOnClickListener(v -> speak("Presque !!!!! Réssaye encore tu vas y arriver !"));
    }

    private void speak(String texte){
        HashMap<String, String> onlineSpeech = new HashMap<>();
        onlineSpeech.put(TextToSpeech.Engine.KEY_FEATURE_NETWORK_SYNTHESIS, "true");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            textToSpeech.speak(texte, TextToSpeech.QUEUE_FLUSH, null, null);
        }
        else {
            textToSpeech.speak(texte, TextToSpeech.QUEUE_FLUSH, onlineSpeech);
        }
    }

    @Override
    public void onDestroy() {
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onDestroy();
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            textToSpeech.setLanguage(Locale.FRANCE);
        }
    }
}