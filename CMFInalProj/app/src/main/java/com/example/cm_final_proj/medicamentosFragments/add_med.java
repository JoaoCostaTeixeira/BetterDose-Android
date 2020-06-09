package com.example.cm_final_proj.medicamentosFragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.cm_final_proj.R;
import com.example.cm_final_proj.model.House;
import com.example.cm_final_proj.model.Medicamento;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class add_med extends Fragment implements View.OnClickListener{
    View v;
    String userID;
    ImageButton b;
    Button save;
    EditText name;
    EditText numPills;
    int size;
    public add_med() {
        // Required empty public constructor
    }

    public add_med(   String userID, int size) {
        // Required empty public constructor
        this.size = size;
        this.userID = userID;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_add_med, container, false);
        b = v.findViewById(R.id.imageButton);
        save = v.findViewById(R.id.button11);
        name = v.findViewById(R.id.editText2);
        numPills = v.findViewById(R.id.editText3);

        b.setOnClickListener(this);
        save.setOnClickListener(this);
        return v;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imageButton:
                try {

                    Intent intent = new Intent("com.google.zxing.client.android.SCAN");
                    //intent.putExtra("SCAN_MODE", "QR_CODE_MODE"); // "PRODUCT_MODE for bar codes

                    startActivityForResult(intent, 0);

                } catch (Exception e) {

                    Uri marketUri = Uri.parse("market://details?id=com.google.zxing.client.android");
                    Intent marketIntent = new Intent(Intent.ACTION_VIEW,marketUri);
                    startActivity(marketIntent);

                }
                break;
            case R.id.button11:
                String n = ""+name.getText();
                String s = ""+numPills.getText();

                if(n.length()!=0 && s.length()!=0){
                    Medicamento m = new Medicamento();
                    m.quantidade = Integer.parseInt(s);
                    m.name = n;
                    m.image = "https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/once-a-month-contraceptive-pill-1575631448.jpg";
                    m.id=-1;
                    qr_code n1 = new qr_code( m, userID, size);
                    FragmentManager manager = ((AppCompatActivity)v.getContext()).getSupportFragmentManager();
                    manager.beginTransaction()
                            .replace(R.id.fragmentmed, n1)
                            .commit();
                }

                break;

        }

    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        // perform your action here
        if (requestCode == 0) {

            if (resultCode == RESULT_OK) {
                String contents = intent.getStringExtra("SCAN_RESULT");
                System.out.println(contents);

                get_med n = new get_med( Integer.parseInt(contents), userID, size);
                FragmentManager manager = ((AppCompatActivity)v.getContext()).getSupportFragmentManager();
                manager.beginTransaction()
                        .replace(R.id.fragmentmed, n)
                        .commit();
            }
            if(resultCode == RESULT_CANCELED){
                //handle cancel
            }
        }
    }
}