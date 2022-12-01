package de.swe_wi2223_praktikum;


import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView simpleList;
    ArrayList<Item> medList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        simpleList =  (ListView) findViewById(R.id.simpleListView);
        medList.add(new Item("MedONE",3,3));


        MedAdapter medAdapter = new MedAdapter(this, R.layout.medi_fragment,medList);
        simpleList.setAdapter(medAdapter);
    }
}