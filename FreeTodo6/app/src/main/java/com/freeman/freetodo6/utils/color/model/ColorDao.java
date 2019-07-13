package com.freeman.freetodo6.utils.color.model;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.freeman.freetodo6.utils.db.dao.BaseDaoInterface;

import java.util.List;

@Dao
public interface ColorDao extends BaseDaoInterface<Color> {

    @Query("SELECT * FROM tbl_colors WHERE id = :id")
    Color getById(String id);

    @Query("SELECT * FROM tbl_colors")
    List<Color> getAll();

    @Query("SELECT * FROM tbl_colors WHERE is_default = 1")
    List<Color> getDefaultColor();

    @Query("DELETE FROM tbl_colors")
    void removeAll();

}
