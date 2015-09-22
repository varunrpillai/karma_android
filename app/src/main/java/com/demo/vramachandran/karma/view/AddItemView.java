package com.demo.vramachandran.karma.view;

/**
 * Created by vramachandran on 9/18/2015.
 */
public interface AddItemView {
    /**
     * Clears the edit field text
     */
    void clearKarmaText();

    /**
     * Enable/Disable item addition UI
     */
    void enableAddItemView(boolean enable);

    /**
     * Set the hint text for the edit field
     */
    void setEditTextHint(String hint);

    /**
     * Update the UI with the progress state
     * @param doneCount number of karmas that are marked done.
     */
    void setProgressState(int doneCount);
}
