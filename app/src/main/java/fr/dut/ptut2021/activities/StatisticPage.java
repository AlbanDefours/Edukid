package fr.dut.ptut2021.activities;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.dut.ptut2021.R;
import fr.dut.ptut2021.database.CreateDatabase;
import fr.dut.ptut2021.models.database.app.User;
import fr.dut.ptut2021.models.database.log.GameResultLog;

public class StatisticPage extends AppCompatActivity implements View.OnClickListener {

    private Vibrator vibe;
    private int pageUser = 0, pageCategory = 0;
    private TextView userTitle, barChartTitle, statTitle1, statTitle2;
    private List<User> listUser;
    private CreateDatabase db = null;
    private Button generalButton, lettresButton, chiffresButton;
    private ImageView nextPage, previousPage;
    private long startWeekTime;
    private final long DAY_MILLIS = 24*3600*1000;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic_page);

        vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        initViews();
        initOnClickViews();
        createDbAndImportUsers();

        startWeekTime = getStartWeekTime();
        displayNewUserPage();
    }

    private void displayNewUserPage() {
        if (!listUser.isEmpty()) displayUserTitle();
        displaygeneralButton();
    }

    private void displaygeneralButton() {
        barChartTitle.setText("Fréquence de jeu");
        statTitle1.setText("Jeu récent");
        statTitle2.setText("Jeu ancien");
        createBarChart(getGameFrequencyData());
    }

    private void displaychiffresButton() {

    }

    private void displaylettresButton() {

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
        barChart.getDescription().setText(" ");
        barChart.animateY(500);
    }

    private void createDbAndImportUsers() {
        db = CreateDatabase.getInstance(StatisticPage.this);
        if (!db.appDao().tabUserIsEmpty()) {
            listUser = db.appDao().getAllUsers();
        }
    }

    private void initViews() {
        userTitle = findViewById(R.id.title_StatisticPage);
        generalButton = findViewById(R.id.buttonGeneral_statistics);
        lettresButton = findViewById(R.id.buttonLettres_statistics);
        chiffresButton = findViewById(R.id.buttonChiffres_statistics);
        nextPage = findViewById(R.id.arrow_nextPage);
        previousPage = findViewById(R.id.arrow_previousPage);

        barChartTitle = findViewById(R.id.title_bar_chart);
        statTitle1 = findViewById(R.id.title_stat1);
        statTitle2 = findViewById(R.id.title_stat2);
    }

    private void initOnClickViews() {
        generalButton.setOnClickListener(this);
        lettresButton.setOnClickListener(this);
        chiffresButton.setOnClickListener(this);
        nextPage.setOnClickListener(this);
        previousPage.setOnClickListener(this);

        generalButton.setEnabled(false);
    }

    private void displayUserTitle() {
        userTitle.setText(listUser.get(pageUser).getUserName());
        verifyPageUserLocation();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private long getStartWeekTime() {
        @SuppressLint("SimpleDateFormat") DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        Date currentDate = new Date();

        String str = df.format(currentDate) + " 00:00:00";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime ldt = LocalDateTime.parse(str, formatter);

        return ldt.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli() - (6* DAY_MILLIS);
    }

    private Map<Integer, Integer> getGameFrequencyData() {
        Map<Integer, Integer> mapData = new HashMap<>();
        List<GameResultLog> listLog = db.gameLogDao().getAllGameResultLogAfterTime(listUser.get(pageUser).getUserId(), startWeekTime);

        final int NB_DAY = 7;
        for (int i = 0; i < NB_DAY; i++) {
            mapData.put(i, 0);
        }

        for (int i = 0; i < listLog.size(); i++) {
            long gameTime = listLog.get(i).getEndGameDate();
            for (int j = 0; j < NB_DAY; j++) {
                if (startWeekTime + (DAY_MILLIS *j) <= gameTime && gameTime < startWeekTime + (DAY_MILLIS *(j+1))) {
                    mapData.put(j, mapData.get(j)+1);
                }
            }
        }
        return mapData;
    }
    
    private void verifyPageUserLocation() {
        previousPage.setAlpha(1f);
        nextPage.setAlpha(1f);
        if (pageUser == 0)
            previousPage.setAlpha(0.5f);
        if (pageUser == listUser.size() - 1)
            nextPage.setAlpha(0.5f);
    }

    public void lockButton(Button button) {
        generalButton.setEnabled(true);
        lettresButton.setEnabled(true);
        chiffresButton.setEnabled(true);
        button.setEnabled(false);
    }

    public void vibrate() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            vibe.vibrate(VibrationEffect.createOneShot(35, VibrationEffect.DEFAULT_AMPLITUDE));
        else
            vibe.vibrate(35);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonGeneral_statistics:
                lockButton(generalButton);
                pageCategory = 0;
                displaygeneralButton();
                break;
            case R.id.buttonChiffres_statistics:
                lockButton(chiffresButton);
                pageCategory = 1;
                displaychiffresButton();
                break;
            case R.id.buttonLettres_statistics:
                lockButton(lettresButton);
                pageCategory = 2;
                displaylettresButton();
                break;

            case R.id.arrow_nextPage:
                if (pageUser < listUser.size() - 1) {
                    vibrate();
                    pageUser++;
                }
                displayNewUserPage();
                break;
            case R.id.arrow_previousPage:
                if (0 < pageUser) {
                    vibrate();
                    pageUser--;
                }
                displayNewUserPage();
                break;
            default:
                break;
        }
    }
}