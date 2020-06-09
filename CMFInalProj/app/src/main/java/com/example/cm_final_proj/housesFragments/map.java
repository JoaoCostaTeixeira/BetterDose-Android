package com.example.cm_final_proj.housesFragments;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cm_final_proj.R;
import com.example.cm_final_proj.Recycler_views.house_recyclerView;
import com.example.cm_final_proj.database.Farmacias;
import com.example.cm_final_proj.get_location;
import com.example.cm_final_proj.model.Farmacia_model;
import com.example.cm_final_proj.Recycler_views.farmacias_recyclerView;
import com.example.cm_final_proj.model.House;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class map extends Fragment implements OnMapReadyCallback,GoogleMap.OnMapClickListener, View.OnClickListener {

    View v;
    private static final float DEFAULT_ZOOM = 15f;
    private GoogleMap mMap;
    private Button add;
    private TextView name;
    private String uid;
    private int size;
    private LatLng pos;
    public map() {
        // Required empty public constructor
    }

    public map(String uid, int size) {
        this.uid = uid;
        this.size=size;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_map, container, false);
        add = v.findViewById(R.id.addHouses);
        add.setVisibility(View.INVISIBLE);
        add.setEnabled(false);
        add.setOnClickListener(this);
        name = v.findViewById(R.id.name);
        name.setEnabled(false);
        name.setVisibility(View.INVISIBLE);
        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        return v;
    }

    LocationManager locationManager;
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        mMap.setOnMapClickListener(this);


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        requestPermissions(new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION,
                android.Manifest.permission.ACCESS_FINE_LOCATION},1);

        locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);

    }
    Marker m1 = null;
    private void moveCamera(LatLng latLng, float zoom){
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(latLng)
                .zoom(DEFAULT_ZOOM)
                .build();
        if(m1!=null){
            m1.remove();
        }
         m1= mMap.addMarker(new MarkerOptions().position(latLng)
                .title("Your House"));
        CameraUpdate cu = CameraUpdateFactory.newCameraPosition(cameraPosition);
        mMap.animateCamera(cu);

        add.setVisibility(View.VISIBLE);
        add.setEnabled(true);
        name.setVisibility(View.VISIBLE);
        name.setEnabled(true);

    }


    @Override
        public void onMapClick(LatLng latLng) {

            moveCamera(latLng, DEFAULT_ZOOM);
            pos=latLng;
            Toast.makeText(
                    getActivity(),
                    "Lat : " + latLng.latitude + " , "
                            + "Long : " + latLng.longitude,
                    Toast.LENGTH_LONG).show();

        }

    @Override
    public void onClick(View view) {
        FragmentManager manager = ((AppCompatActivity)view.getContext()).getSupportFragmentManager();


        house_add second = new house_add(pos, ""+name.getText(), uid, size);

        manager.beginTransaction()
                .replace(R.id.fragmenthouse, second)
                .addToBackStack(null)
                .commit();


    }


    private void OnGPS() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Enable GPS").setCancelable(false).setPositiveButton("Yes", new  DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                getLocation();
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();

            }
        });
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void getLocation() {

        if (ActivityCompat.checkSelfPermission(
                getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            Location locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (locationGPS != null) {
                double lat = locationGPS.getLatitude();
                double longi = locationGPS.getLongitude();
                LatLng pos1 = new LatLng(lat, longi);
                moveCamera(pos1, DEFAULT_ZOOM);

            } else {
                Toast.makeText(getContext(), "Unable to find location.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode,
            String[] permissions,
            int[] grantResults
    ){
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            OnGPS();
        } else {
            getLocation();
        }
    }
}
