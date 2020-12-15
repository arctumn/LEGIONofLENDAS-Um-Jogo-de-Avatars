package com.lol.LEGIONofLENDAS;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.vision.text.Line;

import java.lang.reflect.Array;
import java.security.cert.PolicyQualifierInfo;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class Ranking extends AppCompatActivity {

    private RecyclerView rankingRecyclerView;
    private RecyclerView.Adapter rankingAdapter;
    private RecyclerView.LayoutManager rankingLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        ArrayList<itemsRanking> items = new ArrayList<>();

        /**
         * numeroDEPessoasAMostrar = 15
         * util.txtMEssageServer("1","rankingxp",""+numeroDEPessoasAMostrar);
         *
         * recebido = util.output
         * "infoU1\ninfoU2\ninfoU3\n....\ninfoU15\n"
         * lista = new ArrayList<String>(Arrays.asList(recebido.split(\n))
         * lista ["VALUEnome VALUElvl VALUExp",...,"infoU15"]
         */

        Utils util = new Utils();
        int num = 15;
        ArrayList<String> lista1 = new ArrayList<String>();

        lista1.add(String.valueOf(num));

        util.txtMessageServer("1", "rankingxp", lista1);

        String recebido = util.output;
        System.out.println("ola    "+recebido);
        ArrayList<String> lista2 = new ArrayList<>(Arrays.asList(recebido.split("\n")));

         System.out.println(Arrays.toString(lista2.toArray()));
       // lista.forEach();
        lista2.forEach( string -> {
            String[] pre = string.split(" ");
            items.add(new itemsRanking(
                    getResources()
                            .getIdentifier(pre[0], "drawable", this.getPackageName()),
                    pre[1],
                    Integer.parseInt(pre[2]))
            );
        });

        items.sort(DESCENDING_COMPARATOR);
        rankingRecyclerView = findViewById(R.id.recyclerViewRanking);
        rankingRecyclerView.setHasFixedSize(true);
        rankingLayoutManager = new LinearLayoutManager(this);
        rankingAdapter = new itemRankingAdapter(items);

        rankingRecyclerView.setLayoutManager(rankingLayoutManager);
        rankingRecyclerView.setAdapter(rankingAdapter);

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
    public static final Comparator<itemsRanking> DESCENDING_COMPARATOR =
            Comparator.comparingInt( itemsRanking::getmText2).reversed();
}