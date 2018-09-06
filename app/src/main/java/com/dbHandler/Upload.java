package com.dbHandler;

public class Upload {
    private String mName;
    private String mImageUrl;

    public Upload(){
        //empty cost needed
    }

    public Upload(String name, String imageUrl){
        if(name.trim().equals("")){
            name = "No Name";
        }

        this.mName = name;
        this.mImageUrl = imageUrl;
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
}
