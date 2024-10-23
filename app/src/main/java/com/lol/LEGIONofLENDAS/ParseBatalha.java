package com.lol.LEGIONofLENDAS;

import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
public class ParseBatalha extends AppCompatActivity {

    Thread thread;
    int flag;
    Utils util = new Utils();

    Button btn;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parse_batalha);
        GridLayout grid = findViewById(R.id.gridluta);
        grid.setColumnCount(2);

        Intent intent = getIntent();
        final String LOG = intent.getStringExtra("strluta");

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
        btn.setOnClickListener(this::darSkip);

        flag = 0;
        thread = new Thread() {
            @Override
            public void run() {
                try {
                    jogoFinalizado = parseBatalha(LOG);
                    runOnUiThread( () -> {
                        btn.setText(getString(R.string.return_main_menu));
                    });
                } catch (InterruptedException e) {
                    Log.e("ParseBatalha","Não consegui processar a batalha",e);
                }
            }
        };
        thread.start();
    }

    public void darSkip( View v){
        flag = 1;
        if(jogoFinalizado){
            Sair(v);
        }
    }

    public String getNome(int id){
        ArrayList<String> query = new ArrayList<>();
        String nome;
        query.add("SELECT nome FROM user WHERE ID = "+id+";");
        util.txtMessageServer("4","TESTINGADMIN",query);
        return util.output.split("\n")[0];
    }

    public String getLevel(int id){
        ArrayList<String> query = new ArrayList<>();
        query.add("SELECT level FROM user WHERE ID = "+id+";");
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

        }else {
            ObjectAnimator.ofInt(pb_right,"progress",previous_state,current_life).setDuration(300).start();

        }
    }
    public int getAvatar(int id){
        int ava;
        ArrayList<String> query = new ArrayList<>();
        query.add("SELECT image FROM user WHERE ID = "+id+";");
        util.txtMessageServer("4","TESTINGADMIN",query);
        ava = getResources().getIdentifier(util.output.split(" ")[0],"drawable",getPackageName());

        return ava;
    }

    public boolean parseBatalha(String Mensagem) throws InterruptedException {

        Log.d("ParseBatalha",Mensagem);
        btn.setText(R.string.Skip_Fight);
        int id1 = 0;
        int id2 = 0;
        int progresso1 = 0;
        int progresso2 = 0;

        String[] Relatorio = Mensagem.split("\n");
        for(int i = 0; i < Relatorio.length-1; i++) {
            String[] Ronda = Relatorio[i].split(" ");

            if (Relatorio[i].compareTo("\n") == 0) {
                return true;
            }
            if ( Ronda.length == 0){
                return true;
            }
            if (Ronda[0].compareTo("") == 0){
                return true;
            }

            if (Integer.parseInt(Ronda[3]) == 0) { // Primeira entrada
                id1 = Integer.parseInt(Ronda[0]);
                id2 = Integer.parseInt(Ronda[1]);
                hpinitial1 = (int) Float.parseFloat(Ronda[2]);
                hpinitial2 = (int) Float.parseFloat(Ronda[4]);


                pb_left.setMax((int) Float.parseFloat(Ronda[2]));
                pb_right.setMax((int) Float.parseFloat(Ronda[4]));

                int finalId1 = id1;
                int finalId2 = id2;
                runOnUiThread(() -> {
                    ava1.setImageResource( getAvatar(finalId1));
                    ava2.setImageResource( getAvatar(finalId2));
                    name1.setText( getNome(finalId1));
                    name2.setText( getNome(finalId2));
                    level1.setText(getLevel(finalId1));
                    level2.setText(getLevel(finalId2));
                });

            } else { // Nas outras rondas
                runOnUiThread(() -> {
                    ronda.setText(Ronda[3]);
                });

                if (Integer.parseInt(Ronda[0]) == id1) {
                    progresso1 = (int) Float.parseFloat(Ronda[1]);
                    if (progresso1 <= 0){
                        progresso1 = 0;
                        runOnUiThread(() -> {
                            ava1.setImageAlpha(125);
                        });
                    }

                    int finalProgresso2 = progresso1;
                    runOnUiThread(() -> {
                        pb_left.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#D14519")));

                        pb_left.setProgress(finalProgresso2,true);
                    });
                    // hp1antigo = progresso1;
                    if( flag != 1 )
                        Thread.sleep(2000);
                    runOnUiThread(() -> {
                        pb_left.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#2AFF05")));
                    });

                } else {

                    progresso2 = (int) Float.parseFloat(Ronda[1]);
                    if (progresso2 <= 0){
                        progresso2 = 0;
                        runOnUiThread(() -> {
                            ava2.setImageAlpha(125);
                        });
                    }
                    int finalProgresso = progresso2;
                    runOnUiThread(() -> {
                        pb_right.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#D14519")));

                        pb_right.setProgress(finalProgresso,true);
                    });

                    if( flag != 1 )
                        Thread.sleep(2000);
                    runOnUiThread(() -> {
                        pb_right.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#2AFF05")));
                    });
                }
            }
        }
        return true;
    }

    public void Sair(View v) {
        Intent intent = new Intent(ParseBatalha.this, MenuPrincipal.class);
        Intent out = getIntent();
        intent.putExtra("userid",out.getStringExtra("userid"));
        startActivity(intent);
        finish();
    }

    private int getLifePercentage(int initialLife, int currentLife){
        if(currentLife <= 0) return 0;
        return (int)((currentLife/initialLife)*100);
    }
}