package org.shujito.sandbox.activities;

import org.shujito.sandbox.adapters.ActivitiesAdapter;

import android.app.ListActivity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
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
        this.setListAdapter(new ActivitiesAdapter(this));
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
