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

    @Delete
    void deleteItems(Item...items);

    /* this query returns all purchases - should be used to display information in listView */
    @Query("SELECT * FROM item")
    LiveData<List<Item>> loadAllItems();

    /* returns all roommates */
    @Query("SELECT * FROM mate")
    LiveData<List<Mate>> loadAllMates();

    @Query("SELECT * FROM mate WHERE id > 1")
    LiveData<List<Mate>> loadMatesExceptYourself();

    @Query("SELECT SUM(balance) FROM mate")
    LiveData<Float> loadCompleteBalance();

    @Update
    void updateMates(Mate... mates);

    @Update
    void updateItem(Item item);

    @Query("SELECT * FROM item")
    List<Item> getItems();
}
