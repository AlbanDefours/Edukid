package fr.dut.ptut2021.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import fr.dut.ptut2021.R;
import fr.dut.ptut2021.database.CreateDatabase;
import fr.dut.ptut2021.models.database.app.User;
import fr.dut.ptut2021.models.database.log.GameResultLog;
import fr.dut.ptut2021.utils.MyVibrator;

public class StatisticPage extends AppCompatActivity implements View.OnClickListener {

    private int pageUser = 0, pageCategory = 0;
    private TextView userTitle, barChartTitle;
    private int page = 0;
    private List<User> listUser;
    private CreateDatabase db = null;
    private Button generalButton, lettresButton, chiffresButton;
    private ImageView nextPage, previousPage;
    private long startWeekTime;
    private String startWeekDate;
    private final long DAY_MILLIS = 24*3600*1000;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic_page);

        initViews();
        initOnClickViews();
        createDbAndImportUsers();
        setStartWeekTimeAndDate();
        displayNewUserPage();
    }

    private void initOnClickViews() {
        generalButton.setOnClickListener(this);
        lettresButton.setOnClickListener(this);
        chiffresButton.setOnClickListener(this);
        nextPage.setOnClickListener(this);
        previousPage.setOnClickListener(this);
        generalButton.setEnabled(false);
    }

    private void initViews() {
        userTitle = findViewById(R.id.title_StatisticPage);
        generalButton = findViewById(R.id.buttonGeneral_statistics);
        lettresButton = findViewById(R.id.buttonLettres_statistics);
        chiffresButton = findViewById(R.id.buttonChiffres_statistics);
        nextPage = findViewById(R.id.arrow_nextPage);
        previousPage = findViewById(R.id.arrow_previousPage);

        barChartTitle = findViewById(R.id.title_bar_chart);
    }

    private void createDbAndImportUsers() {
        db = CreateDatabase.getInstance(StatisticPage.this);
        if (!db.appDao().tabUserIsEmpty()) {
            listUser = db.appDao().getAllUsers();
        }
    }

    private void displayNewUserPage() {
        if (!listUser.isEmpty()) displayUserTitle();
        displayGeneralPage();
    }


    private void displayGeneralPage() {
        barChartTitle.setText("Fréquence de jeu");
        createBarChart(getGameFrequencyData());
    }

    private void displayChiffresPage() {

    }

    private void displayLettresPage() {

    }

    private void displayUserTitle() {
        userTitle.setText(listUser.get(pageUser).getUserName());
        verifyPageUserLocation();
    }

    private void createBarChart(Map<String, Integer> mapData) {
        BarChart barChart = findViewById(R.id.bar_chart);
        List<BarEntry> data = new ArrayList<>();

        String[] days = new String[7];
        int it = 0;
        for (Map.Entry<String, Integer> val : mapData.entrySet()) {
            data.add(new BarEntry(it, val.getValue()));
            days[it] = val.getKey();
            it++;
        }

        BarDataSet barDataSet = new BarDataSet(data, "Data");
        barDataSet.setColors(ColorTemplate.JOYFUL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setDrawValues(false);

        XAxis axis = barChart.getXAxis();
        axis.setTypeface(Typeface.MONOSPACE);
        axis.setPosition(XAxis.XAxisPosition.BOTTOM);
        axis.setTextSize(15f);
        axis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                return days[(int) value];
            }
        });

        AxisBase axisBase = barChart.getAxis(YAxis.AxisDependency.LEFT);
        axisBase.setGranularity(1f);
        axisBase.setTextSize(15f);
        axisBase.setAxisMinimum(0f);
        axisBase.setTypeface(Typeface.MONOSPACE);
        barChart.getAxis(YAxis.AxisDependency.RIGHT).setEnabled(false);

        BarData barData = new BarData(barDataSet);
        barChart.setFitBars(true);
        barChart.setData(barData);

        barChart.getDescription().setEnabled(false);
        barChart.animateY(1000);
        barChart.setTouchEnabled(false);
        barChart.setNoDataText("Commencer a jouer !");
        barChart.getLegend().setEnabled(false);
        barChart.setExtraOffsets(0,0,0,5);
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setStartWeekTimeAndDate() {
        @SuppressLint("SimpleDateFormat") DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        Date currentDate = new Date();

        String str = df.format(currentDate) + " 00:00:00";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime ldt = LocalDateTime.parse(str, formatter);

        startWeekTime = ldt.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli() - (6* DAY_MILLIS);
    }

    private Map<String, Integer> getGameFrequencyData() {
        Map<String, Integer> mapData = new LinkedHashMap<>();
        //La map se mélange si ce n'est pas une LinkedHashMap
        List<GameResultLog> listLog = db.gameLogDao().getAllGameResultLogAfterTime(listUser.get(pageUser).getUserId(), startWeekTime);

        DateFormat df = new SimpleDateFormat("E", Locale.FRENCH);
        for (int i = 0; i < 7; i++) {
            mapData.put(df.format(startWeekTime + (DAY_MILLIS *i)).toUpperCase(), 0);
        }

        int nbWeekDay = 0;
        for (int i = 0; i < listLog.size(); i++) {
            long gameTime = listLog.get(i).getEndGameDate();
            for (Map.Entry<String, Integer> val : mapData.entrySet()) {
                if (startWeekTime + (DAY_MILLIS *nbWeekDay) <= gameTime && gameTime < startWeekTime + (DAY_MILLIS *(nbWeekDay+1))) {
                    mapData.put(val.getKey(), mapData.get(val.getKey())+1);
                }
                nbWeekDay++;
            }
            nbWeekDay = 0;
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


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonGeneral_statistics:
                lockButton(generalButton);
                pageCategory = 0;
                displayGeneralPage();
                break;
            case R.id.buttonChiffres_statistics:
                lockButton(chiffresButton);
                pageCategory = 1;
                displayChiffresPage();
                break;
            case R.id.buttonLettres_statistics:
                lockButton(lettresButton);
                pageCategory = 2;
                displayLettresPage();
                break;

            case R.id.arrow_nextPage:
                if (page < listUser.size() - 1) {
                    MyVibrator.vibrate(StatisticPage.this, 35);
                    page++;
                }
                displayNewUserPage();
                break;
            case R.id.arrow_previousPage:
                if (0 < page) {
                    MyVibrator.vibrate(StatisticPage.this, 35);
                    page--;
                }
                displayNewUserPage();
                break;
            default:
                break;
        }
    }
}