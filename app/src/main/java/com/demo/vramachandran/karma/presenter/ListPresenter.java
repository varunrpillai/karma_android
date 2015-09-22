package com.demo.vramachandran.karma.presenter;

/**
 * Created by vramachandran on 9/19/2015.
 */
public interface ListPresenter {
    /**
     * Delete the selected list item
     */
    void deleteItem(long id);

    /**
     * Mark the current list item as done
     */
    void markDone(long id);

    /**
     * Mark the current list item as pending
     */
    void markPending(long id);

    /**
     * Reload the list
     */
    void reload();
}
