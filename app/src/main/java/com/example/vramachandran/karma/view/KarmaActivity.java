package com.example.vramachandran.karma.view;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.example.vramachandran.karma.BuildConfig;
import com.example.vramachandran.karma.settings.KarmaSettings;
import com.example.vramachandran.karma.model.KarmaDataModel;
import com.example.vramachandran.karma.model.KarmaRepository;
import com.example.vramachandran.karma.presenter.KarmaObservable;
import com.example.vramachandran.karma.R;
import com.example.vramachandran.karma.utils.DialogUtils;


public class KarmaActivity extends AppCompatActivity implements KarmaFragment.OnFragmentInteractionListener {

    public static final String KARMA_DIALOG_TAG = "karma_dialog";
    private KarmaRepository mKarmaData = null;
    private KarmaObservable mKarmaObservable;
    private KarmaListFragment mListFragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                    .detectDiskReads()
                    .detectDiskWrites()
                    .detectNetwork()   // or .detectAll() for all detectable problems
                    .penaltyLog()
                    .build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                    .detectLeakedSqlLiteObjects()
                    .detectLeakedClosableObjects()
                    .penaltyLog()
                    .penaltyDeath()
                    .build());
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_karma);
        KarmaSettings settings = new KarmaSettings(this);
        if (settings.needHelpDialog()) {
            showHelpDialog();
        }
    }

    private void showHelpDialog() {
        DialogUtils.KarmaDialogFragment karmaDialog = (DialogUtils.KarmaDialogFragment) getFragmentManager().
                findFragmentByTag(KARMA_DIALOG_TAG);
        if (karmaDialog == null) {
            karmaDialog = DialogUtils.KarmaDialogFragment.newInstance();
        }

        //Set the dialog message
        karmaDialog.setDialogMessage(this.getResources().getString(R.string.dialogMessage));
        karmaDialog.setDialogTitle(this.getResources().getString(R.string.dialogTitle));

        if (!karmaDialog.isAdded()) {
            karmaDialog.show(getFragmentManager(), KARMA_DIALOG_TAG);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_karma, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_info) {
            showHelpDialog();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public KarmaRepository getKarmaData() {
        if (mKarmaData == null) {
            return new KarmaDataModel(this);
        }
        return mKarmaData;
    }

    @Override
    public KarmaObservable getKarmaObservable() {
        if (mKarmaObservable == null) {
            mKarmaObservable = new KarmaObservable();
        }
        return mKarmaObservable;
    }

    @Override
    public void reloadData() {
        if (mListFragment == null) {
            mListFragment = (KarmaListFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.fragment_show_karma);
        }
        mListFragment.reloadData();
    }
}
