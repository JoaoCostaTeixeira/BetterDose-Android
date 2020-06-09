package com.example.cm_final_proj.Recycler_views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cm_final_proj.R;
import com.example.cm_final_proj.farmaciasFragments.farmacia_webview;
import com.example.cm_final_proj.model.Farmacia_model;

import java.util.ArrayList;

public class farmacias_recyclerView extends RecyclerView.Adapter<farmacias_recyclerView.WordViewHolder> {
    LayoutInflater mInflater;
    ArrayList<Farmacia_model> mWordList;

    private Button buttonPrev = null;

    public static final String EXTRA_MESSAGE = "com.example.cm_hw1.ADD";

    public farmacias_recyclerView(Context context, ArrayList<Farmacia_model> wordList) {
        mInflater = LayoutInflater.from(context);
        this.mWordList = wordList;
    }

    @Override
    public WordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Create view from layout
        View mItemView = mInflater.inflate(
                R.layout.listfarm, parent, false);

        return new WordViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(WordViewHolder holder, int position) {
        // Retrieve the data for that position
        String mCurrent = mWordList.get(position).getNome();
        String mCurrent2 = mWordList.get(position).getMorada();
        // Add the data to the view
        holder.wordItemView.setText(mCurrent);
        holder.wordItemView2.setText(mCurrent2);
        holder.but.setTag(mCurrent + "," + mCurrent2);
    }

    @Override
    public int getItemCount() {
        // Return the number of data items to display
        return mWordList.size();
    }


    class WordViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView wordItemView;
        TextView wordItemView2;
        farmacias_recyclerView mAdapter;
        Button but ;
        public WordViewHolder(View itemView, farmacias_recyclerView adapter) {
            super(itemView);
            // Get the layout
            wordItemView = itemView.findViewById(R.id.textView);
            wordItemView2 = itemView.findViewById(R.id.textView2);
            // Associate with this adapter
            this.mAdapter = adapter;
            // Add click listener, if desired
            but = itemView.findViewById(R.id.button2);
            but.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            FragmentManager manager = ((AppCompatActivity)view.getContext()).getSupportFragmentManager();
            Button text = (Button) view;
            String t =  ""+text.getTag();

            farmacia_webview second = new farmacia_webview(t);

            manager.beginTransaction()
                    .replace(R.id.fragmentsecond, second)
                    .addToBackStack(null)
                    .commit();


        }
    }
}