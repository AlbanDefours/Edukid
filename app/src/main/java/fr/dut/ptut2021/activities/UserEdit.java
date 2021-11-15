package fr.dut.ptut2021.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

import fr.dut.ptut2021.R;
import fr.dut.ptut2021.database.CreateDatabase;
import fr.dut.ptut2021.models.User;

public class UserEdit extends AppCompatActivity {

    private boolean isFirstTime = false;
    private ImageView userAvatar;
    private TextView title;
    private TextInputEditText textField_userName;
    private Button valider;
    private CreateDatabase db;

    //TODO (a changer, pour le choix des images)
    private int i = 0;
    private int[] tableauImage =  {R.drawable.a, R.drawable.b, R.drawable.c, R.drawable.d};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_edit);

        new Thread(){
            @Override
            public void run() {
                db = CreateDatabase.getInstance(UserEdit.this);
            }
        }.start();

        userAvatar = findViewById(R.id.userAvatar_editPage);
        title = findViewById(R.id.title_user_editPage);
        textField_userName = findViewById(R.id.textField_userName);
        valider = findViewById(R.id.button_userEditPage);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if(bundle != null){
            isFirstTime = bundle.getBoolean("isFirstTime", false);
            if(!isFirstTime){
                textField_userName.setText(bundle.getString("userName", ""));
                userAvatar.setImageResource(bundle.getInt("userImage", R.drawable.a));
                title.setText("Modification du profil de " + bundle.getString("userName", ""));
            } else {
                title.setText("Creer votre première session");
                userAvatar.setImageResource(R.drawable.a);
            }
        }

        //TODO (add Image options like : take photo, choose photo ...)
        userAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userAvatar.setImageResource(tableauImage[++i%4]);
            }
        });

        valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isCorrect()){
                    db.userDao().createUser(new User(textField_userName.getText().toString(), tableauImage[i]));
                    db.close();
                    Intent intent = new Intent().setClass(getApplicationContext(), UserMenu.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Veuillez saisir un prénom", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //TODO (verify image is correct)
    private boolean isCorrect(){
        return !isUserNameEmpty();
    }

    private boolean isUserNameEmpty(){
        return textField_userName.getText().toString().isEmpty();
    }
}