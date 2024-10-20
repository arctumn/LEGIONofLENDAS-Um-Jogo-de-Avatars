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
    public ArrayList<itemsRanking> mLista;
    public static class itemViewHolder extends RecyclerView.ViewHolder{

        public ImageView mImageView;
        public TextView mTextView1;
        public TextView mTextRnkTxtNivel;
        public TextView mTextVRank;
        public itemViewHolder(View itemView){
            super(itemView);
            mTextVRank = itemView.findViewById(R.id.rank);
            mImageView = itemView.findViewById(R.id.rnkAvatar);
            mTextView1 = itemView.findViewById(R.id.rnkNome);
            mTextRnkTxtNivel = itemView.findViewById(R.id.rnkTxtNivel);
        }
    }

    public itemRankingAdapter(ArrayList<itemsRanking> listaItems){
        mLista = listaItems;
    }

    @NonNull
    @Override
    public itemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ranking, parent, false);
        return new itemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull itemViewHolder holder, int position) {

        itemsRanking currentItem = mLista.get(position);
        holder.mImageView.setImageResource(currentItem.getmImageResource());
        holder.mTextView1.setText(currentItem.getmText1());
        holder.mTextRnkTxtNivel.setText("LVL:" + currentItem.getmText2());
        holder.mTextVRank.setText(String.valueOf(position+1));
    }

    @Override
    public int getItemCount() {
        return mLista.size();
    }







}
