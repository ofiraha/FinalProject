package com.controllers;

import java.util.List;

public class ToReadList extends BookList{

    public ToReadList() {
    }

    public ToReadList(List<BookNode> bookList) {
        super(bookList);
    }

    //CheckBook: gets a bookNode and checked it as read book
    public void CheckBook(BookNode bookNode)
    {
        bookNode.setBookChecked(true);
    }

    //UnCheckBook: gets a bookNode and unchecked it
    public void UnCheckBook(BookNode bookNode)
    {
        bookNode.setBookChecked(false);
    }

    //TODO: readNewBook:
    public void readNewBook()
    {

    }

    //addBook: this function
    @Override
    public void addBook(BookNode newBook) {

        bookList.add(newBook);
    }



}

