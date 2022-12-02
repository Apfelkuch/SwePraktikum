package de.swe_wi2223_praktikum;

public class Med {
    String medName;
    String medCount;
    String recipeCount;

    public Med(String medName, String medCount, String recipeCount) {
        this.medName = medName;
        this.medCount = medCount;
        this.recipeCount = recipeCount;
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