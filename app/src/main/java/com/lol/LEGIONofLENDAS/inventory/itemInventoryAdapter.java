package com.lol.LEGIONofLENDAS.inventory;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lol.LEGIONofLENDAS.R;

import java.util.ArrayList;


public class itemInventoryAdapter extends RecyclerView.Adapter<itemInventoryAdapter.itemViewHolder> {
    public ArrayList<UserItem> mLista;

    public static class itemViewHolder extends RecyclerView.ViewHolder {

        public ImageView mImageView;
        public TextView mTextView1;


        public itemViewHolder(View itemView) {
            super(itemView);

            mImageView = itemView.findViewById(R.id.itemAvatar);
            mTextView1 = itemView.findViewById(R.id.itemNome);

        }
    }

    public itemInventoryAdapter(ArrayList<UserItem> listaItems) {
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

        var currentItem = mLista.get(position);
        holder.mImageView.setImageResource(currentItem.image);
        holder.mTextView1.setText(currentItem.name);
    }

    @Override
    public int getItemCount() {
        return mLista.size();
    }
}