package com.example.cm_final_proj;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.cm_final_proj.medicamentosFragments.medicamentos_list;
import com.example.cm_final_proj.model.Medicamentos;
import com.example.cm_final_proj.model.med_array;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class medicamentos_Acrivity extends AppCompatActivity {

    String userID;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.medicamentos_layout);


        Intent intent = getIntent();
        userID = intent.getStringExtra("userID");
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("medicamentos");

        Query query = myRef
                .orderByChild("userid")
                .equalTo(userID);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                    ArrayList<med_array> u = new ArrayList<>();
                    for(DataSnapshot dataSn : dataSnapshot.getChildren()){
                        //  Users value = dataSn.getValue(Users.class);
                        u.add(new med_array(dataSn.getKey(), dataSn.getValue(Medicamentos.class)));
                    }

                    u.add(null);
                    medicamentos_list n = new medicamentos_list(u, userID);
                    FragmentManager manager = getSupportFragmentManager();

                    manager.beginTransaction()
                            .replace(R.id.fragmentmed, n)
                             .commitAllowingStateLoss();


                // This method is called once with the initial value and again
                // whenever data at this location is updated.

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                System.out.println( "EROOORRRRRRRRR");
            }
        });

    }

}
