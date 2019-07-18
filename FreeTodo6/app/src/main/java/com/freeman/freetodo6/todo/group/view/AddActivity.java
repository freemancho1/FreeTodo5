package com.freeman.freetodo6.todo.group.view;

import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.freeman.freetodo6.R;
import com.freeman.freetodo6.todo.group.model.TodoGroup;
import com.freeman.freetodo6.todo.group.model.TodoGroupRepository;
import com.freeman.freetodo6.utils.AppStatus;
import com.freeman.freetodo6.utils.color.model.Color;
import com.freeman.freetodo6.utils.color.model.ColorRepository;
import com.freeman.freetodo6.utils.color.view.DialogColorPicker;

public class AddActivity extends AppCompatActivity {
    private static final String LOG_TAG = AddActivity.class.getSimpleName();

    private TodoGroupRepository mTodoGroupRepo;
    private ColorRepository mColorRepo;

    private TextInputLayout mNameLayout;
    private TextInputEditText mName;
    private TextInputEditText mMemo;
    private CheckBox mFavorite;
    private TextInputEditText mParentName;
    private ImageView mColorImage;
    private TextView mColorText;
    private Color mSaveColor;
    private TextView mParentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todogroup_add_activity);
        setTitle(R.string.todogroup_manager_title);

        mTodoGroupRepo = new TodoGroupRepository(getApplication());
        mColorRepo = new ColorRepository(getApplication());
        mSaveColor = mColorRepo.getRandomColor();

        mNameLayout = findViewById(R.id.todogroup_add_name_layout);
        mName = findViewById(R.id.todogroup_add_name);
        mMemo = findViewById(R.id.todogroup_add_memo);
        mFavorite = findViewById(R.id.todogroup_add_favorite);
        mParentName = findViewById(R.id.todogroup_add_parent_name);
        mColorImage = findViewById(R.id.todogroup_add_color_image);
        mColorText = findViewById(R.id.todogroup_add_color_text);
        mParentId = findViewById(R.id.todogroup_add_parent_id);

        mColorImage.setColorFilter(mSaveColor.getColor());
        mColorText.setText(mSaveColor.getName());

        final DialogColorPicker pickerColor = new DialogColorPicker(this, mColorRepo);
        mColorImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickerColor.getColor(mSaveColor, mColorImage, mColorText);
            }
        });

        final DialogParentPicker pickerParent = new DialogParentPicker(this, mTodoGroupRepo);
        mParentName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickerParent.getTodoGroup(mParentName, mParentId);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_optionmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Toast.makeText(getApplicationContext(), "home", Toast.LENGTH_SHORT).show();
                break;
            case R.id.main_optionmenu_add:
                addTodoGroup();
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void addTodoGroup() {

        String newName = mName.getText().toString();
        if (newName.isEmpty()) {
            mNameLayout.setError(getString(R.string.sys_required));
            return;
        }

        String newMemo = mMemo.getText().toString();

        int newColor = mColorRepo.get(mSaveColor.getId()).getColor();

        String newParentId = mParentId.getText().toString();
        int newDepth = 0;
        int newSequence = mTodoGroupRepo.getMaxSequence(newParentId).getSequence()+1;
        if (!newParentId.isEmpty()) {
            newDepth = mTodoGroupRepo.get(newParentId).getDepth()+1;
        }

        int newFavorite = mFavorite.isChecked() ? 1: 0;

        TodoGroup todoGroup = new TodoGroup(
                newName, newMemo, newColor, newParentId, newDepth, newSequence, newFavorite);

        mTodoGroupRepo.insert(todoGroup);

        Toast.makeText(getApplicationContext(), "add - "+newName, Toast.LENGTH_SHORT).show();

        AppStatus.getInstance().setSideMenuChange(true);
    }
}
