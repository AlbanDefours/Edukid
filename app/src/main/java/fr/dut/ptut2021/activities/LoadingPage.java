package fr.dut.ptut2021.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import fr.dut.ptut2021.R;

public class LoadingPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_page);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                if (isFirstTime()) {
                    Intent intent = new Intent().setClass(getApplicationContext(), UserEdit.class);
                    intent.putExtra("isFirstTime", true);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent().setClass(getApplicationContext(), UserMenu.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, 1000);   //Loading Page time
    }

    //Verifies que c'est la premiere fois que l'on ouvre l'application
    private boolean isFirstTime() {
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        boolean firstTime = preferences.getBoolean("firstTime", true);
        if (firstTime) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("firstTime", false);
            editor.apply();
        }
        return firstTime;
    }
}