package com.demo.vramachandran.karma.presenter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.demo.vramachandran.karma.R;
import com.demo.vramachandran.karma.model.content.provider.KarmaContract;

/**
 * Created by vramachandran on 9/19/2015.
 */
public class KarmaCursorAdapter extends CursorAdapter {
    private final ListPresenter mListPresenter;

    public KarmaCursorAdapter(Context context, Cursor cursor, int flags, ListPresenter presenter) {
        super(context, cursor, 0);
        mListPresenter = presenter;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.karma_list_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        if (view != null && cursor != null) {
            // Associate image views with id
            long id = cursor.getLong(cursor.getColumnIndexOrThrow(KarmaContract.KarmaEntry._ID));
            final long state = cursor.getLong(cursor.getColumnIndexOrThrow(KarmaContract.KarmaEntry.COLUMN_NAME_KARMA_STATE));
            final ImageView doneIcon = (ImageView) view.findViewById(R.id.primary_icon);

            if (state == KarmaContract.KarmaItemState.DONE) {
                doneIcon.setBackgroundResource(R.drawable.ic_action_ic_check_circle_teal);
            } else {
                doneIcon.setBackgroundResource(R.drawable.ic_action_ic_schedule_red);
            }
            doneIcon.setTag(id);
            doneIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Start flip animating the current image
                    final ScaleAnimation anim1 = new ScaleAnimation(1f, 0f, 1f, 1f, 50f, 50f);
                    anim1.setInterpolator(new LinearInterpolator());
                    anim1.setRepeatCount(0);
                    anim1.setDuration(200);
                    view.startAnimation(anim1);

                    long id = (long) view.getTag();

                    if (state == KarmaContract.KarmaItemState.DONE) {
                        mListPresenter.markPending(id);
                    } else {
                        mListPresenter.markDone(id);
                    }
                }
            });

            ImageView deleteIcon = (ImageView) view.findViewById(R.id.secondary_icon);
            deleteIcon.setTag(id);
            deleteIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    long id = (long) view.getTag();
                    mListPresenter.deleteItem(id);
                }
            });

            // Populate the text
            String karmaText = cursor.getString(cursor.getColumnIndexOrThrow(KarmaContract.KarmaEntry.COLUMN_NAME_KARMA_DESC));
            TextView karmaEntry = (TextView) view.findViewById(R.id.karma_desc_entry);
            karmaEntry.setText(karmaText);
        }
    }
}
