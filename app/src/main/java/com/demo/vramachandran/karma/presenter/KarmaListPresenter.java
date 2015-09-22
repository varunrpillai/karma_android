package com.demo.vramachandran.karma.presenter;

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

import com.demo.vramachandran.karma.model.KarmaRepository;
import com.demo.vramachandran.karma.model.content.provider.KarmaContract;
import com.demo.vramachandran.karma.model.KarmaCursorLoader;
import com.demo.vramachandran.karma.view.KarmaFragment;

/**
 * Created by vramachandran on 9/18/2015.
 * Adapter for ListView fragment and data model
 */
public class KarmaListPresenter implements ListPresenter, LoaderManager.LoaderCallbacks<Cursor> {
    public static final int LOADER_ID = 1;
    private final KarmaRepository mKarmaData;
    private final Context mContext;
    private CursorAdapter mAdapter;
    private ListView mListView;
    private KarmaFragment.FragmentInteractionListener mListener;
    private KarmaCursorLoader mLoader;

    public KarmaListPresenter(KarmaFragment.FragmentInteractionListener listener,
                              Activity activity, ListView listView) {
        mContext = (Context) activity;

        mListener = listener;
        mKarmaData = listener.getKarmaData();

        //Setup adapter and list. Then initiate a list view data load
        mAdapter = new KarmaCursorAdapter(mContext, null, 0, this);
        mListView = listView;
        mListView.setAdapter(mAdapter);
        reload();

        //initiate loader
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
        ContentValues values = new ContentValues();
        values.put(KarmaContract.KarmaEntry.COLUMN_NAME_KARMA_STATE, KarmaContract.KarmaItemState.DONE);
        values.put(KarmaContract.KarmaEntry.COLUMN_NAME_UPDATE_TIME_STAMP, System.currentTimeMillis());
        mKarmaData.updateKarma(id, values);
    }

    public void markPending(long id) {
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
