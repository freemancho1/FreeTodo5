package com.freeman.freetodo6.utils.db.model;

import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.UUID;

public class BaseModel {

    @PrimaryKey
    @NonNull
    private String  id = UUID.randomUUID().toString().replaceAll("-", "");

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }
}
