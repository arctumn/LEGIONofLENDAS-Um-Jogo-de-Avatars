package com.lol.LEGIONofLENDAS;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.lol.LEGIONofLENDAS.Client.User;

import java.util.ArrayList;
import java.util.Collections;

public class MenuLutaOpcoes extends AppCompatActivity {
    Button btnpvp, btnbots, btnamigo;
    String outputLuta;
    String op;
    Utils util = new Utils();
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_luta_opcoes);
        Intent in = getIntent();
        user = User.ExtractUser(in);
        var luta = new ArrayList<String>();
        luta.add(user.userId);

        btnpvp=findViewById(R.id.btn_pvp);
        btnpvp.setOnClickListener(v -> {
            op="Player";
            luta.add(op);
            util.txtMessageServer(user.userId, "fightRandomPlayer", luta);
            outputLuta = getFightLog();
            Intent intent = new Intent(MenuLutaOpcoes.this, ParseBatalha.class);
            intent = user.SetUserNavigationData(intent);
            intent.putExtra("strluta", outputLuta);
            startActivity(intent);
            finish();


        });
        btnbots=findViewById(R.id.btn_bots);
        btnbots.setOnClickListener(v -> {
            op="Bot";
            luta.add(op);
            util.txtMessageServer(user.userId, "fightRandomBot", luta);
            outputLuta = getFightLog();
            Intent intent = new Intent(MenuLutaOpcoes.this, ParseBatalha.class);
            intent = user.SetUserNavigationData(intent);
            intent.putExtra("strluta", outputLuta);
            startActivity(intent);
            finish();
        });
        btnamigo=findViewById(R.id.btn_amigo);
        btnamigo.setOnClickListener(v -> {
            Intent intent = new Intent(MenuLutaOpcoes.this, MenuAmigoOpcoes.class);
            intent = user.SetUserNavigationData(intent);
            startActivity(intent);
            finish();
        });

    }

    private String getFightLog() {
        String query = "SELECT texto FROM batalhaLOG WHERE iduser ="+user.userId+" AND visto = 0";
        util.txtMessageServer(user.userId,"TESTINGADMIN",new ArrayList<>(Collections.singletonList(query)));
        Log.i("SERVERFIGHT",util.output);
        // recebe o texto da batalha
        String final_outputLuta = util.output;
        // atualiza a informação do lado do server
        query = "UPDATE batalhaLOG SET visto = 1 WHERE iduser = "+user.userId+" AND visto = 0";
        util.txtMessageServer(user.userId,"TESTINGADMIN",new ArrayList<>(Collections.singletonList(query)));
        Log.i("TESTLISTA",final_outputLuta);
        return final_outputLuta;
    }
}