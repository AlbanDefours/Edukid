package fr.dut.ptut2021.activities;

import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import fr.dut.ptut2021.R;
import fr.dut.ptut2021.database.CreateDatabase;
import fr.dut.ptut2021.game.PlayWithSound;
import fr.dut.ptut2021.utils.GlobalUtils;
import fr.dut.ptut2021.utils.MyDatabaseInsert;
import fr.dut.ptut2021.utils.MyTextToSpeech;

public class LoadingPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_page);

        textAnimation();
        CreateDatabase db = CreateDatabase.getInstance(getApplicationContext());
        MyDatabaseInsert.getInstance().ajoutDatabase(this);
        MyTextToSpeech.getInstance().initialiser(this);

        new Handler().postDelayed(() -> {
            if (db.appDao().tabUserIsEmpty())
                GlobalUtils.getInstance().startEditPage(this);
            else
                GlobalUtils.getInstance().startPage(this, "UserMenu", true, false);
        }, 1500);
    }

    private void textAnimation() {
        YoYo.with(Techniques.Tada).duration(1000).playOn(findViewById(R.id.applicationName));
    }
}