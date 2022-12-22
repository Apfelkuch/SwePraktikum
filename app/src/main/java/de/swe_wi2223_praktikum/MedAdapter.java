package de.swe_wi2223_praktikum;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
    MedAdapter(Context context, ArrayList<Med> list,  MedStorage vMedstorage ){
        this.context = context;
        this.list = list;
        this.vMedstorage = vMedstorage
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
        addOrder.setOnClickListener(dialog_view -> {
            //Baut das Dialogfenster auf
            AlertDialog.Builder builder = new AlertDialog.Builder();
            dialog_view = getLayoutInflater().inflate(R.layout.add_med_dialog, null);

            //Variablen, zum editieren aus der XML-Datei
            EditText editMed = dialog_view.findViewById(R.id.editTextTextMed);
            EditText editCount = dialog_view.findViewById(R.id.editTextNumber);
            EditText editRecip = dialog_view.findViewById(R.id.editTextNumber2);

            //Der Builder, um das Fenster zu tatsächlich zu befüllen
            builder.setView(dialog_view)
                    .setTitle("Medikament Hinzufügen: ")
                    .setPositiveButton("Hinzufügen", (dialogInterface, i) -> {
                        String tmpMed = editMed.getText().toString();
                        String tmpCount = editCount.getText().toString();
                        String tmpRecip = editRecip.getText().toString();


                        list.add(new Med(tmpMed, tmpCount, tmpRecip));
                        adapter.notifyItemInserted(list.size()-1);
                        recyclerView.scrollToPosition(list.size()-1);
                    })
                    .setNegativeButton("Abbrechen", (dialogInterface, i) -> {
                        //Der Cancel-Button darf leer sein, da als "Default" alles abgebrochen wird und keine Änderungen stattfinden.
                    });
            builder.show();
        });


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

//        //Order Button
//        holder.itemView.findViewById(R.id.Order_button).setOnClickListener(view -> {
//            AlertDialog.Builder builder = new AlertDialog.Builder(context)
//            view = (R.layout.order_med_dialog, null);
//                    .setTitle("Medikament Bestellen")
//                    .setPositiveButton("Bestellen", (dialogInterface, i) -> {
//                        list.remove(holder.getAdapterPosition());
//                        notifyItemRemoved(holder.getAdapterPosition());
//                    })
//                    .setNegativeButton("Abbrechen", (dialogInterface, i) -> {
//
//                    });
//            builder.show();
//        });



    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
