package com.example.coronadetector.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.coronadetector.R;
import com.example.coronadetector.map.Activity.MapsMarkerActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Maps#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Maps extends Fragment {
    Button b;
    View v;
    public Maps() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v= inflater.inflate(R.layout.fragment_maps, container, false);
        b =  v.findViewById(R.id.button6);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(inflater.getContext(), MapsMarkerActivity.class));
            }
        });
        return v;
    }
}