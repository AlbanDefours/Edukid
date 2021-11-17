package fr.dut.ptut2021.activities;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import fr.dut.ptut2021.R;
import fr.dut.ptut2021.adapters.RecyclerItemClickListener;
import fr.dut.ptut2021.adapters.user.UserAdapter;
import fr.dut.ptut2021.database.CreateDatabase;
import fr.dut.ptut2021.models.User;

public class UserMenu extends AppCompatActivity {

    private CreateDatabase db = null;
    private RecyclerView recyclerView;
    private List<User> listUser = null;
    private ImageView addUser, adultProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_menu);

        db = CreateDatabase.getInstance(UserMenu.this);

        getAllUser();
        initializeFindView();
        createRecyclerView();

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

        addUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAddUserPage();
            }
        });

        adultProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO coder la session adulte
            }
        });
    }

    private void initializeFindView(){
        adultProfile = findViewById(R.id.adultProfile);
        addUser = findViewById(R.id.addUser);
        recyclerView = findViewById(R.id.recyclerview_users);
    }

    private void getAllUser() {
        if (!db.userDao().tabUserIsEmpty()) {
            listUser = db.userDao().getAllUsers();
        } else {
            startAddUserPage();
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
        recyclerView.setAdapter(new UserAdapter(getApplicationContext(), listUser));
    }

    private void startAddUserPage() {
        Intent intent = new Intent().setClass(getApplicationContext(), UserEdit.class);
        intent.putExtra("addUser", true);
        startActivity(intent);
        finish();
    }

    private void startThemeMenu(int position) {
        Intent intent = new Intent().setClass(getApplicationContext(), ThemeMenu.class);
        intent.putExtra("idUser", listUser.get(position).getId());
        startActivity(intent);
    }
}