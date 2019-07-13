package com.freeman.freetodo6.utils.db.repository;

import android.os.AsyncTask;

import com.freeman.freetodo6.utils.db.dao.BaseDaoInterface;
import com.freeman.freetodo6.utils.db.model.BaseModel;

import java.util.List;
import java.util.concurrent.ExecutionException;

public abstract class BaseRepository
        <MyDao extends BaseDaoInterface, MyModel extends BaseModel> {

    public static final int SELECT_ID = 1001;
    public static final int SELECT_ALL = 1002;
    public static final int SELECT_ALL_WITH_DELETE_ITEMS = 1003;

    public static final int SELECT_CHILDREN = 1004;
    public static final int SELECT_FAVORITE = 1005;

    public static final int SELECT_DEFAULT = 1006;

    public static final int INSERT_ID = 2001;       // with update
    public static final int INSERT_ARRAY = 2002;    // with update

    public static final int REMOVE_ID = 3001;
    public static final int REMOVE_ALL = 3002;
    public static final int REMOVE_ARRAY = 3003;

    protected MyModel getOne(MyDao dao, int type, String param) {

        try {
            return new GetModelAsyncTask(dao, type).execute(param).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }

    protected List<MyModel> getList(MyDao dao, int type, String param) {

        try {
            return new GetModelsAsyncTask(dao, type).execute(param).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }

    protected void insertOrUpdate(MyDao dao, int type, MyModel myModel) {
        new SetModelsAsyncTask(dao, type).execute(myModel);
    }

    protected void insertOrUpdate(MyDao dao, int type, MyModel... myModels) {
        new SetModelsAsyncTask(dao, type).execute(myModels);
    }

    protected void removeAll(MyDao dao, int type) {
        new SetAllModelsAsyncTask(dao, type).execute();
    }


    protected class GetModelAsyncTask extends AsyncTask<String, Void, MyModel> {
        private final MyDao mDao;
        private final int mType;

        public GetModelAsyncTask(MyDao dao, int type) {
            mDao = dao;
            mType = type;
        }

        @Override
        protected MyModel doInBackground(String... params) {

            switch (mType) {
                case SELECT_ID:
                    return (MyModel) mDao.getById(params[0]);
            }

            return null;
        }
    }

    protected class GetModelsAsyncTask extends AsyncTask<String, Void, List<MyModel>> {
        private final MyDao mDao;
        private final int mType;

        public GetModelsAsyncTask(MyDao dao, int type) {
            mDao = dao;
            mType = type;
        }

        @Override
        protected List<MyModel> doInBackground(String... params) {

            switch (mType) {
                case SELECT_ALL:
                    return mDao.getAll();
                case SELECT_ALL_WITH_DELETE_ITEMS:
                    return mDao.getAllWithDeleteItems();
                case SELECT_CHILDREN:
                    return mDao.getChildren(params[0]);
                case SELECT_FAVORITE:
                    return mDao.getFavorite();
                case SELECT_DEFAULT:
                    return mDao.getDefaultColor();
            }

            return null;
        }
    }

    protected class SetModelsAsyncTask extends AsyncTask<MyModel, Void, Void> {
        private final MyDao mDao;
        private final int mType;

        public SetModelsAsyncTask(MyDao dao, int type) {
            mDao = dao;
            mType = type;
        }

        @Override
        protected Void doInBackground(MyModel... params) {

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
        private final MyDao mDao;
        private final int mType;

        public SetAllModelsAsyncTask(MyDao dao, int type) {
            mDao = dao;
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
