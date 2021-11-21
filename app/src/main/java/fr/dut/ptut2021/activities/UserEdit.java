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
    private int i = 0;
    private final int[] tableauImage = {R.drawable.a, R.drawable.b, R.drawable.c, R.drawable.d};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_edit);

        db = CreateDatabase.getInstance(UserEdit.this);
        tabUserIsEmpty = db.appDao().tabUserIsEmpty();

        initializeLayout();
        checkIfAddOrUpdateUser();
        addOnClickListener();
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
                userName = bundle.getString("userName", "");
                userId = bundle.getInt("userId", 0);
                imageTmp = bundle.getInt("userImage", R.drawable.a);
                i = findImageNumber();
                fillInFields();
                delete.setVisibility(View.VISIBLE);
                title.setText("Modification du profil de " + bundle.getString("userName", ""));
            } else {
                title.setText("Créer votre session");
                userAvatar.setImageResource(R.drawable.a);
            }
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

    private int findImageNumber() {
        for (int i = 0; i < tableauImage.length; i++) {
            if (tableauImage[i] == imageTmp) return i;
        }
        return 0;
    }

    private void createUser() {
        if (isAddUser && isCorrect()) {
            if (db.appDao().tabUserIsEmpty()) {
                db.appDao().insertUser(new User(textField_userName.getText().toString(), tableauImage[i]));
                startUserMenuPage();
            } else {
                db.appDao().insertUser(new User(textField_userName.getText().toString(), tableauImage[i]));
                startUserResumePage();
            }
        } else if (!isAddUser && isCorrect()) {
            User user = db.appDao().getUserById(userId);
            user.setUserName(textField_userName.getText().toString());
            user.setUserImage(tableauImage[i]);
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
                        db.appDao().deleteUserById(userId);
                        //TODO crash ici
                        if (db.appDao().tabUserIsEmpty())
                            startUserMenuPage();
                        else
                            startUserResumePage();
                        //A verifier si y a plus d'user
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
                i = ++i % 4;
                userAvatar.setImageResource(tableauImage[i]);
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

    private void startUserMenuPage() {
        Intent intent = new Intent().setClass(getApplicationContext(), UserMenu.class);
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