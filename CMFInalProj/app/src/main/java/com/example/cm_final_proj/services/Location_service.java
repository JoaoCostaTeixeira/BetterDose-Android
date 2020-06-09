package com.example.cm_final_proj.services;
import android.Manifest;
import android.app.IntentService;
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
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.FragmentManager;

import com.example.cm_final_proj.R;
import com.example.cm_final_proj.get_location;
import com.example.cm_final_proj.housesFragments.houses_recycler;
import com.example.cm_final_proj.model.House;
import com.example.cm_final_proj.model.house_array;
import com.example.cm_final_proj.splash;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;


public class Location_service extends Service {
    // Must create a default constructor
    Handler mhandler = new Handler();

    // Define the code block to be executed
    public static final long NOTIFY_INTERVAL = 20 * 1000; // 10 seconds
    private Timer mTimer = null;
    private int isInside =0;
    private String insideKey ="";
    Context context = this;
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

                    SharedPreferences sharedPreferences;
                    sharedPreferences= getSharedPreferences("MySharedPref",MODE_PRIVATE);
                    String s1 = sharedPreferences.getString("userID", "");

                    if(!s1.equals("")){
                        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("houses");

                        Query query = myRef
                                .orderByChild("id")
                                .equalTo(s1);



                        query.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                double lat;
                                double longi;
                                LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                                        ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                                    Location locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                                    if(locationGPS!=null){


                                        lat = locationGPS.getLatitude();
                                        longi = locationGPS.getLongitude();

                                        ArrayList<house_array> u = new ArrayList<>();
                                        for(DataSnapshot dataSn : dataSnapshot.getChildren()){
                                            //  Users value = dataSn.getValue(Users.class);
                                            u.add(new house_array(dataSn.getKey(),dataSn.getValue(House.class) ));
                                            House house = dataSn.getValue(House.class);
                                            double difLAt = Math.abs(house.latitude) -  Math.abs(lat);
                                            double difLong = Math.abs(house.longitude) -  Math.abs(longi);

                                            System.out.println("LATDIF-" + difLAt);
                                            System.out.println("LATDIF-" + difLong);
                                            if(difLAt<0.0009 && difLAt>-0.0009 && difLong<0.0009 && difLong>-0.0009){
                                                isInside = 1;
                                                System.out.println("LATDIF-" + isInside);
                                                insideKey = dataSn.getKey();
                                            }else{
                                                if(isInside==1 && insideKey.equals(dataSn.getKey())){
                                                    isInside = 0;
                                                    insideKey = "";
                                                    NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), "asd3")
                                                            .setSmallIcon(R.drawable.logo_app)
                                                            .setContentTitle("Attention your are leaving your home!! ")
                                                            .setContentText("Make sure you are carrying your medication.")
                                                            .setPriority(NotificationCompat.PRIORITY_DEFAULT);
                                                    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());

                                                    Intent notificationIntent = new Intent(context, splash.class);


                                                    PendingIntent intent = PendingIntent.getActivity(context, 0,
                                                            notificationIntent, 0);
                                                    builder.setContentIntent(intent);
                                                    // notificationId is a unique int for each notification that you must define
                                                    notificationManager.notify(1, builder.build());


                                                }
                                            }
                                        }

                                    }


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

            });
        }

    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "asd";
            String description = "asd2";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel( "asd3", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}