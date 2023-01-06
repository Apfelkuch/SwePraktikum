package de.swe_wi2223_praktikum;

public class PlanMed implements java.io.Serializable {
    private Med med;
    private float amount;

    public PlanMed(Med med, float amount) {
        this.med = med;
        this.amount = amount;
    }


    // GETTER && SETTER

    public Med getMed() {
        return med;
    }

    public float getAmount() {
        return amount;
    }

    public void setMed(Med med) {
        this.med = med;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }
}
