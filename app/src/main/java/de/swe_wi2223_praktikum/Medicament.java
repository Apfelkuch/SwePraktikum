package de.swe_wi2223_praktikum;

// TODO: dummy class
public class Medicament implements java.io.Serializable {
    String name;

    public Medicament(String name) {
        this.name = name;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Medicament that = (Medicament) o;

        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }


    // GETTER && SETTER

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
