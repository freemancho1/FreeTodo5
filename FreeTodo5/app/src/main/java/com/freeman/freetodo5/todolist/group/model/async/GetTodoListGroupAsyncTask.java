package com.freeman.freetodo5.todolist.group.model.async;

import android.os.AsyncTask;

import com.freeman.freetodo5.todolist.group.model.TodoListGroup;
import com.freeman.freetodo5.todolist.group.model.TodoListGroupDao;
import com.freeman.freetodo5.todolist.group.model.TodoListGroupRepository;

public class GetTodoListGroupAsyncTask extends AsyncTask<String, Void, TodoListGroup> {
    private final TodoListGroupDao mDao;
    private final int mType;

    public GetTodoListGroupAsyncTask(TodoListGroupDao todoListGroupDao, int type) {
        this.mDao = todoListGroupDao;
        this.mType = type;
    }

    @Override
    protected TodoListGroup doInBackground(String... ids) {

        switch (mType) {
            case TodoListGroupRepository.SELECT_ID:
                return mDao.get(ids[0]);
        }

        return null;
    }
}
