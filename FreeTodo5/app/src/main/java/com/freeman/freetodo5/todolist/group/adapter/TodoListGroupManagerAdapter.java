package com.freeman.freetodo5.todolist.group.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.freeman.freetodo5.R;
import com.freeman.freetodo5.todolist.group.model.TodoListGroup;
import com.freeman.freetodo5.todolist.group.model.TodoListGroupRepository;

import java.util.List;

public class TodoListGroupManagerAdapter
        extends RecyclerView.Adapter<TodoListGroupManagerAdapter.ThisViewHolder> {

    private static final String LOG_TAG = TodoListGroupManagerAdapter.class.getSimpleName();

    private final TodoListGroupRepository mRepo;
    private List<TodoListGroup> mItemLists;

    private final LayoutInflater mInflater;
    private final float mDensity;

    public TodoListGroupManagerAdapter(
            Context context, @NonNull TodoListGroupRepository repository) {
        mRepo = repository;
        mInflater = LayoutInflater.from(context);
        mDensity = context.getResources().getDisplayMetrics().density;
    }

    @NonNull
    @Override
    public ThisViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(R.layout.todo_list_group_manager_view_items, viewGroup, false);
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
        private ImageView mmFavorite;
        private ImageView mmDelete;
        private ImageView mmChildrenShow;

        public ThisViewHolder(@NonNull View itemView) {
            super(itemView);

            mmGoAction = itemView.findViewById(R.id.todo_list_group_manager_view_items_go_action);
            mmColor = itemView.findViewById(R.id.todo_list_group_manager_view_items_color);
            mmName = itemView.findViewById(R.id.todo_list_group_manager_view_items_todogroup_name);
            mmFavorite = itemView.findViewById(R.id.todo_list_group_manager_view_items_favorite);
            mmDelete = itemView.findViewById(R.id.todo_list_group_manager_view_items_delete);
            mmChildrenShow = itemView.findViewById(R.id.todo_list_group_manager_view_items_children_show);
        }

        public void onBindData(final TodoListGroup todoListGroup) {

        }

        @Override
        public void onClick(View v) {

        }
    }
}
