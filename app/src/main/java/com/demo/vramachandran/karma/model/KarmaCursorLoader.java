package com.demo.vramachandran.karma.model;

import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;

/**
 * Created by vramachandran on 9/18/2015.
 * Class facilitated for asynchronous db query operation
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
        return mKarmaData.getKarmaItems();
    }
}
