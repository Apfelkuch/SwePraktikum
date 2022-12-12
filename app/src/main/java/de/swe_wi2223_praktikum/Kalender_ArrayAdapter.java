package de.swe_wi2223_praktikum;

import android.annotation.SuppressLint;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Kalender_ArrayAdapter extends RecyclerView.Adapter<Kalender_ArrayAdapter.KalenderViewHolder> {

    private ArrayList<Kalender_Entry> kalender_entries;

    public Kalender_ArrayAdapter() {

    }

    public void setKalender_entries(ArrayList<Kalender_Entry> kalender_entries) {
        this.kalender_entries = kalender_entries;
    }

    @NonNull
    @Override
    public KalenderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.kalender_entry , parent, false);
        return new KalenderViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull KalenderViewHolder holder, int position) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Kalender_Entry kalender_entry = kalender_entries.get(position);
            if (kalender_entry == null)
                return;
            holder.name.setText(kalender_entry.getMedicament().getName());
            holder.time.setText(kalender_entry.getLocalDateTime().toLocalTime().getHour() + ":" +
                                (kalender_entry.getLocalDateTime().toLocalTime().getMinute() < 10 ?
                                        "0" + kalender_entry.getLocalDateTime().toLocalTime().getMinute() : kalender_entry.getLocalDateTime().toLocalTime().getMinute()));
            holder.amount.setText(String.valueOf(kalender_entry.getAmount()));
        }
    }

    @Override
    public int getItemCount() {
        if (kalender_entries == null)
            return 0;
        return kalender_entries.size();
    }

    public void clearEntries() {
        if (kalender_entries == null)
            return;
        this.kalender_entries = new ArrayList<>();
    }

    class KalenderViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView time;
        private TextView amount;

        public KalenderViewHolder(View view) {
            super(view);

            name = view.findViewById(R.id.textview_med_name);
            time = view.findViewById(R.id.textview_med_time);
            amount = view.findViewById(R.id.textview_med_amount);
        }
    }
}
