package de.swe_wi2223_praktikum;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView rcvBestellungen;
    private FloatingActionButton floating_btn;
    private BestellungenAdapter bestellungenAdapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Bestellungen");

        rcvBestellungen = findViewById(R.id.rcv_bestellung);
        floating_btn = findViewById(R.id.floating_btn);

        bestellungenAdapter = new BestellungenAdapter();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcvBestellungen.setLayoutManager(linearLayoutManager);

        bestellungenAdapter.setData(getListBestellungen());
        rcvBestellungen.setAdapter(bestellungenAdapter);
    }

    private List<bestellungen> getListBestellungen() {
        List<bestellungen> list = new ArrayList<>();

//Hardcoded feeding Liste. Später entfernen. Dient nur als Schnittstelle.
        list.add(new bestellungen("Bestellung 1",2));
        list.add(new bestellungen("Bestellung 2",3));
        list.add(new bestellungen("Bestellung 3",4));
        list.add(new bestellungen("Bestellung 4",5));
        list.add(new bestellungen("Bestellung 5",6));
        list.add(new bestellungen("Bestellung 6",7));
        list.add(new bestellungen("Bestellung 7",8));
        list.add(new bestellungen("Bestellung 8",9));
        list.add(new bestellungen("Bestellung 9",10));
        return list;
    }
}