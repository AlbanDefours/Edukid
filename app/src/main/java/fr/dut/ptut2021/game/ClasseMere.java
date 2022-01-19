package fr.dut.ptut2021.game;

import android.content.Context;
import android.content.Intent;

import fr.dut.ptut2021.activities.SubGameMenu;

public class ClasseMere {

    private Context context;

    public ClasseMere(Context context) {
        this.context = context;
    }

    public void findGame(String gameName) {
        switch (gameName) {
            case "Ecoute":
                context.startActivity(new Intent().setClass(context, PlayWithSound.class));
                break;
            case "Dessine":
                context.startActivity(new Intent().setClass(context, DrawOnIt.class));
                break;
            case "Memory":
                context.startActivity(new Intent().setClass(context, SubGameMenu.class));
                break;
            case "SubMemory":
                context.startActivity(new Intent().setClass(context, Memory.class));
                break;
            case "Mot à trou":
                context.startActivity(new Intent().setClass(context, WordWithHole.class));
                break;
        }
    }
}
