package com.example.gal.dogtime;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * <p>
 * to handle interaction events.
 */
public class PuzzleFragment extends Fragment implements View.OnClickListener {
    public static final int IMAGE_GALLERY_REQUEST = 20;//could be any number
    Integer REQUEST_CAMERA = 1;
    Integer SELECT_FILE = 0;
    String mCurrentPhotoPath;
    private Uri photoURI;
    private EditText puzzleSize;//here the user insert the size of the puzzle he wants
    private Button imageBtn;//once you click this button it leads you to the gallry in order to choose image
    private Button tutorials;
     public String numString=0+" ";
    public int columns;//the size that the user picks


    public PuzzleFragment() {
        // Required empty public constructor
    }

    /**
     * once the user leaves the gallery this function will be invoked
     *this function makes the ui disappear(imagebtn,puzzleSize...)
     */
    public void onPause() {

        super.onPause();
        this.imageBtn.setVisibility(View.GONE);
        this.puzzleSize.setVisibility(View.GONE);
        this.tutorials.setVisibility(View.GONE);

    }

    /**
     * makes the views visible again will be invoked when the fragment is resumed
     */
    public void  onResume() {
        super.onResume();
        this.imageBtn.setVisibility(View.VISIBLE);
        this.puzzleSize.setVisibility(View.VISIBLE);
        this.tutorials.setVisibility(View.VISIBLE);

    }

    /**
     * here i init the views and with onClick listener i handle each click the user makes
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_puzzle, container, false);

        puzzleSize = (EditText) view.findViewById(R.id.puzzleSize);
        imageBtn = (Button) view.findViewById(R.id.imageBtn);
        tutorials = (Button) view.findViewById(R.id.tutorials);

        imageBtn.setOnClickListener(this);//setting a listener

        tutorials.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),PuzzleTutorialsActivity.class);

                startActivity(intent);

            }
        });



        return view;
    }

    /**
     *returns true if the user typed anything(he can insert numbers only)
     * an returns false if the user didnt insert anything in the editText where he supposed to
     * type the puzzle size he wants
     * <p>
     *  this function checks if the editText which you insert the puzzle size is not empty
     * </p>
     * @return  true or false
     */

    public boolean checkPuzzleSize() {
        String s1 = puzzleSize.getText().toString();
        if (s1.length() == 0) {
            Toast.makeText(getActivity(), "fill the puzzle size", Toast.LENGTH_LONG).show();
            return false;
        }
        numString=puzzleSize.getText().toString();
        double num=Integer.parseInt(numString);
        if(num>10) {
            Toast.makeText(getActivity(), "the number is too big!", Toast.LENGTH_LONG).show();
            return false;
        }
            if(num<=1) {
                Toast.makeText(getActivity(), "please eneter a number greater than 1!", Toast.LENGTH_LONG).show();
                return false;
            }
        return true;
        }


    /**
     * this function invoked when the user click the button
     * inside it it calls the function checkPuzzleSize which return true or false
     * if the user didnt type anything he will see a toast message indicating he didnt insert anything
     * if the user did inert a number he will go to the gallry to pick an image for the puzzle
     * {@link #checkPuzzleSize()}
     * @param v
     */
    @Override
    public void onClick(View v) {
//if the user didnt type anything he will see a toast message
        if(!checkPuzzleSize())
            return;
        numString=puzzleSize.getText().toString();
        columns=Integer.parseInt(numString);
        selectImage();
        ///onImageGalleryClicked(v);//activiting this function

    }

    /**
     * here i display the dialog which the user will choose from where he want to select the picture
     * gallery or camera
     */
    private void selectImage()
    {
        final CharSequence[] items={"Camera","Gallery","Cancel"};
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        builder.setTitle("Add Image");

        //set a list of items to be displayed in the dialog as the content
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                if(items[i].equals("Camera")){
                    dispatchTakePictureIntent();

                }
                else if(items[i].equals("Gallery")){
                    Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(intent.createChooser(intent,"Select File"),SELECT_FILE);

                }
                else if(items[i].equals("Cancel")){
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }



    /**
     *here i prepare empty file in uri format for the picture the pic the user will take
     *
     *
     */
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getContext().getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                 photoURI = null;
                try {
                    photoURI = FileProvider.getUriForFile(getActivity(),
                            BuildConfig.APPLICATION_ID + ".provider", createImageFile());//prepares an empty file in uri format for the picture the pic the user will take
                } catch (IOException e) {
                    e.printStackTrace();
                }
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_CAMERA);//user goes to camera here
            }
        }
    }

    /**
     * this method creates an empty file that ill store the image that was aken from camera in it
     * @return
     * @throws IOException
     */
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }




    /**
     * Receive the result from a previous call to startActivityForResult(Intent, int).
     * here i handle each case camera,and gallery
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode== Activity.RESULT_OK)
        {
            //camera
            if(requestCode==REQUEST_CAMERA){
                Intent in1 = new Intent(getActivity(), DisplayPuzzleActivity.class);
                in1.putExtra("imagePath", photoURI.toString());//im sending the string uri address
                in1.putExtra("columns", columns);
                startActivity(in1);



            }
            //gallery
            else if(requestCode==SELECT_FILE){
                Uri selectedImageUri=data.getData();
                Intent in1 = new Intent(getActivity(), DisplayPuzzleActivity.class);
                in1.putExtra("imagePath", selectedImageUri.toString());//im sending the string uri address
                in1.putExtra("columns", columns);
                startActivity(in1);


            }

        }
    }






}


