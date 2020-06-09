package com.example.cm_final_proj.medicamentosFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.cm_final_proj.R;
import com.example.cm_final_proj.model.House;
import com.example.cm_final_proj.model.Medicamento;
import com.example.cm_final_proj.model.Medicamentos;
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

public class get_med extends Fragment {
    View v;
    private int id, size;
    private String userID;
    public get_med() {
        // Required empty public constructor
    }

    public get_med(int id, String userID, int size) {
        // Required empty public constructor
        this.id = id;
        this.userID = userID;
        this.size = size;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_get_location, container, false);

        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("med_list");
        Query query = myRef
                .orderByChild("id")
                .equalTo(id);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                    ArrayList<Medicamento> u = new ArrayList<>();
                    for(DataSnapshot dataSn : dataSnapshot.getChildren()){
                        //  Users value = dataSn.getValue(Users.class);
                        u.add(dataSn.getValue(Medicamento.class));

                    }

                    if(u.size()>0){
                        qr_code n = new qr_code( u.get(0), userID, size);
                        FragmentManager manager = ((AppCompatActivity)v.getContext()).getSupportFragmentManager();
                        manager.beginTransaction()
                                .replace(R.id.fragmentmed, n)
                                .commit();
                    }else{
                        getActivity().onBackPressed();
                    }


                // This method is called once with the initial value and again
                // whenever data at this location is updated.

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                System.out.println( "EROOORRRRRRRRR");
            }
        });

        return v;
    }
}