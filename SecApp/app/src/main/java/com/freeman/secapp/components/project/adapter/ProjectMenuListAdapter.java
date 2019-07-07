package com.freeman.secapp.components.project.adapter;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.freeman.secapp.R;
import com.freeman.secapp.components.project.model.Project;
import com.freeman.secapp.components.project.model.ProjectItem;
import com.multilevelview.MultiLevelAdapter;
import com.multilevelview.MultiLevelRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ProjectMenuListAdapter extends MultiLevelAdapter {
    private static final String LOG_TAG = "[PMLA]";

    private static final int FAVORITE_CHECK_COLOR = 0xFFFF2222;
    private static final int FAVORITE_NONCHECK_COLOR = 0xFFCCCCCC;

    private final Context mContext;
    private List<ProjectItem> mItems;
    private final MultiLevelRecyclerView mThisRecyclerView;
    private final LayoutInflater mInflater;

    public ProjectMenuListAdapter(
            Context context, List<ProjectItem> recyclerViewItems,
            MultiLevelRecyclerView multiLevelRecyclerView) {
        super(recyclerViewItems);
        Log.d(LOG_TAG, "Constructor Started..");

        mContext = context;
        mItems = recyclerViewItems;
        mThisRecyclerView = multiLevelRecyclerView;
        mInflater = LayoutInflater.from(context);
    }

    public void setItems(List<ProjectItem> items) {
        Log.d(LOG_TAG, "All item reset..");

        mItems = items;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mItems != null ? mItems.size(): 0;
    }

    @Override
    public ProjectMenuListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(LOG_TAG, "ViewHolder created..");
        View view = mInflater.inflate(R.layout.project_menu_list_item, parent, false);
        return new ProjectMenuListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ProjectMenuListViewHolder viewHolder = (ProjectMenuListViewHolder) holder;
        ProjectItem item = mItems.get(position);
        Project project = item.getProjectItem();
        float dp = mContext.getResources().getDisplayMetrics().density;

        viewHolder.mColorDisplayImage.setColorFilter(project.getColor());
        viewHolder.mProjectNameText.setText(project.getName());
        int paddingStart = (int)(project.getDepth() * 20 * dp);
        viewHolder.mProjectNameText.setPadding(paddingStart, 0, 0, 0);

        if (!project.isChildren()) viewHolder.mChildShowImage.setVisibility(View.INVISIBLE);

        if (project.isFavorite()) {
            viewHolder.mFavoriteImage.setColorFilter(FAVORITE_CHECK_COLOR);
            viewHolder.mFavoriteImage.setImageResource(R.drawable.ic_symbol_heart);
        } else {
            viewHolder.mFavoriteImage.setColorFilter(FAVORITE_NONCHECK_COLOR);
            viewHolder.mFavoriteImage.setImageResource(R.drawable.ic_symbol_heart_broken);
        }

        Log.d(LOG_TAG, "Date Binding end: " + project.toString());
    }


    private class ProjectMenuListViewHolder extends RecyclerView.ViewHolder {
        private ImageView mColorDisplayImage;
        private TextView mProjectNameText;
        private ImageView mChildShowImage;
        private ImageView mFavoriteImage;

        public ProjectMenuListViewHolder(@NonNull View itemView) {
            super(itemView);
            mColorDisplayImage = itemView.findViewById(R.id.project_list_color);
            mProjectNameText = itemView.findViewById(R.id.project_list_item_name);
            mChildShowImage = itemView.findViewById(R.id.project_list_child_show);
            mFavoriteImage = itemView.findViewById(R.id.project_list_favorite);

            mChildShowImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    v.getContext().toggleItemsGroup(getAdapterPosition());
                    mThisRecyclerView.toggleItemsGroup(getAdapterPosition());
                    mChildShowImage.animate()
                            .rotation(mItems.get(getAdapterPosition()).isExpanded() ? -180: 0)
                            .start();
                }
            });
        }
    }
}
