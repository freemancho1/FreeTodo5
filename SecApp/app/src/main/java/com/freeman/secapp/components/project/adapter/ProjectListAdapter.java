package com.freeman.secapp.components.project.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.freeman.secapp.R;
import com.freeman.secapp.components.project.model.Project;

import java.util.List;

public class ProjectListAdapter extends RecyclerView.Adapter<ProjectListAdapter.ViewHolder> {
    private static final String LOG_TAG = "[ProjectListAdapter] ";
    private static final int FAVORITE_CHECK_COLOR = 0xFFFF2222;
    private static final int FAVORITE_NONCHECK_COLOR = 0xFFCCCCCC;

    private final LayoutInflater mInflater;
    private List<Project> mProjects;

    public ProjectListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    public void setProjects(List<Project> projects) {
        mProjects = projects;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProjectListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(R.layout.item_project_list, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull ProjectListAdapter.ViewHolder viewHolder, int i) {
        Project project = mProjects.get(i);
        viewHolder.mColor.setColorFilter(project.getColor());
        viewHolder.mProjectName.setText(project.getName());
        viewHolder.mProjectName.setPadding(project.getDepth()*20, 0, 0, 0);

        if (!project.isChildren()) viewHolder.mChildShow.setVisibility(View.INVISIBLE);

        if (project.isFavorite()) {
            viewHolder.mFavorite.setColorFilter(FAVORITE_CHECK_COLOR);
            viewHolder.mFavorite.setImageResource(R.drawable.ic_symbol_heart);
        } else {
            viewHolder.mFavorite.setColorFilter(FAVORITE_NONCHECK_COLOR);
            viewHolder.mFavorite.setImageResource(R.drawable.ic_symbol_heart_broken);
        }
    }

    @Override
    public int getItemCount() {
        return mProjects != null ? mProjects.size(): 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView mColor;
        private TextView mProjectName;
        private ImageView mChildShow;
        private ImageView mFavorite;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mColor = itemView.findViewById(R.id.project_list_color);
            mProjectName = itemView.findViewById(R.id.project_list_item_name);
            mChildShow = itemView.findViewById(R.id.project_list_child_show);
            mFavorite = itemView.findViewById(R.id.project_list_favorite);
        }
    }
}
