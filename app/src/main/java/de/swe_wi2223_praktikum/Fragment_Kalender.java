package de.swe_wi2223_praktikum;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;


public class Fragment_Kalender extends Fragment {

    private RecyclerView recyclerView_dayMed;
    private ArrayList<Kalender_Entry> dailyKalenderEntries;
    private final HashMap<LocalDate, ArrayList<Kalender_Entry>> kalenderEntries;


    public Fragment_Kalender() {
        // example data
        kalenderEntries = new HashMap<>();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            addEntry(LocalDateTime.of(2022,11,23,15,0), new Medicament("Medikament A"), "10.2 g");
            addEntry(LocalDateTime.of(2022,11,23,12,30), new Medicament("Medikament B"), "12.4 g");
            addEntry(LocalDateTime.of(2022,11,2,18,0), new Medicament("M_A"), "20 g");
            addEntry(LocalDateTime.of(2022,11,2,12,15), new Medicament("M_B"), "0.4 g");
            addEntry(LocalDateTime.of(2022,11,2,13,0), new Medicament("M_C"), "12.3 g");
            addEntry(LocalDateTime.of(2022,11,16,14,0), new Medicament("Medicament C"), "0.2 g");
            addEntry(LocalDateTime.of(2022,11,16,15,0), new Medicament("Medicament C"), "0.2 g");
            addEntry(LocalDateTime.of(2022,11,16,16,0), new Medicament("Medicament C"), "0.2 g");
            addEntry(LocalDateTime.of(2022,11,16,17,0), new Medicament("Medicament C"), "0.2 g");
            addEntry(LocalDateTime.of(2022,11,16,18,0), new Medicament("Medicament C"), "0.2 g");
            addEntry(LocalDateTime.of(2022,11,16,19,0), new Medicament("Medicament C"), "0.2 g");
            addEntry(LocalDateTime.of(2022,11,16,20,0), new Medicament("Medicament C"), "0.2 g");
        }
        Log.w("Date", kalenderEntries.toString());

        dailyKalenderEntries = new ArrayList<>();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment__kalender, container, false);

        // get the elements of the fragment
        CalendarView calenderView_calendar = view.findViewById(R.id.calendarView);
        recyclerView_dayMed = view.findViewById(R.id.dayMedList);
        MainActivity mainActivity = (MainActivity) getActivity();

        // set a layout for the dynamic list (essentially)
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mainActivity);
        recyclerView_dayMed.setLayoutManager(linearLayoutManager);

        // create and add the ArrayAdapter to synchronise the kalender entries with the dynamic list
        Kalender_ArrayAdapter kalender_arrayAdapter = new Kalender_ArrayAdapter();
        kalender_arrayAdapter.setKalender_entries(dailyKalenderEntries);
        recyclerView_dayMed.setAdapter(kalender_arrayAdapter);

        // when the day is changed the dynamic list is updated
        calenderView_calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {
                month++; // The month is between 0 and 11, but the keys  in the kalender entries are between 1 and 12.
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    // get the entry of the new day
                    dailyKalenderEntries = kalenderEntries.get(LocalDate.of(year, month, day));
                    if (dailyKalenderEntries == null) { // if the entry is empty, show nothing
                        kalender_arrayAdapter.clearEntries();
                        Objects.requireNonNull(recyclerView_dayMed.getAdapter()).notifyDataSetChanged();
                    } else { // if the entry is field, show all entries in the dynamic list
                        kalender_arrayAdapter.setKalender_entries(dailyKalenderEntries);
                        Objects.requireNonNull(recyclerView_dayMed.getAdapter()).notifyDataSetChanged();
                    }
                }
                else {
                    Log.e("ERROOOOOOOOOOOOOOOR", "EE");
                }
            }
        });

        return view;
    }

    /**
     * Add the Entry to the Calender.
     * @param localDateTime The day and time of the entry
     * @param medicament The medicament which is taken
     * @param amount The amount in which the medicament is taken
     */
    public void addEntry(LocalDateTime localDateTime, Medicament medicament, String amount) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            ArrayList<Kalender_Entry> entries = null;
            if (kalenderEntries.containsKey(localDateTime.toLocalDate())) {
                entries = kalenderEntries.get(localDateTime.toLocalDate());
            }
            if (entries == null) {
                entries = new ArrayList<>();
            }
            entries.add(new Kalender_Entry(medicament, localDateTime.toLocalTime(), amount));
            kalenderEntries.put(localDateTime.toLocalDate(), entries);
        }
    }

    /**
     * Deletes the first Medicament where the day, time and medicament are identical from the Calender.
     * @param localDateTime The day and time of the entry
     * @param medicament The Medicament which is taken
     * @return True if successfully, otherwise false.
     */
    public boolean removeEntry(LocalDateTime localDateTime, Medicament medicament) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            ArrayList<Kalender_Entry> entries;
            // check if the date of the entry is in the list
            if (kalenderEntries.containsKey(localDateTime.toLocalDate())) {
                // get all entries of this day
                entries = kalenderEntries.get(localDateTime.toLocalDate());
                if (entries == null)
                    return false;

                for (Kalender_Entry entry : entries) {
                    // check if the day has the entry to delete
                    if (entry.getLocalTime().equals(localDateTime.toLocalTime()) && entry.getMedicament().equals(medicament)) {
                        // delete the first entry which is found
                        entries.remove(entry);
                        kalenderEntries.put(localDateTime.toLocalDate(), entries);
                        return true;
                    }
                }
            }
            return false;
        }
        return false;
    }

}