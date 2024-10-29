package com.lol.LEGIONofLENDAS;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.lol.LEGIONofLENDAS.Client.User;

import java.util.ArrayList;
import java.util.Collections;


public class MenuPrincipal extends AppCompatActivity {

    Button btnloja, btnrank, btninv, btnhab, btnluta, btnlogout;
    private User userData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);
        Intent in = getIntent();
        userData = User.ExtractUser(in);
        Utils util = new Utils();
        UpdateInfo(util);

        btnloja = findViewById(R.id.btn_loja);
        btnloja.setOnClickListener(v -> {
            Intent intent = new Intent(MenuPrincipal.this, Shop.class);
            intent = userData.SetUserNavigationData(intent);
            startActivity(intent);
        });
        btnrank = findViewById(R.id.btn_ranking);
        btnrank.setOnClickListener(v -> {
            Intent intent = new Intent(MenuPrincipal.this, Ranking.class);
            intent = userData.SetUserNavigationData(intent);
            startActivity(intent);
        });
        btninv = findViewById(R.id.btn_inventario);
        btninv.setOnClickListener(v -> {
            Intent intent = new Intent(MenuPrincipal.this, Inventory.class);
            intent = userData.SetUserNavigationData(intent);
            startActivity(intent);
        });
        btnhab = findViewById(R.id.btn_habilidades);
        btnhab.setOnClickListener(v -> {
            Intent intent = new Intent(MenuPrincipal.this, LevelUp.class);
            intent = userData.SetUserNavigationData(intent);
            startActivity(intent);
        });
        btnluta = findViewById(R.id.btn_luta);
        btnluta.setOnClickListener(v -> {
            Intent intent = new Intent(MenuPrincipal.this, MenuLutaOpcoes.class);
            intent = userData.SetUserNavigationData(intent);
            startActivity(intent);
        });
        btnlogout = findViewById(R.id.btn_logout);
        btnlogout.setOnClickListener(v -> {
            finish();
            Intent intent = new Intent(MenuPrincipal.this, Login.class);
            startActivity(intent);
            finish();
        });
    }

    private void UpdateInfo(Utils util) {
        runOnUiThread(() -> {
            //Update level and Experience
            util.txtMessageServer(userData.userId, "TESTINGADMIN", new ArrayList<>(Collections.singletonList("SELECT level,xp from user where id = " + userData.userId)));
            var args = util.output.split(" ");
            userData.level = Integer.parseInt(args[0]);
            userData.experience = Integer.parseInt(args[1]);
            //Update UI
            UpdateUI();
        });
    }
    private void UpdateUI(){
        final ImageView avatar = findViewById(R.id.imgAvatar);
        avatar.setImageResource(userData.image.imageId);

        final TextView nome = findViewById(R.id.textusername);
        nome.setText(userData.name);
        TextView nivel = findViewById(R.id.textnivel);
        nivel.setText(String.format("%s%s", getString(R.string.main_menu_level_update), userData.level));
        TextView exp = findViewById(R.id.textexp);
        exp.setText(String.format("%s%s", getString(R.string.main_menu_exp_update), userData.experience));
    }
}