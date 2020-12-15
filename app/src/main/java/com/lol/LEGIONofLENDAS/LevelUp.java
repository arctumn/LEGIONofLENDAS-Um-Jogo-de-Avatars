package com.lol.LEGIONofLENDAS;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class LevelUp extends AppCompatActivity {

    Button btnlvlup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_up);
        Intent in = getIntent();

        PrimeXP prime = new PrimeXP(Integer.parseInt(in.getStringExtra("nivel")));
        //int  next = prime.getNextXP();

        Button btnlvlup  = (Button) findViewById(R.id.btnLvlUp);
        if(true){//next == Integer.parseInt(in.getStringExtra("exp"))){
            btnlvlup.setEnabled(true);
            btnlvlup.setOnClickListener(v -> {

            });
        }else{
            btnlvlup.setEnabled(false);
        }


    }
}