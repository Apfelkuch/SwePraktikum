package de.swe_wi2223_praktikum;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.LocalDate;
import java.util.ArrayList;

public class MedStorage extends Fragment implements Load {
    ArrayList<Med> storage = new ArrayList<>();

    private RecyclerView recyclerView;
    private MedAdapter adapter;
    LocalDate currentDay;

    NavigationDrawer navigationDrawer;

    public MedStorage(NavigationDrawer navigationDrawer) {
        this.navigationDrawer = navigationDrawer;
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            currentDay = LocalDate.now();
//        }
    }



    @SuppressLint("InflateParams")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.med_bib,container,false);

        //Zuweisung der Objekte aus der activity_main.xml
        recyclerView = view.findViewById(R.id.recyViewMed);
        FloatingActionButton addMed = view.findViewById(R.id.addMed);

        //Dient dem RecycleViewer, um eine vertikale Liste zu erstellen.
        recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));

        //Button zum Hinzufügen
        addMed.setOnClickListener(dialog_view -> {
            //Baut das Dialogfenster auf
            AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
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
                        float tmpCount = editCount.getText().toString().isEmpty() ? 0 : Float.parseFloat(editCount.getText().toString());
                        int tmpRecip = editRecip.getText().toString().isEmpty() ? 0 : Integer.parseInt(editRecip.getText().toString());


                        storage.add(new Med(tmpMed, tmpCount, tmpRecip));
                        adapter.notifyItemInserted(storage.size()-1);
                        recyclerView.scrollToPosition(storage.size()-1);
                    })
                    .setNegativeButton("Abbrechen", (dialogInterface, i) -> {
                        //Der Cancel-Button darf leer sein, da als "Default" alles abgebrochen wird und keine Änderungen stattfinden.
                    });
            builder.show();
        });

        adapter = new MedAdapter(requireActivity(), storage,this);
        recyclerView.setAdapter(adapter);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LocalDate dateNow = LocalDate.now();
            if( currentDay == null)
                currentDay = LocalDate.now();
            if(dateNow.isAfter(currentDay)){
                int diff =  (int) LocalDate.now().toEpochDay()-  (int)currentDay.toEpochDay();
                if ( diff >0) {
                    for ( Med med: storage){
                        if(med.getRecipeCount()-diff <0)
                            med.setRecipeCount(0);
                        else
                            med.setRecipeCount(med.getRecipeCount()-diff);

                    }
                    currentDay = LocalDate.now();
                }

            }
        }

        return view;
    }

    @Override
    public void load(Object o) {
        if (o == null){
            return;
        }
        Object o2[] = (Object[])o;
        this.storage = (ArrayList<Med>) o2[0];
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            this.currentDay = (LocalDate) o2[1];
        }
    }

    @Override
    public Object saveData() {
        Object o[] = {storage,currentDay};
        return o;
    }

    public ArrayList<Med> getStorage() {
        return storage;
    }
}

