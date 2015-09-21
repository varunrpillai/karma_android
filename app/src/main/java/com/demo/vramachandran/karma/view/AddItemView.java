package com.demo.vramachandran.karma.view;

/**
 * Created by vramachandran on 9/18/2015.
 */
public interface AddItemView {
    void clearKarmaText();

    void showItemError();

    void enableAddItemView(boolean b);

    void setEditTextHint(int count);

    void setProgressValue(int doneCount);
}
