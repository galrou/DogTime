package Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.gal.dogtime.R;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import entities.Dog;
//An adapter manages the data model and adapts it to the individual entries in the widget
//Adapter of the gridview of the dog lists
public class DogListAdapter extends ArrayAdapter<Dog> implements Filterable {
    private Context myContext;//the context
    private List<Dog> myBreedsList;//list of the dogs
    private List<Dog> filterList;//a list of all the dogs for the search,i store here all the dogs
    private CustomFilter filter;

    /**
     * public constructer: sets the context,mybreedslist and the filterlist
     * @param myContext
     * @param myBreedsList
     */
    public DogListAdapter(Context myContext, List<Dog> myBreedsList) {
        super(myContext, R.layout.dog_list_layout, myBreedsList);
        this.myContext = myContext;
        this.myBreedsList = myBreedsList;
        this.filterList = myBreedsList;


    }

    /**
     * How many items are in the data set represented by this Adapter.
     * @return the size of the list
     */
    @Override
    public int getCount() {
        return myBreedsList.size();
    }

    /**
     * Get the row id associated with the specified position in the list.
     * @param position
     * @return position
     */
    @Override
    public long getItemId(int position) {
        return myBreedsList.get(position).getId();
    }



    /**
     * inner class viewHolder,makes the scrolling faster and more efficient
     */
    private class ViewHolder {
        private final TextView tvName;//will hold the name of the breed
        private final ImageView ivdog;//holds the imageview that the dogs pic will be sored in

        public ViewHolder(TextView tvName, ImageView ivdog) {
            this.tvName = tvName;
            this.ivdog = ivdog;
        }
    }
    /**
     * Gets a View that displays the data at the specified position in the data set
     */
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        Dog dog = myBreedsList.get(position);
        if (convertView == null) {// if it's not recycled, initialize some attributes
            // I inflate the xml which will give me a view
            LayoutInflater inflater = (LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.dog_list_layout, parent, false);
            ImageView ivdog = (ImageView) convertView.findViewById(R.id.ivdog);
            TextView tvName = (TextView) convertView.findViewById(R.id.breedname);

            final ViewHolder viewHolder = new ViewHolder(tvName, ivdog);
            convertView.setTag(viewHolder);
        }
        final ViewHolder viewHolder = (ViewHolder) convertView.getTag();

        viewHolder.tvName.setText(myBreedsList.get(position).getBreedname());
        int drawableId = 0;//init drawable
        try {
            Class res = R.drawable.class;
            Field field = res.getField(myBreedsList.get(position).getImage().toString());
            Log.e("kk", myBreedsList.get(position).getImage().toString());
            drawableId = field.getInt(null);
        } catch (Exception e) {
            Log.e("MyTag", "Failure to get drawable id.", e);
        }
        Glide.with(myContext).load(drawableId).into(viewHolder.ivdog);//using glide library to load pictures faster(picasso is slower)
        return convertView;
    }


    //i worte the methods getfilter and custom filter so i can search an item within my "list"
    //implement method

    /**
     * Returns a filter that can be used to constrain data with a filtering pattern.
     * @return filter
     */
    @Override
    public Filter getFilter() {
        if (filter == null) {//if its null create a new custom filter object
            filter = new CustomFilter();
        }
        return filter;
    }

    //new inner class for custom filter
    class CustomFilter extends Filter {
        /**
         * performs the filtering
         * @param constraint
         * @return
         */
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            // TODO Auto-generated method stub

            FilterResults results = new FilterResults();

            if (constraint != null && constraint.length() > 0) {
                //constarint to upper
                constraint = constraint.toString().toUpperCase();

                ArrayList<Dog> filters = new ArrayList<Dog>();//here ill store the matched results
                //get specific item
                for (int i = 0; i < filterList.size(); i++) {
                    if (filterList.get(i).getBreedname().toUpperCase().contains(constraint) == true) {
                        Dog d = new Dog(filterList.get(i).getId(), filterList.get(i).getBreedname(),
                                filterList.get(i).getImage(), filterList.get(i).getHeight(),
                                filterList.get(i).getWeight(), filterList.get(i).getLifeSpan(),
                                filterList.get(i).getPersonality(), filterList.get(i).getHealth(),
                                filterList.get(i).getFeeding(), filterList.get(i).getChildrenAndPets(),
                                filterList.get(i).getCare());

                        filters.add(d);//adding to my filter list

                    }
                }
                results.count = filters.size();
                results.values = filters;
            } else {
                results.count = filterList.size();
                results.values = filterList;
            }


            return results;
        }

        /**
         * Invoked in the UI thread to publish the filtering results in the user interface
         * @param constraint
         * @param results
         */
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            myBreedsList = (ArrayList< Dog>) results.values;
            notifyDataSetChanged();

        }


    }

    /**
     * toString method
     * @return
     */
    @Override
    public String toString() {
        return "DogListAdapter{" +
                ", filter=" + filter +
                '}';
    }


}
