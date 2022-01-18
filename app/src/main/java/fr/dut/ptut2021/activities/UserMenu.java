package fr.dut.ptut2021.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import fr.dut.ptut2021.R;
import fr.dut.ptut2021.adapters.RecyclerItemClickListener;
import fr.dut.ptut2021.adapters.user.UserAdapter;
import fr.dut.ptut2021.database.CreateDatabase;
import fr.dut.ptut2021.models.databse.User;

public class UserMenu extends AppCompatActivity {

    private Vibrator vibe;
    private UserAdapter adapter;
    private CreateDatabase db = null;
    private RecyclerView recyclerView;
    private List<User> listUser = new ArrayList<>();
    private ImageView settings, adultProfile, addUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_menu);

        vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        initializeFindView();
        hideAddUserImage();

        recyclerView.addOnItemTouchListener(
            new RecyclerItemClickListener(getApplicationContext(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    vibrate();
                    saveUserNameSahredPref(position);
                    startThemeMenu();
                }

                @Override
                public void onLongItemClick(View view, int position) {
                }
            })
        );

        settings.setOnClickListener(v -> startSettingPage());

        adultProfile.setOnClickListener(v -> startStatisticPage());
    }

    public void vibrate(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            vibe.vibrate(VibrationEffect.createOneShot(35, VibrationEffect.DEFAULT_AMPLITUDE));
        else
            vibe.vibrate(35);
    }

    @Override
    protected void onStart() {
        super.onStart();
        createAndGetDatabase();
        createRecyclerView();
        adapter.notifyDataSetChanged();
    }

    private void createAndGetDatabase() {
        db = CreateDatabase.getInstance(UserMenu.this);
        getAllUser();
    }

    private void initializeFindView(){
        adultProfile = findViewById(R.id.adultProfile);
        settings = findViewById(R.id.settings);
        addUser = findViewById(R.id.addUser);
        recyclerView = findViewById(R.id.recyclerview_users);
    }

    private void hideAddUserImage() {
        addUser.setVisibility(View.GONE);
    }

    private void getAllUser() {
        if (!db.appDao().tabUserIsEmpty()) {
            listUser = db.appDao().getAllUsers();
        } else {
            startAddUserPage();
            finish();
        }
    }

    private void createRecyclerView() {
        RecyclerView.LayoutManager mLayoutManager;
        if (listUser.size() < 5) {
            mLayoutManager = new GridLayoutManager(this, 1);
        } else {
            mLayoutManager = new GridLayoutManager(this, 2);
        }

        recyclerView.setLayoutManager(mLayoutManager);
        adapter = new UserAdapter(getApplicationContext(), listUser, false);
        recyclerView.setAdapter(adapter);
    }

    private void startSettingPage() {
        vibrate();
        Intent intent = new Intent().setClass(getApplicationContext(), UserResume.class);
        startActivity(intent);
    }

    private void startStatisticPage() {
        vibrate();
        Intent intent = new Intent().setClass(getApplicationContext(), StatisticPage.class);
        startActivity(intent);
    }

    private void startAddUserPage() {
        vibrate();
        Intent intent = new Intent().setClass(getApplicationContext(), UserEdit.class);
        intent.putExtra("addUser", true);
        startActivity(intent);
        finish();
    }

    private void startThemeMenu() {
        Intent intent = new Intent().setClass(getApplicationContext(), ThemeMenu.class);
        startActivity(intent);
    }

    private void saveUserNameSahredPref(int position){
        SharedPreferences settings = getSharedPreferences("MyPref", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("userName", listUser.get(position).getUserName());
        editor.putInt("userId", listUser.get(position).getUserId());
        editor.putString("userImage", listUser.get(position).getUserImage());
        editor.commit();
    }
}