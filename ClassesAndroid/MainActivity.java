package com.LOL.LEGIONofLENDAS;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button mostrar;
    TextView txtmostrar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mostrar = findViewById(R.id.button_mostrar);
        txtmostrar = findViewById(R.id.mostrar);

        mostrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runSocketExample();

            }
        });
    }

    private void runSocketExample() {
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try  {
                    final Client cliente = new Client();
                     //final String string = cliente.operacaoNaBD("1", "SELECT * FROM user");
                     //ArrayList<String> a = new ArrayList<String>(); a.add("Feijao");
                     final String string2 =
                             //cliente.rankingByXpTopN(5);
                             //cliente.operacaoNaBD("-1","DROP TABLE IF EXISTS TESTUSERTABL");
                             //cliente.operacaoNaBD("1","SELECT * FROM user ORDER BY id DESC LIMIT 1;");
                             //cliente.inserirUserNaDB("AdminTest","password","999","123214213","124121");
                             cliente.login("AdminTest","password");
                             //cliente.operacaoNaBD("-1","INSERT INTO user (id,nome,password,level,xp,image) VALUES (0, '----','----', 0, 0, 0)");
                             //cliente.operacaoNaBD("-1","INSERT INTO user (id,nome,password,level,xp,image)  VALUES (1, 'ADMIN','ADMIN', 999, 5234201, 0)");
                             //cliente.operacaoNaBD("1","SELECT name FROM sqlite_master WHERE type ='table' AND name NOT LIKE 'sqlite_%';");
                     //Log.i("SERVERLOL",string);


                    runOnUiThread(new Runnable() {
                        public void run() {
                            txtmostrar.setText(string2);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
    }
}