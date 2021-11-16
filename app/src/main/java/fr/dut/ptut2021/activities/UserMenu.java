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
import fr.dut.ptut2021.adapters.UserAdapter;
import fr.dut.ptut2021.database.CreateDatabase;
import fr.dut.ptut2021.models.User;

public class UserMenu extends AppCompatActivity {

    private CreateDatabase db = CreateDatabase.getInstance(UserMenu.this);
    private ImageView addUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_menu);

        List<User> listUser = db.userDao().getAllUsers();

        RecyclerView recyclerView = findViewById(R.id.recyclerview_users);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new UserAdapter(getApplicationContext(), listUser));

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(), recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        Intent intent = new Intent().setClass(getApplicationContext(), ThemeMenu.class);
                        intent.putExtra("idUser", listUser.get(position).getId());
                        startActivity(intent);
                    }

                    @Override public void onLongItemClick(View view, int position) {}
                })
        );

        addUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent().setClass(getApplicationContext(), UserEdit.class);
                intent.putExtra("addUser", true);
                startActivity(intent);
            }
        });
    }
}