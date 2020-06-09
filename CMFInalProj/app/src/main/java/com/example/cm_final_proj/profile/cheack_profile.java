package com.example.cm_final_proj.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;

import com.example.cm_final_proj.R;

public class cheack_profile extends Fragment {

    View v;
    String uid="";
    public cheack_profile() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_cheack_profile, container, false);

        return v;
    }

}
