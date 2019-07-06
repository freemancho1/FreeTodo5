package com.freeman.freetodo5.todolist.group.adapter;

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
import android.widget.Toast;

import com.freeman.freetodo5.R;
import com.freeman.freetodo5.todolist.group.model.TodoListGroup;
import com.freeman.freetodo5.todolist.group.model.TodoListGroupRepository;

import java.util.List;

public class TodoListGroupSideMenuFavoriteAdapter
        extends RecyclerView.Adapter<TodoListGroupSideMenuFavoriteAdapter.ThisViewHolder> {
    private static final String LOG_TAG = TodoListGroupSideMenuFavoriteAdapter.class.getSimpleName();

    private final TodoListGroupRepository mRepo;
    private List<TodoListGroup> mItemLists;

    private final Context mContext;
    private final LayoutInflater mInflater;

    public TodoListGroupSideMenuFavoriteAdapter(
            Context context, @NonNull TodoListGroupRepository repository) {
        mRepo = repository;
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
        Log.d(LOG_TAG, "Constructor mItemLists size - " + getItemCount());
    }

    @NonNull
    @Override
    public ThisViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(R.layout.main_sidemenu_menu_favorites, viewGroup, false);
        return new ThisViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ThisViewHolder thisViewHolder, int i) {
        thisViewHolder.onBindData(mItemLists.get(i));
    }

    @Override
    public int getItemCount() {
        return mItemLists != null ? mItemLists.size(): 0;
    }

    public void setItemLists(List<TodoListGroup> itemLists) {
        mItemLists = itemLists;
        notifyDataSetChanged();
    }


    public class ThisViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private LinearLayout mmGoAction;
        private ImageView mmColor;
        private TextView mmName;
        private TextView mmParentName;
        private TextView mmCount;

        public ThisViewHolder(@NonNull View itemView) {
            super(itemView);
            mmGoAction = itemView.findViewById(R.id.main_sidemenu_menu_favorite_go_action);
            mmColor = itemView.findViewById(R.id.main_sidemenu_menu_favorite_color);
            mmName = itemView.findViewById(R.id.main_sidemenu_menu_favorite_todogroup_name);
            mmParentName = itemView.findViewById(R.id.main_sidemenu_menu_favorite_todogroup_parent_name);
            mmCount = itemView.findViewById(R.id.main_sidemenu_menu_items_todolistitem_count);
        }

        public void onBindData(final TodoListGroup todoListGroup) {
            Log.d(LOG_TAG, "Binding - " + todoListGroup.toString());

            mmColor.setColorFilter(todoListGroup.getColor());
            mmName.setText(todoListGroup.getName());

            String parentName = getParentNames(todoListGroup.getParentId(), "");
            if (parentName.equals("")) {
                mmParentName.setVisibility(View.GONE);
            } else {
                mmParentName.setText(parentName);
            }

            mmCount.setText("0");

            mmGoAction.setOnClickListener(this);
        }

        private void mmGoActionClick() {
            int position = getAdapterPosition();
            Toast.makeText(
                    mContext, mItemLists.get(position).getName()+" Clicked!!", Toast.LENGTH_SHORT)
                    .show();
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.main_sidemenu_menu_favorite_go_action:
                    mmGoActionClick();
                    break;
            }
        }

        private String getParentNames(String parentId, String separator) {
            String parentNames = "";
            if (!parentId.isEmpty()) {
                TodoListGroup todoListGroup = mRepo.get(parentId);
                parentNames += getParentNames(todoListGroup.getParentId(), ">");
                parentNames += todoListGroup.getName() + separator;
            }
            return parentNames;
        }
    }
}
