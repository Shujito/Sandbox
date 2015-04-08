package org.shujito.sandbox.activities;

import org.shujito.sandbox.GLRenderer;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

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
