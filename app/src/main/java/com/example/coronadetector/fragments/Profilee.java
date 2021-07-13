package com.example.coronadetector.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.coronadetector.Profile;
import com.example.coronadetector.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Profilee#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Profilee extends Fragment {
    Button b;
    View v;
    public Profilee() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v= inflater.inflate(R.layout.fragment_profile, container, false);
        b =  v.findViewById(R.id.button6);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(inflater.getContext(), Profile.class));
            }
        });
        return v;
    }
}