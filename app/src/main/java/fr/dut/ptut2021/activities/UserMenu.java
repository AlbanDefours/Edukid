package fr.dut.ptut2021.activities;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import fr.dut.ptut2021.R;
import fr.dut.ptut2021.adapters.RecyclerItemClickListener;
import fr.dut.ptut2021.adapters.user.UserAdapter;
import fr.dut.ptut2021.database.CreateDatabase;
import fr.dut.ptut2021.models.User;

public class UserMenu extends AppCompatActivity {

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
        hideAddUserImage();

        recyclerView.addOnItemTouchListener(
            new RecyclerItemClickListener(getApplicationContext(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    startThemeMenu(position);
                }

                @Override
                public void onLongItemClick(View view, int position) {
                }
            })
        );

        settings.setOnClickListener(v -> startSettingPage());

        adultProfile.setOnClickListener(v -> startStatisticPage());
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
        Intent intent = new Intent().setClass(getApplicationContext(), UserResume.class);
        startActivity(intent);
    }

    private void startStatisticPage() {
        Intent intent = new Intent().setClass(getApplicationContext(), StatisticPage.class);
        startActivity(intent);
    }

    private void startAddUserPage() {
        Intent intent = new Intent().setClass(getApplicationContext(), UserEdit.class);
        intent.putExtra("addUser", true);
        startActivity(intent);
        finish();
    }

    private void startThemeMenu(int position) {
        Intent intent = new Intent().setClass(getApplicationContext(), ThemeMenu.class);
        intent.putExtra("userName", listUser.get(position).getUserName());
        startActivity(intent);
    }
}