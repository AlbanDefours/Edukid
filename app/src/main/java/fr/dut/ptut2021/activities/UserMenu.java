package fr.dut.ptut2021.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import fr.dut.ptut2021.R;
import fr.dut.ptut2021.adapters.RecyclerItemClickListener;
import fr.dut.ptut2021.adapters.user.UserAdapter;
import fr.dut.ptut2021.database.CreateDatabase;
import fr.dut.ptut2021.models.database.app.User;
import fr.dut.ptut2021.utils.GlobalUtils;
import fr.dut.ptut2021.utils.MySharedPreferences;
import fr.dut.ptut2021.utils.MyVibrator;

public class UserMenu extends AppCompatActivity {

    private TextView title;
    private UserAdapter adapter;
    private CreateDatabase db = null;
    private RecyclerView recyclerView;
    private List<User> listUser = new ArrayList<>();
    private ImageView settings, adultProfile, addUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_menu);

        initializeFindView();
        title.setText("profiles");
        hideAddUserImage();

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        MyVibrator.getInstance().vibrate(UserMenu.this, 35);
                        saveUserNameSahredPref(position);
                        GlobalUtils.getInstance().startPage(UserMenu.this, "ThemeMenu", false, false);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        MyVibrator.getInstance().vibrate(UserMenu.this, 35);
                        Intent intent = new Intent().setClass(getApplicationContext(), UserEdit.class);
                        intent.putExtra("userName", listUser.get(position).getUserName());
                        intent.putExtra("userId", listUser.get(position).getUserId());
                        intent.putExtra("userImage", listUser.get(position).getUserImage());
                        intent.putExtra("userImageType", listUser.get(position).getUserImageType());
                        intent.putExtra("shortcut", true);
                        startActivity(intent);
                        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                    }
                })
        );

        settings.setOnClickListener(view -> {
            MyVibrator.getInstance().vibrate(UserMenu.this, 35);
            GlobalUtils.getInstance().startPage(UserMenu.this, "UserResume", false, false);
        });

        adultProfile.setOnClickListener(view -> {
            MyVibrator.getInstance().vibrate(UserMenu.this, 35);
            GlobalUtils.getInstance().startPage(UserMenu.this, "StatisticPage", false, false);
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        createAndGetDatabase();
        createRecyclerView();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(UserMenu.this)
                .setTitle("Quitter")
                .setMessage("Voulez-vous quitter l'application ?")
                .setPositiveButton("Oui", (dialog, id) -> UserMenu.super.onBackPressed())
                .setNegativeButton("Non", null)
                .show();
    }

    private void createAndGetDatabase() {
        db = CreateDatabase.getInstance(UserMenu.this);
        getAllUser();
    }

    private void initializeFindView() {
        adultProfile = findViewById(R.id.adultProfile);
        settings = findViewById(R.id.settings);
        addUser = findViewById(R.id.addUser);
        recyclerView = findViewById(R.id.recyclerview_users);
        title = findViewById(R.id.title_userMenu);
    }

    private void hideAddUserImage() {
        addUser.setVisibility(View.GONE);
    }

    private void getAllUser() {
        if (!db.appDao().tabUserIsEmpty())
            listUser = db.appDao().getAllUsers();
        else {
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

    private void startAddUserPage() {
        Intent intent = new Intent().setClass(getApplicationContext(), UserEdit.class);
        intent.putExtra("addUser", true);
        startActivity(intent);
        finish();
    }

    private void saveUserNameSahredPref(int position) {
        MySharedPreferences.getInstance().setSharedPreferencesString(UserMenu.this, "userName", listUser.get(position).getUserName());
        MySharedPreferences.getInstance().setSharedPreferencesInt(UserMenu.this, "userId", listUser.get(position).getUserId());
        MySharedPreferences.getInstance().setSharedPreferencesString(UserMenu.this, "userImage", listUser.get(position).getUserImage());
        MySharedPreferences.getInstance().commit();
    }
}