package e.rosti.listery;


import android.os.Parcel;
import android.os.Parcelable;

public class Einkaufsitem implements Parcelable{

    private boolean checked;
    private String product;
    private String mitbewohner;
    private String price;

    public Einkaufsitem (boolean checked, String product, String mitbewohner) {
        this.checked = checked;
        this.product = product;
        this.mitbewohner = mitbewohner;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getMitbewohner() {
        return mitbewohner;
    }

    public void setMitbewohner(String mitbewohner) {
        this.mitbewohner = mitbewohner;
    }

    public String getPrice() {
        if (price != null) {
            return price;
        }
            return "Null";
    }

    public void setPrice(String price) {
        this.price = price;
    }


    /**Implementing Parcelable-Interface**/
    public static final Parcelable.Creator<Einkaufsitem> CREATOR
            = new Parcelable.Creator<Einkaufsitem>() {
        public Einkaufsitem createFromParcel(Parcel in) {
            return new Einkaufsitem(in);
        }

        public Einkaufsitem[] newArray(int size) {
            return new Einkaufsitem[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(checked ? 1 : 0);
        dest.writeString(this.product);
        dest.writeString(this.mitbewohner);
    }

    protected Einkaufsitem(Parcel in) {
        checked = in.readInt() != 0;
        product = in.readString();
        mitbewohner = in.readString();
    }
}

