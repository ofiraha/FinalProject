package com.controllers;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.samples.vision.ocrreader.R;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class BookListActivity extends Activity
{
    private static final int CAMERA_PHOTO = 111;
    private Uri imageToUploadUri;
    ArrayAdapter<String> adapter;
    ArrayList<String> bookListForView;
    ArrayList<BookNode> bookList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);

        bookListForView=new ArrayList<String>();
        adapter=new ArrayAdapter<String>(this,R.layout.book_list,R.id.bookItemText,bookListForView);
        //find the list view
        ListView listView=(ListView)findViewById(R.id.bookListView);
        listView.setAdapter(adapter);

    }

    private void addListeners()
    {
        Button addButton=(Button)findViewById(R.id.addBookToListButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //open the camera and take a picture of the name of the book
                captureCameraImage();
/*
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,100);
*/

                //TODO: IMPORTANT!!! THIS - TAKE THE TEXT AND ADD IT TO THE LIST
                /*
                //add to the book node the name and the author- take from the camera
                //Todo: take the name from the camera
                String name = "harry poter";
                String author= "hadar cohen";

                //BookNode bookNode=new BookNode(name, author,true);
                // add new item to arraylist
               // bookList.add(bookNode);

                bookListForView.add(name);
                // notify listview of data changed
                adapter.notifyDataSetChanged();*/
            }

        });

    }
    private void captureCameraImage() {
        Intent chooserIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File f = new File(Environment.getExternalStorageDirectory(), "POST_IMAGE.jpg");
        chooserIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
        imageToUploadUri = Uri.fromFile(f);
        startActivityForResult(chooserIntent, CAMERA_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA_PHOTO && resultCode == Activity.RESULT_OK) {
            if(imageToUploadUri != null){
                Uri selectedImage = imageToUploadUri;
                getContentResolver().notifyChange(selectedImage, null);
               // Bitmap bitmap = getBitmap(imageToUploadUri.getPath());
                //Frame frame = new Frame.Builder().setBitmap(bitmap).build();
               //TextRecognizer textRecognizer = new TextRecognizer.Builder(this).build();

               //SparseArray<TextBlock> item = textRecognizer.detect(frame);
            }
            else{
                Toast.makeText(this,"Error while capturing Image",Toast.LENGTH_LONG).show();
            }
        }
    }


  /*  private Bitmap getBitmap(String path) {

        Uri uri = Uri.fromFile(new File(path));
        InputStream in = null;
        try {
            final int IMAGE_MAX_SIZE = 1200000; // 1.2MP
            ContentResolver c=getContentResolver();
            in=c.openInputStream(uri);

            // Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(in, null, o);
            in.close();


            int scale = 1;
            while ((o.outWidth * o.outHeight) * (1 / Math.pow(scale, 2)) >
                    IMAGE_MAX_SIZE) {
                scale++;
            }
            Log.d("", "scale = " + scale + ", orig-width: " + o.outWidth + ", orig-height: " + o.outHeight);

            Bitmap b = null;
            in = getContentResolver().openInputStream(uri);
            if (scale > 1) {
                scale--;
                // scale to max possible inSampleSize that still yields an image
                // larger than target
                o = new BitmapFactory.Options();
                o.inSampleSize = scale;
                b = BitmapFactory.decodeStream(in, null, o);

                // resize to desired dimensions
                int height = b.getHeight();
                int width = b.getWidth();
                Log.d("", "1th scale operation dimenions - width: " + width + ", height: " + height);

                double y = Math.sqrt(IMAGE_MAX_SIZE
                        / (((double) width) / height));
                double x = (y / height) * width;

                Bitmap scaledBitmap = Bitmap.createScaledBitmap(b, (int) x,
                        (int) y, true);
                b.recycle();
                b = scaledBitmap;

                System.gc();
            } else {
                b = BitmapFactory.decodeStream(in);
            }
            in.close();

            Log.d("", "bitmap size - width: " + b.getWidth() + ", height: " +
                    b.getHeight());
            return b;
        } catch (IOException e) {
            Log.e("", e.getMessage(), e);
            return null;
        }
    }*/

   public void addBookToList(View view)
    {
        addListeners();



       /* //LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
       // View bookListView = inflater.inflate(R.layout.book_list, null);
        //the book list -TODO : CREATE THE LIST IN ANOTHER PLACE
        ArrayList<BookNode>  bookList= new ArrayList<BookNode>();

        //add to the book node the name and the author- take from the camera
        //Todo: take the name from the camera
        String name = "harry poter";
        String author= "hadar cohen";

        BookNode bookNode=new BookNode(name, author,true);
        bookList.add(bookNode);
        // Find the ScrollView
     //   ScrollView bookListScrollView = (ScrollView) bookListView.findViewById(R.id.scrollViewForBookList);

        ListView listView = (ListView) findViewById(R.id.BookListView);

        //set the adapter to list view
        adapter=new ListAdapter(this,bookList);
        listView.setAdapter(adapter);


        adapter
        adapter.notifyDataSetChanged();



       LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
       View bookListView = inflater.inflate(R.layout.book_list, null);

        // Find the ScrollView
        ScrollView bookListScrollView = (ScrollView) bookListView.findViewById(R.id.scrollViewForBookList);

        // Create a LinearLayout element
        LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);

        // Add text
        TextView tv = new TextView(this);
        tv.setText("my text");
        ll.addView(tv);

        //TODO: add book to book list
        //TODO: after the camera open and the user picture the name of the book -
        //take the text and add to the list:
        //m_name= , m_author= ;
        //bookNode= new , bookList.add;


        // Add the LinearLayout element to the ScrollView
        bookListScrollView.addView(ll);

        // Display the view
        setContentView(bookListView);*/
    }

}
