package com.freeman.freetodo6.todo.group.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.freeman.freetodo6.R;
import com.freeman.freetodo6.todo.group.model.TodoGroup;
import com.freeman.freetodo6.todo.group.model.TodoGroupRepository;

import java.util.List;

public class SideMenuFavoriteAdapter
        extends RecyclerView.Adapter<SideMenuFavoriteAdapter.ThisViewHolder> {

    private static final String LOG_TAG =
            SideMenuFavoriteAdapter.class.getSimpleName();

    private final Context mContext;

    private final TodoGroupRepository mRepo;
    private List<TodoGroup> mItemLists;

    public SideMenuFavoriteAdapter(
            Context context, @NonNull TodoGroupRepository repository) {
        mContext = context;
        mRepo = repository;
    }

    @NonNull
    @Override
    public ThisViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater
                .inflate(R.layout.main_sidemenu_menu_favorites, viewGroup, false);
        return new ThisViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ThisViewHolder thisViewHolder, int i) {
        thisViewHolder.onBindData(mItemLists.get(i));
    }

    @Override
    public int getItemCount() {
        return mItemLists == null ? 0: mItemLists.size();
    }

    public void setItemLists(List<TodoGroup> todoGroups) {
        mItemLists = todoGroups;
        notifyDataSetChanged();
    }


    protected class ThisViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout mmGoAction;
        private ImageView mmColor;
        private TextView mmName;
        private TextView mmParentName;
        private TextView mmCount;

        private ThisViewHolder(@NonNull View itemView) {
            super(itemView);

            mmGoAction = itemView.findViewById(R.id.main_sidemenu_menu_favorite_go_action);
            mmColor = itemView.findViewById(R.id.main_sidemenu_menu_favorite_color);
            mmName = itemView.findViewById(R.id.main_sidemenu_menu_favorite_todogroup_name);
            mmParentName = itemView.findViewById(R.id.main_sidemenu_menu_favorite_todogroup_parent_name);
            mmCount = itemView.findViewById(R.id.main_sidemenu_menu_favorite_count);
        }

        private void onBindData(final TodoGroup todoGroup) {
            Log.d(LOG_TAG, todoGroup.toString());

            mmColor.setColorFilter(todoGroup.getColor());
            mmName.setText(todoGroup.getName());

            String parentNames = getParentNames(todoGroup.getParentId(), "");
            if (parentNames.isEmpty()) {
                mmParentName.setVisibility(View.GONE);
            } else {
                mmParentName.setVisibility(View.VISIBLE);
                mmParentName.setText(parentNames);
            }

            mmCount.setText("0");

            mmGoAction.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mmGoActionClick();
                }
            });
        }

        private void mmGoActionClick() {

        }

        private String getParentNames(String parentId, String separator) {
            String parentNames = "";

            if (!parentId.isEmpty()) {
                TodoGroup todoGroup = mRepo.get(parentId);
                parentNames += getParentNames(todoGroup.getParentId(), ">");
                parentNames += todoGroup.getName() + separator;
            }

            return parentNames;
        }
    }
}
