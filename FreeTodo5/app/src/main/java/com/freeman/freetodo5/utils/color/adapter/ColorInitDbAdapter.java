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
import com.freeman.freetodo5.utils.color.model.ColorRepository;

import java.util.List;

public class ColorInitDbAdapter extends RecyclerView.Adapter<ColorInitDbAdapter.ThisViewHolder> {
    private static final String LOG_TAG = ColorInitDbAdapter.class.getSimpleName();

    private final ColorRepository mRepo;
    private List<Color> mItemLists;

    private final Context mContext;
    private final LayoutInflater mInflater;

    public ColorInitDbAdapter(Context context, @NonNull ColorRepository repository) {
        mRepo = repository;
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public ThisViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(R.layout.init_database_color_items, viewGroup, false);
        return new ThisViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ThisViewHolder thisViewHolder, int i) {
        thisViewHolder.onBindData(mItemLists.get(i));
        Log.d(LOG_TAG, mItemLists.get(i).toString());
    }

    @Override
    public int getItemCount() {
        return mItemLists != null ? mItemLists.size(): 0;
    }

    public void setItemLists(List<Color> colors) {
        mItemLists = colors;
        notifyDataSetChanged();
    }

    public class ThisViewHolder extends RecyclerView.ViewHolder {
        private ImageView mmImage;
        private TextView mmName;

        public ThisViewHolder(@NonNull View itemView) {
            super(itemView);
            mmImage = itemView.findViewById(R.id.init_color_list_items_image);
            mmName = itemView.findViewById(R.id.init_color_list_items_text);
        }

        public void onBindData(final Color color) {
            mmImage.setColorFilter(color.getColor());
            mmName.setText(color.getName());
        }
    }
}
