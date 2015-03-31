package org.shujito.sandbox;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

public class SurfaceActivity extends Activity
{
    public static final String TAG = SurfaceActivity.class.getSimpleName();
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN)
        {
        }
        this.setContentView(R.layout.activity_surface);
    }
    
    @Override
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void onWindowFocusChanged(boolean hasFocus)
    {
        super.onWindowFocusChanged(hasFocus);
        int uiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
            uiVisibility |= View.SYSTEM_UI_FLAG_FULLSCREEN;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
            uiVisibility |= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        this.getWindow().getDecorView().setSystemUiVisibility(uiVisibility);
    }
    
    
}
