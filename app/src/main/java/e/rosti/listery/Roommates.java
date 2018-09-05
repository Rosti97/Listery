package e.rosti.listery;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class Roommates {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    private int mateID;
    private String mateName;
    private float mateBalance;

    public Roommates(String mateName, float mateBalance) {
        this.mateName = mateName;
        this.mateBalance = mateBalance;
    }

    public void setMateID(@NonNull int mateID) {
        this.mateID = mateID;
    }

    public void setMateName(String mateName) {
        this.mateName = mateName;
    }

    public void setMateBalance(float mateBalance) {
        this.mateBalance = mateBalance;
    }

    @NonNull
    public int getMateID() {
        return mateID;
    }

    public String getMateName() {
        return mateName;
    }

    public float getMateBalance() {
        return mateBalance;
    }
}
