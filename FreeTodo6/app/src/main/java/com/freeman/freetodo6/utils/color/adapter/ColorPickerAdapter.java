package com.freeman.freetodo6.utils.color.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.freeman.freetodo6.R;
import com.freeman.freetodo6.utils.color.model.Color;

import java.util.List;

public class ColorPickerAdapter extends RecyclerView.Adapter<ColorPickerAdapter.ThisViewHolder> {
    private static final String LOG_TAG = ColorPickerAdapter.class.getSimpleName();

    private final Context mContext;

    private List<Color> mItemLists;

    public ColorPickerAdapter(Context context) {
        this.mContext = context;
    }

    @NonNull
    @Override
    public ThisViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.main_color_dialog_items, viewGroup, false);
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

    public void setItemLists(List<Color> colors) {
        mItemLists = colors;
        notifyDataSetChanged();
    }

    public Color getColor(int position) {
        return mItemLists.get(position);
    }


    public class ThisViewHolder extends RecyclerView.ViewHolder {

        private ImageView mmImage;
        private TextView mmText;

        public ThisViewHolder(@NonNull View itemView) {
            super(itemView);
            mmImage = itemView.findViewById(R.id.main_color_dialog_color_items_image);
            mmText = itemView.findViewById(R.id.main_color_dialog_color_items_text);
        }

        private void onBindData(final Color color) {
            mmImage.setColorFilter(color.getColor());
            mmText.setText(color.getName());
        }
    }
}
