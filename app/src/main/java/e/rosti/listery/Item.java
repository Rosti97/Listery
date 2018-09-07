package e.rosti.listery;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import java.util.List;

@Entity
public class Item {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private float price;
    @TypeConverters(DataConverter.class)
    private List<Mate> mates;
    @Ignore
    private boolean checked;

    public Item(String name, float price, List<Mate> mates) {
        this.name = name;
        this.price = price;
        this.mates = mates;
        checked = false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public List<Mate> getMates() {
        return mates;
    }

    public void setMates(List<Mate> mates) {
        this.mates = mates;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
