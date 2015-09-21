package com.demo.vramachandran.karma.presenter;

/**
 * Created by vramachandran on 9/19/2015.
 */
public interface ListPresenter {
    public void deleteItem(long id);

    public void markDone(long id);

    public void markPending(long id);

    void reload();
}
