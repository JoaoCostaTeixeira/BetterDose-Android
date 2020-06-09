package com.example.cm_final_proj;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class splash extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen);

        sharedPreferences= this.getSharedPreferences("MySharedPref",MODE_PRIVATE);
        String s1 = sharedPreferences.getString("userID", "");

        if(s1.equals("")){
            Intent intent = new Intent(splash.this, Login.class);
            startActivity(intent);
        }else{
            System.out.println("USERID - " +s1 );
            Intent intent = new Intent(splash.this, profile_cheack.class);
            intent.putExtra("userID", ""+s1);
            startActivity(intent);
        }
    }

}