package org.shujito.sandbox;

import android.app.Application;

public class SandboxApplication extends Application
{
    public static final String TAG = SandboxApplication.class.getSimpleName();
    private static SandboxApplication instance;
    
    public static SandboxApplication getInstance()
    {
        return instance;
    }
    
    private Database mDatabase;
    
    @Override
    public void onCreate()
    {
        super.onCreate();
        instance = this;
        this.mDatabase = new Database(this);
    }
    
    public Database getDatabase()
    {
        return this.mDatabase;
    }
}
