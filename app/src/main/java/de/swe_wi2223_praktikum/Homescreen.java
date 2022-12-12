package de.swe_wi2223_praktikum;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.health.SystemHealthManager;
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

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Homescreen extends Fragment {

    //region Variablen
//    private static final long START_TIME_IN_MILLIS = 86400000;
    private TextView mTextViewCountDown;
    //    private Button mBtnStartPause;
//    private Button mBtnReset;
    private Button mBtnNext;
    private CountDownTimer mCountDownTimer;
    private boolean mTimerRunning;
    private long mTimeLeftInMillis=getmTimeLeftInMillis();
    private NotificationManagerCompat notificationManagerCompat;
    private Notification notification;
    //endregion

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.homescreen_main,container,false);

        //region Zuordnungen
        mTextViewCountDown = view.findViewById(R.id.Timer);
//        mBtnStartPause = view.findViewById(R.id.btnStartPause);
//        mBtnReset = view.findViewById(R.id.btnReset);
        mBtnNext = view.findViewById(R.id.btnNext);
        //endregion

        //region Countdowntimer auf dem Startmenu
//        mBtnStartPause.setOnClickListener(StartPauseView -> {
//            if (mTimerRunning){
//                pauseTimer();
//            } else {
//                startTimer();
//            }
//        });
        setmTimeLeftInMillis(10000);

        mBtnNext.setOnClickListener(NextView -> {
            startTimer();
        });

//        mBtnReset.setOnClickListener(ResetView -> resetTimer());
//        updateCountDownText();

        // Notification setup
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("SWE", "Praktikum", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = requireActivity().getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);

        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(requireActivity(), "SWE")
                .setSmallIcon(android.R.drawable.stat_notify_sync)
                .setContentTitle("MedikamentenAPP") //TODO: Name der APP?
                .setContentText("Zeit die Drugs einzunehmen :)");

        notification = builder.build();
        notificationManagerCompat = NotificationManagerCompat.from(requireActivity());
        //endregion

        return view;
    }

    //region Getter und Setter
    //Getter für verbleibende Millisekunden
    public long getmTimeLeftInMillis() {
        return mTimeLeftInMillis;
    }

    //Setter für verbleibende Millisekunden
    public void setmTimeLeftInMillis(long givenTime) {
        this.mTimeLeftInMillis =givenTime;
        startTimer();
    }
    //endregion

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
//                mBtnStartPause.setText("Start");
//                mBtnStartPause.setVisibility(View.INVISIBLE);
//                mBtnReset.setVisibility(View.VISIBLE);
                mBtnNext.setVisibility(View.VISIBLE);
                reminderNotification();
            }
        }.start();

        mTimerRunning = true;
//        mBtnStartPause.setText("Pause");
//        mBtnReset.setVisibility(View.INVISIBLE);
    }

    //Push-Benachrichtigung
    private void reminderNotification() {
        notificationManagerCompat.notify(1, notification);
    }

//    @SuppressLint("SetTextI18n")
//    private void pauseTimer() {
//        mCountDownTimer.cancel();
//        mTimerRunning=false;
//        mBtnStartPause.setText("Start");
//        mBtnReset.setVisibility(View.VISIBLE);
//    }

//    private void resetTimer() {
//        mTimeLeftInMillis = getmTimeLeftInMillis();
//        updateCountDownText();
//        mBtnReset.setVisibility(View.INVISIBLE);
//        mBtnStartPause.setVisibility(View.VISIBLE);
//    }

    private void updateCountDownText() {
        int hours = (int) (mTimeLeftInMillis/3600000);
        int minutes = (int) (mTimeLeftInMillis/60000)%60;
        int seconds = (int) (mTimeLeftInMillis/1000)%60;

        String timeLeftFormatted = String.format(Locale.getDefault(),"%02d:%02d:%02d", hours,minutes, seconds);
        mTextViewCountDown.setText(timeLeftFormatted);
    }
    //endregion
}