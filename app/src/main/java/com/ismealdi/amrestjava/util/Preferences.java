package com.ismealdi.amrestjava.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Al
 * on 22/04/19 | 18:38
 */
public class Preferences {
    
    private Context context;
    private SharedPreferences sharedPref;
    
    public Preferences(Context context) {
        this.context = context;
        this.sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void storeToken(String string) {
        sharedPref.edit().putString(new Constants().TOKEN, string).apply();
    }
    
    public String getToken() {
        return sharedPref.getString(new Constants().TOKEN, "");
    }
    
    public void storeName(String string) {
        sharedPref.edit().putString(new Constants().NAME, string).apply();
    }
    
    public String getName() {
        return sharedPref.getString(new Constants().NAME, "");
    }
    
}
