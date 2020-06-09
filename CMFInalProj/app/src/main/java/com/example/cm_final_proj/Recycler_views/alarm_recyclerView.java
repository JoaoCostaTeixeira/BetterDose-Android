package com.example.cm_final_proj.Recycler_views;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cm_final_proj.R;
import com.example.cm_final_proj.farmaciasFragments.farmacia_webview;
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

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static java.security.AccessController.getContext;

public class alarm_recyclerView extends RecyclerView.Adapter<alarm_recyclerView.WordViewHolder> implements View.OnClickListener {
    LayoutInflater mInflater;
    ArrayList<alarm_array> mWordList;
    Context context;
    public alarm_recyclerView(Context context, ArrayList<alarm_array> wordList) {
        mInflater = LayoutInflater.from(context);
        this.mWordList = wordList;
        this.context = context;
    }

    @Override
    public WordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Create view from layout
        View mItemView = mInflater.inflate(
                R.layout.listalarm, parent, false);

        return new WordViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(WordViewHolder holder, int position) {
        if(position == mWordList.size()-1){
            holder.c.setVisibility(View.INVISIBLE);
            holder.c.setEnabled(false);
        }else{



            holder.nameMed.setText(mWordList.get(position).getAlarm().medicamento + "");
            int m11 = mWordList.get(position).getAlarm().minuto;
            int h11 = mWordList.get(position).getAlarm().hora;

            String m1 = "";
            String h1 = "";
            if(m11<10){
                m1 = "0" + m11;
            }else{
                m1 = m11 +"";
            }

            if(h11<10){
                h1 = "0" + h11;
            }else{
                h1 = h11 +"";
            }
            holder.hora.setText(h1 + ":" + m1);
            holder.remove.setTag(mWordList.get(position).getKey());
            holder.remove.setOnClickListener(this);

            holder.numP.setText(mWordList.get(position).getAlarm().compNumb+"");
            if(mWordList.get(position).getImage()!=""){
                Picasso.with(context)
                        .load(mWordList.get(position).getImage())
                        .into(  holder.image);
            }

        }



    }

    @Override
    public void onClick(View view) {
        final String keyToRemove = "" + view.getTag();
        new AlertDialog.Builder(context)
                .setTitle("Remove alarm?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("alarms");
                        myRef.child(keyToRemove).removeValue();

                    }
                })
                // remove speed dial
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setIcon(R.drawable.logo_app)
                .show();
    }

    @Override
    public int getItemCount() {
        // Return the number of data items to display
        return mWordList.size();
    }


    class WordViewHolder extends RecyclerView.ViewHolder  {
        alarm_recyclerView mAdapter;

        TextView nameMed, hora, numP;
        ConstraintLayout c;
        ImageButton remove;
        ImageView image;
        public WordViewHolder(View itemView, alarm_recyclerView adapter) {
            super(itemView);

            nameMed = itemView.findViewById(R.id.textView);
            hora = itemView.findViewById(R.id.textView2);
            numP = itemView.findViewById(R.id.textView10);
            c = itemView.findViewById(R.id.cc);
            remove = itemView.findViewById(R.id.removeButton);
            image = itemView.findViewById(R.id.imageView8);
            // Associate with this adapter
            this.mAdapter = adapter;
            // Add click listener, if desired
        }



    }
}