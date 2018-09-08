package e.rosti.listery;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

@Entity
public class Mate implements Parcelable {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private float balance;

    private Mate(Parcel in){
        id = in.readInt();
        name = in.readString();
        balance = in.readFloat();
    }

    public Mate(String name, float balance) {
        this.name = name;
        this.balance = balance;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeFloat(balance);
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

    public static final Parcelable.Creator<Mate> CREATOR = new Parcelable.Creator<Mate>(){
        @Override
        public Mate createFromParcel(Parcel parcel) {
            return new Mate(parcel);
        }

        @Override
        public Mate[] newArray(int i) {
            return new Mate[i];
        }
    };
}
