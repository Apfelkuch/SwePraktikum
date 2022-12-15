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

public class MedAdapter extends RecyclerView.Adapter<MedAdapter.ViewHolder> {

    //Globale variablen
    Context context;
    ArrayList<Med> list;

    //Constructor für den Adapter
    MedAdapter(Context context, ArrayList<Med> list){
        this.context = context;
        this.list = list;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView Med_Name, Med_Count, Rep_Count;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            Med_Name = itemView.findViewById(R.id.Med_name);
            Med_Count = itemView.findViewById(R.id.Med_count);
            Rep_Count = itemView.findViewById(R.id.day_count);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.medi_fragment, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.Med_Name.setText(list.get(position).getMedName());
        holder.Med_Count.setText(list.get(position).getMedCount());
        holder.Rep_Count.setText(list.get(position).getRecipeCount());

        //Delete Button mit Dialog-Bestätigung
        holder.itemView.findViewById(R.id.trashbin).setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context)
                    .setTitle("Medikament Löschen")
                    .setMessage("Sind Sie sicher, dass Sie das Medikament löschen möchten?")
                    .setPositiveButton("Löschen", (dialogInterface, i) -> {
                        list.remove(holder.getAdapterPosition());
                        notifyItemRemoved(holder.getAdapterPosition());
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
