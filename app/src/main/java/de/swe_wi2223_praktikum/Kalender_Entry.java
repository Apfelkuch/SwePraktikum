package de.swe_wi2223_praktikum;

import java.time.LocalTime;

public class Kalender_Entry {
    private Medicament medicament;
    private LocalTime localTime;
    private String amount;

    public Kalender_Entry(Medicament medicament,LocalTime localTime, String amount) {
        this.medicament = medicament;
        this.localTime = localTime;
        this.amount = amount;

    }

    /// GETTER && SETTER

    public Medicament getMedicament() {
        return medicament;
    }

    public LocalTime getLocalTime() {
        return localTime;
    }

    public String getAmount() {
        return amount;
    }

    public void setMedicament(Medicament medicament) {
        this.medicament = medicament;
    }

    public void setLocalTime(LocalTime localTime) {
        this.localTime = localTime;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
