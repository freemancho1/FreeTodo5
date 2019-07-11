package com.freeman.freetodo5.utils.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.freeman.freetodo5.todolist.group.model.TodoListGroup;
import com.freeman.freetodo5.todolist.group.model.TodoListGroupDao;
import com.freeman.freetodo5.utils.color.model.Color;
import com.freeman.freetodo5.utils.color.model.ColorDao;

@Database(
        entities = {
                Color.class,
                TodoListGroup.class
        },
        version = 2,
        exportSchema = false
)
public abstract class AppDatabase extends RoomDatabase {

    private static volatile AppDatabase INSTANCE;

    public abstract ColorDao colorDao();
    public abstract TodoListGroupDao todoListGroupDao();

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
