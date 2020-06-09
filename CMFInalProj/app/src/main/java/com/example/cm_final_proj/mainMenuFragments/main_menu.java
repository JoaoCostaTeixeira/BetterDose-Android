package com.example.cm_final_proj.mainMenuFragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.cm_final_proj.R;
import com.example.cm_final_proj.alarm_activity;
import com.example.cm_final_proj.farmacias_api;
import com.example.cm_final_proj.house_activity;
import com.example.cm_final_proj.mainMenu;
import com.example.cm_final_proj.medicamentos_Acrivity;
import com.example.cm_final_proj.register;

public class main_menu extends Fragment implements View.OnClickListener{

    View v;
    ConstraintLayout house,pharmacy,my_profile, medicamentos, alarm;
    TextView nameuser;

    String userID, email, idade, name, sexo;
    public main_menu() {
        // Required empty public constructor
    }

    public main_menu( String userID, String email, String idade, String name, String sexo) {
        this.userID = userID;
        this.email = email;
        this.idade = idade;
        this.name = name;
        this.sexo = sexo;

        System.out.println("USERID - " +userID );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_main_menu, container, false);

        house = v.findViewById(R.id.constraintLayout2);
        pharmacy = v.findViewById(R.id.constraintLayout);
        my_profile = v.findViewById(R.id.constraintLayoutProfile);
        medicamentos = v.findViewById(R.id.constraintLayout4);
        alarm = v.findViewById(R.id.constraintLayout3);
        house.setOnClickListener(this);
        pharmacy.setOnClickListener(this);
        my_profile.setOnClickListener(this);
        medicamentos.setOnClickListener(this);
        alarm.setOnClickListener(this);

        nameuser  = v.findViewById(R.id.text5);
        nameuser.setText(this.name);


        return v;
    }

    @Override
    public void onClick(View v) {
        System.out.println("BUTTON CLICK");
        switch (v.getId()){
            case R.id.constraintLayoutProfile:
                System.out.println("USERID - " +this.userID  );
                my_profile_mainmenu r = new my_profile_mainmenu (userID,email,idade,name, sexo);
                FragmentManager manager = ((AppCompatActivity)v.getContext()).getSupportFragmentManager();
                manager.beginTransaction()
                        .replace(R.id.fragmentmenu, r)
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.constraintLayout:
                System.out.println("USERID - " +this.userID );
                Intent intent = new Intent(getActivity(), farmacias_api.class);
                intent.putExtra("userID", ""+userID);
                startActivity(intent);
                break;
            case R.id.constraintLayout2:
                System.out.println("USERID - " +this.userID  );
                Intent intent2 = new Intent(getActivity(), house_activity.class);
                intent2.putExtra("userID", ""+userID);
                startActivity(intent2);
                break;
            case R.id.constraintLayout4:

                System.out.println("USERID - " +this.userID  );
                Intent intent3 = new Intent(getActivity(), medicamentos_Acrivity.class);
                intent3.putExtra("userID", ""+userID);
                startActivity(intent3);
                break;

            case R.id.constraintLayout3:

                System.out.println("USERID - " +this.userID  );
                Intent intent4 = new Intent(getActivity(), alarm_activity.class);
                intent4.putExtra("userID", ""+userID);
                startActivity(intent4);
                break;

        }


    }

}
