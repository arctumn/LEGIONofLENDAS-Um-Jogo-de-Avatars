package com.lol.LEGIONofLENDAS;

import static com.lol.LEGIONofLENDAS.UserRanking.USER_RANKING_COMPARATOR_DESCENDING_COMPARATOR;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.lol.LEGIONofLENDAS.Client.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class Ranking extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        ArrayList<UserRanking> items = new ArrayList<>();



        Utils util = new Utils();
        int num = 15;
        ArrayList<String> lista1 = new ArrayList<String>();

        lista1.add(String.valueOf(num));

        util.txtMessageServer("1", "rankingxp", lista1);

        String recebido = util.output;
        ArrayList<String> lista2 = new ArrayList<>(Arrays.asList(recebido.split("\n")));



        lista2.forEach(string -> {
            var userRankData = string.split(" ");
            var userRanking = new UserRanking(userRankData,getResources(),getPackageName());
            items.add(userRanking);

        });

        items.sort(USER_RANKING_COMPARATOR_DESCENDING_COMPARATOR);
        RecyclerView rankingRecyclerView = findViewById(R.id.recyclerViewRanking);
        rankingRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager rankingLayoutManager = new LinearLayoutManager(this);
        RecyclerView.Adapter<itemRankingAdapter.itemViewHolder> rankingAdapter = new itemRankingAdapter(items);

        rankingRecyclerView.setLayoutManager(rankingLayoutManager);
        rankingRecyclerView.setAdapter(rankingAdapter);

    }
    @Override
    public void onBackPressed(){
        super.onBackPressed();
        Intent in = getIntent();
        var user = User.ExtractUser(in);
        Intent intent = new Intent(this,MenuPrincipal.class);
        assert user != null;
        intent = user.SetUserNavigationData(intent);
        startActivity(intent);
        finish();
    }
    public static final Comparator<UserRanking> USER_DESCENDING_COMPARATOR =
            Comparator.comparingInt(UserRanking::comparator).reversed();
}