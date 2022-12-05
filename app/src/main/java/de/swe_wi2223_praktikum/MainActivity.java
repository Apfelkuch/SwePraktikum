package de.swe_wi2223_praktikum;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.med_bib);

        MedStorage medStorage = new MedStorage();
        getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayout, medStorage).commit();
    }
}