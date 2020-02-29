package com.example.gal.dogtime;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import Adapters.PuzzleAdapter;
import Detectors.GestureDetectGridView;

public class DisplayPuzzleActivity extends AppCompatActivity {
    private Bitmap bitmap;//here ill store the selected image
    private ArrayList<Bitmap> smallimages;//an arraylist of the splitted images
    private Drawable d;//i convert the bitmap to drawable here i store the current image(see getDrawableImage)
    private static int COLUMNS=5;//the deafault number of my coloumns
    private static int DIMENSIONS=25;//the number of my tiles
    private static int[] tileList;//an array of my tiles its size is the dimensions(i have inside it my indexes and i scramble them also)
    private static ArrayList<Drawable>drawableImages;//arraylist of drawable images (converted from bitmap)so i can display them easily
    private static GestureDetectGridView mGridView;//an objects of a class i created,my layout
    private static int mColumnWidth,mColumnHeight;//columns height and width
    public static final String  up="up";
    public static final String down="down";
    public static final String  left="left";
    public static final String  right="right";

    /**
     * this function is invoked once the activity is resumed
     * here i start the music in the background
     */
    public void  onResume() {
        super.onResume();
        startService(new Intent(this,Music.class));

    }
    /**
    * once the user leaves the gallery this function will be invoked
    *this function "resets" the puzzle and my arraylists in addition it makes ui dissapear and i stop the music here
     */
    @Override
    protected void onPause() {
        super.onPause();
        bitmap.recycle();
        bitmap = null;
        if (drawableImages != null) {
           // Toast.makeText(this, "jjjjIn", Toast.LENGTH_SHORT).show();

//cleaning my drawableiamges arraylist
            for (int j = 0; j < drawableImages.size(); j++) {

                drawableImages.remove(j);

            }
        }
       // Toast.makeText(this,"jjjj",Toast.LENGTH_SHORT).show();
        this.mGridView.setVisibility(View.GONE);//ui disapears
        stopService(new Intent(this,Music.class));



    }

    /**
     * here i get from intent the puzzle's size and my uri string
     * then i converting the uri string back to uri and converting my uri to bitmap
     * moreover,i call splitImage(),init(),scramble()
     * and setDimensions()
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_puzzle);


//i sent the string uri of the taken image in the prvious activity here i get it(i converted the uri to string so i could send it)
            String image_path= getIntent().getStringExtra("imagePath");
            int col=getIntent().getIntExtra("columns",0);//the number of my coloumns(user typed it)
             COLUMNS=col;
             DIMENSIONS=COLUMNS*COLUMNS;//the number of my tiles

            Uri fileUri = Uri.parse(image_path);//im converting the string back to uri

            //smallimage_numbers is the number of the tiles ill have(the number of my splitted images)
            int smallimage_Numbers =DIMENSIONS;

            //Uri to bitmap,i convert the uri to bitmap so i can split it
            try {
                //Retrieves an image for the given url as a Bitmap.getBitmap is a public function in
                // this class:MediaStore.Images.Media
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), fileUri);

                //invoking this method makes the actual splitting of the source image to given number of smallimages
                smallimages=splitImage(smallimage_Numbers,bitmap);//here i send the number of the images i want to have and the bitmap of the taken image
                drawableImages= getDrawableImages();//converts  bitmap  to drawable
                //d=new BitmapDrawable(getResources(),smallimages.get(0));

            } catch (IOException e) {
                e.printStackTrace();
            }

            init();

            scramble();

            setDimensions();


        }
        /**
         *this function inits mGridVew( a GestureDetectorGridView object),
         * sets the number of coloumns
         * sets the size of the tilelist array(as the DIMENSIONS)
         * and puts into tileList indexes in this order(in position 0 i=0,in position 1 i=1)and so on
         * until i=DIMENSIONS
         */
    private void init() {
        mGridView = (GestureDetectGridView) findViewById(R.id.grid);
        //sets the number of colums
        mGridView.setNumColumns(COLUMNS);//gesturedetector extends gridview,in grideview i have a function for setting the number of columns
        tileList = new int[DIMENSIONS];//tileList's length is the dimensions
        for (int i = 0; i < DIMENSIONS; i++) {
            tileList[i] = i;//i set in tileList indexes the numbers in the i order(1,2,3...),ill scramble it
        }
    }


    /**
     * this function scrambles my array of the indexes(tileList)
     * inside the function i have a for loop which in it i pick a random number
     * in the scale of i+1 and switch between tileList[i] and tileList[the random number]
     * my for loop runs from the tileList length-1 until i>0
     * the result will be a scrambled array.
     *
     * <p>
     * Here i scramble my indexes(so my puzzle will be scrambled)
     *my tileList array is actually array of indexes that i scramble in order to have a random order of
     *splitted images that will appear on my screen
     * </p>
     */
    private void scramble() {
        int index;
        int temp;
        Random random = new Random();
        //here im actually doing the switching
        for (int i = tileList.length - 1; i > 0; i--) {
            index = random.nextInt(i + 1);//picks a random number in the bound of i+1
            temp = tileList[index];//i put in temp the value of the tileList in position of index
            tileList[index] = tileList[i];//here i put the value in position i into the value in poistion index
            tileList[i] = temp;
        }

    }


    /**
     * this function converts my smallImages array(which is an arraylist of bitmaps) to arrayList of drawable
     * thats stored in the arraylist drawableimages
     * @return drawableImages
     */
        private ArrayList<Drawable> getDrawableImages() {
            ArrayList<Drawable> drawableImages=new ArrayList<>();//an array of drawable images

            for(int i=0;i<smallimages.size();i++)
            {
                d=new BitmapDrawable(getResources(),smallimages.get(i));//my current bitmap that i convert to drawable i store it temprarly at d
                drawableImages.add(i,d);//hre im adding it to the array list with its index,i need its index so


            }
            return drawableImages;
        }


    /**
     *this function sets the height and width of each splitted image(the actual setting is in display function via the
     * adapter)
     */
    private void setDimensions() {
            //without viewtreeobserver the getMeasure will return 0
        ViewTreeObserver vto = mGridView.getViewTreeObserver();
        //register action that will activate a callback when the are changes in the view/layout
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mGridView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                int displayWidth = mGridView.getMeasuredWidth();//returns the raw measured width of this view.
                int displayHeight = mGridView.getMeasuredHeight();//returns the raw measured height of this view.

                int statusbarHeight = getStatusBarHeight(getApplicationContext());
                int requiredHeight = displayHeight - statusbarHeight;//in some phones the splitted images somtimes do not appear properly here i make sure they will
              // int requiredHeight=displayHeight;

                //so i can know the size of each image
                mColumnWidth = displayWidth / COLUMNS;
                mColumnHeight = requiredHeight / COLUMNS;

                display(getApplicationContext());


            }
        });
    }


    /**
     * returns the statusbar height
     * @param context
     * @return result
     */
    private int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");

        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * this function displays the splitted images and sets the adapter after the
     * loops are over
     * @param context
     */
    private static void display(Context context) {

        ArrayList<ImageView> imgviews = new ArrayList<>();// arraylist of views
        ImageView imgview;
        for(int i=0;i<tileList.length;i++) {
            imgview = new ImageView(context);//creating new imageview view
            for (int j = 0; j < tileList.length; j++) {
                if (tileList[i] == j) {
                   // Glide.with(context).load(drawableImages.get(j)).into(imgview);
                    imgview.setBackground(drawableImages.get(j));//setting theimage in position j as a background of the imageview
                }
            }
            imgviews.add(imgview);//adding the imgview to my list of imageviews
        }


        mGridView.setAdapter(new PuzzleAdapter(imgviews, mColumnWidth, mColumnHeight));//setting the adapter
    }


    /**
     * here i swapping between two tiles and calling display method again
     * @param context
     * @param position
     * @param swap
     */
    public static void swap(Context context, int position, int swap) {
        int newPosition = tileList[position + swap];
        tileList[position + swap] = tileList[position];
        tileList[position] = newPosition;
        display(context);

    }

    /**
     *this method gets as a parameters the context,the direction the use swipped and the position of the tile
     * the method sends to swap() method diffrent numbers depends on its position on the screen,and the direction
     * if the function that is in the class GestureDetectGridView returns false the user will see
     * a toast message that says "invalid move"
     * @param context
     * @param direction
     * @param position
     */
    public static void moveTiles(Context context, String direction, int position) {

        // the upper-left-corner tile
        if (position == 0) {

            if (direction.equals(right))//if the user slides the tile right
                swap(context, position, 1);

            else if (direction.equals(down))//if the user slides the tile down
                swap(context, position, COLUMNS);

            else Toast.makeText(context, "Invalid move", Toast.LENGTH_SHORT).show();


        }

        // Upper-center tiles
        else if (position > 0 && position < COLUMNS - 1) {

            if (direction.equals(left))
                swap(context, position, -1);

            else if (direction.equals(down))
                swap(context, position, COLUMNS);

            else if (direction.equals(right))
                swap(context, position, 1);

            else Toast.makeText(context, "Invalid move", Toast.LENGTH_SHORT).show();
        }

        // Upper-right-corner tile
        else if (position == COLUMNS - 1) {

            if (direction.equals(left))
                swap(context, position, -1);

            else if (direction.equals(down))
                swap(context, position, COLUMNS);

            else Toast.makeText(context, "Invalid move", Toast.LENGTH_SHORT).show();

            // Left-side tiles
        } else if (position > COLUMNS - 1 && position < DIMENSIONS - COLUMNS &&
                position % COLUMNS == 0) {

             if (direction.equals(up))
                swap(context, position, -COLUMNS);

             else if (direction.equals(right))
                swap(context, position, 1);

             else if (direction.equals(down))
                swap(context, position, COLUMNS);

             else Toast.makeText(context, "Invalid move", Toast.LENGTH_SHORT).show();


        }
        // Right-side AND bottom-right-corner tiles
        else if (position == COLUMNS * 2 - 1 || position == COLUMNS * 3 - 1) {

            if (direction.equals(up))
                swap(context, position, -COLUMNS);

            else if (direction.equals(left)) {
                swap(context, position, -1);
            }
            else if (direction.equals(down)) {

                // Tolerates only the right-side tiles to swap downwards as opposed to the bottom-
                // right-corner tile.
                if (position <= DIMENSIONS - COLUMNS - 1) swap(context, position,
                        COLUMNS);
                else Toast.makeText(context, "Invalid move", Toast.LENGTH_SHORT).show();

            } else Toast.makeText(context, "Invalid move", Toast.LENGTH_SHORT).show();


            // Bottom-left corner tile
        } else if (position == DIMENSIONS - COLUMNS) {

            if (direction.equals(up))
                swap(context, position, -COLUMNS);

            else if (direction.equals(right))
                swap(context, position, -1);

            else Toast.makeText(context, "Invalid move", Toast.LENGTH_SHORT).show();

            // Bottom-center tiles
        } else if (position < DIMENSIONS - 1 && position > DIMENSIONS - COLUMNS) {

            if (direction.equals(up))
                swap(context, position, -COLUMNS);

            else if (direction.equals(left))
                swap(context, position, -1);

            else if (direction.equals(right))
                swap(context, position, 1);

            else Toast.makeText(context, "Invalid move", Toast.LENGTH_SHORT).show();

            // Center tiles
        } else {
            if (direction.equals(up))
                swap(context, position, -COLUMNS);

            else if (direction.equals(left))
                swap(context, position, -1);

            else if (direction.equals(right))
                swap(context, position, 1);

            else
                swap(context, position, COLUMNS);
        }
    }


    /**
     * splits the image
     * @param smallimage_Numbers
     * @param bitmap
     * @return
     */
    private ArrayList<Bitmap> splitImage( int smallimage_Numbers, Bitmap bitmap) {
        //For the number of rows and columns of the grid to be displayed
        int rows,cols;

        //For height and width of the small image smallimages
        int smallimage_Height,smallimage_Width;

        //To store all the small image smallimages in bitmap format in this list
        ArrayList<Bitmap> smallimages = new ArrayList<Bitmap>(smallimage_Numbers);

        //Creates a new bitmap, scaled from an existing bitmap
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth(), bitmap.getHeight(), true);

        rows = cols = (int) Math.sqrt(smallimage_Numbers);
        smallimage_Height = bitmap.getHeight()/rows;//the wanted height of tile
        smallimage_Width = bitmap.getWidth()/cols;//the wanted width of tile

        //xCo and yCo are the pixel positions of the image smallimage_s
        int yCo = 0;
        for(int x=0; x<rows; x++){

            int xCo = 0;

            for(int y=0; y<cols; y++){
                //adding each
                smallimages.add(Bitmap.createBitmap(scaledBitmap, xCo, yCo, smallimage_Width, smallimage_Height));
                xCo += smallimage_Width;

            }
            yCo+= smallimage_Height;
        }


        return smallimages;


    }
    }

