package org.shujito.sandbox.activities;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

public class TouhousActivity extends ListActivity
{
    public static final String TAG = TouhousActivity.class.getSimpleName();
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }
    
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id)
    {
    }
}
