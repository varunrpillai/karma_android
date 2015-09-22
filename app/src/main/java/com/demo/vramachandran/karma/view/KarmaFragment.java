package com.demo.vramachandran.karma.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.demo.vramachandran.karma.model.KarmaRepository;
import com.demo.vramachandran.karma.presenter.KarmaObservable;


/**
 * A fragment which ensures that the subclass fragment
 * does have access to the FragmentInteractionListener methods from it's activity
 */
public class KarmaFragment extends Fragment {
    public interface FragmentInteractionListener {
        /**
         * Get an instance of the data model
         */
        KarmaRepository getKarmaData();

        /**
         * Get an instance of observable which indicates about data availability
         */
        KarmaObservable getKarmaObservable();

        /**
         * Reload the karma data which feeds list
         */
        void reloadData();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!(getActivity() instanceof FragmentInteractionListener)) {
            throw new IllegalArgumentException("Activity doesnt implement" +
                    " OnFragmentInteractionListener");
        }
    }
}
