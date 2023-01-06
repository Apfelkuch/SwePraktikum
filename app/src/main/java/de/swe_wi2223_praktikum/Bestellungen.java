package de.swe_wi2223_praktikum;

public class Bestellungen implements java.io.Serializable {
    Med med;
    private float menge;

    public Bestellungen(Med med, float menge) {
        this.med = med;
        this.menge = menge;
    }

    public Med getMed() {
        return med;
    }

    public void setMenge(float menge) {
        this.menge = menge;
    }

    public float getMenge() {
        return menge;
    }
}
