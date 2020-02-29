package entities;

import android.widget.ImageView;

/**
 * Created by gal on 08/06/2018.
 */

public class Card {

    private String name;//the name of the image,its for debugg purposes
    private ImageView views;//pointer to an image view
    private int img;// image id
    private boolean isClicked=false;//is the card clicked

    /**
     * an empty constructor
     */
    public Card()
    {
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ImageView getView() {
        return views;
    }

    public void setView(ImageView view) {
        this.views = view;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public boolean isClicked() {
        return isClicked;
    }

    public void setClicked(boolean clicked) {
        isClicked = clicked;
    }




}
