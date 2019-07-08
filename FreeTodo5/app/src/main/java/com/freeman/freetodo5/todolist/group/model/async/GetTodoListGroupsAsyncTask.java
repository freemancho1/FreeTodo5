package com.freeman.freetodo5.todolist.group.model.async;

import android.os.AsyncTask;

import com.freeman.freetodo5.todolist.group.model.TodoListGroup;
import com.freeman.freetodo5.todolist.group.model.TodoListGroupDao;
import com.freeman.freetodo5.todolist.group.model.TodoListGroupRepository;

import java.util.List;

public class GetTodoListGroupsAsyncTask extends AsyncTask<TodoListGroup, Void, List<TodoListGroup>> {
    private final TodoListGroupDao mDao;
    private final int mType;

    public GetTodoListGroupsAsyncTask(TodoListGroupDao todoListGroupDao, int type) {
        this.mDao = todoListGroupDao;
        this.mType = type;
    }

    @Override
    protected List<TodoListGroup> doInBackground(TodoListGroup... todoListGroups) {

        switch (mType) {
            case TodoListGroupRepository.SELECT_ALL:
                return mDao.get();
            case TodoListGroupRepository.SELECT_ALL_WITHOUT_DELETE_ITEMS:
                return mDao.getWithoutDeleteItems();
            case TodoListGroupRepository.SELECT_CHILDREN:
                return mDao.getChildren(todoListGroups[0].getParentId());
            case TodoListGroupRepository.SELECT_FAVORITE:
                return mDao.getFavorites();
        }

        return null;
    }
}
