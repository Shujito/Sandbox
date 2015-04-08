package org.shujito.sandbox;

import java.io.InputStream;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES11;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.GLUtils;

public class GLRenderer implements Renderer
{
    public static final String TAG = GLRenderer.class.getSimpleName();
    final float[] vVertices = {
        0, 0,
        128, 0,
        0, 128,
        128, 128,
    };
    final float[] vCoords = {
        0, 0,
        1, 0,
        0, 1,
        1, 1,
    };
    final int[] texture = new int[1];
    final Buffer vertices;
    final Buffer coords;
    
    public GLRenderer()
    {
        this.vertices = ByteBuffer
            .allocateDirect(this.vVertices.length * 4)
            .order(ByteOrder.nativeOrder())
            .asFloatBuffer()
            .put(this.vVertices)
            .position(0);
        this.coords = ByteBuffer
            .allocateDirect(this.vCoords.length * 4)
            .order(ByteOrder.nativeOrder())
            .asFloatBuffer()
            .put(this.vCoords)
            .position(0);
    }
    
    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config)
    {
        try
        {
            // create
            GLES11.glEnable(GLES11.GL_TEXTURE_2D);
            GLES11.glGenTextures(1, this.texture, 0);
            GLES11.glBindTexture(GLES11.GL_TEXTURE_2D, this.texture[0]);
            InputStream is = SandboxApplication.getInstance().getAssets().open("heart.png");
            Bitmap bmp = BitmapFactory.decodeStream(is);
            GLES11.glTexParameteri(GLES11.GL_TEXTURE_2D, GLES11.GL_TEXTURE_MAG_FILTER, GLES11.GL_NEAREST);
            GLES11.glTexParameteri(GLES11.GL_TEXTURE_2D, GLES11.GL_TEXTURE_MIN_FILTER, GLES11.GL_NEAREST);
            GLUtils.texImage2D(GLES11.GL_TEXTURE_2D, 0, bmp, 0);
            GLES11.glEnable(GLES11.GL_BLEND);
            GLES11.glBlendFunc(GLES11.GL_SRC_ALPHA, GLES11.GL_ONE_MINUS_SRC_ALPHA);
            bmp.recycle();
        }
        catch (Exception ex)
        {
            throw new RuntimeException(ex);
        }
    }
    
    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height)
    {
        GLES11.glMatrixMode(GLES11.GL_PROJECTION);
        GLES11.glLoadIdentity();
        GLES11.glOrthof(-width / 2, width / 2, height, 0, -100, 100);
    }
    
    @Override
    public void onDrawFrame(GL10 gl)
    {
        GLES11.glClearColor(0, .25f, 0, 1);
        GLES11.glClear(GLES11.GL_COLOR_BUFFER_BIT);
        GLES11.glEnableClientState(GLES11.GL_VERTEX_ARRAY);
        GLES11.glEnableClientState(GLES11.GL_TEXTURE_COORD_ARRAY);
        GLES11.glBindTexture(GLES11.GL_TEXTURE_2D, this.texture[0]);
        GLES11.glTexCoordPointer(2, GLES11.GL_FLOAT, 0, this.coords);
        GLES11.glVertexPointer(2, GLES11.GL_FLOAT, 0, this.vertices);
        GLES11.glColor4f(1, 1, 1, 1);
        GLES11.glMatrixMode(GLES11.GL_MODELVIEW);
        GLES11.glDrawArrays(GLES11.GL_TRIANGLE_STRIP, 0, 4);
        GLES11.glDisableClientState(GLES11.GL_VERTEX_ARRAY);
        GLES11.glDisableClientState(GLES11.GL_TEXTURE_COORD_ARRAY);
    }
}
