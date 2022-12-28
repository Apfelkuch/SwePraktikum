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
    BestellungsOverView bestellung;
    //Constructor für den Adapter
    BestellungenAdapter(Context context, ArrayList<Bestellungen> list, BestellungsOverView bestellung ){
        this.context = context;
        this.list = list;
        this.bestellung = bestellung;
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
        holder.Medikament.setText(list.get(position).getMed().getMedName());
        holder.Menge.setText(String.valueOf(list.get(position).getMenge()));

        //Delete Button mit Dialog-Bestätigung
        holder.itemView.findViewById(R.id.btnDelete).setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context)
                    .setTitle("Bestellung Löschen")
                    .setMessage("Sind Sie sicher, dass Sie diese Bestellung löschen möchten?")
                    .setPositiveButton("Bestätigen", (dialogInterface, i) -> {
                        list.remove(holder.getAbsoluteAdapterPosition());
                        notifyItemRemoved(holder.getAbsoluteAdapterPosition());
                    })
                    .setNegativeButton("Abbrechen", (dialogInterface, i) -> {

                    });
            builder.show();
        });

        //Approve Button mit Dialog-Bestätigung
        holder.itemView.findViewById(R.id.btnApprove).setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context)
                    .setTitle("Lieferung bestätigen")
                    .setMessage("Sind Sie sicher, dass Sie die Lieferung bestätigen möchten?")
                    .setPositiveButton("Bestätigen", (dialogInterface, i) -> {
                        //TODO: Hier muss durchs bestätigen der Medikamentenschrank bearbeitet werden (Menge ändern).
                        //TODO: Ggf. noch der Artikel hinzugefügt werden?
                        //TODO: Was soll mit der Bestellung passieren? Einfach entfernen?
                        for( Med med :bestellung.getNavigationDrawer().getStorage().storage) {
                            if (med.getMedName() == bestellung.list.get(holder.getAbsoluteAdapterPosition()).getMed().getMedName())
                                med.setMedCount(bestellung.list.get(holder.getAbsoluteAdapterPosition()).getMenge()+
                                        med.medCount);
                        }
                        list.remove(holder.getAbsoluteAdapterPosition());
                        notifyItemRemoved(holder.getAbsoluteAdapterPosition());
                    })
                    .setNegativeButton("Abbrechen", (dialogInterface, i) -> {
                        //Kein Inhalt nötig. Da "Default"-Einstellung alles Abbrechen.
                    });
            builder.show();
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}