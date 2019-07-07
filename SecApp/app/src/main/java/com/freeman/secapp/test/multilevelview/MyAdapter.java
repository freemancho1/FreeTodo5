package com.freeman.secapp.test.multilevelview;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.freeman.secapp.R;
import com.multilevelview.MultiLevelAdapter;
import com.multilevelview.MultiLevelRecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MyAdapter extends MultiLevelAdapter {

    private Context mContext;
    private Item mItem;
    private List<Item> mListItems = new ArrayList<>();
    private MultiLevelRecyclerView mMultiLevelRecyclerView;
    private MyHolder mMyHolder;

    public MyAdapter(Context context, List<Item> recyclerViewItems, MultiLevelRecyclerView multiLevelRecyclerView) {
        super(recyclerViewItems);
        mListItems = recyclerViewItems;
        mContext = context;
        mMultiLevelRecyclerView = multiLevelRecyclerView;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.multilevelview_item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        mMyHolder = (MyHolder) holder;
        mItem = mListItems.get(position);

        switch (getItemViewType(position)) {
            case 1:
                holder.itemView.setBackgroundColor(Color.parseColor("#efefef"));
                break;
            case 2:
                holder.itemView.setBackgroundColor(Color.parseColor("#dedede"));
                break;
            default:
                holder.itemView.setBackgroundColor(Color.parseColor("#ffffff"));
                break;
        }

        mMyHolder.mTitle.setText(mItem.getText());
        mMyHolder.mSubTitle.setText(mItem.getSecondText());

        if (mItem.hasChildren() && mItem.getChildren().size() > 0) {
            setExpandButton(mMyHolder.mExpandIcon, mItem.isExpanded());
            mMyHolder.mExpandButton.setVisibility(View.VISIBLE);
        } else {
            mMyHolder.mExpandButton.setVisibility(View.GONE);
        }

        float density = mContext.getResources().getDisplayMetrics().density;
        ((ViewGroup.MarginLayoutParams) mMyHolder.mTextBox.getLayoutParams()).leftMargin =
                (int) ((getItemViewType(position)*20)*density+0.5);
    }

    private void setExpandButton(ImageView expandButton, boolean isExpanded) {
        expandButton.setImageResource(
                isExpanded ? R.drawable.ic_symbol_arrow_down: R.drawable.ic_symbol_arrow_up);
    }


    private class MyHolder extends RecyclerView.ViewHolder {

        TextView mTitle, mSubTitle;
        ImageView mExpandIcon;
        LinearLayout mTextBox, mExpandButton;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            mTitle = itemView.findViewById(R.id.title);
            mSubTitle = itemView.findViewById(R.id.subtitle);
            mExpandIcon = itemView.findViewById(R.id.image_view);
            mTextBox = itemView.findViewById(R.id.text_box);
            mExpandButton = itemView.findViewById(R.id.expand_field);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(
                            mContext,
                            String.format(Locale.KOREA, "Item at position %d was clicked!",
                                    getAdapterPosition()), Toast.LENGTH_LONG)
                            .show();
                }
            });

            mExpandButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mMultiLevelRecyclerView.toggleItemsGroup(getAdapterPosition());
                    mExpandIcon.animate()
                            .rotation(mListItems.get(getAdapterPosition()).isExpanded() ? -180: 0)
                            .start();
                    Toast.makeText(
                            mContext,
                            String.format(Locale.KOREA, "Item at position %d is expanded: %s",
                                    getAdapterPosition(), mItem.isExpanded()),
                            Toast.LENGTH_SHORT)
                            .show();
                }
            });
        }
    }
}
