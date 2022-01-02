package fr.dut.ptut2021.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import fr.dut.ptut2021.R;
import fr.dut.ptut2021.database.CreateDatabase;
import fr.dut.ptut2021.models.User;

public class UserEdit extends AppCompatActivity implements View.OnClickListener {

    private TextView title;
    private String userName;
    private ImageView userAvatar;
    private int userId, imageTmp;
    private CreateDatabase db = null;
    private Button valid, cancel, delete;
    private TextInputEditText textField_userName;
    private boolean isAddUser = false, tabUserIsEmpty = false;

    //TODO (a changer, pour le choix des images)
    private int cpt= 0;
    private final int[] tableauImage = {R.drawable.a, R.drawable.b, R.drawable.c, R.drawable.d};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_edit);

        getDb();
        initializeLayout();
        checkIfAddOrUpdateUser();
        addOnClickListener();
    }

    private void getDb() {
        db = CreateDatabase.getInstance(UserEdit.this);
        tabUserIsEmpty = db.appDao().tabUserIsEmpty();
    }

    private void initializeLayout() {
        userAvatar = findViewById(R.id.userAvatar_editPage);
        title = findViewById(R.id.title_user_editPage);
        textField_userName = findViewById(R.id.textField_userName);
        valid = findViewById(R.id.buttonValider_userEditPage);
        cancel = findViewById(R.id.buttonCancel_userEditPage);
        delete = findViewById(R.id.buttonDelete_userEditPage);
    }

    private void checkIfAddOrUpdateUser() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            isAddUser = bundle.getBoolean("addUser", false);
            if (!isAddUser) {
                getUserAttribute(bundle);
                findCurrentImageUser();
                fillInFields();
                delete.setVisibility(View.VISIBLE);
                title.setText("Modification du profil de " + bundle.getString("userName", ""));
            } else {
                title.setText("Créer votre session");
                userAvatar.setImageResource(R.drawable.a);
            }
        }
    }

    private void getUserAttribute(Bundle bundle){
        userName = bundle.getString("userName", "");
        userId = bundle.getInt("userId", 0);
        imageTmp = bundle.getInt("userImage", R.drawable.a);
    }

    private void findCurrentImageUser() {
        for (int i = 0; i < tableauImage.length; i++) {
            if (tableauImage[i] == imageTmp)
                cpt = i;
        }
    }

    private void fillInFields() {
        userAvatar.setImageResource(imageTmp);
        textField_userName.setText(userName);
    }

    private void addOnClickListener() {
        userAvatar.setOnClickListener(this);
        valid.setOnClickListener(this);
        cancel.setOnClickListener(this);
        delete.setOnClickListener(this);
    }

    private void createUser() {
        if (isAddUser && isCorrect()) {
            if (db.appDao().tabUserIsEmpty()) {
                db.appDao().insertUser(new User(textField_userName.getText().toString(), tableauImage[cpt]));
                startUserMenuPage(false);
            } else {
                db.appDao().insertUser(new User(textField_userName.getText().toString(), tableauImage[cpt]));
                startUserResumePage();
            }
        } else if (!isAddUser && isCorrect()) {
            User user = db.appDao().getUserById(userId);
            user.setUserName(textField_userName.getText().toString());
            user.setUserImage(tableauImage[cpt]);
            db.appDao().updateUser(user);
            startUserResumePage();
        } else if (!isCorrect()) {
            Toast.makeText(getApplicationContext(), "Veuillez saisir un prénom", Toast.LENGTH_SHORT).show();
        }
    }

    private void showMesageDialog(String title, String message, boolean wantToDelete) {
        AlertDialog alertDialog = new AlertDialog.Builder(UserEdit.this).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OUI",
                (dialog, which) -> {
                    dialog.dismiss();
                    if (wantToDelete) {
                        deleteGameData();
                        db.appDao().deleteUserById(userId);
                        if (db.appDao().tabUserIsEmpty()){
                            startUserMenuPage(true);
                        }
                        else
                            startUserResumePage();
                    } else
                        finish();
                });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "NON",
                (dialog, which) -> dialog.dismiss());
        alertDialog.show();
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
                cpt= ++cpt% 4;
                userAvatar.setImageResource(tableauImage[cpt]);
                break;

            case R.id.buttonValider_userEditPage:
                createUser();
                break;

            case R.id.buttonCancel_userEditPage:
                if (tabUserIsEmpty)
                    showMesageDialog("Voulez-vous quitter ?", "Vous n'avez aucune session, êtes vous sur de vouloir quitter ?", false);
                else
                    startUserResumePage();
                break;

            case R.id.buttonDelete_userEditPage:
                showMesageDialog("Suppression d'un utilisateur", "Voulez-vous vraiment supprimer \"" + userName + "\" ?", true);
                break;
            default:
                break;
        }
    }

    private void deleteGameData() {
        db.gameDao().deleteWWHDataByUser(userId);
        //TODO rajouter les fonctions de suppression des jeux à venir
    }

    private void startUserMenuPage(boolean force) {
        Intent intent = new Intent().setClass(getApplicationContext(), UserMenu.class);
        if(force)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        finish();
    }

    private void startUserResumePage() {
        Intent intent = new Intent().setClass(getApplicationContext(), UserResume.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        finish();
    }
}