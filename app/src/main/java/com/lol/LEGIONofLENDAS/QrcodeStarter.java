package com.lol.LEGIONofLENDAS;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Button;


import java.util.ArrayList;
import java.util.Collections;

public class QrcodeStarter extends AppCompatActivity {
    QrcodeStarter var = this;
    Button lutar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode_starter);
        WebView qr_code = findViewById(R.id.getQRCODE);

        Intent in = getIntent();
        String id = in.getStringExtra("userid");

        String url1 = "https://chart.googleapis.com/chart?cht=qr&chs=300x300&chl="+id;
        qr_code.setWebChromeClient(new WebChromeClient());
        qr_code.loadUrl(url1);

        Utils util = new Utils();

        lutar = findViewById(R.id.buttonFIGHT);
        lutar.setOnClickListener(v ->{
            String query = "SELECT texto FROM batalhaLOG WHERE iduser ="+id+" AND visto = 0";
            util.txtMessageServer(id,"TESTINGADMIN",new ArrayList<>(Collections.singletonList(query)));
            Log.i("SERVERFIGHT",util.output);
            // recebe o texto da batalha
            String batalha = util.output;
            // atualiza a informação do lado do server
            query = "UPDATE batalhaLOG SET visto = 1 WHERE iduser = "+id+" AND visto = 0";
            util.txtMessageServer(id,"TESTINGADMIN",new ArrayList<>(Collections.singletonList(query)));

            Intent out = new Intent(var, ParseBatalha.class);
            out.putExtra("strluta",batalha);
            startActivity(out);
        });
    }
}