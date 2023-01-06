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

public class PlanList extends Fragment implements Load{
    
    ArrayList<Plan> list = new ArrayList<>();

    private RecyclerView recyclerView;
    private PlanAdapter adapter;
    private CheckBox cBMonday, cBTuesday, cBWednesday, cBThursday, cBFriday, cBSaturday, cBSunday;
    private CheckBox cBMorning, cBMidday, cBEvening;
    private LocalDateTime localDateTime;
    private LocalDateTime morning;
    private LocalDateTime midday;
    private LocalDateTime evening;
    NavigationDrawer navigationDrawer;

    private Med placeHolderMed = new Med("Medikamente auswählen", 0f,0);

    public PlanList(NavigationDrawer navigationDrawer) {

        this.navigationDrawer = navigationDrawer;
    }




    @SuppressLint({"InflateParams"})
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.plan_bib,container,false);



        //Zuweisung der Objekte aus der activity_main.xml
        recyclerView = view.findViewById(R.id.recyViewPlan);
        FloatingActionButton addPlan = view.findViewById(R.id.addPlan);

        //Dient dem RecycleViewer, um eine vertikale Liste zu erstellen.
        recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));

        //Button zum Bearbeiten
        addPlan.setOnClickListener(dialog_view -> {
            // Baut das Dialogfenster auf
            AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
            dialog_view = getLayoutInflater().inflate(R.layout.add_plan_dialog, null);

            ArrayList<PlanMed> usedMeds = new ArrayList<>();


            //Variablen, zum editieren aus der XML-Datei
            EditText editPlanName = dialog_view.findViewById(R.id.editTextPlanName);
            // TODO: Medikamente aus der Medikamenten Listen nehmen
            EditText editMedic = dialog_view.findViewById(R.id.editTextMedikament);
            Spinner spinner = (Spinner) dialog_view.findViewById(R.id.spinner_med);

            RecyclerView medList = dialog_view.findViewById(R.id.medList);
            medList.setLayoutManager(new LinearLayoutManager(requireActivity()));

            PlanMedAdapter planMedAdapter = new PlanMedAdapter(requireActivity());
            planMedAdapter.setMeds(usedMeds);
            medList.setAdapter(planMedAdapter);


            // build drop down menu
            PlanSpinnerAdapter adapter = new PlanSpinnerAdapter(navigationDrawer, (ArrayList<Med>) navigationDrawer.getStorage().getStorage().clone(), placeHolderMed);
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(R.layout.plan_spinner_item);
            // Apply Adapter to spinner
            spinner.setAdapter(adapter);
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



            Fragment_Kalender test = new Fragment_Kalender(null);


            //Der Builder, um das Fenster zu tatäschlich zu befüllen
            builder.setView(dialog_view)
                    .setTitle("Pläne  erstellen: ")
                    .setPositiveButton("Hinzufügen", (dialogInterface, i) -> {
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
                                }

                                //Dienstag
                                if (cBTuesday.isChecked()) {
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                        day_list[1] = DayOfWeek.TUESDAY;
                                    }
                                    System.out.println(day_list[1]);
                                } else {
                                    System.out.println("Nothing");
                                }

                                //Mittwoch
                                if (cBWednesday.isChecked()) {
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                        day_list[2] = DayOfWeek.WEDNESDAY;
                                    }
                                    System.out.println(day_list[2]);
                                } else {
                                    System.out.println("Nothing");
                                }

                                //Donnertsag
                                if (cBThursday.isChecked()) {
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                        day_list[3] = DayOfWeek.THURSDAY;
                                    }
                                    System.out.println(day_list[3]);
                                } else {
                                    System.out.println("Nothing");
                                }

                                //Freitag
                                if (cBFriday.isChecked()) {
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                        day_list[4] = DayOfWeek.FRIDAY;
                                    }
                                    System.out.println(day_list[4]);
                                } else {
                                    System.out.println("Nothing");
                                }

                                //Samstag
                                if (cBSaturday.isChecked()) {
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                        day_list[5] = DayOfWeek.SATURDAY;
                                    }
                                    System.out.println(day_list[5]);
                                } else {
                                    System.out.println("Nothing");
                                }

                                //Sonntag
                                if (cBSunday.isChecked()) {
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                        day_list[6] = DayOfWeek.SUNDAY;
                                    }
                                    System.out.println(day_list[6]);
                                } else {
                                    System.out.println("Nothing");
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
                                }





                        //Am Ende hinzufügen, Software notifyen damit sie animiert, View springt zum Eintrag am Ende
                        list.add(new Plan(tmpPlanName,day_list,morning,midday,evening));
                        planMedAdapter.notifyItemInserted(list.size()-1);
                        recyclerView.scrollToPosition(list.size()-1);
                    })
                    .setNegativeButton("Abbrechen", (dialogInterface, i) -> {
                        //Der Cancel-Button darf leer sein, da als "Default" alles abgebrochen wird und keine Änderungen stattfinden.
                    });
            builder.show();
        });

        adapter = new PlanAdapter(requireActivity(), list);
        recyclerView.setAdapter(adapter);

        return view;
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
