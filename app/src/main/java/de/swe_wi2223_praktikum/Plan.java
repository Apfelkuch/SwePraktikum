package de.swe_wi2223_praktikum;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Plan implements java.io.Serializable {
    private String PlanName;
    private DayOfWeek[] day_list;
    private LocalDateTime morning, midday, evening;
    private ArrayList<PlanMed> planMeds;


    public Plan(String PlanName, DayOfWeek[] day_list, LocalDateTime morning, LocalDateTime midday, LocalDateTime evening, ArrayList<PlanMed> planMeds) {
        this.PlanName = PlanName;
        this.day_list = day_list;
        this.morning = morning;
        this.midday = midday;
        this.evening = evening;
        this.planMeds = planMeds;
    }

    public String getPlanName() {
        return PlanName;
    }

    public DayOfWeek[] getDay_list() {
        return day_list;
    }

    public LocalDateTime getMorning() {
        return morning;
    }

    public LocalDateTime getMidday() {
        return midday;
    }

    public LocalDateTime getEvening() {
        return evening;
    }

    public ArrayList<PlanMed> getPlanMeds() {
        return planMeds;
    }
}
