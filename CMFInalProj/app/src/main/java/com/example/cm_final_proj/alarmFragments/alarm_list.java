package com.example.cm_final_proj.alarmFragments;

import android.app.Activity;
import android.app.ActivityManager;
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

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cm_final_proj.R;
import com.example.cm_final_proj.Recycler_views.alarm_recyclerView;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class alarm_list extends Fragment{

    View v;
    RecyclerView mRecyclerView;
    alarm_recyclerView mAdapter;
    ImageButton add;
    String uid;
    ImageButton addb;
    private ArrayList<alarm_array> farm;
    public alarm_list() {
        // Required empty public constructor
    }

    public alarm_list(ArrayList<alarm_array> farm, String uid) {
        // Required empty public constructor
        this.farm = farm;
        this.uid=uid;

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if(farm == null || farm.size()==1){
            v = inflater.inflate(R.layout.fargment_alarm_empty, container, false);

        }else{
            v = inflater.inflate(R.layout.fragment_alarm_list, container, false);
            Resources res = getResources();

            DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("medicamentos");

            Query query = myRef
                    .orderByChild("userid")
                    .equalTo(uid);

            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    ArrayList<med_array> u = new ArrayList<>();
                    for(DataSnapshot dataSn : dataSnapshot.getChildren()){
                        //  Users value = dataSn.getValue(Users.class);
                        u.add(new med_array(dataSn.getKey(), dataSn.getValue(Medicamentos.class)));
                    }

                    for(alarm_array a : farm){
                        for(med_array m : u){
                            if(a!=null && a.getAlarm().medicamento.equals(m.getMed().nome)){
                                a.setImage(m.getMed().image);
                                a.setNum(m.getMed().atual);
                            }
                        }
                    }

                    ArrayList<alarm_array> alarm_lit = new ArrayList<>();

                    while (farm.size()!=1){
                        int index=0;
                        int hora=0;
                        int minuto=0;
                        for(int i=0; i<farm.size()-1; i++){
                            if(i==0){
                                index=0;
                                hora = farm.get(i).getAlarm().hora;
                                minuto = farm.get(i).getAlarm().minuto;

                            }else{
                                if(index != i ){
                                    if(hora> farm.get(i).getAlarm().hora){
                                        index = i;
                                        hora = farm.get(i).getAlarm().hora;
                                        minuto = farm.get(i).getAlarm().minuto;
                                    }else if(hora== farm.get(i).getAlarm().hora){
                                        if(minuto> farm.get(i).getAlarm().minuto){
                                            index = i;
                                            hora = farm.get(i).getAlarm().hora;
                                            minuto = farm.get(i).getAlarm().minuto;
                                        }
                                    }
                                }
                            }
                        }
                        alarm_lit.add( farm.remove(index));
                    }
                    alarm_lit.add(null);
                    mRecyclerView = v.findViewById(R.id.recyclerview3);
                    mAdapter = new alarm_recyclerView(getContext(), alarm_lit);
                    mRecyclerView.setAdapter(mAdapter);
                    mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    System.out.println( "EROOORRRRRRRRR");
                }
            });




        }

        return v;
    }


}
