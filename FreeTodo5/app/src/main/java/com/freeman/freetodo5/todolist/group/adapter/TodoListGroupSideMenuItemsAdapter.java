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

import com.freeman.freetodo5.R;
import com.freeman.freetodo5.todolist.group.model.TodoListGroup;
import com.freeman.freetodo5.todolist.group.model.TodoListGroupRepository;

import java.util.List;

public class TodoListGroupSideMenuItemsAdapter
        extends RecyclerView.Adapter<TodoListGroupSideMenuItemsAdapter.ThisViewHolder> {
    private static final String LOG_TAG = TodoListGroupSideMenuItemsAdapter.class.getSimpleName();

    private final TodoListGroupRepository mRepo;
    private List<TodoListGroup> mItemLists;

    private final LayoutInflater mInflater;
    private final float mDensity;

    public TodoListGroupSideMenuItemsAdapter(
            Context context, @NonNull TodoListGroupRepository repository) {
        mRepo = repository;
        mInflater = LayoutInflater.from(context);
        mDensity = context.getResources().getDisplayMetrics().density;
        Log.d(LOG_TAG, "Constructor mItemLists size - " + getItemCount());
    }
    @NonNull
    @Override
    public ThisViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(R.layout.main_sidemenu_menu_items, viewGroup, false);
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
        private TextView mmCount;
        private ImageView mmChildrenShow;

        public ThisViewHolder(@NonNull View itemView) {
            super(itemView);
            mmGoAction = itemView.findViewById(R.id.main_sidemenu_menu_items_go_action);
            mmColor = itemView.findViewById(R.id.main_sidemenu_menu_items_color);
            mmName = itemView.findViewById(R.id.main_sidemenu_menu_items_todogroup_name);
            mmCount = itemView.findViewById(R.id.main_sidemenu_menu_items_todolistitem_count);
            mmChildrenShow = itemView.findViewById(R.id.main_sidemenu_menu_items_children_show);
        }

        public void onBindData(final TodoListGroup todoListGroup) {
            Log.d(LOG_TAG, "Binding data - " + todoListGroup.toString());

            if (todoListGroup.isFavorite()) {
                mmColor.setImageResource(R.drawable.ic_heart);
            } else {
                mmColor.setImageResource(R.drawable.ic_blank_circle);
            }
            mmColor.setColorFilter(todoListGroup.getColor());
            ((ViewGroup.MarginLayoutParams) mmColor.getLayoutParams())
                    .leftMargin = (int) (todoListGroup.getDepth() * 25 * mDensity);

            mmName.setText(todoListGroup.getName());

            if (todoListGroup.isChildren()) {
                mmChildrenShow.setVisibility(View.VISIBLE);
                mmCount.setVisibility(View.GONE);
            } else {
                mmChildrenShow.setVisibility(View.GONE);
                mmCount.setVisibility(View.VISIBLE);
                mmCount.setText("0");
            }

            mmGoAction.setOnClickListener(this);
            mmChildrenShow.setOnClickListener(this);
        }

        private void mmGoActionClick() {

        }

        private void mmChildrenShowClick() {
            int position = getAdapterPosition();
            TodoListGroup todoListGroup = mItemLists.get(position);

            if (todoListGroup.isExpanded()) {
                todoListGroup.setExpanded(false);
                invisibleChildren(0, position);
            } else {
                todoListGroup.setExpanded(true);
                visibleChildren(position);
            }

            mItemLists.set(position, todoListGroup);

            mmChildrenShow.animate()
                    .rotation(todoListGroup.isExpanded() ? -180: 0).start();
        }

        private void visibleChildren(int position) {
            List<TodoListGroup> children = mRepo.getChildren(mItemLists.get(position).getId());
            mItemLists.addAll(position+1, children);
            notifyItemRangeInserted(position+1, children.size());
        }

        private void invisibleChildren(int startPosition, int thisPosition) {
            int allItemSize = mItemLists.size();

            for (int i = startPosition; i < allItemSize; i++) {

                if (mItemLists.get(i).getParentId().equals(mItemLists.get(thisPosition).getId())) {

                    if (mItemLists.get(i).isExpanded()) {
                        mItemLists.get(i).setExpanded(false);
                        invisibleChildren(i+1, i);
                        allItemSize = mItemLists.size();
                    }

                    mItemLists.remove(i);
                    notifyItemRangeRemoved(i, 1);

                    allItemSize--;
                    i--;
                }
            }

        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.main_sidemenu_menu_items_go_action:
                    mmGoActionClick();
                    break;
                case R.id.main_sidemenu_menu_items_children_show:
                    mmChildrenShowClick();
                    break;
            }
        }
    }
}
