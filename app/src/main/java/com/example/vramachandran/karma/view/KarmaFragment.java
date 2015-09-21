package com.example.vramachandran.karma.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.example.vramachandran.karma.model.KarmaRepository;
import com.example.vramachandran.karma.presenter.KarmaObservable;


/**
 * A placeholder fragment containing a simple view.
 */
public class KarmaFragment extends Fragment {
    public interface OnFragmentInteractionListener {
        public KarmaRepository getKarmaData();

        public KarmaObservable getKarmaObservable();

        public void reloadData();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!(getActivity() instanceof OnFragmentInteractionListener)) {
            throw new IllegalArgumentException("Activity doesnt implement" +
                    " OnFragmentInteractionListener");
        }
    }
}
