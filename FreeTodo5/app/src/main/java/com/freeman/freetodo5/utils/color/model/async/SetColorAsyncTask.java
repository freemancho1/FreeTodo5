package com.freeman.freetodo5.utils.color.model.async;

import android.os.AsyncTask;

import com.freeman.freetodo5.utils.color.model.Color;
import com.freeman.freetodo5.utils.color.model.ColorDao;
import com.freeman.freetodo5.utils.color.model.ColorRepository;

public class SetColorAsyncTask extends AsyncTask<Color, Void, Void> {
    private final ColorDao mDao;
    private final int mType;

    public SetColorAsyncTask(ColorDao colorDao, int type) {
        mDao = colorDao;
        mType = type;
    }

    @Override
    protected Void doInBackground(Color... colors) {

        switch (mType) {
            case ColorRepository.INSERT_ID:
                mDao.insertOrUpdate(colors[0]);
                break;
            case ColorRepository.INSERT_ARRAY:
                mDao.insertOrUpdate(colors);
                break;
            case ColorRepository.REMOVE_ALL:
                mDao.removeAll();
                break;
            case ColorRepository.REMOVE_ID:
                mDao.remove(colors[0]);
                break;
            case ColorRepository.REMOVE_ARRAY:
                mDao.remove(colors);
                break;
        }

        return null;
    }
}
