package e.rosti.listery;

public class Mitbewohner {

    private String name;
    private String bilanz;


    public Mitbewohner(String name, String bilanz) {
        this.name = name;
        this.bilanz = bilanz;
    }


    public String getBilanz() {
        return bilanz;
    }

    public void setBilanz(String bilanz) {
        this.bilanz = bilanz;
    }

    public String getName() {
        if (name != null) {
            return name;
        }
        return "Null";
    }

    public void setName(String name) {
        this.name = name;
    }
}



