package com.freeman.freetodo5.utils.color.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.UUID;

@Entity(tableName = "tbl_color")
public class Color {

    @PrimaryKey
    @NonNull
    private String          id = UUID.randomUUID().toString().replaceAll("-", "");

    private int             color;
    @ColumnInfo(name = "default_color")
    private int             defaultColor;

    private String          name;

    @ColumnInfo(name = "is_default")
    private int             isDefault = 0;


    public Color() {}
    public Color(int color, String name) {
        new Color(color, name, false);
    }
    public Color(int color, String name, boolean isDefault) {
        this.color = color;
        this.name = name;
        this.isDefault = isDefault ? 1: 0;
        this.defaultColor = isDefault ? color: 0;
    }

    @Override
    public String toString() {
        return "Color{" +
                "id='" + id + '\'' +
                ", color=" + color +
                ", defaultColor=" + defaultColor +
                ", name='" + name + '\'' +
                ", isDefault=" + isDefault +
                '}';
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getDefaultColor() {
        return defaultColor;
    }

    public void setDefaultColor(int defaultColor) {
        this.defaultColor = defaultColor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isDefault() {
        return isDefault == 1;
    }
    public int getIsDefault() {
        return isDefault;
    }

    public void setDefault(boolean isDefault) {
        this.isDefault = isDefault ? 1: 0;
    }
    public void setIsDefault(int isDefault) {
        this.isDefault = isDefault;
    }
}
