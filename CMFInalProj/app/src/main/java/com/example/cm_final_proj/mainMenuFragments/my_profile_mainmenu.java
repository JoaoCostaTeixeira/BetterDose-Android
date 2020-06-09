package com.example.cm_final_proj.mainMenuFragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.cm_final_proj.Login;
import com.example.cm_final_proj.R;
import com.example.cm_final_proj.mainMenu;
import com.example.cm_final_proj.model.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

public class my_profile_mainmenu extends Fragment  implements View.OnClickListener  {

    View v;
    String userID, email, idade, name, sexo;
    TextView nameuser, emailuser, sexouser, idadeuser;;


    ConstraintLayout of;
    Button logOut;
    public my_profile_mainmenu() {
        // Required empty public constructor
    }

    public my_profile_mainmenu( String userID, String email, String idade, String name, String sexo) {
        this.userID = userID;
        this.email = email;
        this.idade = idade;
        this.name = name;
        this.sexo = sexo;


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_my_profile_mainmenu, container, false);

        logOut = v.findViewById(R.id.button5);
        of = v.findViewById(R.id.clickoff);
        logOut.setOnClickListener(this);
        of.setOnClickListener(this);

        nameuser  = v.findViewById(R.id.text5);
        nameuser.setText(this.name);

        sexouser  = v.findViewById(R.id.text7);
        sexouser.setText(this.sexo+"");

        emailuser  = v.findViewById(R.id.text6);
        emailuser.setText(this.email);

        idadeuser  = v.findViewById(R.id.text8);
        idadeuser.setText(this.idade+"");
        return v;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.clickoff:
                FragmentManager manager = ((AppCompatActivity)v.getContext()).getSupportFragmentManager();
                manager.popBackStack();
                break;
            default:
                SharedPreferences sharedPreferences= this.getActivity().getSharedPreferences("MySharedPref",MODE_PRIVATE);
                SharedPreferences.Editor myEdit= sharedPreferences.edit();
                myEdit.remove("userID");
                myEdit.commit();
                Intent intent = new Intent(getActivity(), Login.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                break;
        }

    }


}
