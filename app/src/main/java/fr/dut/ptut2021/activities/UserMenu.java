package fr.dut.ptut2021.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
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
    private List<User> listUser = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_menu);

        db = CreateDatabase.getInstance(UserMenu.this);

        //Peut etre plus opti de faire un seul getAllUser() en testant listUser empty ?
        if (!db.userDao().dbIsEmpty()) {
            listUser = db.userDao().getAllUsers();
        } else {
            startAddUserPage();
        }

        ImageView addUser = findViewById(R.id.addUser);
        RecyclerView recyclerView = findViewById(R.id.recyclerview_users);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new UserAdapter(getApplicationContext(), listUser));

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