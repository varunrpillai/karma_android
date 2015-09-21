package com.example.vramachandran.karma.model;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.Date;

/**
 * Created by vramachandran on 9/18/2015.
 */
public interface KarmaRepository {
    public void addKarma(String item);

    void updateKarma(ContentValues values);

    public void updateKarma(long karmaId, ContentValues values);

    public void removeKarma(long karmaId);

    public Cursor getKarmaItems();

    public int getCount();

    int getDoneCount();

    Date getLatestDate();
}
