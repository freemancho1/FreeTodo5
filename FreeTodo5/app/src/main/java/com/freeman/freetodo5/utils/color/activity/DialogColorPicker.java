package com.freeman.freetodo5.utils.color.activity;

import android.app.Application;
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

import com.freeman.freetodo5.R;
import com.freeman.freetodo5.utils.color.adapter.DialogColorPickerAdapter;
import com.freeman.freetodo5.utils.color.model.Color;
import com.freeman.freetodo5.utils.color.model.ColorRepository;

public class DialogColorPicker {

    private final Context mContext;
    private ColorRepository mColorRepo;

    public DialogColorPicker(Context context, ColorRepository repository) {
        mContext = context;
        mColorRepo = repository;
    }

    public void getColor(final Color color, final ImageView imageView, final TextView textView) {
        final Dialog dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.todo_list_group_add_color_dialog);

        RecyclerView colorLayout = dialog.findViewById(R.id.todo_list_group_add_color_list);
        final DialogColorPickerAdapter adapter = new DialogColorPickerAdapter(mContext);
        colorLayout.setHasFixedSize(true);
        colorLayout.setLayoutManager(new GridLayoutManager(mContext, 5));
        adapter.setItemLists(mColorRepo.getAll());
        colorLayout.setAdapter(adapter);

        dialog.show();

        colorLayout.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {
                View view = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());
                int position = recyclerView.getChildAdapterPosition(view);
                Color pickerColor = adapter.getItem(position);
                color.setId(pickerColor.getId());
                imageView.setColorFilter(pickerColor.getColor());
                textView.setText(pickerColor.getName());
                dialog.dismiss();
                return false;
            }

            @Override
            public void onTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean b) {

            }
        });
    }

}
