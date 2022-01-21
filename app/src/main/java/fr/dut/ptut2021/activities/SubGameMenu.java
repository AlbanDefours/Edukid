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
import fr.dut.ptut2021.adapters.subgame.SubGameAdapter;
import fr.dut.ptut2021.database.CreateDatabase;
import fr.dut.ptut2021.models.database.app.SubGame;
import fr.dut.ptut2021.utils.GlobalUtils;
import fr.dut.ptut2021.utils.MySharedPreferences;
import fr.dut.ptut2021.utils.MyVibrator;

public class SubGameMenu extends AppCompatActivity {

    private int userId;
    private SubGameAdapter adapter;
    private CreateDatabase db = null;
    private List<SubGame> subGameList;
    private String themeName, gameName;
    private RecyclerView recyclerViewListGame;
    private List<Boolean> subgamelocks = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subgame_menu);

        db = CreateDatabase.getInstance(SubGameMenu.this);

        themeName = MySharedPreferences.getThemeName(SubGameMenu.this);
        gameName = MySharedPreferences.getGameName(SubGameMenu.this);
        userId = MySharedPreferences.getUserId(SubGameMenu.this);
        getAllSubGames();
        getAllLockGames();
        createRecyclerView();

        recyclerViewListGame.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(), recyclerViewListGame, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        saveGameName(position);
                        if (!isLock(position)) {
                            MyVibrator.vibrate(SubGameMenu.this, 35);
                            GlobalUtils.startGame(SubGameMenu.this, "SubMemory", false, false);
                        } else {
                            MyVibrator.vibrate(SubGameMenu.this, 60);
                            GlobalUtils.toast(SubGameMenu.this, "Atteint le niveau 4 dans le jeu " + subGameList.get(position - 1).getSubGameName(), false);
                        }
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                    }
                })
        );

    }

    @Override
    protected void onStart() {
        super.onStart();
        getAllSubGames();
        getAllLockGames();
        tesyhduehfkehzfklhfzh();
        createRecyclerView();
        adapter.notifyDataSetChanged();
    }

    private void tesyhduehfkehzfklhfzh() {
        for (SubGame temp : subGameList){
            System.out.print(temp.getSubGameName()+"-");
        }
        System.out.println("------------");
        for (Boolean temp : subgamelocks){
            System.out.print(temp+"-");
        }
        System.out.println("------------");
    }

    private void getAllSubGames() {
        subGameList = db.appDao().getAllSubGamesByGame(db.appDao().getGameId(gameName, themeName));
    }

    private void getAllLockGames(){
        subgamelocks.clear();
        for (int i = 0; i < subGameList.size(); i++)
            subgamelocks.add(isLock(i));
    }

    private boolean isLock(int position) {
        if (themeName.equals("Chiffres")) {
            switch (position + 1) {
                case 1:
                    return false;
                case 2:
                    return db.gameDao().getMemoryDataMaxDifficulty(userId, themeName, 1) < 4;
                case 3:
                    return db.gameDao().getMemoryDataMaxDifficulty(userId, themeName, 2) < 4;
                case 4:
                    return db.gameDao().getMemoryDataMaxDifficulty(userId, themeName, 3) < 4;
            }
        }
        return true;
    }

    private void saveGameName(int position) {
        MySharedPreferences.setSharedPreferencesString(SubGameMenu.this, "subGameName", subGameList.get(position).getSubGameName());
        MySharedPreferences.setSharedPreferencesInt(SubGameMenu.this, "subGameId", subGameList.get(position).getSubGameId());
        MySharedPreferences.commit();
    }

    private void createRecyclerView() {
        recyclerViewListGame = findViewById(R.id.recyclerview_subgame);
        recyclerViewListGame.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SubGameAdapter(getApplicationContext(), subGameList, subgamelocks);
        recyclerViewListGame.setAdapter(adapter);
    }
}
