package org.shujito.sandbox;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database extends SQLiteOpenHelper
{
    public static final String TAG = Database.class.getSimpleName();
    
    public Database(Context context)
    {
        super(context, context.getPackageName() + ".db3", null, 1);
    }
    
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("create table touhous("
            + "uuid text primary key on conflict replace default (hex(randomblob(16))),"
            + "created_at integer not null on conflict ignore default(cast(((julianday('now') - julianday('1970-01-01')) * 86400000) as integer)),"
            + "updated_at integer not null on conflict ignore default(cast(((julianday('now') - julianday('1970-01-01')) * 86400000) as integer)),"
            + "deleted_at integer,"
            + "name text not null on conflict fail,"
            + "last_name text not null on conflict fail,"
            + "title text not null on conflict fail"
            + ")");
    }
    
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
    }
}
