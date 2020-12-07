package com.LOL.LEGIONofLENDAS;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
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
                messageSERVER();
            }
        });
    }
    /**
    * some Use Cases IGNORE USE VIA MESSAGESERVER
    * cliente.rankingByXpTopN(5);
    * cliente.operacaoNaBD("-1","DROP TABLE IF EXISTS loja");
    * cliente.operacaoNaBD("-1","CREATE TABLE loja (ID INT PRIMARY KEY NOT NULL, itemName TEXT NOT NULL, forca INT NOT NULL, magia INT NOT NULL, defesa INT NOT NULL, defesaMagica INT NOT NULL, vida INT NOT NULL, price INT NOT NULL)");
    * cliente.operacaoNaBD("1","SELECT * FROM user ORDER BY id DESC LIMIT 1;");
    * cliente.inserirUserNaDB("AdminTest","password","999","123214213","124121");
    * cliente.login("AdminTest","password");
    * cliente.operacaoNaBD("-1","INSERT INTO user (id,nome,password,level,xp,image) VALUES (0, '----','----', 0, 0, 0)");
    * cliente.operacaoNaBD("-1","INSERT INTO user (id,nome,password,level,xp,image)  VALUES (1, 'ADMIN','ADMIN', 999, 5234201, 0)");
    * cliente.operacaoNaBD("1","SELECT * FROM sqlite_master WHERE type ='table' AND name NOT LIKE 'sqlite_%';");
    */
    private void messageSERVER() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try  {
                    /*
                      Adicionar argumentos conforme a necessidade de comunicar
                      se não precisar atualizar a ui na hora comente o UIThread
                      provavelmente vai ser preciso sempre tal que de todas as outras
                      vezes não atualizou o texto porque terminou e continuou e enviou o output
                      que a gente não experava, TOTRY:talvez uma solução seja retornar outro algo que seja atualizado do
                      outro lado, provavelmente vai explodir num nullException ou num networkException
                      callservice method(ID,TASK,ARGUMENTS FOR TASK) check client functions
                     */
                    final ArrayList<String> args = new ArrayList<>();
                    args.add("AdminTest");
                    args.add("password");
                    final String string2 = oMS.callService("1","login",args);

                    runOnUiThread(new Runnable() {
                        public void run() {
                            txtmostrar.setText(string2);
                            Log.i("SERVERLOL","DEBUG");
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }
    /**
     * Ter sempre uma copia desta parte nas classes que forem chamar o MessageServer
     * sem isto o serviço não consegue comunicar com o servidor
     */
    MessageServer oMS;
    boolean bVinculo = false;

    @Override
    protected void onStart(){
        super.onStart();
        Intent intent = new Intent (this, MessageServer.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }
    @Override
    protected void onStop(){
        super.onStop();

        if (bVinculo){
            unbindService(mConnection);
            bVinculo = false;
        }
    }

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MessageServer.BinderLocal binder = (MessageServer.BinderLocal) service;
            oMS = binder.getService();
            bVinculo = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            bVinculo = false;
        }
    };
}