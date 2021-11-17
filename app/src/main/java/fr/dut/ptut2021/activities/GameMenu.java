package fr.dut.ptut2021.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import fr.dut.ptut2021.R;
import fr.dut.ptut2021.adapters.RecyclerItemClickListener;
import fr.dut.ptut2021.adapters.game.GameAdapter;
import fr.dut.ptut2021.database.CreateDatabase;
import fr.dut.ptut2021.models.Game;
import fr.dut.ptut2021.models.Theme;

public class GameMenu extends AppCompatActivity {

    private String themeName;
    private CreateDatabase db = null;
    private RecyclerView recyclerViewListGame;
    RecyclerView.LayoutManager mLayoutManager;
    private List<Game> gameList = new ArrayList<>();
    private List<Theme> listTheme = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_menu);

        getDatabase();
        getActivityIntent();
        getAllThemes();
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

    //TODO create list from BDD themes
    private void getDatabase() {
        db = CreateDatabase.getInstance(GameMenu.this);

        gameList.add(new Game("Memory", R.drawable.memory_icon, listTheme));
        gameList.add(new Game("Dessine", R.drawable.memory_icon, listTheme));
        gameList.add(new Game("Texte a trous", R.drawable.memory_icon, listTheme));
        gameList.add(new Game("Coloriage magique", R.drawable.memory_icon, listTheme));
        gameList.add(new Game("Calcul", R.drawable.memory_icon, listTheme));
    }

    private void getActivityIntent(){
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            themeName = bundle.getString("themeName", " ");
        }
    }

    private void getAllThemes(){
        if (!db.themeDao().tabThemeIsEmpty()) {
            listTheme = db.themeDao().getAllThemes();
        }
    }

    private void createRecyclerView(){
        recyclerViewListGame = findViewById(R.id.recyclerview_game);

        if (gameList.size() < 5) {
            mLayoutManager = new GridLayoutManager(this, 1);
        } else {
            mLayoutManager = new GridLayoutManager(this, 2);
        }

        recyclerViewListGame.setLayoutManager(mLayoutManager);
        recyclerViewListGame.setAdapter(new GameAdapter(getApplicationContext(), gameList));
    }

    private void startGame(Intent intent, int position){
        //intent.putExtra("themeName", gameList.get(position).getTheme()); //TODO a voir si utile
        startActivity(intent);
    }

    private void findWhichGame(int position) {
        switch (position){
            case 0 :
                Toast.makeText(getApplicationContext(), "Memory", Toast.LENGTH_SHORT).show();
                //startGame(new Intent().setClass(getApplicationContext(), Memory.class), position);
                break;
            case 1 :
                Toast.makeText(getApplicationContext(), "Dessine", Toast.LENGTH_SHORT).show();
                //startGame(new Intent().setClass(getApplicationContext(), Memory.class), position);
                break;
        }
    }
}