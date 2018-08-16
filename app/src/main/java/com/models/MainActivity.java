package com.models;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.dbObjects.Book;
import com.google.android.gms.samples.vision.ocrreader.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    public void openOcrActivity(View view) {

        // Create new post at /user-posts/$userid/$postid and at
        // /posts/$postid simultaneously
        String key = mDatabase.child("booksproject-41fe3").child("books").push().getKey();
        Book newBook = new Book("book name", "book author");
        Map<String, Object> booksValues = newBook.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/books/" + key, booksValues);

        mDatabase.updateChildren(childUpdates);

//        Intent openOcrActivity = new Intent(this, OcrCaptureActivity.class);
//        //openOcrActivity.putExtra("KEY_TEXT", 5);
//        startActivity(openOcrActivity);
    }
}
