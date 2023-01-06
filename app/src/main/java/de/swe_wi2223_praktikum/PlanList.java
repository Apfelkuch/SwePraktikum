package de.swe_wi2223_praktikum;


import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;


import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.time.LocalDateTime;

public class PlanList extends Fragment implements Load {

    ArrayList<Plan> list = new ArrayList<>();

    private RecyclerView recyclerView;
    private PlanAdapter planAdapter;
    private CheckBox cBMonday, cBTuesday, cBWednesday, cBThursday, cBFriday, cBSaturday, cBSunday;
    private CheckBox cBMorning, cBMidday, cBEvening;
    private LocalDateTime localDateTime;
    private LocalDateTime morning;
    private LocalDateTime midday;
    private LocalDateTime evening;
    NavigationDrawer navigationDrawer;

    private Med placeHolderMed = new Med("Medikamente auswählen", 0f, 0);

    public PlanList(NavigationDrawer navigationDrawer) {

        this.navigationDrawer = navigationDrawer;
    }


    @SuppressLint({"InflateParams"})
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.plan_bib, container, false);


        //Zuweisung der Objekte aus der activity_main.xml
        recyclerView = view.findViewById(R.id.recyViewPlan);
        FloatingActionButton addPlan = view.findViewById(R.id.addPlan);

        //Dient dem RecycleViewer, um eine vertikale Liste zu erstellen.
        recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));

        //Button zum Bearbeiten
        addPlan.setOnClickListener(dialog_view -> {
            buildDialog(dialog_view, null);
        });

        planAdapter = new PlanAdapter(requireActivity(), list, this);
        recyclerView.setAdapter(planAdapter);

        return view;
    }

    public void buildDialog(View dialog_view, Plan plan) {
        // Baut das Dialogfenster auf
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        dialog_view = getLayoutInflater().inflate(R.layout.add_plan_dialog, null);

        ArrayList<PlanMed> usedMeds = new ArrayList<>();


        //Variablen, zum editieren aus der XML-Datei
        EditText editPlanName = dialog_view.findViewById(R.id.editTextPlanName);

        Spinner spinner = (Spinner) dialog_view.findViewById(R.id.spinner_med);

        RecyclerView medList = dialog_view.findViewById(R.id.medList);
        medList.setLayoutManager(new LinearLayoutManager(requireActivity()));

        PlanMedAdapter planMedAdapter = new PlanMedAdapter(requireActivity());
        planMedAdapter.setMeds(usedMeds);
        medList.setAdapter(planMedAdapter);


        // build drop down menu
        PlanSpinnerAdapter planSpinnerAdapter = new PlanSpinnerAdapter(navigationDrawer, (ArrayList<Med>) navigationDrawer.getStorage().getStorage().clone(), placeHolderMed);
        // Specify the layout to use when the list of choices appears
        planSpinnerAdapter.setDropDownViewResource(R.layout.plan_spinner_item);
        // Apply Adapter to spinner
        spinner.setAdapter(planSpinnerAdapter);
        // Set a default position
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                Med med = (Med) parent.getItemAtPosition(pos);
                // check if the med is not the placeholder
                if (med.getMedName().equals(placeHolderMed.getMedName()))
                    return;
                // check if the med is already used
                for (PlanMed planMed : usedMeds) {
                    if (planMed.getMed().equals(med)) {
                        spinner.setSelection(0);
                        return;
                    }
                }

                usedMeds.add(new PlanMed(med, 0f));
                planMedAdapter.notifyDataSetChanged();
                // jump to the top after a med is added to the plan
                spinner.setSelection(0);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //Checkboxen der Wochentage
        cBMonday = dialog_view.findViewById(R.id.checkBox_Mo);
        cBTuesday = dialog_view.findViewById(R.id.checkBox_Di);
        cBWednesday = dialog_view.findViewById(R.id.checkBox_Mi);
        cBThursday = dialog_view.findViewById(R.id.checkBox_Do);
        cBFriday = dialog_view.findViewById(R.id.checkBox_Fr);
        cBSaturday = dialog_view.findViewById(R.id.checkBox_Sa);
        cBSunday = dialog_view.findViewById(R.id.checkBox_So);

        DayOfWeek[] day_list;
        day_list = new DayOfWeek[7];

        //Zeit der einahme
        cBMorning = dialog_view.findViewById(R.id.checkBox_Morning);
        cBMidday = dialog_view.findViewById(R.id.checkBox_Midday);
        cBEvening = dialog_view.findViewById(R.id.checkBox_Evening);



        if (plan == null) {
            //Der Builder, um das Fenster zu tatäschlich zu befüllen
            builder.setView(dialog_view)
                    .setTitle("Pläne  erstellen: ")
                    .setPositiveButton("Hinzufügen", (dialogInterface, i) -> {
                        String tmpPlanName = editPlanName.getText().toString();
//                        String tmpMedic = editMedic.getText().toString();

                        if (cBMonday.isChecked()) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                day_list[0] = DayOfWeek.MONDAY;
                            }
                            System.out.println(day_list[0]);
                        } else {
                            System.out.println("nothing");
                            day_list[0] =null;
                        }

                        //Dienstag
                        if (cBTuesday.isChecked()) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                day_list[1] = DayOfWeek.TUESDAY;
                            }
                            System.out.println(day_list[1]);
                        } else {
                            System.out.println("Nothing");
                            day_list[1] =null;
                        }

                        //Mittwoch
                        if (cBWednesday.isChecked()) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                day_list[2] = DayOfWeek.WEDNESDAY;
                            }
                            System.out.println(day_list[2]);
                        } else {
                            System.out.println("Nothing");
                            day_list[2] =null;
                        }

                        //Donnertsag
                        if (cBThursday.isChecked()) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                day_list[3] = DayOfWeek.THURSDAY;
                            }
                            System.out.println(day_list[3]);
                        } else {
                            System.out.println("Nothing");
                            day_list[3] = null ;
                        }

                        //Freitag
                        if (cBFriday.isChecked()) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                day_list[4] = DayOfWeek.FRIDAY;
                            }
                            System.out.println(day_list[4]);
                        } else {
                            System.out.println("Nothing");
                            day_list[4] =null ;
                        }

                        //Samstag
                        if (cBSaturday.isChecked()) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                day_list[5] = DayOfWeek.SATURDAY;
                            }
                            System.out.println(day_list[5]);
                        } else {
                            System.out.println("Nothing");
                            day_list[5] =null ;
                        }

                        //Sonntag
                        if (cBSunday.isChecked()) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                day_list[6] = DayOfWeek.SUNDAY;
                            }
                            System.out.println(day_list[6]);
                        } else {
                            System.out.println("Nothing");
                            day_list[6] =null ;
                        }


                        //Zeiten der Einnahme
                        //Morgens
                        if (cBMorning.isChecked()) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                                morning = LocalDateTime.now().with(LocalTime.of(9, 0));
                                System.out.println(morning.getHour());
                            }
                            System.out.println("Morning");


                        } else {
                            System.out.println("Nothing");
                            morning = null ;
                        }

                        //Mittag
                        if (cBMidday.isChecked()) {

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                midday = LocalDateTime.now().with(LocalTime.of(12, 0));

                                System.out.println(midday.getHour());
                            }

                            System.out.println("Mittag");


                        } else {
                            System.out.println("Nothing");
                            midday = null;
                        }

                        //Abends
                        if (cBEvening.isChecked()) {

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                evening = LocalDateTime.now().with(LocalTime.of(18, 0));
                                System.out.println(evening.getHour());
                            }

                            System.out.println("Abends");

                        } else {
                            System.out.println("Nothing");
                            evening= null;
                        }
                        Plan vPlan = new Plan(tmpPlanName, day_list, morning, midday, evening, usedMeds);
                        activate(vPlan);

                        //Am Ende hinzufügen, Software notifyen damit sie animiert, View springt zum Eintrag am Ende
                        list.add(vPlan);
                        planMedAdapter.notifyItemInserted(list.size() - 1);
                        recyclerView.scrollToPosition(list.size() - 1);
                    })
                    .setNegativeButton("Abbrechen", (dialogInterface, i) -> {
                        //Der Cancel-Button darf leer sein, da als "Default" alles abgebrochen wird und keine Änderungen stattfinden.
                    });
            builder.show();
        }
        else
        {
            int index = list.indexOf(plan);

            deactivate(plan);
            list.remove(plan);

            // Load old values
            editPlanName.setText(plan.getPlanName());
            cBMonday.setChecked(plan.getDay_list()[0] != null);
            cBTuesday.setChecked(plan.getDay_list()[1] != null);
            cBWednesday.setChecked(plan.getDay_list()[2] != null);
            cBThursday.setChecked(plan.getDay_list()[3] != null);
            cBFriday.setChecked(plan.getDay_list()[4] != null);
            cBSaturday.setChecked(plan.getDay_list()[5] != null);
            cBSunday.setChecked(plan.getDay_list()[6] != null);
            cBMorning.setChecked(plan.getMorning() != null);
            cBMidday.setChecked(plan.getMidday() != null);
            cBEvening.setChecked(plan.getEvening() != null);
            usedMeds.addAll(plan.getPlanMeds());

            //Der Builder, um das Fenster zu tatäschlich zu befüllen
            builder.setView(dialog_view)
                    .setTitle("Plan Bearbeiten")
                    .setPositiveButton("Akzeptieren", (dialogInterface, i) -> {
                        String tmpPlanName = editPlanName.getText().toString();
//                        String tmpMedic = editMedic.getText().toString();

                        // TODO: LOCALDATE TIME für die Zeit ... Array für Tage
                        //Checkbox überprüfen
                        if (cBMonday.isChecked()) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                day_list[0] = DayOfWeek.MONDAY;
                            }
                            System.out.println(day_list[0]);
                        } else {
                            System.out.println("nothing");
                            day_list[0] =null;
                        }

                        //Dienstag
                        if (cBTuesday.isChecked()) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                day_list[1] = DayOfWeek.TUESDAY;
                            }
                            System.out.println(day_list[1]);
                        } else {
                            System.out.println("Nothing");
                            day_list[1] =null;
                        }

                        //Mittwoch
                        if (cBWednesday.isChecked()) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                day_list[2] = DayOfWeek.WEDNESDAY;
                            }
                            System.out.println(day_list[2]);
                        } else {
                            System.out.println("Nothing");
                            day_list[2] =null;
                        }

                        //Donnertsag
                        if (cBThursday.isChecked()) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                day_list[3] = DayOfWeek.THURSDAY;
                            }
                            System.out.println(day_list[3]);
                        } else {
                            System.out.println("Nothing");
                            day_list[3] = null ;
                        }

                        //Freitag
                        if (cBFriday.isChecked()) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                day_list[4] = DayOfWeek.FRIDAY;
                            }
                            System.out.println(day_list[4]);
                        } else {
                            System.out.println("Nothing");
                            day_list[4] =null ;
                        }

                        //Samstag
                        if (cBSaturday.isChecked()) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                day_list[5] = DayOfWeek.SATURDAY;
                            }
                            System.out.println(day_list[5]);
                        } else {
                            System.out.println("Nothing");
                            day_list[5] =null ;
                        }

                        //Sonntag
                        if (cBSunday.isChecked()) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                day_list[6] = DayOfWeek.SUNDAY;
                            }
                            System.out.println(day_list[6]);
                        } else {
                            System.out.println("Nothing");
                            day_list[6] =null ;
                        }


                        //Zeiten der Einnahme
                        //Morgens
                        if (cBMorning.isChecked()) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                                morning = LocalDateTime.now().with(LocalTime.of(9, 0));
                                System.out.println(morning.getHour());
                            }
                            System.out.println("Morning");


                        } else {
                            System.out.println("Nothing");
                            morning = null ;
                        }

                        //Mittag
                        if (cBMidday.isChecked()) {

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                midday = LocalDateTime.now().with(LocalTime.of(12, 0));

                                System.out.println(midday.getHour());
                            }

                            System.out.println("Mittag");


                        } else {
                            System.out.println("Nothing");
                            midday = null;
                        }

                        //Abends
                        if (cBEvening.isChecked()) {

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                evening = LocalDateTime.now().with(LocalTime.of(18, 0));
                                System.out.println(evening.getHour());
                            }

                            System.out.println("Abends");

                        } else {
                            System.out.println("Nothing");
                            evening= null;
                        }


                        //Am Ende hinzufügen, Software notifyen damit sie animiert, View springt zum Eintrag am Ende


                        Plan vPlan = new Plan(tmpPlanName, day_list, morning, midday, evening, usedMeds);
                        activate(vPlan);

                        list.add(index, vPlan);
                        planMedAdapter.notifyDataSetChanged();
                        planAdapter.notifyDataSetChanged();


                    })
                    .setNegativeButton("Abbrechen", (dialogInterface, i) -> {
                        //Der Cancel-Button darf leer sein, da als "Default" alles abgebrochen wird und keine Änderungen stattfinden.

                        activate(plan);

                        list.add(index, plan);
                        planMedAdapter.notifyDataSetChanged();
                        planAdapter.notifyDataSetChanged();
                    });
            builder.show();
        }
        planAdapter.notifyDataSetChanged();
    }
    public void deactivate(Plan plan) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LocalDate dateNow = LocalDate.now();
            for (int week = 0 ; week <= 11 ; week++){
                for(int count = 0; count < 7; count++){



                    for (PlanMed planMed : plan.getPlanMeds()) {
                        LocalDate vdate = dateNow.plusWeeks(week).plusDays(count);
                        if (plan.getDay_list()[vdate.getDayOfWeek().getValue()-1] != null ) {
                            if (plan.getMorning() != null) {
                                this.navigationDrawer.getFragmentKalender().removeEntry(
                                        LocalDateTime.of(vdate.getYear(),vdate.getMonth() , vdate.getDayOfMonth(), plan.getMorning().getHour()
                                                , plan.getMorning().getMinute()), planMed.getMed(), planMed.getAmount());
                            }
                            if (plan.getMidday() != null) {
                                this.navigationDrawer.getFragmentKalender().removeEntry(
                                        LocalDateTime.of(vdate.getYear(),vdate.getMonth(),vdate.getDayOfMonth(), plan.getMidday().getHour()
                                                , plan.getMidday().getMinute()), planMed.getMed(), planMed.getAmount());
                            }
                            if (plan.getEvening() != null) {
                                this.navigationDrawer.getFragmentKalender().removeEntry(
                                        LocalDateTime.of(vdate.getYear(),vdate.getMonth(),vdate.getDayOfMonth(), plan.getEvening().getHour()
                                                , plan.getEvening().getMinute()), planMed.getMed(), planMed.getAmount());
                            }
                        }
                    }
                }
            }
        }
        this.navigationDrawer.getHomescreen().restartTimer();
    }
    public void activate(Plan plan){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LocalDate dateNow = LocalDate.now();
            for (int week = 0 ; week <= 10 ; week++){
                for(int count = 0; count < 7; count++){



                    for (PlanMed planMed : plan.getPlanMeds()) {
                        LocalDate vdate = dateNow.plusWeeks(week).plusDays(count);
                        if (plan.getDay_list()[vdate.getDayOfWeek().getValue()-1] != null ) {
                            if (morning != null) {
                                navigationDrawer.getFragmentKalender().addEntry(LocalDateTime.of(vdate.getYear(),vdate.getMonth() , vdate.getDayOfMonth(), morning.getHour()
                                        , morning.getMinute()), planMed.getMed(), planMed.getAmount());
                            }
                            if (midday != null) {
                                navigationDrawer.getFragmentKalender().addEntry(LocalDateTime.of(vdate.getYear(),vdate.getMonth(),vdate.getDayOfMonth(), midday.getHour()
                                        , midday.getMinute()), planMed.getMed(), planMed.getAmount());
                            }
                            if (evening != null) {
                                navigationDrawer.getFragmentKalender().addEntry(LocalDateTime.of(vdate.getYear(),vdate.getMonth(),vdate.getDayOfMonth(), evening.getHour()
                                        , evening.getMinute()), planMed.getMed(), planMed.getAmount());
                            }
                        }
                    }
                }
            }
        }
        navigationDrawer.getHomescreen().restartTimer();

    }

    @Override
    public void load(Object o) {
        if (o == null)
            return;
        this.list = (ArrayList<Plan>) o;
    }

    @Override
    public Object saveData() {
        return list;
    }
}
