package com.freeman.freetodo6.todo.group.view.adapter;

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

import com.freeman.freetodo6.R;
import com.freeman.freetodo6.todo.group.model.TodoGroup;
import com.freeman.freetodo6.todo.group.model.TodoGroupRepository;
import com.freeman.freetodo6.utils.AppStatus;

import java.util.List;

public class ManagerAdapter extends RecyclerView.Adapter<ManagerAdapter.ThisViewHolder> {

    private static final String LOG_TAG = ManagerAdapter.class.getSimpleName();

    private static final int FAVORITE_CHECK_COLOR = 0xFFEE3333;
    private static final int FAVORITE_NONCHECK_COLOR = 0xFFDDDDDD;

    private final Context mContext;

    private final TodoGroupRepository mRepo;
    private List<TodoGroup> mItemLists;

    public ManagerAdapter(Context context, @NonNull TodoGroupRepository repository) {
        mContext = context;
        mRepo = repository;
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
        thisViewHolder.onBindData(mItemLists.get(i), i);
    }

    @Override
    public int getItemCount() {
        return mItemLists == null ? 0: mItemLists.size();
    }

    public void setItemLists(List<TodoGroup> todoGroups) {
        for (int i=0; i<todoGroups.size(); i++) {
            todoGroups.get(i).setExpanded(todoGroups.get(i).isChildren());
        }
        mItemLists = todoGroups;
        notifyDataSetChanged();
    }


    protected class ThisViewHolder
            extends RecyclerView.ViewHolder implements View.OnClickListener {

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

        private void onBindData(final TodoGroup todoGroup, int position) {
            Log.d(LOG_TAG, "Binding data["+position+"] - " + todoGroup.toString());

            mmColor.setColorFilter(todoGroup.getColor());
            float density = mContext.getResources().getDisplayMetrics().density;
            ((ViewGroup.MarginLayoutParams) mmColor.getLayoutParams())
                    .leftMargin = (int) (todoGroup.getDepth() * 25 * density);

            mmName.setText(todoGroup.getName());

            if (todoGroup.isChildren()) {
                showAnimation(mItemLists.get(position).isExpanded());
                mmChildrenShow.setVisibility(View.VISIBLE);
            } else {
                mmChildrenShow.setVisibility(View.INVISIBLE);
            }

            if (todoGroup.isFavorite()) {
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

            mmCount.setVisibility(View.GONE);

        }

        private void mmGoActionClick(int position, TodoGroup todoGroup) {

        }

        private void mmDeleteClick(final int position, final TodoGroup todoGroup) {
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder.setTitle(todoGroup.getName())
                    .setMessage(R.string.sys_alert_delete_message)
                    .setPositiveButton(R.string.sys_delete,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    deleteTodoGroup(position, todoGroup);
                                }
                            })
                    .setNegativeButton(R.string.sys_cancel,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }

        private void deleteTodoGroup(int position, TodoGroup todoGroup) {
            if (todoGroup.isChildren()) {
                invisibleChildren(position+1, position, true);
            }

            mItemLists.remove(position);
            notifyItemRangeRemoved(position, 1);

            mRepo.delete(todoGroup);

            if (mRepo.getChildren(todoGroup.getParentId()).size() == 0) {
                TodoGroup item = mRepo.get(todoGroup.getParentId());
                item.setChildren(false);
                mRepo.update(item);
                mItemLists.get(position-1).setChildren(false);
                notifyItemChanged(position-1, mItemLists.get(position-1));
            }

            AppStatus.getInstance().setSideMenuChange(true);
        }

        private void mmFavoriteClick(int position, TodoGroup todoGroup) {
            if (todoGroup.isFavorite()) {
                mmFavorite.setColorFilter(FAVORITE_NONCHECK_COLOR);
                mmFavorite.setImageResource(R.drawable.ic_heart_broken);
                todoGroup.setFavorite(false);
            } else {
                mmFavorite.setColorFilter(FAVORITE_CHECK_COLOR);
                mmFavorite.setImageResource(R.drawable.ic_heart);
                todoGroup.setFavorite(true);
            }
            mItemLists.set(position, todoGroup);
            notifyItemChanged(position, todoGroup);
            mRepo.update(todoGroup);

            AppStatus.getInstance().setSideMenuChange(true);
        }

        private void mmChildrenShowClick(int position, TodoGroup todoGroup) {
            if (todoGroup.isExpanded()) {
                todoGroup.setExpanded(false);
                invisibleChildren(position+1, position);
            } else {
                todoGroup.setExpanded(true);
                visibleChildren(position);
            }

            mItemLists.set(position, todoGroup);

            showAnimation(todoGroup.isExpanded());
        }

        private void showAnimation(boolean isExpanded) {
            mmChildrenShow.animate()
                    .rotation(isExpanded ? -180: 0).start();
        }

        private void visibleChildren(int position) {
            List<TodoGroup> children = mRepo.getChildren(mItemLists.get(position).getId());
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
            int position = getAdapterPosition();
            TodoGroup todoGroup = mItemLists.get(position);

            switch (v.getId()) {
                case R.id.todogroup_list_items_go_action:
                    mmGoActionClick(position, todoGroup);
                    break;
                case R.id.todogroup_list_items_delete:
                    mmDeleteClick(position, todoGroup);
                    break;
                case R.id.todogroup_list_items_favorite:
                    mmFavoriteClick(position, todoGroup);
                    break;
                case R.id.todogroup_list_items_children_show:
                    mmChildrenShowClick(position, todoGroup);
                    break;
            }
        }

    }

}
