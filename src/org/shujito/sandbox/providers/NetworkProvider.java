package org.shujito.sandbox.providers;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

public final class NetworkProvider extends ContentProvider
{
    public static final String TAG = NetworkProvider.class.getSimpleName();
    
    @Override
    public boolean onCreate()
    {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public String getType(Uri uri)
    {
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