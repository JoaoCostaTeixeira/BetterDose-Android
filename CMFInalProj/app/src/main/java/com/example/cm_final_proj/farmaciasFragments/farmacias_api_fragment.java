package com.example.cm_final_proj.farmaciasFragments;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cm_final_proj.R;
import com.example.cm_final_proj.model.Farmacia_model;
import com.example.cm_final_proj.Recycler_views.farmacias_recyclerView;

import java.util.ArrayList;

public class farmacias_api_fragment extends Fragment {

    View v;
    RecyclerView mRecyclerView;
    farmacias_recyclerView mAdapter;
    private ArrayList<Farmacia_model> farm;
    public farmacias_api_fragment() {
        // Required empty public constructor
    }

    public farmacias_api_fragment(ArrayList<Farmacia_model> farm) {
        // Required empty public constructor
        this.farm = farm;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_farmacias_api_fragment, container, false);
        Resources res = getResources();
        String[] city = new String [4];
        city[0] = "OLA";
        city[1] = "SWAG";
        city[2] = "OLOL";
        city[3] = "OLO2L";
        mRecyclerView = v.findViewById(R.id.recyclerview);
        mAdapter = new farmacias_recyclerView(getContext(), farm);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return v;
    }

}
