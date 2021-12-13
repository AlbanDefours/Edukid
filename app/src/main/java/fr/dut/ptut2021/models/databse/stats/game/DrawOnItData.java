package fr.dut.ptut2021.models.databse.stats.game;

import androidx.annotation.NonNull;
import androidx.room.Entity;

@Entity(primaryKeys = {"userId", "draw"})
public class DrawOnItData {

    private int userId;
    @NonNull
    private String draw;
    private long touchTime;
    private boolean lastUsed;

    public DrawOnItData(int userId, @NonNull String draw) {
        this.userId = userId;
        this.draw = draw;
        this.touchTime = 0;
        this.lastUsed = false;
    }

    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }

    @NonNull
    public String getDraw() {
        return draw;
    }
    public void setDraw(@NonNull String draw) {
        this.draw = draw;
    }

    public long getTouchTime() {
        return touchTime;
    }
    public void setTouchTime(long touchTime) {
        this.touchTime = touchTime;
    }

    public boolean isLastUsed() {
        return lastUsed;
    }
    public void setLastUsed(boolean lastUsed) {
        this.lastUsed = lastUsed;
    }
}
