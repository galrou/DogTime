package com.example.gal.dogtime;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 */
public class FactsFragment extends Fragment {
public static final int SIZE=16;//THE NUMBER OF MY ICONINFO pics

    public FactsFragment() {
        // Required empty public constructor
    }

    /**
     * here i display the "did you know" information using glide for smooth scrolling
     *  and i also handle the clicks on each item with onTouch listener
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_facts, container, false);
       ImageButton[]imgbtns=new ImageButton[SIZE];//an arraylist of imagebuttons so i can put the images usimg glide for smooth scrolling

        final LinearLayout iconsInfo=(LinearLayout) view.findViewById(R.id.iconsInfo);

        int j=0;
        for(int i=0;i<SIZE;i++)
        {
            j++;
           int resId=getResources().getIdentifier("iv"+i,"id",getContext().getPackageName());//the id of the image buttons
            imgbtns[i]=(ImageButton)view.findViewById(resId);
            int imgId=getResources().getIdentifier("iconinfo"+j,"drawable",getContext().getPackageName());//the images

            Glide.with(view).load(imgId).into(imgbtns[i]);//displaying with glide

            imgbtns[i].setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if(event.getAction() == MotionEvent.ACTION_UP){
                        int j=0;

                        for( int i=0;i<16;i++)
                        {
                            j++;

                          int id=getResources().getIdentifier("iconinfo"+j,"drawable",getContext().getPackageName());

                            if(v.getId()==iconsInfo.getChildAt(i).getId())//checking what was clicked
                            {
                                Intent intent=new Intent(getActivity(),DisplayFcatsActivity.class);
                                int position=j;
                                intent.putExtra("position",position);
                                startActivity(intent);

                            }
                        }


                        return true;
                    }
                    return false;
                }
            });
        }


        return view;
    }



}







