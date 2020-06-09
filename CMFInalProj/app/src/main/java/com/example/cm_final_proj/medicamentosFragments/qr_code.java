package com.example.cm_final_proj.medicamentosFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.example.cm_final_proj.R;
import com.example.cm_final_proj.model.Medicamento;
import com.example.cm_final_proj.model.Medicamentos;
import com.example.cm_final_proj.model.med_array;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class qr_code extends Fragment  implements View.OnClickListener {
    View v;
    Medicamento m;
    String userID;
    TextView name, atua;
    ImageView image;
    Button save;
    ImageButton up, down;
    int size;
    public qr_code() {
    }

    public qr_code(Medicamento m, String userID, int size) {
        this.m=m;
        this.userID=userID;
        this.size = size;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_qr_code, container, false);

        name = v.findViewById(R.id.textView7);
        name.setText(m.name);

        atua = v.findViewById(R.id.textView6);
        atua.setText(m.quantidade+"");


        save = v.findViewById(R.id.button8);
        save.setOnClickListener(this);

        up = v.findViewById(R.id.imageButton3);
        up.setOnClickListener(this);

        down = v.findViewById(R.id.imageButton2);
        down.setOnClickListener(this);

        image = v.findViewById(R.id.imageView7);
        Picasso.with(getContext())
                .load(m.image)
                .into(image);

        return v;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.imageButton3:
                int v = Integer.parseInt(""+atua.getText());
                v = v+1;
                atua.setText(v+"");
                break;
            case R.id.imageButton2:
                int v1 = Integer.parseInt(""+atua.getText());
                v1 = v1-1;
                atua.setText(v1+"");
                break;

            case R.id.button8:
                FragmentManager fm = getActivity().getSupportFragmentManager();
                for(int i = 0; i < fm.getBackStackEntryCount(); ++i) {
                    fm.popBackStack();
                }



                final DatabaseReference myRef = FirebaseDatabase.getInstance().getReference().child("medicamentos");
                Query query = myRef
                        .orderByChild("nome")
                        .equalTo(m.name);


                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        ArrayList<med_array> u = new ArrayList<>();
                        if(dataSnapshot.exists()){
                            for(DataSnapshot dataSn : dataSnapshot.getChildren()){
                                //  Users value = dataSn.getValue(Users.class);
                                Medicamentos mm = dataSn.getValue(Medicamentos.class);
                                if(mm.userid.equals(userID)){
                                    u.add(new med_array(dataSn.getKey(),mm));
                                    break;
                                }
                            }
                        }
                        if(u.size()>0){
                            Map<String, Object> map = new HashMap<>();
                            map.put("atual", u.get(u.size()-1).getMed().atual + m.quantidade);
                            myRef.child("" + u.get(u.size()-1).getKey()).updateChildren(map);
                        }else{
                            Medicamentos med = new Medicamentos();

                            String key="" + size;
                            key=userID+"__"+key;

                            med.userid = userID;
                            med.nome = m.name;
                            med.image = m.image;
                            med.quantidade = m.quantidade;
                            med.atual =  Integer.parseInt(""+atua.getText());
                            Map<String, Object> map = new HashMap<>();
                            map.put(key, med);
                            myRef.updateChildren(map);

                        }
                        // This method is called once with the initial value and again
                        // whenever data at this location is updated.

                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                        System.out.println( "ERROR");
                    }
                });
                break;
        }




    }
}