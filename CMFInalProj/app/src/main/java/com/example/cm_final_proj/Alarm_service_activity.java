package com.example.cm_final_proj;

import android.app.ActivityManager;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cm_final_proj.Recycler_views.alarm_recyclerView;
import com.example.cm_final_proj.Recycler_views.alarm_service_recycler;
import com.example.cm_final_proj.alarmFragments.alarm_list;
import com.example.cm_final_proj.housesFragments.houses_recycler;
import com.example.cm_final_proj.medicamentosFragments.medicamentos_list;
import com.example.cm_final_proj.model.House;
import com.example.cm_final_proj.model.Medicamentos;
import com.example.cm_final_proj.model.alarm_array;
import com.example.cm_final_proj.model.alarm_model;
import com.example.cm_final_proj.model.house_array;
import com.example.cm_final_proj.model.med_array;
import com.example.cm_final_proj.model.med_array_alarm;
import com.example.cm_final_proj.services.Location_service;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Alarm_service_activity extends AppCompatActivity {


    ArrayList<med_array_alarm> u;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_service);

        u = new ArrayList<>();
        System.out.println("FUI CHAMADA");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            // For newer than Android Oreo: call setShowWhenLocked, setTurnScreenOn
            setShowWhenLocked(true);
            setTurnScreenOn(true);

            // If you want to display the keyguard to prompt the user to unlock the phone:
            KeyguardManager keyguardManager = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
            keyguardManager.requestDismissKeyguard(this, null);
        } else {
            getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                            | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                            | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                            | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        }
         Intent intent = getIntent();

        final String [] names = intent.getStringExtra("names").split(";");
        final String [] quant = intent.getStringExtra("quant").split(";");

        int [] q = new int [quant.length];

        for(int k = 0; k<quant.length;k++){
            q[k] = Integer.parseInt(quant[k]);
        }

        final int [] quanti = q;
        final String hora = intent.getStringExtra("hora");
        final String uid = intent.getStringExtra("uid");


        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("medicamentos");


        Query query = myRef
                .orderByChild("userid")
                .equalTo(uid);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                for(DataSnapshot dataSn : dataSnapshot.getChildren()){
                    Medicamentos m = dataSn.getValue(Medicamentos.class);
                    for(int j = 0; j<names.length; j++){
                        String n = names[j];
                        if(n.equals(m.nome)){
                            u.add(new med_array_alarm(dataSn.getKey(),m, (quanti[j])));
                        }
                    }
                }

                System.out.println("SIZE-" + u.size());
                alarm_service_fragment n = new alarm_service_fragment(u, uid, hora);
                FragmentManager manager = getSupportFragmentManager();
                manager.beginTransaction()
                        .replace(R.id.fragmentAlarm_service, n)
                        .commitAllowingStateLoss();
            }



            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                System.out.println( "EROOORRRRRRRRR");
            }
        });


    }


}