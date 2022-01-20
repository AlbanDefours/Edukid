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
import fr.dut.ptut2021.adapters.subgame.SubGameAdapter;
import fr.dut.ptut2021.database.CreateDatabase;
import fr.dut.ptut2021.game.Memory;
import fr.dut.ptut2021.models.database.app.Game;
import fr.dut.ptut2021.models.database.app.SubGame;
import fr.dut.ptut2021.utils.*;

public class SubGameMenu extends AppCompatActivity {

    private String themeName, gameName;
    private CreateDatabase db = null;
    private RecyclerView recyclerViewListGame;
    private List<SubGame> subGameList;
    private int userId;
    private List<Boolean> subgamelocks = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subgame_menu);
        db = CreateDatabase.getInstance(SubGameMenu.this);

        db = CreateDatabase.getInstance(getApplicationContext());
        themeName = MySharedPreferences.getThemeName(SubGameMenu.this);
        gameName = MySharedPreferences.getGameName(SubGameMenu.this);
        createRecyclerView();

        subGameList = db.appDao().getAllSubGamesByGame(db.appDao().getGameId(gameName, themeName));

        userId = MySharedPreferences.getUserId(SubGameMenu.this);
        createRecyclerView();

        for (int i=0;i<subGameList.size();i++){
            subgamelocks.add(isLock(i));
        }

        recyclerViewListGame.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(), recyclerViewListGame, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        saveGameName(position);
                        if (!isLock(position)) {
                            MyVibrator.vibrate(SubGameMenu.this, 35);
                            GlobalUtils.startGame(SubGameMenu.this, "SubMemory");
                        }else{
                            MyVibrator.vibrate(SubGameMenu.this, 60);
                            GlobalUtils.toast(SubGameMenu.this,"Atteint le niveau 4 dans le jeu "+subGameList.get(position-1).getSubGameName(),false);
                        }
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                    }
                })
        );

    }

    private boolean isLock(int position){
        if(themeName.equals("Chiffres")) {
            switch (position + 1) {
                case 1:
                    return false;
                case 2:
                    if (db.gameDao().getMemoryDataDifficulty(userId, themeName, 1) >= 4) {
                        return false;
                    } else {
                        return true;
                    }
                case 3:
                    if (db.gameDao().getMemoryDataDifficulty(userId, themeName, 2) >= 4) {
                        return false;
                    } else {
                        return true;
                    }
                case 4:
                    if (db.gameDao().getMemoryDataDifficulty(userId, themeName, 3) >= 4) {
                        return false;
                    } else {
                        return true;
                    }
            }
        }
        return true;
    }

    private void saveGameName(int position){
        MySharedPreferences.setSharedPreferencesString(SubGameMenu.this, "subGameName", subGameList.get(position).getSubGameName());
        MySharedPreferences.commit();
    }

    private void createRecyclerView() {
        recyclerViewListGame = findViewById(R.id.recyclerview_subgame);
        recyclerViewListGame.setLayoutManager(new LinearLayoutManager(this));

        recyclerViewListGame.setAdapter(new SubGameAdapter(getApplicationContext(), subGameList, subgamelocks));
    }
}
