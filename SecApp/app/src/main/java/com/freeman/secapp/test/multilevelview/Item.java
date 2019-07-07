package com.freeman.secapp.test.multilevelview;

import com.multilevelview.models.RecyclerViewItem;

public class Item extends RecyclerViewItem {
    String text;
    String secondText;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSecondText() {
        return secondText;
    }

    public void setSecondText(String secondText) {
        this.secondText = secondText;
    }

    protected Item(int level) {
        super(level);
    }
}
