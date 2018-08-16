package com.dbObjects;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Book
{
    private String mName;
    private String mAuthor;

    public Book() {
    }

    public Book(String name, String author) {
        this.mName = name;
        this.mAuthor = author;
    }

    public String getName() {
        return mName;
    }

    public void setName(String m_name) {
        this.mName = m_name;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public void setAuthor(String m_author) {
        this.mAuthor = m_author;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("name", mName);
        result.put("author", mAuthor);

        return result;
    }
}
