package com.dbHandler;

import com.google.firebase.database.Exclude;

public class Upload {
    private String mName;
    private String mImageUrl;
    private String mKey;
    private boolean mCheck;

    public Upload(){
        //empty cost needed
    }

    public Upload(String name, String imageUrl){
        if(name.trim().equals("")){
            name = "No Name";
        }

        this.mName = name;
        this.mImageUrl = imageUrl;
        this.mCheck = false;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }

    @Exclude
    public String getKey() { return mKey; }

    @Exclude
    public void setKey(String key) { this.mKey = key; }

    public boolean isCheck() { return mCheck; }

    public void setCheck(boolean check) { this.mCheck = check; }
}
