package fr.dut.ptut2021.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import fr.dut.ptut2021.R;
import fr.dut.ptut2021.database.CreateDatabase;
import fr.dut.ptut2021.models.databse.User;

public class StatisticPage extends AppCompatActivity implements View.OnClickListener {

    private int page = 0;
    private TextView title;
    private List<User> listUser;
    private CreateDatabase db = null;
    private ImageView nextPage, previousPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic_page);

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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.arrow_nextPage:
                if (page < listUser.size() - 1)
                    page++;
                displayTitle();
                break;
            case R.id.arrow_previousPage:
                if (0 < page)
                    page--;
                displayTitle();
                break;
        }
    }
}