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
        return uri.getPathSegments().get(0);
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
        throw new UnsupportedOperationException();
    }
    
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder)
    {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs)
    {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs)
    {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public int bulkInsert(Uri uri, ContentValues[] values)
    {
        throw new UnsupportedOperationException();
    }
}
