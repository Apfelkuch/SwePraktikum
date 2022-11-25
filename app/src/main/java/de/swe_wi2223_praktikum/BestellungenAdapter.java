package de.swe_wi2223_praktikum;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BestellungenAdapter extends RecyclerView.Adapter<BestellungenAdapter.ViewHolder> {

    //Globale variablen
    Context context;
    ArrayList<Bestellungen> list;

    //Constructor für den Adapter
    BestellungenAdapter(Context context, ArrayList<Bestellungen> list){
        this.context = context;
        this.list = list;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView Medikament, Menge;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            Medikament = itemView.findViewById(R.id.itemName);
            Menge = itemView.findViewById(R.id.itemMenge);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cardview, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.Medikament.setText(list.get(position).getName());
        holder.Menge.setText(list.get(position).getMenge());

        //Delete Button mit Dialog-Bestätigung
        holder.itemView.findViewById(R.id.btnDelete).setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context)
                    .setTitle("Bestellung Löschen")
                    .setMessage("Sind Sie sicher, dass Sie diese Bestellung löschen möchten?")
                    .setPositiveButton("Bestätigen", (dialogInterface, i) -> {
                        list.remove(holder.getBindingAdapterPosition());
                        notifyItemRemoved(holder.getBindingAdapterPosition());
                    })
                    .setNegativeButton("Abbrechen", (dialogInterface, i) -> {

                    });
            builder.show();
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}