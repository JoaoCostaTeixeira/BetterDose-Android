package com.example.cm_final_proj;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cm_final_proj.R;
import com.example.cm_final_proj.Recycler_views.alarm_recyclerView;
import com.example.cm_final_proj.Recycler_views.alarm_service_recycler;
import com.example.cm_final_proj.Recycler_views.house_recyclerView;
import com.example.cm_final_proj.Recycler_views.medicamentos_recyclerView;
import com.example.cm_final_proj.farmaciasFragments.farmacia_webview;
import com.example.cm_final_proj.medicamentosFragments.medicamentos_list;
import com.example.cm_final_proj.model.Farmacia_model;
import com.example.cm_final_proj.Recycler_views.farmacias_recyclerView;
import com.example.cm_final_proj.model.House;
import com.example.cm_final_proj.model.Medicamento;
import com.example.cm_final_proj.model.Medicamentos;
import com.example.cm_final_proj.model.alarm_array;
import com.example.cm_final_proj.model.med_array;
import com.example.cm_final_proj.model.med_array_alarm;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class alarm_service_fragment extends Fragment implements View.OnClickListener {

    View v;
    RecyclerView mRecyclerView;
    alarm_service_recycler mAdapter;
    TextView horaView;
    ImageButton take;
    String uid, hora;

    private ArrayList<med_array_alarm> farm;
    public alarm_service_fragment() {
        // Required empty public constructor
    }

    public alarm_service_fragment(ArrayList<med_array_alarm> farm, String uid, String hora) {
        // Required empty public constructor
        this.farm = farm;
        this.uid=uid;
        this.hora = hora;

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_alarm_service_fragment, container, false);


        take = v.findViewById(R.id.imageButton14);
        take.setOnClickListener(this);

        horaView = v.findViewById(R.id.textView8);
        String [] hhh = hora.split(":");
        int m11 = Integer.parseInt(hhh[1]);
        int h11 = Integer.parseInt(hhh[0]);
        String m1 = "";
        String h1 = "";
        if(m11<10){
            m1 = "0" + m11;
        }else{
            m1 = m11 +"";
        }

        if(h11<10){
            h1 = "0" + h11;
        }else{
            h1 = h11 +"";
        }

        horaView.setText(h1 + ":" + m1);
        mRecyclerView = v.findViewById(R.id.alarmRecycler);
        mAdapter = new alarm_service_recycler(getContext(), farm);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return v;
    }

    @Override
    public void onClick(View view) {

        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference().child("medicamentos");
        for(med_array_alarm m : farm){
            if(m.getQuant()<=m.getMed().atual){
                Map<String, Object> map = new HashMap<>();
                map.put("atual", m.getMed().atual-m.getQuant());
                myRef.child(m.getKey()).updateChildren(map);
            }
        }
        NotificationManager mNotificationManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.cancel(2);

        getActivity().finish();

    }



}
