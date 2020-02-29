package com.example.gal.dogtime;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 */
public class MemoryGameFragment extends Fragment {
ImageView easy,medium,hard;
Button tutorials;


    public MemoryGameFragment() {
        // Required empty public constructor
    }

    /**
     * here the user picks the kevel he wants or he can read the tutorials
     * omce the user clicks one of the buttons he goes to the wanted activity
     * i used on click listener in order to do it
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view=inflater.inflate(R.layout.fragment_memory_game, container, false);

        easy=(ImageView)view.findViewById(R.id.easy);
        medium=(ImageView)view.findViewById(R.id.medium);
        hard=(ImageView)view.findViewById(R.id.hard);
        tutorials=(Button)view.findViewById(R.id.tutorials);

        easy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),Level1Activity.class);
                startActivity(intent);

            }
        });

        medium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),Level2Activity.class);

                startActivity(intent);

            }
        });

       hard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),Level3Activity.class);

                startActivity(intent);

            }
        });

        tutorials.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),MemoryTutrialsActivity.class);

                startActivity(intent);

            }
        });




        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event




}
