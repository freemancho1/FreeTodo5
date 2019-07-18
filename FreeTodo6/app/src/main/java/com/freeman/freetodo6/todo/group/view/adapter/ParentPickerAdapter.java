package com.freeman.freetodo6.todo.group.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.freeman.freetodo6.R;
import com.freeman.freetodo6.todo.group.model.TodoGroup;

import java.util.List;

public class ParentPickerAdapter extends RecyclerView.Adapter<ParentPickerAdapter.ThisViewHolder> {
    private static final String LOG_TAG = ParentPickerAdapter.class.getSimpleName();

    private final Context mContext;

    private List<TodoGroup> mItemLists;

    public ParentPickerAdapter(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    public ThisViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater
                .inflate(R.layout.todogroup_list_items, viewGroup, false);
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

    public TodoGroup getTodoGroup(int position) {
        return mItemLists.get(position);
    }

    public class ThisViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout mmGoAction;
        private ImageView mmColor;
        private TextView mmName;
        private TextView mmCount;
        private ImageView mmChildrenShow;
        private ImageView mmFavorite;
        private ImageView mmDelete;

        private ThisViewHolder(@NonNull View itemView) {
            super(itemView);

            mmGoAction = itemView.findViewById(R.id.todogroup_list_items_go_action);
            mmColor = itemView.findViewById(R.id.todogroup_list_items_color);
            mmName = itemView.findViewById(R.id.todogroup_list_items_todogroup_name);
            mmCount = itemView.findViewById(R.id.todogroup_list_items_count);
            mmChildrenShow = itemView.findViewById(R.id.todogroup_list_items_children_show);
            mmFavorite = itemView.findViewById(R.id.todogroup_list_items_favorite);
            mmDelete = itemView.findViewById(R.id.todogroup_list_items_delete);
        }

        private void onBindData(final TodoGroup todoGroup) {
            mmColor.setColorFilter(todoGroup.getColor());
            float density = mContext.getResources().getDisplayMetrics().density;
            ((ViewGroup.MarginLayoutParams) mmColor.getLayoutParams())
                    .leftMargin = (int) (todoGroup.getDepth() * 25 * density);

            mmName.setText(todoGroup.getName());

            mmChildrenShow.setVisibility(View.GONE);
            mmFavorite.setVisibility(View.GONE);
            mmDelete.setVisibility(View.GONE);
            mmCount.setVisibility(View.GONE);
        }

    }
}
