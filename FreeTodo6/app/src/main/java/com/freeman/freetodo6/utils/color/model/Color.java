package com.freeman.freetodo6.utils.color.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;

import com.freeman.freetodo6.utils.db.model.BaseModel;

@Entity(tableName = "tbl_colors")
public class Color extends BaseModel {

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
                "id='" + super.getId() + '\'' +
                ", color=" + color +
                ", defaultColor=" + defaultColor +
                ", name='" + name + '\'' +
                ", isDefault=" + isDefault +
                '}';
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
