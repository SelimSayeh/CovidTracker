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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Form#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Form extends Fragment {
    Button b;
    View v;
    public Form() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v= inflater.inflate(R.layout.fragment_form, container, false);
        b =  v.findViewById(R.id.button6);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(inflater.getContext(), com.example.coronadetector.form.MainActivity.class));
            }
        });
        return v;
    }
}