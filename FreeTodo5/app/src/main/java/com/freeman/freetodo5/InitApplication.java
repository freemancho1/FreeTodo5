package com.freeman.freetodo5;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class InitApplication extends Application {

    private boolean mSideMenuFavoriteChange = false;
    private boolean mSideMenuItemsChange = false;

    @Override
    public void onCreate() {
        super.onCreate();

        initRealmDatabase();
    }

    private void initRealmDatabase() {
        Realm.init(this);

        RealmConfiguration configuration =
                new RealmConfiguration.Builder()
                        .name("freetodo5")
                        .schemaVersion(1)
                        .deleteRealmIfMigrationNeeded()
                        .build();

        Realm.setDefaultConfiguration(configuration);
    }

    public boolean isSideMenuFavoriteChange() {
        return mSideMenuFavoriteChange;
    }

    public void setSideMenuFavoriteChange(boolean mSideMenuFavoriteChange) {
        this.mSideMenuFavoriteChange = mSideMenuFavoriteChange;
    }

    public boolean isSideMenuItemsChange() {
        return mSideMenuItemsChange;
    }

    public void setSideMenuItemsChange(boolean mSideMenuItemsChange) {
        this.mSideMenuItemsChange = mSideMenuItemsChange;
    }
}
