package fr.dut.ptut2021.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.textfield.TextInputEditText;

import fr.dut.ptut2021.R;
import fr.dut.ptut2021.database.CreateDatabase;
import fr.dut.ptut2021.models.databse.User;

public class UserEdit extends AppCompatActivity implements View.OnClickListener {

    private TextView title;
    private String userName;
    private ImageView userAvatar;
    private int userId, imageTmp;
    private CreateDatabase db = null;
    private Button valid, cancel, delete;
    private TextInputEditText textField_userName;
    private boolean isAddUser = false, tabUserIsEmpty = false;
    private static final int CAMERA_REQUEST = 1888, MY_CAMERA_PERMISSION_CODE = 100;

    private final static int REQUEST_CODE_ASK_PERMISSIONS = 1;
    private static final String[] REQUIRED_SDK_PERMISSIONS = new String[]{
            Manifest.permission.CAMERA};

    //TODO (a changer, pour le choix des images)
    private int cpt = 0;
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

    private void getUserAttribute(Bundle bundle) {
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
                        if (db.appDao().tabUserIsEmpty()) {
                            startUserMenuPage(true);
                        } else
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
                PopupMenu popupMenu = new PopupMenu(UserEdit.this, findViewById(R.id.userAvatar_editPage));
                popupMenu.getMenuInflater().inflate(R.menu.menu_popup, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.gallery_app:
                                cpt = ++cpt % 4;
                                userAvatar.setImageResource(tableauImage[cpt]);
                                break;
                            case R.id.gallery_tel:
                                break;
                            case R.id.camera:
                                takePhoto();
                                break;
                        }
                        return true;
                    }
                });
                popupMenu.show();
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

    private void takePhoto() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, CAMERA_REQUEST);
        } else
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
    }

    private void deleteGameData() {
        db.gameDao().deleteWWHDataByUser(userId);
        //TODO rajouter les fonctions de suppression des jeux à venir
    }

    private void startUserMenuPage(boolean force) {
        Intent intent = new Intent().setClass(getApplicationContext(), UserMenu.class);
        if (force)
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

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                takePhoto();
            else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    new AlertDialog.Builder(UserEdit.this)
                            .setMessage("Vous devez accepter cette permission pour continuer.")
                            .setPositiveButton("OK", (dialog, which) -> {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
                            })
                            .setNegativeButton("Annuler", null)
                            .create()
                            .show();
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            userAvatar.setImageBitmap(photo);
        }
    }
}