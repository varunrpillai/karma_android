package com.demo.vramachandran.karma.view;

import android.app.WallpaperManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.demo.vramachandran.karma.R;
import com.demo.vramachandran.karma.presenter.AddItemPresenter;
import com.demo.vramachandran.karma.presenter.AddKarmaPresenter;

import java.io.IOException;


/**
 * A placeholder fragment containing a simple view.
 */
public class AddKarmaFragment extends KarmaFragment implements AddItemView, View.OnClickListener {

    private ImageButton mAddButton;
    private EditText mKarmaText;
    private AddItemPresenter mPresenter;
    private ProgressBar mProgressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add_karma, container, false);
        mAddButton = (ImageButton) rootView.findViewById(R.id.addButton);
        mAddButton.setOnClickListener(this);
        mKarmaText = (EditText) rootView.findViewById(R.id.newKarmaText);
        mKarmaText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mAddButton.setBackgroundResource(s.length() > 0 ?
                        R.drawable.ic_action_ic_add_teal :
                        R.drawable.ic_action_ic_add_grey);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mProgressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);
        mProgressBar.setMax(7);
        mPresenter = new AddKarmaPresenter(this, (OnFragmentInteractionListener) getActivity());
        return rootView;
    }

    @Override
    public void onClick(View v) {
        String karmaDesc = mKarmaText.getText().toString();
        if (karmaDesc.isEmpty()) {
            Toast.makeText(getActivity(), "Cannot add empty Karma", Toast.LENGTH_SHORT);
            return;
        }
        mPresenter.addItem(mKarmaText.getText().toString());
        clearKarmaText();
    }

    @Override
    public void clearKarmaText() {
        mKarmaText.setText("");
    }

    @Override
    public void showItemError() {

    }

    @Override
    public void enableAddItemView(boolean enabled) {
        mKarmaText.setEnabled(enabled);
        mAddButton.setEnabled(enabled);
    }

    @Override
    public void setEditTextHint(int count) {
        if (count < 7) {
            mKarmaText.setHint("Please enter your " + (count + 1) + "/7 Karma");
        } else {
            mKarmaText.setHint("You have entered all the 7 Karmas. Please manage it daily.");
        }
    }

    @Override
    public void setProgressValue(int doneCount) {
        SetWallPaperTask setWallPaperTask = new SetWallPaperTask();
        setWallPaperTask.execute(doneCount);
        mProgressBar.setProgress(doneCount);
    }



    private class SetWallPaperTask extends AsyncTask<Integer, Void, Void> {

        @Override
        protected Void doInBackground(Integer... params) {
            if (params.length < 1) {
                return null;
            }
            Integer doneCount = params[0];
            WallpaperManager myWallpaperManager
                    = WallpaperManager.getInstance(getActivity());
            try {
                switch (doneCount) {
                    case 0:
                        myWallpaperManager.setResource(R.drawable.garden1);
                        break;
                    case 1:
                        myWallpaperManager.setResource(R.drawable.garden2);
                        break;
                    case 2:
                        myWallpaperManager.setResource(R.drawable.garden3);
                        break;
                    case 3:
                        myWallpaperManager.setResource(R.drawable.garden4);
                        break;
                    case 4:
                        myWallpaperManager.setResource(R.drawable.garden5);
                        break;
                    case 5:
                        myWallpaperManager.setResource(R.drawable.garden6);
                        break;
                    case 6:
                        myWallpaperManager.setResource(R.drawable.garden7);
                        break;
                    case 7:
                        myWallpaperManager.setResource(R.drawable.garden77);
                        break;
                    default:
                        new IllegalArgumentException("done count is wrong");
                        break;
                }

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return null;
        }
    }
}
