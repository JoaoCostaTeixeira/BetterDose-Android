package com.example.cm_final_proj.medicamentosFragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.cm_final_proj.R;
import com.example.cm_final_proj.alarmFragments.alarm_list;
import com.example.cm_final_proj.model.House;
import com.example.cm_final_proj.model.Medicamento;
import com.example.cm_final_proj.model.Medicamentos;
import com.example.cm_final_proj.model.alarm_array;
import com.example.cm_final_proj.model.alarm_model;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class add_alarm extends Fragment implements View.OnClickListener {
    View v;
    String userID = "";
    String name = "";

    ImageButton upH, downH, upM, downM, upP, downP;
    Button save;

    TextView noomeMed, hora, min, nP;


    public add_alarm() {
        // Required empty public constructor
    }

    public add_alarm(String userID, String name) {
        this.userID = userID;
        this.name = name;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_add_alarm, container, false);

        noomeMed = v.findViewById(R.id.name2);
        noomeMed.setText(name);

        nP = v.findViewById(R.id.name3);
        nP.setText("1");


        upH = v.findViewById(R.id.imageButton8);
        upM = v.findViewById(R.id.imageButton10);
        downH = v.findViewById(R.id.imageButton11);
        downM = v.findViewById(R.id.imageButton9);
        save = v.findViewById(R.id.button6);
        upP = v.findViewById(R.id.imageButton13);
        downP = v.findViewById(R.id.imageButton12);

        upH.setOnClickListener(this);
        upM.setOnClickListener(this);
        downH.setOnClickListener(this);
        downM.setOnClickListener(this);
        save.setOnClickListener(this);
        upP.setOnClickListener(this);
        downP.setOnClickListener(this);


        hora = v.findViewById(R.id.name);
        min = v.findViewById(R.id.name4);

        return v;
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.button6:
                DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("alarms");

                Query query = myRef
                        .orderByChild("uid")
                        .equalTo(userID);

                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        ArrayList<alarm_array> u = new ArrayList<>();
                        for (DataSnapshot dataSn : dataSnapshot.getChildren()) {
                            u.add(new alarm_array(dataSn.getKey(), dataSn.getValue(alarm_model.class), "", 0));
                        }

                        String keyOfAlarm = "asd";
                        if (u.size() == 0) {
                            keyOfAlarm = userID + "__" + 0;
                        } else {
                            int size = Integer.parseInt(u.get(u.size() - 1).getKey().split("__")[1]);

                            keyOfAlarm = userID + "__" + (size + 1);
                        }

                        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("alarms");
                       alarm_model m = new alarm_model();

                       m.medicamento = name;
                       m.minuto = Integer.parseInt(min.getText()+"");
                       m.hora= Integer.parseInt(hora.getText()+"");
                       m.uid = userID;
                       m.compNumb =  Integer.parseInt(nP.getText()+"");
                       Map<String, Object> map = new HashMap<>();
                       map.put(keyOfAlarm, m);
                       myRef.updateChildren(map);

                        Toast.makeText(getContext(), "Alarm added successfully!", Toast.LENGTH_SHORT).show();
                        getActivity().onBackPressed();

                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                        System.out.println("EROOORRRRRRRRR");
                    }
                });
                break;

            case R.id.imageButton8:
                int h = Integer.parseInt(hora.getText()+"");
                if(h+1 == 24){
                    hora.setText(0 + "");
                }else{
                    hora.setText(h+1 + "");
                }

                break;
            case R.id.imageButton10:
                int m = Integer.parseInt(min.getText()+"");
                if(m+1 == 60){
                    min.setText(0 + "");
                }else{
                    min.setText(m+1 + "");
                }
                break;
            case R.id.imageButton9:
                int m1 = Integer.parseInt(min.getText()+"");
                if(m1-1 == -1){
                    min.setText(59 + "");
                }else{
                    min.setText(m1-1 + "");
                }
                break;
            case R.id.imageButton11:
                int h1 = Integer.parseInt(hora.getText()+"");
                if(h1-1 == -1){
                    hora.setText(23 + "");
                }else{
                    hora.setText(h1-1 + "");
                }
                break;
            case R.id.imageButton12:
                int p = Integer.parseInt(nP.getText()+"");
                if(p-1 != 0){
                    nP.setText(p-1 + "");
                }
                break;
            case R.id.imageButton13:
                int p1 = Integer.parseInt(nP.getText()+"");
                nP.setText(p1+1 + "");

                break;
        }


    }
}