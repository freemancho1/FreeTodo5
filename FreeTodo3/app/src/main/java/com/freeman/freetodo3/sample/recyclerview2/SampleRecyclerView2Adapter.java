package com.freeman.freetodo3.sample.recyclerview2;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.freeman.freetodo3.R;
import com.freeman.freetodo3.todo.group.model.TodoGroup;
import com.freeman.freetodo3.todo.group.model.TodoGroupRepository;

import java.util.List;

public class SampleRecyclerView2Adapter
        extends RecyclerView.Adapter<SampleRecyclerView2Adapter.MyViewHolder> {
    private static final String LOG_TAG = "[SRV2A]";

    private static final int FAVORITE_CHECK_COLOR = 0xFFEE3333;
    private static final int FAVORITE_NONCHECK_COLOR = 0xFFDDDDDD;

    private final TodoGroupRepository mTodoGroupRepo;
    private List<TodoGroup> mItemList;

    private final LayoutInflater mInflater;
    private final float mDensity;

    public SampleRecyclerView2Adapter(
            Context context, @NonNull TodoGroupRepository repository) {
        mInflater = LayoutInflater.from(context);
        mDensity = context.getResources().getDisplayMetrics().density;
        mTodoGroupRepo = repository;
        mItemList = repository.getChildren(0L);
    }

    @NonNull
    @Override
    public SampleRecyclerView2Adapter.MyViewHolder
            onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(R.layout.sample_recycler_item2, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull SampleRecyclerView2Adapter.MyViewHolder myViewHolder, int position) {
        myViewHolder.onBindData(mItemList.get(position), position);
    }


    @Override
    public int getItemCount() {
        return mItemList != null ? mItemList.size(): 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView mmColor;
        private TextView mmName;
        private ImageView mmChildrenShow;
        private ImageView mmFavorite;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mmColor = itemView.findViewById(R.id.sample_recycler_item2_color);
            mmName = itemView.findViewById(R.id.sample_recycler_item2_todogroup_name);
            mmChildrenShow = itemView.findViewById(R.id.sample_recycler_item2_children_show);
            mmFavorite = itemView.findViewById(R.id.sample_recycler_item2_favorite);
        }

        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.sample_recycler_item2_children_show:
                    mmChildrenShowOnClick();
                    break;
            }

        }

        private void mmChildrenShowOnClick() {
            int position = getAdapterPosition();
            TodoGroup todoGroup = mItemList.get(position);

            if (todoGroup.isExpanded()) {
                todoGroup.setExpanded(false);
                removeChildren(0, position);
            } else {
                addChildren(position);
                todoGroup.setExpanded(true);
            }

            mItemList.set(position, todoGroup);

            mmChildrenShow.animate()
                    .rotation(todoGroup.isExpanded() ? -180: 0).start();
        }

        private void addChildren(int position) {
            List<TodoGroup> children = mTodoGroupRepo.getChildren(mItemList.get(position).getId());
            mItemList.addAll(position+1, children);
            notifyItemRangeInserted(position+1, children.size());
        }

        private int removeChildren(int startPosition, int thisPosition) {
            int removeSize = 0;
            int allItemSize = mItemList.size();

            for (int i = startPosition; i < allItemSize; i++) {

                if (mItemList.get(i).getParentId() == mItemList.get(thisPosition).getId()) {

                    if (mItemList.get(i).isExpanded()) {
                        allItemSize -= removeChildren(i+1, i);
                        mItemList.get(i).setExpanded(false);
                    }

                    mItemList.remove(i);
                    notifyItemRangeRemoved(i, 1);
                    removeSize++;
                    allItemSize--;
                    i--;
                }
            }

            return removeSize;
        }

        public void onBindData(TodoGroup todoGroup, int position) {

            Log.d(LOG_TAG, "Binding - " + todoGroup.toString());

            ((ViewGroup.MarginLayoutParams) mmColor.getLayoutParams())
                    .leftMargin = (int) (todoGroup.getDepth() * 25 * mDensity);
            mmColor.setColorFilter(todoGroup.getColor());

            mmName.setText(todoGroup.getName());

            if (todoGroup.isChildren()) {
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

            mmChildrenShow.setOnClickListener(this);

        }

    }
}
