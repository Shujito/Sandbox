package org.shujito.sandbox;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class SurfaceActivity extends Activity implements OnClickListener
{
    public static final String TAG = SurfaceActivity.class.getSimpleName();
    private Surface mSurface;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_surface);
        this.mSurface = (Surface) this.findViewById(R.id.surface);
        this.findViewById(R.id.btn_toggle_x).setOnClickListener(this);
        this.findViewById(R.id.btn_toggle_y).setOnClickListener(this);
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
    
    /* OnClickListener */
    @Override
    public void onClick(View v)
    {
        if (v.getId() == R.id.btn_toggle_x)
        {
            this.mSurface.toggleX();
        }
        if (v.getId() == R.id.btn_toggle_y)
        {
            this.mSurface.toggleY();
        }
    }
}
