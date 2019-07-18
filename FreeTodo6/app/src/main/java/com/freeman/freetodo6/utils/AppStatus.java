package com.freeman.freetodo6.utils;

public class AppStatus {
    private static AppStatus INSTANCE = null;

    private boolean isFavoriteChange = true;
    private boolean isMenuChange = true;

    public boolean isFavoriteChange() {
        return isFavoriteChange;
    }

    public void setFavoriteChange(boolean favoriteChange) {
        isFavoriteChange = favoriteChange;
    }

    public boolean isMenuChange() {
        return isMenuChange;
    }

    public void setMenuChange(boolean groupMessageChange) {
        isMenuChange = groupMessageChange;
    }

    public void setSideMenuChange(boolean sideMenuChange) {
        isFavoriteChange = sideMenuChange;
        isMenuChange = sideMenuChange;
    }

    public static synchronized AppStatus getInstance() {
        if (INSTANCE == null) INSTANCE = new AppStatus();
        return INSTANCE;
    }
}
