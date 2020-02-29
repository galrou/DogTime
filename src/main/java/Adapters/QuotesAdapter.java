package Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.gal.dogtime.R;

import java.util.ArrayList;
import java.util.List;

import entities.Dog;

/**
 * Created by gal on 13/05/2018.
 */

public class QuotesAdapter extends ArrayAdapter {
    private Context myContext;//my context
    private List quotesList;//a list of the quotes images

    /**
     * a contructor that sets context and quoteList
     * @param myContext
     * @param quotesList
     */
    public QuotesAdapter(Context myContext, List quotesList) {
        super(myContext, R.layout.quotes_layout ,quotesList);
        this.myContext = myContext;
        this.quotesList = quotesList;


    }

    /**
     * How many items are in the data set represented by this Adapter.
     * @return
     */
    public int getCount() {
        return quotesList.size();
    }





    //inner class viewHolder,makes the scrolling faster and more efficient
    private class ViewHolder {
        private final ImageView ivquote;

        public ViewHolder( ImageView ivquote) {

            this.ivquote = ivquote;
        }
    }
    /**
     * Gets a View that displays the data at the specified position in the data set
     * i used here in glide library as well so the scrolling and the displaying will be much faster
     */
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        if (convertView == null) {// if it's not recycled, initialize some attributes

            // I inflate the xml which will give me a view
            LayoutInflater inflater = (LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.quotes_layout, parent, false);
            ImageView ivquote = (ImageView) convertView.findViewById(R.id.ivquote);

            final ViewHolder viewHolder = new ViewHolder(ivquote);
            convertView.setTag(viewHolder);
        }
        final ViewHolder viewHolder = (ViewHolder) convertView.getTag();
        Glide.with(myContext).load(quotesList.get(position)).into(viewHolder.ivquote);//using glide library to load pictures faster(picasso is slower)

        return convertView;

    }
}
