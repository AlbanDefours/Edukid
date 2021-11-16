package fr.dut.ptut2021.activities;

import static androidx.core.content.ContextCompat.startActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import fr.dut.ptut2021.R;
import fr.dut.ptut2021.game.Memory;

public class PageChargement extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_chargement);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                Intent intent = new Intent().setClass(getApplicationContext(), Memory.class);
                intent.putExtra("addMemory",true);
                startActivity(intent);
                finish();
            }
        },1000);
    }
}