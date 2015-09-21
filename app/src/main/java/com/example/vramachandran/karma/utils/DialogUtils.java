package com.example.vramachandran.karma.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import com.example.vramachandran.karma.R;

/**
 * Created by vramachandran on 9/20/2015.
 */
public class DialogUtils {
    public static class KarmaDialogFragment extends DialogFragment {
        private String mDialogMessage;
        private String mDialogTitle;

        public void setDialogMessage(String dialogMessage) {
            mDialogMessage = dialogMessage;
        }

        public void setDialogTitle(String dialogTitle) {
            mDialogTitle = dialogTitle;
        }

        public static KarmaDialogFragment newInstance() {
            KarmaDialogFragment dialog = new KarmaDialogFragment();
            return dialog;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            if (mDialogTitle != null) {
                builder.setTitle(mDialogTitle);
            }
            if (mDialogMessage != null) {
                builder.setMessage(mDialogMessage);
            }
       /*     builder.setPositiveButton(R.string.ok,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //do nothing
                        }
                    });*/
            builder.setNegativeButton(R.string.ok,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        }
                    });
            return builder.create();
        }
    }
}
