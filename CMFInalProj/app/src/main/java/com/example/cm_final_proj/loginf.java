package com.example.cm_final_proj;

import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

import static android.content.Context.MODE_PRIVATE;

public class loginf extends Fragment implements View.OnClickListener {
    private EditText email;
    private EditText password;
    private FirebaseAuth mAuth;
    View v;
    SharedPreferences sharedPreferences;

    public loginf(){


    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_loginf, container, false);
        mAuth = FirebaseAuth.getInstance();
        email= v.findViewById(R.id.emailEdit);
        sharedPreferences= this.getActivity().getSharedPreferences("MySharedPref",MODE_PRIVATE);
        password = v.findViewById(R.id.passwordEdit);
        Button b = (Button) v.findViewById(R.id.button);
        Button register = v.findViewById(R.id.register);
        b.setOnClickListener(this);
        register.setOnClickListener(this);

        return v;
    }


    @Override
    public void onClick(View v) {
        System.out.println("BUTTON CLICK");
        switch (v.getId()){
            case R.id.button:
                String s1 = "" + email.getText();
                String s2 = "" + password.getText();
                if(s1.length()>0 && s2.length()>0){
                    signin(v);
                }

                break;
            case R.id.register:
                register r = new register ();
                FragmentManager manager = ((AppCompatActivity)v.getContext()).getSupportFragmentManager();
                manager.beginTransaction()
                        .replace(R.id.fragmentfirst, r)
                        .addToBackStack(null)
                        .commit();

                break;
        }


    }

    public void signin (View view){
        mAuth.signInWithEmailAndPassword("" + email.getText(),"" + password.getText())
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            SharedPreferences.Editor myEdit= sharedPreferences.edit();
                            myEdit.putString("userID", ""+ user.getUid());
                            myEdit.commit();
                            Toast.makeText(getActivity(), "Authentication Worked.",
                                    Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getActivity(), profile_cheack.class);
                            intent.putExtra("userID", ""+user.getUid());
                            intent.putExtra("email", ""+user.getEmail());
                            startActivity(intent);

                        } else {
                            Toast.makeText(getActivity(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
