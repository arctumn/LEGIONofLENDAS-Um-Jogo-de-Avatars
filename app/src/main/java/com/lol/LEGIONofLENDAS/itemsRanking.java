package com.lol.LEGIONofLENDAS;

public class itemsRanking {
    private int mImageResource;
    private String mText1;
    private int mText2;

    public itemsRanking(int mImageResource, String mText1, int mText2) {
        this.mImageResource = mImageResource;
        this.mText1 = mText1;
        this.mText2 = mText2;
    }

    public int getmImageResource() {
        return mImageResource;
    }

    public void setmImageResource(int mImageResource) {
        this.mImageResource = mImageResource;
    }

    public String getmText1() {
        return mText1;
    }

    public void setmText1(String mText1) {
        this.mText1 = mText1;
    }

    public int getmText2() {
        return mText2;
    }

    public void setmText2(int mText2) {
        this.mText2 = mText2;
    }

}
