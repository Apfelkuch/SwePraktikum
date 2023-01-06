package de.swe_wi2223_praktikum;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.checkbox.MaterialCheckBox;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class PlanAdapter extends RecyclerView.Adapter<PlanAdapter.ViewHolder> {
    // TODO: Switch Taste mit Funktion ausstaten
    //Globale variablen
    Context context;
    ArrayList<Plan> list;
    PlanList planList;


    //Constructor für den Adapter
    PlanAdapter(Context context, ArrayList<Plan> list, PlanList planList){
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
        View view = LayoutInflater.from(context).inflate(R.layout.plan_fragment, parent,false);
        return new ViewHolder(view);
    }





    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.Plan_Name.setText(list.get(position).getPlanName());






        //Delete Button mit Dialog-Bestätigung
        holder.itemView.findViewById(R.id.trashbin).setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context)
                    .setTitle("Plan Löschen")
                    .setMessage("Sind Sie sicher, dass Sie diesen Plan löschen möchten?")
                    .setPositiveButton("Löschen", (dialogInterface, i) -> {
                        Plan plan = list.get(holder.getAbsoluteAdapterPosition());
                        list.remove(holder.getAbsoluteAdapterPosition());
                        notifyItemRemoved(holder.getAbsoluteAdapterPosition());




                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            LocalDate dateNow = LocalDate.now();
                            for (int week = 0 ; week <= 11 ; week++){
                                for(int count = 0; count < 7; count++){



                                    for (PlanMed planMed : plan.getPlanMeds()) {
                                        LocalDate vdate = dateNow.plusWeeks(week).plusDays(count);
                                        if (plan.getDay_list()[vdate.getDayOfWeek().getValue()-1] != null ) {
                                            if (plan.getMorning() != null) {
                                                planList.navigationDrawer.getFragmentKalender().removeEntry(
                                                        LocalDateTime.of(vdate.getYear(),vdate.getMonth() , vdate.getDayOfMonth(), plan.getMorning().getHour()
                                                        , plan.getMorning().getMinute()), planMed.getMed(), planMed.getAmount());
                                            }
                                            if (plan.getMidday() != null) {
                                                planList.navigationDrawer.getFragmentKalender().removeEntry(
                                                        LocalDateTime.of(vdate.getYear(),vdate.getMonth(),vdate.getDayOfMonth(), plan.getMidday().getHour()
                                                        , plan.getMidday().getMinute()), planMed.getMed(), planMed.getAmount());
                                            }
                                            if (plan.getEvening() != null) {
                                                planList.navigationDrawer.getFragmentKalender().removeEntry(
                                                        LocalDateTime.of(vdate.getYear(),vdate.getMonth(),vdate.getDayOfMonth(), plan.getEvening().getHour()
                                                        , plan.getEvening().getMinute()), planMed.getMed(), planMed.getAmount());
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        planList.navigationDrawer.getHomescreen().restartTimer();

                    })
                    .setNegativeButton("Abbrechen", (dialogInterface, i) -> {
                        System.out.println("TEST");
                    });
            builder.show();
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
