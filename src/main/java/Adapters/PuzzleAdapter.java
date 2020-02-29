package Adapters;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by gal on 17/05/2018.
 */

public class PuzzleAdapter extends BaseAdapter {
    private ArrayList<ImageView> mImgviews;//array list of the imageviews
    private int mColumnWidth,mColumnHeight;//my puzzle is dynamic(the user picks the size) the height and
    // the width of the columns changes

    /**
     *  a constructer that sets the arraylist of the imageviews,the column width and the column height
     */
    public PuzzleAdapter(ArrayList<ImageView> mImgviews, int columnWidth, int columnHeight) {
        this.mImgviews = mImgviews;
        this.mColumnWidth=columnWidth;
        this.mColumnHeight=columnHeight;
    }

    /**
     * How many items are in the data set represented by this Adapter.
     * @return
     */
    @Override
    public int getCount() {
        return mImgviews.size();
    }

    /**
     * Get the data item associated with the specified position in the data set.
     * @param position
     * @return
     */
    @Override
    public Object getItem(int position) {
        return (Object)mImgviews.get(position);
    }

    /**
     * Get the row id associated with the specified position in the list.
     * @param position
     * @return
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * /**
     * Gets a View that displays the data at the specified position in the data set
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ImageView imgview;
        if(convertView==null) {
            imgview=mImgviews.get(position);
        }
        else{
            imgview=(ImageView) convertView;
        }
       // subclasses of this class can display the content of the list in a grid
        //set the dimensions
        //AbsListView extends LayoutParams to provide a place to hold the view type
        android.widget.AbsListView.LayoutParams params=
                new android.widget.AbsListView.LayoutParams(mColumnWidth,mColumnHeight);

        //Set the layout parameters associated with this view.
        // These supply parameters to the parent of this view specifying how it should be arranged
        imgview.setLayoutParams(params);



        return imgview;
    }
}

