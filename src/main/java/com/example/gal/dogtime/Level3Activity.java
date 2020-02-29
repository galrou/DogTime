package com.example.gal.dogtime;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import entities.Card;

public class Level3Activity extends AppCompatActivity {

    int numberOfClicks=0;
    int mistakes=0;
    TextView numOfMistakes;
    public static int counter=0;
    final int  ARR_SIZE = 16;
    Card[] cards=new Card[ARR_SIZE];
    ImageView[]views = new ImageView[ARR_SIZE];//array of views
    Card previousCard=new Card();
    int[] dogs=new int[ARR_SIZE/2];



    /**
     * this function is invoked once the activity is paused
     * here i stop the music thats in the background
     */
    @SuppressLint("ValidFragment")
    public void onPause() {
        super.onPause();
        stopService(new Intent(this,Music.class));

    }
    /**
     * this function is invoked once the activity is resumed
     * here i start the music in the background
     */
    public void  onResume() {
        super.onResume();
        startService(new Intent(this,Music.class));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level3);

        numOfMistakes=(TextView)findViewById(R.id.numOfMistakes);

        for(int i=0;i<ARR_SIZE;i++){
            views[i]=(ImageView)findViewById(getResources().getIdentifier("view"+i,"id",getPackageName()));
        }

        for(int i=0;i<ARR_SIZE/2;i++){
            dogs[i]=getResources().getIdentifier("dog"+i,"drawable",getPackageName());
        }



        int []picNums;//array of pictures
        // divide the images's array for 2
        int picIndex = 0;//picture indexs ,always half of the size of the cards's array
        picNums = pickRandom(dogs,ARR_SIZE /2);//get random pictures half size of the cards's array
        for(int i=0;i<ARR_SIZE;i++){
            cards[i] =new Card();//init the card object
            cards[i].setView(views[i]);//setting the matched view
            if ( i == ARR_SIZE /2){//initilize the random array
                picNums = pickRandom(dogs,ARR_SIZE /2);
                picIndex = 0;//init picIndex
            }


            cards[i].setImg(picNums[picIndex]);//set the dog image
            cards[i].setName("dog" +i);//setting the dog's name
            final int index=i;
            cards[i].getView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // turn over the picture
                    Glide.with(getApplicationContext()).load(cards[index].getImg()).into(cards[index].getView());

                    // if this is the first click and if we didnt double click the same image
                    if(numberOfClicks==0 && !cards[index].isClicked())
                    {

                        previousCard=cards[index];//saving the the clicked picture
                        cards[index].setClicked(true);//this is the first ,setting click to true
                        numberOfClicks++;
                    }
                    //if this is the second click and if we didnt double click the same image
                    else if(numberOfClicks==1 && !cards[index].isClicked())
                    {


                        // if the second click(image) doesnt match to the first click(first image)
                        if (cards[index].getImg() != previousCard.getImg() )
                        {
                            cards[index].setClicked(false);//this the second image it doesnt match setting it to false
                            previousCard.setClicked(false);//this the first image it doesnt match setting it to false
                            numberOfClicks = 0;//reset the clicks counter
                            mistakes++;
                            numOfMistakes.setText("number of mistakes:"+mistakes);
                            //setting delay to the runnable thread action(it wil run in the background)
                            Handler myHandler = new Handler();
                            myHandler.postDelayed(mMyRunnable, 500);//Message will be delivered in 0.5 second.


                            // else if the click on the card is match
                        }else{
                            numberOfClicks = 0;
                            counter++;
                            cards[index].setClicked(true);
                            previousCard.setClicked(true);
                        }
//                        if(counter==ARR_SIZE/2)
//                        {
//                            savingDataInFile();
//                            results.setText("see results");
//                        }
                    }
                }
            });
            picIndex++;
        }
    }

    public static int[] pickRandom(int[] array, int n) {

        List<Integer> list = new ArrayList<>(array.length);
        for (int i =0 ; i< n ; i++)
            list.add(array[i]);
        Collections.shuffle(list);

        int[] answer = new int[n];
        for (int i = 0; i < n; i++)
            answer[i] = list.get(i);

        return answer;

    }

    private Runnable mMyRunnable = new Runnable()
    {
        @Override
        public void run()
        {
            //loop over all the picture and turn over all the unclicked pictures
            for(int i=0;i<ARR_SIZE;i++){

                if (!cards[i].isClicked()){
                    cards[i].getView().setImageResource(R.drawable.blankcard );
                }

            }

        }
    };

}

