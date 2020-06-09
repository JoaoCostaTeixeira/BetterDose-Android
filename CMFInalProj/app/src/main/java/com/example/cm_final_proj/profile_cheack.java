package com.example.cm_final_proj;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.cm_final_proj.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import static java.security.AccessController.getContext;

public class profile_cheack extends AppCompatActivity {

    String userID;
    String useremail="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_checker);

        Intent intent = getIntent();
        userID = intent.getStringExtra("userID");
        useremail = intent.getStringExtra("email");

        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("users");

        Query query = myRef
                .orderByChild("id")
                .equalTo(userID);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    User user = null;
                    for(DataSnapshot dataSn : dataSnapshot.getChildren()){
                        user = dataSn.getValue(User.class);
                    }
                    System.out.println("IDADE1212 " + user.idade);

                    Intent intent = new Intent(profile_cheack.this, mainMenu.class);
                    intent.putExtra("userID", userID);
                    intent.putExtra("email", user.email);
                    intent.putExtra("idade", ""+user.idade);
                    intent.putExtra("name", user.name);
                    intent.putExtra("sexo", user.sexo);

                    startActivity(intent);

                }else{
                    FragmentManager manager = getSupportFragmentManager();

                    no_profile second = new no_profile(useremail,userID);

                    manager.beginTransaction()
                            .replace(R.id.fragmentthird, second)
                            .addToBackStack(null)
                            .commit();

                }
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
