package fr.dut.ptut2021.activities;

import android.content.Context;
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
import fr.dut.ptut2021.game.ClasseMere;
import fr.dut.ptut2021.models.database.app.Game;
//import fr.dut.ptut2021.models.databse.Game;

public class SubGameMenu extends AppCompatActivity {

    private String themeName;
    private CreateDatabase db = null;
    private RecyclerView recyclerViewListGame;
    private List<Game> subgameList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subgame_menu);

        getThemeName();
        createRecyclerView();

        subgameList.add(new Game("Chiffre1", R.drawable.memory_icon));
        subgameList.add(new Game("Chiffre2", R.drawable.memory_icon));
        subgameList.add(new Game("Chiffre3", R.drawable.memory_icon));
        subgameList.add(new Game("Chiffre4", R.drawable.memory_icon));

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
                        new ClasseMere(SubGameMenu.this).findGame("SubMemory");
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
        editor.putString("subGameName", subgameList.get(position).getGameName());
        editor.commit();
    }

    private void createRecyclerView() {
        recyclerViewListGame = findViewById(R.id.recyclerview_subgame);
        recyclerViewListGame.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewListGame.setAdapter(new GameAdapter(getApplicationContext(), subgameList));
    }
}
