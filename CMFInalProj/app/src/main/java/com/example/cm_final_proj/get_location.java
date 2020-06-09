package com.example.cm_final_proj;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.cm_final_proj.database.Farmacias;
import com.example.cm_final_proj.farmaciasFragments.farmacias_api_fragment;
import com.example.cm_final_proj.farmaciasFragments.sem_Farmacias;
import com.example.cm_final_proj.model.Farmacia_model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class get_location extends Fragment  {

    View v;
    LocationManager locationManager;
    String latitude, longitude;
    Farmacias farm_db;
    ArrayList<Farmacia_model> farm_list;
    public get_location() {
        // Required empty public constructor


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_get_location, container, false);
        requestPermissions(new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION,
                        android.Manifest.permission.ACCESS_FINE_LOCATION},1);


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
        farm_db = new Farmacias(getContext());
        return v;
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
                getContext(),Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            Location locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (locationGPS != null) {
                double lat = locationGPS.getLatitude();
                double longi = locationGPS.getLongitude();
                latitude = String.valueOf(lat);
                longitude = String.valueOf(longi);
                new MyAsyncTask().execute();
            } else {
                Cursor res = farm_db.getAllData();
                farm_list = new ArrayList<>();
                while(res.moveToNext()){
                    farm_list.add(new Farmacia_model(res.getInt(0), res.getString(1), res.getString(2)));
                }

                if(farm_list.isEmpty()){
                    sem_Farmacias sem = new sem_Farmacias();
                    FragmentManager manager = ((AppCompatActivity)v.getContext()).getSupportFragmentManager();
                    manager.beginTransaction()
                            .replace(R.id.fragmentsecond, sem)
                            .commit();
                }else{
                    farmacias_api_fragment farm = new farmacias_api_fragment(farm_list);
                    FragmentManager manager = ((AppCompatActivity)v.getContext()).getSupportFragmentManager();
                    manager.beginTransaction()
                            .replace(R.id.fragmentsecond, farm)
                            .commit();
                }
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

    private class MyAsyncTask extends AsyncTask< Void, Void, Void>{


        @Override
        protected Void doInBackground(Void... voids) {
                getFarm();
                return null;
        }



        private void getFarm() {
            String charset = "UTF-8";
            final String URL2 = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="+latitude + ','+longitude +"&rankby=distance&language=pt-PT&types=pharmacy&key=AIzaSyAJWnpsN6ex46vpLXYE_A8qeuo776cgHsA";
            HttpURLConnection connection = null;
            try {

                URL url = new URL(URL2);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestProperty("Accept-Charset", charset);
                connection.setRequestMethod("GET");

                BufferedReader in = new BufferedReader(
                        new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }


                in.close();
                JSONObject parser = new JSONObject(response.toString());
                JSONArray result = parser.getJSONArray("results");

                if(result.length()>0){
                    farm_db.clearData();
                    for(int i=0; i<result.length(); i++){
                        farm_db.insertData(result.getJSONObject(i).getString("name"),result.getJSONObject(i).getString("vicinity") );
                    }
                }



                Cursor res = farm_db.getAllData();
                farm_list = new ArrayList<>();
                while(res.moveToNext()){
                    farm_list.add(new Farmacia_model(res.getInt(0), res.getString(1), res.getString(2)));
                }

                if(farm_list.isEmpty()){
                    sem_Farmacias sem = new sem_Farmacias();
                    FragmentManager manager = ((AppCompatActivity)v.getContext()).getSupportFragmentManager();
                    manager.beginTransaction()
                            .replace(R.id.fragmentsecond, sem)
                            .commit();
                }else{
                    farmacias_api_fragment farm = new farmacias_api_fragment(farm_list);
                    FragmentManager manager = ((AppCompatActivity)v.getContext()).getSupportFragmentManager();
                    manager.beginTransaction()
                            .replace(R.id.fragmentsecond, farm)
                            .commit();
                }



            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }

        }
    }

}


