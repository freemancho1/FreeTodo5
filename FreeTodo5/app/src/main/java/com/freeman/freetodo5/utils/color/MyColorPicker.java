package com.freeman.freetodo5.utils.color;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.freeman.freetodo5.R;

public class MyColorPicker extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.color_picker_activity);

        final AlertColorPicker picker = new AlertColorPicker(this);

        final ImageView imageView = findViewById(R.id.color_picker_image);
        final TextView textView = findViewById(R.id.color_picker_value);

        Button button = findViewById(R.id.color_picker_show);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                picker.colorPicker(imageView, textView);
            }
        });

    }
}
