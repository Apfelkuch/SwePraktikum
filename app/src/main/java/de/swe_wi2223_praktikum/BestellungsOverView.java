package de.swe_wi2223_praktikum;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;

public class BestellungsOverView extends Fragment implements Load {
    ArrayList<Bestellungen> list = new ArrayList<>();

    private RecyclerView recyclerView;
    private BestellungenAdapter adapter;

    private NavigationDrawer navigationDrawer;

    public BestellungsOverView(NavigationDrawer navigationDrawer) {
        this.navigationDrawer = navigationDrawer;
    }

    @SuppressLint("InflateParams")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bestellungs_overview, container, false);

        //Zuweisung der Objekte aus der activity_main.xml
        recyclerView = view.findViewById(R.id.rcvBestellung);
        FloatingActionButton btnFloat = view.findViewById(R.id.btnFloat);

        //Dient dem RecycleViewer, um eine vertikale Liste zu erstellen.
        recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));

        //Button zum shoppen
        btnFloat.setOnClickListener(dialog_view -> {
            //Baut das Dialogfenster auf
            AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
            dialog_view = getLayoutInflater().inflate(R.layout.dialog, null);

            //Variablen, zum editieren aus der XML-Datei
            EditText edtMedikament = dialog_view.findViewById(R.id.DialogMedikament);
            EditText edtMenge = dialog_view.findViewById(R.id.DialogMenge);

            //Der Builder, um das Fenster zu tatäschlich zu befüllen
            builder.setView(dialog_view)
                    .setTitle("Shop")
                    .setPositiveButton("Bestellen", (dialogInterface, i) -> {
                        String tmpMedikament = edtMedikament.getText().toString();
                        String tmpMenge = edtMenge.getText().toString();

                        //Am Ende hinzufügen, Software notifyen damit sie animiert, View springt zum Eintrag am Ende.
                        list.add(new Bestellungen(tmpMedikament, tmpMenge));
                        adapter.notifyItemInserted(list.size() - 1);
                        recyclerView.scrollToPosition(list.size() - 1);
                    })
                    .setNegativeButton("Abbrechen", (dialogInterface, i) -> {
                        //Der Cancel-Button darf leer sein, da als "Default" alles abgebrochen wird und keine Änderungen stattfinden.
                    });
            builder.show();
        });

        adapter = new BestellungenAdapter(requireActivity(), list);
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void load(Object o) {
        if (o == null) return;
        this.list = (ArrayList<Bestellungen>) o;
    }

    @Override
    public Object saveData() {
        return list;
    }
}