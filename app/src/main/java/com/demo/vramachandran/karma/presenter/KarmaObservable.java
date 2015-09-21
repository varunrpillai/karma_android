package com.demo.vramachandran.karma.presenter;

import android.database.Observable;

/**
 * Created by vramachandran on 9/19/2015.
 */
public class KarmaObservable extends Observable<KarmaObserver> {
    /**
     * Notify {@code KarmaObserver} that an event changed.
     */
    public void notifyChange() {
        synchronized (mObservers) {
            int size = mObservers.size();
            for (int x = (size - 1); x >= 0; --x) {
                mObservers.get(x).onKarmaDataAvailable();
            }
        }
    }
}
