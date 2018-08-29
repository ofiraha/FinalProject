package com.controllers;

import com.dbHandler.*;

public class BookNode extends Book
{
    private boolean isBookChecked;

    public BookNode(boolean isBookChecked) {
        this.isBookChecked = isBookChecked;
    }

    public BookNode(String name, String author, boolean isBookChecked) {
        super(name, author);
        this.isBookChecked = isBookChecked;
    }

    public boolean isBookChecked() {
        return isBookChecked;
    }

    public void setBookChecked(boolean bookChecked) {
        isBookChecked = bookChecked;
    }
}
