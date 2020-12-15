package com.lol.LEGIONofLENDAS;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.Arrays;

import static com.lol.LEGIONofLENDAS.Ranking.DESCENDING_COMPARATOR;

public class Inventario extends AppCompatActivity {

    private RecyclerView rankingRecyclerView;
    private RecyclerView.Adapter rankingAdapter;
    private RecyclerView.LayoutManager rankingLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventario);

        ArrayList<itemsRanking> items = new ArrayList<>();
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
        String query = "SELECT image,itemName,forca FROM inventario WHERE iduser = "+in.getStringExtra("userid");
        lista1.add(query);

        util.txtMessageServer("1", "TESTINGADMIN", lista1);

        String recebido = util.output;

        ArrayList<String> lista2 = new ArrayList<>(Arrays.asList(recebido.split("\n")));

        System.out.println(Arrays.toString(lista2.toArray()));
        // lista.forEach();
        lista2.forEach( string -> {
            System.out.println("Valor da string"+ string);
            String[] pre = string.split(" ");
            StringBuilder ab = new StringBuilder(pre[1]);
            for(int i = 2; i <pre.length-1;i++){ ab.append(" ").append(pre[i]);
                System.out.println("Valor de AB: "+ab);
            }
            System.out.println("nome: "+ab);
            System.out.println("Valor do pre[0] = ["+pre[0]+"]");

            items.add(new itemsRanking(
                    getResources()
                            .getIdentifier(pre[0], "drawable", this.getPackageName()),
                    ab.toString(),
                    0)
            );
        });

        items.sort(DESCENDING_COMPARATOR);
        rankingRecyclerView = findViewById(R.id.recyclerViewInventario);
        rankingRecyclerView.setHasFixedSize(true);
        rankingLayoutManager = new LinearLayoutManager(this);
        rankingAdapter = new itemInventoryAdapter(items);

        rankingRecyclerView.setLayoutManager(rankingLayoutManager);
        rankingRecyclerView.setAdapter(rankingAdapter);

    }
}