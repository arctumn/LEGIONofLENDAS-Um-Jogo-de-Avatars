package com.lol.LEGIONofLENDAS;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Collections;

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
        var luta = new ArrayList<String>();
        luta.add(id);

        btnpvp=findViewById(R.id.btn_pvp);
        btnpvp.setOnClickListener(v -> {
            op="Player";
            luta.add(op);
            util.txtMessageServer(id, "fightRandomPlayer", luta);
            outputLuta = getFightLog();
            Intent intent = new Intent(MenuLutaOpcoes.this, ParseBatalha.class);
            intent.putExtra("userid",id);
            intent.putExtra("ava", ava);
            intent.putExtra("strluta", outputLuta);
            startActivity(intent);
            finish();


        });
        btnbots=findViewById(R.id.btn_bots);
        btnbots.setOnClickListener(v -> {
            op="Bot";
            luta.add(op);
            util.txtMessageServer(id, "fightRandomBot", luta);
            outputLuta = getFightLog();
            Intent intent = new Intent(MenuLutaOpcoes.this, ParseBatalha.class);
            intent.putExtra("userid",id);
            intent.putExtra("ava", ava);
            intent.putExtra("strluta", outputLuta);
            startActivity(intent);
            finish();
        });
        btnamigo=findViewById(R.id.btn_amigo);
        btnamigo.setOnClickListener(v -> {
            Intent intent = new Intent(MenuLutaOpcoes.this, MenuAmigoOpcoes.class);
            intent.putExtra("userid",id);
            intent.putExtra("ava", ava);
            startActivity(intent);
            finish();
        });

    }

    private String getFightLog() {
        String query = "SELECT texto FROM batalhaLOG WHERE iduser ="+id+" AND visto = 0";
        util.txtMessageServer(id,"TESTINGADMIN",new ArrayList<>(Collections.singletonList(query)));
        Log.i("SERVERFIGHT",util.output);
        // recebe o texto da batalha
        String final_outputLuta = util.output;
        // atualiza a informação do lado do server
        query = "UPDATE batalhaLOG SET visto = 1 WHERE iduser = "+id+" AND visto = 0";
        util.txtMessageServer(id,"TESTINGADMIN",new ArrayList<>(Collections.singletonList(query)));
        Log.i("TESTLISTA",final_outputLuta);
        return final_outputLuta;
    }
}