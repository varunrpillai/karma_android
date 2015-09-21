package com.demo.vramachandran.karma.model;

import android.app.IntentService;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.demo.vramachandran.karma.model.content.provider.KarmaContract;
import com.demo.vramachandran.karma.model.database.KarmaDbHelper;
import com.demo.vramachandran.karma.model.service.KarmaService;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by vramachandran on 9/18/2015.
 */
public class KarmaDataModel implements KarmaRepository {


    public static final String LOG_TAG = "KDM";
    private SQLiteOpenHelper mDbHelper;
    private Context mContext;
    public static final int INVALID_VALUE = -1;
    private static int sCount = INVALID_VALUE;
    private static int sDoneCount = INVALID_VALUE;
    private static Date sLastDate = null;
    public IntentService mService = null;

    public KarmaDataModel(Context context) {
        initializeDatabaseHelpers(context);
        mContext = context;
    }

    private IntentService getKarmaService() {
        if (mService == null) {
            mService = new KarmaService(LOG_TAG);
        }
        return mService;
    }

    protected void initializeDatabaseHelpers(Context context) {
        mDbHelper = new KarmaDbHelper(context);
    }

    @Override
    public void addKarma(String itemDesc) {
        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(KarmaContract.KarmaEntry.COLUMN_NAME_KARMA_DESC, itemDesc);
        values.put(KarmaContract.KarmaEntry.COLUMN_NAME_KARMA_STATE, KarmaContract.KarmaItemState.PENDING);
        values.put(KarmaContract.KarmaEntry.COLUMN_NAME_UPDATE_TIME_STAMP, System.currentTimeMillis());

        Intent karmaServiceIntent = new Intent();
        karmaServiceIntent.setAction(KarmaService.KarmaActions.INSERT);
        karmaServiceIntent.putExtra(KarmaService.KARMA_CONTENT_VALUE, values);
        karmaServiceIntent.setComponent(new ComponentName(mContext.getPackageName(),
                "com.example.vramachandran.karma.model.service.KarmaService"));
        mContext.startService(karmaServiceIntent);
    }

    @Override
    public void updateKarma(ContentValues values) {
        updateKarma(INVALID_VALUE, values);
    }

    @Override
    public void updateKarma(long karmaId, ContentValues values) {
        String selection = null;
        String[] selectionArgs = null;
        // Which row to update, based on the ID
        if (karmaId != INVALID_VALUE) {
            selection = KarmaContract.KarmaEntry._ID + "=?";
            selectionArgs = new String[]{String.valueOf(karmaId)};
            Intent karmaServiceIntent = new Intent();
            karmaServiceIntent.setAction(KarmaService.KarmaActions.UPDATE);
            karmaServiceIntent.putExtra(KarmaService.KARMA_CONTENT_VALUE, values);
            karmaServiceIntent.putExtra(KarmaService.KARMA_SELECTION, selection);
            karmaServiceIntent.putExtra(KarmaService.KARMA_SELECTION_ARGS, selectionArgs);
            karmaServiceIntent.setComponent(new ComponentName(mContext.getPackageName(),
                    "com.example.vramachandran.karma.model.service.KarmaService"));
            mContext.startService(karmaServiceIntent);
        }
    }

    @Override
    public void removeKarma(long karmaId) {
        // Define 'where' part of query.
        String selection = KarmaContract.KarmaEntry._ID + "=?";
        // Specify arguments in placeholder order.
        String[] selectionArgs = {String.valueOf(karmaId)};
        Intent karmaServiceIntent = new Intent();
        karmaServiceIntent.setAction(KarmaService.KarmaActions.DELETE);
        karmaServiceIntent.putExtra(KarmaService.KARMA_SELECTION, selection);
        karmaServiceIntent.putExtra(KarmaService.KARMA_SELECTION_ARGS, selectionArgs);
        karmaServiceIntent.setComponent(new ComponentName(mContext.getPackageName(),
                "com.example.vramachandran.karma.model.service.KarmaService"));
        mContext.startService(karmaServiceIntent);
    }

    @Override
    public Cursor getKarmaItems() {
        // Projection for loading
        String[] projection = {
                KarmaContract.KarmaEntry._ID,
                KarmaContract.KarmaEntry.COLUMN_NAME_KARMA_DESC,
                KarmaContract.KarmaEntry.COLUMN_NAME_KARMA_STATE,
                KarmaContract.KarmaEntry.COLUMN_NAME_UPDATE_TIME_STAMP
        };

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                KarmaContract.KarmaEntry.COLUMN_NAME_KARMA_DESC + " ASC";
        Cursor cursor = null;

        try {

            cursor = mContext.getContentResolver().query(
                    KarmaContract.KARMA_ENTITY_CONTENT_URI,
                    projection,
                    null,
                    null,
                    sortOrder
            );
            if (cursor != null) {
                sCount = cursor.getCount();
                sDoneCount = calculateDoneCount(cursor);
                sLastDate = calculateLastDate(cursor);
            }
        } catch (SQLiteConstraintException e) {
            Log.e(LOG_TAG, e.getLocalizedMessage());
        } finally {
            return cursor;
        }
    }

    private Date calculateLastDate(Cursor cursor) {
        Date lastDate = null;
        long latest = 0;
        if (cursor.moveToFirst()) {
            do {
                long date = cursor.getLong(cursor.getColumnIndex(KarmaContract.KarmaEntry.COLUMN_NAME_UPDATE_TIME_STAMP));
                if (date > latest) {
                    latest = date;
                }
            } while (cursor.moveToNext());
            SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm");
            lastDate = new Date(latest);
        }
        return lastDate;
    }

    private int calculateDoneCount(Cursor cursor) {
        int doneCount = 0;
        if (cursor.moveToFirst()) {
            do {
                long state = cursor.getLong(cursor.getColumnIndex(KarmaContract.KarmaEntry.COLUMN_NAME_KARMA_STATE));
                if ((state & KarmaContract.KarmaItemState.DONE) > 0) {
                    doneCount += 1;
                }
            } while (cursor.moveToNext());
        }
        return doneCount;
    }

    @Override
    public int getCount() {
        return sCount;
    }

    @Override
    public int getDoneCount() {
        return sDoneCount;
    }

    @Override
    public Date getLatestDate() {
        return sLastDate;
    }
}