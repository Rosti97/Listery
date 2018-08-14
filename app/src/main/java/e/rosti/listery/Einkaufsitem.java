package e.rosti.listery;

public class Einkaufsitem {

    private boolean checked;
    private String product;

    public Einkaufsitem (boolean checked, String product) {
        this.checked = checked;
        this.product = product;
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
}
