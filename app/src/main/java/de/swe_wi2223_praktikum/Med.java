package de.swe_wi2223_praktikum;

import java.util.Objects;

public class Med implements java.io.Serializable{
    String medName;
    String medCount;
    String recipeCount;

    public Med(String medName, String medCount, String recipeCount) {
        this.medName = medName;
        this.medCount = medCount;
        this.recipeCount = recipeCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Med med = (Med) o;
        return medName.equals(med.medName) && medCount.equals(med.medCount) && recipeCount.equals(med.recipeCount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(medName, medCount, recipeCount);
    }

    public String getMedName() {
        return medName;
    }

    public String getMedCount() {
        return medCount;
    }

    public String getRecipeCount() {
        return recipeCount;
    }
}