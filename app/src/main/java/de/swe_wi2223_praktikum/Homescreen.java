package de.swe_wi2223_praktikum;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
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

import java.util.ArrayList;
import java.util.Locale;

public class Homescreen extends Fragment {

    //Arraylist für die Millisekunden, die der Timer Anzeigen soll.
    ArrayList<Long> futureDates = new ArrayList<>();

    //region Variablen
    private TextView mMain_Countdown_Timer;
    private TextView mSub_Countdown_Timer;
    private Button mBtnNext;
    private boolean mMainTimerRunning = true;
    private long mTimeLeftInMillis;
    private NotificationManagerCompat notificationManagerCompat;
    private Notification notification;
    private int mMain_Count = 0;
    private final long currentTime = System.currentTimeMillis();
    //endregion

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

        //Beispiel Zeiten. Später entfernen.
        futureDates.add(currentTime+5000);
        futureDates.add(currentTime+10000);
        futureDates.add(currentTime+(1000*60*60*24));
        futureDates.add(currentTime+(1000*60*60*24*2));
        futureDates.add(currentTime+(1000*60*60*24*3));

        //startbedingungen
        if(mMainTimerRunning){
            setmTimeLeftInMillis(futureDates.get(mMain_Count)); //Problem wahrscheinlich hier.
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
            updateCountDownText();
            //TODO: Ggf. Inhalt des Kalenders überschreiben. -> Janis.
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
        this.mTimeLeftInMillis = givenTime - currentTime;
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
                mMain_Count = ++mMain_Count;
                setmTimeLeftInMillis(futureDates.get(mMain_Count));
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