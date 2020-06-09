package com.example.cm_final_proj.Recycler_views;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cm_final_proj.R;
import com.example.cm_final_proj.farmaciasFragments.farmacia_webview;
import com.example.cm_final_proj.model.House;
import com.example.cm_final_proj.model.house_array;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class house_recyclerView extends RecyclerView.Adapter<house_recyclerView.WordViewHolder> {
    LayoutInflater mInflater;
    ArrayList<house_array> mWordList;
    Context context;
    private Button buttonPrev = null;

    public static final String EXTRA_MESSAGE = "com.example.cm_hw1.ADD";

    public house_recyclerView(Context context, ArrayList<house_array> wordList) {
        mInflater = LayoutInflater.from(context);
        this.mWordList = wordList;
        this.context = context;
    }

    @Override
    public WordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Create view from layout
        View mItemView = mInflater.inflate(
                R.layout.listhouse, parent, false);

        return new WordViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(WordViewHolder holder, int position) {

        if( mWordList.get(position) == null){
            holder.c.setVisibility(View.INVISIBLE);
            holder.c.setEnabled(false);
        }else{
            // Retrieve the data for that position
            String mCurrent = mWordList.get(position).getHouse().Nome;

            // Add the data to the view
            holder.wordItemView.setText(mCurrent);
            holder.but.setTag(mWordList.get(position).getKey());
        }
    }

    @Override
    public int getItemCount() {
        // Return the number of data items to display
        return mWordList.size();
    }


    class WordViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView wordItemView;
        house_recyclerView mAdapter;
        ImageButton but ;
        ConstraintLayout c;
        public WordViewHolder(View itemView, house_recyclerView adapter) {
            super(itemView);
            // Get the layout
            wordItemView = itemView.findViewById(R.id.textView);
            // Associate with this adapter
            this.mAdapter = adapter;
            // Add click listener, if desired
            c = itemView.findViewById(R.id.cc);
            but = itemView.findViewById(R.id.removeButton4);
            but.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            final String keyToRemove = "" + view.getTag();
            new AlertDialog.Builder(context)
                    .setTitle("Remove house ?")
                    // call using speed dial
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            DatabaseReference myRef = FirebaseDatabase.getInstance().getReference().child("houses");
                            myRef.child("" + keyToRemove).removeValue();
                        }
                    })
                    // remove speed dial
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .setIcon(android.R.drawable.ic_input_add)
                    .show();

        }
    }
}