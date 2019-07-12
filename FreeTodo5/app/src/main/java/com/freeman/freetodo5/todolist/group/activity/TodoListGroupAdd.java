package com.freeman.freetodo5.todolist.group.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.freeman.freetodo5.R;
import com.freeman.freetodo5.utils.color.activity.DialogColorPicker;
import com.freeman.freetodo5.utils.color.model.Color;
import com.freeman.freetodo5.utils.color.model.ColorRepository;

import java.util.Locale;

public class TodoListGroupAdd extends AppCompatActivity {
    private static final String LOG_TAG = TodoListGroupAdd.class.getSimpleName();

    private ColorRepository mColorRepo;

    private ImageView mColorImage;
    private TextView mColorName;

    private Color mColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todo_list_group_add_activity);
        setTitle(R.string.todogroup_manager_title);

        mColorRepo = new ColorRepository(getApplication());
        mColor = mColorRepo.getRandomColor();

        mColorImage = findViewById(R.id.todo_list_group_add_color_image);
        mColorName = findViewById(R.id.todo_list_group_add_color_text);

        setColorPart();

        final DialogColorPicker picker = new DialogColorPicker(this, mColorRepo);
        mColorImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                picker.getColor(mColor, mColorImage, mColorName);
            }
        });

    }

    public void setColorPart() {
        mColorImage.setColorFilter(mColor.getColor());
        mColorName.setText(mColor.getName());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.todo_list_group_add_optionmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Toast.makeText(getApplicationContext(), "home", Toast.LENGTH_SHORT).show();
                break;
            case R.id.todo_list_group_optionmenu_add:
                mColor = mColorRepo.get(mColor.getId());
                Toast.makeText(getApplicationContext(), "add - "+mColor.toString(), Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
