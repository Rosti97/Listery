package e.rosti.listery;


public class Einkaufsitem {

    private boolean checked;
    private String product;
    private String mitbewohner;

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
}
