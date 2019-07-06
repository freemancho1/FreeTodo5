package com.freeman.freetodo5.todolist.group.model;

import java.util.UUID;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class TodoListGroup extends RealmObject {

    @PrimaryKey
    @Required
    private String          id = UUID.randomUUID().toString().replaceAll("-", "");

    @Required
    private String          name;

    private String          memo;
    private int             color;

    private String          parentId = "";
    private int             depth = 0;
    private int             sequence =0;

    private boolean         isChildren = false;
    private boolean         isFavorite = false;
    private boolean         isDelete = false;

    @Ignore
    private boolean         isExpanded = false;

    public TodoListGroup() {}
    public TodoListGroup(
            String name, String memo, int color,
            String parentId, int depth, int sequence, boolean isFavorite) {
        this.name = name;
        this.memo = memo;
        this.color = color;
        this.parentId = parentId;
        this.depth = depth;
        this.sequence = sequence;
        this.isFavorite = isFavorite;
    }

    @Override
    public String toString() {
        return "TodoListGroup{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", memo='" + memo + '\'' +
                ", color=" + color +
                ", parentId='" + parentId + '\'' +
                ", depth=" + depth +
                ", sequence=" + sequence +
                ", isChildren=" + isChildren +
                ", isFavorite=" + isFavorite +
                ", isDelete=" + isDelete +
                ", isExpanded=" + isExpanded +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public boolean isChildren() {
        return isChildren;
    }

    public void setChildren(boolean children) {
        isChildren = children;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public boolean isDelete() {
        return isDelete;
    }

    public void setDelete(boolean delete) {
        isDelete = delete;
    }

    public boolean isExpanded() {
        return isExpanded;
    }

    public void setExpanded(boolean expanded) {
        isExpanded = expanded;
    }
}
