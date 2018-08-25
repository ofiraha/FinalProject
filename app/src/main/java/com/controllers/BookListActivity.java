package com.controllers;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.gms.samples.vision.ocrreader.R;

import java.util.ArrayList;

public class BookListActivity extends Activity
{
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

        Button addButton=(Button)findViewById(R.id.addBookToListButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //add to the book node the name and the author- take from the camera
                //Todo: take the name from the camera
                String name = "harry poter";
                String author= "hadar cohen";

                //BookNode bookNode=new BookNode(name, author,true);
                // add new item to arraylist
               // bookList.add(bookNode);

                bookListForView.add(name);
                // notify listview of data changed
                adapter.notifyDataSetChanged();
            }

        });


    }

/*    public void addBookToList(View view)
    {
        //LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
        setContentView(bookListView);
    }*/

}
