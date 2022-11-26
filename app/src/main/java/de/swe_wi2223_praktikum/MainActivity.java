package de.swe_wi2223_praktikum;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Fragment_Kalender fragment_kalender = new Fragment_Kalender();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.FrameLayout, fragment_kalender)
                .commit();
    }
}