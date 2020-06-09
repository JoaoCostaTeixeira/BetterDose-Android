package com.example.cm_final_proj.housesFragments;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.cm_final_proj.R;
import com.example.cm_final_proj.Recycler_views.house_recyclerView;
import com.example.cm_final_proj.model.House;
import com.example.cm_final_proj.model.User;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class house_add  extends Fragment {

    private LatLng pos;
    private String name, uid;
    private int size;
    private View v;
    public house_add() {
        // Required empty public constructor
    }

    public house_add(LatLng pos, String name, String uid, int size) {
        // Required empty public constructor
        this.pos = pos;
        this.name = name;
        this.size=size;
        this.uid = uid;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_get_location, container, false);

        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference().child("houses");

        House user = new House();

        String key="" + size;
        key=uid+"__"+key;
        user.id = uid;
        user.Nome = name;
        user.latitude = pos.latitude;
        user.longitude = pos.longitude;
        Map<String, Object> map = new HashMap<>();
        map.put(key, user);
        myRef.updateChildren(map);

        FragmentManager fm = getActivity().getSupportFragmentManager();
        for(int i = 0; i < fm.getBackStackEntryCount(); ++i) {
            fm.popBackStack();
        }
        return v;
    }
}
