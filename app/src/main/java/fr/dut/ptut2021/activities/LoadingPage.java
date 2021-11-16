package fr.dut.ptut2021.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import fr.dut.ptut2021.R;
import fr.dut.ptut2021.database.CreateDatabase;

public class LoadingPage extends AppCompatActivity {

    private CreateDatabase db = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_page);

        db = CreateDatabase.getInstance(getApplicationContext());

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                if (db.userDao().dbIsEmpty()) {
                    Intent intent = new Intent().setClass(getApplicationContext(), UserEdit.class);
                    intent.putExtra("addUser", true);
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
}