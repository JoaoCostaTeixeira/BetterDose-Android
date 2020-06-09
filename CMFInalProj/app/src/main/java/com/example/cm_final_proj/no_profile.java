package com.example.cm_final_proj;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.cm_final_proj.profile.create_profile;

public class no_profile extends Fragment implements View.OnClickListener {

    View v;
    String uid="";
    Button b;
    String userEmail;
    String userId;
    public no_profile() {
        // Required empty public constructor
    }

    public no_profile(String userEmail, String userId) {
        // Required empty public constructor
        this.userEmail=userEmail;
        this.userId=userId;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_no_profile, container, false);
        b = v.findViewById(R.id.button3);
        b.setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View v) {
        create_profile p = new create_profile(userEmail, userId);
        FragmentManager manager = ((AppCompatActivity)v.getContext()).getSupportFragmentManager();
        manager.beginTransaction()
                .replace(R.id.fragmentthird, p)
                .commit();

    }

}
