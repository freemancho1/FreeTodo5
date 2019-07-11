package com.freeman.freetodo5.utils.color.model.async;

import android.os.AsyncTask;

import com.freeman.freetodo5.utils.color.model.Color;
import com.freeman.freetodo5.utils.color.model.ColorDao;
import com.freeman.freetodo5.utils.color.model.ColorRepository;

public class GetColorAsyncTask extends AsyncTask<String, Void, Color> {
    private final ColorDao mDao;
    private final int mType;

    public GetColorAsyncTask(ColorDao colorDao, int type) {
        mDao = colorDao;
        mType = type;
    }

    @Override
    protected Color doInBackground(String... strings) {

        switch (mType) {
            case ColorRepository.SELECT_ID:
                return mDao.getById(strings[0]);
        }

        return null;
    }
}
