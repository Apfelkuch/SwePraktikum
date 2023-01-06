package de.swe_wi2223_praktikum;

import java.util.Objects;

public class Med implements java.io.Serializable {
    String medName;
    float medCount;
    int recipeCount;

    public Med(String medName, float medCount, int recipeCount) {
        this.medName = medName;
        this.medCount = medCount;
        this.recipeCount = recipeCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Med med = (Med) o;
        return Float.compare(med.medCount, medCount) == 0 && recipeCount == med.recipeCount && medName.equals(med.medName);
    }


    public void setMedCount(float medCount) {
        this.medCount = medCount;
    }

    public void setRecipeCount(int recipeCount) {
        this.recipeCount = recipeCount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(medName, medCount, recipeCount);
    }

    public String getMedName() {
        return medName;
    }

    public float getMedCount() {
        return medCount;
    }

    public int getRecipeCount() {
        return recipeCount;
    }
}