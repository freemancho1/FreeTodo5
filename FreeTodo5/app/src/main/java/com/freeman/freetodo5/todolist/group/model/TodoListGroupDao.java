package com.freeman.freetodo5.todolist.group.model;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface TodoListGroupDao {

    @Query("SELECT * FROM tbl_todolistgroup ORDER BY id ASC")
    List<TodoListGroup> get();
    @Query("SELECT * FROM tbl_todolistgroup WHERE is_delete = 0 ORDER BY id ASC")
    List<TodoListGroup> getWithoutDeleteItems();

    @Query("SELECT * FROM tbl_todolistgroup WHERE id = :id")
    TodoListGroup get(String id);

    @Query("SELECT * FROM tbl_todolistgroup " +
            "WHERE is_delete = 0 AND parent_id = :parentId ORDER BY sequence ASC")
    List<TodoListGroup> getChildren(String parentId);

    @Query("SELECT * FROM tbl_todolistgroup WHERE is_delete = 0 AND is_favorite = 1")
    List<TodoListGroup> getFavorites();

    @Insert(onConflict = REPLACE)
    void insertOrUpdate(TodoListGroup todoListGroup);
    @Insert(onConflict = REPLACE)
    void insertOrUpdate(TodoListGroup... todoListGroups);

    @Delete
    void remove(TodoListGroup todoListGroup);
    @Delete
    void remove(TodoListGroup... todoListGroups);
    @Query("DELETE FROM tbl_todolistgroup")
    void remove();

}
