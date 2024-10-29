package com.lol.LEGIONofLENDAS.fight;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.lol.LEGIONofLENDAS.R;
import com.lol.LEGIONofLENDAS.client.User;
import com.lol.LEGIONofLENDAS.fight.qrcode.QrCamera;
import com.lol.LEGIONofLENDAS.fight.qrcode.QrcodeStarter;

public class MenuAmigoOpcoes extends AppCompatActivity {
    Button btnler, btngerar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_amigo_opcoes);

        Intent in = getIntent();
        var user = User.ExtractUser(in);
        assert user != null;

        btnler=findViewById(R.id.btn_qrcamera);
        btnler.setOnClickListener(v -> {
            Intent intent = new Intent(MenuAmigoOpcoes.this, QrCamera.class);
            intent = user.SetUserNavigationData(intent);
            startActivity(intent);
        });
        btngerar=findViewById(R.id.btn_qrstart);
        btngerar.setOnClickListener(v -> {
            Intent intent = new Intent(MenuAmigoOpcoes.this, QrcodeStarter.class);
            intent = user.SetUserNavigationData(intent);
            startActivity(intent);
        });
    }
}