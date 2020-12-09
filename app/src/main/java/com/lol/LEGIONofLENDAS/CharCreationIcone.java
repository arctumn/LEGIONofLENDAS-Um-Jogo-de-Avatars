package com.lol.LEGIONofLENDAS;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

public class CharCreationIcone extends AppCompatActivity {

    List<Integer> listImages = new ArrayList<>();


    /**
     * FIXED UI CONSTRAINTS
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_char_creation_icone);
        initData();

        ViewPager pager = findViewById(R.id.pagerCreate);
        MyAdapter adapter = new MyAdapter(listImages, getBaseContext());
        pager.setAdapter(adapter);

        //Log.i("AQUIpager", String.valueOf(pager.getCurrentItem()));
        final Button button = findViewById(R.id.btnEscolheAva);
        button.setOnClickListener(v -> {

            Toast.makeText(getApplicationContext(),"Clicked image ", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(CharCreationIcone.this, MenuPrincipal.class);
            Log.i("char", String.valueOf(pager.getCurrentItem()));
            intent.putExtra("char", "ava"+pager.getCurrentItem());
            startActivity(intent);
        });

    }

    private void initData() {
        listImages.add(R.drawable.ava0);
        listImages.add(R.drawable.ava1);
        listImages.add(R.drawable.ava2);
        listImages.add(R.drawable.ava3);
        listImages.add(R.drawable.ava4);
        listImages.add(R.drawable.ava5);
        listImages.add(R.drawable.ava6);
        listImages.add(R.drawable.ava7);
        listImages.add(R.drawable.ava8);
        listImages.add(R.drawable.ava9);
    }
}