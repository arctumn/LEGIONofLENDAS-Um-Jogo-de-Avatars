package com.lol.LEGIONofLENDAS;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicInteger;

public class LevelUp extends AppCompatActivity {

    Button btnlvlup,btn_upgrade_str,btn_upgrade_hp,btn_upgrade_m;
    TextView nivel_atual,pontos_xp,str,hp,magic,xpval;
    ProgressBar xp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_up);
        Utils utils = new Utils();
        Intent in = getIntent();
        utils.txtMessageServer("-1",
                "TESTINGADMIN",
                new ArrayList<>(Collections.singleton("SELECT level from user where id = "+in.getStringExtra("userid")))
        );
        int val = Integer.parseInt(utils.output.trim())+1;
        utils.txtMessageServer("-1",
                "TESTINGADMIN",
                new ArrayList<>(Collections.singleton("SELECT xp from user where id = "+in.getStringExtra("userid")))
        );
        int xp_Start = Integer.parseInt(utils.output.trim());
        Log.i("XP","["+val+"]");

        int next = nextPrime(val);
        Log.i("XP","\n\n\n\nValor de NEXT:"+next+"\n\n\n");

        incializer();

        runOnUiThread(() ->{
                    int lvl_val = val -1;
                nivel_atual.setText(nivel_atual.getText().toString()+lvl_val);

                xpval.setText(xp_Start+" / "+next);
                double percent = Math.floor(((double) xp_Start / next) * 100);
                Log.i("XP","Valor de xp em percentagem: "+percent+" valor de val = "+val +" valor de next = "+next);
                xp.setProgress((int) percent,true);

                utils.txtMessageServer("-1",
                       "TESTINGADMIN",
                        new ArrayList<>(Collections.singleton("SELECT forca from status where id = "+in.getStringExtra("userid")))
                );
                str.setText(utils.output.trim());

                utils.txtMessageServer("-1",
                    "TESTINGADMIN",
                    new ArrayList<>(Collections.singleton("SELECT magia from status where id = "+in.getStringExtra("userid")))
                );
                magic.setText(utils.output.trim());

                utils.txtMessageServer("-1",
                    "TESTINGADMIN",
                    new ArrayList<>(Collections.singleton("SELECT vida from status where id = "+in.getStringExtra("userid")))
                );
                hp.setText(utils.output.trim());

        });

        if(next > Integer.parseInt(in.getStringExtra("exp").trim())){//)){
            hide();
        }else{
            btnlvlup.setVisibility(View.VISIBLE);
            btnlvlup.setOnClickListener(v -> {



                btnlvlup.setVisibility(View.INVISIBLE);
                utils.txtMessageServer("-1",
                        "TESTINGADMIN",
                        new ArrayList<>(Collections.singleton("SELECT level from user where id = "+in.getStringExtra("userid")))
                );
                int nivel = Integer.parseInt(utils.output.trim())+1;
                utils.txtMessageServer("-1",
                        "TESTINGADMIN",
                        new ArrayList<>(Collections.singleton("update user set xp = 0, level = "+nivel+" where id = "+in.getStringExtra("userid")))
                );
                pontos_xp.setText(pontos_xp.getText().toString()+" 5");
                pontos_xp.setVisibility(View.VISIBLE);



                AtomicInteger pontos = new AtomicInteger(Integer.parseInt(pontos_xp.getText().toString().split(" ")[3]));

                    runOnUiThread(() -> {
                        if(pontos.get() < 1)  hide();
                        btn_upgrade_str.setVisibility(View.VISIBLE);
                        btn_upgrade_m.setVisibility(View.VISIBLE);
                        btn_upgrade_hp.setVisibility(View.VISIBLE);
                        enable();
                        int next_new = nextPrime(nivel);

                        xpval.setText("0"+" / "+next_new);
                        //double percent = Math.floor((Double.parseDouble(in.getStringExtra("exp").trim()) / next_new) * 100);
                        //Log.i("XP","Valor de xp em percentagem: "+percent+" valor de val = "+val +" valor de next = "+next);
                        xp.setProgress(0,true);

                        nivel_atual.setText(""+nivel);
                        if(pontos.get() < 1)  hide();
                        btn_upgrade_str.setOnClickListener(vstr -> {
                            if(pontos.get() < 1)  hide();
                            else {

                            int str_int;

                            str_int = Integer.parseInt(str.getText().toString()) + 1;

                            utils.txtMessageServer("-1",
                                    "TESTINGADMIN",
                                    new ArrayList<>(Collections.singleton("UPDATE status set forca = " + str_int + " where id = " + in.getStringExtra("userid")))
                            );
                            utils.txtMessageServer("-1",
                                    "TESTINGADMIN",
                                    new ArrayList<>(Collections.singleton("SELECT forca from status where id = " + in.getStringExtra("userid")))
                            );
                            str.setText(utils.output.trim());
                            pontos.getAndDecrement();
                            pontos_xp.setText("" + pontos.get());
                        }
                        });
                        if(pontos.get() < 1)  disable();
                        btn_upgrade_m.setOnClickListener(vstr -> {
                            if(pontos.get() < 1)  hide();
                            else {
                                int m_int;

                                m_int = Integer.parseInt(magic.getText().toString()) + 1;

                                utils.txtMessageServer("-1",
                                        "TESTINGADMIN",
                                        new ArrayList<>(Collections.singleton("UPDATE status set magia = " + m_int + " where id = " + in.getStringExtra("userid")))
                                );
                                utils.txtMessageServer("-1",
                                        "TESTINGADMIN",
                                        new ArrayList<>(Collections.singleton("SELECT magia from status where id = " + in.getStringExtra("userid")))
                                );
                                magic.setText(utils.output.trim());
                                pontos.getAndDecrement();
                                pontos_xp.setText("" + pontos.get());
                            }
                        });
                        btn_upgrade_hp.setOnClickListener(vstr -> {
                            if(pontos.get() < 1)  hide();
                            else {
                                int hp_int;

                                hp_int = Integer.parseInt(hp.getText().toString()) + 1;


                                utils.txtMessageServer("-1",
                                        "TESTINGADMIN",
                                        new ArrayList<>(Collections.singleton("UPDATE status set vida = " + hp_int + " where id = " + in.getStringExtra("userid")))
                                );
                                utils.txtMessageServer("-1",
                                        "TESTINGADMIN",
                                        new ArrayList<>(Collections.singleton("SELECT vida from status where id = " + in.getStringExtra("userid")))
                                );
                                hp.setText(utils.output.trim());
                                pontos.getAndDecrement();
                                pontos_xp.setText("" + pontos.get());
                            }
                        });
                    });
            });
        }
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        Intent out = getIntent();
        Intent intent = new Intent(this,MenuPrincipal.class);
        intent.putExtra("id",out.getStringExtra("userid"));
        startActivity(intent);
        finish();
    }

    private void enable(){
        btnlvlup.setEnabled(true);
        btn_upgrade_str.setEnabled(true);
        btn_upgrade_m.setEnabled(true);
        btn_upgrade_hp.setEnabled(true);
        pontos_xp.setEnabled(true);
    }
    private void disable() {
        btnlvlup.setEnabled(false);
        btn_upgrade_str.setEnabled(false);
        btn_upgrade_m.setEnabled(false);
        btn_upgrade_hp.setEnabled(false);
        pontos_xp.setEnabled(false);
    }

    private void incializer() {
        btnlvlup        = findViewById(R.id.btnLvlUp);
        nivel_atual     = findViewById(R.id.txt_level);
        pontos_xp       = findViewById(R.id.textView5);
        xp              = findViewById(R.id.progressBar);
        xpval           = findViewById(R.id.txt_level_upgrade_per);
        btn_upgrade_hp  = findViewById(R.id.btn_upgrade_life);
        btn_upgrade_m   = findViewById(R.id.btn_upgrade_magic);
        btn_upgrade_str = findViewById(R.id.btn_upgrade_str);
        str             = findViewById(R.id.txt_str);
        hp              = findViewById(R.id.txt_hp);
        magic           = findViewById(R.id.txt_m);
    }

    private void hide() {
        btnlvlup.setVisibility(View.INVISIBLE);
        btn_upgrade_str.setVisibility(View.INVISIBLE);
        btn_upgrade_m.setVisibility(View.INVISIBLE);
        btn_upgrade_hp.setVisibility(View.INVISIBLE);
        pontos_xp.setVisibility(View.INVISIBLE);
    }

    private int nextPrime(int n){
        BigInteger index = new BigInteger(String.valueOf(n));
        return Integer.parseInt(index.nextProbablePrime().toString());
    }
}