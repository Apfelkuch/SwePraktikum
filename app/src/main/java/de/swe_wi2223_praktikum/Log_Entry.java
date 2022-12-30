package de.swe_wi2223_praktikum;

import java.time.LocalDateTime;

public class Log_Entry implements java.io.Serializable {
    private String name;
    private LocalDateTime localDateTime;
    private float amount;

    public Log_Entry(String name, LocalDateTime timeStemp, float amount) {
        this.name = name;
        this.localDateTime = timeStemp;
        this.amount = amount;
    }

    // GETTER && SETTER

    public String getName() {
        return name;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public float getAmount() {
        return amount;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }
}
