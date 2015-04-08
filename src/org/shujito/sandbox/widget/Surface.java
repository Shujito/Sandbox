package org.shujito.sandbox.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class Surface extends SurfaceView
    implements
    Runnable,
    SurfaceHolder.Callback
{
    public static final String TAG = Surface.class.getSimpleName();
    private Thread mRenderThread;
    private Paint mPaint;
    private boolean mRunning;
    private int mWidth = 0;
    private int mHeight = 0;
    private float x = 0;
    private float y = 0;
    private boolean reverseX = false;
    private boolean reverseY = false;
    private float speed = 5;
    
    public Surface(Context context)
    {
        this(context, null);
    }
    
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public Surface(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        if (this.isInEditMode())
            return;
        this.mPaint = new Paint();
        this.mPaint.setStyle(Style.FILL);
        this.mPaint.setColor(0xffccccff);
        //this.setZOrderOnTop(true);
        //this.setZOrderMediaOverlay(true);
        //this.getHolder().setFormat(PixelFormat.TRANSLUCENT);
        this.getHolder().addCallback(this);
    }
    
    /* Runnable */
    @Override
    public void run()
    {
        Log.i(TAG, "thread start!");
        while (this.mRunning)
        {
            Canvas canvas = this.getHolder().lockCanvas();
            if (canvas == null)
                continue;
            if (this.reverseX)
                this.x -= this.speed;
            else
                this.x += this.speed;
            if (this.reverseY)
                this.y -= this.speed;
            else
                this.y += this.speed;
            if (this.x > this.mWidth)
                this.reverseX = true;
            if (this.x < 0)
                this.reverseX = false;
            if (this.y > this.mHeight)
                this.reverseY = true;
            if (this.y < 0)
                this.reverseY = false;
            //canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
            canvas.drawColor(0xff333333);
            canvas.drawCircle(this.x, this.y, 50, this.mPaint);
            canvas.scale(4, 4);
            canvas.drawText(String.format("w:'%d' h:'%d'", this.mWidth, this.mHeight), 10, 20, this.mPaint);
            this.getHolder().unlockCanvasAndPost(canvas);
            try
            {
                Thread.sleep(1);
            }
            catch (InterruptedException e)
            {
            }
        }
        Log.i(TAG, "thread end!");
    }
    
    /* SurfaceHolder.Callback */
    @Override
    public void surfaceCreated(SurfaceHolder holder)
    {
        Log.i(TAG, "surfaceCreated");
        this.mRunning = true;
        this.mRenderThread = new Thread(this);
        this.mRenderThread.start();
    }
    
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
    {
        Log.i(TAG, "surfaceChanged");
        this.mWidth = width;
        this.mHeight = height;
    }
    
    @Override
    public void surfaceDestroyed(SurfaceHolder holder)
    {
        Log.i(TAG, "surfaceDestroyed");
        this.mRunning = false;
        boolean trying = true;
        while (trying)
        {
            try
            {
                this.mRenderThread.join();
                trying = false;
            }
            catch (InterruptedException e)
            {
            }
        }
    }
    
    /* ownmeth */
    public void pause()
    {
        this.mRunning = false;
    }
    
    public void resume()
    {
        this.mRunning = true;
    }
    
    public void toggleX()
    {
        this.reverseX = !this.reverseX;
    }
    
    public void toggleY()
    {
        this.reverseY = !this.reverseY;
    }
}
