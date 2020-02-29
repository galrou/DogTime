package com.example.gal.dogtime;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import entities.Card;

public class Level1Activity extends AppCompatActivity {
    int numberOfClicks=0;
    int mistakes=0;
    TextView numOfMistakes;//textView that holds the number of mitakes
    public static int counter=0;//so i can know when the games over
    Card previousCard=new Card();//the previous card cuz i need to find a match
    final int  ARR_SIZE = 6;//the number of cards is my ARR_SIZE
    Card[] cards=new Card[ARR_SIZE];//an array of cards
    ImageView[]views = new ImageView[ARR_SIZE];//array of views
    int[] dogs=new int[ARR_SIZE/2];//the size of my dog array(an array for the images index) is half of the ARR_SIZE

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


    /**
     * this the main process of the memory game
     * we init 3 arrays: the cards array that contains the cards
     * the dogs array that contains the index of the images and its size half of the number of the cards
     * and imagview array which contains the imagesviews.
     * in the first step we scramble the cards using pickrandom()
     * in the second step i get the user selection by using onClick listener and check if
     * the clicked card are matched
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level1);
//        MediaPlayer player= MediaPlayer.create(this, Settings.System.DEFAULT_RINGTONE_URI);
//        player.setLooping(true);
//        player.start();
        numOfMistakes=(TextView)findViewById(R.id.numOfMistakes);

        //init the views
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
            cards[i] = new Card();//init the card object
            cards[i].setView(views[i]);//setting the matched view
            if ( i == ARR_SIZE /2){//initilize the random array
                picNums = pickRandom(dogs,ARR_SIZE /2);
                picIndex = 0;//init picIndex cuz the size of image array half of the cards
            }
            cards[i].setImg(picNums[picIndex]);//set the dog image index
            cards[i].setName("dog" +i);//set the dog name,for debugg purporses

            final int index=i;
            cards[i].getView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // turn over the picture
                    cards[index].getView().setImageResource(cards[index].getImg());//displays the image
                    System.out.print("kkkk");
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


                        // if the second click(image) doesn't match to the first click(first image)
                        if (cards[index].getImg() != previousCard.getImg() )
                        {
                            cards[index].setClicked(false);//this the second image it doesnt match setting it to false
                            previousCard.setClicked(false);//this the first image it doesnt match setting it to false
                            numberOfClicks = 0;//reset the clicks counter
                            mistakes++;
                            numOfMistakes.setText("number of mistakes:"+mistakes);
                            //setting delay to the runnable thread action(it wil run in the background)
                            Handler myHandler = new Handler();
                            myHandler.postDelayed(mMyRunnable, 500);//will be delivered in 0.5 second.


                            // else if the click on the card is match
                        }else{
                            numberOfClicks = 0;
                            counter++;
                            //  t.setText(" "+ counter);
                            cards[index].setClicked(true);
                            previousCard.setClicked(true);
                        }


                    }
                }
            });
            picIndex++;

        }
    }

    /**
     *scrambles the array randomaly
     * @param array
     * @param n
     * @return
     */
    public static int[] pickRandom(int[] array, int n) {
        //a list for string the array[i] in each cell so i can shuffle it wuthout
        //demaging the orginial one
        List<Integer> list = new ArrayList<>(array.length);
        //inserting the cells
        for (int i =0 ; i< n ; i++)
            list.add(array[i]);

        Collections.shuffle(list);//shffeling

        int[] answer = new int[n];
        //inserting to answer array so ill return a scarmbled array
        for (int i = 0; i < n; i++)
            answer[i] = list.get(i);

        return answer;

    }

    /**
     * this function is activated after 0.5 once the user's selection is wrong and  and turns back the cards
     * thats not matched
     */
    private Runnable mMyRunnable = new Runnable()
    {
        @Override
        public void run()
        {
            //loop over all the pictures and turn over all the unclicked pictures
            for(int i=0;i<ARR_SIZE;i++){

                if (!cards[i].isClicked()){
                    cards[i].getView().setImageResource(R.drawable.blankcard );
                }

            }

        }
    };
}
