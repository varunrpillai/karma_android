package com.demo.vramachandran.karma.model;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.Date;

/**
 * Created by vramachandran on 9/18/2015.
 */
public interface KarmaRepository {
    /**
     * Persist the added Karma item
     * @param itemDesc itemDesc of item to be added
     */
    void addKarma(String itemDesc);

    /**
     * Update all Karma item with the provide values
     */
    void updateKarma(ContentValues values);

    /**
     * Update specified Karma item with the provide values
     */
    void updateKarma(long karmaId, ContentValues values);

    /**
     * Remove specified Karma item
     */
    void removeKarma(long karmaId);

    /**
     * Retrieve all karma items
     */
    Cursor getKarmaItems();

    /**
     * Get the number of persisted karma items
     */
    int getCount();

    /**
     * Get the number of karma items that are marked done
     */
    int getDoneCount();

    /**
     * Get the date of latest persistance occured
     */
    Date getLatestDate();
}
