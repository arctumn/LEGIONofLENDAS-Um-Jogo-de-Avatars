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
                             //cliente.operacaoNaBD("4","DROP TABLE IF EXISTS user");
                             //cliente.operacaoNaBD("4","SELECT * FROM user ORDER BY id DESC LIMIT 1;");
                             cliente.inserirUserNaDB("AdminTest","password","999","123214213");
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