package com.example.gal.dogtime;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.lang.reflect.Field;
import java.util.ArrayList;

import entities.Dog;

public class BreedsInfoActivity extends AppCompatActivity {
    private ImageView dogImg;
    //private ListView info_lv;
    private TextView name;
    private TextView breedHeight;
    private TextView breedWeight;
    private TextView lifeSpan;
    private TextView personality;
    private TextView health;
    private TextView feeding;
    private TextView childrenAndPets;
    private TextView care;
    public ArrayList<Dog> myBreedsList;//an arraylist of all the dogs

    /**
     * inits the views and reads the data from
     * my list of dogs and displays it on the screen
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breeds_info);
        //init the views
        dogImg=(ImageView) findViewById(R.id.dogImg);
        name=(TextView) findViewById(R.id.name);
        breedHeight=(TextView) findViewById(R.id.breedheight);
        breedWeight=(TextView) findViewById(R.id.breedweight);
        lifeSpan=(TextView) findViewById(R.id.lifeSpan);
        personality=(TextView) findViewById(R.id.personality);
        health=(TextView) findViewById(R.id.health);
        feeding=(TextView) findViewById(R.id.feeding);
        childrenAndPets=(TextView) findViewById(R.id.childrenAndPets);
        care=(TextView) findViewById(R.id.care);


        Intent intent=getIntent();
        int id = intent.getIntExtra("id", 0);//the position
        //Toast.makeText(this,id+"",Toast.LENGTH_SHORT).show();

        myBreedsList= (ArrayList<Dog>) intent.getSerializableExtra("myBreedsList");

        //here im setting texts into the views
        name.setText(myBreedsList.get(id-1).getBreedname());//id-1 cuz my id(thats in the database) starts from 1
        breedHeight.setText(myBreedsList.get(id-1).getHeight());
        breedWeight.setText(myBreedsList.get(id-1).getWeight());
        lifeSpan.setText(myBreedsList.get(id-1).getLifeSpan());
        personality.setText(myBreedsList.get(id-1).getPersonality());
        health.setText(myBreedsList.get(id-1).getHealth());
        feeding.setText(myBreedsList.get(id-1).getFeeding());
        childrenAndPets.setText(myBreedsList.get(id-1).getChildrenAndPets());
        care.setText(myBreedsList.get(id-1).getCare());
        // Toast.makeText(this,myBreedsList.get(id-1).getPersonality()+"",Toast.LENGTH_SHORT).show();


        //in the database the pictures are saved as"dog0","dog11" and etc... this method makes the
        // pictures appear by their names
        int drawableId=0;//init drawableId
        try {
            Class res = R.drawable.class;
            Field field = res.getField(myBreedsList.get(id -1).getImage().toString());
            drawableId = field.getInt(null);
        }
        catch (Exception e) {
            Log.e("MyTag", "Failure to get drawable id.", e);
        }
        Glide.with(this).load(drawableId).into(dogImg);


    }

}

