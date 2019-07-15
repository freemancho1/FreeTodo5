package com.freeman.freetodo6.utils;

public class AppStatus {
    private static AppStatus INSTANCE = null;

    private boolean isFavoriteChange = true;
    private boolean isGroupMessageChange = true;

    public boolean isFavoriteChange() {
        return isFavoriteChange;
    }

    public void setFavoriteChange(boolean favoriteChange) {
        isFavoriteChange = favoriteChange;
    }

    public boolean isGroupMessageChange() {
        return isGroupMessageChange;
    }

    public void setGroupMessageChange(boolean groupMessageChange) {
        isGroupMessageChange = groupMessageChange;
    }

    public void setSideMenuChange(boolean sideMenuChange) {
        isFavoriteChange = sideMenuChange;
        isGroupMessageChange = sideMenuChange;
    }

    public static synchronized AppStatus getInstance() {
        if (INSTANCE == null) INSTANCE = new AppStatus();
        return INSTANCE;
    }
}
