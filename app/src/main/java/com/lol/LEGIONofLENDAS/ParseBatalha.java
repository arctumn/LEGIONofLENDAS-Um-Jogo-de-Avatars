package com.lol.LEGIONofLENDAS;

import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Objects;

public class ParseBatalha extends AppCompatActivity {

    Thread thread;
    int flag;
    Utils util = new Utils();

    ProgressBar pb_left;
    ProgressBar pb_right;

    ImageView ava1;
    ImageView ava2;

    int hpinitial1;
    int hpinitial2;
    boolean jogoFinalizado;

    TextView name1;
    TextView name2;
    TextView ronda;
    TextView level1;
    TextView level2;
    TextView btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parse_batalha);
        GridLayout grid = findViewById(R.id.gridluta);
        grid.setColumnCount(2);

        Intent intent = getIntent();
        String LOG = "";
        LOG = intent.getStringExtra("strLuta");

        pb_left = findViewById(R.id.pb_left);
        pb_right = findViewById(R.id.pb_right);
        runOnUiThread(() ->{
                pb_left.setMax(25);
                pb_right.setMax(25);
        });

        jogoFinalizado = false;
        ava1 = findViewById(R.id.imgAvatar1);
        ava2 = findViewById(R.id.imgAvatar2);

        name1 = findViewById(R.id.username1);
        name2 = findViewById(R.id.username2);
        ronda = findViewById(R.id.textRonda);
        level1 = findViewById(R.id.usernivel1);
        level2 = findViewById(R.id.usernivel2);
        btn = findViewById(R.id.btn_menu);

        flag = 0;
        String finalLOG = LOG;
        thread = new Thread() {

            @Override
            public void run() {
                runOnUiThread(() -> {
                    try {
                        jogoFinalizado = parseBatalha(finalLOG);
                        btn.setText("Voltar ao menu principal");
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
        if(jogoFinalizado)
            Sair(v);
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

    public boolean parseBatalha(String Mensagem) throws InterruptedException {

        btn.setText("Passar batalha à frente");
        int id1 = 0;
        int id2 = 0;
        int hp1antigo = 0;  // Vida user 1
        int hp2antigo = 0;  // Vida user 2
        int progresso1 = 0;
        int progresso2 = 0;

        String[] Relatorio = Mensagem.split("\n");
        for(int i = 0; i < Relatorio.length; i++) {
            String[] Ronda = Relatorio[i].split(" ");

            if (Ronda[0].compareTo("") == 0) {
                return true;
            }

            if (flag == 1) { // Dar Skip
                String[] u1 = Relatorio[Relatorio.length - 1].split("");
                String[] u2 = Relatorio[Relatorio.length - 2].split("");
                ronda.setText(Ronda[3]);

                if (Integer.parseInt(u1[0]) == id1) {

                    setVida(hpinitial1,0,true);
                    setVida(hpinitial2,getLifePercentage(hpinitial2,Integer.parseInt(u2[1])),false);

                } else {

                    setVida(hpinitial2,0,false);
                    setVida(hpinitial1,getLifePercentage(hpinitial2,Integer.parseInt(u1[1])),true);
                }
                return true;
            } else {
                if (Integer.parseInt(Ronda[3]) == 0) { // Primeira entrada
                    id1 = Integer.parseInt(Ronda[0]);
                    id2 = Integer.parseInt(Ronda[1]);
                    hpinitial1 = Integer.parseInt(Ronda[2]);
                    hpinitial2 = Integer.parseInt(Ronda[4]);
                    hp1antigo = 100;
                    hp2antigo = 100;

                    pb_left.setMax(Integer.parseInt(Ronda[2]));
                    pb_right.setMax(Integer.parseInt(Ronda[4]));

                    ava1.setImageResource( getAvatar(id1));
                    ava2.setImageResource( getAvatar(id2));
                    name1.setText( getNome(id1));
                    name2.setText( getNome(id2));
                    level1.setText(getLevel(id1));
                    level2.setText(getLevel(id1));

                } else { // Nas outras rondas
                    ronda.setText(Ronda[3]);

                    if (Integer.parseInt(Ronda[0]) == id1) {
                        pb_left.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#D14519")));

                        progresso1 = getLifePercentage(hpinitial1,Integer.parseInt(Ronda[1]));
                        setVida(hp1antigo, progresso1,true);
                        hp1antigo = progresso1;

                        Thread.sleep(2000);
                        pb_left.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#2AFF05")));
                    } else {
                        pb_right.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#D14519")));

                        progresso2 = getLifePercentage(hpinitial1,Integer.parseInt(Ronda[1]));
                        setVida(hp2antigo, progresso2,false);
                        hp2antigo = progresso2;

                        Thread.sleep(2000);
                        pb_right.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#2AFF05")));
                    }
                }
            }
        }
        return true;
    }

    public void Sair( View v) {
        finish();
    }

    private int getLifePercentage(int initialLife, int currentLife){
        if(currentLife <= 0) return 0;
            return (int)((currentLife/initialLife)*100);
    }
}