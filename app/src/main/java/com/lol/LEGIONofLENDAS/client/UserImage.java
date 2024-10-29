package com.lol.LEGIONofLENDAS.client;

import android.content.res.Resources;

public class UserImage {
    public String imageName;
    public int imageId;
    private transient Resources android;
    private transient String packageName;

    public UserImage(String imageName, Resources android, String packageName) {
        this.imageName = imageName;
        this.android = android;
        this.packageName = packageName;
        this.imageId = this.android.getIdentifier(imageName,"drawable",this.packageName);
    }
}
