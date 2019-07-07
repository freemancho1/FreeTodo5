package com.freeman.freetodo4.todolist.group.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.freeman.freetodo4.R;
import com.freeman.freetodo4.todolist.group.model.TodoListGroup;
import com.freeman.freetodo4.todolist.group.model.TodoListGroupRepository;

import java.util.List;

@SuppressLint("LongLogTag")
public class TodoListGroupMenuListAdapter
        extends RecyclerView.Adapter<TodoListGroupMenuListAdapter.ThisViewHolder> {

    private static final String LOG_TAG = "[TodoListGroupMenuListAdapter]";

    private static final int FAVORITE_CHECK_COLOR = 0xFFEE3333;
    private static final int FAVORITE_NONCHECK_COLOR = 0xFFDDDDDD;

    private final TodoListGroupRepository mRepo;
    private List<TodoListGroup> mItemLists;

    private final Context mContext;
    private final LayoutInflater mInflater;
    private final float mDensity;

    public TodoListGroupMenuListAdapter(
            Context context, @NonNull TodoListGroupRepository repository) {
        mRepo = repository;
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mDensity = context.getResources().getDisplayMetrics().density;
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
        private ImageView mmFavorite;

        public ThisViewHolder(@NonNull View itemView) {
            super(itemView);

            mmGoAction = itemView.findViewById(R.id.main_sidemenu_menu_items_go_action);
            mmColor = itemView.findViewById(R.id.main_sidemenu_menu_items_color);
            mmName = itemView.findViewById(R.id.main_sidemenu_menu_items_todogroup_name);
            mmCount = itemView.findViewById(R.id.main_sidemenu_menu_items_todolistitem_count);
            mmChildrenShow = itemView.findViewById(R.id.main_sidemenu_menu_items_children_show);
            mmFavorite = itemView.findViewById(R.id.main_sidemenu_menu_items_favorite);
        }

        public void onBindData(final TodoListGroup todoListGroup) {
            Log.d(LOG_TAG, "Binding data - " + todoListGroup.toString());
            Log.d(LOG_TAG, "item height - " + mmGoAction.getHeight());

            mmColor.setColorFilter(todoListGroup.getColor());
            ((ViewGroup.MarginLayoutParams) mmColor.getLayoutParams())
                    .leftMargin = (int) (todoListGroup.getDepth() * 25 * mDensity);

            mmName.setText(todoListGroup.getName());

            mmCount.setText("0");

            if (todoListGroup.isChildren()) {
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
                case R.id.main_sidemenu_menu_items_favorite:
                    mmFavoriteClick();
                    break;
            }
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

        private void mmFavoriteClick() {

        }

        private void visibleChildren(int position) {
            List<TodoListGroup> children = mRepo.getChildren(mItemLists.get(position).getId());
            mItemLists.addAll(position+1, children);
            notifyItemRangeInserted(position+1, children.size());
        }

        private int invisibleChildren(int startPosition, int thisPosition) {
            int invisibleSize = 0;
            int allItemSize = mItemLists.size();

            for (int i = startPosition; i < allItemSize; i++) {

                if (mItemLists.get(i).getParentId() == mItemLists.get(thisPosition).getId()) {

                    if (mItemLists.get(i).isExpanded()) {
                        allItemSize -= invisibleChildren(i+1, i);
                        mItemLists.get(i).setExpanded(false);
                    }

                    mItemLists.remove(i);
                    notifyItemRangeRemoved(i, 1);

                    invisibleSize++;
                    allItemSize--;
                    i--;
                }
            }

            return invisibleSize;
        }
    }
}
