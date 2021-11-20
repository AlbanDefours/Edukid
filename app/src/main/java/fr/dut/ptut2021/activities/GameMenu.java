package fr.dut.ptut2021.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import fr.dut.ptut2021.game.Memory;
import fr.dut.ptut2021.models.Game;
import fr.dut.ptut2021.models.ThemeWithGame;

public class GameMenu extends AppCompatActivity {

    private List<Game> gameList = new ArrayList<>();
    //private List<ThemeGame> themeGameList = new ArrayList<>();
    private String themeName;
    private CreateDatabase db = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_menu);

        db = CreateDatabase.getInstance(getApplicationContext());

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            themeName = bundle.getString("themeName", " ");
        }

        //TODO create list from BDD themes
        db.gameDao().deleteAllGames();
        List<Game> listGame = db.gameDao().getAllGames();
        for (int i = 0; i < listGame.size(); i++){
            Log.e("LISTGAMEEMPTY", listGame.get(i).getName());
        }

        db.gameDao().createGame(new Game("Jeu 1", R.drawable.chiffres));
        db.gameDao().createGame(new Game("Jeu 2", R.drawable.lettres));

        listGame = db.gameDao().getAllGames();
        for (int i = 0; i < listGame.size(); i++){
            Log.e("LISTGAMEFULL", listGame.get(i).getName());
        }

        //juste pour pas que ca crash
        gameList.add(new Game("test", R.drawable.lettres));


        RecyclerView recyclerViewListGame = findViewById(R.id.recyclerview_game);
        recyclerViewListGame.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewListGame.setAdapter(new GameAdapter(getApplicationContext(), gameList));

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

    private void startGame(Intent intent, int position){
        intent.putExtra("themeName", gameList.get(position).getName());
        startActivity(intent);
    }

    private void findWhichGame(int position) {
        switch (position){
            case 0 :
                startGame(new Intent().setClass(getApplicationContext(), Memory.class), position);
                break;
            case 1 :
                startGame(new Intent().setClass(getApplicationContext(), Memory.class), position);
                break;
        }
    }
}