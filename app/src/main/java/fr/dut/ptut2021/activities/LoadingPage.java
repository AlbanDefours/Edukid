package fr.dut.ptut2021.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import fr.dut.ptut2021.R;
import fr.dut.ptut2021.database.CreateDatabase;

public class LoadingPage extends AppCompatActivity {

    private CreateDatabase db;
    private boolean vide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_page);

        new Thread() {
            @Override
            public void run() {
                db = CreateDatabase.getInstance(LoadingPage.this);
                vide = db.userDao().dbIsEmpty();
            }
        }.start();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                if (vide) {
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
        }, 10000);   //Loading Page time
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