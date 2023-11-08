package org.techtown.cap2.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PreferenceDelegator {

    private static PreferenceDelegator mInstance;


    private SharedPreferences mPreferences;


    private PreferenceDelegator(Context context) {
        this.mPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static PreferenceDelegator getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new PreferenceDelegator(context);
        }
        return mInstance;
    }

    public String get(String key) {
        return this.mPreferences.getString(key, null);
    }

    public void put(String key, String value) {
        this.mPreferences.edit().putString(key, value).commit();
    }

    public void remove(String key) {
        this.mPreferences.edit().remove(key).commit();
    }
}
