package com.demo.vramachandran.karma.model.service;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.demo.vramachandran.karma.model.content.provider.KarmaContract;

/**
 * Created by vramachandran on 9/20/2015.
 * An adapter between service clients and Karma Content Provider
 */
public class KarmaService extends IntentService {

    public static final String KARMA_CONTENT_VALUE = "content_values";
    public static final String KARMA_SELECTION = "selection";
    public static final String KARMA_SELECTION_ARGS = "selection_args";
    public static final String LOG_TAG = "KS";
    public static final String KARMA_CLASS = "com.demo.vramachandran.karma.model.service.KarmaService";

    public static final class KarmaActions {
        public static final String NO_ACTION = "no_action";
        public static final String DELETE = "delete_action";
        public static final String INSERT = "insert_action";
        public static final String UPDATE = "update_action";
    }

    /**
     * Construct a new Karma Intent Service.
     */
    public KarmaService() {
        super(KarmaService.class.getName());
    }

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public KarmaService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent karmaIntent) {
        // Gets data from the incoming Intent
        Bundle extras = karmaIntent.getExtras();
        if (extras != null) {
            String action = karmaIntent.getAction();
            switch (action) {
                case KarmaActions.DELETE:
                    deleteKarma(extras);
                    break;
                case KarmaActions.INSERT:
                    insertKarma(extras);
                    break;
                case KarmaActions.UPDATE:
                    updateKarma(extras);
                    break;
                default:
                    Log.e(LOG_TAG, "No action or wrong karma action requested");
                    break;
            }
        }
    }

    private void updateKarma(Bundle extras) {
        boolean success = false;
        ContentValues values = extras.getParcelable(KARMA_CONTENT_VALUE);
        String selection = extras.getString(KARMA_SELECTION);
        String[] selectionArgs = extras.getStringArray(KARMA_SELECTION_ARGS);
        if (values != null) {
            try {
                int mRowsUpdated = getContentResolver().update(
                        KarmaContract.KARMA_ENTITY_CONTENT_URI,   // the user dictionary content URI
                        values,                                   // the columns to update
                        selection,                                // the column to select on
                        selectionArgs                             // the value to compare to
                );
                if (mRowsUpdated <= 0) {
                    Log.e(LOG_TAG, "No such row with that uri");
                } else {
                    success = true;
                }
            } catch (SQLiteConstraintException e) {
                Log.e(LOG_TAG, e.getLocalizedMessage());
            } finally {
                Log.d(LOG_TAG, "Karma update operation success="+success);
            }
        }
    }

    private void insertKarma(Bundle extras) {
        ContentValues values = extras.getParcelable(KARMA_CONTENT_VALUE);
        if (values != null) {
            Uri uri = getContentResolver().insert(
                    KarmaContract.KARMA_ENTITY_CONTENT_URI,  // The content URI of the words table
                    values);                       // The sort order for the returned rows
            Log.d(LOG_TAG, "Karma Inserted with uri="+uri);
        } else {
            Log.e(LOG_TAG, "Content value is empty or corrupt");
        }
    }

    private void deleteKarma(Bundle extras) {
        boolean success = false;
        String selection = extras.getString(KARMA_SELECTION);
        String[] selectionArgs = extras.getStringArray(KARMA_SELECTION_ARGS);
        try {
            int mRowsDeleted = getContentResolver().delete(
                    KarmaContract.KARMA_ENTITY_CONTENT_URI,   // the user dictionary content URI
                    selection,                                // the column to select on
                    selectionArgs                             // the value to compare to
            );
            if (mRowsDeleted <= 0) {
                Log.e(LOG_TAG, "No such row with that uri");
            } else {
                success = true;
            }
        } catch (SQLiteConstraintException e) {
            Log.e(LOG_TAG, e.getLocalizedMessage());
        } finally {
            Log.d(LOG_TAG, "Karma delete operation success="+success);
        }
    }
}