package com.example.cm_final_proj.housesFragments;

import android.app.ActivityManager;
import android.content.Context;
import android.content.res.Resources;
import android.media.Image;
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
import com.example.cm_final_proj.farmaciasFragments.farmacia_webview;
import com.example.cm_final_proj.model.Farmacia_model;
import com.example.cm_final_proj.Recycler_views.farmacias_recyclerView;
import com.example.cm_final_proj.model.House;
import com.example.cm_final_proj.model.house_array;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class houses_recycler extends Fragment implements View.OnClickListener {

    View v;
    RecyclerView mRecyclerView;
    house_recyclerView mAdapter;
    ImageButton add;
    String uid;
    private ArrayList<house_array> farm;
    public houses_recycler() {
        // Required empty public constructor
    }

    public houses_recycler(ArrayList<house_array> farm, String uid) {
        // Required empty public constructor
        this.farm = farm;
        this.uid=uid;

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(farm.size() ==0){
            v = inflater.inflate(R.layout.fragment_no_houses, container, false);
            add = v.findViewById(R.id.imageButton6);
            add.setOnClickListener(this);
        }else{
            v = inflater.inflate(R.layout.fragment_houses_recycler, container, false);
            Resources res = getResources();

            add = v.findViewById(R.id.imageButton5);
            add.setOnClickListener(this);
            mRecyclerView = v.findViewById(R.id.recyclerview2);
            mAdapter = new house_recyclerView(getContext(), farm);
            mRecyclerView.setAdapter(mAdapter);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        }

        return v;
    }

    @Override
    public void onClick(View view) {
        FragmentManager manager = ((AppCompatActivity)view.getContext()).getSupportFragmentManager();

        int toAdd = 0;
        if(farm !=null && farm.size()>1){
            String[] ss = farm.get(farm.size()-2).getKey().split("__");
            toAdd = Integer.parseInt(ss[1]);
        }



        map second = new map(uid,toAdd +1);

        manager.beginTransaction()
                .replace(R.id.fragmenthouse, second)
                .addToBackStack(null)
                .commit();


    }
}
