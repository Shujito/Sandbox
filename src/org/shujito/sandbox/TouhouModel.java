package org.shujito.sandbox;

import java.util.Date;

import android.content.ContentValues;
import android.database.Cursor;

public class TouhouModel
{
    public static final String TAG = TouhouModel.class.getSimpleName();
    public static final String TOUHOUS = "touhous";
    public static final String UUID = "uuid";
    public static final String CREATED_AT = "created_at";
    public static final String UPDATED_AT = "updated_at";
    public static final String DELETED_AT = "deleted_at";
    public static final String NAME = "name";
    public static final String LAST_NAME = "last_name";
    public static final String TITLE = "title";
    public final String uuid;
    public final Date createdAt;
    public final Date updatedAt;
    public final Date deletedAt;
    public String name;
    public String lastName;
    public String title;
    
    public TouhouModel()
    {
        this.uuid = null;
        this.createdAt = null;
        this.updatedAt = null;
        this.deletedAt = null;
    }
    
    public TouhouModel(Cursor cursor)
    {
        if (cursor == null)
            throw new NullPointerException();
        this.uuid = cursor.getString(cursor.getColumnIndex(UUID));
        this.createdAt = new Date(cursor.getLong(cursor.getColumnIndex(CREATED_AT)));
        this.updatedAt = new Date(cursor.getLong(cursor.getColumnIndex(UPDATED_AT)));
        this.deletedAt = new Date(cursor.getLong(cursor.getColumnIndex(DELETED_AT)));
        this.name = cursor.getString(cursor.getColumnIndex(NAME));
        this.lastName = cursor.getString(cursor.getColumnIndex(LAST_NAME));
        this.title = cursor.getString(cursor.getColumnIndex(TITLE));
    }
    
    public ContentValues toValues()
    {
        ContentValues values = new ContentValues();
        if (this.uuid != null)
            values.put(UUID, this.uuid);
        if (this.createdAt != null)
            values.put(CREATED_AT, this.createdAt.getTime());
        if (this.updatedAt != null)
            values.put(UPDATED_AT, this.updatedAt.getTime());
        if (this.deletedAt != null)
            values.put(DELETED_AT, this.deletedAt.getTime());
        values.put(NAME, this.name);
        values.put(LAST_NAME, this.lastName);
        values.put(TITLE, this.title);
        return values;
    }
}
