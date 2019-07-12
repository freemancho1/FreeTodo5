package com.freeman.freetodo5.utils.color.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.freeman.freetodo5.R;
import com.freeman.freetodo5.utils.color.model.Color;

import java.util.List;

public class DialogColorPickerAdapter
        extends RecyclerView.Adapter<DialogColorPickerAdapter.ThisViewHolder> {
    private static final String LOG_TAG = DialogColorPickerAdapter.class.getSimpleName();

    private List<Color> mItemLists;

    private final Context mContext;
    private final LayoutInflater mInflater;

    public DialogColorPickerAdapter(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public ThisViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(R.layout.todo_list_group_add_color_dialog_items, viewGroup, false);
        return new ThisViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ThisViewHolder thisViewHolder, int i) {
        Log.d(LOG_TAG, mItemLists.get(i).toString());
        thisViewHolder.onBindData(mItemLists.get(i));
    }

    @Override
    public int getItemCount() {
        return mItemLists != null ? mItemLists.size(): 0;
    }

    public void setItemLists(List<Color> colors) {
        mItemLists = colors;
        notifyDataSetChanged();
    }

    public Color getItem(int position) {
        return mItemLists.get(position);
    }

    public class ThisViewHolder extends RecyclerView.ViewHolder {
        private ImageView mmImage;
        private TextView mmName;

        public ThisViewHolder(@NonNull View itemView) {
            super(itemView);
            mmImage = itemView.findViewById(R.id.todo_list_group_add_color_list_items_image);
            mmName = itemView.findViewById(R.id.todo_list_group_add_color_list_items_text);
        }

        public void onBindData(final Color color) {
            mmImage.setColorFilter(color.getColor());
            mmName.setText(color.getName());
        }
    }
}
