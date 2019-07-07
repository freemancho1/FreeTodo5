package com.freeman.freetodo3.sample.recyclerview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.freeman.freetodo3.R;

import java.util.ArrayList;

public class MyRecyclerViewAdapter
        extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder> {
    private static final String LOG_TAG = "[MRVA]";

    private Context mContext;

    private ArrayList<MyDataItem> mItems = new ArrayList<>();

    private SparseBooleanArray mSelectedItems = new SparseBooleanArray();
    private int mPreviousPosition = -1;

    @NonNull
    @Override
    public MyRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        mContext = viewGroup.getContext();
        View view =
                LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.sample_recycler_item, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyRecyclerViewAdapter.MyViewHolder myViewHolder, int i) {
        myViewHolder.onBind(mItems.get(i), i);
    }

    @Override
    public int getItemCount() {
        return mItems != null ? mItems.size(): 0;
    }

    public void setItems(ArrayList<MyDataItem> items) {
        mItems = items;
        notifyDataSetChanged();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private LinearLayout mmItem;
        private TextView mmTitle;
        private TextView mmContent;
        private ImageView mmImage;
        private ImageView mmImageDetail;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            mmItem = itemView.findViewById(R.id.sample_recycler_item);
            mmTitle = itemView.findViewById(R.id.sample_recycler_item_title);
            mmContent = itemView.findViewById(R.id.sample_recycler_item_contents);
            mmImage = itemView.findViewById(R.id.sample_recycler_item_image);
            mmImageDetail = itemView.findViewById(R.id.sample_recycler_item_image_detail);

        }

        public void onBind(MyDataItem item, int position) {

            mmTitle.setText(item.getTitle());
            mmContent.setText(item.getContents());
            mmImage.setImageResource(item.getResId());
            mmImageDetail.setImageResource(item.getResId());

            changeLevelView(mSelectedItems.get(position));

            // Need to implements View.OnClickListener
            mmTitle.setOnClickListener(this);
            mmContent.setOnClickListener(this);
            mmImage.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.sample_recycler_item_title:
                    mmTitleOnClick();
                    break;
                case R.id.sample_recycler_item_contents:
                    mmContentOnClick();
                    break;
                case R.id.sample_recycler_item_image:
                    mmImageOnClick();
                    break;
            }
        }

        private void mmTitleOnClick() {
            Toast.makeText(
                    mContext,
                    "Title: "+ mItems.get(getAdapterPosition()).getTitle(),
                    Toast.LENGTH_SHORT)
                    .show();
        }

        private void mmContentOnClick() {

        }

        private void mmImageOnClick() {
            int position = getAdapterPosition();

            if (mSelectedItems.get(position)) {
                mSelectedItems.delete(position);
            } else {
                mSelectedItems.delete(mPreviousPosition);
                mSelectedItems.put(position, true);
            }

//            if (mPreviousPosition != -1) notifyItemChanged(mPreviousPosition);
            notifyItemChanged(position);
//            mPreviousPosition = position;
        }

        private void changeLevelView(final boolean isExpanded) {
            int layoutImageHeight = 200;
            float dp = mContext.getResources().getDisplayMetrics().density;
            int imageHeight = (int) (layoutImageHeight * dp);

            ValueAnimator valueAnimator =
                    isExpanded ?
                            ValueAnimator.ofInt(0, imageHeight):
                            ValueAnimator.ofInt(imageHeight, 0);
            valueAnimator.setDuration(300);
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    int value = (int) animation.getAnimatedValue();
                    mmImageDetail.getLayoutParams().height = value;
                    mmImageDetail.requestLayout();
                    mmImageDetail.setVisibility(isExpanded? View.VISIBLE: View.GONE);
                }
            });

            valueAnimator.start();
        }
    }
}