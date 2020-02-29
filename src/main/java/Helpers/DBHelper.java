package Helpers;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import entities.Dog;

/**
 * Created by gal on 03/05/2018.
 */

public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "breeds.db";//the name of the database
    public static String DB_PATH = "data/data/com.example.gal.dogtime/databases/";//the path of the sql database
    private SQLiteDatabase myDataBase;
    private final Context myContext;


    /**
     * Constructor
     * Takes and keeps a reference of the passed context in order to access to the application assets and resources.
     *
     * @param context
     */
    public DBHelper(Context context) {

        super(context, DATABASE_NAME, null, 1);
        this.myContext = context;
    }

    /**
     *called when the database is created for the first time.
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    /**
     * Called when the database needs to be upgraded
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /**
     * open the database if its not opened and null
     */
    public void openDataBase() throws SQLException {

        //Opens the database
        String myPath = myContext.getDatabasePath(DATABASE_NAME).getPath();
        //if its not null or its open
        if (myDataBase != null && myDataBase.isOpen()) {
            return;
        }
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
    }
/*
closes the database
 */
    public void closeDataBase() {
        if (myDataBase != null) {
            myDataBase.close();
        }
    }

    /**
     * this function reads the information thats saved in the database and inserts it into a list
     * this function inserts into a list all the dogs that are saved in te database
     * @return breedList
     */
    public List<Dog> getListDog() {
        Dog dog = null;
        List<Dog> breedList = new ArrayList<>();
        openDataBase();
        Cursor cursor = myDataBase.rawQuery("SELECT * FROM all_breeds5 ORDER BY id ASC", null);

        cursor.moveToNext();//the raw in place 0 is null(althought that in the table is not) so i start from 1
        while (!cursor.isAfterLast()) {

            dog = new Dog(cursor.getInt(0), cursor.getString(1), cursor.getString(2),
                    cursor.getString(3), cursor.getString(4), cursor.getString(5),
                    cursor.getString(6), cursor.getString(7), cursor.getString(8),
                    cursor.getString(9), cursor.getString(10));

            //adding to the list
            breedList.add(dog);
            cursor.moveToNext();
        }
        cursor.close();
        closeDataBase();

        return breedList;
    }

}





