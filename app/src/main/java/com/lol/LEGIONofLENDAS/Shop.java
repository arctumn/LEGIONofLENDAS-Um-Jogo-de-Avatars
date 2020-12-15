package com.lol.LEGIONofLENDAS;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static com.lol.LEGIONofLENDAS.Ranking.DESCENDING_COMPARATOR;

public class Shop extends AppCompatActivity {

    private RecyclerView rankingRecyclerView;
    private RecyclerView.Adapter rankingAdapter;
    private RecyclerView.LayoutManager rankingLayoutManager;

    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_shop);

        ArrayList<itemsShop> items = new ArrayList<>();
        Intent in = getIntent();
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

        ArrayList<String> lista1 = new ArrayList<String>();
        String query = "SELECT image,price,forca,magia,defesa,defesaMagica,vida,id,itemName FROM loja";
        lista1.add(query);

        util.txtMessageServer("1", "TESTINGADMIN", lista1);

        String recebido = util.output;

        ArrayList<String> lista2 = new ArrayList<>(Arrays.asList(recebido.split("\n")));
        System.out.println(Arrays.toString(lista2.toArray()));
        // lista.forEach();
        lista2.stream().map(string -> string.split(" ")).forEach(pre -> {
            StringBuilder nome = new StringBuilder();
            for (int i = 8; i < pre.length; i++) {
                nome.append(" ").append(pre[i]);
            }
            String id = pre[7];
            System.out.println("Valor de id:" + id);
            items.add(new itemsShop(
                            getResources()
                                    .getIdentifier(pre[0], "drawable", this.getPackageName()), //icone
                            pre[1],
                            pre[2],
                            pre[3],
                            pre[4],
                            pre[5],
                            pre[6],
                            pre[7],
                            nome.toString(),
                            in.getStringExtra("userid")
                    )
            );
        });


        items.sort(itemsShop.DESCENDING);
        rankingRecyclerView = findViewById(R.id.recyclerViewShop);
        rankingRecyclerView.setHasFixedSize(true);
        rankingLayoutManager = new LinearLayoutManager(this);
        rankingAdapter = new itemShopAdapter(items);

        rankingRecyclerView.setLayoutManager(rankingLayoutManager);
        rankingRecyclerView.setAdapter(rankingAdapter);

    }
}