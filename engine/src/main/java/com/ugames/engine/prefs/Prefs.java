package com.ugames.engine.prefs;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collections;

public class Prefs {

    private static final String tag = Prefs.class.getSimpleName();
    private static Prefs instance;
    private Context context;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;


    public static void migration(MigrationPrefsEnum[] names) {
        for (int i = 0; i < names.length; i++) {
            Log.e("TEST111", names[i].toString());
        }
    }

    @SuppressLint("CommitPrefEdits")
    private Prefs(@NonNull Context context) {
        this.context = context.getApplicationContext();
        sharedPreferences = this.getContext().getSharedPreferences("MY_PREF", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    private Context getContext() {
        return context;
    }

    private static Prefs getInstance() {
        return instance;
    }

    private SharedPreferences.Editor getEditor() {
        return editor;
    }

    private SharedPreferences getSharedPreferences() {
        sharedPreferences = this.getContext().getSharedPreferences("MY_PREF", Context.MODE_PRIVATE);
        return sharedPreferences;
    }

    public static void init(@NonNull Context context) {
        if (getInstance() == null || getInstance().getContext() != context) {
            instance = new Prefs(context);
        }
    }

    public static void setInt(@NonNull String key, int value) {
        SharedPreferences sharedPreferences = getInstance().getContext().getSharedPreferences("MY_PREF", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
    }

    public static int getInt(@NonNull String key, int defaultValue) {
        SharedPreferences sharedPreferences = getInstance().getContext().getSharedPreferences("MY_PREF", Context.MODE_PRIVATE);
        return sharedPreferences.getInt(key, defaultValue);
    }

    public static void setLong(@NonNull String key, long value) {
        SharedPreferences sharedPreferences = getInstance().getContext().getSharedPreferences("MY_PREF", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(key, value);
    }

    public static long getLong(@NonNull String key, long defaultValue) {
        SharedPreferences sharedPreferences = getInstance().getContext().getSharedPreferences("MY_PREF", Context.MODE_PRIVATE);
        return sharedPreferences.getLong(key, defaultValue);
    }

    public static void setFloat(@NonNull String key, float value) {
        SharedPreferences sharedPreferences = getInstance().getContext().getSharedPreferences("MY_PREF", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat(key, value);
    }

    public static float getFloat(@NonNull String key, float defaultValue) {
        SharedPreferences sharedPreferences = getInstance().getContext().getSharedPreferences("MY_PREF", Context.MODE_PRIVATE);
        return sharedPreferences.getFloat(key, defaultValue);
    }

    public static void setBool(@NonNull String key, boolean value) {
        SharedPreferences sharedPreferences = getInstance().getContext().getSharedPreferences("MY_PREF", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
    }

    public static boolean getBool(@NonNull String key, boolean defaultValue) {
        SharedPreferences sharedPreferences = getInstance().getContext().getSharedPreferences("MY_PREF", Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(key, defaultValue);
    }

    public static void setString(String key, String value) {
        SharedPreferences sharedPreferences = getInstance().getContext().getSharedPreferences("MY_PREF", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
    }

    public static String getString(@NonNull String key, String defaultValue) {
        SharedPreferences sharedPreferences = getInstance().getContext().getSharedPreferences("MY_PREF", Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, defaultValue);
    }

    public static void deleteKey(String key) {
        if (hasKey(key)) {
            getInstance().getEditor().remove(key);
        }
    }

    public static void deleteAll() {
        SharedPreferences sharedPreferences = getInstance().getContext().getSharedPreferences("MY_PREF", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        for (String key : sharedPreferences.getAll().keySet()) {
            if (hasKey(key))
                editor.remove(key);
        }
    }

    public static boolean hasKey(@NonNull String key) {
        SharedPreferences sharedPreferences = getInstance().getContext().getSharedPreferences("MY_PREF", Context.MODE_PRIVATE);
        return sharedPreferences.contains(key);
    }

    public static void save() {
        getInstance().getEditor().apply();
    }

    public static void showAll() {
        ArrayList<String> keys = new ArrayList<>(getInstance().getSharedPreferences().getAll().keySet());
        Collections.sort(keys);

        for (String key : keys) {
            Object value = getInstance().getSharedPreferences().getAll().get(key);
            if (value != null)
                Log.d(tag, key + ": " + value.toString());
        }
    }
}
