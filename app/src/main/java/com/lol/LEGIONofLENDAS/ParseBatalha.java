package com.lol.LEGIONofLENDAS;

import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Objects;

public class ParseBatalha extends AppCompatActivity {

    Thread thread;
    int flag;
    Utils util = new Utils();
    ProgressBar pb_left;
    ProgressBar pb_right;
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
    int i = 25;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parse_batalha);
        GridLayout grid = findViewById(R.id.gridluta);
        grid.setColumnCount(2);

        pb_left = findViewById(R.id.pb_left);
        pb_right = findViewById(R.id.pb_right);
        runOnUiThread(() ->{
                pb_left.setMax(25);
                pb_right.setMax(25);
        });


        Button btn_test = findViewById(R.id.btn_menu);
            btn_test.setOnClickListener(v -> {
                runOnUiThread(() ->{
                    setVida(i+1, i, true);
                    setVida(i+1, i,false);
                    //Toast.makeText(ParseBatalha.this,""+i,Toast.LENGTH_SHORT).show();
                    i--;
                });
            });


        flag = 0;
        thread = new Thread() {

            @Override
            public void run() {
                runOnUiThread(() -> {
                    try {
                        parseBatalha("");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
            }
        };
        thread.start();
    }

    public void darSkip( View v){
        flag = 1;
    }

    public String getNome(int id){
        ArrayList<String> query = new ArrayList<>();
        String nome;
        query.set(0, "SELECT nome FROM user WHERE ID = "+id+";");
        util.txtMessageServer("4","TESTINGADMIN",query);
        return util.output.split("\n")[0];
    }

    public String getLevel(int id){
        ArrayList<String> query = new ArrayList<>();
        query.set(0,"SELECT level FROM user WHERE ID = "+id+";");
        util.txtMessageServer("4","TESTINGADMIN",query);
        return "Lvl " + util.output.split("\n")[0];
    }

    /**
     * Atualiza a vida conforme o estado atual
     * @param previous_state estado inicial da vida do player
     * @param current_life estado atual da vida do player
     * @param elemento_esquerda true se for o jogador da esquerda, false se for o da direita
     */
    public void setVida(int previous_state,int current_life,Boolean elemento_esquerda){
        if(elemento_esquerda) {
            ObjectAnimator.ofInt(pb_left,"progress",previous_state,current_life).setDuration(300).start();
           // pb_left.setProgress(getLifePercentage(start_life,current_life),true);
        }else {
            ObjectAnimator.ofInt(pb_right,"progress",previous_state,current_life).setDuration(300).start();
            //pb_right.setProgress(getLifePercentage(start_life,current_life),true);
        }
    }
    public int getAvatar(int id){
        int ava;
        ArrayList<String> query = new ArrayList<>();
        query.set(0,"SELECT image FROM user WHERE ID = "+id+";");
        util.txtMessageServer("4","TESTINGADMIN",query);
        ava = getResources().getIdentifier(util.output.split("\n")[0],"drawable",getPackageName());

        return ava;
    }

    public void parseBatalha(String Mensagem) throws InterruptedException {

        int id1 = 0;
        int id2 = 0;
        int hp1 = 0;  // Vida user 1
        int hp2 = 0;  // Vida user 2
        int dano = 0;
        int ronda = 0;

        String[] Relatorio = Mensagem.split("\n");
        for(int i = 0; i < Relatorio.length; i++){
            String[] Ronda = Relatorio[i].split(" ");

            if (Ronda[0].compareTo("") == 0){
                return;
            }

            if (flag == 1){ // Dar Skip
                String[] u1 = Relatorio[Relatorio.length-1].split("");
                String[] u2 = Relatorio[Relatorio.length-2].split("");
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
                    Thread.sleep(2000);
                    // HP1.setTextColor( Color.parseColor("#FFFFFF")); // Muda o HP para branco
                }
                else{
                    // HP2.setText(Ronda[1]) // Actualiza o HP do jogador 2
                    // HP2.setTextColor( Color.parseColor("#D14519")); // Muda o HP para vermelho
                    Thread.sleep(2000);
                    // HP2.setTextColor( Color.parseColor("#FFFFFF")); // Muda o HP para branco
                }
            }
        }

        // RONDA ACABOU JESUS

    }
    private int getLifePercentage(int inicalLife, int currentLife){
        if(currentLife <= 0) return 0;
            return (int)((currentLife/inicalLife)*100);
    }
}