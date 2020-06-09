package com.example.cm_final_proj;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.cm_final_proj.mainMenuFragments.main_menu;

public class mainMenu extends AppCompatActivity {
    String userID, email, idade, name, sexo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainscreen);

        // speed dial number
        Intent intent = getIntent();
        userID = intent.getStringExtra("userID");
        email = intent.getStringExtra("email");
        idade = intent.getStringExtra("idade");
        name = intent.getStringExtra("name");
        sexo = intent.getStringExtra("sexo");

        System.out.println("USERID - " +userID );
        System.out.println("IDADE " + idade);
        main_menu second = new main_menu(userID,email,idade,name, sexo );
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction()
                .replace(R.id.fragmentmenu, second)
            .setCustomAnimations(R.anim.slide_up, R.anim.slide_out)
                .commit();

    }


}