package fr.dut.ptut2021.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

        //TODO (a supprimer et importer BDD User)
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle != null){
            listUser.add(new User(bundle.getString("envoieNomPersonne", ""), R.drawable.a));
        } else {
            listUser.add(new User("NAME", R.drawable.a));
        }

        RecyclerView recyclerView = findViewById(R.id.recyclerview_users);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new UserAdapter(getApplicationContext(), listUser));
    }
}