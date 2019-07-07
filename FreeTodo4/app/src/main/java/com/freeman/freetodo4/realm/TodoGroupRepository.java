package com.freeman.freetodo4.realm;

import android.util.Log;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

public class TodoGroupRepository {

    private final Realm mRealm;

    public TodoGroupRepository(Realm realm) {
        mRealm = realm;
    }

    public TodoGroup get(String id) {
        return mRealm.where(TodoGroup.class)
                .equalTo("id", id)
                .findFirst();
    }

    public RealmResults<TodoGroup> get() {
        return mRealm.where(TodoGroup.class)
                .equalTo("isDelete", false)
                .findAll();
    }

    public RealmResults<TodoGroup> getChildren(String parentId) {
        return mRealm.where(TodoGroup.class)
                .equalTo("parentId", parentId)
                .equalTo("isDelete", false)
                .findAll();
    }

    public void insert(TodoGroup todoGroup) {
        insertOrUpdate(todoGroup);
    }
    public void update(TodoGroup todoGroup) {
        insertOrUpdate(todoGroup);
    }
    public void insert(List<TodoGroup> todoGroups) {
        insertOrUpdate(todoGroups);
    }
    public void update(List<TodoGroup> todoGroups) {
        insertOrUpdate(todoGroups);
    }

    public void removeAll() {
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<TodoGroup> todoGroups = realm.where(TodoGroup.class).findAll();
                todoGroups.deleteAllFromRealm();
            }
        });
    }

    private void insertOrUpdate(final TodoGroup todoGroup) {
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insertOrUpdate(todoGroup);
            }
        });
    }

    private void insertOrUpdate(final List<TodoGroup> todoGroups) {
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Log.d("TODOREPO", "todoGroups size() - " + todoGroups.size());
                realm.insertOrUpdate(todoGroups);
            }
        });
    }

}
