package com.freeman.freetodo6.utils.color.model;

import android.app.Application;

import com.freeman.freetodo6.utils.db.AppDatabase;
import com.freeman.freetodo6.utils.db.repository.BaseRepository;

import java.util.List;
import java.util.Random;

public class ColorRepository extends BaseRepository<ColorDao, Color> {
    private static final String LOG_TAG = ColorRepository.class.getSimpleName();

    private final ColorDao mDao;

    public ColorRepository(Application application) {
        AppDatabase database = AppDatabase.getInstance(application);
        mDao = database.colorDao();
    }

    public Color get(String id) {
        return getOne(mDao, SELECT_ID, id);
    }

    public List<Color> getAll() {
        return getList(mDao, SELECT_ALL, "");
    }

    public Color getRandomColor() {
        List<Color> colors = getAll();
        Random random = new Random();
        return colors.get(random.nextInt(colors.size()));
    }

    public List<Color> getDefaultColor() {
        return getList(mDao, SELECT_DEFAULT, "");
    }

    public void insert(Color color) {
        insertOrUpdate(mDao, INSERT_ID, color);
    }
    public void insert(List<Color> colors) {
        insertOrUpdate(mDao, INSERT_ARRAY, colors.toArray(new Color[0]));
    }

    public void update(Color color) {
        insert(color);
    }
    public void update(List<Color> colors) {
        insert(colors);
    }

    public void remove(Color color) {
        insertOrUpdate(mDao, REMOVE_ID, color);
    }
    public void remove(List<Color> colors) {
        insertOrUpdate(mDao, REMOVE_ARRAY, colors.toArray(new Color[0]));
    }
    public void removeAll() {
        super.removeAll(mDao, REMOVE_ALL);
    }

}
