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
        logEntries.add(0, new Log_Entry(kalender_entry.getMedicament().getMedName(), kalender_entry.getLocalDateTime(), kalender_entry.getAmount()));
        while (logEntries.size() >= maxLogLength) {
            logEntries.remove(logEntries.size() - 1);
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