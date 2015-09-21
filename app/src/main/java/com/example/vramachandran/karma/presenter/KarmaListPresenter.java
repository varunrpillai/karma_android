package com.example.vramachandran.karma.presenter;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Loader;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.widget.CursorAdapter;
import android.widget.ListView;

import com.example.vramachandran.karma.model.KarmaRepository;
import com.example.vramachandran.karma.model.content.provider.KarmaContract;
import com.example.vramachandran.karma.model.KarmaCursorLoader;
import com.example.vramachandran.karma.view.KarmaFragment;
import com.example.vramachandran.karma.view.KarmaListFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by vramachandran on 9/18/2015.
 */
public class KarmaListPresenter implements ListPresenter, LoaderManager.LoaderCallbacks<Cursor> {
    public static final int LOADER_ID = 1;
    private final KarmaRepository mKarmaData;
    private final Context mContext;
    private CursorAdapter mAdapter;
    private ListView mListView;
    private KarmaFragment.OnFragmentInteractionListener mListener;
    private KarmaCursorLoader mLoader;

    public KarmaListPresenter(KarmaListFragment karmaListFragment,
                              KarmaFragment.OnFragmentInteractionListener listener,
                              Activity activity, ListView listView) {
        mContext = (Context) activity;

        mListener = listener;
        mKarmaData = listener.getKarmaData();

        mAdapter = new KarmaCursorAdapter(mContext, null, 0, this);
        mListView = listView;
        mListView.setAdapter(mAdapter);

        activity.getLoaderManager().initLoader(LOADER_ID, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        mLoader = new KarmaCursorLoader(mContext, mKarmaData);
        return mLoader;
    }

    @Override
    public void onLoadFinished(final Loader<Cursor> loader, Cursor data) {
        mAdapter.swapCursor(data);
        if (data != null) {
            mListener.getKarmaObservable().notifyChange();
            ContentObserver cursorObserver = new ContentObserver(new Handler()) {
                @Override
                public void onChange(boolean selfChange) {
                    super.onChange(selfChange);
                    reload();
                }
            };
            data.registerContentObserver(cursorObserver);
            data.setNotificationUri(mContext.getContentResolver(), KarmaContract.KARMA_ENTITY_CONTENT_URI);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }

    public void deleteItem(long id) {
        mKarmaData.removeKarma(id);
    }

    public void markDone(long id) {
        // New value for one column
        ContentValues values = new ContentValues();
        values.put(KarmaContract.KarmaEntry.COLUMN_NAME_KARMA_STATE, KarmaContract.KarmaItemState.DONE);
        values.put(KarmaContract.KarmaEntry.COLUMN_NAME_UPDATE_TIME_STAMP, System.currentTimeMillis());
        mKarmaData.updateKarma(id, values);
    }

    public void markPending(long id) {
        // New value for one column
        ContentValues values = new ContentValues();
        values.put(KarmaContract.KarmaEntry.COLUMN_NAME_KARMA_STATE, KarmaContract.KarmaItemState.PENDING);
        values.put(KarmaContract.KarmaEntry.COLUMN_NAME_UPDATE_TIME_STAMP, System.currentTimeMillis());
        mKarmaData.updateKarma(id, values);
    }

    @Override
    public void reload() {
        if (mLoader != null) {
            mLoader.forceLoad();
        }
    }
}
