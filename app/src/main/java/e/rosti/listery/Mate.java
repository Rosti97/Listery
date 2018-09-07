package e.rosti.listery;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class Mate {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private float balance;

    public Mate(String name, float balance) {
        this.name = name;
        this.balance = balance;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    @NonNull
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public float getBalance() {
        return balance;
    }
}
