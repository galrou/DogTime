package com.example.gal.dogtime;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SearchView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import Adapters.DogListAdapter;
import Helpers.DBHelper;
import entities.Dog;


/**
 * A simple {@link Fragment} subclass.
 *
 */
public class BreedsActivity extends Fragment {
    private GridView gridViewDogs;//the name of the gridView in the xml file
    private List<Dog> myBreedsList;//list of all the breeds
    private DBHelper myDBHelper;

    /**
     *  Required empty public constructor
     */
    public BreedsActivity() {
        // Required empty public constructor
    }

    /**
     * makes the views disappear once the fragment is paused
     */
    @SuppressLint("ValidFragment")
    public void onPause() {

        super.onPause();
        this.gridViewDogs.setVisibility(View.GONE);

    }

    /**
     * makes views appear once the fragment is resumed
     */
    public void  onResume() {
        super.onResume();
        this.gridViewDogs.setVisibility(View.VISIBLE);

    }

    /**
     * here i setting my adapter,checking if the database exists
     * ,call copyDatabase function,insert into my list the dogs,
     * and handle click(if the user clicks one of the diplayed items he will go to
     * the wanted page)
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return view
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //i need to inflate the view before i try to find the view
        View view = inflater.inflate(R.layout.fragment_breeds, container,true);

        setHasOptionsMenu(true);//im calling the oncreatinoptionsmenu method with this line,without this line it wont appear
        this.gridViewDogs=(GridView)view.findViewById(R.id.gridViewDogs);


        myDBHelper=new DBHelper(getContext());//creating the object
        File database=getContext().getDatabasePath(DBHelper.DATABASE_NAME);//
        //check if the database exists
        if(false==database.exists())
            myDBHelper.getReadableDatabase();

        //copies database from assests folder
        if(copyDatabase(getContext())) {
           // Toast.makeText(getContext(),"copy database succeed",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getContext(),"copy database didnt succeed",Toast.LENGTH_SHORT).show();

        }
        //get the breeds list if db exists
        myBreedsList=myDBHelper.getListDog();
        //Toast.makeText(getContext(),myBreedsList.toString(), Toast.LENGTH_LONG).show();

        final DogListAdapter adapter=new DogListAdapter(getContext(),myBreedsList);
        this.gridViewDogs.setAdapter(adapter);//setting the adapter

        //if the user clicks a picture he goes to  breedsInfo activity
        gridViewDogs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                Intent intent=new Intent(getActivity(),BreedsInfoActivity.class);
                intent.putExtra("id",myBreedsList.get(position).getId());
                intent.putExtra("myBreedsList", (Serializable) myBreedsList);
                startActivity(intent);

            }
        });

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_breeds, container, false);


    }


    /**
     *Copies my database from my local assets-folder to the just created empty database in the system folder
     *copies the data that is saved in assests folder "breeds.db"
    @param context
     */
    private boolean copyDatabase(Context context)//copies database that saved in assests
    {
        try {
            InputStream inputStream = context.getAssets().open(DBHelper.DATABASE_NAME);
            String outFileName = DBHelper.DB_PATH + DBHelper.DATABASE_NAME;//the path of the database
            OutputStream outputStream = new FileOutputStream(outFileName);//Creates a file output stream to write to the file with the specified name
            byte[] buff = new byte[1024];
            int length = 0;
            while ((length = inputStream.read(buff)) > 0) {
                outputStream.write(buff, 0, length);
            }
            outputStream.flush();//writing to the disk
            outputStream.close();
            return true;

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }






//here i create the search icon and activate filter method which i worte in DogListAdapter class
    //becaue the activity is a fragment activity i had to call this methond by writing:
  //setHasOptionsMenu(true);

    /**
     * this function inflates menu_search and makes my search view appear
     * inside those function are onQueryTextSubmit and  onQueryTextChange as well.
     * i activate filter method which i worte in DogListAdapter class
     * because the activity is a fragment activity i had to call this methond by writing:
     *setHasOptionsMenu(true);
     * @param menu
     * @param inflater
     */
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {

        inflater.inflate(R.menu.menu_search,menu);
        MenuItem item=menu.findItem(R.id.menuSearch);
        SearchView searchView=(SearchView)item.getActionView();
        final DogListAdapter resultsAdapter=new DogListAdapter(getContext(),myBreedsList);//here the results will be stored
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            /**
             * Called when the user submits the query
             * . The listener can override the standard behavior by returning true to indicate that it has
             * handled the submit request. Otherwise return false to let the SearchView handle the submission
             * by launching any associated intent.
             * @param query
             * @return
             */
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            /**
             *this function updates the results on running time-results are changing in real time every time the
             *text changes
             * @param newText
             * @return false
             */
            @Override
            public boolean onQueryTextChange(String newText) {//we can do anything to get search result here
                //this line goes to getFilter methond which is in dogListAdapter class
                resultsAdapter.getFilter().filter(newText);
                //adapter has in it the results of the search(the results are updating as the user types)
                //so in this line i update the UI and i do it by setAdapter method
                gridViewDogs.setAdapter(resultsAdapter);
                return false;
            }
        });

    }



}
