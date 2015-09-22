package com.demo.vramachandran.karma.model.content.provider;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;

import com.demo.vramachandran.karma.model.database.KarmaDbHelper;

/**
 * Created by vramachandran on 9/18/2015.
 * Content Provider defined for Karma
 */
public class KarmaProvider extends ContentProvider {

    private KarmaDbHelper mDbHelper;

    // Creates a UriMatcher object.
    private static final UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    public static final int VALID_KARMA_TABLE_URI = 1;

    public static final int VALID_KARMA_ITEM_URI = 2;

    public static final String CONTENT_TYPE_KARMA_ENTITIES = ContentResolver.CURSOR_DIR_BASE_TYPE
            + "/" + KarmaContract.KarmaEntry.TABLE_NAME;

    public static final String CONTENT_TYPE_KARMA_ENTITY = ContentResolver.CURSOR_ITEM_BASE_TYPE
            + "/" + KarmaContract.KarmaEntry.TABLE_NAME;

    static {
        URI_MATCHER.addURI(KarmaContract.AUTHORITY, KarmaContract.KarmaEntry.TABLE_NAME, VALID_KARMA_TABLE_URI);

        URI_MATCHER.addURI(KarmaContract.AUTHORITY, KarmaContract.KarmaEntry.TABLE_NAME + "/#", VALID_KARMA_ITEM_URI);
    }

    @Override
    public boolean onCreate() {
        mDbHelper = new KarmaDbHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        switch (URI_MATCHER.match(uri)) {
            case VALID_KARMA_TABLE_URI:
                if (TextUtils.isEmpty(sortOrder)) sortOrder = "_ID ASC";
                break;

            case VALID_KARMA_ITEM_URI:
                selection = selection + "_ID = " + uri.getLastPathSegment();
                break;

            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        // call the code to actually do the query
        return mDbHelper.karmaQuery(projection, selection, selectionArgs, sortOrder);
    }

    @Override
    public String getType(Uri uri) {
        switch (URI_MATCHER.match(uri)) {
            // If the incoming URI was for all of karma_entity
            case VALID_KARMA_TABLE_URI:
                return CONTENT_TYPE_KARMA_ENTITIES;
            // If the incoming URI was for a single row
            case VALID_KARMA_ITEM_URI:
                return CONTENT_TYPE_KARMA_ENTITY;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        switch (URI_MATCHER.match(uri)) {
            // If the incoming URI was for all of karma_entity
            case VALID_KARMA_TABLE_URI:
                long id = mDbHelper.addKarma(values);
                Uri entityUri = ContentUris.withAppendedId(KarmaContract.KARMA_ENTITY_CONTENT_URI, id);
                getContext().getContentResolver().notifyChange(KarmaContract.KARMA_ENTITY_CONTENT_URI, null);
                return entityUri;
            default:
                //TODO: case VALID_KARMA_ITEM_URI
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        switch (URI_MATCHER.match(uri)) {
            // If the incoming URI was for all of karma_entity
            case VALID_KARMA_TABLE_URI:
                getContext().getContentResolver().notifyChange(KarmaContract.KARMA_ENTITY_CONTENT_URI, null);
                return mDbHelper.deleteKarma(selection, selectionArgs);
            default:
                //TODO: case VALID_KARMA_ITEM_URI
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        switch (URI_MATCHER.match(uri)) {
            // If the incoming URI was for all of karma_entity
            case VALID_KARMA_TABLE_URI:
                getContext().getContentResolver().notifyChange(KarmaContract.KARMA_ENTITY_CONTENT_URI, null);
                return mDbHelper.updateKarma(values, selection, selectionArgs);
            case VALID_KARMA_ITEM_URI:
                selection = selection + "_ID = " + uri.getLastPathSegment();
                getContext().getContentResolver().notifyChange(KarmaContract.KARMA_ENTITY_CONTENT_URI, null);
                return mDbHelper.updateKarma(values, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }
}
