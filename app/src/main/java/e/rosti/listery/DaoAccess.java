package e.rosti.listery;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface DaoAccess {
    @Insert
    void insertSingleRoommate(Roommates roommate);

    @Insert
    void insertRoommates(Roommates... roommates);

    @Insert
    void insertSingleItem(Item item);

    @Insert
    void insertItems(Item... items);

    @Query("SELECT MAX(itemID) FROM purchases")
    int getCurrentItemID();

    /* returns the assigned persons to every item on purchase list */
    @Query("SELECT mateName FROM roommates r, purchases p WHERE r.mateID = p.mateID AND itemID = :search")
    List<String> loadAssignedMates(int search);

    /* this query returns all purchases - should be used to display information in listView */
    @Query("SELECT * FROM purchases GROUP BY itemID")
    List<Item> loadAllPurchases();

    /* deletes entry from database */
    @Query("DELETE FROM purchases WHERE itemID = :search")
    void deleteByPurchaseID(int search);

    /* return the names of all roommates */
    @Query("SELECT mateName FROM roommates")
    List<String> loadAllRoommates();

    @Query("SELECT * FROM purchases")
    List<Item> getAllItems();

    @Query("SELECT * FROM roommates")
    List<Roommates> getAllRoommates();
}
