package kh.edu.rupp.ite.mad_project.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManager {
    private static final String SHARED_PREF_NAME = "MyAppPrefs";
    private static final String TOKEN_KEY = "auth_token";
    private static final String USER_ID_KEY = "user_id";
    private final SharedPreferences sharedPreferences;
    private final SharedPreferences.Editor editor;

    public SharedPrefManager(Context context) {
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    // Save token
    public void saveToken(String token) {
        editor.putString(TOKEN_KEY, token);
        editor.apply();
    }

    // Get token
    public String getToken() {
        return sharedPreferences.getString(TOKEN_KEY, null);
    }

    // Save userId
    public void saveUserId(String userId) {
        editor.putString(USER_ID_KEY, userId);
        editor.apply();
    }

    // Get userId
    public String getUserId() {
        return sharedPreferences.getString(USER_ID_KEY, null);
    }

    // Clear all data
    public void clearToken() {
        editor.clear().apply();
    }
}
