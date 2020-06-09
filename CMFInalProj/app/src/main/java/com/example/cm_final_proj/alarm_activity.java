package com.example.cm_final_proj;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.cm_final_proj.alarmFragments.alarm_list;
import com.example.cm_final_proj.housesFragments.houses_recycler;
import com.example.cm_final_proj.medicamentosFragments.medicamentos_list;
import com.example.cm_final_proj.model.House;
import com.example.cm_final_proj.model.Medicamentos;
import com.example.cm_final_proj.model.alarm_array;
import com.example.cm_final_proj.model.alarm_model;
import com.example.cm_final_proj.model.house_array;
import com.example.cm_final_proj.model.med_array;
import com.example.cm_final_proj.services.Alarm_service;
import com.example.cm_final_proj.services.Location_service;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class alarm_activity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    String userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarms);

        if(!isMyServiceRunning(Alarm_service.class)){
           startService(new Intent(this, Alarm_service.class));
        }
        Intent intent = getIntent();
        userID = intent.getStringExtra("userID");
        System.out.println("USER ID - " + userID);

        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("alarms");

        Query query = myRef
                .orderByChild("uid")
                .equalTo(userID);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                ArrayList<alarm_array> u = new ArrayList<>();
                for(DataSnapshot dataSn : dataSnapshot.getChildren()){
                    //  Users value = dataSn.getValue(Users.class);
                    DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("medicamentos");
                    u.add(new alarm_array(dataSn.getKey(),dataSn.getValue(alarm_model.class), "", 0 ));
                }

                u.add(null);
                alarm_list n = new alarm_list(u, userID);
                FragmentManager manager = getSupportFragmentManager();
                manager.beginTransaction()
                        .replace(R.id.fragmentAlarm, n)
                        .commitAllowingStateLoss();



                // This method is called once with the initial value and again
                // whenever data at this location is updated.

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                System.out.println( "EROOORRRRRRRRR");
            }
        });

    }


    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }


}