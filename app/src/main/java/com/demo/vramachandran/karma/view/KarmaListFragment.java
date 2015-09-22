package com.demo.vramachandran.karma.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.demo.vramachandran.karma.R;
import com.demo.vramachandran.karma.presenter.KarmaListPresenter;
import com.demo.vramachandran.karma.presenter.ListPresenter;


/**
 * Fragment that is responsible for the list view
 */
public class KarmaListFragment extends KarmaFragment implements ShowItemView {

    private ListView mListView;
    private ListPresenter mPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list_karma, container, false);
        mListView = (ListView) rootView.findViewById(R.id.karmaList);
        mPresenter = new KarmaListPresenter((FragmentInteractionListener) getActivity(),
                getActivity(),
                mListView);

        return rootView;
    }

    public void reloadData() {
        mPresenter.reload();
    }
}
