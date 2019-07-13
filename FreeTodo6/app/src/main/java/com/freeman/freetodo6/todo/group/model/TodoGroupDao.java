package com.freeman.freetodo6.todo.group.model;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.freeman.freetodo6.utils.db.dao.BaseDaoInterface;

import java.util.List;

@Dao
public interface TodoGroupDao extends BaseDaoInterface<TodoGroup> {

    @Query("SELECT * FROM tbl_todogroups WHERE id = :id")
    TodoGroup getById(String id);

    @Query("SELECT * FROM tbl_todogroups WHERE is_delete = 0")
    List<TodoGroup> getAll();

    @Query("SELECT * FROM tbl_todogroups")
    List<TodoGroup> getAllWithDeleteItems();

    @Query("SELECT * FROM tbl_todogroups " +
            "WHERE is_delete = 0 AND parent_id = :parent_id ORDER BY sequence ASC")
    List<TodoGroup> getChildren(String parent_id);

    @Query("SELECT * FROM tbl_todogroups WHERE is_delete = 0 AND is_favorite = 1")
    List<TodoGroup> getFavorite();

    @Query("DELETE FROM tbl_todogroups")
    void removeAll();

}
