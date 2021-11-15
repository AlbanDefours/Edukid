package fr.dut.ptut2021.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import java.util.ArrayList;
import java.util.List;

import fr.dut.ptut2021.R;
import fr.dut.ptut2021.adapters.UserAdapter;
import fr.dut.ptut2021.database.CreateDatabase;
import fr.dut.ptut2021.database.dao.getDb;
import fr.dut.ptut2021.models.User;

public class UserMenu extends AppCompatActivity {

    private CreateDatabase db;

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
            listUser.add(new User("Léon",R.drawable.a));
        }

        new Thread(){
            @Override
            public void run() {
                db = CreateDatabase.getInstance(UserMenu.this);
                db.userDao().createUser(new User("Léon",R.drawable.a));
                User user = db.userDao().getUser(1);
                System.out.println(user.getName());
                Toast.makeText(getApplicationContext(), user.getName(), Toast.LENGTH_LONG).show();
                //Log.e("will",user.getName());*/
                db.close();
            }
        }.start();

        RecyclerView recyclerView = findViewById(R.id.recyclerview_users);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new UserAdapter(getApplicationContext(), listUser));
    }
}