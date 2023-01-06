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

public class PlanAdapter extends RecyclerView.Adapter<PlanAdapter.ViewHolder> {
    //Globale variablen
    Context context;
    ArrayList<Plan> list;
    PlanList planList;


    //Constructor für den Adapter
    PlanAdapter(Context context, ArrayList<Plan> list, PlanList planList) {
        this.context = context;
        this.list = list;
        this.planList = planList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView Plan_Name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            Plan_Name = itemView.findViewById(R.id.Plan_name);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.plan_fragment, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.Plan_Name.setText(list.get(position).getPlanName());

        holder.itemView.findViewById(R.id.Edit_button).setOnClickListener(view -> {
            planList.buildDialog(view, list.get(position));
        });

        //Delete Button mit Dialog-Bestätigung
        holder.itemView.findViewById(R.id.trashbin).setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context)
                    .setTitle("Plan Löschen")
                    .setMessage("Sind Sie sicher, dass Sie diesen Plan löschen möchten?")
                    .setPositiveButton("Löschen", (dialogInterface, i) -> {
                        Plan plan = list.get(holder.getAbsoluteAdapterPosition());
                        list.remove(holder.getAbsoluteAdapterPosition());
                        notifyItemRemoved(holder.getAbsoluteAdapterPosition());
                        planList.deactivate(plan);

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
