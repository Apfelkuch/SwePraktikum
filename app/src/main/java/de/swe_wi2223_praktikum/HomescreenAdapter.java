package de.swe_wi2223_praktikum;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HomescreenAdapter extends RecyclerView.Adapter<HomescreenAdapter.ViewHolder> {

    //Globale variablen
    Context context;
    ArrayList<Kalender_Entry> pastEntries;

    //Constructor für den Adapter
    HomescreenAdapter(Context context, ArrayList<Kalender_Entry> pastEntries){
        this.context = context;
        this.pastEntries = pastEntries;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView Medikament, Menge;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            Medikament = itemView.findViewById(R.id.itemName_homescreen);
            Menge = itemView.findViewById(R.id.itemMenge_homescreen);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cardview_homescreen, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.Medikament.setText(pastEntries.get(position).getMedicament().getMedName());
        holder.Menge.setText(String.valueOf(pastEntries.get(position).getAmount()));
    }

    @Override
    public int getItemCount() {
        return pastEntries.size();
    }
}