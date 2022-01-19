package fr.dut.ptut2021.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import fr.dut.ptut2021.R;
import fr.dut.ptut2021.activities.GameMenu;
import fr.dut.ptut2021.activities.LoadingPage;
import fr.dut.ptut2021.activities.ResultGamePage;
import fr.dut.ptut2021.activities.StatisticPage;
import fr.dut.ptut2021.activities.SubGameMenu;
import fr.dut.ptut2021.activities.ThemeMenu;
import fr.dut.ptut2021.activities.UserEdit;
import fr.dut.ptut2021.activities.UserMenu;
import fr.dut.ptut2021.activities.UserResume;
import fr.dut.ptut2021.game.DrawOnIt;
import fr.dut.ptut2021.game.Memory;
import fr.dut.ptut2021.game.PlayWithSound;
import fr.dut.ptut2021.game.WordWithHole;

public class GlobalUtils {

    public static void startGame(Context context, String gameName) {
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

    public static boolean startPage(Context context, String className, boolean wantToFinish, boolean animation) {
        switch (className) {
            case "GameMenu":
                context.startActivity(new Intent().setClass(context, GameMenu.class));
                break;
            case "LoadingPage":
                context.startActivity(new Intent().setClass(context, LoadingPage.class));
                break;
            case "ResultGamePage":
                context.startActivity(new Intent().setClass(context, ResultGamePage.class));
                break;
            case "StatisticPage":
                context.startActivity(new Intent().setClass(context, StatisticPage.class));
                break;
            case "SubGameMenu":
                context.startActivity(new Intent().setClass(context, SubGameMenu.class));
                break;
            case "ThemeMenu":
                context.startActivity(new Intent().setClass(context, ThemeMenu.class));
                break;
            case "UserEdit":
                context.startActivity(new Intent().setClass(context, UserEdit.class));
                break;
            case "UserMenu":
                context.startActivity(new Intent().setClass(context, UserMenu.class));
                break;
            case "UserResume":
                context.startActivity(new Intent().setClass(context, UserResume.class));
                break;

            default:
                return false;
        }
        if(animation)
            ((Activity) context).overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        if (wantToFinish)
            ((Activity) context).finish();
        return true;
    }
}
