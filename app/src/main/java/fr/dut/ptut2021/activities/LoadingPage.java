package fr.dut.ptut2021.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import fr.dut.ptut2021.R;
import fr.dut.ptut2021.database.CreateDatabase;
import fr.dut.ptut2021.models.Theme;

public class LoadingPage extends AppCompatActivity {

    private CreateDatabase db = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_page);

        db = CreateDatabase.getInstance(getApplicationContext());
        createThemes();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {

                if (db.userDao().tabUserIsEmpty()) {
                    openUserEditPage();
                } else {
                    openUserMenuPage();
                }
            }
        }, 1000);   //Loading Page time
    }

    private void createThemes() {
        if (db.themeDao().tabThemeIsEmpty()) {
            db.themeDao().createTheme(new Theme("Lettres", R.drawable.lettres));
            db.themeDao().createTheme(new Theme("Chiffres", R.drawable.chiffres));
        }
    }

    private void openUserMenuPage() {
        Intent intent = new Intent().setClass(getApplicationContext(), UserMenu.class);
        startActivity(intent);
        finish();
    }

    private void openUserEditPage() {
        Intent intent = new Intent().setClass(getApplicationContext(), UserEdit.class);
        intent.putExtra("addUser", true);
        startActivity(intent);
        finish();
    }
}