package de.swe_wi2223_praktikum;

//Basic Stuff.
//Constructor, Getter und Setter
public class Bestellungen implements java.io.Serializable {
    private String name;
    private int menge;

    public Bestellungen(String name, int menge) {
        this.name = name;
        this.menge = menge;
    }

    public void addMenge(int addMenge) {
        this.menge += addMenge;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public void setMenge(int menge) {
        this.menge = menge;
    }
    public int getMenge() {
        return menge;
    }
}
