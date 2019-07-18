package com.freeman.freetodo6.todo.group.view;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.freeman.freetodo6.R;
import com.freeman.freetodo6.todo.group.model.TodoGroup;
import com.freeman.freetodo6.todo.group.model.TodoGroupRepository;
import com.freeman.freetodo6.todo.group.view.adapter.ParentPickerAdapter;

public class DialogParentPicker {

    private final Context mContext;
    private final TodoGroupRepository mRepo;

    private boolean itemTouch = false;

    public DialogParentPicker(Context mContext, @NonNull TodoGroupRepository repository) {
        this.mContext = mContext;
        this.mRepo = repository;
    }

    public void getTodoGroup(final TextInputEditText parentName, final TextView parentId) {
        final Dialog dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.todogroup_parent_dialog);

        RecyclerView parentLayout = dialog.findViewById(R.id.todogroup_parent_list);
        parentLayout.setHasFixedSize(true);
        parentLayout.setLayoutManager(new LinearLayoutManager(mContext));
        final ParentPickerAdapter adapter = new ParentPickerAdapter(mContext);
        adapter.setItemLists(mRepo.getTree(""));
        parentLayout.setAdapter(adapter);

        dialog.show();

        parentLayout.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(
                    @NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP && itemTouch) {
                    View view = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());
                    int position = recyclerView.getChildAdapterPosition(view);
                    TodoGroup todoGroup = adapter.getTodoGroup(position);
                    parentName.setText(todoGroup.getName());
                    parentId.setText(todoGroup.getId());
                    dialog.dismiss();
                } else if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    itemTouch = true;
                }
                return false;
            }

            @Override
            public void onTouchEvent(
                    @NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) { }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean b) { }
        });

        parentLayout.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                itemTouch = false;
            }
        });
    }
}
