package com.freeman.freetodo5;

public class GlobalVariable {
    private static GlobalVariable instance = null;

    private boolean mSideMenuFavoriteChange = true;
    private boolean mSideMenuItemsChange = true;

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

    public void setSideMenuChange(boolean sideMenuChange) {
        this.mSideMenuFavoriteChange = sideMenuChange;
        this.mSideMenuItemsChange = sideMenuChange;
    }

    public static synchronized GlobalVariable getInstance() {
        if (instance == null) instance = new GlobalVariable();
        return instance;
    }
}
