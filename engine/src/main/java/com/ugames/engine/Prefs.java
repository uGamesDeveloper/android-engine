package com.ugames.engine;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

public class Prefs {

    private static Prefs instance;
    private Context context;
    private SharedPreferences sharedPreferences;

    private Prefs(@NonNull Context context) {
        this.context = context.getApplicationContext();
        sharedPreferences = this.context.getSharedPreferences("ENGINE_PREFS", Context.MODE_PRIVATE);
    }

    private static SharedPreferences.Editor getEditor() {
        return getSharedPreferences().edit();
    }

    private static SharedPreferences getSharedPreferences() {
        return instance.sharedPreferences;
    }

    private static Prefs getInstance(Context context) {
        if (instance == null || instance.context != context) {
            instance = new Prefs(context);
        }
        return instance;
    }

    public static void init(@NonNull Context context) {
        instance = getInstance(context);
    }

    public static void setInt(@NonNull String key, int value) {
        getEditor().putInt(key, value);
        getEditor().apply();
    }

    public static int getInt(@NonNull String key, int defaultValue) {
        return getSharedPreferences().getInt(key, defaultValue);
    }

    public static void setLong(@NonNull String key, long value) {
        getEditor().putLong(key, value);
        getEditor().apply();
    }

    public static long getLong(@NonNull String key, long defaultValue) {
        return getSharedPreferences().getLong(key, defaultValue);
    }

    public static void setFloat(@NonNull String key, float value) {
        getEditor().putFloat(key, value);
        getEditor().apply();
    }

    public static float getFloat(@NonNull String key, float defaultValue) {
        return getSharedPreferences().getFloat(key, defaultValue);
    }

    public static void setBool(@NonNull String key, boolean value) {
        getEditor().putBoolean(key, value);
        getEditor().apply();
    }

    public static boolean getBool(@NonNull String key, boolean defaultValue) {
        return getSharedPreferences().getBoolean(key, defaultValue);
    }

    public static void setString(String key, String value) {
        getEditor().putString(key, value);
        getEditor().apply();
    }

    public static String getString(@NonNull String key, String defaultValue) {
        return getSharedPreferences().getString(key, defaultValue);
    }

    public static void deleteKey(String key) {
        getEditor().remove(key);
        getEditor().apply();
    }

    public static void deleteAll() {
        for (String key : getSharedPreferences().getAll().keySet())
            getEditor().remove(key);
        getEditor().apply();
    }

    public static boolean hasKey(@NonNull String key) {
        return getSharedPreferences().contains(key);
    }
}
