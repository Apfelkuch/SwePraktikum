package de.swe_wi2223_praktikum;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Fragment_Log extends Fragment implements Load {

    private ArrayList<Log_Entry> logEntries;
    private final int maxLogLength = 10_000_000;

    public Fragment_Log() {
        logEntries = new ArrayList<>();

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            addLogEntry(new Kalender_Entry(new Medicament("Medikament A"), LocalDateTime.of(2022, 11, 23, 15, 0), 10.2));
//            addLogEntry(new Kalender_Entry(new Medicament("Medikament B"), LocalDateTime.of(2022, 11, 23, 12, 30), 12.4));
//            addLogEntry(new Kalender_Entry(new Medicament("M_A"), LocalDateTime.of(2022, 11, 2, 18, 0), 20));
//            addLogEntry(new Kalender_Entry(new Medicament("M_B"), LocalDateTime.of(2022, 11, 2, 12, 15), 0.4));
//            addLogEntry(new Kalender_Entry(new Medicament("M_C"), LocalDateTime.of(2022, 11, 2, 13, 0), 12.3));
//            addLogEntry(new Kalender_Entry(new Medicament("Medicament C"), LocalDateTime.of(2022, 11, 16, 14, 0), 0.2));
//            addLogEntry(new Kalender_Entry(new Medicament("Medicament C"), LocalDateTime.of(2022, 11, 16, 15, 0), 0.2));
//            addLogEntry(new Kalender_Entry(new Medicament("Medicament C"), LocalDateTime.of(2022, 11, 16, 16, 0), 0.2));
//            addLogEntry(new Kalender_Entry(new Medicament("Medicament C"), LocalDateTime.of(2022, 11, 16, 17, 0), 0.2));
//            addLogEntry(new Kalender_Entry(new Medicament("Medicament C"), LocalDateTime.of(2022, 11, 16, 18, 0), 0.2));
//            addLogEntry(new Kalender_Entry(new Medicament("Medicament C"), LocalDateTime.of(2022, 11, 16, 19, 0), 0.2));
//            addLogEntry(new Kalender_Entry(new Medicament("Medicament C"), LocalDateTime.of(2022, 11, 16, 20, 0), 0.2));
//        }

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment__log, container, false);

        RecyclerView recyclerView_logList = view.findViewById(R.id.logList);
        Button logDeleteLog = view.findViewById(R.id.logDeleteLog);
        NavigationDrawer navigationDrawer = (NavigationDrawer) getActivity();

        recyclerView_logList.setLayoutManager(new LinearLayoutManager(navigationDrawer));

        Log_ArrayAdapter log_arrayAdapter = new Log_ArrayAdapter();
        log_arrayAdapter.setLog_entries(logEntries);
        recyclerView_logList.setAdapter(log_arrayAdapter);

        logDeleteLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logEntries.clear();
                recyclerView_logList.getAdapter().notifyDataSetChanged();
            }
        });

        return view;
    }

    public void addLogEntry(Kalender_Entry kalender_entry) {
        logEntries.add(new Log_Entry(kalender_entry.getMedicament().getMedName(), kalender_entry.getLocalDateTime(), kalender_entry.getAmount()));
        while (logEntries.size() >= maxLogLength) {
            logEntries.remove(0);
        }
    }

    @Override
    public void load(Object o) {
        if (o == null)
            return;
        logEntries = (ArrayList<Log_Entry>) o;
    }

    @Override
    public Object saveData() {
        return logEntries;
    }
}