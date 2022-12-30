package de.swe_wi2223_praktikum;

public class Plan implements java.io.Serializable {
    String PlanName;


    public Plan(String PlanName) {
        this.PlanName = PlanName;


    }

    public String getPlanName() {
        return PlanName;
    }

}
