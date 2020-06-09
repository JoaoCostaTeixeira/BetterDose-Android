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

import java.util.ArrayList;
import java.util.List;

public class sem_Farmacias extends Fragment {

    View v;

    private ArrayList<Farmacia_model> farm;
    public sem_Farmacias() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_sem__farmacias, container, false);

        return v;
    }

}
