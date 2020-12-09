package com.lol.LEGIONofLENDAS;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class CharCreationInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_char_creation_info);
        boolean finish = true;
        if(finish){
            Intent intent = new Intent(CharCreationInfo.this, CharCreationIcone.class);
            startActivity(intent);
        }
    }
}