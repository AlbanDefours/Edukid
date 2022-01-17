package fr.dut.ptut2021.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import fr.dut.ptut2021.R;
import fr.dut.ptut2021.adapters.RecyclerItemClickListener;
import fr.dut.ptut2021.adapters.game.GameAdapter;
import fr.dut.ptut2021.database.CreateDatabase;
import fr.dut.ptut2021.game.DrawOnIt;
import fr.dut.ptut2021.game.Memory;
import fr.dut.ptut2021.game.PlayWithSound;
import fr.dut.ptut2021.game.WordWithHole;
import fr.dut.ptut2021.models.databse.Game;

public class GameMenu extends AppCompatActivity {

    private String themeName;
    private CreateDatabase db = null;
    private RecyclerView recyclerViewListGame;
    private List<Game> gameList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_menu);

        getThemeName();
        createDatabaseAndImportGames();
        createRecyclerView();

        recyclerViewListGame.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(), recyclerViewListGame, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                            v.vibrate(VibrationEffect.createOneShot(35, VibrationEffect.DEFAULT_AMPLITUDE));
                        else
                            v.vibrate(35);
                        saveGameName(position);
                        findWhichGame(position);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                    }
                })
        );
    }

    private void getThemeName(){
        SharedPreferences settings = getSharedPreferences("MyPref", 0);
        themeName = settings.getString("themeName", "");
    }

    private void saveGameName(int position){
        SharedPreferences settings = getSharedPreferences("MyPref", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("gameName", gameList.get(position).getGameName());
        editor.commit();
    }

    private void createDatabaseAndImportGames() {
        db = CreateDatabase.getInstance(getApplicationContext());
        gameList = db.appDao().getAllGamesByTheme(themeName);
    }

    private void createRecyclerView() {
        recyclerViewListGame = findViewById(R.id.recyclerview_game);
        recyclerViewListGame.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewListGame.setAdapter(new GameAdapter(getApplicationContext(), gameList));
    }

    private void findWhichGame(int position) {
        switch (gameList.get(position).getGameName()) {
            case "Ecoute":
                startActivity(new Intent().setClass(getApplicationContext(), PlayWithSound.class));
                break;
            case "Dessine":
                startActivity(new Intent().setClass(getApplicationContext(), DrawOnIt.class));
                break;
            case "Memory":
                startActivity(new Intent().setClass(getApplicationContext(), Memory.class));
                break;
            case "Mot à trou":
                startActivity(new Intent().setClass(getApplicationContext(), WordWithHole.class));
                break;
        }
    }
}
