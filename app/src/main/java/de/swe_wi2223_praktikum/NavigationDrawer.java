package de.swe_wi2223_praktikum;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;

public class NavigationDrawer extends AppCompatActivity{

    private DrawerLayout drawerLayout;

    //Instancen der Fragmente
    private BestellungsOverView bestellungsOverView;
    private Fragment_Kalender kalender;
    private MedStorage storage;
    private Homescreen homescreen;
    private Fragment_Log log;
    //TODO: Restliche Fragmente von den anderen instanzieren

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
//        kalender.load(FileManager.load(FileManager.KALENDER, getApplicationContext()));
        storage = new MedStorage(this);
        storage.load(FileManager.load(FileManager.MEDIKAMENT, getApplicationContext()));
        log = new Fragment_Log();
        log.load(FileManager.load(FileManager.LOG, getApplicationContext()));
        homescreen = new Homescreen(this);
        homescreen.load(FileManager.load(FileManager.HOME, getApplicationContext()));

        loadFragment(homescreen);
        // restart the timer
        homescreen.restartTimer();


//        System.out.println("Savepath: " + getFilesDir().getAbsolutePath());

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

        //Neues Icon für die Navigationsleiste
        toolbar.post(() -> {
            Drawable icon = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_baseline_format_list_bulleted_24, null);
            toolbar.setNavigationIcon(icon);
        });

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
                    //TODO: Navigation zu den Plänen.
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
        System.out.println(FileManager.BESTELLUNGEN + " is " +
                (FileManager.save(FileManager.BESTELLUNGEN, getApplicationContext(), bestellungsOverView.saveData()) ? "" : "not ")
                + "saved.");
        System.out.println(FileManager.KALENDER + " is " +
                (FileManager.save(FileManager.KALENDER, getApplicationContext(), kalender.saveData()) ? "" : "not ")
                + "saved.");
        System.out.println(FileManager.MEDIKAMENT + " is " +
                (FileManager.save(FileManager.MEDIKAMENT, getApplicationContext(), storage.saveData()) ? "" : "not ")
                + "saved.");
        System.out.println(FileManager.HOME + " is " +
                (FileManager.save(FileManager.HOME, getApplicationContext(), homescreen.saveData()) ? "" : "not ")
                + "saved.");
        System.out.println(FileManager.LOG + " is " +
                (FileManager.save(FileManager.LOG, getApplicationContext(), log.saveData()) ? "" : "not ")
                + "saved.");
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

    public MedStorage getStorage() {return storage;  }

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

}