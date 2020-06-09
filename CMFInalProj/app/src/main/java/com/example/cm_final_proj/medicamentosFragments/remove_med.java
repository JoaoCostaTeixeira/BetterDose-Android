package com.example.cm_final_proj.medicamentosFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.cm_final_proj.R;
import com.example.cm_final_proj.model.House;
import com.example.cm_final_proj.model.Medicamentos;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class remove_med extends Fragment implements View.OnClickListener {
    View v;
    String key;
    ImageButton back;
    public remove_med() {
        // Required empty public constructor
    }

    public remove_med(String key) {
        // Required empty public constructor
        this.key = key;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_remove_med, container, false);
        back = v.findViewById(R.id.removeButton2);
        back.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View view) {
        getActivity().onBackPressed();
    }


}