package e.rosti.listery;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Item implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private float price;
    @TypeConverters(DataConverter.class)
    private List<Mate> mates;
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

    protected Item(Parcel in) {
        name = in.readString();
        price = in.readFloat();
        if (in.readByte() == 0x01) {
            mates = new ArrayList<Mate>();
            in.readList(mates, Mate.class.getClassLoader());
        } else {
            mates = null;
        }
        checked = in.readByte() != 0x00;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeFloat(price);
        if (mates == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(mates);
        }
        dest.writeByte((byte) (checked ? 0x01 : 0x00));
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Item> CREATOR = new Parcelable.Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };
}