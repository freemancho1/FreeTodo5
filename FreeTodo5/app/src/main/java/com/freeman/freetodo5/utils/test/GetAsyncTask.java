package com.freeman.freetodo5.utils.test;

import android.os.AsyncTask;

public abstract class GetAsyncTask<MyDao, Params, Result> extends AsyncTask<Params, Void, Result> {
    private final MyDao mDao;
    private final int mType;

    public GetAsyncTask(MyDao dao, int type) {
        mDao = dao;
        mType = type;
    }

    @Override
    protected Result doInBackground(Params... params) {
        return null;
    }



}
