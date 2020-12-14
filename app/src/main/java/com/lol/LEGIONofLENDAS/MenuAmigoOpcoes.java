package com.lol.LEGIONofLENDAS;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MenuAmigoOpcoes extends AppCompatActivity {
    Button btnler, btngerar;
    String userid, ava;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_amigo_opcoes);

        Intent in = getIntent();
        userid = in.getStringExtra("userid");
        ava = in.getStringExtra("ava");

        btnler=findViewById(R.id.btn_qrcamera);
        btnler.setOnClickListener(v -> {
            Intent intent = new Intent(MenuAmigoOpcoes.this, QrCamera.class);
            intent.putExtra("userid",userid);
            intent.putExtra("ava", ava);
            startActivity(intent);
        });
        btngerar=findViewById(R.id.btn_qrstart);
        btngerar.setOnClickListener(v -> {
            Intent intent = new Intent(MenuAmigoOpcoes.this, QrcodeStarter.class);
            intent.putExtra("userid",userid);
            intent.putExtra("ava", ava);
            startActivity(intent);
        });
    }
}