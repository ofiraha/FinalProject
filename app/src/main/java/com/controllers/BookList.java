
package com.controllers;

import com.dbHandler.Book;

import java.util.List;

public abstract class BookList
{
    protected List<BookNode> bookList;

    public BookList() {
    }

    public BookList(List<BookNode> bookList) {
        bookList = bookList;
    }

    //Todo: to create a list of books ,
    public abstract void addBook(BookNode newBook);

    //TODO: public void ReadBookFromDb()

    public boolean IsBookInDb(BookNode bookToCheck)
    {
        boolean isBookInDb=false;
        return isBookInDb;
    }

}

