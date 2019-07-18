package com.freeman.freetodo6.todo.group.model;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface TodoGroupDao {

    @Query("SELECT * FROM tbl_todogroups WHERE id = :id")
    TodoGroup getById(String id);

    @Query("SELECT * FROM tbl_todogroups WHERE is_delete = 0")
    List<TodoGroup> getAll();

    @Query("SELECT * FROM tbl_todogroups")
    List<TodoGroup> getAllWithDeleteItems();

    @Query("SELECT * FROM tbl_todogroups " +
            "WHERE is_delete = 0 AND parent_id = :parent_id ORDER BY sequence ASC")
    List<TodoGroup> getChildren(String parent_id);

    @Query("SELECT * FROM tbl_todogroups " +
            "WHERE is_delete = 0 AND parent_id = :parent_id ORDER BY sequence DESC LIMIT 1")
    TodoGroup getMaxSequence(String parent_id);

    @Query("SELECT * FROM tbl_todogroups WHERE is_delete = 0 AND is_favorite = 1")
    List<TodoGroup> getFavorite();

    @Insert(onConflict = REPLACE)
    void insertOrUpdate(TodoGroup todoGroup);
    @Insert(onConflict = REPLACE)
    void insertOrUpdate(TodoGroup... todoGroups);

    @Delete
    void remove(TodoGroup todoGroup);
    @Delete
    void remove(TodoGroup... todoGroups);

    @Query("DELETE FROM tbl_todogroups")
    void removeAll();

}
