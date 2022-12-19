package de.swe_wi2223_praktikum;

public interface Load {
    /**
     * The data is given as a Object and internally loaded into the class.
     * @param o The given data.
     */
    void load(Object o);

    /**
     * Build one Object from all parts that should be saved and return this.
     * @return The Object that should be saved.
     */
    Object saveData();
}
