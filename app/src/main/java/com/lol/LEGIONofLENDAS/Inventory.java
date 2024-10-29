package com.lol.LEGIONofLENDAS;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.Arrays;

import static com.lol.LEGIONofLENDAS.UserItem.USER_ITEM_DESCENDING_COMPARATOR;

import com.lol.LEGIONofLENDAS.Client.User;

public class Inventory extends AppCompatActivity {

    private User userData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventario);

        ArrayList<UserItem> items = new ArrayList<>();
        Intent in = getIntent();
        userData = User.ExtractUser(in);

        Utils util = new Utils();

        var sqlRequest = new ArrayList<String>();
        var query = "SELECT image,itemName,forca FROM inventario WHERE iduser = "+ userData.userId;
        sqlRequest.add(query);

        util.txtMessageServer("1", "TESTINGADMIN", sqlRequest);

        String recebido = util.output;

        var output = new ArrayList<>(Arrays.asList(recebido.split("\n")));

        output.forEach(item -> {
            String[] itemData = item.split(" ");
            StringBuilder ab = new StringBuilder(itemData[1]);
            for(int i = 2; i <itemData.length-1;i++){
                ab.append(" ").append(itemData[i]);

            }
            var itemImage = getResources()
                            .getIdentifier(itemData[0], "drawable", this.getPackageName());
            var itemName = ab.toString();
            var itemForce = 0;
            var newItem = new UserItem(itemImage,itemName,itemForce);
            items.add(newItem);
        });

        items.sort(USER_ITEM_DESCENDING_COMPARATOR);
        RecyclerView rankingRecyclerView = findViewById(R.id.recyclerViewInventario);
        rankingRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager rankingLayoutManager = new LinearLayoutManager(this);
        var rankingAdapter = new itemInventoryAdapter(items);

        rankingRecyclerView.setLayoutManager(rankingLayoutManager);
        rankingRecyclerView.setAdapter(rankingAdapter);

    }
    @Override
    public void onBackPressed(){
        super.onBackPressed();

        Intent intent = new Intent(this,MenuPrincipal.class);
        intent = userData.SetUserNavigationData(intent);
        startActivity(intent);
        finish();
    }
}