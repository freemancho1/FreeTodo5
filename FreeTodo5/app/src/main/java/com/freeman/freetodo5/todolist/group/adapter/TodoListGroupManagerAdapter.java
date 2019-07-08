package com.freeman.freetodo5.todolist.group.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.freeman.freetodo5.GlobalVariable;
import com.freeman.freetodo5.R;
import com.freeman.freetodo5.todolist.group.model.TodoListGroup;
import com.freeman.freetodo5.todolist.group.model.TodoListGroupRepository;

import java.util.List;

public class TodoListGroupManagerAdapter
        extends RecyclerView.Adapter<TodoListGroupManagerAdapter.ThisViewHolder> {

    private static final String LOG_TAG = TodoListGroupManagerAdapter.class.getSimpleName();

    private static final int FAVORITE_CHECK_COLOR = 0xFFEE3333;
    private static final int FAVORITE_NONCHECK_COLOR = 0xFFDDDDDD;

    private final Context mContext;
    private final TodoListGroupRepository mRepo;
    private List<TodoListGroup> mItemLists;

    private final LayoutInflater mInflater;
    private final float mDensity;

    public TodoListGroupManagerAdapter(
            Context context, @NonNull TodoListGroupRepository repository) {
        mContext = context;
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
        thisViewHolder.onBindData(mItemLists.get(i), i);
    }

    @Override
    public int getItemCount() {
        return mItemLists != null ? mItemLists.size(): 0;
    }

    public void setItemLists(List<TodoListGroup> itemLists) {
        for (int i=0; i<itemLists.size(); i++) {
            itemLists.get(i).setExpanded(itemLists.get(i).isChildren());
        }
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

        public void onBindData(final TodoListGroup todoListGroup, int position) {
            Log.d(LOG_TAG, "Binding data - " + todoListGroup.toString());

            mmColor.setColorFilter(todoListGroup.getColor());
            ((ViewGroup.MarginLayoutParams) mmColor.getLayoutParams())
                    .leftMargin = (int) (todoListGroup.getDepth() * 25 * mDensity);

            mmName.setText(todoListGroup.getName());

            if (todoListGroup.isChildren()) {
                showAnimation(mItemLists.get(position).isExpanded());
                mmChildrenShow.setVisibility(View.VISIBLE);
            } else {
                mmChildrenShow.setVisibility(View.INVISIBLE);
            }

            if (todoListGroup.isFavorite()) {
                mmFavorite.setColorFilter(FAVORITE_CHECK_COLOR);
                mmFavorite.setImageResource(R.drawable.ic_heart);
            } else {
                mmFavorite.setColorFilter(FAVORITE_NONCHECK_COLOR);
                mmFavorite.setImageResource(R.drawable.ic_heart_broken);
            }

            mmGoAction.setOnClickListener(this);
            mmChildrenShow.setOnClickListener(this);
            mmFavorite.setOnClickListener(this);
            mmDelete.setOnClickListener(this);
        }

        private void mmGoActionClick() {

        }

        private void mmDeleteClick() {
            final int position = getAdapterPosition();
            final TodoListGroup todoListGroup = mItemLists.get(position);

            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder.setTitle(todoListGroup.getName())
                    .setMessage(R.string.todogroup_manager_alert_delete_message)
                    .setPositiveButton(R.string.sys_msg_delete,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    deleteTodoListGroup(position, todoListGroup);
                                }
                            })
                    .setNegativeButton(R.string.sys_msg_cancel,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {}
                            });

            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }

        private void deleteTodoListGroup(int position, TodoListGroup todoListGroup) {
            if (todoListGroup.isChildren()) {
                invisibleChildren(position+1, position, true);
            }

            mItemLists.remove(position);
            notifyItemRangeRemoved(position, 1);

            mRepo.delete(todoListGroup);

            if (mRepo.getChildren(todoListGroup.getParentId()).size() == 0) {
                TodoListGroup item = mRepo.get(todoListGroup.getParentId());
                item.setChildren(false);
                mRepo.update(item);
                mItemLists.get(position-1).setChildren(false);
                notifyItemChanged(position-1, mItemLists.get(position-1));
            }

            GlobalVariable.getInstance().setSideMenuChange(true);
        }

        private void mmFavoriteClick() {
            final int position = getAdapterPosition();
            final TodoListGroup todoListGroup = mItemLists.get(position);

            if (todoListGroup.isFavorite()) {
                mmFavorite.setColorFilter(FAVORITE_NONCHECK_COLOR);
                mmFavorite.setImageResource(R.drawable.ic_heart_broken);
                todoListGroup.setFavorite(false);
            } else {
                mmFavorite.setColorFilter(FAVORITE_CHECK_COLOR);
                mmFavorite.setImageResource(R.drawable.ic_heart);
                todoListGroup.setFavorite(true);
            }
            mItemLists.set(position, todoListGroup);
            notifyItemChanged(position, todoListGroup);
            mRepo.update(todoListGroup);
            GlobalVariable.getInstance().setSideMenuChange(true);
        }

        private void mmChildrenShowClick() {
            int position = getAdapterPosition();
            TodoListGroup todoListGroup = mItemLists.get(position);

            if (todoListGroup.isExpanded()) {
                todoListGroup.setExpanded(false);
                invisibleChildren(position+1, position);
            } else {
                todoListGroup.setExpanded(true);
                visibleChildren(position);
            }

            mItemLists.set(position, todoListGroup);

            showAnimation(todoListGroup.isExpanded());
        }

        private void showAnimation(boolean isExpanded) {
            mmChildrenShow.animate()
                    .rotation(isExpanded ? -180: 0).start();
        }

        private void visibleChildren(int position) {
            List<TodoListGroup> children = mRepo.getChildren(mItemLists.get(position).getId());
            mItemLists.addAll(position+1, children);
            notifyItemRangeInserted(position+1, children.size());
        }

        private void invisibleChildren(int startPosition, int thisPosition) {
            invisibleChildren(startPosition, thisPosition, false);
        }
        private void invisibleChildren(int startPosition, int thisPosition, boolean isDbDelete) {
            int allItemSize = mItemLists.size();

            for (int i = startPosition; i < allItemSize; i++) {

                if (mItemLists.get(i).getParentId().equals(mItemLists.get(thisPosition).getId())) {

                    if (mItemLists.get(i).isExpanded()) {
                        mItemLists.get(i).setExpanded(false);
                        invisibleChildren(i+1, i, isDbDelete);
                        allItemSize = mItemLists.size();
                    }

                    if (isDbDelete) mRepo.delete(mItemLists.get(i));

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
                case R.id.todo_list_group_manager_view_items_go_action:
                    mmGoActionClick();
                    break;
                case R.id.todo_list_group_manager_view_items_delete:
                    mmDeleteClick();
                    break;
                case R.id.todo_list_group_manager_view_items_favorite:
                    mmFavoriteClick();
                    break;
                case R.id.todo_list_group_manager_view_items_children_show:
                    mmChildrenShowClick();
                    break;
            }
        }
    }
}
