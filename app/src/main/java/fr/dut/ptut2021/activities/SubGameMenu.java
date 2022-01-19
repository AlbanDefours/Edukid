package fr.dut.ptut2021.activities;

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
import fr.dut.ptut2021.models.database.app.Game;
import fr.dut.ptut2021.utils.*;

public class SubGameMenu extends AppCompatActivity {

    private String themeName;
    private CreateDatabase db = null;
    private RecyclerView recyclerViewListGame;
    private List<Game> subgameList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subgame_menu);

        themeName = MySharedPreferences.getThemeName(SubGameMenu.this);
        createRecyclerView();

        subgameList.add(new Game("Image / Image", R.drawable.img_img));
        subgameList.add(new Game("Image / Image différente", R.drawable.img_imgdiff));
        subgameList.add(new Game("Chiffre / Chiffre", R.drawable.chiffre_chiffre));
        subgameList.add(new Game("Image / Chiffre", R.drawable.img_chiffre));

        recyclerViewListGame.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(), recyclerViewListGame, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        MyVibrator.vibrate(SubGameMenu.this, 35);
                        saveGameName(position);
                        GlobalUtils.startGame(SubGameMenu.this, "SubMemory");
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                    }
                })
        );
    }

    private void saveGameName(int position){
        MySharedPreferences.setSharedPreferencesString(SubGameMenu.this, "subGameName", subgameList.get(position).getGameName());
        MySharedPreferences.commit();
    }

    private void createRecyclerView() {
        recyclerViewListGame = findViewById(R.id.recyclerview_subgame);
        recyclerViewListGame.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewListGame.setAdapter(new GameAdapter(getApplicationContext(), subgameList));
    }
}
