package fr.dut.ptut2021.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.util.List;

import fr.dut.ptut2021.R;
import fr.dut.ptut2021.adapters.RecyclerItemClickListener;
import fr.dut.ptut2021.adapters.user.UserAdapter;
import fr.dut.ptut2021.database.CreateDatabase;
import fr.dut.ptut2021.models.User;

public class UserResume extends AppCompatActivity {

    private CreateDatabase db = null;
    private RecyclerView recyclerView;
    private List<User> listUser = null;
    private ImageView adultProfile, settings, addUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_menu);

        db = CreateDatabase.getInstance(UserResume.this);

        getAllUser();
        initializeFindView();
        hideSettingsAdultImage();
        createRecyclerView();

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        startEditUserPage(position);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                    }
                })
        );

        addUser.setOnClickListener(view -> startAddUserPage());
    }

    private void getAllUser() {
        if (!db.appDao().tabUserIsEmpty()) {
            listUser = db.appDao().getAllUsers();
        }
    }

    private void initializeFindView() {
        adultProfile = findViewById(R.id.adultProfile);
        settings = findViewById(R.id.settings);
        recyclerView = findViewById(R.id.recyclerview_users);
        addUser = findViewById(R.id.addUser);
    }

    private void hideSettingsAdultImage() {
        settings.setVisibility(View.GONE);
        adultProfile.setVisibility(View.GONE);
    }

    private void createRecyclerView() {
        RecyclerView.LayoutManager mLayoutManager;
        if (listUser.size() < 5) {
            mLayoutManager = new GridLayoutManager(this, 1);
        } else {
            mLayoutManager = new GridLayoutManager(this, 2);
        }

        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(new UserAdapter(getApplicationContext(), listUser, true));
    }

    private void startAddUserPage() {
        Intent intent = new Intent().setClass(getApplicationContext(), UserEdit.class);
        intent.putExtra("addUser", true);
        startActivity(intent);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        finish();
    }

    private void startEditUserPage(int position) {
        Intent intent = new Intent().setClass(getApplicationContext(), UserEdit.class);
        intent.putExtra("userName", listUser.get(position).getUserName());
        intent.putExtra("userId", listUser.get(position).getUserId());
        intent.putExtra("userImage", listUser.get(position).getUserImage());
        startActivity(intent);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        finish();
    }
}