package com.demo.vramachandran.karma.settings;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;

import com.demo.vramachandran.karma.R;

/**
 * Created by vramachandran on 9/20/2015.
 * Settings class which perform asynchronous access to preference file.
 */
public class KarmaSettings {
    public interface SettingsInteractionListener {
        /**
         * Notifies that it is app's first launch
         */
        void onFirstLaunch();
    }

    private static String sFirstLaunchKey = null;
    private static SharedPreferences sDefaultPrefs = null;

    private enum SettingsType {FIRST_LAUNCH}

    private final Context mContext;
    private final SettingsInteractionListener mListener;

    public KarmaSettings(Context context, SettingsInteractionListener listener) {
        mContext = context;
        mListener = listener;
    }

    /**
     * Verifies if the app is making it's first launch.
     * If it does, it notifies the listener that it is the first launch
     * and resets the key value
     */
    public void verifyFirstLaunch() {
        if (sDefaultPrefs == null || sFirstLaunchKey == null) {
            GetSharedPreferenceTask task = new GetSharedPreferenceTask();
            task.execute(SettingsType.FIRST_LAUNCH);
            return;
        }
        boolean isFirstLaunch = sDefaultPrefs.getBoolean(sFirstLaunchKey, true);
        if (isFirstLaunch) {
            mListener.onFirstLaunch();
            SharedPreferences.Editor editor = sDefaultPrefs.edit();
            editor.putBoolean(sFirstLaunchKey, false);
            editor.commit();
        }
    }


    private class GetSharedPreferenceTask extends AsyncTask<SettingsType, Void, SettingsType> {

        @Override
        protected SettingsType doInBackground(SettingsType... params) {
            if (params.length < 1) {
                return null;
            }
            SettingsType type = params[0];

            if (sDefaultPrefs == null) {
                sDefaultPrefs = PreferenceManager.getDefaultSharedPreferences(mContext);
            }
            switch (type) {
                case FIRST_LAUNCH:
                    if (sFirstLaunchKey == null) {
                        sFirstLaunchKey = mContext.getResources().getString(
                                R.string.pref_key_first_launch);
                    }
                    break;
                default:
                    break;
            }
            return type;
        }


        @Override
        protected void onPostExecute(SettingsType type) {
            super.onPostExecute(type);

            switch (type) {
                case FIRST_LAUNCH:
                    verifyFirstLaunch();
                    break;
                default:
                    break;
            }
        }
    }
}
