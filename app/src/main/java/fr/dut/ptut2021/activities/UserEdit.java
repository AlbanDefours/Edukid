package fr.dut.ptut2021.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import fr.dut.ptut2021.R;
import fr.dut.ptut2021.database.CreateDatabase;
import fr.dut.ptut2021.models.User;

public class UserEdit extends AppCompatActivity implements View.OnClickListener {

    private boolean addUser = false;
    private TextView title;
    private Button valid;
    private ImageView userAvatar;
    private TextInputEditText textField_userName;
    private CreateDatabase db = null;

    //TODO (a changer, pour le choix des images)
    private int i = 0;
    private final int[] tableauImage = {R.drawable.a, R.drawable.b, R.drawable.c, R.drawable.d};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_edit);

        db = CreateDatabase.getInstance(UserEdit.this);

        initializeLayout();

        checkIfAddOrUpdateUser();

        userAvatar.setOnClickListener(this);
        valid.setOnClickListener(this);
    }

    private void checkIfAddOrUpdateUser(){
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            addUser = bundle.getBoolean("addUser", false);
            if (!addUser) {
                textField_userName.setText(bundle.getString("userName", ""));
                userAvatar.setImageResource(bundle.getInt("userImage", R.drawable.a));
                title.setText("Modification du profil de " + bundle.getString("userName", ""));
            } else {
                title.setText("Créer votre session");
                userAvatar.setImageResource(R.drawable.a);
            }
        }
    }

    private void initializeLayout() {
        userAvatar = findViewById(R.id.userAvatar_editPage);
        title = findViewById(R.id.title_user_editPage);
        textField_userName = findViewById(R.id.textField_userName);
        valid = findViewById(R.id.button_userEditPage);
    }

    private void startUserMenuPage(){
        Intent intent = new Intent().setClass(getApplicationContext(), UserMenu.class);
        startActivity(intent);
        finish();
    }

    private void createUser(){
        if (addUser && isCorrect()) {
            db.userDao().createUser(new User(textField_userName.getText().toString(), tableauImage[i]));
            startUserMenuPage();
        } else if (!addUser && isCorrect()) {
            //TODO DB UPDATE
            startUserMenuPage();
        } else if (!isCorrect()) {
            Toast.makeText(getApplicationContext(), "Veuillez saisir un prénom", Toast.LENGTH_SHORT).show();
        }
    }

    //TODO (verify image is correct)
    private boolean isCorrect() {
        return !isUserNameEmpty();
    }

    private boolean isUserNameEmpty() {
        return textField_userName.getText().toString().isEmpty();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.userAvatar_editPage:
                userAvatar.setImageResource(tableauImage[++i % 4]);
                break;

            case R.id.button_userEditPage:
                createUser();
                break;
            default:
                break;
        }
    }
}