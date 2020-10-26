package com.nz.nztravelmate.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Preferences {

        SharedPreferences preferences;
        SharedPreferences.Editor editor;
        Context mContext;
        private String FIRST_TIME_CALL = "FIRST_TIME_CALL";
        private String REGISTERED = "REGISTERED";
        private String SUBSCRIBED = "SUBSCRIBED";
        private String LOGIN = "LOGIN";

    public Preferences() {

    }

    public Preferences(Context context) {
        // TODO Auto-generated constructor stub\
        mContext = context;
        preferences = context.getSharedPreferences("RotoruaPref", Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

        public boolean isLogin() {
        return preferences.getBoolean(LOGIN, true);
    }

        public void setLogin(boolean firstTimeCall) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(this.LOGIN, firstTimeCall);
        editor.commit();
    }

        public Boolean getFirstTime() {
        return preferences.getBoolean(PrefConstants.FIRST_TIME, false);
    }

        public void setFirstTime(Boolean frst) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(PrefConstants.FIRST_TIME, frst);
        editor.commit();
    }

        public boolean isFirstTimeCall() {
        return preferences.getBoolean(FIRST_TIME_CALL, true);
    }

        public void setFirstTimeCall(boolean firstTimeCall) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(this.FIRST_TIME_CALL, firstTimeCall);
        editor.commit();
    }

        public Boolean getREGISTERED() {
        return preferences.getBoolean(REGISTERED, false);
    }

        public void setREGISTERED(Boolean REGISTERED) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(this.REGISTERED, REGISTERED);
        editor.commit();
    }

        public Boolean getSubscribed() {
        return preferences.getBoolean(SUBSCRIBED, false);
    }

        public void setSubscribed(Boolean SUBSCRIBED) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(this.SUBSCRIBED, SUBSCRIBED);
        editor.commit();
    }

        public Preferences getInstance(Context ctx) {
        preferences = PreferenceManager.getDefaultSharedPreferences(ctx);
        editor = preferences.edit();
        mContext = ctx;
        return this;
    }

        public void clearPreferences() {
        editor.clear();
        editor.commit();
    }

        public void setPreferences(SharedPreferences preferences) {
        this.preferences = preferences;
    }

        public void putString(String key, String value) {
        editor.putString(key, value);
        editor.commit();
    }

        public String getString(String key) {
        return preferences.getString(key, "");
    }

        public void putBoolean(String key, boolean value) {
        editor.putBoolean(key, value);
        editor.commit();
    }

        public boolean getBoolean(String key) {
        return preferences.getBoolean(key, false);
    }

        public void putInt(String key, int value) {
        editor.putInt(key, value);
        editor.commit();
    }

        public int getInt(String key) {
        return preferences.getInt(key, 0);
    }

        public void putFloat(String key, float value) {
        editor.putFloat(key, value);
        editor.commit();
    }

        public float getFloat(String key) {
        return preferences.getFloat(key, 0f);
    }

        public void putLong(String key, long value) {
        editor.putLong(key, value);
        editor.commit();
    }

        public long getLong(String key) {
        return preferences.getLong(key, 0);
    }




}
