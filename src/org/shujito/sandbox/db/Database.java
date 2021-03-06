package org.shujito.sandbox.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database extends SQLiteOpenHelper
{
    public static final String TAG = Database.class.getSimpleName();
    public static final int VERSION = 0x0001;
    
    public Database(Context context)
    {
        super(context, context.getPackageName() + ".db3", null, VERSION);
    }
    
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("create view if not exists unixtime as select cast(((julianday('now') - julianday('1970-01-01')) * 86400000) as integer) as unixtime");
        db.execSQL("create table if not exists touhous("
            + "uuid text primary key on conflict replace not null on conflict ignore default (hex(randomblob(16))),"
            + "created_at integer not null on conflict ignore default(cast(((julianday('now') - julianday('1970-01-01')) * 86400000) as integer)),"
            + "updated_at integer not null on conflict ignore default(cast(((julianday('now') - julianday('1970-01-01')) * 86400000) as integer)),"
            + "deleted_at integer,"
            + "name text not null on conflict fail,"
            + "last_name text not null on conflict fail,"
            + "title text not null on conflict fail"
            + ")");
    }
    
    @Override
    public void onOpen(SQLiteDatabase db)
    {
        super.onOpen(db);
        if (!db.isReadOnly())
            db.execSQL("pragma foreign_keys=on;");
    }
    
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("create temporary table temp_touhous as select * from touhous");
        db.execSQL("drop table touhous");
        this.onCreate(db);
        // TODO: build insert using pragma table_info(table)
    }
}
