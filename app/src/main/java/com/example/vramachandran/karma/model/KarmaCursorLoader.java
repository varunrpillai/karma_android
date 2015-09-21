package com.example.vramachandran.karma.model;

import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;

/**
 * Created by vramachandran on 9/18/2015.
 */
public class KarmaCursorLoader extends CursorLoader {
    private static final String TAG = "KarmaCursorLoader";
    private final KarmaRepository mKarmaData;

    public KarmaCursorLoader(Context context, KarmaRepository karmaData) {
        super(context);
        mKarmaData = karmaData;
    }

    @Override
    public Cursor loadInBackground() {
        // this is just a simple query, could be anything that gets a cursor
        return mKarmaData.getKarmaItems();
    }
}
