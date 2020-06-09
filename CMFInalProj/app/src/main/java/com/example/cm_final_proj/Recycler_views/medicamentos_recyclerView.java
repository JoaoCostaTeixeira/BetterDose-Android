package com.example.cm_final_proj.Recycler_views;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cm_final_proj.R;
import com.example.cm_final_proj.alarmFragments.alarm_list;
import com.example.cm_final_proj.farmaciasFragments.farmacia_webview;
import com.example.cm_final_proj.medicamentosFragments.add_alarm;
import com.example.cm_final_proj.medicamentosFragments.add_med;
import com.example.cm_final_proj.medicamentosFragments.remove_med;
import com.example.cm_final_proj.model.House;
import com.example.cm_final_proj.model.Medicamentos;
import com.example.cm_final_proj.model.alarm_array;
import com.example.cm_final_proj.model.alarm_model;
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

import static java.security.AccessController.getContext;

public class medicamentos_recyclerView extends RecyclerView.Adapter<medicamentos_recyclerView.WordViewHolder> implements View.OnClickListener {
    LayoutInflater mInflater;
    ArrayList<med_array> mWordList;
    Context context;
    public medicamentos_recyclerView(Context context, ArrayList<med_array> wordList) {
        mInflater = LayoutInflater.from(context);
        this.mWordList = wordList;
        this.context = context;
    }

    @Override
    public WordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Create view from layout
        View mItemView = mInflater.inflate(
                R.layout.list_medicamentos, parent, false);

        return new WordViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(WordViewHolder holder, int position) {
        if(position == mWordList.size()-1){
            holder.c.setVisibility(View.INVISIBLE);
            holder.c.setEnabled(false);
        }else{
            // Add the data to the view
            holder.wordItemView.setText(mWordList.get(position).getMed().nome);
            holder.wordItemView2.setText("" + mWordList.get(position).getMed().atual + " pills left");

            Picasso.with(context)
                    .load(mWordList.get(position).getMed().image)
                    .into(  holder.image);

            holder.removeButton.setTag(mWordList.get(position).getKey() + ";" + mWordList.get(position).getMed().nome);
            holder.removeButton.setOnClickListener(this);


            holder.takePill.setTag(mWordList.get(position).getKey() + ";" + mWordList.get(position).getMed().atual + ";" +mWordList.get(position).getMed().nome );
            holder.takePill.setOnClickListener(this);


            holder.alarmAdd.setTag(mWordList.get(position).getMed().userid + ";" + mWordList.get(position).getMed().nome);
            holder.alarmAdd.setOnClickListener(this);

            if(mWordList.get(position).getMed().atual<6 && mWordList.get(position).getMed().atual>0 ){
                holder.c.setBackground(ContextCompat.getDrawable(context,R.drawable.rounded_corner_orange));

            }
            if(mWordList.get(position).getMed().atual==0){
                holder.c.setBackground(ContextCompat.getDrawable(context,R.drawable.rounded_corner_red));
                holder.takePill.setEnabled(false);
                holder.takePill.setVisibility(View.INVISIBLE);
            }

        }



    }

    @Override
    public void onClick(View view) {
            System.out.println("KEY: " + view.getTag());
            final String keyToRemove = "" + view.getTag();

            switch (view.getId()){
                case R.id.removeButton:
                    new AlertDialog.Builder(context)
                            .setTitle("Remove " + keyToRemove.split(";")[1]  + " and all alarms associated?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                    DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("alarms");

                                    final String[] userID = keyToRemove.split("__");
                                    Query query = myRef
                                            .orderByChild("uid")
                                            .equalTo(userID[0]);

                                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            ArrayList<String> arr = new ArrayList<>();
                                            String [] n = userID[1].split(";");
                                            for(DataSnapshot dataSn : dataSnapshot.getChildren()){
                                                //  Users value = dataSn.getValue(Users.class);
                                                alarm_model m = dataSn.getValue(alarm_model.class);

                                                if(m.medicamento.equals(n[1])){
                                                    arr.add(dataSn.getKey());
                                                }
                                            }
                                            DatabaseReference myRef2 = FirebaseDatabase.getInstance().getReference("alarms");
                                            if(arr.size()>0){
                                                for(String s : arr){
                                                    myRef2.child(s).removeValue();
                                                }
                                            }

                                        }

                                        @Override
                                        public void onCancelled(DatabaseError error) {
                                            // Failed to read value
                                            System.out.println( "EROOORRRRRRRRR");
                                        }
                                    });

                                    String[] rr = keyToRemove.split(";");
                                    DatabaseReference myRef3 = FirebaseDatabase.getInstance().getReference().child("medicamentos");
                                    myRef3.child("" + rr[0]).removeValue();




                                }
                            })
                            // remove speed dial
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .setIcon(R.drawable.logo_app)
                            .show();
                    break;

                case R.id.takePillButton:

                    new AlertDialog.Builder(context)
                        .setTitle("Take a pill of " + keyToRemove.split(";")[2] + "?")
                        // call using speed dial
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                String [] ss = keyToRemove.split(";");
                                DatabaseReference myRef = FirebaseDatabase.getInstance().getReference().child("medicamentos");
                                Map<String, Object> map = new HashMap<>();
                                map.put("atual", Integer.parseInt(ss[1])-1);
                                myRef.child("" + ss[0]).updateChildren(map);
                            }
                        })
                        // remove speed dial
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setIcon(R.drawable.logo_app)
                        .show();


                    break;

                case R.id.alarmButton:
                    String [] ss1 = keyToRemove.split(";");

                    add_alarm n = new add_alarm( ss1[0], ss1[1]);
                    FragmentManager manager = ((AppCompatActivity)view.getContext()).getSupportFragmentManager();
                    manager.beginTransaction()
                            .replace(R.id.fragmentmed, n)
                            .addToBackStack(null)
                            .commit();


                    break;


            }


    }

    @Override
    public int getItemCount() {
        // Return the number of data items to display
        return mWordList.size();
    }


    class WordViewHolder extends RecyclerView.ViewHolder  {
        TextView wordItemView;
        TextView wordItemView2;
        ImageView image;
        ImageButton removeButton;
        ImageButton takePill;
        ImageButton alarmAdd;
        medicamentos_recyclerView mAdapter;

        ConstraintLayout c;
        public WordViewHolder(View itemView, medicamentos_recyclerView adapter) {
            super(itemView);
            // Get the layout
            wordItemView = itemView.findViewById(R.id.textView);
            wordItemView2 = itemView.findViewById(R.id.textView2);
            image = itemView.findViewById(R.id.imageView8);
            removeButton = itemView.findViewById(R.id.removeButton);
            takePill = itemView.findViewById(R.id.takePillButton);
            alarmAdd = itemView.findViewById(R.id.alarmButton);
            c = itemView.findViewById(R.id.cc);
            // Associate with this adapter
            this.mAdapter = adapter;
            // Add click listener, if desired
        }



    }
}