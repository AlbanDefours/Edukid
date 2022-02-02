package fr.dut.ptut2021.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.textfield.TextInputEditText;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import fr.dut.ptut2021.R;
import fr.dut.ptut2021.database.CreateDatabase;
import fr.dut.ptut2021.models.database.app.User;
import fr.dut.ptut2021.utils.GlobalUtils;
import fr.dut.ptut2021.utils.MyVibrator;

public class UserEdit extends AppCompatActivity implements View.OnClickListener {

    private TextView title;
    private ImageView userAvatar;
    private boolean shortcut = false;
    private CreateDatabase db = null;
    private Button valid, cancel, delete, inport;
    private TextInputEditText textField_userName;
    private String userName, imageLocation = null, imageTmp;
    private int userId, userImageType = 0;
    private boolean isAddUser = false, tabUserIsEmpty = false;
    private static final int CAMERA_REQUEST = 20, MY_CAMERA_PERMISSION_CODE = 200;
    private static final int GALLERY_REQUEST = 30, MY_STORAGE_PERMISSION_CODE = 300;

    private int cpt = 0;
    private List<String> tabImage = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_edit);

        addingAvatar();
        Log.e("WILLY", "" + tabImage.get(0).toString());
        Log.e("WILLY", "" + String.valueOf(tabImage.get(0)));
        imageLocation = String.valueOf(tabImage.get(0));

        getDb();
        initializeLayout();
        checkIfAddOrUpdateUser();
        addOnClickListener();
    }

    private void addingAvatar() {
        tabImage.add(String.valueOf(R.drawable.user_image_a));
        tabImage.add(String.valueOf(R.drawable.user_image_b));
        tabImage.add(String.valueOf(R.drawable.user_image_c));
        tabImage.add(String.valueOf(R.drawable.user_image_d));
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
        inport = findViewById(R.id.buttonImport_userEditPage);
    }

    private void checkIfAddOrUpdateUser() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            isAddUser = bundle.getBoolean("addUser", false);
            if (!isAddUser) {
                getUserAttribute(bundle);
                setImageAvatar();
                fillInFields();
                delete.setVisibility(View.VISIBLE);
                title.setText("Modification du profil de " + bundle.getString("userName", ""));
            } else if (db.appDao().getAllUsers().isEmpty()) {
                title.setText("Créer votre première session");
                userAvatar.setImageResource(R.drawable.user_image_a);
            } else {
                title.setText("Créer votre session");
                userAvatar.setImageResource(R.drawable.user_image_a);
            }
        }
    }

    private void getUserAttribute(Bundle bundle) {
        userName = bundle.getString("userName", "");
        userId = bundle.getInt("userId", 0);
        shortcut = bundle.getBoolean("shortcut", false);
        imageTmp = bundle.getString("userImage", String.valueOf(R.drawable.user_image_a));
        imageLocation = imageTmp;
        userImageType = bundle.getInt("userImageType", -1);
    }

    private void setImageAvatar() {
        if (userImageType == 0) {
            userAvatar.setImageResource(Integer.parseInt(tabImage.get(cpt)));
        } else {
            try {
                File f = new File(tabImage.get(cpt));
                Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
                userAvatar.setImageBitmap(b);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private void fillInFields() {
        textField_userName.setText(userName);
    }

    private void addOnClickListener() {
        userAvatar.setOnClickListener(this);
        valid.setOnClickListener(this);
        cancel.setOnClickListener(this);
        delete.setOnClickListener(this);
        inport.setOnClickListener(this);
    }

    private void createUser() {
        if (imageLocation.indexOf(".jpg") > 0) {
            userImageType = 30;
        } else {
            userImageType = 0;
        }
        if (isAddUser && isDataCorrect()) {
            db.appDao().insertUser(new User(textField_userName.getText().toString(), imageLocation, userImageType));
            startUserMenu();
        } else if (!isAddUser && isDataCorrect()) {
            User user = db.appDao().getUserById(userId);
            user.setUserName(textField_userName.getText().toString());
            user.setUserImage(imageLocation);
            user.setUserImageType(userImageType);
            db.appDao().updateUser(user);
            startUserMenu();
        } else if (!isDataCorrect())
            Toast.makeText(getApplicationContext(), "Veuillez saisir un prénom", Toast.LENGTH_SHORT).show();
    }

    public void startUserMenu() {
        if (tabUserIsEmpty)
            GlobalUtils.startPage(UserEdit.this, "UserMenu", true, false);
        else
            finish();
    }

    private void showMesageDialog(String title, String message, boolean wantToDelete) {
        AlertDialog alertDialog = new AlertDialog.Builder(UserEdit.this).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OUI",
                (dialog, which) -> {
                    dialog.dismiss();
                    if (wantToDelete) {
                        File file = new File(db.appDao().getUserById(userId).getUserImage());
                        file.delete(); //TODO (pas delete si on modifie l'image par une de l'ap)
                        deleteGameData();
                        db.appDao().deleteUserById(userId);
                        finish();
                    } else
                        finish();
                });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "NON",
                (dialog, which) -> dialog.dismiss());
        alertDialog.show();
    }

    private boolean isDataCorrect() {
        return !textField_userName.getText().toString().isEmpty() && imageLocation != null;
    }

    private void getPhotoFromGallery() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            String[] mimeTypes = {"image/jpeg", "image/png", "image/jpg"};
            intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
            startActivityForResult(intent, GALLERY_REQUEST);
        } else
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_STORAGE_PERMISSION_CODE);
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

    private String saveToInternalStorage(Bitmap bitmapImage) {
        String name = db.appDao().getUserImageMaxInt();
        int id = 0;
        if (name != null && name.indexOf(".jpg") - 1 > 0)
            id = Integer.parseInt(name.substring(name.indexOf(".jpg") - 1, name.indexOf(".jpg"))) + 1;
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        File mypath = new File(directory, "userimage" + id + ".jpg");

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 90, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return mypath.getAbsolutePath();
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
        if (requestCode == MY_STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                getPhotoFromGallery();
            else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                Toast.makeText(getApplicationContext(), "Permission non accordée !", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK)
            switch (requestCode) {
                case GALLERY_REQUEST:
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getContentResolver().query(data.getData(), filePathColumn, null, null, null);
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    imageLocation = cursor.getString(columnIndex);
                    cursor.close();
                    Bitmap bitmap = BitmapFactory.decodeFile(imageLocation);
                    if (bitmap.getWidth() < bitmap.getHeight())
                        bitmap = Bitmap.createBitmap(bitmap, 0, (bitmap.getHeight() - bitmap.getWidth()) / 2, bitmap.getWidth(), bitmap.getWidth());
                    else if (bitmap.getWidth() > bitmap.getHeight())
                        bitmap = Bitmap.createBitmap(bitmap, (bitmap.getWidth() - bitmap.getHeight()) / 2, 0, bitmap.getHeight(), bitmap.getHeight());
                    if (bitmap.getWidth() > 150)
                        bitmap = Bitmap.createScaledBitmap(bitmap, 150, 150, false);
                    imageLocation = saveToInternalStorage(bitmap);
                    userImageType = GALLERY_REQUEST;
                    userAvatar.setImageBitmap(bitmap);
                    break;

                case CAMERA_REQUEST:
                    Bitmap photo = (Bitmap) data.getExtras().get("data");
                    if (photo.getWidth() < photo.getHeight())
                        photo = Bitmap.createBitmap(photo, 0, (photo.getHeight() - photo.getWidth()) / 2, photo.getWidth(), photo.getWidth());
                    else if (photo.getWidth() > photo.getHeight())
                        photo = Bitmap.createBitmap(photo, (photo.getWidth() - photo.getHeight()) / 2, 0, photo.getHeight(), photo.getHeight());
                    imageLocation = saveToInternalStorage(photo);
                    userImageType = CAMERA_REQUEST;
                    userAvatar.setImageBitmap(photo);
                    break;
            }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        MyVibrator.vibrate(UserEdit.this, 35);
        switch (v.getId()) {
            case R.id.userAvatar_editPage:
                cpt = ++cpt % 4;
                imageLocation = String.valueOf(tabImage.get(cpt));54
                userAvatar.setImageDrawable(tabImage.get(cpt));
                break;

            case R.id.buttonValider_userEditPage:
                createUser();
                break;

            case R.id.buttonCancel_userEditPage:
                if (tabUserIsEmpty)
                    showMesageDialog("Voulez-vous quitter ?", "Vous n'avez aucune session, êtes vous sur de vouloir quitter ?", false);
                else if (shortcut)
                    finish();
                else
                    GlobalUtils.startPage(UserEdit.this, "UserResume", true, true);
                break;

            case R.id.buttonDelete_userEditPage:
                showMesageDialog("Suppression d'un utilisateur", "Voulez-vous vraiment supprimer \"" + userName + "\" ?", true);
                break;

            case R.id.buttonImport_userEditPage:
                PopupMenu popupMenu = new PopupMenu(UserEdit.this, findViewById(R.id.userAvatar_editPage));
                popupMenu.inflate(R.menu.menu_popup);
                popupMenu.setOnMenuItemClickListener(item -> {
                    switch (item.getItemId()) {
                        case R.id.gallery_tel:
                            getPhotoFromGallery();
                            break;
                        case R.id.camera:
                            takePhoto();
                            break;
                    }
                    return true;
                });
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    popupMenu.setForceShowIcon(true);
                } else {
                    try {
                        Field[] fields = popupMenu.getClass().getDeclaredFields();
                        for (Field field : fields) {
                            if ("mPopup".equals(field.getName())) {
                                field.setAccessible(true);
                                Object menuPopupHelper = field.get(popupMenu);
                                Class<?> classPopupHelper = Class.forName(menuPopupHelper
                                        .getClass().getName());
                                Method setForceIcons = classPopupHelper.getMethod(
                                        "setForceShowIcon", boolean.class);
                                setForceIcons.invoke(menuPopupHelper, true);
                                break;
                            }
                        }
                    } catch (Throwable e) {
                        e.printStackTrace();
                    }
                }
                popupMenu.show();
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        GlobalUtils.startPage(UserEdit.this, "UserResume", true, true);
    }

    @Override
    public void finish() {
        super.finish();
        if (shortcut)
            overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }
}