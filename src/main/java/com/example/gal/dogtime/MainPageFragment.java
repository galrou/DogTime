package com.example.gal.dogtime;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 */
public class MainPageFragment extends Fragment {
private ImageView start2,start3,start4,start5,start6;
    private FragmentActivity myContext;//SO I CAN HAVE ACCESS TO IT
//    public void onPause() {
//
//        super.onPause();
//        this.gridViewDogs.setVisibility(View.GONE);
//
//    }
//    public void  onResume() {
//        super.onResume();
//        this.gridViewDogs.setVisibility(View.VISIBLE);
//
//    }

    /**
     * an empty public constructor that is required
     */
    public MainPageFragment() {
        // Required empty public constructor
    }

    /**
     * Called when a fragment is first attached to its activity
     * @param activity
     */
    @Override
    public void onAttach(Activity activity) {
        myContext=(FragmentActivity) activity;
        super.onAttach(activity);
    }

    /**
     *
     *here i init the views and handle the clicks on each view.
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

      //If set to true then when your layout is inflated it will be automatically added to the view hierarchy of the
        // ViewGroup specified in the 2nd parameter as a child.
        // For example if the root parameter was a LinearLayout then the
        // inflated view will be automatically added as a child of that view.
       // If it is set to false then your layout will be inflated but won't be attached to
        // any other layout (so it won't be drawn, receive touch events etc)
        View view = inflater.inflate(R.layout.fragment_main_page, container, false);



        start2 =(ImageButton) view.findViewById(R.id.starts2);
        start3 =(ImageButton) view.findViewById(R.id.starts3);
        start4 =(ImageButton) view.findViewById(R.id.starts4);
        start5 =(ImageButton) view.findViewById(R.id.starts5);
        start6 =(ImageButton) view.findViewById(R.id.starts6);


        Glide.with(view).load(R.drawable.start2).into(start2);

        start2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               BreedsActivity fragment=new BreedsActivity();
                android.support.v4.app.FragmentTransaction fragmentTransaction=
                        myContext.getSupportFragmentManager().beginTransaction();
               // FragmentManager fragManager = myContext.getSupportFragmentManager(); //If using fragments from support v4

                fragmentTransaction.replace(R.id.fragment_container,fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();//making this heppen


            }
        });
        start3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FactsFragment fragment=new FactsFragment();
                android.support.v4.app.FragmentTransaction fragmentTransaction=
                        myContext.getSupportFragmentManager().beginTransaction();
                // FragmentManager fragManager = myContext.getSupportFragmentManager(); //If using fragments from support v4

                fragmentTransaction.replace(R.id.fragment_container,fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();//making this heppen


            }
        });

        start4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               QuotesFragment fragment=new QuotesFragment();
                android.support.v4.app.FragmentTransaction fragmentTransaction=
                        myContext.getSupportFragmentManager().beginTransaction();
                // FragmentManager fragManager = myContext.getSupportFragmentManager(); //If using fragments from support v4

                fragmentTransaction.replace(R.id.fragment_container,fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();//making this heppen


            }
        });

        start5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PuzzleFragment fragment=new PuzzleFragment();
                android.support.v4.app.FragmentTransaction fragmentTransaction=
                        myContext.getSupportFragmentManager().beginTransaction();
                // FragmentManager fragManager = myContext.getSupportFragmentManager(); //If using fragments from support v4

                fragmentTransaction.replace(R.id.fragment_container,fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();//making this heppen


            }
        });


        start6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               MemoryGameFragment fragment=new MemoryGameFragment();
                android.support.v4.app.FragmentTransaction fragmentTransaction=
                        myContext.getSupportFragmentManager().beginTransaction();
                // FragmentManager fragManager = myContext.getSupportFragmentManager(); //If using fragments from support v4
                fragmentTransaction.replace(R.id.fragment_container,fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();//making this heppen


            }
        });


        return view;
    }








}
