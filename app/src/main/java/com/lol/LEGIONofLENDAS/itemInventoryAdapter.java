package com.lol.LEGIONofLENDAS;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class itemInventoryAdapter extends RecyclerView.Adapter<itemInventoryAdapter.itemViewHolder> {
    public ArrayList<itemsRanking> mLista;

    public static class itemViewHolder extends RecyclerView.ViewHolder {

        public ImageView mImageView;
        public TextView mTextView1;


        public itemViewHolder(View itemView) {
            super(itemView);

            mImageView = itemView.findViewById(R.id.itemAvatar);
            mTextView1 = itemView.findViewById(R.id.itemNome);

        }
    }

    public itemInventoryAdapter(ArrayList<itemsRanking> listaItems) {
        mLista = listaItems;
    }

    @NonNull
    @Override
    public itemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_inventario, parent, false);
        return new itemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull itemViewHolder holder, int position) {

        itemsRanking currentItem = mLista.get(position);
        holder.mImageView.setImageResource(currentItem.getmImageResource());
        holder.mTextView1.setText(currentItem.getmText1());
    }

    @Override
    public int getItemCount() {
        return mLista.size();
    }
}