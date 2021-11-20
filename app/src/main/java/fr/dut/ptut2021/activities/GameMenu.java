package fr.dut.ptut2021.activities;

import android.content.Intent;
import android.os.Bundle;
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
import fr.dut.ptut2021.models.Game;

public class GameMenu extends AppCompatActivity {

    private String themeName;
    private CreateDatabase db = null;
    private RecyclerView recyclerViewListGame;
    private List<Game> gameList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_menu);

        createDatabaseAndImportGames();
        getThemeName();
        createRecyclerView();

        recyclerViewListGame.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(), recyclerViewListGame, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        findWhichGame(position);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                    }
                })
        );
    }

    private void createDatabaseAndImportGames() {
        db = CreateDatabase.getInstance(getApplicationContext());
        gameList = db.appDao().getAllGamesByTheme(themeName);
    }

    private void getThemeName() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            themeName = bundle.getString("themeName", " ");
        }
    }

    private void createRecyclerView() {
        recyclerViewListGame = findViewById(R.id.recyclerview_game);
        recyclerViewListGame.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewListGame.setAdapter(new GameAdapter(getApplicationContext(), gameList));
    }

    private void startGame(Intent intent, int position) {
        intent.putExtra("themeName", themeName);
        startActivity(intent);
    }

    //TODO A mettre les jeux développer
    private void findWhichGame(int position) {
        switch (position) {
            case 0:
                //startGame(new Intent().setClass(getApplicationContext(), Memory.class), position);
                break;
            case 1:
                //startGame(new Intent().setClass(getApplicationContext(), Memory.class), position);
                break;
        }
    }
}