package ru.mirea.sevostyanov.data.storage;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefsUserStorage {
    private static final String PREFS_NAME = "UserPrefs";
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_USER_NAME = "user_name";
    private static final String KEY_USER_EMAIL = "user_email";
    private static final String KEY_IS_GUEST = "is_guest";

    private SharedPreferences sharedPreferences;

    public SharedPrefsUserStorage(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public void saveUser(String id, String name, String email) {
        sharedPreferences.edit()
                .putString(KEY_USER_ID, id)
                .putString(KEY_USER_NAME, name)
                .putString(KEY_USER_EMAIL, email)
                .apply();
    }

    public String getUserId() {
        return sharedPreferences.getString(KEY_USER_ID, null);
    }

    public String getUserName() {
        return sharedPreferences.getString(KEY_USER_NAME, null);
    }

    public String getUserEmail() {
        return sharedPreferences.getString(KEY_USER_EMAIL, null);
    }

    public void setGuestMode(boolean isGuest) {
        sharedPreferences.edit()
                .putBoolean(KEY_IS_GUEST, isGuest)
                .apply();
    }

    public boolean isGuestMode() {
        return sharedPreferences.getBoolean(KEY_IS_GUEST, false);
    }
}