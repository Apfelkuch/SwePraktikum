package de.swe_wi2223_praktikum;

import java.time.LocalDateTime;

public class Kalender_Entry implements java.io.Serializable {
    private Medicament medicament;
    private LocalDateTime localDateTime;
    private String amount;

    public Kalender_Entry(Medicament medicament,LocalDateTime localDateTime, String amount) {
        this.medicament = medicament;
        this.localDateTime = localDateTime;
        this.amount = amount;
    }

    // GETTER && SETTER

    public Medicament getMedicament() {
        return medicament;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public String getAmount() {
        return amount;
    }

    public void setMedicament(Medicament medicament) {
        this.medicament = medicament;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
