package e.rosti.listery;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface DaoAccess {
    @Insert
    void insertSingleMate(Mate mate);

    @Insert
    void insertMates(Mate... mates);

    @Insert
    void insertSingleItem(Item item);

    @Insert
    void insertItems(Item... items);

    @Delete
    void deleteMate(Mate mate);

    @Delete
    void deleteItem(Item item);

    /* this query returns all purchases - should be used to display information in listView */
    @Query("SELECT * FROM item")
    LiveData<List<Item>> loadAllItems();

    /* returns all roommates */
    @Query("SELECT * FROM mate")
    LiveData<List<Mate>> loadAllMates();

}
