package com.lol.LEGIONofLENDAS;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class itemRankingAdapter  extends RecyclerView.Adapter<itemRankingAdapter.itemViewHolder> {
    public ArrayList<UserRanking> rankList;
    public static class itemViewHolder extends RecyclerView.ViewHolder{

        public ImageView userAvatar;
        public TextView rankText,nameText,levelText, userWon,userLoss,userStr,userMagic,userXp,userDefence,userMagicDefence, userLife;

        public itemViewHolder(View itemView){
            super(itemView);
            rankText         = itemView.findViewById(R.id.rank);
            userAvatar       = itemView.findViewById(R.id.rnkAvatar);
            nameText         = itemView.findViewById(R.id.rnkNome);
            levelText        = itemView.findViewById(R.id.rnkTxtNivel);
            userWon          = itemView.findViewById(R.id.user_win);
            userLoss         = itemView.findViewById(R.id.user_loss);
            userXp           = itemView.findViewById(R.id.user_xp);
            userLife         = itemView.findViewById(R.id.user_life);
            userStr          = itemView.findViewById(R.id.user_str);
            userDefence      = itemView.findViewById(R.id.user_def);
            userMagic        = itemView.findViewById(R.id.user_magic);
            userMagicDefence = itemView.findViewById(R.id.user_magic_def);

        }
    }


    public itemRankingAdapter(ArrayList<UserRanking> listaItems){
        rankList = listaItems;
    }

    @NonNull
    @Override
    public itemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ranking, parent, false);
        return new itemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull itemViewHolder holder, int position) {

        UserRanking currentRank = rankList.get(position);
        var image = currentRank.avatarImage;
        var name = currentRank.name;
        var level = "LVL: "+ currentRank.level;
        var rankPosition = "#"+ (position+1);
        var won = getString(R.string.won,currentRank.victories,holder.itemView);
        var loss = getString(R.string.lost,currentRank.losses,holder.itemView);
        var xp = getString(R.string.experience,currentRank.experience,holder.itemView);
        var str = getString(R.string.STR,currentRank.str,holder.itemView);
        var def = getString(R.string.Defence,currentRank.defence,holder.itemView);
        var magic = getString(R.string.Magic,currentRank.magic,holder.itemView);
        var magicDef = getString(R.string.Magic_Defence,currentRank.magicDefence,holder.itemView);
        var life = getString(R.string.HP,currentRank.life,holder.itemView);

        holder.userAvatar.setImageResource(image);
        holder.nameText.setText(name);
        holder.levelText.setText(level);
        holder.rankText.setText(rankPosition);
        holder.userWon.setText(won);
        holder.userLoss.setText(loss);
        holder.userXp.setText(xp);
        holder.userLife.setText(life);
        holder.userStr.setText(str);
        holder.userDefence.setText(def);
        holder.userMagic.setText(magic);
        holder.userMagicDefence.setText(magicDef);
    }
    private String getString(int id, Object txt, View view){
      var _txt = txt.toString();
      var resourceString = view.getResources().getString(id);
      return resourceString + " " + _txt;
    }
    @Override
    public int getItemCount() {
        return rankList.size();
    }








}
