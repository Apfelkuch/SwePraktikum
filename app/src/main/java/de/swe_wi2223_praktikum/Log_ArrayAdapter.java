package de.swe_wi2223_praktikum;

import android.annotation.SuppressLint;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Log_ArrayAdapter extends RecyclerView.Adapter<Log_ArrayAdapter.LogViewHolder> {

    private ArrayList<Log_Entry> log_entries;

    public Log_ArrayAdapter() {

    }

    public void setLog_entries(ArrayList<Log_Entry> log_entries) {
        this.log_entries = log_entries;
    }

    @NonNull
    @Override
    public LogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.log_entry , parent, false);
        return new LogViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull LogViewHolder holder, int position) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Log_Entry log_entry = log_entries.get(position);
            if (log_entry == null)
                return;
            holder.name.setText(log_entry.getName());
            holder.time.setText(log_entry.getLocalDateTime().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")));
            holder.amount.setText(String.valueOf(log_entry.getAmount()));
        }
    }

    @Override
    public int getItemCount() {
        if (log_entries == null)
            return 0;
        return log_entries.size();
    }

    public void clearEntries() {
        if (log_entries == null)
            return;
        this.log_entries = new ArrayList<>();
    }

    class LogViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView time;
        private TextView amount;

        public LogViewHolder(View view) {
            super(view);

            name = view.findViewById(R.id.logMedName);
            time = view.findViewById(R.id.logMedZeit);
            amount = view.findViewById(R.id.logMedAmount);
        }
    }
}
