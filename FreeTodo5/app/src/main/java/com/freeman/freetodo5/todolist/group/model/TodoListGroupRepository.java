package com.freeman.freetodo5.todolist.group.model;

import android.app.Application;

import com.freeman.freetodo5.todolist.group.model.async.GetTodoListGroupAsyncTask;
import com.freeman.freetodo5.todolist.group.model.async.GetTodoListGroupsAsyncTask;
import com.freeman.freetodo5.todolist.group.model.async.SetTodoListGroupAsyncTask;
import com.freeman.freetodo5.utils.db.AppDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class TodoListGroupRepository {

    public static final int SELECT_ALL = 1001;
    public static final int SELECT_ALL_WITHOUT_DELETE_ITEMS = 1002;
    public static final int SELECT_ID = 1003;
    public static final int SELECT_CHILDREN = 1004;
    public static final int SELECT_FAVORITE = 1005;
    public static final int INSERT_ID = 2001;    // with update
    public static final int INSERT_ARRAY = 2002;    // with update
    public static final int REMOVE_ALL = 3001;
    public static final int REMOVE_ID = 3002;
    public static final int REMOVE_ARRAY = 3003;

    private final TodoListGroupDao mDao;
    private TodoListGroup mDummyData;

    public TodoListGroupRepository(Application application) {
        AppDatabase database = AppDatabase.getInstance(application);
        mDao = database.todoListGroupDao();
        mDummyData = new TodoListGroup();
    }

    public TodoListGroup get(String id) {
        TodoListGroup result = new TodoListGroup();

        try {
            result = new GetTodoListGroupAsyncTask(mDao, SELECT_ID)
                    .execute(id).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return result;
    }

    public List<TodoListGroup> getAll() {
        return getAllTodoListGroup(SELECT_ALL_WITHOUT_DELETE_ITEMS);
    }
    public List<TodoListGroup> getAll(boolean withoutDeleteFlag) {
        if (withoutDeleteFlag) {
            return getAllTodoListGroup(SELECT_ALL_WITHOUT_DELETE_ITEMS);
        } else {
            return getAllTodoListGroup(SELECT_ALL);
        }
    }

    public List<TodoListGroup> getChildren(String parentId) {
        mDummyData = new TodoListGroup(parentId);
        return getAllTodoListGroup(SELECT_CHILDREN);
    }

    public List<TodoListGroup> getTree(String parentId) {
        List<TodoListGroup> results = new ArrayList<>();

        List<TodoListGroup> children = getChildren(parentId);
        for (TodoListGroup todoListGroup: children) {
            results.add(todoListGroup);
            if (todoListGroup.isChildren()) {
                results.addAll(getTree(todoListGroup.getId()));
            }
        }

        return results;
    }

    public List<TodoListGroup> getFavorites() {
        return getAllTodoListGroup(SELECT_FAVORITE);
    }

    private List<TodoListGroup> getAllTodoListGroup(int type) {
        List<TodoListGroup> results = new ArrayList<>();

        try {
            switch (type) {
                case SELECT_ALL:
                    results =
                            new GetTodoListGroupsAsyncTask(mDao, SELECT_ALL)
                                    .execute(mDummyData).get();
                    break;
                case SELECT_ALL_WITHOUT_DELETE_ITEMS:
                    results =
                            new GetTodoListGroupsAsyncTask(
                                    mDao, SELECT_ALL_WITHOUT_DELETE_ITEMS)
                                    .execute(mDummyData).get();
                    break;
                case SELECT_CHILDREN:
                    results =
                            new GetTodoListGroupsAsyncTask(mDao, SELECT_CHILDREN)
                                    .execute(mDummyData).get();
                    break;
                case SELECT_FAVORITE:
                    results =
                            new GetTodoListGroupsAsyncTask(mDao, SELECT_FAVORITE)
                                    .execute(mDummyData).get();
                    break;
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return results;
    }

    public void insert(TodoListGroup todoListGroup) {
        new SetTodoListGroupAsyncTask(mDao, INSERT_ID).execute(todoListGroup);
    }
    public void insert(List<TodoListGroup> todoListGroups) {
        new SetTodoListGroupAsyncTask(mDao, INSERT_ARRAY)
                .execute(todoListGroups.toArray(new TodoListGroup[0]));
    }

    public void update(TodoListGroup todoListGroup) {
        new SetTodoListGroupAsyncTask(mDao, INSERT_ID).execute(todoListGroup);
    }
    public void update(List<TodoListGroup> todoListGroups) {
        new SetTodoListGroupAsyncTask(mDao, INSERT_ARRAY)
                .execute(todoListGroups.toArray(new TodoListGroup[0]));
    }

    public void delete(String id) {
        delete(get(id));
    }
    public void delete(TodoListGroup todoListGroup) {
        todoListGroup.setDelete(true);
        update(todoListGroup);
    }
    public void delete(List<TodoListGroup> todoListGroups) {
        for (TodoListGroup todoListGroup: todoListGroups) todoListGroup.setDelete(true);
        update(todoListGroups);
    }

    public void removeAll() {
        new SetTodoListGroupAsyncTask(mDao, REMOVE_ALL).execute(mDummyData);
    }
    public void remove(TodoListGroup todoListGroup) {
        new SetTodoListGroupAsyncTask(mDao, REMOVE_ID).execute(todoListGroup);
    }
    public void remove(List<TodoListGroup> todoListGroups) {
        new SetTodoListGroupAsyncTask(mDao, REMOVE_ARRAY)
                .execute(todoListGroups.toArray(new TodoListGroup[0]));
    }
}
