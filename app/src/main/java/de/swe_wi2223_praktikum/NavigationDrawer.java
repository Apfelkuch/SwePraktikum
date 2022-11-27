package de.swe_wi2223_praktikum;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.util.Locale;

public class NavigationDrawer extends AppCompatActivity {

    //region Variablen
    private DrawerLayout drawerLayout;
    private static final long START_TIME_IN_MILLIS = 86400000;
    private TextView mTextViewCountDown;
    private Button mBtnStartPause;
    private Button mBtnReset;
    private CountDownTimer mCountDownTimer;
    private boolean mTimerRunning;
    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;
    private NotificationManagerCompat notificationManagerCompat;
    private Notification notification;
    //endregion

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
        mTextViewCountDown = findViewById(R.id.Timer);
        mBtnStartPause = findViewById(R.id.btnStartPause);
        mBtnReset = findViewById(R.id.btnReset);
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
//                    startActivity(new Intent(this, BestellungsOverView.class));
                    Toast.makeText(NavigationDrawer.this,"Bestellungen", Toast.LENGTH_SHORT).show();

            } //TODO: Alle activitys müssen in der AndroidManifest.xml bekannt sein.
            //TODO: Brauchen wir die content_main.xml? Gehts für die Activitys oder nur für Fragments möglich?

            //Fenster soll nach Klick schließen.
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });
        //endregion

        //region Countdowntimer auf dem Startmenu
        mBtnStartPause.setOnClickListener(view -> {
            if (mTimerRunning){
                pauseTimer();
            } else {
                startTimer();
            }
        });

        mBtnReset.setOnClickListener(view -> resetTimer());
        updateCountDownText();


        // Notification setup
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("SWE", "Praktikum", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);

        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "SWE")
                .setSmallIcon(android.R.drawable.stat_notify_sync)
                .setContentTitle("MedikamentenAPP") //TODO: Name der APP?
                .setContentText("Zeit die Drugs einzunehmen :)");

        notification = builder.build();
        notificationManagerCompat = NotificationManagerCompat.from(this);
        //endregion
    }

    //region Countdowntimer-Button-Funktionen
    @SuppressLint("SetTextI18n")
    private void startTimer() {
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                mTimerRunning=false;
                mBtnStartPause.setText("Start");
                mBtnStartPause.setVisibility(View.INVISIBLE);
                mBtnReset.setVisibility(View.VISIBLE);
                reminderNotification();
            }
        }.start();

        mTimerRunning = true;
        mBtnStartPause.setText("Pause");
        mBtnReset.setVisibility(View.INVISIBLE);
    }

    //Push-Benachrichtigung
    private void reminderNotification() {
        notificationManagerCompat.notify(1, notification);
    }

    @SuppressLint("SetTextI18n")
    private void pauseTimer() {
        mCountDownTimer.cancel();
        mTimerRunning=false;
        mBtnStartPause.setText("Start");
        mBtnReset.setVisibility(View.VISIBLE);
    }

    private void resetTimer() {
        mTimeLeftInMillis = START_TIME_IN_MILLIS;
        updateCountDownText();
        mBtnReset.setVisibility(View.INVISIBLE);
        mBtnStartPause.setVisibility(View.VISIBLE);
    }

    private void updateCountDownText() {
        int hours = (int) (mTimeLeftInMillis/3600000);
        int minutes = (int) (mTimeLeftInMillis/60000)%60;
        int seconds = (int) (mTimeLeftInMillis/1000)%60;

        String timeLeftFormatted = String.format(Locale.getDefault(),"%02d:%02d:%02d", hours,minutes, seconds);
        mTextViewCountDown.setText(timeLeftFormatted);
    }
    //endregion

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