package de.swe_wi2223_praktikum;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class NavigationDrawer extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    @SuppressLint({"MissingInflatedId", "NonConstantResourceId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_drawer_main);
        setTitle("MedikamentenApp"); //TODO: Name der App?

        drawerLayout = findViewById(R.id.layout_drawer);
        navigationView = findViewById(R.id.navigationView);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.OpenDrawer, R.string.CloseDrawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(item -> {
            switch(item.getItemId()){
                case R.id.navHome:
                    //TODO: Navigation zum Homescreen.
                    Toast.makeText(NavigationDrawer.this,"Home", Toast.LENGTH_SHORT).show();
                case R.id.navKalender:
                    //TODO: Navigation zum Kalender.
                    Toast.makeText(NavigationDrawer.this,"Kalender", Toast.LENGTH_SHORT).show();
                case R.id.navPläne:
                    //TODO: Navigation zu den Plänen.
                    Toast.makeText(NavigationDrawer.this,"Pläne", Toast.LENGTH_SHORT).show();
                case R.id.navMedikamente:
                    //TODO: Navgiation zu drecks Penix drogenschrank.
                    Toast.makeText(NavigationDrawer.this,"Medikamente", Toast.LENGTH_SHORT).show();
                case R.id.navBestellungen:
                    //TODO: Später auskommentieren.
//                    startActivity(new Intent(this, BestellungsOverView.class));
                    Toast.makeText(NavigationDrawer.this,"Bestellungen", Toast.LENGTH_SHORT).show();

            } //TODO: Alle activitys müssen in der AndroidManifest.xml bekannt sein.
            //TODO: Brauchen wir die content_main.xml? Gehts für die Activitys oder nur für Fragments möglich?

            //Fenster soll nach Klick schließen.
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });
    }

    //Wenn man in den Hintergrund klickt, dann verschwindet das Fenster.
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        } else{
            super.onBackPressed();
        }
    }
}

//TODO: Was in jede Main-Datei kopiert werden muss:
//--Außerhalb von "onCreate":
//Toolbar toolbar;

//--Innerhalb von "onCreate":
//setContentView(R.layout.bestellungs_overview);
//toolbar = findViewById(R.id.toolbar);
//setSupportActionBar(toolbar);
//setTitle("Bestellungen");

//--Ggf. noch in XML-Files mit RecycleView
// <include layout="@layout/app_bar_main"/>