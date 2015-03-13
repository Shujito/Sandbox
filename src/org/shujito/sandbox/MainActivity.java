package org.shujito.sandbox;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class MainActivity extends Activity implements OnClickListener
{
    public static final String TAG = MainActivity.class.getSimpleName();
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);
        this.findViewById(R.id.btn_start).setOnClickListener(this);
        this.findViewById(R.id.btn_stop).setOnClickListener(this);
    }
    
    @Override
    public void onClick(View v)
    {
        if (v.getId() == R.id.btn_start)
        {
            this.startService(new Intent(this, FloatingViewService.class));
        }
        if (v.getId() == R.id.btn_stop)
        {
            this.stopService(new Intent(this, FloatingViewService.class));
        }
    }
}
