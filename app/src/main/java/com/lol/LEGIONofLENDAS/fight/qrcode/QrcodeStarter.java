package com.lol.LEGIONofLENDAS.fight.qrcode;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Button;


import com.lol.LEGIONofLENDAS.client.User;
import com.lol.LEGIONofLENDAS.fight.ParseBatalha;
import com.lol.LEGIONofLENDAS.R;
import com.lol.LEGIONofLENDAS.utils.Utils;

import java.util.ArrayList;
import java.util.Collections;

public class QrcodeStarter extends AppCompatActivity {
    Button lutar;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode_starter);
        WebView qr_code = findViewById(R.id.getQRCODE);

        Intent in = getIntent();
        user = User.ExtractUser(in);
        assert user != null;
        String url1 = "https://quickchart.io/chart?cht=qr&chs=300x300&chl="+user.userId;
        qr_code.setWebChromeClient(new WebChromeClient());
        qr_code.loadUrl(url1);

        Utils util = new Utils();

        lutar = findViewById(R.id.buttonFIGHT);
        lutar.setOnClickListener(v ->{
            String query = "SELECT texto FROM batalhaLOG WHERE iduser ="+user.userId+" AND visto = 0";
            util.txtMessageServer(user.userId,"TESTINGADMIN",new ArrayList<>(Collections.singletonList(query)));
            Log.i("SERVERFIGHT",util.output);
            // recebe o texto da batalha
            String batalha = util.output;
            // atualiza a informação do lado do server
            query = "UPDATE batalhaLOG SET visto = 1 WHERE iduser = "+user.userId+" AND visto = 0";
            util.txtMessageServer(user.userId,"TESTINGADMIN",new ArrayList<>(Collections.singletonList(query)));

            Intent out = new Intent(this, ParseBatalha.class);
            out = user.SetUserNavigationData(out);
            out.putExtra("strluta",batalha);
            startActivity(out);
        });
    }
}