package com.lol.LEGIONofLENDAS;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;


public class MenuPrincipal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);

        final ImageView avatar = findViewById(R.id.imgAvatar);

        Intent intent = getIntent();

        String charImg = intent.getStringExtra("char");


        int id = getResources().getIdentifier(charImg, "drawable", getPackageName());
        avatar.setImageResource(id);
    }
    private void messageSERVER() {

        Thread thread = new Thread(() -> {
            try  {
                Log.i("SERVERLOL","THREAD");
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
                Client client = new Client();
                String string2 = client.login(args.get(0),args.get(1));
                //oMS.callService("1","login",args);


                runOnUiThread(() -> {
                    Toast.makeText(MenuPrincipal.this,string2,Toast.LENGTH_SHORT).show();
                    //send.setText(string2);
                    Log.i("SERVERLOL","DEBUG");
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }
    /*
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
}