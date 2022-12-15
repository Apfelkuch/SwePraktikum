package de.swe_wi2223_praktikum;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Locale;

public class Homescreen extends Fragment {

    //Arraylist für die Millisekunden, die der Timer Anzeigen soll.
    ArrayList<Kalender_Entry> futureEntries = new ArrayList<>();
    ArrayList<Kalender_Entry> pastEntries = new ArrayList<>();

    //region Variablen
    private TextView mMain_Countdown_Timer;
    private TextView mSub_Countdown_Timer;
    private Button mBtnNext;
    private boolean mMainTimerRunning = true;
    private long mTimeLeftInMillis;
    private NotificationManagerCompat notificationManagerCompat;
    private Notification notification;
    private int mMain_Count = 0;
    //endregion

    public Homescreen(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

            //TODO: dummyMedicament bekommen wir von Felix. Später nochmal die Test-Entries testen.
            Medicament dummyMedicament = new Medicament("A");
            futureEntries.add(new Kalender_Entry(dummyMedicament, LocalTime.ofNanoOfDay(System.currentTimeMillis() + 5000), "5"));
            futureEntries.add(new Kalender_Entry(dummyMedicament, LocalTime.ofNanoOfDay(System.currentTimeMillis() + 15000), "10"));
            futureEntries.add(new Kalender_Entry(dummyMedicament, LocalTime.ofNanoOfDay(System.currentTimeMillis() + 30000), "15"));
            futureEntries.add(new Kalender_Entry(dummyMedicament, LocalTime.ofNanoOfDay(System.currentTimeMillis() + 60000), "20"));
            setmTimeLeftInMillis(futureEntries.get(mMain_Count).getLocalTime().toNanoOfDay());
        }
    }

    public void addFutureEntries(Kalender_Entry futureEntry){
        futureEntries.add(futureEntry);
    }

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.homescreen_main,container,false);

        //region Zuordnungen
        mMain_Countdown_Timer = view.findViewById(R.id.Timer);
        mSub_Countdown_Timer = view.findViewById(R.id.Timer2);
        mBtnNext = view.findViewById(R.id.btnNext);
        //endregion

        //startbedingungen
        if(mMainTimerRunning){
//            setmTimeLeftInMillis(futureDates.get(mMain_Count)); //Problem wahrscheinlich hier.
        }else{
            System.out.println("Fehler");
            mSub_Countdown_Timer.setVisibility(View.VISIBLE);
            mBtnNext.setVisibility(View.VISIBLE);
        }

        //Was soll beim Klick auf den Next Button passieren?
        mBtnNext.setOnClickListener(NextView -> {
            mMainTimerRunning = true;
            mSub_Countdown_Timer.setVisibility(View.INVISIBLE);
            mBtnNext.setVisibility(View.INVISIBLE);
            pastEntries.remove(mMain_Count);
            updateCountDownText();
        });

        //region Notification setup
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("SWE", "Praktikum", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = requireActivity().getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);

        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(requireActivity(), "SWE")
                .setSmallIcon(android.R.drawable.stat_notify_sync)
                .setContentTitle("MedikamentenAPP") //TODO: Name der APP
                .setContentText("Zeit die Drugs einzunehmen :)");

        notification = builder.build();
        notificationManagerCompat = NotificationManagerCompat.from(requireActivity());
        //endregion
        return view;
    }

    //region Getter und Setter für verbleibende Millisekunden
    public long getmTimeLeftInMillis() {
        return mTimeLeftInMillis;
    }

    public void setmTimeLeftInMillis(long givenTime) {
        this.mTimeLeftInMillis = givenTime - System.currentTimeMillis();
//        System.out.println(mTimeLeftInMillis);
        startTimer();
    }
    //endregion

    //region Timer starten & updaten
    @SuppressLint("SetTextI18n")
    private void startTimer() {
        new CountDownTimer(getmTimeLeftInMillis(), 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                mMainTimerRunning = false;
                pastEntries.add(futureEntries.remove(mMain_Count));
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    setmTimeLeftInMillis(futureEntries.get(mMain_Count).getLocalTime().toNanoOfDay());
                }
                mSub_Countdown_Timer.setVisibility(View.VISIBLE);
                mBtnNext.setVisibility(View.VISIBLE);
                reminderNotification();
            }
        }.start();
    }

    private void updateCountDownText() {
        int hours = (int) (getmTimeLeftInMillis()/3600000);
        int minutes = (int) (getmTimeLeftInMillis()/60000)%60;
        int seconds = (int) (getmTimeLeftInMillis()/1000)%60;

        String timeLeftFormatted = String.format(Locale.getDefault(),"%02d:%02d:%02d", hours,minutes, seconds);

        if(mMainTimerRunning){
            mMain_Countdown_Timer.setText(timeLeftFormatted);
        }else{
            mSub_Countdown_Timer.setText(timeLeftFormatted);
        }
    }
    //endregion

    //Push-Benachrichtigung
    private void reminderNotification() {
        notificationManagerCompat.notify(1, notification);
    }
}