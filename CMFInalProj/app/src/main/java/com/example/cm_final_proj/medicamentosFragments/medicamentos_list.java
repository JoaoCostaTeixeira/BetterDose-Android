package com.example.cm_final_proj.medicamentosFragments;

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
import com.example.cm_final_proj.Recycler_views.house_recyclerView;
import com.example.cm_final_proj.Recycler_views.medicamentos_recyclerView;
import com.example.cm_final_proj.farmaciasFragments.farmacia_webview;
import com.example.cm_final_proj.model.Farmacia_model;
import com.example.cm_final_proj.Recycler_views.farmacias_recyclerView;
import com.example.cm_final_proj.model.House;
import com.example.cm_final_proj.model.Medicamentos;
import com.example.cm_final_proj.model.med_array;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class medicamentos_list extends Fragment implements View.OnClickListener {

    View v;
    RecyclerView mRecyclerView;
    medicamentos_recyclerView mAdapter;
    ImageButton add;
    String uid;
    ImageButton addb;
    private ArrayList<med_array> farm;
    public medicamentos_list() {
        // Required empty public constructor
    }

    public medicamentos_list(ArrayList<med_array> farm, String uid) {
        // Required empty public constructor
        this.farm = farm;
        this.uid=uid;

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if(farm == null || farm.size()==1){
            v = inflater.inflate(R.layout.fargment_medicamentos_empty, container, false);
            addb = v.findViewById(R.id.imageButton7);
            addb.setOnClickListener(this);

        }else{
            v = inflater.inflate(R.layout.fragment_medicamentos_list, container, false);
            Resources res = getResources();

            add = v.findViewById(R.id.imageButton4);
            add.setOnClickListener(this);
            mRecyclerView = v.findViewById(R.id.recyclerview3);
            mAdapter = new medicamentos_recyclerView(getContext(), farm);
            mRecyclerView.setAdapter(mAdapter);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        }

        return v;
    }

    @Override
    public void onClick(View view) {
        int toAdd = 0;
        if(farm !=null && farm.size()>1){
            String[] ss = farm.get(farm.size()-2).getKey().split("__");
            toAdd = Integer.parseInt(ss[1]);
        }
        add_med n = new add_med( uid, toAdd+1);
        FragmentManager manager = ((AppCompatActivity)v.getContext()).getSupportFragmentManager();
        manager.beginTransaction()
                .replace(R.id.fragmentmed, n)
                .addToBackStack(null)
                .commit();
    }



}
