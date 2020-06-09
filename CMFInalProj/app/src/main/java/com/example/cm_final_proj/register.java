package com.example.cm_final_proj;

import android.content.res.Resources;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class register extends Fragment implements View.OnClickListener {
    private EditText email;
    private EditText password;
    private FirebaseAuth mAuth;
    View v;

    public register(){}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_register, container, false);
        mAuth = FirebaseAuth.getInstance();
        email= v.findViewById(R.id.emailEdit);
        password = v.findViewById(R.id.passwordEdit);
        Button b = (Button) v.findViewById(R.id.buttonRegister);
        Button register = v.findViewById(R.id.register);
        b.setOnClickListener(this);
        register.setOnClickListener(this);
        return v;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonRegister:
                String s2 = "" + password.getText();
                if(validmail() ){
                    if(s2.length()>5){
                        signup(v);
                    }else{
                       Toast.makeText(getActivity(), "Password To Short",
                               Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getActivity(), "Invalid Email",
                            Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.register:
                FragmentManager manager = ((AppCompatActivity)v.getContext()).getSupportFragmentManager();
                manager.popBackStack();
                break;
        }

    }
    private boolean validmail(){
        String s1 = "" + email.getText();
        String [] s2 = s1.split("@");
        System.out.println("@-" + s2.length);
        if(s2.length==2){
            String [] s3 = s2[1].split("\\.");
            System.out.println( s2[1]);
            System.out.println("\\." + s3.length);
            if(s3.length==2){
                return true;
            }
        }
        return false;
    }
    public void signup (View view){
        mAuth.createUserWithEmailAndPassword("" + email.getText(), "" + password.getText())
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(getActivity(), "Register successful!!",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(getActivity(), "Register failed!!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
