package com.freeman.freetodo6.utils.color.view;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.freeman.freetodo6.R;
import com.freeman.freetodo6.utils.color.adapter.ColorPickerAdapter;
import com.freeman.freetodo6.utils.color.model.Color;
import com.freeman.freetodo6.utils.color.model.ColorRepository;

public class DialogColorPicker {

    private final Context mContext;
    private final ColorRepository mColorRepo;

    public DialogColorPicker(Context mContext, ColorRepository mColorRepo) {
        this.mContext = mContext;
        this.mColorRepo = mColorRepo;
    }

    public void getColor(final Color color, final ImageView imageView, final TextView textView) {
        final Dialog dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.main_color_dialog);

        RecyclerView colorLayout = dialog.findViewById(R.id.main_color_dialog_color_list);
        colorLayout.setHasFixedSize(true);
        colorLayout.setLayoutManager(new GridLayoutManager(mContext, 5));
        final ColorPickerAdapter adapter = new ColorPickerAdapter(mContext);
        adapter.setItemLists(mColorRepo.getAll());
        colorLayout.setAdapter(adapter);

        dialog.show();

        colorLayout.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(
                    @NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {
                View view = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());
                int position = recyclerView.getChildAdapterPosition(view);
                Color pickerColor = adapter.getColor(position);
                color.setId(pickerColor.getId());
                imageView.setColorFilter(pickerColor.getColor());
                textView.setText(pickerColor.getName());
                dialog.dismiss();
                return false;
            }

            @Override
            public void onTouchEvent(
                    @NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) { }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean b) { }
        });

    }
}
