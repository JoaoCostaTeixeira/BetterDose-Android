package com.example.cm_final_proj.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.cm_final_proj.R;
import com.example.cm_final_proj.mainMenu;
import com.example.cm_final_proj.model.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class create_profile extends Fragment implements View.OnClickListener {

    View v;
    String userEmail="";
    String userId="";
    TextView email, name, age;
    Spinner spinner;
    Button b;
    public create_profile() {
        // Required empty public constructor
    }

    public create_profile(String userEmail, String userId) {
        this.userEmail = userEmail;
        this.userId = userId;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_create_profile, container, false);
        spinner = v. findViewById(R.id.choseSex);

        email = v.findViewById(R.id.emailEdit3);
        name = v.findViewById(R.id.editName);
        age = v.findViewById(R.id.editText);

        email.setText(userEmail);

        String[] plants = new String[]{
                "Male",
                "Female",
                "Other",
        };

        // Initializing an ArrayAdapter
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                getContext(),R.layout.spinner_itm,plants
        );
        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_itm);
        spinner.setAdapter(spinnerArrayAdapter);
        spinner.setPrompt("Sex");

        b = v.findViewById(R.id.button4);
        b.setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View v) {
        String s1 = "" + email.getText();
        String s2 = "" + name.getText();
        String s3 = "" + age.getText();
        String s4 ="" + spinner.getSelectedItem();
        System.out.println(s1 + "\n" + s2 + "\n" + s3 + "\n" + s4 );

        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference().child("users");

        User user = new User();
        user.email = s1;
        user.id = userId;
        user.idade = Integer.parseInt(s3);
        user.name = s2;
        user.sexo = s4;
        Map<String, Object> map = new HashMap<>();
        map.put(userId, user);
        myRef.updateChildren(map);

        Intent intent = new Intent(getActivity(), mainMenu.class);
        intent.putExtra("user", userId);
        intent.putExtra("email", user.email);
        intent.putExtra("idade", user.idade);
        intent.putExtra("name", user.name);
        intent.putExtra("sexo", user.sexo);

        startActivity(intent);
    }

}
