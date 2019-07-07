package com.freeman.secapp.utils.multilevelview;

import java.util.List;

public abstract class ViewItem {
    private int         level;
    private int         position;
    private boolean     expanded = false;

    private List<ViewItem> children;

    public ViewItem(int level) {
        this.level = level;
    }

    public int getLevel() {
        return this.level;
    }
    public void setLevel(int level) {
        this.level = level;
    }

    public int getPosition() {
        return this.position;
    }
    public void setPosition(int position) {
        this.position = position;
    }

    public boolean getExpanded() {
        return this.expanded;
    }
    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    public List<ViewItem> getChildren() {
        return this.children;
    }
    public void setChildren(List<ViewItem> children) {
        this.children = children;
    }

    public boolean hasChildren() {
        return this.children != null && this.children.size() > 0;
    }
}
