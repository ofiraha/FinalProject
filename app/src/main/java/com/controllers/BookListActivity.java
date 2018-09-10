package com.controllers;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.dbHandler.ImageAdapter;
import com.dbHandler.Upload;
import com.google.android.gms.samples.vision.ocrreader.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class BookListActivity extends Activity
{
    Button btnpic;
    ImageView imgTakenPic;
    private static final int CAM_REQUEST=1313;

    //private Uri imageToUploadUri;
    ArrayAdapter<String> adapter;
    ArrayList<String> bookListForView;
    ArrayList<BookNode> bookList;

    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    private ProgressBar mProgressBar;
    private StorageTask mUploadTask;
    private RecyclerView mRecyclerView;
    private ImageAdapter mAdapter;
    private List<Upload> mUploads;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);

        bookListForView=new ArrayList<String>();
        adapter=new ArrayAdapter<String>(this,R.layout.book_list,R.id.bookItemText,bookListForView);
        //find the list view
        ListView listView=(ListView)findViewById(R.id.bookListView);
        listView.setAdapter(adapter);

        mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("uploads");
        mStorageRef = FirebaseStorage.getInstance().getReference().child("uploads");
        mProgressBar = findViewById(R.id.progress_bar);

        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mUploads = new ArrayList<>();

        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    Upload upload = postSnapshot.getValue(Upload.class);
                    mUploads.add(upload);
                }

                mAdapter = new ImageAdapter(BookListActivity.this, mUploads);
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(BookListActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
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
        if(mUploadTask != null && mUploadTask.isInProgress()){
            Toast.makeText(BookListActivity.this, "Upload in progress", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, CAM_REQUEST);
        }
      /*  Intent chooserIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File f = new File(Environment.getExternalStorageDirectory(), "POST_IMAGE.jpg");
        chooserIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
        imageToUploadUri = Uri.fromFile(f);
        startActivityForResult(chooserIntent, CAMERA_PHOTO);*/
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap;
        imgTakenPic  = (ImageView)findViewById(R.id.imageViewForBookCover);
        if(requestCode == CAM_REQUEST) {
            mRecyclerView.setVisibility(View.GONE);
            imgTakenPic.setVisibility(View.VISIBLE);

            bitmap = (Bitmap) data.getExtras().get("data");
            imgTakenPic.setImageBitmap(bitmap);

            TextRecognizer textRecognizer = new TextRecognizer.Builder(getApplicationContext()).build();
            Frame imageFrame = new Frame.Builder()
                    .setBitmap(bitmap)                 // your image bitmap
                    .build();

            String imageText = "";

            SparseArray<TextBlock> textBlocks = textRecognizer.detect(imageFrame);

            for (int i = 0; i < textBlocks.size(); i++) {
                TextBlock textBlock = textBlocks.get(textBlocks.keyAt(i));
                imageText = textBlock.getValue();                   // return string
            }

            //prepare file and upload it
            Bitmap bitmapToUpload = ((BitmapDrawable) imgTakenPic.getDrawable()).getBitmap();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmapToUpload.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] dataToUpload = baos.toByteArray();
            if(imageText == "" || imageText == null){imageText = "NoName"; }

            uploadFile(dataToUpload, imageText);
        }
    }

    {
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
    }

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

    private void uploadFile(byte[] bookImg, final String bookName){
            if (bookImg != null) {
                //uploading the file
                StorageReference fileRef = mStorageRef.child(bookName);
                mUploadTask = fileRef.putBytes(bookImg)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        mProgressBar.setProgress(0);
                                        imgTakenPic.setVisibility(View.GONE);
                                        mRecyclerView.setVisibility(View.VISIBLE);
                                    }
                                }, 5000);

                                Toast.makeText(BookListActivity.this, "Upload Success", Toast.LENGTH_LONG).show();

                                //add entry to current pic in db
                                taskSnapshot.getMetadata().getReference().getDownloadUrl()
                                        .addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        Upload upload = new Upload(bookName, uri.toString());
                                        String uploadId = mDatabaseRef.push().getKey(); //creates new entry on db
                                        mDatabaseRef.child(uploadId).setValue(upload); //saves the file metadata on the new entry
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception exception) {
                                        //TODO: fail to get url
                                    }
                                });
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(BookListActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                                mProgressBar.setProgress((int) progress);
                            }
                        });
            } else {
                Toast.makeText(this, "NoFile", Toast.LENGTH_LONG).show();
            }

    } //uploadFile()
}
