package de.swe_wi2223_praktikum;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;

public class NavigationDrawer extends AppCompatActivity {

    private DrawerLayout drawerLayout;

    //Instancen der Fragmente
    private BestellungsOverView bestellungsOverView;
    private Fragment_Kalender kalender;
    private MedStorage storage;
    private Homescreen homescreen;
    private Fragment_Log log;
    private PlanList plan;

    @SuppressLint({"MissingInflatedId", "NonConstantResourceId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_drawer_main);
        setTitle(getResources().getString(R.string.app_name));
        // construct or load Fragments
        bestellungsOverView = new BestellungsOverView(this);
        bestellungsOverView.load(FileManager.load(FileManager.BESTELLUNGEN, getApplicationContext()));
        kalender = new Fragment_Kalender(this);
        kalender.load(FileManager.load(FileManager.KALENDER, getApplicationContext()));
        storage = new MedStorage(this);
        storage.load(FileManager.load(FileManager.MEDIKAMENT, getApplicationContext()));
        log = new Fragment_Log();
        log.load(FileManager.load(FileManager.LOG, getApplicationContext()));
        plan = new PlanList(this);
        plan.load(FileManager.load(FileManager.PLAN, getApplicationContext()));
        homescreen = new Homescreen(this);
        homescreen.load(FileManager.load(FileManager.HOME, getApplicationContext()));

        loadFragment(homescreen);
        // restart the timer
        homescreen.restartTimer();

        //region Zuordnungen
        drawerLayout = findViewById(R.id.layout_drawer);
        NavigationView navigationView = findViewById(R.id.navigationView);
        Toolbar toolbar = findViewById(R.id.toolbar);
        //endregion

        //region Navigation menu
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.OpenDrawer, R.string.CloseDrawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        //Link menus
        navigationView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.navHome:
                    setTitle(getResources().getString(R.string.app_name));
                    loadFragment(homescreen);
                    Toast.makeText(this, getResources().getString(R.string.Home), Toast.LENGTH_SHORT).show();
                    break;
                case R.id.navKalender:
                    setTitle(getResources().getString(R.string.Kalender));
                    loadFragment(kalender);
                    Toast.makeText(this, getResources().getString(R.string.Kalender), Toast.LENGTH_SHORT).show();
                    break;
                case R.id.navPläne:
                    setTitle(getResources().getString(R.string.Plaene));
                    loadFragment(plan);
                    Toast.makeText(this, getResources().getString(R.string.Plaene), Toast.LENGTH_SHORT).show();
                    break;
                case R.id.navMedikamente:
                    setTitle(getResources().getString(R.string.Medikamente));
                    loadFragment(storage);
                    Toast.makeText(this, getResources().getString(R.string.Medikamente), Toast.LENGTH_SHORT).show();
                    break;
                case R.id.navBestellungen:
                    setTitle(getResources().getString(R.string.Bestellungen));
                    loadFragment(bestellungsOverView);
                    Toast.makeText(this, getResources().getString(R.string.Bestellungen), Toast.LENGTH_SHORT).show();
                    break;
                case R.id.navLog:
                    setTitle(getResources().getString(R.string.Log));
                    loadFragment(log);
                    Toast.makeText(this, getResources().getString(R.string.Log), Toast.LENGTH_SHORT).show();
            }

            //Fenster soll nach Klick schließen.
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });
        //endregion
    }

    private void save() {
        FileManager.save(FileManager.BESTELLUNGEN, getApplicationContext(), bestellungsOverView.saveData());
        FileManager.save(FileManager.KALENDER, getApplicationContext(), kalender.saveData());
        FileManager.save(FileManager.MEDIKAMENT, getApplicationContext(), storage.saveData());
        FileManager.save(FileManager.HOME, getApplicationContext(), homescreen.saveData());
        FileManager.save(FileManager.LOG, getApplicationContext(), log.saveData());
        FileManager.save(FileManager.PLAN, getApplicationContext(), plan.saveData());
    }

    @Override
    protected void onPause() {
        save();
        super.onPause();
    }

    @Override
    protected void onStop() {
        save();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        save();
        super.onDestroy();
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.container, fragment);
        ft.commit();
    }

    //Wenn man in den Hintergrund klickt, dann verschwindet das Fenster.
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    // GETTER && SETTER

    public MedStorage getStorage() {
        return storage;
    }

    public Fragment_Kalender getKalender() {
        return kalender;
    }

    public BestellungsOverView getBestellungsOverView() {
        return bestellungsOverView;
    }

    public Fragment_Log getLog() {
        return log;
    }

    public Homescreen getHomescreen() {
        return homescreen;
    }

    public Fragment_Kalender getFragmentKalender() {
        return kalender;
    }

}