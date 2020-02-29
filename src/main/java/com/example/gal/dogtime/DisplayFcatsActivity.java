package com.example.gal.dogtime;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.jsibbold.zoomage.ZoomageView;

public class DisplayFcatsActivity extends AppCompatActivity {
    /**
     * this function inits my zoomage view,and diplays on it the wanted information
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_fcats);
        Intent intent=getIntent();

        int position = intent.getIntExtra("position", 0);
        int resId=getResources().getIdentifier("info"+position,"drawable",getPackageName());
        //i used a library so the user will be able to zoom images in and out
        ZoomageView ivfact=(ZoomageView)findViewById(R.id.ivfact);

        Glide.with(this).load(resId).into(ivfact);//using glide library in order to load the image faster


    }
}
