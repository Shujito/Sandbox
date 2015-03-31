package org.shujito.sandbox;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PixelFormat;
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
    private boolean mRunning;
    private Paint mPaint;
    private int mWidth = 0;
    private int mHeight = 0;
    private float x = 0;
    private float y = 0;
    private boolean reverseX = false;
    private boolean reverseY = false;
    private float speed = 10;
    
    public Surface(Context context)
    {
        this(context, null);
    }
    
    public Surface(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        this.mPaint = new Paint();
        this.mPaint.setStyle(Style.FILL);
        this.mPaint.setColor(Color.BLACK);
        this.setZOrderOnTop(true);
        this.getHolder().setFormat(PixelFormat.TRANSLUCENT);
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
            canvas.save();
            canvas.drawColor(0x20ffffcc);
            canvas.drawCircle(this.x, this.y, 50, this.mPaint);
            //canvas.scale(4, 4);
            //canvas.drawText(String.format("w:'%d' h:'%d'", width, height), 10, 20, this.mPaint);
            canvas.restore();
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
}
