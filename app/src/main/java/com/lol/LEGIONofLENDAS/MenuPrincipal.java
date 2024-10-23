package com.lol.LEGIONofLENDAS;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;


public class MenuPrincipal extends AppCompatActivity {
    String userid;String level;String xp;String unome;String imagem;
    Button btnloja, btnrank, btninv, btnhab, btnluta, btnlogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);
        Intent in = getIntent();
        userid = in.getStringExtra("userid");
        Utils util = new Utils();
        updateInfo(util);

        btnloja=findViewById(R.id.btn_loja);
        btnloja.setOnClickListener(v -> {
            Intent intent = new Intent(MenuPrincipal.this, Shop.class);
            intent.putExtra("userid",userid);
            intent.putExtra("exp",xp);
            startActivity(intent);
        });
        btnrank=findViewById(R.id.btn_ranking);
        btnrank.setOnClickListener(v -> {
            Intent intent = new Intent(MenuPrincipal.this, Ranking.class);
            intent.putExtra("userid",userid);
            startActivity(intent);
        });
        btninv=findViewById(R.id.btn_inventario);
        btninv.setOnClickListener(v -> {
            Intent intent = new Intent(MenuPrincipal.this, Inventario.class);
            intent.putExtra("userid",userid);
            startActivity(intent);
        });
        btnhab=findViewById(R.id.btn_habilidades);
        btnhab.setOnClickListener(v -> {
            Intent intent = new Intent(MenuPrincipal.this, LevelUp.class);
            intent.putExtra("userid",userid);
            intent.putExtra("nivel",level);
            intent.putExtra("exp",xp);
            startActivity(intent);
        });
        btnluta=findViewById(R.id.btn_luta);
        btnluta.setOnClickListener(v -> {
            Intent intent = new Intent(MenuPrincipal.this, MenuLutaOpcoes.class);
            intent.putExtra("userid",userid);
            intent.putExtra("ava",getResources().getIdentifier(imagem,"drawable",this.getPackageName()));
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

        runOnUiThread( () -> {

            System.out.println("RECEBIDO = [---->"+imagem+"<----]"+" VALOR Da IMAGEM: "+getResources().getIdentifier(imagem,"drawable",this.getPackageName()));
            avatar.setImageResource(getResources().getIdentifier(imagem,"drawable",this.getPackageName()));

            final TextView nome = findViewById(R.id.textusername);
            nome.setText(unome);
            TextView nivel = findViewById(R.id.textnivel);
            nivel.setText(String.format("%s%s", getString(R.string.main_menu_level_update), level));

            TextView exp = findViewById(R.id.textexp);
            exp.setText(String.format("%s%s", getString(R.string.main_menu_exp_update), xp));
        });
    }

    private void updateInfo(Utils util) {
        runOnUiThread(() ->{
        util.txtMessageServer(userid,"TESTINGADMIN",new ArrayList<>(Collections.singletonList("SELECT level from user where id = "+userid)));
         level = util.output.replace("\n","");
        util.txtMessageServer(userid,"TESTINGADMIN",new ArrayList<>(Collections.singletonList("SELECT xp from user where id = "+userid)));
         xp = util.output.replace("\n","");
        util.txtMessageServer(userid,"TESTINGADMIN",new ArrayList<>(Collections.singletonList("SELECT nome from user where id = "+userid)));
        unome = util.output.replace("\n","");
        util.txtMessageServer(userid,"TESTINGADMIN",new ArrayList<>(Collections.singletonList("SELECT image from user where id = "+userid)));
        String []pre = util.output.split(" ");
        imagem = pre[0];
        //Toast.makeText(this,"[---->"+imagem+"<----]",Toast.LENGTH_SHORT).show();
    });
    }
}