package com.example.coronadetector.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.coronadetector.R;
import com.example.coronadetector.RecyclerView.newsfeed;


public class Feed extends Fragment {


    Button b;
    View v;
    public Feed() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v= inflater.inflate(R.layout.fragment_feed, container, false);
        b =  v.findViewById(R.id.button6);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(inflater.getContext(), newsfeed.class));
            }
        });
        return v;
    }
}