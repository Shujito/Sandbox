package org.shujito.sandbox;

import android.app.ListActivity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;

public class MainActivity extends ListActivity
{
    public static final String TAG = MainActivity.class.getSimpleName();
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        try
        {
            this.setListAdapter(new ActivitiesAdapter(this));
        }
        catch (NameNotFoundException e)
        {
            throw new RuntimeException(e);
        }
    }
    
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id)
    {
        ActivityInfo ainfo = ActivityInfo.class.cast(l.getAdapter().getItem(position));
        if (TextUtils.equals(ainfo.name, this.getClass().getName()))
        {
            return;
        }
        Intent intent = new Intent();
        intent.setClassName(ainfo.applicationInfo.packageName, ainfo.name);
        this.startActivity(intent);
    }
}
