package com.freeman.secapp.utils.multilevelview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Adapter;

import java.util.List;

public class MultiLevelView extends RecyclerView implements OnMultiLevelViewItemClickListener {

    private Context mContext;

    private boolean isExpanded = false;
    private boolean isAccordion = false;
    private boolean isItemOnClick = true;

    private int prevPosition = -1;
    private int addItems = 0;

    private MultiLevelViewAdapter mAdapter;

    private OnMultiLevelViewItemTouchListener mTouchListener;
    private OnMultiLevelViewItemClickListener mClickListener;

    public MultiLevelView(@NonNull Context context) {
        super(context);
        mContext = context;
        setUpMultiLevelView();
    }
    public MultiLevelView(@NonNull Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        setUpMultiLevelView();
    }
    public MultiLevelView(@NonNull Context context, AttributeSet attributeSet, int defStyle) {
        super(context, attributeSet, defStyle);
        setUpMultiLevelView();
    }

    @Override
    public void onItemClick(View view, ViewItem item, int position) {

    }

    private void setUpMultiLevelView() {
        mTouchListener = new OnMultiLevelViewItemTouchListener(mContext);
        mTouchListener.setClickListener(this);
        addOnItemTouchListener(mTouchListener);
        setItemAnimator(new DefaultItemAnimator());
    }

    public void removeAllChildren(List<ViewItem> items) {
        for (ViewItem item: items) {
            if (item.getExpanded()) {
                item.setExpanded(false);
                removeAllChildren(item.getChildren());
                removePrevItems(mAdapter.getItemList(), item.getPosition(), item.getChildren().size());
            }
        }
    }

    private void removePrevItems(List<ViewItem> items, int position, int addItems) {
        for (int i=0; i < addItems; i++) {
            items.remove(position+1);
        }
        isExpanded = false;
        mAdapter.setItemList(items);
        mAdapter.notifyItemRangeRemoved(position+1, addItems);
        refreshPosition();
    }

    private void refreshPosition() {
        int position = 0;
        for (ViewItem item: mAdapter.getItemList()) {
            item.setPosition(position++);
        }
    }

    public void setItemOnClick(boolean toggleItemOnClick) {
        isItemOnClick = toggleItemOnClick;
    }

    public void setClickListener(OnMultiLevelViewItemClickListener listener) {
        mClickListener = listener;
    }
    public void removeClickListener() {
        if (mTouchListener != null) removeOnItemTouchListener(mTouchListener);
    }

    public void setAccordion(boolean accordion) {
        isAccordion = accordion;
    }

    @Override
    public void setItemAnimator(ItemAnimator animator) {
        super.setItemAnimator(animator);
    }

    @Override
    public void setAdapter(Adapter adapter) {
        mAdapter = (MultiLevelViewAdapter) adapter;
        super.setAdapter(adapter);
    }

    private final class OnMultiLevelViewItemTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector mDetector;
        private OnMultiLevelViewItemClickListener mmClickListener;

        public OnMultiLevelViewItemTouchListener(Context context) {
            mDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent motionEvent) {
                    return true;
                }
            });
        }

        public void setClickListener(OnMultiLevelViewItemClickListener listener) {
            mmClickListener = listener;
        }

        @Override
        public boolean onInterceptTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {
            View view = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());

            if (view != null && mDetector.onTouchEvent(motionEvent)) {
                view.performClick();
                int position = recyclerView.getChildAdapterPosition(view);

                if (mmClickListener != null) {
                    mmClickListener.onItemClick(view, mAdapter.getItemList().get(position), position);
                }

                return isItemOnClick;
            }
            return false;
        }

        @Override
        public void onTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) { }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean b) { }
    }
}
