package com.lol.LEGIONofLENDAS;

import android.content.Intent;
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
    public static class itemViewHolder extends RecyclerView.ViewHolder{

        public ImageView mImageView;
        public TextView price;
        public TextView F;
        public TextView M;
        public TextView D;
        public TextView DM;
        public TextView V;
        public TextView nome;
        public Button comprar;
        public itemViewHolder(View itemView){
            super(itemView);
            price = itemView.findViewById(R.id.shopPrice);
            mImageView = itemView.findViewById(R.id.shopAvatar);
            F = itemView.findViewById(R.id.item_status);
            M = itemView.findViewById(R.id.magic_Shop);
            D = itemView.findViewById(R.id.defense_Shop);
            DM = itemView.findViewById(R.id.magic_defense_Shop);
            V = itemView.findViewById(R.id.hp_Shop);
            nome = itemView.findViewById(R.id.item_name);
            comprar = itemView.findViewById(R.id.button_comprar);
        }
    }

    public itemShopAdapter(ArrayList<itemsShop> listaItems){
        mLista = listaItems;
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
        holder.mImageView.setImageResource(currentItem.getImage());
        holder.price.setText(currentItem.getPrice());
        holder.nome.setText(String.valueOf(currentItem.getName()));
        holder.V.setText(String.valueOf(currentItem.getLife()));
        holder.DM.setText(String.valueOf(currentItem.getMagicDefense()));
        holder.D.setText(String.valueOf(currentItem.getDefense()));
        holder.M.setText(String.valueOf(currentItem.getMagic()));
        holder.F.setText(String.valueOf(currentItem.getStr()));
        holder.comprar.setText("Comprar");
        holder.comprar.setOnClickListener(v -> {
            util.txtMessageServer(currentItem.getUid(),"Comprar",new ArrayList<>(Collections.singletonList(currentItem.getName())));
        });
    }

    @Override
    public int getItemCount() {
        return mLista.size();
    }







}
