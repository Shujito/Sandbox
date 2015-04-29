package org.shujito.sandbox.providers;

import org.shujito.sandbox.db.Database;
import org.shujito.sandbox.db.schema.CommonColumns;
import org.shujito.sandbox.db.schema.TouhouColumns;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

public final class Provider extends ContentProvider
{
    public static final String TAG = Provider.class.getSimpleName();
    private static final int TOUHOU_LIST = 0;
    private static final int TOUHOU_ID = 1;
    private static final UriMatcher URI_MATCHER;
    static
    {
        URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
        URI_MATCHER.addURI(CommonColumns.AUTHORITY, getPath(TouhouColumns.CONTENT_URI), TOUHOU_LIST);
        URI_MATCHER.addURI(CommonColumns.AUTHORITY, getPath(TouhouColumns.CONTENT_URI) + "/*", TOUHOU_ID);
    }
    private Database mDatabase = null;
    
    private static String getPath(Uri uri)
    {
        if (uri.getPathSegments().size() > 0)
            return uri.getPathSegments().get(0);
        return null;
    }
    
    @Override
    public boolean onCreate()
    {
        this.mDatabase = new Database(this.getContext().getApplicationContext());
        return true;
    }
    
    @Override
    public String getType(Uri uri)
    {
        switch (URI_MATCHER.match(uri))
        {
            case TOUHOU_LIST:
                return TouhouColumns.CONTENT_TYPE;
            case TOUHOU_ID:
                return TouhouColumns.CONTENT_TYPE_ITEM;
        }
        throw new UnsupportedOperationException();
    }
    
    @Override
    public Uri insert(Uri uri, ContentValues values)
    {
        long id = this.mDatabase.getWritableDatabase().insert(getPath(uri), null, values);
        return uri.buildUpon().appendPath(String.valueOf(id)).build();
    }
    
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder)
    {
        //return this.mDatabase.getReadableDatabase().rawQuery(selection, selectionArgs);
        return this.mDatabase.getReadableDatabase().query(getPath(uri), projection, selection, selectionArgs, null, null, sortOrder);
    }
    
    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs)
    {
        return this.mDatabase.getWritableDatabase().update(getPath(uri), values, selection, selectionArgs);
    }
    
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs)
    {
        return this.mDatabase.getWritableDatabase().delete(getPath(uri), selection, selectionArgs);
    }
    
    @Override
    public int bulkInsert(Uri uri, ContentValues[] values)
    {
        int count = 0;
        try
        {
            this.mDatabase.getWritableDatabase().beginTransaction();
            for (ContentValues value : values)
            {
                this.mDatabase.getWritableDatabase().insert(getPath(uri), null, value);
                count++;
            }
        }
        finally
        {
            this.mDatabase.getWritableDatabase().endTransaction();
        }
        return count;
    }
}
