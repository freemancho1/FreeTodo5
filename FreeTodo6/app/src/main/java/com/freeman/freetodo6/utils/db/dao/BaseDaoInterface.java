package com.freeman.freetodo6.utils.db.dao;

import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;

import com.freeman.freetodo6.utils.db.model.BaseModel;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

public interface BaseDaoInterface<MyModel extends BaseModel> {

    MyModel getById(String id);
    List<MyModel> getAll();
    List<MyModel> getAllWithDeleteItems();

    // TodoGroupDao
    List<MyModel> getChildren(String parent_id);
    List<MyModel> getFavorite();

    // ColorDao
    List<MyModel> getDefaultColor();

    @Insert(onConflict = REPLACE)
    void insertOrUpdate(MyModel myModel);
    @Insert(onConflict = REPLACE)
    void insertOrUpdate(MyModel... myModels);

    @Delete
    void remove(MyModel myModel);
    @Delete
    void remove(MyModel... myModel);
    void removeAll();

}
