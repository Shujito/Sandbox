package org.shujito.sandbox.db.schema;

import android.net.Uri;
import android.provider.BaseColumns;

public interface CommonColumns extends BaseColumns
{
    public static final String AUTHORITY = "org.shujito.sandbox";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    public static final String CREATED_AT = "created_at";
    public static final String UPDATED_AT = "updated_at";
    public static final String DELETED_AT = "deleted_at";
}
