package de.swe_wi2223_praktikum;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
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
import java.util.Comparator;
import java.util.HashMap;
import java.util.Objects;


public class Fragment_Kalender extends Fragment implements Load {

    private RecyclerView recyclerView_dayMed;
    private ArrayList<Kalender_Entry> dailyKalenderEntries;
    private HashMap<LocalDate, ArrayList<Kalender_Entry>> kalenderEntries;

    private final int maxLookupDistance = 10;

    private NavigationDrawer navigationDrawer;

    public Fragment_Kalender(NavigationDrawer navigationDrawer) {
        this.navigationDrawer = navigationDrawer;
        kalenderEntries = new HashMap<>();
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
        NavigationDrawer navigationDrawer = (NavigationDrawer) getActivity();

        // set a layout for the dynamic list (essentially)
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(navigationDrawer);
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
                    dailyKalenderEntries = getEntriesOnDay(LocalDate.of(year, month, day));
                    if (dailyKalenderEntries == null) { // if the entry is empty, show nothing
                        kalender_arrayAdapter.clearEntries();
                        Objects.requireNonNull(recyclerView_dayMed.getAdapter()).notifyDataSetChanged();
                    } else { // if the entry is field, show all entries in the dynamic list
                        kalender_arrayAdapter.setKalender_entries(dailyKalenderEntries);
                        Objects.requireNonNull(recyclerView_dayMed.getAdapter()).notifyDataSetChanged();
                    }
                }
            }
        });
        kalender_arrayAdapter.notifyDataSetChanged();
        recyclerView_dayMed.getAdapter().notifyDataSetChanged();
        return view;
    }

    /**
     * Returns the next entry after a given day and time.
     *
     * @param localDateTime The day and time after which the next entry s returned.
     * @return The next entry after the given day and time.
     */
    public Kalender_Entry getNextEntry(LocalDateTime localDateTime) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            int lookupDistance = maxLookupDistance;
            LocalDate currentLocalDate = localDateTime.toLocalDate();
            while (lookupDistance > 0) {
                ArrayList<Kalender_Entry> dailyKalenderEntries = getEntriesOnDay(currentLocalDate);
                while (dailyKalenderEntries == null && lookupDistance > 0) {
                    currentLocalDate = currentLocalDate.plusDays(1);
                    dailyKalenderEntries = getEntriesOnDay(currentLocalDate);
                    lookupDistance--;
                }
                if (lookupDistance < 0 || dailyKalenderEntries == null) {
                    return null;
                }
                for (Kalender_Entry kalender_entry : dailyKalenderEntries) {
                    if (kalender_entry.getLocalDateTime().isAfter(localDateTime)) {

                        return kalender_entry;
                    }
                }
                currentLocalDate = currentLocalDate.plusDays(1);
                lookupDistance--;
            }
        }
        return null;
    }

    /**
     * Builds an ordered list of all entries for a specific day.
     *
     * @param localDate The Day of the list with Entries.
     * @return Null if the day has no entries and otherwise the list of all entries, ordered by the time of the entry.
     */
    public ArrayList<Kalender_Entry> getEntriesOnDay(LocalDate localDate) {
        ArrayList<Kalender_Entry> dailyKalenderEntries = kalenderEntries.get(localDate);
        if (dailyKalenderEntries == null) { // if the entry is empty, show nothing
            return null;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            dailyKalenderEntries.sort(new Comparator<Kalender_Entry>() {
                @Override
                public int compare(Kalender_Entry kalender_entry, Kalender_Entry second_lender_entry) {
                    if (kalender_entry.getLocalDateTime().equals(second_lender_entry.getLocalDateTime())) {
                        return 0;
                    }
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        if (kalender_entry.getLocalDateTime().toLocalTime().toNanoOfDay() < second_lender_entry.getLocalDateTime().toLocalTime().toNanoOfDay()) {
                            return -1;
                        } else {
                            return 1;
                        }
                    }
                    return 0;
                }
            });
        }
        return dailyKalenderEntries;
    }

    /**
     * Add the Entry to the Calender.
     *
     * @param localDateTime The day and time of the entry
     * @param medicament    The medicament which is taken
     * @param amount        The amount in which the medicament is taken
     */
    public void addEntry(LocalDateTime localDateTime, Med medicament, float amount) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            ArrayList<Kalender_Entry> entries = null;
            if (kalenderEntries.containsKey(localDateTime.toLocalDate())) {
                entries = kalenderEntries.get(localDateTime.toLocalDate());
            }
            if (entries == null) {
                entries = new ArrayList<>();
            }
            entries.add(new Kalender_Entry(medicament, localDateTime, amount));
            kalenderEntries.put(localDateTime.toLocalDate(), entries);
        }
    }

    /**
     * Deletes the first Medicament where the day, time and medicament are identical from the Calender.
     *
     * @param localDateTime The day and time of the entry
     * @param medicament    The Medicament which is taken
     * @return True if successfully, otherwise false.
     */
    public boolean removeEntry(LocalDateTime localDateTime, Med medicament, float amount) {
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
                    if (entry.getLocalDateTime().toLocalTime().equals(localDateTime.toLocalTime()) && entry.getMedicament().equals(medicament) && entry.getAmount() == amount) {
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

    @Override
    public void load(Object o) {
        if (o == null) return;
        kalenderEntries = (HashMap<LocalDate, ArrayList<Kalender_Entry>>) o;
    }

    @Override
    public Object saveData() {
        return kalenderEntries;
    }
}