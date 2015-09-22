package com.demo.vramachandran.karma.presenter;

import android.content.ContentValues;
import android.content.Context;

import com.demo.vramachandran.karma.R;
import com.demo.vramachandran.karma.model.KarmaRepository;
import com.demo.vramachandran.karma.model.content.provider.KarmaContract;
import com.demo.vramachandran.karma.utils.DateUtils;
import com.demo.vramachandran.karma.view.AddItemView;
import com.demo.vramachandran.karma.view.KarmaFragment;

import java.util.Date;

/**
 * Created by vramachandran on 9/18/2015.
 * Adapter between Add View and Data Model.
 */
public class AddKarmaPresenter implements AddItemPresenter, KarmaObserver {

    private final KarmaRepository mKarmaData;
    private final AddItemView mKarmaView;
    private final Context mContext;

    public AddKarmaPresenter(AddItemView view,
                             KarmaFragment.FragmentInteractionListener activity,
                             Context context) {
        mKarmaView = view;
        mKarmaData = activity.getKarmaData();
        mContext = context;
        activity.getKarmaObservable().registerObserver(this);//TODO : Unregister it
    }


    @Override
    public void addItem(String itemDesc) {
        if (itemDesc == null) {
            throw new NullPointerException();
        }
        if (itemDesc.trim().isEmpty()) return;//TODO:show error
        mKarmaData.addKarma(itemDesc);
    }

    @Override
    public void onKarmaDataAvailable() {
        int count = mKarmaData.getCount();
        mKarmaView.enableAddItemView(count < 7);
        String hintText = count < 7
                ? String.format(mContext.getString(R.string.allowHintText), count + 1)
                : mContext.getString(R.string.disallowHintText);
        mKarmaView.setEditTextHint(hintText);
        mKarmaView.setProgressState(mKarmaData.getDoneCount());
        resetStateForNewDate();
    }

    private void resetStateForNewDate() {
        Date lastUsed = mKarmaData.getLatestDate();
        Date today = new Date(System.currentTimeMillis());

        if (lastUsed != null && today != null) {
            long difference = DateUtils.daysBetween(lastUsed, today);

            if (difference > 0) {
                ContentValues values = new ContentValues();
                values.put(KarmaContract.KarmaEntry.COLUMN_NAME_KARMA_STATE, KarmaContract.KarmaItemState.PENDING);
                mKarmaData.updateKarma(values);
            }
        }
    }
}
