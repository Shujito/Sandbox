package org.shujito.sandbox.db.schema;

import android.content.ContentResolver;
import android.net.Uri;

public interface TouhouColumns extends CommonColumns
{
    // fields
    public static final String TABLE = "touhous";
    public static final String UUID = "uuid";
    public static final String NAME = "name";
    public static final String LAST_NAME = "last_name";
    public static final String TITLE = "title";
    // provider things
    public static final Uri CONTENT_URI = Uri.withAppendedPath(CommonColumns.CONTENT_URI, TouhouColumns.TABLE);
    public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/vnd." + AUTHORITY + "." + TouhouColumns.TABLE;
    public static final String CONTENT_TYPE_ITEM = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/vnd." + AUTHORITY + "." + TouhouColumns.TABLE;
}
