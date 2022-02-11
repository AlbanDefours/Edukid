package fr.dut.ptut2021.utils;

import static android.content.Context.AUDIO_SERVICE;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.widget.Toast;

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
    private static GlobalUtils instance;

    public static GlobalUtils getInstance() {
        if (instance == null) {
            instance = new GlobalUtils();
        }
        return instance;
    }

    public void startGame(Context context, String gameName, boolean finish, boolean animation) {
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
        if (animation)
            ((Activity) context).overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        if (finish)
            ((Activity) context).finish();
    }

    public void startEditPage(Context context) {
        Intent intent = new Intent().setClass(context, UserEdit.class);
        intent.putExtra("addUser", true);
        context.startActivity(intent);
        ((Activity) context).finish();
    }

    public void startResultPage(Context context, int starsNb) {
        Intent intent = new Intent(context, ResultGamePage.class);
        intent.putExtra("starsNumber", starsNb);
        context.startActivity(intent);
        ((Activity) context).finish();
    }

    public boolean startPage(Context context, String className, boolean wantToFinish, boolean animation) {
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
        if (animation)
            ((Activity) context).overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        if (wantToFinish)
            ((Activity) context).finish();
        return true;
    }

    public void toast(Context context, String msg, boolean wantLong) {
        Toast.makeText(context, msg, Boolean.compare(wantLong, false)).show();
    }

    public void verifyIfSoundIsOn(Context context) {
        AudioManager am = (AudioManager) context.getSystemService(AUDIO_SERVICE);
        int volume_level = am.getStreamVolume(AudioManager.STREAM_MUSIC);
        if (volume_level == 0)
            toast(context, "Veuillez activer le son pour jouer !", true);
    }

    public String cutString(String string, int size) {
        if (string.length() > size)
            string = string.substring(0, size-2) + "..";

        return string;
    }

    public void stopAllSound(Context context) {
        MyTextToSpeech.getInstance().stop(context);
        MyMediaPlayer.getInstance().stop();
    }
}
