package com.lol.LEGIONofLENDAS.client;

import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class User {
    public String userId;
    public String name;
    public int experience;
    public int level;
    @Nullable
    public UserImage image;
    public User(String userId, String name, String experience, String level, @Nullable UserImage image) {
        this.userId = userId;
        this.name = name;
        this.experience = Integer.parseInt(experience);
        this.level = Integer.parseInt(level);
        this.image = image;
    }
    public User CreateUser (User user){
        return new User(user.userId, user.name, user.experience +"", user.level + "", user.image);
    }


    public Intent SetUserNavigationData(Intent intent){
        var json = new Gson();
        var user = CreateUser(this);
        var serializedData = json.toJson(user);
        intent.putExtra("UserData",serializedData);
        return intent;
    }
    @Nullable
    public static User ExtractUser(@NonNull Intent intent){
        var serializedData = intent.getStringExtra("UserData");
        var json = new Gson();
        try {
            return json.fromJson(serializedData,User.class);
        } catch (JsonSyntaxException ex){
            Log.e("UserData", "ExtractUser: Invalid Json Found",ex);
        }
        return null;
    }
}
