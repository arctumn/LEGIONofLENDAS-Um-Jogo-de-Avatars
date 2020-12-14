package com.lol.LEGIONofLENDAS;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import java.util.ArrayList;

public class MenuLutaOpcoes extends AppCompatActivity {
    Button btnpvp, btnbots, btnamigo;
    String outputLuta;
    String id, op, ava;
    Utils util = new Utils();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_luta_opcoes);
        Intent in = getIntent();
        ava = in.getStringExtra("ava");
        id = in.getStringExtra("userid");
        ArrayList<String> luta = new ArrayList<String>();
        luta.add(id);

        btnpvp=findViewById(R.id.btn_pvp);
        btnpvp.setOnClickListener(v -> {
            op="Player";
            luta.add(op);
            util.txtMessageServer(id, "fightRandom", luta);
            outputLuta = util.output;
            Intent intent = new Intent(MenuLutaOpcoes.this, ParseBatalha.class);
            intent.putExtra("userid",id);
            intent.putExtra("ava", ava);
            intent.putExtra("strluta", outputLuta);
            startActivity(intent);


        });
        btnbots=findViewById(R.id.btn_bots);
        btnbots.setOnClickListener(v -> {
            op="Bot";
            luta.add(op);
            util.txtMessageServer(id, "fightRandom", luta);
            outputLuta = util.output;
            Intent intent = new Intent(MenuLutaOpcoes.this, Shop.class);
            intent.putExtra("userid",id);
            intent.putExtra("ava", ava);
            intent.putExtra("strluta", outputLuta);
            startActivity(intent);
        });
        btnamigo=findViewById(R.id.btn_amigo);
        btnamigo.setOnClickListener(v -> {
            Intent intent = new Intent(MenuLutaOpcoes.this, MenuAmigoOpcoes.class);
            intent.putExtra("userid",id);
            intent.putExtra("ava", ava);
            startActivity(intent);
        });

    }
}