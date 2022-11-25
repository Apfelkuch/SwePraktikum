package de.swe_wi2223_praktikum;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<Bestellungen> list = new ArrayList<>();

    private RecyclerView recyclerView;
    private BestellungenAdapter adapter;

    //Wenn die App gestartet wird, wollen die die activity_main sehen.
    @SuppressLint({"MissingInflatedId", "InflateParams"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Bestellungen");

        //Zuweisung der Objekte aus der activity_main.xml
        recyclerView = findViewById(R.id.rcvBestellung);
        FloatingActionButton btnFloat = findViewById(R.id.btnFloat);

        //Dient dem RecycleViewer, um eine vertikale Liste zu erstellen.
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Button zum shoppen
        btnFloat.setOnClickListener(view -> {
            //Baut das Dialogfenster auf
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            view = getLayoutInflater().inflate(R.layout.dialog, null);

            //Variablen, zum editieren aus der XML-Datei
            EditText edtMedikament = view.findViewById(R.id.DialogMedikament);
            EditText edtMenge = view.findViewById(R.id.DialogMenge);

            //Der Builder, um das Fenster zu tatäschlich zu befüllen
            builder.setView(view)
                    .setTitle("Shop")
                    .setPositiveButton("Bestellen", (dialogInterface, i) -> {
                        String tmpMedikament = edtMedikament.getText().toString();
                        String tmpMenge = edtMenge.getText().toString();

                        //Am Ende hinzufügen, Software notifyen damit sie animiert, View springt zum Eintrag am Ende.
                        list.add(new Bestellungen(tmpMedikament, tmpMenge));
                        adapter.notifyItemInserted(list.size()-1);
                        recyclerView.scrollToPosition(list.size()-1);
                    })
                    .setNegativeButton("Abbrechen", (dialogInterface, i) -> {
                        //Der Cancel-Button darf leer sein, da als "Default" alles abgebrochen wird und keine Änderungen stattfinden.
                    });
            builder.show();
        });

        adapter = new BestellungenAdapter(this, list);
        recyclerView.setAdapter(adapter);
    }
}