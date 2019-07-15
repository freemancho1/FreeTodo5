package com.freeman.freetodo6.utils.color.model;

import android.os.AsyncTask;

import com.freeman.freetodo6.utils.db.BaseAsyncTask;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class ColorAsyncTask extends BaseAsyncTask {

    private ColorDao mDao;

    protected void setDao(ColorDao dao) {
        this.mDao = dao;
    }

    protected Color getOne(int type, String param) {

        try {
            return new GetModelAsyncTask(type).execute(param).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }

    protected List<Color> getList(int type, String param) {

        try {
            return new GetModelsAsyncTask(type).execute(param).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }

    protected void insertOrUpdate(int type, Color myModel) {
        new SetModelsAsyncTask(type).execute(myModel);
    }

    protected void insertOrUpdate(int type, Color... myModels) {
        new SetModelsAsyncTask(type).execute(myModels);
    }

    protected void removeAll(int type) {
        new SetAllModelsAsyncTask(type).execute();
    }


    protected class GetModelAsyncTask extends AsyncTask<String, Void, Color> {
        private final int mType;

        public GetModelAsyncTask(int type) {
            mType = type;
        }

        @Override
        protected Color doInBackground(String... params) {

            switch (mType) {
                case SELECT_ID:
                    return mDao.getById(params[0]);
            }

            return null;
        }
    }

    protected class GetModelsAsyncTask extends AsyncTask<String, Void, List<Color>> {
        private final int mType;

        public GetModelsAsyncTask(int type) {
            mType = type;
        }

        @Override
        protected List<Color> doInBackground(String... params) {

            switch (mType) {
                case SELECT_ALL:
                    return mDao.getAll();
                case SELECT_DEFAULT:
                    return mDao.getDefaultColor();
            }

            return null;
        }
    }

    protected class SetModelsAsyncTask extends AsyncTask<Color, Void, Void> {
        private final int mType;

        public SetModelsAsyncTask(int type) {
            mType = type;
        }

        @Override
        protected Void doInBackground(Color... params) {

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
