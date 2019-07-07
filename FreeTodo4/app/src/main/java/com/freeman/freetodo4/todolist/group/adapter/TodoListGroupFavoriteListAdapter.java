package com.freeman.freetodo4.todolist.group.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
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

import com.freeman.freetodo4.R;
import com.freeman.freetodo4.todolist.group.model.TodoListGroup;
import com.freeman.freetodo4.todolist.group.model.TodoListGroupRepository;
import com.freeman.freetodo4.utils.db.InitDatabase;

import java.util.List;

@SuppressLint("LongLogTag")
public class TodoListGroupFavoriteListAdapter
        extends RecyclerView.Adapter<TodoListGroupFavoriteListAdapter.ThisViewHolder> {

    private static final String LOG_TAG = "[TodoListGroupFavoriteListAdapter]";

    private final TodoListGroupRepository mRepo;
    private List<TodoListGroup> mItemLists;

    private final Context mContext;
    private final LayoutInflater mInflater;

    public TodoListGroupFavoriteListAdapter(
            Context context, @NonNull TodoListGroupRepository repository) {
        mRepo = repository;
        mContext = context;
        mInflater = LayoutInflater.from(context);
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
        private ImageView mmReset;

        public ThisViewHolder(@NonNull View itemView) {
            super(itemView);
            mmGoAction = itemView.findViewById(R.id.main_sidemenu_menu_favorite_go_action);
            mmColor = itemView.findViewById(R.id.main_sidemenu_menu_favorite_color);
            mmName = itemView.findViewById(R.id.main_sidemenu_menu_favorite_todogroup_name);
            mmParentName = itemView.findViewById(R.id.main_sidemenu_menu_favorite_todogroup_parent_name);
            mmCount = itemView.findViewById(R.id.main_sidemenu_menu_items_todolistitem_count);
            mmReset = itemView.findViewById(R.id.main_sidemenu_menu_items_reset);
        }

        public void onBindData(final TodoListGroup todoListGroup) {

            Log.d(LOG_TAG, "Binding - " + todoListGroup.toString());

            mmGoAction.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(
                            mContext, todoListGroup.getName()+" Clicked!!", Toast.LENGTH_SHORT)
                            .show();
                    //Intent intent = new Intent(mContext, InitDatabase.class);
                    //mContext.startActivity(intent);
                }
            });

            mmColor.setColorFilter(todoListGroup.getColor());

            mmName.setText(todoListGroup.getName());

            String parentName = getParentNames(todoListGroup.getParentId(), "");
            if (parentName.equals("")) {
                mmParentName.setVisibility(View.GONE);
            } else {
                mmParentName.setText(parentName);
            }

            mmCount.setText("0");

            mmReset.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mmResetClick();
                }
            });
        }

        private String getParentNames(long parentId, String separator) {
            String returnParentName = "";

            if (parentId != 0) {
                TodoListGroup todoListGroup = mRepo.get(parentId);
                if (todoListGroup.getParentId() != 0) {
                    returnParentName += getParentNames(todoListGroup.getParentId(), ">");
                }
                returnParentName += todoListGroup.getName() + separator;
            }

            return returnParentName;
        }

        @Override
        public void onClick(View v) {
//            switch (v.getId()) {
//                case R.id.main_sidemenu_menu_items_reset:
//                    mmResetClick();
//                    break;
//            }
        }

        private void mmResetClick() {
            int position = getAdapterPosition();
            TodoListGroup todoListGroup = mItemLists.get(position);
            todoListGroup.setIsFavorite(0);
            mRepo.update(todoListGroup);
            mItemLists.remove(position);
            notifyDataSetChanged();
        }
    }
}
