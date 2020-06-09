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
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cm_final_proj.R;
import com.example.cm_final_proj.farmaciasFragments.farmacia_webview;
import com.example.cm_final_proj.medicamentosFragments.add_med;
import com.example.cm_final_proj.medicamentosFragments.remove_med;
import com.example.cm_final_proj.model.House;
import com.example.cm_final_proj.model.Medicamentos;
import com.example.cm_final_proj.model.alarm_array;
import com.example.cm_final_proj.model.med_array;
import com.example.cm_final_proj.model.med_array_alarm;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static java.security.AccessController.getContext;

public class alarm_service_recycler extends RecyclerView.Adapter<alarm_service_recycler.WordViewHolder> {
    LayoutInflater mInflater;
    ArrayList<med_array_alarm> mWordList;
    Context context;

    public alarm_service_recycler(Context context, ArrayList<med_array_alarm> wordList) {
        mInflater = LayoutInflater.from(context);
        this.mWordList = wordList;
        this.context = context;
    }

    @Override
    public WordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Create view from layout
        View mItemView = mInflater.inflate(
                R.layout.listalarmmed, parent, false);

        return new WordViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(WordViewHolder holder, int position) {
        holder.nameMed.setText(mWordList.get(position).getMed().nome);
        holder.numP.setText(mWordList.get(position).getQuant()+ "x");


        if(mWordList.get(position).getMed().atual-mWordList.get(position).getQuant() <=5 ){
            holder.c.setBackground(ContextCompat.getDrawable(context,R.drawable.rounded_corner_orange));
        }
        if(mWordList.get(position).getQuant()>mWordList.get(position).getMed().atual){
            holder.c.setBackground(ContextCompat.getDrawable(context,R.drawable.rounded_corner_red));
        }

        Picasso.with(context)
                .load(mWordList.get(position).getMed().image)
                .into(holder.image);

    }


    @Override
    public int getItemCount() {
        // Return the number of data items to display
        return mWordList.size();
    }


    class WordViewHolder extends RecyclerView.ViewHolder  {
        alarm_service_recycler mAdapter;
        ConstraintLayout c;
        TextView nameMed, numP;
        ImageView image;
        public WordViewHolder(View itemView, alarm_service_recycler adapter) {
            super(itemView);

            nameMed = itemView.findViewById(R.id.textView);
            numP = itemView.findViewById(R.id.textView2);
            image = itemView.findViewById(R.id.imageView8);
            c = itemView.findViewById(R.id.cc);
            // Associate with this adapter
            this.mAdapter = adapter;
            // Add click listener, if desired
        }



    }
}