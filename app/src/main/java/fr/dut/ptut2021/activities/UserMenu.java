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



    }
}