package fr.dut.ptut2021.activities;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import fr.dut.ptut2021.R;
import fr.dut.ptut2021.database.CreateDatabase;
import fr.dut.ptut2021.models.databse.User;

public class StatisticPage extends AppCompatActivity implements View.OnClickListener {

    private Vibrator vibe;
    private int page = 0;
    private TextView title;
    private List<User> listUser;
    private CreateDatabase db = null;
    private ImageView nextPage, previousPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic_page);

            vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        initViews();
        createDbAndImportUsers();
        initOnClickViews();

        if (!listUser.isEmpty())
            displayTitle();
    }

    private void initViews() {
        title = findViewById(R.id.title_StatisticPage);
        nextPage = findViewById(R.id.arrow_nextPage);
        previousPage = findViewById(R.id.arrow_previousPage);
    }

    private void createDbAndImportUsers() {
        db = CreateDatabase.getInstance(StatisticPage.this);
        if (!db.appDao().tabUserIsEmpty()) {
            listUser = db.appDao().getAllUsers();
        }
    }

    private void initOnClickViews() {
        nextPage.setOnClickListener(this);
        previousPage.setOnClickListener(this);
    }

    private void displayTitle() {
        title.setText(listUser.get(page).getUserName());
        verifPageLocation();
    }


    private void verifPageLocation() {
        if (page == 0)
            previousPage.setAlpha(0.5f);
        if (page == listUser.size() - 1)
            nextPage.setAlpha(0.5f);
        else {
            previousPage.setAlpha(1f);
            nextPage.setAlpha(1f);
        }
    }

    public void vibrate() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            vibe.vibrate(VibrationEffect.createOneShot(35, VibrationEffect.DEFAULT_AMPLITUDE));
        else
            vibe.vibrate(35);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.arrow_nextPage:
                if (page < listUser.size() - 1) {
                    vibrate();
                    page++;
                }
                displayTitle();
                break;
            case R.id.arrow_previousPage:
                if (0 < page) {
                    vibrate();
                    page--;
                }
                displayTitle();
                break;
        }
    }
}