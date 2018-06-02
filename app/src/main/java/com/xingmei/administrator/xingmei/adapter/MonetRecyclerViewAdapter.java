package com.xingmei.administrator.xingmei.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xingmei.administrator.xingmei.R;

public class MonetRecyclerViewAdapter extends RecyclerView.Adapter<MonetRecyclerViewAdapter.MonetViewHolder>{

    @Override
    public MonetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image,null);
        MonetRecyclerViewAdapter.MonetViewHolder viewHolder = new MonetRecyclerViewAdapter.MonetViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MonetViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 50;
    }

    class MonetViewHolder extends RecyclerView.ViewHolder{
        MonetViewHolder(View itemView){
            super(itemView);
        }
    }
}
