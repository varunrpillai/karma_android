package com.example.vramachandran.karma.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.vramachandran.karma.R;
import com.example.vramachandran.karma.presenter.KarmaListPresenter;
import com.example.vramachandran.karma.presenter.ListPresenter;


/**
 * A placeholder fragment containing a simple view.
 */
public class KarmaListFragment extends KarmaFragment implements ShowItemView {

    private ListView mListView;
    private ListPresenter mPresenter;
    private ImageView mDeleteIcon;
    private ImageView mDoneIcon;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list_karma, container, false);
        mListView = (ListView) rootView.findViewById(R.id.karmaList);
        mPresenter = new KarmaListPresenter(this,
                (OnFragmentInteractionListener) getActivity(),
                getActivity(),
                mListView);

        return rootView;
    }

    public void reloadData() {
        mPresenter.reload();
    }
}
