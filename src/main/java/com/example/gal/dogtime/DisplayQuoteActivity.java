package com.example.gal.dogtime;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jsibbold.zoomage.ZoomageView;

//an activity to display the quote image
public class DisplayQuoteActivity extends AppCompatActivity {
private ZoomageView displayQuote;

    /**
     * here i init the views and display the quote using glide library
     * i know which quote was selected because i got the position from the previous
     * activity using intent
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_quote);

        displayQuote=(ZoomageView) findViewById(R.id.displayQuote);
        Intent intent=getIntent();

        int position = intent.getIntExtra("position", 0)+1;//the position starts from 1,not 0
        int resId=getResources().getIdentifier("q"+position,"drawable",getPackageName());


        Glide.with(this).load(resId).into(displayQuote);//using glide library in order to load the image faster
    }
}
