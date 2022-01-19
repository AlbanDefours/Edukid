package fr.dut.ptut2021.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.dut.ptut2021.R;
import fr.dut.ptut2021.database.CreateDatabase;
import fr.dut.ptut2021.models.databse.User;
import fr.dut.ptut2021.utils.MyVibrator;

public class StatisticPage extends AppCompatActivity implements View.OnClickListener {

    private int page = 0;
    private TextView title;
    private List<User> listUser;
    private CreateDatabase db = null;
    private Button generalPage, lettresPage, chiffresPage;
    private ImageView nextPage, previousPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic_page);

        initViews();
        initOnClickViews();
        createDbAndImportUsers();

        if (!listUser.isEmpty())
            displayTitle();

        displayGeneralPage();
    }

    private void displayGeneralPage() {
        createBarChart(getGameFrequency());
    }

    private void displayChiffresPage() {

    }

    private void displayLettresPage() {

    }

    private void createBarChart(Map<Integer, Integer> mapData) {
        BarChart barChart = findViewById(R.id.bar_chart);
        List<BarEntry> data = new ArrayList<>();

        for (Map.Entry<Integer, Integer> val : mapData.entrySet())
            data.add(new BarEntry(val.getKey(), val.getValue()));

        BarDataSet barDataSet = new BarDataSet(data, "Data");
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(16f);
        //barDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);

        BarData barData = new BarData(barDataSet);
        barChart.setFitBars(true);
        barChart.setData(barData);
        barChart.getDescription().setText("Fréquence de jeu");
        barChart.animateY(2000);

    }

    private void createDbAndImportUsers() {
        db = CreateDatabase.getInstance(StatisticPage.this);
        if (!db.appDao().tabUserIsEmpty()) {
            listUser = db.appDao().getAllUsers();
        }
    }

    private void initViews() {
        title = findViewById(R.id.title_StatisticPage);
        generalPage = findViewById(R.id.buttonGeneral_statistics);
        lettresPage = findViewById(R.id.buttonLettres_statistics);
        chiffresPage = findViewById(R.id.buttonChiffres_statistics);
        nextPage = findViewById(R.id.arrow_nextPage);
        previousPage = findViewById(R.id.arrow_previousPage);
    }

    private void initOnClickViews() {
        generalPage.setOnClickListener(this);
        lettresPage.setOnClickListener(this);
        chiffresPage.setOnClickListener(this);
        nextPage.setOnClickListener(this);
        previousPage.setOnClickListener(this);

        generalPage.setEnabled(false);
    }

    private void displayTitle() {
        title.setText(listUser.get(page).getUserName());
        verifPageLocation();
    }

    private Map<Integer, Integer> getGameFrequency() {
        Map<Integer, Integer> mapData = new HashMap<>();

        return mapData;
    }
    
    private void verifPageLocation() {
        previousPage.setAlpha(1f);
        nextPage.setAlpha(1f);
        if (page == 0)
            previousPage.setAlpha(0.5f);
        if (page == listUser.size() - 1)
            nextPage.setAlpha(0.5f);
    }

    public void lockButton(Button button) {
        generalPage.setEnabled(true);
        lettresPage.setEnabled(true);
        chiffresPage.setEnabled(true);
        button.setEnabled(false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonGeneral_statistics:
                lockButton(generalPage);
                displayGeneralPage();
                break;
            case R.id.buttonChiffres_statistics:
                lockButton(chiffresPage);
                displayChiffresPage();
                break;
            case R.id.buttonLettres_statistics:
                lockButton(lettresPage);
                displayLettresPage();
                break;

            case R.id.arrow_nextPage:
                if (page < listUser.size() - 1) {
                    MyVibrator.vibrate(StatisticPage.this, 35);
                    page++;
                }
                displayTitle();
                break;
            case R.id.arrow_previousPage:
                if (0 < page) {
                    MyVibrator.vibrate(StatisticPage.this, 35);
                    page--;
                }
                displayTitle();
                break;
            default:
                break;
        }
    }
}