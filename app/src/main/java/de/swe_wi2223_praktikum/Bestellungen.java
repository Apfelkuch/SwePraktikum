package de.swe_wi2223_praktikum;

//Basic Stuff.
//Constructor, Getter und Setter
public class Bestellungen implements java.io.Serializable {
    Med med;
    private int menge;

    public Bestellungen(Med med, int menge) {
        this.med = med;
        this.menge = menge;
    }

    public void addMenge(int addMenge) {
        this.menge += addMenge;
    }

    public Med getMed() {return med;}

    public void setMenge(int menge) {
        this.menge = menge;
    }
    public int getMenge() {
        return menge;
    }
}
