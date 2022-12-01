package de.swe_wi2223_praktikum;

public class Item {
    String medName;
    int medCount;
    int recipeCount;

    public Item(String medName, int medCount, int recipeCount) {
        this.medName = medName;
        this.medCount = medCount;
        this.recipeCount = recipeCount;
    }

    public String getMedName() {
        return medName;
    }

    public int getMedCount() {
        return medCount;
    }

    public int getRecipeCount() {
        return recipeCount;
    }
}
