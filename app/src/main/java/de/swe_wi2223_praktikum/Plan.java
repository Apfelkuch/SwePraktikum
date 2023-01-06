package de.swe_wi2223_praktikum;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

public class Plan implements java.io.Serializable {
    String PlanName;
    DayOfWeek[] day_list;
    LocalDateTime morning,midday,evening;



    public Plan(String PlanName,DayOfWeek[] day_list,LocalDateTime morning,LocalDateTime midday,LocalDateTime evening) {
        this.PlanName = PlanName;
        this.day_list = day_list;
        this.morning = morning;
        this.midday = midday;
        this.evening = evening;


    }

    public String getPlanName() {
        return PlanName;
    }
    public DayOfWeek[] getDay_list() { return day_list; }
    public LocalDateTime getMorning() { return morning; }
    public LocalDateTime getMidday() { return midday; }
    public LocalDateTime getEvening() { return evening; }


}
