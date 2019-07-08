package com.freeman.freetodo5.todolist.group.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.UUID;

@Entity(tableName = "tbl_todolistgroup")
public class TodoListGroup {

    @PrimaryKey
    @NonNull
    private String          id = UUID.randomUUID().toString().replaceAll("-", "");

    private String          name;
    private String          memo;
    private int             color;
    @ColumnInfo(name = "parent_id")
    private String          parentId = "";
    private int             depth = 0;
    private int             sequence =0;

    @ColumnInfo(name = "is_children")
    private int             isChildren = 0;
    @ColumnInfo(name = "is_favorite")
    private int             isFavorite = 0;
    @ColumnInfo(name = "is_delete")
    private int             isDelete = 0;

    @Ignore
    private int             isExpanded = 0;

    public TodoListGroup() {}

    public TodoListGroup(String parentId) {
        this.parentId = parentId;
    }
    public TodoListGroup(
            String name, String memo, int color,
            String parentId, int depth, int sequence, int isFavorite) {
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
        return isChildren == 1;
    }
    public int getIsChildren() {
        return isChildren;
    }

    public void setChildren(boolean children) {
        isChildren = children ? 1: 0;
    }
    public void setIsChildren(int isChildren) {
        this.isChildren = isChildren;
    }

    public boolean isFavorite() {
        return isFavorite == 1;
    }
    public int getIsFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite ? 1: 0;
    }
    public void setIsFavorite(int isFavorite) {
        this.isFavorite = isFavorite;
    }

    public boolean isDelete() {
        return isDelete == 1;
    }
    public int getIsDelete() {
        return isDelete;
    }

    public void setDelete(boolean delete) {
        isDelete = delete ? 1: 0;
    }
    public void setIsDelete(int isDelete) {
        this.isDelete = isDelete;
    }

    public boolean isExpanded() {
        return isExpanded == 1;
    }
    public int getIsExpanded() {
        return isExpanded;
    }

    public void setExpanded(boolean expanded) {
        isExpanded = expanded ? 1: 0;
    }
    public void setIsExpanded(int isExpanded) {
        this.isExpanded = isExpanded;
    }
}
