package com.lol.LEGIONofLENDAS;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


public class MenuPrincipal extends AppCompatActivity {
    Button btnloja, btnrank, btninv, btnhab, btnluta, btnlogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);
        Intent in = getIntent();

        btnloja=findViewById(R.id.btn_loja);
        btnloja.setOnClickListener(v -> {
            Intent intent = new Intent(MenuPrincipal.this, Shop.class);
            startActivity(intent);
        });
        btnrank=findViewById(R.id.btn_ranking);
        btnrank.setOnClickListener(v -> {
            Intent intent = new Intent(MenuPrincipal.this, Shop.class);
            startActivity(intent);
        });
        btninv=findViewById(R.id.btn_inventario);
        btninv.setOnClickListener(v -> {
            Intent intent = new Intent(MenuPrincipal.this, Inventario.class);
            startActivity(intent);
        });
        btnhab=findViewById(R.id.btn_habilidades);
        btnhab.setOnClickListener(v -> {
            Intent intent = new Intent(MenuPrincipal.this, Shop.class);
            startActivity(intent);
        });
        btnluta=findViewById(R.id.btn_luta);
        btnluta.setOnClickListener(v -> {
            Intent intent = new Intent(MenuPrincipal.this, QrCamera.class);
            intent.putExtra("id",in.getStringExtra("id"));
            startActivity(intent);
        });
        btnlogout=findViewById(R.id.btn_logout);
        btnlogout.setOnClickListener(v -> {
            finish();
            Intent intent = new Intent(MenuPrincipal.this, Login.class);
            startActivity(intent);
            finish();
        });


       final ImageView avatar = findViewById(R.id.imgAvatar);

       int id = Integer.parseInt(in.getStringExtra("imagem"));
        avatar.setImageResource(id);

        final TextView nome = findViewById(R.id.textusername);
        nome.setText(in.getStringExtra("nome"));

        final TextView nivel = findViewById(R.id.textnivel);
        nivel.setText(String.format("%s%s", getString(R.string.main_menu_level_update), in.getStringExtra("nivel")));

        final TextView exp = findViewById(R.id.textexp);
        exp.setText(String.format("%s%s", getString(R.string.main_menu_exp_update), in.getStringExtra("exp")));





    }
}