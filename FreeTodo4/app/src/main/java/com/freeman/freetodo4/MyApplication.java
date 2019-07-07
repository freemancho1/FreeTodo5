package com.freeman.freetodo4;

import android.app.Application;
import android.util.Log;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MyApplication extends Application {
    private static final String LOG_TAG = "[INIT]";

    @Override
    public void onCreate() {
        Log.d(LOG_TAG, "Init application.....");
        super.onCreate();

        Realm.init(this);

        RealmConfiguration configuration = new RealmConfiguration.Builder()
                .name("myrealm")
                .schemaVersion(1)
                .deleteRealmIfMigrationNeeded()
                .build();

        Realm.setDefaultConfiguration(configuration);
        Log.d(LOG_TAG, "End init application....");
    }
}
