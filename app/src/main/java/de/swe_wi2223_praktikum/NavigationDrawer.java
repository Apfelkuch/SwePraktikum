package de.swe_wi2223_praktikum;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.Toast;
import com.google.android.material.navigation.NavigationView;

public class NavigationDrawer extends AppCompatActivity {

    private DrawerLayout drawerLayout;

    @SuppressLint({"MissingInflatedId", "NonConstantResourceId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_drawer_main);
        setTitle("MedikamentenApp"); //TODO: Name der App?

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
//                    loadFragment(new BestellungsOverView());
                    Toast.makeText(NavigationDrawer.this,"Bestellungen", Toast.LENGTH_SHORT).show();
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
        ft.add(R.id.container, fragment);
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