package com.example.cm_final_proj.services;

import android.Manifest;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.media.AudioManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.FragmentManager;

import com.example.cm_final_proj.Alarm_service_activity;
import com.example.cm_final_proj.R;
import com.example.cm_final_proj.get_location;
import com.example.cm_final_proj.housesFragments.houses_recycler;
import com.example.cm_final_proj.model.House;
import com.example.cm_final_proj.model.alarm_array;
import com.example.cm_final_proj.model.alarm_model;
import com.example.cm_final_proj.model.house_array;
import com.example.cm_final_proj.splash;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;


public class Alarm_service extends Service {
    // Must create a default constructor
    Handler mhandler = new Handler();

    // Define the code block to be executed
    public static final long NOTIFY_INTERVAL = 30 * 1000; // 10 seconds
    private Timer mTimer = null;
    Context context = this;

    int hora = 0;
    int minuto = 0;

    // Run the above code block on the main thread after 2 seconds


    @Override
    public void onCreate() {
        createNotificationChannel();
        if (mTimer != null) {
            mTimer.cancel();
        } else {
            // recreate new
            mTimer = new Timer();
        }

        // schedule task
        mTimer.scheduleAtFixedRate(new TimeDisplayTimerTask(), 0, NOTIFY_INTERVAL);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    class TimeDisplayTimerTask extends TimerTask {

        @Override
        public void run() {
            // run on another thread
            mhandler.post(new Runnable() {

                @Override
                public void run() {
                    String dateToday = "";
                    DateFormat df = new SimpleDateFormat("dd MM yyyy, HH:mm");
                    dateToday = df.format(Calendar.getInstance().getTime());

                    String [] time = dateToday.split(" ")[3].split(":");
                    int h = Integer.parseInt(time[0]);
                    int m = Integer.parseInt(time[1]);


                    if(h != hora || minuto != m){
                        hora = h;
                        minuto = m;
                        SharedPreferences sharedPreferences;
                        sharedPreferences= getSharedPreferences("MySharedPref",MODE_PRIVATE);
                        final String s1 = sharedPreferences.getString("userID", "");

                        if(!s1.equals("")) {
                            DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("alarms");

                            Query query = myRef
                                    .orderByChild("uid")
                                    .equalTo(s1);
                            query.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    ArrayList<alarm_array> u = new ArrayList<>();
                                    for (DataSnapshot dataSn : dataSnapshot.getChildren()) {
                                        alarm_model m = dataSn.getValue(alarm_model.class);
                                        System.out.println("HORA" + hora);
                                        System.out.println("HORA" + minuto);
                                        if(m.hora == hora && m.minuto == minuto){
                                            u.add(new alarm_array(dataSn.getKey(), dataSn.getValue(alarm_model.class), "", 0));
                                        }
                                    }

                                    if(u.size()>0){


                                        Intent fullScreenIntent = new Intent(context, Alarm_service_activity.class);

                                        fullScreenIntent.putExtra("uid", s1);

                                        String names ="";
                                        String quant = "";

                                        for(int j =0; j<u.size(); j++){
                                            if(j==u.size()-1){
                                                names = names + u.get(j).getAlarm().medicamento;
                                                quant = quant + u.get(j).getAlarm().compNumb;
                                            }else{
                                                names = names + u.get(j).getAlarm().medicamento + ";";
                                                quant = quant + u.get(j).getAlarm().compNumb + ";";
                                            }
                                        }

                                        fullScreenIntent.putExtra("names", names);
                                        fullScreenIntent.putExtra("quant", quant);
                                        fullScreenIntent.putExtra("hora", hora + ":" + minuto);

                                        PendingIntent fullScreenPendingIntent = PendingIntent.getActivity(context, 0,
                                                fullScreenIntent,PendingIntent.FLAG_UPDATE_CURRENT);
                                        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
                                        NotificationCompat.Builder notificationBuilder =
                                                new NotificationCompat.Builder(context, "alarm1")
                                                        .setSmallIcon(R.drawable.logo_app)
                                                        .setContentTitle("Time to take your medication")
                                                        .setContentText(hora + ":" + minuto)
                                                        .setPriority(NotificationCompat.PRIORITY_MAX)
                                                        .setCategory(NotificationCompat.CATEGORY_ALARM)
                                                        .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                                                        .setSound(alarmSound)
                                                        .setFullScreenIntent(fullScreenPendingIntent, true);

                                        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());

                                        Notification notifi =  notificationBuilder.build();
                                        notifi.flags = Notification.FLAG_INSISTENT;
                                        notificationManager.notify(2,notifi);
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError error) {
                                    // Failed to read value
                                    System.out.println( "EROOORRRRRRRRR");
                                }
                            });

                    }




                    }
                   /*     */
                }
            });
        }

    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Alarm";
            String description = "Service for Alarm";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel( "alarm1", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}

