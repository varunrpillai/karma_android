package com.demo.vramachandran.karma.model.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.demo.vramachandran.karma.model.content.provider.KarmaContract;

/**
 * Created by vramachandran on 9/18/2015.
 * The db helper interacts with the database
 */
public class KarmaDbHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Karma.db";
    private static final String TEXT_TYPE = " TEXT";
    private static final String INTEGER = " INTEGER";
    private static final String COMMA_SEP = ",";
    public static final String DEFAULT = " DEFAULT ";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + KarmaContract.KarmaEntry.TABLE_NAME + " (" +
                    KarmaContract.KarmaEntry._ID + INTEGER + " PRIMARY KEY" + COMMA_SEP +
                    KarmaContract.KarmaEntry.COLUMN_NAME_KARMA_DESC + TEXT_TYPE + COMMA_SEP +
                    KarmaContract.KarmaEntry.COLUMN_NAME_KARMA_STATE + INTEGER +
                    DEFAULT + KarmaContract.KarmaItemState.PENDING + COMMA_SEP +
                    KarmaContract.KarmaEntry.COLUMN_NAME_UPDATE_TIME_STAMP + INTEGER +
                    " )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + KarmaContract.KarmaEntry.TABLE_NAME;
    public static final int KARMA_NO_ID = -1;


    public KarmaDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public Cursor karmaQuery(String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        SQLiteDatabase db = getReadableDatabase();
        Cursor itemCursor = null;
        try {
            db.beginTransaction();
            itemCursor = db.query(
                    KarmaContract.KarmaEntry.TABLE_NAME,  // The table to query
                    projection,                               // The columns to return
                    selection,                                // The columns for the WHERE clause
                    selectionArgs,                            // The values for the WHERE clause
                    null,                                     // don't group the rows
                    null,                                     // don't filter by row groups
                    sortOrder                                 // The sort order
            );
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
            return itemCursor;
        }
    }

    public long addKarma(ContentValues values) {
        // Gets the data repository in write mode
        SQLiteDatabase db = getWritableDatabase();

        // Insert the new row, returning the primary key value of the new row
        long newRowId = KARMA_NO_ID;

        try {
            db.beginTransaction();
            newRowId = db.insert(
                    KarmaContract.KarmaEntry.TABLE_NAME,
                    KarmaContract.COLUMN_NAME_NULLABLE,
                    values);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
            return newRowId;
        }
    }

    public int deleteKarma(String selection, String[] selectionArgs) {
        // Gets the data repository in write mode
        SQLiteDatabase db = getWritableDatabase();
        int rowsAffected = 0;
        try {
            db.beginTransaction();
            // Issue SQL statement.
            rowsAffected = db.delete(KarmaContract.KarmaEntry.TABLE_NAME, selection, selectionArgs);

            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
            return rowsAffected;
        }
    }

    public int updateKarma(ContentValues values, String selection, String[] selectionArgs) {
        // Gets the data repository in write mode
        SQLiteDatabase db = getWritableDatabase();
        int rowsAffected = 0;
        try {
            db.beginTransaction();
            // Issue SQL statement.
            rowsAffected = db.update(KarmaContract.KarmaEntry.TABLE_NAME, values, selection, selectionArgs);

            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
            return rowsAffected;
        }
    }
}
