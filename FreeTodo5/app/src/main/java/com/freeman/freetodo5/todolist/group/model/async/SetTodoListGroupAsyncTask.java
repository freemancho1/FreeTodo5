package com.freeman.freetodo5.todolist.group.model.async;

import android.os.AsyncTask;

import com.freeman.freetodo5.todolist.group.model.TodoListGroup;
import com.freeman.freetodo5.todolist.group.model.TodoListGroupDao;
import com.freeman.freetodo5.todolist.group.model.TodoListGroupRepository;

public class SetTodoListGroupAsyncTask extends AsyncTask<TodoListGroup, Void, Void> {
    private final TodoListGroupDao mDao;
    private final int mType;

    public SetTodoListGroupAsyncTask(TodoListGroupDao todoListGroupDao, int type) {
        this.mDao = todoListGroupDao;
        this.mType = type;
    }

    @Override
    protected Void doInBackground(TodoListGroup... todoListGroups) {

        switch (mType) {
            case TodoListGroupRepository.INSERT_ID:
                mDao.insertOrUpdate(todoListGroups[0]);
                break;
            case TodoListGroupRepository.INSERT_ARRAY:
                mDao.insertOrUpdate(todoListGroups);
                break;
            case TodoListGroupRepository.REMOVE_ALL:
                mDao.remove();
                break;
            case TodoListGroupRepository.REMOVE_ID:
                mDao.remove(todoListGroups[0]);
                break;
            case TodoListGroupRepository.REMOVE_ARRAY:
                mDao.remove(todoListGroups);
                break;
        }

        return null;
    }
}
