package de.swe_wi2223_praktikum;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class PlanList extends Fragment implements Load{
    ArrayList<Plan> list = new ArrayList<>();

    private RecyclerView recyclerView;
    private PlanAdapter adapter;
    private CheckBox cBMonday, cBTuesday, cBWednesday, cBThursday, cBFriday, cBSaturday, cBSunday;
    private CheckBox cBMorning, cBMidday, cBEvening;



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



            //Variablen, zum editieren aus der XML-Datei
            EditText editPlanName = dialog_view.findViewById(R.id.editTextPlanName);
            // TODO: Medikamente aus der Medikamenten Listen nehmen
            EditText editMedic = dialog_view.findViewById(R.id.editTextMedikament);

            //Checkboxen der Wochentage
            cBMonday = dialog_view.findViewById(R.id.checkBox_Mo);
            cBTuesday = dialog_view.findViewById(R.id.checkBox_Di);
            cBWednesday = dialog_view.findViewById(R.id.checkBox_Mi);
            cBThursday = dialog_view.findViewById(R.id.checkBox_Do);
            cBFriday = dialog_view.findViewById(R.id.checkBox_Fr);
            cBSaturday = dialog_view.findViewById(R.id.checkBox_Sa);
            cBSunday = dialog_view.findViewById(R.id.checkBox_So);

            //Zeit der einahme
            cBMorning = dialog_view.findViewById(R.id.checkBox_Morning);
            cBMidday = dialog_view.findViewById(R.id.checkBox_Midday);
            cBEvening = dialog_view.findViewById(R.id.checkBox_Evening);

            //Der Builder, um das Fenster zu tatäschlich zu befüllen
            builder.setView(dialog_view)
                    .setTitle("Pläne  erstellen: ")
                    .setPositiveButton("Hinzufügen", (dialogInterface, i) -> {
                        String tmpPlanName = editPlanName.getText().toString();
                        //String tmpMedic = editMedic.getText().toString();

                        // TODO: Die Zeiten in Millisekunden zurück geben oder so das gleiche für die Wochentage abspache mit Kalender
                        //Checkbox überprüfen
                        if (cBMonday.isChecked()){
                            System.out.println("Monday");
                        }else {
                            System.out.println("Nothing");
                        }

                        //Dienstag
                        if (cBTuesday.isChecked()){
                            System.out.println("Dienstag");
                        } else{
                            System.out.println("Nothing");
                        }

                        //Mittwoch
                        if (cBWednesday.isChecked()){
                            System.out.println("Mittwoch");
                        }else {
                            System.out.println("Nothing");
                        }

                        //Donnertsag
                        if (cBThursday.isChecked()){
                            System.out.println("Donnerstag");
                        }else {
                            System.out.println("Nothing");
                        }

                        //Freitag
                        if (cBFriday.isChecked()){
                            System.out.println("Freitag");
                        }else {
                            System.out.println("Nothing");
                        }

                        //Samstag
                        if (cBSaturday.isChecked()){
                            System.out.println("Samsatg");
                        }else {
                            System.out.println("Nothing");
                        }

                        //Sonntag
                        if (cBSunday.isChecked()){
                            System.out.println("Sonntag");
                        }else {
                            System.out.println("Nothing");
                        }


                        //Zeiten der Einnahme
                        //Morgens
                        if (cBMorning.isChecked()){
                            System.out.println("Morgens");
                        }else {
                            System.out.println("Nothing");
                        }

                        //Mittag
                        if (cBMidday.isChecked()){
                            System.out.println("Mittag");
                        }else {
                            System.out.println("Nothing");
                        }

                        //Abends
                        if (cBEvening.isChecked()){
                            System.out.println("Abends");
                        }else {
                            System.out.println("Nothing");
                        }




                        //Am Ende hinzufügen, Software notifyen damit sie animiert, View springt zum Eintrag am Ende
                        list.add(new Plan(tmpPlanName));
                        adapter.notifyItemInserted(list.size()-1);
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
