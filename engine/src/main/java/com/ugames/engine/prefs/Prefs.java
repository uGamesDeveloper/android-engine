package com.ugames.engine.prefs;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Prefs {

    private static final String tag = Prefs.class.getSimpleName();
    private static Prefs instance;
    private Context context;

    private HashMap<PrefsLibrary, SharedPreferences> sharedPreferencesHashMap;
    private HashMap<PrefsLibrary, SharedPreferences.Editor> editorHashMap;


    private enum DefaultLibrary implements PrefsLibrary {
        UGamesSharedPreferences
    }

    @SuppressLint("CommitPrefEdits")
    private Prefs(@NonNull Context context, PrefsLibrary[] libraryNames) {
        this.context = context.getApplicationContext();

        sharedPreferencesHashMap = new HashMap<>();
        editorHashMap = new HashMap<>();

        for (PrefsLibrary libraryName : libraryNames) {

            SharedPreferences sharedPreferences = getContext().getSharedPreferences(libraryName.toString(), Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            sharedPreferencesHashMap.put(libraryName, sharedPreferences);
            editorHashMap.put(libraryName, editor);
        }
    }

    private Context getContext() {
        return context;
    }

    private static Prefs getInstance() {
        return instance;
    }


    public static void init(@NonNull Context context) {
        instance = new Prefs(context, new PrefsLibrary[]{DefaultLibrary.UGamesSharedPreferences});
    }

    public static void initWithOtherLibrary(@NonNull Context context, PrefsLibrary[] libraryNames) {
        PrefsLibrary[] libraries = new PrefsLibrary[libraryNames.length + 1];
        System.arraycopy(libraryNames, 0, libraries, 1, libraryNames.length);
        libraries[0] = DefaultLibrary.UGamesSharedPreferences;
        instance = new Prefs(context, libraries);
    }

    private static SharedPreferences getSharedPreferences(PrefsLibrary library) {
        return getInstance().sharedPreferencesHashMap.get(library);
    }

    private static SharedPreferences.Editor getEditor(PrefsLibrary library) {
        return getInstance().editorHashMap.get(library);
    }

    //region Int

    public static void setInt(@NonNull String key, int value) {
        setInt(key, value, DefaultLibrary.UGamesSharedPreferences);
    }

    public static void setInt(@NonNull String key, int value, PrefsLibrary library) {
        getEditor(library).putInt(key, value);
    }

    public static int getInt(@NonNull String key, int defaultValue) {
        return getInt(key, defaultValue, DefaultLibrary.UGamesSharedPreferences);
    }

    public static int getInt(@NonNull String key, int defaultValue, PrefsLibrary library) {
        return getSharedPreferences(library).getInt(key, defaultValue);
    }

    //endregion

    //region long
    public static void setLong(@NonNull String key, long value) {
        setLong(key, value, DefaultLibrary.UGamesSharedPreferences);
    }

    public static void setLong(@NonNull String key, long value, PrefsLibrary library) {
        getEditor(library).putLong(key, value);
    }

    public static long getLong(@NonNull String key, long defaultValue) {
        return getLong(key, defaultValue, DefaultLibrary.UGamesSharedPreferences);
    }

    public static long getLong(@NonNull String key, long defaultValue, PrefsLibrary library) {
        return getSharedPreferences(library).getLong(key, defaultValue);
    }

    //endregion

    //region float

    public static void setFloat(@NonNull String key, float value) {
        setFloat(key, value, DefaultLibrary.UGamesSharedPreferences);
    }

    public static void setFloat(@NonNull String key, float value, PrefsLibrary library) {
        getEditor(library).putFloat(key, value);
    }

    public static float getFloat(@NonNull String key, float defaultValue) {
        return getFloat(key, defaultValue, DefaultLibrary.UGamesSharedPreferences);
    }

    public static float getFloat(@NonNull String key, float defaultValue, PrefsLibrary library) {
        return getSharedPreferences(library).getFloat(key, defaultValue);
    }

    //endregion

    //region bool

    public static void setBool(@NonNull String key, boolean value) {
        setBool(key, value, DefaultLibrary.UGamesSharedPreferences);
    }

    public static void setBool(@NonNull String key, boolean value, PrefsLibrary library) {
        getEditor(library).putBoolean(key, value);
    }

    public static boolean getBool(@NonNull String key, boolean defaultValue) {
        return getBool(key, defaultValue, DefaultLibrary.UGamesSharedPreferences);
    }

    public static boolean getBool(@NonNull String key, boolean defaultValue, PrefsLibrary library) {
        return getSharedPreferences(library).getBoolean(key, defaultValue);
    }

    //endregion

    //region string

    public static void setString(@NonNull String key, String value) {
        setString(key, value, DefaultLibrary.UGamesSharedPreferences);
    }

    public static void setString(@NonNull String key, String value, PrefsLibrary library) {
        getEditor(library).putString(key, value);
    }

    public static String getString(@NonNull String key, String defaultValue) {
        return getString(key, defaultValue, DefaultLibrary.UGamesSharedPreferences);
    }

    public static String getString(@NonNull String key, String defaultValue, PrefsLibrary library) {
        return getSharedPreferences(library).getString(key, defaultValue);
    }

    //endregion

    //region deleteKey

    public static void deleteKey(String key) {
        deleteKey(key, DefaultLibrary.UGamesSharedPreferences);
    }

    public static void deleteKey(String key, PrefsLibrary library) {
        SharedPreferences.Editor editor = getEditor(library);
        if (hasKey(key, library)) {
            editor.remove(key);
        }
    }

    //endregion

    //region deleteAll

    public static void deleteAll() {
        deleteAll(DefaultLibrary.UGamesSharedPreferences);
    }

    public static void deleteAll(PrefsLibrary library) {
        SharedPreferences sharedPreferences = getSharedPreferences(library);
        SharedPreferences.Editor editor = getEditor(library);
        for (String key : sharedPreferences.getAll().keySet()) {
            if (hasKey(key))
                editor.remove(key);
        }
    }

    //endregion

    //region hasKey

    public static boolean hasKey(@NonNull String key) {
        return hasKey(key, DefaultLibrary.UGamesSharedPreferences);
    }

    public static boolean hasKey(@NonNull String key, PrefsLibrary library) {
        return getSharedPreferences(library).contains(key);
    }

    //endregion

    //region save

    public static void save() {
        save(DefaultLibrary.UGamesSharedPreferences);
    }

    public static void save(PrefsLibrary library) {
        getEditor(library).apply();
    }

    //endregion

    //region showAll

    public static void showAll() {
        showAll(DefaultLibrary.UGamesSharedPreferences);
    }

    public static void showAll(PrefsLibrary library) {
        ArrayList<String> keys = new ArrayList<>(getSharedPreferences(library).getAll().keySet());
        Collections.sort(keys);

        for (String key : keys) {
            Object value = getSharedPreferences(library).getAll().get(key);
            if (value != null)
                Log.d(tag, key + ": " + value.toString());
        }
    }

    //endregion
}
