package fr.dut.ptut2021.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import fr.dut.ptut2021.R;
import fr.dut.ptut2021.adapters.RecyclerItemClickListener;
import fr.dut.ptut2021.adapters.game.GameAdapter;
import fr.dut.ptut2021.database.CreateDatabase;

import fr.dut.ptut2021.models.database.app.Game;
import fr.dut.ptut2021.utils.GlobalUtils;
import fr.dut.ptut2021.utils.MySharedPreferences;
import fr.dut.ptut2021.utils.MyVibrator;


public class GameMenu extends AppCompatActivity {

    private TextView title;
    private String themeName;
    private CreateDatabase db = null;
    private RecyclerView recyclerViewListGame;
    private List<Game> gameList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_menu);

        getThemeName();
        setTitle();
        createDatabaseAndImportGames();
        createRecyclerView();

        recyclerViewListGame.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(), recyclerViewListGame, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                       MyVibrator.getInstance().vibrate(GameMenu.this, 35);
                        saveGameName(position);
                        GlobalUtils.getInstance().startGame(GameMenu.this, gameList.get(position).getGameName(), false, false);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                    }
                })
        );
    }

    private void getThemeName(){
        themeName = MySharedPreferences.getInstance().getThemeName(this);
    }

    private void setTitle(){
        title = findViewById(R.id.title_gameMenu);
        title.setText("jeu "+themeName.toLowerCase());
    }

    private void saveGameName(int position){
        MySharedPreferences.getInstance().setSharedPreferencesString(this, "gameName", gameList.get(position).getGameName());
        MySharedPreferences.getInstance().setSharedPreferencesInt(this, "gameId", gameList.get(position).getGameId());
        MySharedPreferences.getInstance().commit();
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
}
