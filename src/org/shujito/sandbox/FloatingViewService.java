package org.shujito.sandbox;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

public class FloatingViewService extends Service
    implements View.OnTouchListener
{
    public static final String TAG = FloatingViewService.class.getSimpleName();
    private WindowManager mWindowManager;
    private ImageView mFloatingView;
    private WindowManager.LayoutParams mViewParams;
    //
    private int initX = 0;
    private int initY = 0;
    private float initTouchX = 0.f;
    private float initTouchY = 0.f;
    
    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }
    
    @Override
    public void onCreate()
    {
        super.onCreate();
        this.mWindowManager = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        this.mFloatingView = new ImageView(this);
        this.mFloatingView.setImageResource(android.R.drawable.sym_def_app_icon);
        this.mFloatingView.setBackgroundColor(0x7fffff00);
        this.mFloatingView.setOnTouchListener(this);
        this.mViewParams = new WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.TYPE_PHONE,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT);
        mWindowManager.addView(this.mFloatingView, this.mViewParams);
    }
    
    @Override
    public void onDestroy()
    {
        super.onDestroy();
        if (this.mFloatingView != null)
            this.mWindowManager.removeView(this.mFloatingView);
    }
    
    /* View.OnTouchListener */
    @Override
    @SuppressLint("ClickableViewAccessibility")
    public boolean onTouch(View v, MotionEvent event)
    {
        //this.mFloatingView.getRootView().setBackgroundColor(0xffff0000);
        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                this.initX = this.mViewParams.x;
                this.initY = this.mViewParams.y;
                this.initTouchX = event.getRawX();
                this.initTouchY = event.getRawY();
                break;
            case MotionEvent.ACTION_UP:
                break;
            case MotionEvent.ACTION_MOVE:
                this.mViewParams.x = initX + (int) (event.getRawX() - initTouchX);
                this.mViewParams.y = initY + (int) (event.getRawY() - initTouchY);
                this.mWindowManager.updateViewLayout(this.mFloatingView, this.mViewParams);
                break;
        }
        return false;
    }
}
