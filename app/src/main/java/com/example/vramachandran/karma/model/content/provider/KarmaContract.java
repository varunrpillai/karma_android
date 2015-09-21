package com.example.vramachandran.karma.model.content.provider;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by vramachandran on 9/18/2015.
 */
public final class KarmaContract {

    public static final String AUTHORITY = "com.example.vramachandran.karma.provider";

    public static final String COLUMN_NAME_NULLABLE = null;
    /**
     * URI at which Karma entities (List Items) for a profile may be accessed.
     */
    public static final Uri KARMA_ENTITY_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/"
            + KarmaEntry.TABLE_NAME);

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public KarmaContract() {
    }

    /* Inner class that defines the table contents */
    public static abstract class KarmaEntry implements BaseColumns {
        public static final String TABLE_NAME = "karma_entity";
        public static final String COLUMN_NAME_KARMA_DESC = "karma_desc";
        public static final String COLUMN_NAME_KARMA_STATE = "karma_state";
        public static final String COLUMN_NAME_UPDATE_TIME_STAMP = "karma_update_time_stamp";
    }

    public static final class KarmaItemState {
        public static final long PENDING                       = 0;
        public static final long DONE                          = 1L << 1;
    }
}
