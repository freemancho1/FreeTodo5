package com.freeman.freetodo5.todolist.group.model;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class TodoListGroupRepository {

    private final Realm mRealm;

    public TodoListGroupRepository(Realm mRealm) {
        this.mRealm = mRealm;
    }

    private List<TodoListGroup> RealmResultToList(
            @NonNull RealmResults<TodoListGroup> todoListGroups) {
        List<TodoListGroup> results = new ArrayList<>();
        for(TodoListGroup todoListGroup: todoListGroups) {
            results.add(todoListGroup);
        }
        return results;
    }

    public TodoListGroup get(String id) {
        return mRealm.where(TodoListGroup.class)
                .equalTo("id", id).equalTo("isDelete", false).findFirst();
    }
    public List<TodoListGroup> get() {
        return RealmResultToList(
                mRealm.where(TodoListGroup.class).equalTo("isDelete", false).findAll());
    }

    public List<TodoListGroup> getChildren(String parentId) {
        return RealmResultToList(mRealm.where(TodoListGroup.class)
                .equalTo("parentId", parentId).equalTo("isDelete", false).findAll());
    }

    public List<TodoListGroup> getAllTree(String parentId) {
        List<TodoListGroup> results = new ArrayList<>();
        List<TodoListGroup> children = getChildren(parentId);
        for (TodoListGroup todoListGroup: children) {
            results.add(todoListGroup);
            if (todoListGroup.isChildren()) results.addAll(getAllTree(todoListGroup.getId()));
        }
        return results;
    }

    public List<TodoListGroup> getFavorite() {
        return RealmResultToList(mRealm.where(TodoListGroup.class)
                .equalTo("isFavorite", true).equalTo("isDelete", false).findAll());
    }

    public void insert(TodoListGroup todoListGroup) {
        insertOrUpdate(todoListGroup);
    }
    public void insert(List<TodoListGroup> todoListGroups) {
        insertOrUpdate(todoListGroups);
    }

    public void update(TodoListGroup todoListGroup) {
        insertOrUpdate(todoListGroup);
    }
    public void update(List<TodoListGroup> todoListGroups) {
        insertOrUpdate(todoListGroups);
    }

    public void delete(TodoListGroup todoListGroup) {
        todoListGroup.setDelete(true);
        insertOrUpdate(todoListGroup);
    }
    public void delete(List<TodoListGroup> todoListGroups) {
        for (int i=0; i<todoListGroups.size(); i++) {
            todoListGroups.get(i).setDelete(true);
        }
        insertOrUpdate(todoListGroups);
    }

    public void remove(final String id) {
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                TodoListGroup todoListGroup = get(id);
                if (todoListGroup != null) remove(todoListGroup);
            }
        });
    }
    public void remove(final TodoListGroup todoListGroup) {
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                todoListGroup.deleteFromRealm();
            }
        });
    }
    public void removeAll() {
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<TodoListGroup> results = realm.where(TodoListGroup.class).findAll();
                results.deleteAllFromRealm();
            }
        });
    }

    private void insertOrUpdate(final TodoListGroup todoListGroup) {
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insertOrUpdate(todoListGroup);
            }
        });
    }
    private void insertOrUpdate(final List<TodoListGroup> todoListGroups) {
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insertOrUpdate(todoListGroups);
            }
        });
    }
}
