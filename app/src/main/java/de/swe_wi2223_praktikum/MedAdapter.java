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

public class MedAdapter extends RecyclerView.Adapter<MedAdapter.ViewHolder> {

    //Globale variablen
    Context context;
    ArrayList<Med> list;
    MedStorage vMedstorage;
    View.OnClickListener click ;

    //Constructor für den Adapter
    MedAdapter(Context context, ArrayList<Med> list,  MedStorage vMedstorage ){
        this.context = context;
        this.list = list;
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

        click = new View.OnClickListener() {
            public AlertDialog.Builder builder;
            @Override
            public void onClick(View view) {
                builder = new AlertDialog.Builder(vMedstorage.requireActivity());
                view = vMedstorage.getLayoutInflater().inflate(R.layout.add_recip_days, null);

                //Variablen, zum editieren aus der XML-Datei

                EditText addDays = view.findViewById(R.id.add_recip_days);

                //Der Builder, um das Fenster zu tatsächlich zu befüllen

                builder.setView(view)
                        .setTitle("Rezept verlängern: ")

                        .setNegativeButton("Abbrechen", (dialogInterface, i) -> {
                            //Der Cancel-Button darf leer sein, da als "Default" alles abgebrochen wird und keine Änderungen stattfinden.
                        });

            }

//        addRecipDays.setOnClickListener(dialog_view -> {
//            //Baut das Dialogfenster auf
//            builder = new AlertDialog.Builder(vMedstorage.requireActivity());
//            dialog_view = vMedstorage.getLayoutInflater().inflate(R.layout.add_recip_days, null);
//
//            //Variablen, zum editieren aus der XML-Datei
//
//            EditText addDays = dialog_view.findViewById(R.id.add_recip_days);
//
//            //Der Builder, um das Fenster zu tatsächlich zu befüllen
//
//            builder.setView(dialog_view)
//                    .setTitle("Rezept verlängern: ")
//
//                    .setNegativeButton("Abbrechen", (dialogInterface, i) -> {
//                        //Der Cancel-Button darf leer sein, da als "Default" alles abgebrochen wird und keine Änderungen stattfinden.
//                    });
//
        };






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

//        //Recipy Button
//        addRecipDays.setOnClickListener(dialog_view -> {
//            //Baut das Dialogfenster auf
//            AlertDialog.Builder builder = new AlertDialog.Builder(vMedstorage.requireActivity());
//            dialog_view = vMedstorage.getLayoutInflater().inflate(R.layout.add_recip_days, null);
//
//            //Variablen, zum editieren aus der XML-Datei
//
//            EditText addDays = dialog_view.findViewById(R.id.add_recip_days);
//
//            //Der Builder, um das Fenster zu tatsächlich zu befüllen
//            dialog_view.requestFocus();
//
//            builder.setView(dialog_view)
//                    .setTitle("Rezept verlängern: ")
//                    .setPositiveButton("Verlängern", (dialogInterface, i) -> {
//                        String tmpCount = addDays.getText().toString();
//                        System.out.println("WERT VON IIIIIIIIIIIIII"+parent.indexOfChild(parent.getFocusedChild()));
//                    })
//                    .setNegativeButton("Abbrechen", (dialogInterface, i) -> {
//                        //Der Cancel-Button darf leer sein, da als "Default" alles abgebrochen wird und keine Änderungen stattfinden.
//                    });
//            builder.show();
//        });
        click.builder
                .setpositiveButton("Löschen", (dialogInterface, i)-> {
            System.out.println(position);
        })
                .show();
        holder.itemView.findViewById(R.id.add_recip_day_bttn).setOnClickListener(click);


    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
