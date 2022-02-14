package fr.dut.ptut2021.activities;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import fr.dut.ptut2021.R;
import fr.dut.ptut2021.database.CreateDatabase;
import fr.dut.ptut2021.models.database.app.Game;
import fr.dut.ptut2021.models.database.app.SubGame;
import fr.dut.ptut2021.models.database.app.User;
import fr.dut.ptut2021.models.database.log.GameLog;
import fr.dut.ptut2021.utils.GlobalUtils;
import fr.dut.ptut2021.utils.MyVibrator;

public class StatisticPage extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener, View.OnTouchListener {

    private CreateDatabase db = null;
    private User currentUser;
    private TextView userTitle, barChartTitle, leftStatTitle, rightStatTitle, leftStatText, rightStatText, spinnerStatText;
    private Button generalButton, lettresButton, chiffresButton;
    private ImageView arrowNext, arrowPrevious, leftStatIcon, rightStatIcon, spinnerStatIcon;
    private Spinner gameSpinner, difficultySpinner, subGameSpinner;
    private List<User> userList;
    private List<Game> gameList;
    private int userPage = 0, currentGameId;
    private String themeName = "General";
    private long startWeekTime;
    private final long DAY_MILLIS = 24 * 3600 * 1000;
    private boolean spinnerRefresh = false, subGameExist = false;

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

    private void initViews() {
        userTitle = findViewById(R.id.title_StatisticPage);
        generalButton = findViewById(R.id.buttonGeneral_statistics);
        lettresButton = findViewById(R.id.buttonLettres_statistics);
        chiffresButton = findViewById(R.id.buttonChiffres_statistics);
        arrowNext = findViewById(R.id.arrow_nextPage);
        arrowPrevious = findViewById(R.id.arrow_previousPage);

        barChartTitle = findViewById(R.id.title_bar_chart);
        leftStatTitle = findViewById(R.id.title_left_stat1);
        rightStatTitle = findViewById(R.id.title_right_stat1);
        leftStatText = findViewById(R.id.text_left_stat1);
        rightStatText = findViewById(R.id.text_right_stat1);
        leftStatIcon = findViewById(R.id.image_left_stat1);
        rightStatIcon = findViewById(R.id.image_right_stat1);

        gameSpinner = findViewById(R.id.spinner_game_stats);
        difficultySpinner = findViewById(R.id.spinner_difficulty_stats);
        subGameSpinner = findViewById(R.id.spinner_subgame_stats);
        spinnerStatIcon = findViewById(R.id.image_spinner_stats);
        spinnerStatText = findViewById(R.id.text_spinner_stats);
    }

    private void initOnClickViews() {
        generalButton.setOnClickListener(this);
        lettresButton.setOnClickListener(this);
        chiffresButton.setOnClickListener(this);
        arrowNext.setOnClickListener(this);
        arrowPrevious.setOnClickListener(this);
        generalButton.setEnabled(false);
        gameSpinner.setOnItemSelectedListener(this);
        gameSpinner.setOnTouchListener(this);
        difficultySpinner.setOnItemSelectedListener(this);
        difficultySpinner.setOnTouchListener(this);
        subGameSpinner.setOnItemSelectedListener(this);
        subGameSpinner.setOnTouchListener(this);
    }

    private void createDbAndImportUsers() {
        db = CreateDatabase.getInstance(StatisticPage.this);
        if (!db.appDao().tabUserIsEmpty()) {
            userList = db.appDao().getAllUsers();
        }
        gameList = db.appDao().getAllGames();
    }

    private void displayNewUserPage() {
        if (!userList.isEmpty()) {
            currentUser = userList.get(userPage);
            displayUserTitle();
        }
        setTitleText();
        displayNewCategoryPage();
    }

    private void displayNewCategoryPage() {
        createBarChart(getGameFrequencyData());
        //createLineChart(getGameAvgStarsData());
        setStatsText();
        setSpinnerResources();
    }

    private void displayUserTitle() {
        userTitle.setText(GlobalUtils.getInstance().cutString(currentUser.getUserName(), 15));
        verifyUserPageLocation();
    }

    @SuppressLint("SetTextI18n")
    private void setTitleText() {
        barChartTitle.setText("Fréquence de jeu");
        leftStatTitle.setText("Jeu le plus joué");
        rightStatTitle.setText("Jeu le moins joué");
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
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
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
        barChart.animateY(500);
        barChart.setTouchEnabled(false);
        barChart.setNoDataText("Commencer a jouer !");
        barChart.getLegend().setEnabled(false);
        barChart.setExtraOffsets(0, 0, 0, 5);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setStartWeekTimeAndDate() {
        @SuppressLint("SimpleDateFormat") DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        Date currentDate = new Date();

        String str = df.format(currentDate) + " 00:00:00";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime ldt = LocalDateTime.parse(str, formatter);

        startWeekTime = ldt.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli() - (6 * DAY_MILLIS);
    }

    private Map<String, Integer> getGameFrequencyData() {
        Map<String, Integer> mapData = new LinkedHashMap<>();
        //La map se mélange si ce n'est pas une LinkedHashMap
        List<GameLog> listLog;

        if (themeName.equals("Chiffres") || themeName.equals("Lettres"))
            listLog = db.gameLogDao().getAllGameLogAfterTimeByTheme(currentUser.getUserId(), themeName, startWeekTime);
        else
            listLog = db.gameLogDao().getAllGameLogAfterTime(currentUser.getUserId(), startWeekTime);

        DateFormat df = new SimpleDateFormat("E", Locale.FRENCH);
        for (int i = 0; i < 7; i++) {
            mapData.put(df.format(startWeekTime + (DAY_MILLIS * i)).toUpperCase(), 0);
        }

        int nbWeekDay = 0;
        for (int i = 0; i < listLog.size(); i++) {
            long gameTime = listLog.get(i).getEndGameDate();
            for (Map.Entry<String, Integer> val : mapData.entrySet()) {
                if (startWeekTime + (DAY_MILLIS * nbWeekDay) <= gameTime && gameTime < startWeekTime + (DAY_MILLIS * (nbWeekDay + 1))) {
                    mapData.put(val.getKey(), mapData.get(val.getKey()) + 1);
                }
                nbWeekDay++;
            }
            nbWeekDay = 0;
        }
        return mapData;
    }

/*
    public void createLineChart(Map<Integer, Float> mapData) {
        LineChart lineChart = findViewById(R.id.line_chart);
        List<Entry> data = new ArrayList<>();

        for (Map.Entry<Integer, Float> val : mapData.entrySet())
            data.add(new Entry(val.getKey(), val.getValue()));

        LineDataSet lineDataSet = new LineDataSet(data, "Data");
        lineDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        lineDataSet.setValueTextColor(Color.BLACK);
        lineDataSet.setLineWidth(3);
        lineDataSet.setCircleRadius(5);
        lineDataSet.setDrawValues(false);

        String[] list = {"10", "20", "30", "40", "50", "60"};

        XAxis axis = lineChart.getXAxis();
        axis.setTypeface(Typeface.MONOSPACE);
        axis.setPosition(XAxis.XAxisPosition.BOTTOM);
        axis.setTextSize(15f);
        axis.setAxisMinimum(1);
        axis.setGranularity(1);
        axis.setAxisMaximum(6);
        axis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                return list[Math.round(value) - 1];
            }
        });

        AxisBase axisBase = lineChart.getAxis(YAxis.AxisDependency.LEFT);
        axisBase.setGranularity(1f);
        axisBase.setTextSize(15f);
        axisBase.setAxisMinimum(0f);
        axisBase.setAxisMaximum(3f);
        axisBase.setTypeface(Typeface.MONOSPACE);
        lineChart.getAxis(YAxis.AxisDependency.RIGHT).setEnabled(false);

        LineData lineData = new LineData(lineDataSet);
        lineChart.setData(lineData);

        lineChart.getDescription().setEnabled(false);
        lineChart.animateY(500);
        lineChart.setTouchEnabled(false);
        lineChart.setNoDataText("Tu n'as encore jamais joué !");
        lineChart.getLegend().setEnabled(false);
        lineChart.setExtraOffsets(0, 0, 0, 5);
    }
*/

    private Map<Integer, Float> getGameAvgStarsData() {
        Map<Integer, Float> mapData = new TreeMap<>();
        List<GameLog> listLog;

        if (themeName.equals("Chiffres") || themeName.equals("Lettres"))
            listLog = db.gameLogDao().getAllGameLogByUserLimitByTheme(currentUser.getUserId(), themeName);
        else
            listLog = db.gameLogDao().getAllGameLogByUserLimit(currentUser.getUserId());

        final int COLUMN = 6;
        int it = 0;
        for (int i = 0; i < COLUMN; i++) {
            float n = 0;
            float sum = 0;
            while ((it + 1) % 10 != 0 && listLog.size() > it) {
                n++;
                sum += listLog.get(it).getStars();
                it++;
            }
            float avg;
            if (n == 0)
                avg = 0;
            else
                avg = sum/n;

            mapData.put(COLUMN - i, avg);
            it++;
        }

        return mapData;
    }

    @SuppressLint("SetTextI18n")
    private void setStatsText() {
        Map<Game, Integer> gameCountMap = new LinkedHashMap<>();

        if (!db.gameLogDao().tabGameLogIsEmpty(currentUser.getUserId())) {
            for (int i = 0; i < gameList.size(); i++) {
                if (themeName.equals("Chiffres") || themeName.equals("Lettres")) {
                    if (gameList.get(i).getThemeName().equals(themeName))
                        gameCountMap.put(gameList.get(i), db.gameLogDao().getGameLogNbGame(currentUser.getUserId(), gameList.get(i).getGameId()));
                } else {
                    gameCountMap.put(gameList.get(i), db.gameLogDao().getGameLogNbGame(currentUser.getUserId(), gameList.get(i).getGameId()));
                }
            }

            Game minGame = null, maxGame = null;
            int minIt = -1, maxIt = -1;

            for (Map.Entry<Game, Integer> val : gameCountMap.entrySet()) {
                if (val.getValue() < minIt || minIt == -1) {
                    minGame = val.getKey();
                    minIt = val.getValue();
                }
                if (val.getValue() > maxIt || maxIt == -1) {
                    maxGame = val.getKey();
                    maxIt = val.getValue();
                }
            }
            if (minGame != null) {
                leftStatText.setText(maxGame.getGameName());
                leftStatIcon.setImageResource(maxGame.getGameImage());
            }
            if (maxGame != null) {
                rightStatText.setText(minGame.getGameName());
                rightStatIcon.setImageResource(minGame.getGameImage());
            }
        }
        else {
            leftStatText.setText("Aucun");
            leftStatIcon.setImageResource(R.drawable.ic_square);
            rightStatText.setText("Aucun");
            rightStatIcon.setImageResource(R.drawable.ic_square);
        }
    }

    private void setSpinnerResources() {
        if (themeName.equals("General")) {
            hideSpinners();
        } else {
            List<Game> gamePlayedList = db.gameLogDao().getAllGamePlayed(currentUser.getUserId(), themeName);
            if (gamePlayedList.isEmpty())
                hideSpinners();
            else {
                String[] gameNameTab = new String[gamePlayedList.size()];
                for (int i = 0; i < gamePlayedList.size(); i++)
                    gameNameTab[i] = gamePlayedList.get(i).getGameName();

                ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_spinner_item,
                        gameNameTab);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                gameSpinner.setAdapter(adapter);

                currentGameId = db.appDao().getGameId(gameSpinner.getSelectedItem().toString(), themeName);
                subGameExist = updateSubGameSpinner();
                updateSpinnerDifficulty();
                updateGameAverage();
                displaySpinners();
            }
        }
    }

    private void updateSpinnerDifficulty() {
        int maxDifficulty = db.gameLogDao().getGameLogMaxDifByGame(currentUser.getUserId(), currentGameId);
        String[] difficultyTab = new String[maxDifficulty];

        for (int i = 1; i <= maxDifficulty; i++)
            difficultyTab[i-1] = "Difficulté " + i;

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                difficultyTab);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        if (subGameExist)
            difficultySpinner.setAdapter(adapter);
        else
            subGameSpinner.setAdapter(adapter);
    }

    public boolean updateSubGameSpinner() {
        if (db.appDao().getAllGameNameWithSubGame().contains(gameSpinner.getSelectedItem().toString())) {
            subGameSpinner.setVisibility(View.VISIBLE);
            difficultySpinner.setVisibility(View.VISIBLE);

            List<SubGame> subGamePlayedList = db.gameLogDao().getAllSubGamePlayed(
                    currentUser.getUserId(),
                    db.appDao().getGameId(gameSpinner.getSelectedItem().toString(),
                    themeName));

            if (!subGamePlayedList.isEmpty()) {
                String[] subGameNameTab = new String[subGamePlayedList.size()];
                for (int i = 0; i < subGamePlayedList.size(); i++)
                    subGameNameTab[i] = subGamePlayedList.get(i).getSubGameName();

                ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_spinner_item,
                        subGameNameTab);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                subGameSpinner.setAdapter(adapter);
                return true;
            }
        }
        else {
            difficultySpinner.setVisibility(View.GONE);
        }
        return false;
    }

    @SuppressLint("SetTextI18n")
    private void updateGameAverage() {
        String val = "";
        Log.e("APPLOG", "subgame : " + subGameExist);
        Log.e("APPLOG", "userId : " + currentUser.getUserId());
        Log.e("APPLOG", "gameId : " + currentGameId);
        int difficulty = difficultySpinner.getSelectedItemPosition() + 1;
        Log.e("APPLOG", "difficulty : " + difficulty);
        if (subGameExist) {
            val = db.gameLogDao().getGameAvgBySubGameIdAndDifficulty(
                    currentUser.getUserId(),
                    currentGameId,
                    db.appDao().getSubGameByName(subGameSpinner.getSelectedItem().toString()).getSubGameId(),
                    difficultySpinner.getSelectedItemPosition() + 1
            ).toString();
        } else {
            val = db.gameLogDao().getGameAvgByGameIdAndDifficulty(
                    currentUser.getUserId(),
                    currentGameId,
                    difficultySpinner.getSelectedItemPosition() + 1
            ).toString();
        }
        spinnerStatText.setText(val);
    }

    private void hideSpinners() {
        gameSpinner.setVisibility(View.GONE);
        difficultySpinner.setVisibility(View.GONE);
        subGameSpinner.setVisibility(View.GONE);
        spinnerStatText.setVisibility(View.GONE);
        spinnerStatIcon.setVisibility(View.GONE);
    }

    private void displaySpinners() {
        gameSpinner.setVisibility(View.VISIBLE);
        subGameSpinner.setVisibility(View.VISIBLE);
        spinnerStatText.setVisibility(View.VISIBLE);
        spinnerStatIcon.setVisibility(View.VISIBLE);
    }
    
    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    private void verifyUserPageLocation() {
        arrowPrevious.setAlpha(1f);
        arrowNext.setAlpha(1f);
        arrowPrevious.setEnabled(true);
        arrowNext.setEnabled(true);

        if (userPage == 0) {
            arrowPrevious.setAlpha(0.5f);
            arrowPrevious.setEnabled(false);
        }
        if (userPage == userList.size() - 1) {
            arrowNext.setAlpha(0.5f);
            arrowNext.setEnabled(false);
        }
    }

    public void lockButton(Button button) {
        generalButton.setEnabled(true);
        lettresButton.setEnabled(true);
        chiffresButton.setEnabled(true);
        button.setEnabled(false);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonGeneral_statistics:
                lockButton(generalButton);
                themeName = "General";
                displayNewCategoryPage();
                break;
            case R.id.buttonChiffres_statistics:
                lockButton(chiffresButton);
                themeName = "Chiffres";
                displayNewCategoryPage();
                break;
            case R.id.buttonLettres_statistics:
                lockButton(lettresButton);
                themeName = "Lettres";
                displayNewCategoryPage();
                break;

            case R.id.arrow_nextPage:
                if (userPage < userList.size() - 1) {
                   MyVibrator.getInstance().vibrate(StatisticPage.this, 35);
                    userPage++;
                }
                displayNewUserPage();
                break;
            case R.id.arrow_previousPage:
                if (0 < userPage) {
                   MyVibrator.getInstance().vibrate(StatisticPage.this, 35);
                    userPage--;
                }
                displayNewUserPage();
                break;

            default:
                break;
        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (spinnerRefresh) {
            switch (parent.getId()) {
                case R.id.spinner_game_stats:
                    currentGameId = db.appDao().getGameId(gameSpinner.getSelectedItem().toString(), themeName);
                    subGameExist = updateSubGameSpinner();
                    updateSpinnerDifficulty();
                    updateGameAverage();
                    break;
                case R.id.spinner_difficulty_stats:
                    updateGameAverage();
                    break;
                default:
                    break;
            }
            spinnerRefresh = false;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        spinnerRefresh = true;
        return false;
    }
}