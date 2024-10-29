package com.lol.LEGIONofLENDAS.shop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.Arrays;


import com.lol.LEGIONofLENDAS.MainMenu;
import com.lol.LEGIONofLENDAS.R;
import com.lol.LEGIONofLENDAS.client.User;
import com.lol.LEGIONofLENDAS.utils.Utils;

public class Shop extends AppCompatActivity {

    private RecyclerView rankingRecyclerView;
    private RecyclerView.Adapter rankingAdapter;
    private RecyclerView.LayoutManager rankingLayoutManager;
    protected User userData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_shop);

        ArrayList<itemsShop> items = new ArrayList<>();
        Intent in = getIntent();
        userData = User.ExtractUser(in);
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
                            userData.userId
                    )
            );
        });


        items.sort(itemsShop.DESCENDING);
        rankingRecyclerView = findViewById(R.id.recyclerViewShop);
        rankingRecyclerView.setHasFixedSize(true);
        rankingLayoutManager = new LinearLayoutManager(this);
        var context = Shop.this;
        rankingAdapter = new itemShopAdapter(items,context);
        rankingRecyclerView.setLayoutManager(rankingLayoutManager);
        rankingRecyclerView.setAdapter(rankingAdapter);

    }
    @Override
    public void onBackPressed(){
        super.onBackPressed();
        Intent intent = new Intent(this, MainMenu.class);
        intent = userData.SetUserNavigationData(intent);
        startActivity(intent);
        finish();
    }
}