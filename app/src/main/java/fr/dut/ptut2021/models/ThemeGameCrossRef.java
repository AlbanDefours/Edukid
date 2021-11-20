package fr.dut.ptut2021.models;

import androidx.room.Entity;

@Entity(primaryKeys = {"gameId", "themeId"})
public class ThemeGameCrossRef {
    public int gameId;
    public int themeId;
}