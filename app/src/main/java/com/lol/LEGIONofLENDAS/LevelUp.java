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
        final ImageView avatar = findViewById(R.id.lvlUpAvatar);

        int id = Integer.parseInt(in.getStringExtra("imagem"));
        avatar.setImageResource(id);

        final TextView nome = findViewById(R.id.lvlUpNome);
        nome.setText(in.getStringExtra("nome"));

        final TextView nivel = findViewById(R.id.lvlUpNivel);
        nivel.setText(String.format("%s%s", getString(R.string.main_menu_level_update), in.getStringExtra("nivel")));

        final TextView exp = findViewById(R.id.lvlUpXP);
        exp.setText(String.format("%s%s", getString(R.string.main_menu_exp_update), in.getStringExtra("exp")));

        Button btnlvlup  = (Button) findViewById(R.id.btnLvlUp);
        if(!exp.equals("\"EXP:\"100")){
            btnlvlup.setEnabled(false);
            btnlvlup.setOnClickListener(v -> {

            });
        }


    }
}