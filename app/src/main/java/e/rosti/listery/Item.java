package e.rosti.listery;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "purchases", primaryKeys = {"mateID", "itemID"}, foreignKeys = @ForeignKey(entity = Roommates.class, parentColumns = "mateID", childColumns = "mateID"))
public class Item {
    @NonNull
    private int itemID;
    @NonNull
    private int mateID;
    private float itemPrice;
    private String itemName;

    public Item(@NonNull int itemID, @NonNull int mateID, float itemPrice, String itemName) {
        this.itemID = itemID;
        this.mateID = mateID;
        this.itemPrice = itemPrice;
        this.itemName = itemName;
    }

    public void setItemID(@NonNull int itemID) {
        this.itemID = itemID;
    }

    public void setMateID(@NonNull int mateID) {
        this.mateID = mateID;
    }

    public void setItemPrice(float itemPrice) {
        this.itemPrice = itemPrice;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    @NonNull
    public int getItemID() {
        return itemID;
    }

    @NonNull
    public int getMateID() {
        return mateID;
    }

    public float getItemPrice() {
        return itemPrice;
    }

    public String getItemName() {
        return itemName;
    }
}
