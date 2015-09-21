package com.example.vramachandran.karma.presenter;

import android.content.ContentValues;

import com.example.vramachandran.karma.model.KarmaRepository;
import com.example.vramachandran.karma.model.content.provider.KarmaContract;
import com.example.vramachandran.karma.utils.DateUtils;
import com.example.vramachandran.karma.view.AddItemView;
import com.example.vramachandran.karma.view.KarmaFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by vramachandran on 9/18/2015.
 */
public class AddKarmaPresenter implements AddItemPresenter, KarmaObserver {

    private final KarmaRepository mKarmaData;
    private final AddItemView mKarmaView;

    public AddKarmaPresenter(AddItemView view, KarmaFragment.OnFragmentInteractionListener activity) {
        mKarmaView = view;
        mKarmaData = activity.getKarmaData();
        activity.getKarmaObservable().registerObserver(this);//TODO : Unregister it
    }


    @Override
    public void addItem(String itemDesc) {
        if (itemDesc == null) {
            throw new NullPointerException();
        }
        if (itemDesc.isEmpty()) mKarmaView.showItemError();
        mKarmaData.addKarma(itemDesc);
    }

    @Override
    public void onKarmaDataAvailable() {
        int count = mKarmaData.getCount();
        mKarmaView.enableAddItemView(count < 7);
        mKarmaView.setEditTextHint(count);
        mKarmaView.setProgressValue(mKarmaData.getDoneCount());
        resetStateForNewDate();
    }

    private void resetStateForNewDate() {
        Date lastUsed = mKarmaData.getLatestDate();
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm");
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
