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
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
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
    private String userName;
    private int userId, cpt = 0;
    private ImageView userAvatar;
    private CreateDatabase db = null;
    private Button valid, cancel, delete, inport;
    private TextInputEditText textField_userName;
    private boolean isAddUser = false, tabUserIsEmpty = false, shortcut = false;
    private static final int CAMERA_REQUEST = 20, MY_CAMERA_PERMISSION_CODE = 200;
    private static final int GALLERY_REQUEST = 30, MY_STORAGE_PERMISSION_CODE = 300;
    private List<String> tabImage = new ArrayList<>();
    private List<Integer> tabImageType = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_edit);

        addingAvatar();
        getDb();
        initializeLayout();
        checkIfAddOrUpdateUser();
        addOnClickListener();
    }

    private void addingAvatar() {
        for (int i = 1; i <= 8; i++)
            tabImageType.add(0);

        tabImage.add(String.valueOf(R.drawable.profil1));
        tabImage.add(String.valueOf(R.drawable.profil2));
        tabImage.add(String.valueOf(R.drawable.profil3));
        tabImage.add(String.valueOf(R.drawable.profil4));
        tabImage.add(String.valueOf(R.drawable.profil5));
        tabImage.add(String.valueOf(R.drawable.profil6));
        tabImage.add(String.valueOf(R.drawable.profil7));
        tabImage.add(String.valueOf(R.drawable.profil8));
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
                userAvatar.setImageResource(R.drawable.profil1);
            } else {
                title.setText("Créer votre session");
                userAvatar.setImageResource(R.drawable.profil1);
            }
        }
    }

    private void findImageActual(String imageName) {
        for (int i = 0; i < tabImage.size(); i++) {
            if (tabImage.get(i).equals(imageName))
                cpt = i;
        }
    }

    private void getUserAttribute(Bundle bundle) {
        userId = bundle.getInt("userId", 0);
        userName = bundle.getString("userName", "");
        shortcut = bundle.getBoolean("shortcut", false);
        String imageName = bundle.getString("userImage", null);
        addImageTabIfNotExist(imageName, bundle.getInt("userImageType", 30));
        findImageActual(imageName);
    }

    public void addImageTabIfNotExist(String userImage, int userImageType) {
        boolean exist = false;
        for (String s : tabImage) {
            if (s.equals(userImage))
                exist = true;
        }
        if (!exist) {
            tabImage.add(userImage);
            tabImageType.add(userImageType);
        }
    }

    private void setImageAvatar() {
        if (tabImageType.get(cpt) == 0) {
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
        if (isAddUser && isDataCorrect()) {
            db.appDao().insertUser(new User(textField_userName.getText().toString(), tabImage.get(cpt), tabImageType.get(cpt)));
            startUserMenu();
        } else if (!isAddUser && isDataCorrect()) {
            User user = db.appDao().getUserById(userId);
            user.setUserName(textField_userName.getText().toString());
            user.setUserImage(tabImage.get(cpt));
            user.setUserImageType(tabImageType.get(cpt));
            db.appDao().updateUser(user);
            startUserMenu();
        } else if (!isDataCorrect())
            Toast.makeText(getApplicationContext(), "Veuillez saisir un prénom", Toast.LENGTH_SHORT).show();
    }

    public void startUserMenu() {
        if (tabUserIsEmpty)
            GlobalUtils.getInstance().startPage(UserEdit.this, "UserMenu", true, false);
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
                        file.delete();
                        deleteUserData();
                        finish();
                    } else
                        finish();
                });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "NON",
                (dialog, which) -> dialog.dismiss());
        alertDialog.show();
    }

    private boolean isDataCorrect() {
        return !textField_userName.getText().toString().isEmpty();
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

    private void deleteUserData() {
        db.appDao().deleteUserById(userId);
        db.gameLogDao().deleteGameLogDataByUser(userId);
        db.gameDao().deleteWWHDataByUser(userId);
        db.gameDao().deletePWSDataByUser(userId);
        db.gameDao().deleteDOIDataByUser(userId);
        db.gameDao().deleteMemoryDataByUser(userId);
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
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData());
                        if (bitmap.getWidth() < bitmap.getHeight())
                            bitmap = Bitmap.createBitmap(bitmap, 0, (bitmap.getHeight() - bitmap.getWidth()) / 2, bitmap.getWidth(), bitmap.getWidth());
                        else if (bitmap.getWidth() > bitmap.getHeight())
                            bitmap = Bitmap.createBitmap(bitmap, (bitmap.getWidth() - bitmap.getHeight()) / 2, 0, bitmap.getHeight(), bitmap.getHeight());
                        if (bitmap.getWidth() > 250)
                            bitmap = Bitmap.createScaledBitmap(bitmap, 250, 250, false);

                        tabImageType.add(GALLERY_REQUEST);
                        tabImage.add(saveToInternalStorage(bitmap));
                        cpt = tabImage.size() - 1;
                        setImageAvatar();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;

                case CAMERA_REQUEST:
                    Bitmap photo = (Bitmap) data.getExtras().get("data");
                    if (photo.getWidth() < photo.getHeight())
                        photo = Bitmap.createBitmap(photo, 0, (photo.getHeight() - photo.getWidth()) / 2, photo.getWidth(), photo.getWidth());
                    else if (photo.getWidth() > photo.getHeight())
                        photo = Bitmap.createBitmap(photo, (photo.getWidth() - photo.getHeight()) / 2, 0, photo.getHeight(), photo.getHeight());
                    if (photo.getWidth() > 250)
                        photo = Bitmap.createScaledBitmap(photo, 250, 250, false);
                    tabImageType.add(CAMERA_REQUEST);
                    tabImage.add(saveToInternalStorage(photo));
                    cpt = tabImage.size() - 1;
                    setImageAvatar();
                    break;
            }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        MyVibrator.getInstance().vibrate(UserEdit.this, 35);
        switch (v.getId()) {
            case R.id.userAvatar_editPage:
                cpt = ++cpt % tabImage.size();
                setImageAvatar();
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
                    GlobalUtils.getInstance().startPage(UserEdit.this, "UserResume", true, true);
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
        GlobalUtils.getInstance().startPage(UserEdit.this, "UserResume", true, true);
    }

    @Override
    public void finish() {
        super.finish();
        if (shortcut)
            overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }
}
