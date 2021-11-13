package fr.dut.ptut2021.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import fr.dut.ptut2021.R;
import fr.dut.ptut2021.adapters.UserAdapter;
import fr.dut.ptut2021.models.User;

public class UserMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_menu);

        List<User> listUser = new ArrayList<>();

        // A remplacer par la BDD et supprimer les drawables au passage (a, b, c, d)
        listUser.add(new User(1, "Leon", R.drawable.a));
        listUser.add(new User(2, "ALban", R.drawable.b));
        listUser.add(new User(3, "William", R.drawable.c));
        listUser.add(new User(4, "Axel", R.drawable.d));

        RecyclerView recyclerView = findViewById(R.id.recyclerview_users);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new UserAdapter(getApplicationContext(), listUser));
    }
}