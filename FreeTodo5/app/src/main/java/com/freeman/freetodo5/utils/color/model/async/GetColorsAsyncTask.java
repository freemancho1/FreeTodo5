package com.freeman.freetodo5.utils.color.model.async;

import android.os.AsyncTask;

import com.freeman.freetodo5.utils.color.model.Color;
import com.freeman.freetodo5.utils.color.model.ColorDao;
import com.freeman.freetodo5.utils.color.model.ColorRepository;

import java.util.List;

public class GetColorsAsyncTask extends AsyncTask<Color, Void, List<Color>> {
    private final ColorDao mDao;
    private final int mType;

    public GetColorsAsyncTask(ColorDao colorDao, int type) {
        mDao = colorDao;
        mType = type;
    }

    @Override
    protected List<Color> doInBackground(Color... colors) {

        switch (mType) {
            case ColorRepository.SELECT_ALL:
                return mDao.getAll();
            case ColorRepository.SELECT_DEFAULT:
                return mDao.getDefault();
        }

        return null;
    }
}
