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
import fr.dut.ptut2021.adapters.theme.ThemeAdapter;
import fr.dut.ptut2021.database.CreateDatabase;
import fr.dut.ptut2021.models.database.app.Theme;
import fr.dut.ptut2021.utils.*;

public class ThemeMenu extends AppCompatActivity {

    private CreateDatabase db = null;
    private RecyclerView recyclerViewListTheme;
    private List<Theme> listTheme = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme_menu);

        createAndGetDatabase();
        createRecyclerView();

        recyclerViewListTheme.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(), recyclerViewListTheme, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        MyVibrator.vibrate(ThemeMenu.this, 35);
                        saveUserNameSahredPref(position);
                        GlobalUtils.startPage(ThemeMenu.this, "GameMenu", false, false);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                    }
                })
        );
    }

    private void createAndGetDatabase() {
        db = CreateDatabase.getInstance(ThemeMenu.this);
        listTheme = db.appDao().getAllThemes();
    }

    private void createRecyclerView() {
        recyclerViewListTheme = findViewById(R.id.recyclerview_theme);
        recyclerViewListTheme.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewListTheme.setAdapter(new ThemeAdapter(getApplicationContext(), listTheme));
    }

    private void saveUserNameSahredPref(int position) {
        MySharedPreferences.setSharedPreferencesString(this, "themeName", listTheme.get(position).getThemeName());
        MySharedPreferences.commit();
    }
}