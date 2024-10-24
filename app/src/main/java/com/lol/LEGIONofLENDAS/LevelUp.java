package com.lol.LEGIONofLENDAS;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lol.LEGIONofLENDAS.Client.User;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicInteger;

public class LevelUp extends AppCompatActivity {

    Button btnlvlup,btn_upgrade_str,btn_upgrade_hp,btn_upgrade_m;
    TextView nivel_atual,pontos_xp,str,hp,magic,xpval;
    ProgressBar xp;
    User userData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_up);
        Utils utils = new Utils();
        var in = getIntent();
        userData = User.ExtractUser(in);
        assert userData != null;
        utils.txtMessageServer("-1",
                "TESTINGADMIN",
                new ArrayList<>(Collections.singleton("SELECT level, xp from user where id = "+ userData.userId))
        );
        var output = utils.output.split(" ");
        userData.level = Integer.parseInt(output[0]);
        userData.experience = Integer.parseInt(output[1]);
        utils.txtMessageServer("-1",
                "TESTINGADMIN",
                new ArrayList<>(Collections.singleton("SELECT xp from user where id = "+userData.userId))
        );

        int next = nextPrime(userData.level +1);

        inicializer();

        runOnUiThread(() -> {

            nivel_atual.setText(nivel_atual.getText().toString() + userData.level);

            xpval.setText(userData.experience + " / " + next);
            double percent = Math.floor(((double) userData.experience / next) * 100);
            xp.setProgress((int) percent, true);

            var query = "SELECT forca,magia,vida from status where id = " + userData.userId;
            utils.txtMessageServer("-1",
                    "TESTINGADMIN",
                    new ArrayList<>(Collections.singleton(query))
            );
            var outputStatus = Arrays
                    .stream(utils.output.split(" "))
                    .map(String::trim)
                    .toArray();
            var strPoints = getString(R.string.STR) + outputStatus[0];
            str.setText(strPoints);

            var magicPoints = getString(R.string.Magic) + outputStatus[1];
            magic.setText(magicPoints);

            var hpPoints = getString(R.string.HP) + outputStatus[2];
            hp.setText(hpPoints);

        });

        if(next > userData.experience){
            hide();
        }else{
            btnlvlup.setVisibility(View.VISIBLE);
            btnlvlup.setOnClickListener(v -> {
                btnlvlup.setVisibility(View.INVISIBLE);
                userData.level++;

                utils.txtMessageServer("-1",
                        "TESTINGADMIN",
                        new ArrayList<>(Collections.singleton("update user set xp = 0, level = "+userData.level+" where id = "+ userData.userId))
                );
                userData.experience = 0;
                pontos_xp.setText(pontos_xp.getText().toString()+" 5");
                pontos_xp.setVisibility(View.VISIBLE);


                var _pontos = pontos_xp.getText().toString().split(" ");
                AtomicInteger pontos = new AtomicInteger(Integer.parseInt(_pontos[_pontos.length - 1]));

                    runOnUiThread(() -> {
                        if(pontos.get() < 1)  hide();
                        btn_upgrade_str.setVisibility(View.VISIBLE);
                        btn_upgrade_m.setVisibility(View.VISIBLE);
                        btn_upgrade_hp.setVisibility(View.VISIBLE);
                        enable();
                        int next_new = nextPrime(userData.level+1);

                        xpval.setText("0 / "+ next_new);

                        xp.setProgress(0,true);
                        var nivelTxt = getString(R.string.main_menu_level) + userData.level;
                        nivel_atual.setText(nivelTxt);
                        if(pontos.get() < 1)  hide();
                        btn_upgrade_str.setOnClickListener(vstr -> {
                            if(pontos.get() < 1)  hide();
                            else {
                            int str_int;

                            str_int = Integer.parseInt(str.getText().toString().split(":")[1]) + 1;

                            utils.txtMessageServer("-1",
                                    "TESTINGADMIN",
                                    new ArrayList<>(Collections.singleton("UPDATE status set forca = " + str_int + " where id = " + userData.userId))
                            );
                            utils.txtMessageServer("-1",
                                    "TESTINGADMIN",
                                    new ArrayList<>(Collections.singleton("SELECT forca from status where id = " + userData.userId))
                            );
                            var strPoints = getString(R.string.STR) +  utils.output.trim();
                            str.setText(strPoints);
                            pontos.getAndDecrement();
                            var availablePoints = getString(R.string.Avaliable_Points) +  pontos.get();
                            pontos_xp.setText(availablePoints);
                        }
                        });
                        if(pontos.get() < 1)  disable();
                        btn_upgrade_m.setOnClickListener(vstr -> {
                            if(pontos.get() < 1)  hide();
                            else {
                                int m_int;

                                m_int = Integer.parseInt(magic.getText().toString().split(":")[1]) + 1;

                                utils.txtMessageServer("-1",
                                        "TESTINGADMIN",
                                        new ArrayList<>(Collections.singleton("UPDATE status set magia = " + m_int + " where id = " + userData.userId))
                                );
                                utils.txtMessageServer("-1",
                                        "TESTINGADMIN",
                                        new ArrayList<>(Collections.singleton("SELECT magia from status where id = " + userData.userId))
                                );

                                var magicPoints = getString(R.string.Magic) +  utils.output.trim();
                                magic.setText(magicPoints);

                                pontos.getAndDecrement();
                                var availablePoints = getString(R.string.Avaliable_Points) +  pontos.get();
                                pontos_xp.setText(availablePoints);
                            }
                        });
                        btn_upgrade_hp.setOnClickListener(vstr -> {
                            if(pontos.get() < 1)  hide();
                            else {
                                int hp_int;

                                hp_int = Integer.parseInt(hp.getText().toString().split(":")[1]) + 1;


                                utils.txtMessageServer("-1",
                                        "TESTINGADMIN",
                                        new ArrayList<>(Collections.singleton("UPDATE status set vida = " + hp_int + " where id = " + userData.userId))
                                );
                                utils.txtMessageServer("-1",
                                        "TESTINGADMIN",
                                        new ArrayList<>(Collections.singleton("SELECT vida from status where id = " + userData.userId))
                                );
                                hp.setText(utils.output.trim());
                                var hpPoints = getString(R.string.HP) +  utils.output.trim();
                                hp.setText(hpPoints);
                                pontos.getAndDecrement();
                                var availablePoints = getString(R.string.Avaliable_Points) +  pontos.get();
                                pontos_xp.setText(availablePoints);
                            }
                        });
                    });
            });
        }
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        Intent intent = new Intent(this,MenuPrincipal.class);
        intent = userData.SetUserNavigationData(intent);
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

    private void inicializer() {
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