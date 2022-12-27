package de.swe_wi2223_praktikum;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MedAdapter extends RecyclerView.Adapter<MedAdapter.ViewHolder> {

    //Globale variablen
    Context context;
    ArrayList<Med> storage;
    MedStorage vMedstorage;

    //Constructor für den Adapter
    MedAdapter(Context context, ArrayList<Med> storage, MedStorage vMedstorage ){
        this.context = context;
        this.storage = storage;
        this.vMedstorage = vMedstorage;
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

        Button addOrder = view.findViewById(R.id.order_button);
        ImageButton addRecipDays = view.findViewById(R.id.add_recip_day_bttn);


        addOrder.setOnClickListener(dialog_view -> {
            //Baut das Dialogfenster auf
            AlertDialog.Builder builder = new AlertDialog.Builder(vMedstorage.requireActivity());
            dialog_view = vMedstorage.getLayoutInflater().inflate(R.layout.order_med_dialog, null);
            //Variablen, zum editieren aus der XML-Datei

            EditText addMedCount = dialog_view.findViewById(R.id.order_med_count);

            //Der Builder, um das Fenster zu tatsächlich zu befüllen
            builder.setView(dialog_view)
                    .setTitle("Medikament Bestellen: ")
                    .setPositiveButton("Bestellen", (dialogInterface, i) -> {
                        String tmpCount = addMedCount.getText().toString();

                        //TODO: Bestellübergabe

                    })
                    .setNegativeButton("Abbrechen", (dialogInterface, i) -> {
                        //Der Cancel-Button darf leer sein, da als "Default" alles abgebrochen wird und keine Änderungen stattfinden.
                    });
            builder.show();
        });

        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.Med_Name.setText(storage.get(holder.getAbsoluteAdapterPosition()).getMedName());
        holder.Med_Count.setText(storage.get(holder.getAbsoluteAdapterPosition()).getMedCount());
        holder.Rep_Count.setText(storage.get(holder.getAbsoluteAdapterPosition()).getRecipeCount());

        //Delete Button mit Dialog-Bestätigung
        holder.itemView.findViewById(R.id.trashbin).setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context)
                    .setTitle("Medikament Löschen")
                    .setMessage("Sind Sie sicher, dass Sie das Medikament löschen möchten?")
                    .setPositiveButton("Löschen", (dialogInterface, i) -> {
                        storage.remove(holder.getAbsoluteAdapterPosition());
                        notifyItemRemoved(holder.getAbsoluteAdapterPosition());
                    })
                    .setNegativeButton("Abbrechen", (dialogInterface, i) -> {

                    });
            builder.show();
        });

        holder.itemView.findViewById(R.id.add_recip_day_bttn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(vMedstorage.requireActivity());
                view = vMedstorage.getLayoutInflater().inflate(R.layout.add_recip_days, null);

                //Variablen, zum editieren aus der XML-Datei

                EditText addDays = view.findViewById(R.id.add_recip_days);

                //Der Builder, um das Fenster zu tatsächlich zu befüllen

                builder.setView(view)
                        .setTitle("Rezept verlängern: ")
                        .setPositiveButton("Bestätigen", (dialogInterface, i) -> {
                            vMedstorage.storage.get(holder.getAbsoluteAdapterPosition()).setRecipeCount(addDays.getText().toString());
                            notifyItemChanged(holder.getAbsoluteAdapterPosition());
                        })
                        .setNegativeButton("Abbrechen", (dialogInterface, i) -> {
                            //Der Cancel-Button darf leer sein, da als "Default" alles abgebrochen wird und keine Änderungen stattfinden.
                        })
                        .show();
            }
        });

        holder.itemView.findViewById(R.id.order_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(vMedstorage.requireActivity());
                view = vMedstorage.getLayoutInflater().inflate(R.layout.order_med_dialog, null);

                //Variablen, zum editieren aus der XML-Datei

                EditText orderCount = view.findViewById(R.id.order_med_count);

                //Der Builder, um das Fenster zu tatsächlich zu befüllen

                builder.setView(view)
                        .setTitle("Bestellen: ")
                        .setPositiveButton("Bestätigen", (dialogInterface, i) -> {
                            //TODO: Null abfangen ( Also wenn nichts eingegeben wird aber trotzdem auf bestätigen gedrückt wird!!!!!!!!!
                            String medName = vMedstorage.storage.get(holder.getAbsoluteAdapterPosition()).getMedName();

                            vMedstorage.navigationDrawer.getBestellungsOverView().addBestellung(medName, Integer.parseInt(orderCount.getText().toString()));
                        })
                        .setNegativeButton("Abbrechen", (dialogInterface, i) -> {
                            //Der Cancel-Button darf leer sein, da als "Default" alles abgebrochen wird und keine Änderungen stattfinden.
                        })
                        .show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return storage.size();
    }
}
