package fr.dut.ptut2021.adapters;

import android.app.Application;

import fr.dut.ptut2021.database.CreateDatabase;

public class ApplicationPtut extends Application {

    //C'est M Banand qui a créé cette classe pour la BDD et j'ose pas trop l'enlever donc pas touché
    @Override
    public void onCreate() {
        super.onCreate();
        CreateDatabase.getInstance(getApplicationContext());
    }



}
