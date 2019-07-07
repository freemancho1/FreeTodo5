package com.freeman.secapp.utils.multilevelview;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public abstract class MultiLevelViewAdapter
        extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<ViewItem> mItemList = new ArrayList<>();

    public MultiLevelViewAdapter(List<ViewItem> viewItems) {
        this.mItemList = viewItems;
    }

    public List<ViewItem> getItemList() {
        return this.mItemList;
    }

    public void setItemList(List<ViewItem> itemList) {
        this.mItemList = itemList;
    }

    @Override
    public abstract RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType);

    @Override
    public abstract void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position);

    @Override
    public int getItemCount() {
        return mItemList.size();
    }

    public int getItemLevel(int position) {
        return mItemList.get(position).getLevel();
    }
}
