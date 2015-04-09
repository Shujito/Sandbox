package org.shujito.sandbox.activities;

import org.shujito.sandbox.GLRenderer;

import android.annotation.TargetApi;
import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

public class GLESActivity extends Activity
{
    public static final String TAG = GLESActivity.class.getSimpleName();
    private GLSurfaceView mGlView;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.mGlView = new GLSurfaceView(this);
        this.mGlView.setRenderer(new GLRenderer());
        //this.mGlView.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
        this.setContentView(this.mGlView);
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
    
    @Override
    protected void onResume()
    {
        super.onResume();
        this.mGlView.onResume();
    }
    
    @Override
    protected void onPause()
    {
        this.mGlView.onPause();
        super.onPause();
    }
}
