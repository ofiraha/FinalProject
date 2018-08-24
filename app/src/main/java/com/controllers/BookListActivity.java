package com.controllers;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.gms.samples.vision.ocrreader.R;

public class BookListActivity extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_list);
    }

    public void addBookToList(View view)
    {
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

        // Add the LinearLayout element to the ScrollView
        bookListScrollView.addView(ll);

        // Display the view
        setContentView(bookListView);
    }

}
