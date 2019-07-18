package com.freeman.freetodo6.todo.group.model;

import android.os.AsyncTask;

import com.freeman.freetodo6.utils.db.BaseAsyncTask;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class TodoGroupAsyncTask extends BaseAsyncTask {

    private TodoGroupDao mDao;

    protected void setDao(TodoGroupDao dao) {
        this.mDao = dao;
    }

    protected TodoGroup getOne(int type, String param) {

        try {
            return new GetModelAsyncTask(type).execute(param).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }

    protected List<TodoGroup> getList(int type, String param) {

        try {
            return new GetModelsAsyncTask(type).execute(param).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }

    protected void insertOrUpdate(int type, TodoGroup myModel) {
        new SetModelsAsyncTask(type).execute(myModel);
    }

    protected void insertOrUpdate(int type, TodoGroup... myModels) {
        new SetModelsAsyncTask(type).execute(myModels);
    }

    protected void removeAll(int type) {
        new SetAllModelsAsyncTask(type).execute();
    }


    protected class GetModelAsyncTask extends AsyncTask<String, Void, TodoGroup> {
        private final int mType;

        public GetModelAsyncTask(int type) {
            mType = type;
        }

        @Override
        protected TodoGroup doInBackground(String... params) {

            switch (mType) {
                case SELECT_ID:
                    return mDao.getById(params[0]);
                case SELECT_MAX_SEQ:
                    return mDao.getMaxSequence(params[0]);
            }

            return null;
        }
    }

    protected class GetModelsAsyncTask extends AsyncTask<String, Void, List<TodoGroup>> {
        private final int mType;

        public GetModelsAsyncTask(int type) {
            mType = type;
        }

        @Override
        protected List<TodoGroup> doInBackground(String... params) {

            switch (mType) {
                case SELECT_ALL:
                    return mDao.getAll();
                case SELECT_ALL_WITH_DELETE_ITEMS:
                    return mDao.getAllWithDeleteItems();
                case SELECT_CHILDREN:
                    return mDao.getChildren(params[0]);
                case SELECT_FAVORITE:
                    return mDao.getFavorite();
            }

            return null;
        }
    }

    protected class SetModelsAsyncTask extends AsyncTask<TodoGroup, Void, Void> {
        private final int mType;

        public SetModelsAsyncTask(int type) {
            mType = type;
        }

        @Override
        protected Void doInBackground(TodoGroup... params) {

            switch (mType) {
                case INSERT_ID:
                    mDao.insertOrUpdate(params[0]);
                    break;
                case INSERT_ARRAY:
                    mDao.insertOrUpdate(params);
                    break;
                case REMOVE_ID:
                    mDao.remove(params[0]);
                    break;
                case REMOVE_ARRAY:
                    mDao.remove(params);
                    break;
            }

            return null;
        }
    }

    protected class SetAllModelsAsyncTask extends AsyncTask<Void, Void, Void> {
        private final int mType;

        public SetAllModelsAsyncTask(int type) {
            mType = type;
        }

        @Override
        protected Void doInBackground(Void... voids) {

            switch (mType) {
                case REMOVE_ALL:
                    mDao.removeAll();
                    break;
            }

            return null;
        }
    }
}
