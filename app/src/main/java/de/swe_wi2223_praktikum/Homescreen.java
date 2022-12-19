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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Locale;

public class Homescreen extends Fragment implements Load{

    ArrayList<Kalender_Entry> futureEntries = new ArrayList<>(); //Entries für die Timer
    ArrayList<Kalender_Entry> pastEntries = new ArrayList<>(); //Entries für die Medikamenten Anzeige

    //region Variablen
    private TextView mMain_Countdown_Timer;
    private TextView mSub_Countdown_Timer;
    private Button mBtnNext;
    private boolean mMainTimerRunning = true;
    private long mTimeLeftInMillis;
    private NotificationManagerCompat notificationManagerCompat;
    private Notification notification;
    private final int mMain_Count = 0;
    private HomescreenAdapter adapterHomescreen;
    //endregion

    public Homescreen(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            //TODO: dummyMedicament bekommen wir von Felix. Später nochmal die Test-Entries testen.
            Med dummyMedicament = new Med("A","0","0");
            futureEntries.add(new Kalender_Entry(dummyMedicament, LocalDateTime.of(LocalDate.now(), LocalTime.ofNanoOfDay(System.currentTimeMillis() + 5000)), "5"));
            futureEntries.add(new Kalender_Entry(dummyMedicament, LocalDateTime.of(LocalDate.now(), LocalTime.ofNanoOfDay(System.currentTimeMillis() + 15000)), "10"));
            futureEntries.add(new Kalender_Entry(dummyMedicament, LocalDateTime.of(LocalDate.now(), LocalTime.ofNanoOfDay(System.currentTimeMillis() + 30000)), "15"));
            futureEntries.add(new Kalender_Entry(dummyMedicament, LocalDateTime.of(LocalDate.now(), LocalTime.ofNanoOfDay(System.currentTimeMillis() + 60000)), "20"));
            setmTimeLeftInMillis(futureEntries.get(mMain_Count).getLocalDateTime().toLocalTime().toNanoOfDay());
        }
    }

    //Schnittstelle für Janis
    public void addFutureEntries(Kalender_Entry futureEntry){
        futureEntries.add(futureEntry);
    }

    @SuppressWarnings("CollectionAddedToSelf")
    @SuppressLint({"MissingInflatedId", "NotifyDataSetChanged"})
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.homescreen_main,container,false);

        //region Assignments
        mMain_Countdown_Timer = view.findViewById(R.id.Timer);
        mSub_Countdown_Timer = view.findViewById(R.id.Timer2);
        mBtnNext = view.findViewById(R.id.btnNext);
        RecyclerView recyclerView_homescreen = view.findViewById(R.id.rcvHomescreen);
        //endregion

        //region Start conditions
        recyclerView_homescreen.setLayoutManager(new LinearLayoutManager(requireActivity()));
        if(mMainTimerRunning){
            System.out.println("Hier könnte Ihre Werbung stehen.");
        }else{
            System.out.println("Fehler");
            mSub_Countdown_Timer.setVisibility(View.VISIBLE);
            mBtnNext.setVisibility(View.VISIBLE);
        }//endregion

        //region Button Next
        mBtnNext.setOnClickListener(NextView -> {
            /*
              On click it sets the timer on running mode. The subtimer and next button disappear
              ans all elements of pastEntries (medicament box) are removed.
              The adapter gets notified about the changes and the countdown updated.
              */
            mMainTimerRunning = true;
            mSub_Countdown_Timer.setVisibility(View.INVISIBLE);
            mBtnNext.setVisibility(View.INVISIBLE);
            pastEntries.removeAll(pastEntries);
            adapterHomescreen.notifyDataSetChanged();
            updateCountDownText();
        });//endregion

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

        adapterHomescreen = new HomescreenAdapter(requireActivity(), pastEntries);
        recyclerView_homescreen.setAdapter(adapterHomescreen);

        return view;
    }

    //region Getter & Setter
    public long getmTimeLeftInMillis() {
        return mTimeLeftInMillis;
    }

    public void setmTimeLeftInMillis(long givenTime) {
        this.mTimeLeftInMillis = givenTime - System.currentTimeMillis();
        startTimer();
    }
    //endregion

    //region Timer starten & updaten
    @SuppressLint("SetTextI18n")
    public void startTimer() {
        new CountDownTimer(getmTimeLeftInMillis(), 1000) {
            /*
             * Gets the left time in millis and counts down with an interval of 1000 milliseconds (1s).
             * With every tick we call the method updateCountDownText, so we get the correct time.
             * */
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onFinish() {
                /*
                 * When the timer runs out we set the boolean to false and add the current Kalender_Entry
                 * information to a new array, so we can further use it for the medicament box. At the same
                 * time we remove the information from the futureEntries list. Next we check if the
                 * futureEntries list still has some entries. If true the next timer starts, else we
                 * display a message that tells the user there are no more entries.
                 * Finally we set the seconds timer, the next button to visible, notify the adapter
                 * of every change and send a push notification.
                 **/
                mMainTimerRunning = false;

                if (futureEntries.isEmpty())
                    return;


                pastEntries.add(futureEntries.remove(mMain_Count));
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && !futureEntries.isEmpty()) {
                    setmTimeLeftInMillis(futureEntries.get(mMain_Count).getLocalDateTime().toLocalTime().toNanoOfDay());
                }else{
                    if(getContext() != null)
                        Toast.makeText(getContext(),"Keine weiteren Einnahmen geplant", Toast.LENGTH_SHORT).show();
                }
                if(mSub_Countdown_Timer != null)
                    mSub_Countdown_Timer.setVisibility(View.VISIBLE);
                if(mBtnNext != null)
                    mBtnNext.setVisibility(View.VISIBLE);
                if(adapterHomescreen != null) {
                    adapterHomescreen.notifyDataSetChanged();
                    reminderNotification();
                }
            }
        }.start();
    }

    public void updateCountDownText() {
        /*
         * Calculates the milliseconds into the correct form (hours, minutes or seconds) and casts
         * them to an int. We bundle it in the format we want and give it to the timers.
         * We check what timer should get the text.
         * */
        int hours = (int) (getmTimeLeftInMillis()/3600000);
        int minutes = (int) (getmTimeLeftInMillis()/60000)%60;
        int seconds = (int) (getmTimeLeftInMillis()/1000)%60;

        String timeLeftFormatted = String.format(Locale.getDefault(),"%02d:%02d:%02d", hours,minutes, seconds);

        if (mMain_Countdown_Timer != null) {

            if (mMainTimerRunning) {
                mMain_Countdown_Timer.setText(timeLeftFormatted);
            } else {
                mSub_Countdown_Timer.setText(timeLeftFormatted);
            }
        }
    }
    //endregion

    //Push notification
    private void reminderNotification() {
        notificationManagerCompat.notify(1, notification);
    }

    @Override
    public void load(Object o) {
        if( o ==null)
            return;
        Object[] objects = (Object[]) o;
        this.pastEntries = (ArrayList<Kalender_Entry>) objects[0];
        this.futureEntries = (ArrayList<Kalender_Entry>) objects[1];
    }

    @Override
    public Object saveData() {
        Object[] objects = new Object[2];
        objects[0] = pastEntries;
        objects[1] = futureEntries;
        return objects;
    }
}