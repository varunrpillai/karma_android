package com.demo.vramachandran.karma.presenter;

/**
 * Created by vramachandran on 9/19/2015.
 */
public interface KarmaObserver {
    /**
     * When query result is available from Karma_entity table
     */
    public void onKarmaDataAvailable();
}
