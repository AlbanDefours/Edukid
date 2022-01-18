package fr.dut.ptut2021.database.dao;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import fr.dut.ptut2021.models.database.game.DrawOnItData;
import fr.dut.ptut2021.models.database.game.MemoryCard;
import fr.dut.ptut2021.models.database.game.MemoryData;
import fr.dut.ptut2021.models.database.game.MemoryDataCardCrossRef;
import fr.dut.ptut2021.models.database.game.PlayWithSoundData;
import fr.dut.ptut2021.models.database.game.WordWithHoleData;

@Dao
public interface GameDao {
    //TODO supprimer toutes les données quand on supprime un utilisateur

//WordWithHoleData
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertWWHData(WordWithHoleData wordWithHoleStats);

    @Query("SELECT MAX(dataId) FROM WordWithHoleData")
    int getWWHMaxId();

    @Update
    void updateWWHData(WordWithHoleData wordWithHoleStats);

    @Query("UPDATE WordWithHoleData SET lastUsed = 0 WHERE userId = :userId AND lastUsed = 1")
    void updateAllWWHDataLastUsed(int userId);

    @Query("SELECT * FROM WordWithHoleData WHERE userId = :userId AND result = :result")
    WordWithHoleData getWWHDataByData(int userId, String result);

    @Query("SELECT * FROM WordWithHoleData WHERE userId = :userId")
    List<WordWithHoleData> getAllWWHData(int userId);


    @RequiresApi(api = Build.VERSION_CODES.N)
    default List<String> getAllWWHDataLastUsed(List<WordWithHoleData> listData, int difficulty, int lastUsed) {
        List<String> listRes = new ArrayList<>();
        List<WordWithHoleData> listDataSort = new ArrayList<>();
        List<WordWithHoleData> listSortLose = new ArrayList<>();
        List<WordWithHoleData> listSortWin = new ArrayList<>();
        List<WordWithHoleData> listNeverUsed = new ArrayList<>();


        for (int i = 0; i < listData.size(); i++) {
            if (listData.get(i).getLoseStreak() > 0)
                listSortLose.add(listData.get(i));
            else if (listData.get(i).getWinStreak() > 0)
                listSortWin.add(listData.get(i));
            else
                listNeverUsed.add(listData.get(i));
        }
        listSortLose.sort(Comparator.comparing(WordWithHoleData::getLoseStreak).reversed());
        listSortWin.sort(Comparator.comparing(WordWithHoleData::getLoseStreak));

        listDataSort.addAll(listSortLose);
        listDataSort.addAll(listNeverUsed);
        listDataSort.addAll(listSortWin);

        for (int i = 0; i < listDataSort.size(); i++) {
            if (listDataSort.get(i).getLastUsed() == lastUsed && listDataSort.get(i).getDifficulty() == difficulty) {
                listRes.add(listDataSort.get(i).getResult());
            }
        }
        return listRes;
    }

    @Query("DELETE FROM WordWithHoleData WHERE userId = :userId")
    void deleteWWHDataByUser(int userId);


//PlayWithSoundData
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertPWSData(PlayWithSoundData playWithSoundData);

    @Query("SELECT MAX(dataId) FROM PlayWithSoundData")
    int getPWSMaxId();

    @Update
    void updatePWSData(PlayWithSoundData playWithSoundData);

    @Query("UPDATE PlayWithSoundData SET lastUsed = 0 WHERE userId = :userId AND lastUsed = 1")
    void updateAllPWSDataLastUsed(int userId);

    @Query("SELECT * FROM PlayWithSoundData WHERE userId = :userId AND result = :result")
    PlayWithSoundData getPWSDataByResult(int userId, String result);

    @Query("SELECT * FROM PlayWithSoundData WHERE userId = :userId AND theme = :theme AND difficulty = :difficulty")
    List<PlayWithSoundData> getAllPWSDataByTheme(int userId, String theme, int difficulty);

    default List<Integer> getAllPWSDataLastUsed(List<PlayWithSoundData> listData, int lastUsed) {
        List<Integer> listInt = new ArrayList<>();

            for (int i = 0; i < listData.size(); i++) {
                if (listData.get(i).getLastUsed() == lastUsed) {
                    listInt.add(i);
                }
            }
        return  listInt;
    }

    @Query("DELETE FROM PlayWithSoundData WHERE userId = :userId")
    void deletePWSDataByUser(int userId);


//DrawOnItData
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertDOIData(DrawOnItData drawOnItData);

    @Update
    void updateDOIData(DrawOnItData drawOnItData);

    @Query("UPDATE DrawOnItData SET lastUsed = 0 WHERE userId = :userId")
    void updateAllDOIDataLastUsed(int userId);

    @Query("SELECT * FROM DrawOnItData WHERE userId = :userId AND draw = :draw")
    DrawOnItData getDOIData(int userId, String draw);

    @Query("SELECT * FROM DrawOnItData WHERE userId = :userId")
    List<DrawOnItData> getAllDOIData(int userId);

    default List<Integer> getAllDOIDataLastUsed(List<DrawOnItData> listData, boolean lastUsed) {
        List<Integer> listInt = new ArrayList<>();
        if (lastUsed) {
            for (int i = 0; i < listData.size(); i++) {
                if (listData.get(i).isLastUsed()) {
                    listInt.add(i);
                }
            }
        }
        else {
            for (int i = 0; i < listData.size(); i++) {
                if (!listData.get(i).isLastUsed()) {
                    listInt.add(i);
                }
            }
        }
        return  listInt;
    }

    @Query("DELETE FROM DrawOnItData WHERE userId = :userId")
    void deleteDOIDataByUser(int userId);


//MemoryData
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertMemoryData(MemoryData memoryData);

    @Update
    void updateMemoryData(MemoryData memoryData);

    @Query("UPDATE MemoryData SET difficultyChiffres = (difficultyChiffres +1) WHERE userId = :userId")
    void increaseMemoryDataDifficultyChiffres(int userId);

    @Query("UPDATE MemoryData SET difficultyChiffres = (difficultyChiffres -1) WHERE userId = :userId")
    void decreaseMemoryDataDifficultyChiffres(int userId);

    @Query("UPDATE MemoryData SET difficultyLettres = (difficultyLettres +1) WHERE userId = :userId")
    void increaseMemoryDataDifficultyLettres(int userId);

    @Query("UPDATE MemoryData SET difficultyLettres = (difficultyLettres -1) WHERE userId = :userId")
    void decreaseMemoryDataDifficultyLettres(int userId);

    @Query("SELECT * FROM MemoryData WHERE userId = :userId")
    MemoryData getMemoryData(int userId);

    @Query("SELECT difficultyChiffres FROM MemoryData WHERE userId = :userId")
    int getMemoryDataDifficultyChiffres(int userId);

    @Query("SELECT difficultyLettres FROM MemoryData WHERE userId = :userId")
    int getMemoryDataDifficultyLettres(int userId);

    @Query("SELECT * FROM MemoryData")
    List<MemoryData> getAllMemoryData();


//MemoryCard
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertMemoryCard(MemoryCard memoryCard);

    @Update
    void updateMemoryCard(MemoryCard memoryCard);

    @Query("SELECT * FROM MemoryCard WHERE cardValue LIKE :cardValue")
    MemoryCard getMemoryCard(String cardValue);

    @Query("SELECT drawableImage FROM MemoryCard WHERE cardValue LIKE :cardValue")
    int getMemoryCardDrawableImage(String cardValue);

    @Query("SELECT * FROM MemoryCard")
    List<MemoryCard> getAllMemoryCard();

//TODO Quand on change la difficulté mettre tout les used à false
//MemoryDataCardCrossRef
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertMemoryDataCard(MemoryDataCardCrossRef memoryDataCardCrossRef);

    @Update
    void updateMemoryDataCard(MemoryDataCardCrossRef memoryDataCardCrossRef);

    @Query("SELECT * FROM MemoryDataCardCrossRef WHERE userId = :userId AND cardValue LIKE :cardValue")
    MemoryDataCardCrossRef getMemoryDataCard(int userId, String cardValue);

    @Query("SELECT used FROM MemoryDataCardCrossRef WHERE userId = :userId AND cardValue LIKE :cardValue")
    boolean getMemoryDataCardUsed(int userId, String cardValue);

    @Query("UPDATE MemoryDataCardCrossRef SET used = :used WHERE userId = :userId AND cardValue LIKE :cardValue")
    void updateMemoryDataCardUsed(int userId, String cardValue, boolean used);

    @Query("SELECT * FROM MemoryDataCardCrossRef")
    List<MemoryDataCardCrossRef> getAllMemoryDataCard();

    @Query("SELECT * FROM MemoryDataCardCrossRef WHERE userId = :userId")
    List<MemoryDataCardCrossRef> getAllMemoryDataCardByUserId(int userId);

    @Query("SELECT COUNT(*) FROM MemoryDataCardCrossRef WHERE userId = :userId AND used = 0")
    int getMemoryDataCardNbNotUsed(int userId);

}
