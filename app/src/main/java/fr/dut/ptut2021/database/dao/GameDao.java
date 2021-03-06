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

import fr.dut.ptut2021.models.database.game.Card;
import fr.dut.ptut2021.models.database.game.DrawOnItData;
import fr.dut.ptut2021.models.database.game.MemoryData;
import fr.dut.ptut2021.models.database.game.MemoryDataCardCrossRef;
import fr.dut.ptut2021.models.database.game.PlayWithSoundData;
import fr.dut.ptut2021.models.database.game.WordWithHoleData;

@Dao
public interface GameDao {

//WordWithHoleData
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertWWHData(WordWithHoleData wordWithHoleStats);

    @Update
    void updateWWHData(WordWithHoleData wordWithHoleStats);

    @Query("UPDATE WordWithHoleData SET lastUsed = 0 WHERE userId = :userId AND lastUsed = 1")
    void updateAllWWHDataLastUsed(int userId);

    @Query("SELECT max(difficulty) FROM WordWithHoleData WHERE userId = :userId")
    int getWWHDataMaxDifficulty(int userId);

    @Query("SELECT * FROM WordWithHoleData WHERE userId = :userId AND result = :result")
    WordWithHoleData getWWHDataByResult(int userId, String result);

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

    @Update
    void updatePWSData(PlayWithSoundData playWithSoundData);

    @Query("UPDATE PlayWithSoundData SET lastUsed = 0 WHERE userId = :userId AND lastUsed = 1")
    void updateAllPWSDataLastUsed(int userId);

    @Query("SELECT * FROM PlayWithSoundData WHERE userId = :userId AND result LIKE :result")
    PlayWithSoundData getPWSDataByResult(int userId, String result);

    @Query("SELECT * FROM PlayWithSoundData WHERE userId = :userId AND theme LIKE :themeName")
    List<PlayWithSoundData> getAllPWSDataByTheme(int userId, String themeName);

    @Query("SELECT * FROM PlayWithSoundData WHERE userId = :userId AND theme LIKE :themeName AND difficulty = :difficulty")
    List<PlayWithSoundData> getAllPWSDataByThemeAndDifficulty(int userId, String themeName, int difficulty);

    @Query("SELECT max(difficulty) FROM PlayWithSoundData WHERE userId = :userId AND theme LIKE :themeName")
    int getPWSDataMaxDifficulty(int userId, String themeName);

    @RequiresApi(api = Build.VERSION_CODES.N)
    default List<String> getAllPWSDataLastUsed(List<PlayWithSoundData> listData, int difficulty, int lastUsed) {
        List<String> listRes = new ArrayList<>();
        List<PlayWithSoundData> listDataSort = new ArrayList<>();
        List<PlayWithSoundData> listSortLose = new ArrayList<>();
        List<PlayWithSoundData> listSortWin = new ArrayList<>();
        List<PlayWithSoundData> listNeverUsed = new ArrayList<>();

        for (int i = 0; i < listData.size(); i++) {
            if (listData.get(i).getLoseStreak() > 0)
                listSortLose.add(listData.get(i));
            else if (listData.get(i).getWinStreak() > 0)
                listSortWin.add(listData.get(i));
            else
                listNeverUsed.add(listData.get(i));
        }
        listSortLose.sort(Comparator.comparing(PlayWithSoundData::getLoseStreak).reversed());
        listSortWin.sort(Comparator.comparing(PlayWithSoundData::getLoseStreak));

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

    @Query("SELECT difficulty FROM DrawOnItData WHERE userId = :userId AND draw = :draw")
    int getDOIDataMaxDif(int userId, String draw);

    @Query("SELECT * FROM DrawOnItData WHERE userId = :userId")
    List<DrawOnItData> getAllDOIData(int userId);


    @Query("DELETE FROM DrawOnItData WHERE userId = :userId")
    void deleteDOIDataByUser(int userId);


//MemoryData
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertMemoryData(MemoryData memoryData);

    @Update
    void updateMemoryData(MemoryData memoryData);

    @Query("SELECT * FROM MemoryData WHERE userId = :userId AND category LIKE :category AND subCategory = :subCategory")
    MemoryData getMemoryData(int userId, String category, int subCategory);

    @Query("SELECT difficulty FROM MemoryData WHERE userId = :userId AND category LIKE :category AND subCategory = :subCategory")
    int getMemoryDataDifficulty(int userId, String category, int subCategory);

    @Query("SELECT maxDifficulty FROM MemoryData WHERE userId = :userId AND category LIKE :category AND subCategory = :subCategory")
    int getMemoryDataMaxDifficulty(int userId, String category, int subCategory);

    @Query("SELECT * FROM MemoryData")
    List<MemoryData> getAllMemoryData();

    @Query("UPDATE MemoryData SET difficulty = (difficulty +1) WHERE userId = :userId AND category LIKE :category AND subCategory = :subCategory")
    void increaseMemoryDataDifficulty(int userId, String category, int subCategory);

    @Query("UPDATE MemoryData SET difficulty = (difficulty -1) WHERE userId = :userId AND category LIKE :category AND subCategory = :subCategory")
    void decreaseMemoryDataDifficulty(int userId, String category, int subCategory);

    @Query("UPDATE MemoryData SET maxDifficulty = (maxDifficulty +1) WHERE userId = :userId AND category LIKE :category AND subCategory = :subCategory")
    void increaseMemoryDataMaxDifficulty(int userId, String category, int subCategory);

    @Query("UPDATE MemoryData SET winStreak = 0, loseStreak = 0 WHERE userId = :userId AND category LIKE :category AND subCategory = :subCategory")
    void resetAllMemoryDataStreak(int userId, String category, int subCategory);

    @Query("DELETE FROM DrawOnItData WHERE userId = :userId")
    void deleteMemoryDataByUser(int userId);

//Card
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertCard(Card memoryCard);

    @Update
    void updateCard(Card memoryCard);

    @Query("SELECT * FROM Card WHERE cardValue LIKE :cardValue")
    Card getCard(String cardValue);

    @Query("SELECT drawableImage FROM Card WHERE cardValue LIKE :cardValue")
    int getCardDrawableImage(String cardValue);

    @Query("SELECT * FROM Card WHERE type LIKE :type")
    List<Card> getAllCardByType(String type);

    @Query("SELECT * FROM Card")
    List<Card> getAllCard();


//MemoryDataCardCrossRef
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertMemoryDataCard(MemoryDataCardCrossRef memoryDataCardCrossRef);

    @Update
    void updateMemoryDataCard(MemoryDataCardCrossRef memoryDataCardCrossRef);

    @Query("SELECT * FROM MemoryDataCardCrossRef WHERE userId = :userId AND category LIKE :category AND subCategory = :subCategory AND cardValue LIKE :cardValue")
    MemoryDataCardCrossRef getMemoryDataCard(int userId, String category, int subCategory, String cardValue);

    @Query("SELECT used FROM MemoryDataCardCrossRef WHERE userId = :userId AND category LIKE :category AND subCategory = :subCategory AND cardValue LIKE :cardValue")
    int getMemoryDataCardUsed(int userId, String category, int subCategory, String cardValue);

    @Query("SELECT * FROM MemoryDataCardCrossRef")
    List<MemoryDataCardCrossRef> getAllMemoryDataCard();

    @Query("SELECT * FROM MemoryDataCardCrossRef WHERE userId = :userId AND category LIKE :category AND subCategory = :subCategory")
    List<MemoryDataCardCrossRef> getAllMemoryDataCardByUser(int userId, String category, int subCategory);

    @Query("SELECT COUNT(*) FROM MemoryDataCardCrossRef WHERE userId = :userId AND category LIKE :category AND subCategory = :subCategory AND used = 0")
    int getMemoryDataCardNbNotUsed(int userId, String category, int subCategory);

    @Query("SELECT COUNT(*) FROM MemoryDataCardCrossRef WHERE userId = :userId AND category LIKE :category AND subCategory = :subCategory AND :maxUsed != used")
    int getMemoryDataCardNbNotMaxUsed(int userId, String category, int subCategory,int maxUsed);

    @Query("SELECT MAX(used) FROM MemoryDataCardCrossRef WHERE userId = :userId AND category LIKE :category AND subCategory = :subCategory")
    int getMemoryDataCardMaxUsed(int userId, String category, int subCategory);

    @Query("SELECT COUNT(*) FROM MemoryDataCardCrossRef WHERE userId = :userId AND category LIKE :category AND subCategory = :subCategory AND used <= (:value-1)")
    int getMemoryDataCardNbUsedLessThan(int userId, String category, int subCategory, int value);

    @Query("SELECT COUNT(*) FROM MemoryDataCardCrossRef WHERE userId = :userId AND category LIKE :category AND subCategory = :subCategory")
    int getMemoryDataCardNbTotal(int userId, String category, int subCategory);

    @Query("UPDATE MemoryDataCardCrossRef SET used = :used WHERE userId = :userId AND category LIKE :category AND subCategory = :subCategory AND cardValue LIKE :cardValue")
    void updateMemoryDataCardUsed(int userId, String category, int subCategory, String cardValue, int used);

    @Query("UPDATE MemoryDataCardCrossRef SET used = 0 WHERE userId = :userId AND category LIKE :category AND subCategory = :subCategory")
    void resetAllMemoryDataCardUsed(int userId, String category, int subCategory);
}
