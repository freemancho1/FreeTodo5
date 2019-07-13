package com.freeman.freetodo6.todo.group.model;

import android.app.Application;

import com.freeman.freetodo6.utils.db.AppDatabase;
import com.freeman.freetodo6.utils.db.repository.BaseRepository;

import java.util.ArrayList;
import java.util.List;

public class TodoGroupRepository extends BaseRepository<TodoGroupDao, TodoGroup> {
    private static final String LOG_TAG = TodoGroupRepository.class.getSimpleName();

    private final TodoGroupDao mDao;

    public TodoGroupRepository(Application application) {
        AppDatabase database = AppDatabase.getInstance(application);
        mDao = database.todoListGroupDao();
    }

    public TodoGroup get(String id) {
        return getOne(mDao, SELECT_ID, id);
    }

    public List<TodoGroup> getAll() { return getAll(false); }
    public List<TodoGroup> getAll(boolean withDeleteItems) {
        if (withDeleteItems) {
            return getList(mDao, SELECT_ALL_WITH_DELETE_ITEMS, "");
        } else {
            return getList(mDao, SELECT_ALL, "");
        }
    }

    public List<TodoGroup> getChildren(String parentId) {
        return getList(mDao, SELECT_CHILDREN, parentId);
    }

    public List<TodoGroup> getTree(String parentId) {
        List<TodoGroup> results = new ArrayList<>();
        List<TodoGroup> children = getChildren(parentId);
        for (TodoGroup todoGroup: children) {
            results.add(todoGroup);
            if (todoGroup.isChildren()) {
                results.addAll(getTree(todoGroup.getId()));
            }
        }
        return results;
    }

    public List<TodoGroup> getFavorites() {
        return getList(mDao, SELECT_FAVORITE, "");
    }

    public void insert(TodoGroup todoGroup) {
        insertOrUpdate(mDao, INSERT_ID, todoGroup);
    }
    public void insert(List<TodoGroup> todoGroups) {
        insertOrUpdate(mDao, INSERT_ARRAY, todoGroups.toArray(new TodoGroup[0]));
    }

    public void update(TodoGroup todoGroup) {
        insert(todoGroup);
    }
    public void update(List<TodoGroup> todoGroups) {
        insert(todoGroups);
    }

    public void delete(TodoGroup todoGroup) {
        todoGroup.setDelete(true);
        insert(todoGroup);
    }
    public void delete(List<TodoGroup> todoGroups) {
        for (int i=0; i < todoGroups.size(); i++) todoGroups.get(i).setDelete(true);
        insert(todoGroups);
    }

    public void remove(TodoGroup todoGroup) {
        insertOrUpdate(mDao, REMOVE_ID, todoGroup);
    }
    public void remove(List<TodoGroup> todoGroups) {
        insertOrUpdate(mDao, REMOVE_ARRAY, todoGroups.toArray(new TodoGroup[0]));
    }
    public void removeAll() {
        super.removeAll(mDao, REMOVE_ALL);
    }


}
