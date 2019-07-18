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

public class SideMenuMenuAdapter
        extends RecyclerView.Adapter<SideMenuMenuAdapter.ThisViewHolder> {

    private String LOG_TAG = SideMenuMenuAdapter.class.getCanonicalName();

    private final Context mContext;
    private final TodoGroupRepository mRepo;
    private List<TodoGroup> mItemLists;

    public SideMenuMenuAdapter(
            Context context, @NonNull TodoGroupRepository repository) {
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

        public void onBindData(final TodoGroup todoGroup) {
            Log.d(LOG_TAG, "Binding data - " + todoGroup.toString());

            if (todoGroup.isFavorite()) {
                mmColor.setImageResource(R.drawable.ic_heart);
            } else {
                mmColor.setImageResource(R.drawable.ic_circle);
            }

            float density = mContext.getResources().getDisplayMetrics().density;
            mmColor.setColorFilter(todoGroup.getColor());
            ((ViewGroup.MarginLayoutParams) mmColor.getLayoutParams())
                    .leftMargin = (int) (todoGroup.getDepth() * 25 * density);

            mmName.setText(todoGroup.getName());

            if (todoGroup.isChildren()) {
                mmChildrenShow.setVisibility(View.VISIBLE);
                mmCount.setVisibility(View.GONE);
            } else {
                mmChildrenShow.setVisibility(View.GONE);
                mmCount.setVisibility(View.VISIBLE);
                mmCount.setText("0");
            }

            mmFavorite.setVisibility(View.GONE);
            mmDelete.setVisibility(View.GONE);

            mmGoAction.setOnClickListener(this);
            mmChildrenShow.setOnClickListener(this);
        }

        private void mmGoActionClick() {

        }

        private void mmChildrenShowClick() {
            int position = getAdapterPosition();
            TodoGroup todoGroup = mItemLists.get(position);

            if (todoGroup.isExpanded()) {
                todoGroup.setExpanded(false);
                invisibleChildren(0, position);
            } else {
                todoGroup.setExpanded(true);
                visibleChildren(position);
            }

            mItemLists.set(position, todoGroup);

            mmChildrenShow.animate()
                    .rotation(todoGroup.isExpanded() ? -180: 0).start();
        }

        private void visibleChildren(int position) {
            List<TodoGroup> children =
                    mRepo.getChildren(mItemLists.get(position).getId());
            mItemLists.addAll(position+1, children);
            notifyItemRangeInserted(position+1, children.size());
        }

        private void invisibleChildren(int startPosition, int thisPosition) {
            int allItemSize = mItemLists.size();

            for (int i = startPosition; i < allItemSize; i++) {

                if (mItemLists.get(i).getParentId()
                        .equals(mItemLists.get(thisPosition).getId())) {

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
                case R.id.todogroup_list_items_go_action:
                    mmGoActionClick();
                    break;
                case R.id.todogroup_list_items_children_show:
                    mmChildrenShowClick();
                    break;
            }
        }
    }
}
