package com.example.vramachandran.karma.settings;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.vramachandran.karma.R;

/**
 * Created by vramachandran on 9/20/2015.
 */
public class KarmaSettings {


    private final Context mContext;
    private static String sFirstLaunchKey = null;

    public KarmaSettings(Context context) {
        mContext = context;
    }

    public boolean needHelpDialog() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
        if (sFirstLaunchKey == null) {
            sFirstLaunchKey = mContext.getResources().getString(
                    R.string.pref_key_first_launch);
        }
        boolean isFirstLaunch =  prefs.getBoolean(sFirstLaunchKey, false);
        if (isFirstLaunch) {
            SharedPreferences sharedPref = ((Activity)mContext).getPreferences(Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putBoolean(sFirstLaunchKey, false);
            editor.commit();
        }
        return isFirstLaunch;
    }



}
