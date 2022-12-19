package de.swe_wi2223_praktikum;

//Basic Stuff.
//Constructor, Getter und Setter
public class Bestellungen implements java.io.Serializable {
    private String name;
    private String menge;

    public Bestellungen(String name, String menge) {
        this.name = name;
        this.menge = menge;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public void setMenge(String menge) {
        this.menge = menge;
    }
    public String getMenge() {
        return menge;
    }
}
