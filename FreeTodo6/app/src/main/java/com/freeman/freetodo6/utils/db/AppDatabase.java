package com.freeman.freetodo6.utils.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.freeman.freetodo6.todo.group.model.TodoGroup;
import com.freeman.freetodo6.todo.group.model.TodoGroupDao;
import com.freeman.freetodo6.utils.color.model.Color;
import com.freeman.freetodo6.utils.color.model.ColorDao;

@Database(
        entities = {
                TodoGroup.class,
                Color.class
        },
        version = 1,
        exportSchema = false
)
public abstract class AppDatabase extends RoomDatabase {

    private static volatile AppDatabase INSTANCE;

    public abstract ColorDao colorDao();
    public abstract TodoGroupDao todoListGroupDao();

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                INSTANCE = Room.databaseBuilder(
                        context.getApplicationContext(),
                        AppDatabase.class,
                        "freetodo.sqlite")
                        .fallbackToDestructiveMigration()       // Not migration
                        .build();
            }
        }
        return INSTANCE;
    }

}
