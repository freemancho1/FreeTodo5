package com.freeman.freetodo5.utils.color.model;

import android.app.Application;

import com.freeman.freetodo5.utils.color.model.async.GetColorAsyncTask;
import com.freeman.freetodo5.utils.color.model.async.GetColorsAsyncTask;
import com.freeman.freetodo5.utils.color.model.async.SetColorAsyncTask;
import com.freeman.freetodo5.utils.db.AppDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;

public class ColorRepository {

    public static final int SELECT_ALL = 1001;
    public static final int SELECT_ID = 1002;
    public static final int SELECT_DEFAULT = 1003;
    public static final int INSERT_ID = 2001;       // with update
    public static final int INSERT_ARRAY = 2002;    // with update
    public static final int REMOVE_ALL = 3001;
    public static final int REMOVE_ID = 3002;
    public static final int REMOVE_ARRAY = 3003;

    private final ColorDao mDao;
    private Color mDummyData;

    public ColorRepository(Application application) {
        AppDatabase database = AppDatabase.getInstance(application);
        mDao = database.colorDao();
        mDummyData = new Color();
    }

    public Color get(String id) {
        Color result = new Color();
        try {
            result = new GetColorAsyncTask(mDao, SELECT_ID).execute(id).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return result;
    }

    public Color getRandomColor() {
        List<Color> colors = getAllColor(SELECT_DEFAULT);
        Random random = new Random();
        return colors.get(random.nextInt(colors.size()));
    }
    public List<Color> getAll() { return getAllColor(SELECT_ALL); }
    public List<Color> getDefault() { return getAllColor(SELECT_DEFAULT); }
    private List<Color> getAllColor(int type) {
        List<Color> results = new ArrayList<>();
        try {
            switch (type) {
                case SELECT_ALL:
                    results = new GetColorsAsyncTask(mDao, SELECT_ALL).execute(mDummyData).get();
                    break;
                case SELECT_DEFAULT:
                    results = new GetColorsAsyncTask(mDao, SELECT_DEFAULT).execute(mDummyData).get();
                    break;
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return results;
    }

    public void insert(Color color) {
        new SetColorAsyncTask(mDao, INSERT_ID).execute(color);
    }
    public void insert(List<Color> colors) {
        new SetColorAsyncTask(mDao, INSERT_ARRAY).execute(colors.toArray(new Color[0]));
    }
    public void update(Color color) { insert(color); }
    public void update(List<Color> colors) { insert(colors); }

    public void removeAll() {
        new SetColorAsyncTask(mDao, REMOVE_ALL).execute(mDummyData);
    }
    public void remove(Color color) {
        new SetColorAsyncTask(mDao, REMOVE_ID).execute(color);
    }
    public void remove(List<Color> colors) {
        new SetColorAsyncTask(mDao, REMOVE_ARRAY).execute(colors.toArray(new Color[0]));
    }

}
