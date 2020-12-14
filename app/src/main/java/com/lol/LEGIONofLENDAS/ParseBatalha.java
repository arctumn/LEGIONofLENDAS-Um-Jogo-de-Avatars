package com.lol.LEGIONofLENDAS;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

public class ParseBatalha extends AppCompatActivity {

    Thread thread;
    int flag;
    Utils util = new Utils();

    // Definir avatar ava1
    // Definir avatar ava2
    // Definir TextView HP1
    // Definir TextView HP2
    // Definir TextView Name1
    // Definir TextView Name2
    // Definir TextView Ronda
    // Definir TextView Dano
    // Definir TextView Level1
    // Definir TextView Level2

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parse_batalha);

        flag = 0;
        thread = new Thread() {

            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            parseBatalha("");
                        } catch (InterruptedException e) {

                        }
                    }
                });
            }
        };
        thread.start();
    }

    public void darSkip( View v){
        flag = 1;
    }

    public String[] getNome(int id){
        ArrayList<String> query = new ArrayList<>();
        String nome;
        query.set(0, "SELECT nome FROM user WHERE userid = "+id+";");
        util.txtMessageServer("4","TESTINGADMIN",query);
        nome = util.output;

        return nome.split("\n");
    }

    public String getLevel(int id){
        String level;
        ArrayList<String> query = new ArrayList<>();
        query.set(0,"SELECT level FROM user WHERE userid = "+id+";");
        util.txtMessageServer("4","TESTINGADMIN",query);
        level = "Lvl " + util.output.split("\n")[0];

        return level;
    }

    public int getAvatar(int id){
        int ava;
        ArrayList<String> query = new ArrayList<>();
        query.set(0,"SELECT avatar FROM user WHERE userid = "+id+";");
        util.txtMessageServer("4","TESTINGADMIN",query);
        ava = Integer.parseInt(util.output.split("\n")[0]);

        return ava;
    }

    public void parseBatalha(String Mensagem) throws InterruptedException {

        int id1 = 0;
        int id2 = 0;
        int hp1 = 0;  // Vida user 1
        int hp2 = 0;  // Vida user 2
        int dano = 0;
        int ronda = 0;

        String Relatorio[] = Mensagem.split("\n");
        for(int i = 0; i < Relatorio.length; i++){
            String Ronda[] = Relatorio[i].split(" ");

            if (Ronda[0].compareTo("") == 0){
                return;
            }

            if (flag == 1){ // Dar Skip
                String u1[] = Relatorio[Relatorio.length-1].split("");
                String u2[] = Relatorio[Relatorio.length-2].split("");
                // Ronda.setText(Ronda[3]) Actualizar Ronda
                // Dano.setText(Ronda[2]) Actualizar Dano
                if( Integer.parseInt(u1[0]) == id1){
                    // HP1.setText(u1[1]) // Actualiza o HP do jogador 1
                    // HP1.setTextColor( Color.parseColor("#D14519")); // Muda o HP para vermelho
                    // HP2.setText(u2[1]) // Actualiza o HP do jogador 2
                }else{
                    // HP2.setText(u1[1]) // Actualiza o HP do jogador 2
                    // HP2.setTextColor( Color.parseColor("#D14519")); // Muda o HP para vermelho
                    // HP1.setText(u2[1]) // Actualiza o HP do jogador 1
                }
            }

            if( Integer.parseInt(Ronda[3]) == 0 ){ // Primeira entrada
                id1 = Integer.parseInt(Ronda[0]);
                id2 = Integer.parseInt(Ronda[1]);
                hp1 = Integer.parseInt(Ronda[2]);
                hp2 = Integer.parseInt(Ronda[4]);

                // Inicializar ava1 avatar.setImageResource( getAvatar(id1));
                // Inicializar ava2 avatar.setImageResource( getAvatar(id2));
                // Inicializar HP1 textView.setText(getLevel("" + hp1));
                // Inicializar HP2 textView.setText(getLevel("" + hp2));
                // Inicializar Name1 textView.setText(getNome(id1)[0]);
                // Inicializar Name2 textView.setText(getNome(id2)[0]);
                // Inicializar Level1 textView.setText(getLevel(id1));
                // Inicializar Level2 textView.setText(getLevel(id2));

            }
            else{ // Nas outras rondas
                // Ronda.setText(Ronda[3]) Actualizar Ronda
                // Dano.setText(Ronda[2]) Actualizar Dano
                if( Integer.parseInt(Ronda[0]) == id1){
                    // HP1.setText(Ronda[1]) // Actualiza o HP do jogador 1
                    // HP1.setTextColor( Color.parseColor("#D14519")); // Muda o HP para vermelho
                    thread.sleep(2000);
                    // HP1.setTextColor( Color.parseColor("#FFFFFF")); // Muda o HP para branco
                }
                else{
                    // HP2.setText(Ronda[1]) // Actualiza o HP do jogador 2
                    // HP2.setTextColor( Color.parseColor("#D14519")); // Muda o HP para vermelho
                    thread.sleep(2000);
                    // HP2.setTextColor( Color.parseColor("#FFFFFF")); // Muda o HP para branco
                }
            }
        }

        // RONDA ACABOU JESUS

        return;
    }
}