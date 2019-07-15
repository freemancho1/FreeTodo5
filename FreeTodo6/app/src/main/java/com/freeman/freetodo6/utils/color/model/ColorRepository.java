package com.freeman.freetodo6.utils.color.model;

import android.app.Application;

import com.freeman.freetodo6.utils.db.AppDatabase;

import java.util.List;
import java.util.Random;

public class ColorRepository extends ColorAsyncTask {
    private static final String LOG_TAG = ColorRepository.class.getSimpleName();

    public ColorRepository(Application application) {
        AppDatabase database = AppDatabase.getInstance(application);
        super.setDao(database.colorDao());
    }

    public Color get(String id) {
        return getOne(SELECT_ID, id);
    }

    public List<Color> getAll() {
        return getList(SELECT_ALL, "");
    }

    public Color getRandomColor() {
        List<Color> colors = getAll();
        Random random = new Random();
        return colors.get(random.nextInt(colors.size()));
    }

    public List<Color> getDefaultColor() {
        return getList(SELECT_DEFAULT, "");
    }

    public void insert(Color color) {
        insertOrUpdate(INSERT_ID, color);
    }
    public void insert(List<Color> colors) {
        insertOrUpdate(INSERT_ARRAY, colors.toArray(new Color[0]));
    }

    public void update(Color color) {
        insert(color);
    }
    public void update(List<Color> colors) {
        insert(colors);
    }

    public void remove(Color color) {
        insertOrUpdate(REMOVE_ID, color);
    }
    public void remove(List<Color> colors) {
        insertOrUpdate(REMOVE_ARRAY, colors.toArray(new Color[0]));
    }
    public void removeAll() {
        super.removeAll(REMOVE_ALL);
    }

}
