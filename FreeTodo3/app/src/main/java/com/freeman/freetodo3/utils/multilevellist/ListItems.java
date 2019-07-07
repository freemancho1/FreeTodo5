package com.freeman.freetodo3.utils.multilevellist;

import java.util.List;

public abstract class ListItems {

    private int level;
    private boolean isExpanded = false;

    private List<ListItems> children;

    public ListItems(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public boolean isExpanded() {
        return isExpanded;
    }

    public void setExpanded(boolean expanded) {
        isExpanded = expanded;
    }

    public List<ListItems> getChildren() {
        return children;
    }

    public void setChildren(List<ListItems> children) {
        this.children = children;
    }

    public boolean isChildren() {
        return children != null && children.size() > 0;
    }
}
