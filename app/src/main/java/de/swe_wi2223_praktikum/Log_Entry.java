package de.swe_wi2223_praktikum;

import java.time.LocalDateTime;

public class Log_Entry implements java.io.Serializable {
    private String name;
    private LocalDateTime localDateTime;
    private String amount;

    public Log_Entry(String name, LocalDateTime timeStemp, String amount) {
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

    public String getAmount() {
        return amount;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
