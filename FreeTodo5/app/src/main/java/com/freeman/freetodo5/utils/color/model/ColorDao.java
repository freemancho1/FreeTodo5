package com.freeman.freetodo5.utils.color.model;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface ColorDao {

    @Query("SELECT * FROM tbl_color")
    List<Color> getAll();
    @Query("SELECT * FROM tbl_color WHERE is_default = 1")
    List<Color> getDefault();

    @Query("SELECT * FROM tbl_color WHERE id = :id")
    Color getById(String id);

    @Insert(onConflict = REPLACE)
    void insertOrUpdate(Color color);
    @Insert(onConflict = REPLACE)
    void insertOrUpdate(Color... colors);

    @Delete
    void remove(Color color);
    @Delete
    void remove(Color... colors);
    @Query("DELETE FROM tbl_color")
    void removeAll();
}
