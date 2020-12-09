package com.lol.LEGIONofLENDAS;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;


public class MenuPrincipal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);

        final ImageView avatar = findViewById(R.id.imgAvatar);

        Intent intent = getIntent();

        String charImg = intent.getStringExtra("char");


        int id = getResources().getIdentifier(charImg, "drawable", getPackageName());
        avatar.setImageResource(id);
    }
}