package fr.dut.ptut2021.models;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

public class ThemeWithGame {
    @Embedded
    public Theme theme;
    @Relation(
            parentColumn = "themeId",
            entityColumn = "gameId",
            associateBy = @Junction(ThemeGameCrossRef.class)
    )
    public List<Game> gameList;
}