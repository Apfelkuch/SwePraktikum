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

public class NavigationDrawer extends AppCompatActivity {

    private DrawerLayout drawerLayout;

    //Instancen der Fragmente
    //TODO: Später auskommentieren
//    private final Homescreen homescreen = new Homescreen();
    private BestellungsOverView bestellungsOverView;
    private Fragment_Kalender kalender;
    //TODO: Restliche Fragmente von den anderen instanzieren

    @SuppressLint({"MissingInflatedId", "NonConstantResourceId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_drawer_main);
        setTitle(getResources().getString(R.string.app_name));
//        loadFragment(new Homescreen()); //TODO: Später auskommentieren

        // construct Fragments
        bestellungsOverView = new BestellungsOverView();
        kalender = new Fragment_Kalender();

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
            switch(item.getItemId()){
                case R.id.navHome:
                    setTitle(getResources().getString(R.string.app_name));
                    //TODO: Später auskommentieren.
//                    loadFragment(homescreen);
                    Toast.makeText(this,getResources().getString(R.string.Home), Toast.LENGTH_SHORT).show();
                    break;
                case R.id.navKalender:
                    setTitle(getResources().getString(R.string.Kalender));
                    loadFragment(kalender);
                    Toast.makeText(this,getResources().getString(R.string.Kalender), Toast.LENGTH_SHORT).show();
                    break;
                case R.id.navPläne:
                    setTitle(getResources().getString(R.string.Plaene));
                    //TODO: Navigation zu den Plänen.
                    Toast.makeText(this,getResources().getString(R.string.Plaene), Toast.LENGTH_SHORT).show();
                    break;
                case R.id.navMedikamente:
                    setTitle(getResources().getString(R.string.Medikamente));
                    //TODO: Navgiation zu drecks Penix drogenschrank.
                    Toast.makeText(this,getResources().getString(R.string.Medikamente), Toast.LENGTH_SHORT).show();
                    break;
                case R.id.navBestellungen:
                    setTitle(getResources().getString(R.string.Bestellungen));
                    loadFragment(bestellungsOverView);
                    Toast.makeText(this,getResources().getString(R.string.Bestellungen), Toast.LENGTH_SHORT).show();
                    break;
            }

            //Fenster soll nach Klick schließen.
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });
        //endregion
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
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        } else{
            super.onBackPressed();
        }
    }
}