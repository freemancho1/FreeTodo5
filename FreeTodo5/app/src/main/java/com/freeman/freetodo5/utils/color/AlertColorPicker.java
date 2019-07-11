package com.freeman.freetodo5.utils.color;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.freeman.freetodo5.R;
import com.larswerkman.holocolorpicker.ColorPicker;
import com.larswerkman.holocolorpicker.OpacityBar;
import com.larswerkman.holocolorpicker.SVBar;

import java.util.Locale;

public class AlertColorPicker implements ColorPicker.OnColorChangedListener {

    private Context mContext;

    public AlertColorPicker(Context context) {
        mContext = context;
    }

    public void colorPicker(final ImageView imageView, final TextView textView) {

        final Dialog dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.color_picker_dialog);

        final ColorPicker picker = dialog.findViewById(R.id.color_picker_picker);
        SVBar svBar = dialog.findViewById(R.id.color_picker_svbar);
        OpacityBar opacityBar = dialog.findViewById(R.id.color_picker_opacitybar);

        picker.addSVBar(svBar);
        picker.addOpacityBar(opacityBar);
        picker.setOnColorChangedListener(this);
        picker.setOldCenterColor(0xFF334455);
        picker.setColor(0xFF334455);

        Button okButton = dialog.findViewById(R.id.color_picker_ok_button);
        Button cancelButton = dialog.findViewById(R.id.color_picker_cancel_button);

        dialog.show();

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView.setColorFilter(picker.getColor());
                textView.setText(String.format(Locale.KOREA, "%8X", picker.getColor()));
                dialog.dismiss();
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

    }

    @Override
    public void onColorChanged(int color) {

    }

}
