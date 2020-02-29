package com.example.gal.dogtime;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import Adapters.DogListAdapter;
import Adapters.QuotesAdapter;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 *
 * to handle interaction events.
 */
public class QuotesFragment extends Fragment {
private GridView gridViewQuotes;
private ArrayList quotesList;
public static final int SIZE=24;//the number of the quotes images



    public QuotesFragment() {
        // Required empty public constructor
    }
    /**
     * once the user leaves the gallery this function will be invoked
     *this function makes the ui disappear
     */
    @SuppressLint("ValidFragment")
    public void onPause() {

        super.onPause();
        this.gridViewQuotes.setVisibility(View.GONE);

    }

    /**
     * makes the views visible again will be invoked when the fragment is resumed
     */
    public void  onResume() {
        super.onResume();
        this.gridViewQuotes.setVisibility(View.VISIBLE);

    }

    /**
     * here i iit the views ,calling my QuotesAdapter and ny using onItemClickListener
     * i get the user selection and transfer him to the wanted information(which is in anther activity) and passing via
     * intent the position of the selected quote
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quotes, container,true);
       this.gridViewQuotes=(GridView) view.findViewById(R.id.gridViewQuotes);
       quotesList= insertQuotes();//here i insert the qootes images into an arraylist
        final QuotesAdapter adapter=new QuotesAdapter(getContext(),quotesList);
        this.gridViewQuotes.setAdapter(adapter);//setting the adapter

//when the user clicks a quote the quote is opened
        this.gridViewQuotes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                Intent intent=new Intent(getActivity(),DisplayQuoteActivity.class);
                intent.putExtra("position",position);
                //intent.putExtra("myBreedsList", (Serializable) myBreedsList);
                startActivity(intent);

            }
        });


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quotes, container, false);
    }

    /**
     * inserts the quotes images into the list
     * @return
     */
    private ArrayList insertQuotes() {
       final ArrayList quotesList=new ArrayList();
       for(int i=1;i<=SIZE;i++)
       {
           String st="q"+i;
           int resId=getResources().getIdentifier("q"+i,"drawable",getContext().getPackageName());
           quotesList.add(resId);

       }

return quotesList;

    }




}
