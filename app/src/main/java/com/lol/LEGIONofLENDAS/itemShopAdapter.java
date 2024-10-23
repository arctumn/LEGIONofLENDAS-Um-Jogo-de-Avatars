package com.lol.LEGIONofLENDAS;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;

public class itemShopAdapter  extends RecyclerView.Adapter<itemShopAdapter.itemViewHolder> {
    public ArrayList<itemsShop> mLista;
    public Shop shop;
    public static class itemViewHolder extends RecyclerView.ViewHolder{
        public ImageView mImageView;
        public TextView price;
        public TextView strength;
        public TextView magic;
        public TextView defence;
        public TextView magicDefence;
        public TextView life;
        public TextView name;
        public Button buy;
        public itemViewHolder(View itemView){
            super(itemView);
            price = itemView.findViewById(R.id.shopPrice);
            mImageView = itemView.findViewById(R.id.shopAvatar);
            strength = itemView.findViewById(R.id.item_status);
            magic = itemView.findViewById(R.id.magic_Shop);
            defence = itemView.findViewById(R.id.defense_Shop);
            magicDefence = itemView.findViewById(R.id.magic_defense_Shop);
            life = itemView.findViewById(R.id.hp_Shop);
            name = itemView.findViewById(R.id.item_name);
            buy = itemView.findViewById(R.id.button_comprar);
        }
    }

    public itemShopAdapter(ArrayList<itemsShop> listaItems,Shop shop){
        mLista = listaItems;
        shop = shop;
    }

    @NonNull
    @Override
    public itemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_shop, parent, false);
        return new itemViewHolder(v);
    }

    Utils util = new Utils();
    @Override
    public void onBindViewHolder(@NonNull itemViewHolder holder, int position) {
        itemsShop currentItem = mLista.get(position);
        var _shop = shop;
        var price = shop.getString(R.string.Item_Price) + " " + currentItem.getPrice();
        var name =  shop.getString(R.string.Item_Name) + " " + currentItem.getName();
        var life = shop.getString(R.string.Item_HP) + " " + currentItem.getLife();
        var strength = shop.getString(R.string.Item_STR) + " " + currentItem.getStr();
        var magic = shop.getString(R.string.Item_Magic) + " " + currentItem.getMagic();
        var defence = shop.getString(R.string.Item_Defence) + " " + currentItem.getDefense();
        var magicDefence = shop.getString(R.string.Item_Magic_Defence) + " " + currentItem.getMagicDefense();

        holder.mImageView.setImageResource(currentItem.getImage());
        holder.price.setText(price);
        holder.name.setText(name);
        holder.life.setText(life);
        holder.strength.setText(strength);
        holder.magic.setText(magic);
        holder.defence.setText(defence);
        holder.magicDefence.setText(magicDefence);
        holder.buy.setOnClickListener(v -> {
            util.txtMessageServer(
                    currentItem.getUid(),"Comprar",
                    new ArrayList<>(Collections.singletonList(currentItem.getName()))
            );
            var itemBough = R.string.Item_Bought;
            Toast.makeText(shop,R.string.Item_Bought,Toast.LENGTH_SHORT).show();
        });

    }

    @Override
    public int getItemCount() {
        return mLista.size();
    }







}
