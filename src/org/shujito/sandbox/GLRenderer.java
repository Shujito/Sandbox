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
        256, 0,
        0, 256,
        256, 256,
    };
    final float[] vCoords = {
        0, 0,
        1, 0,
        0, 1,
        1, 1,
    };
    final int[] textures = new int[1];
    final Buffer vertices;
    final Buffer coords;
    int width;
    int height;
    
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
            GLES11.glGenTextures(this.textures.length, this.textures, 0);
            GLES11.glBindTexture(GLES11.GL_TEXTURE_2D, this.textures[0]);
            InputStream is = SandboxApplication.getInstance().getAssets().open("heart.png");
            Bitmap bmp = BitmapFactory.decodeStream(is);
            GLES11.glTexParameteri(GLES11.GL_TEXTURE_2D, GLES11.GL_TEXTURE_MAG_FILTER, GLES11.GL_LINEAR);
            GLES11.glTexParameteri(GLES11.GL_TEXTURE_2D, GLES11.GL_TEXTURE_MIN_FILTER, GLES11.GL_LINEAR);
            GLUtils.texImage2D(GLES11.GL_TEXTURE_2D, 0, bmp, 0);
            GLES11.glEnable(GLES11.GL_BLEND);
            GLES11.glBlendFunc(GLES11.GL_SRC_ALPHA, GLES11.GL_ONE_MINUS_SRC_ALPHA);
            bmp.recycle();
            is.close();
        }
        catch (Exception ex)
        {
            throw new RuntimeException(ex);
        }
    }
    
    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height)
    {
        this.width = width;
        this.height = height;
        float ratio = (float) width / (float) height;
        float scaledWidth = ratio * 480.f;
        float scaledHeight = 480.f;
        GLES11.glViewport(0, 0, width, height);
        GLES11.glMatrixMode(GLES11.GL_PROJECTION);
        GLES11.glLoadIdentity();
        GLES11.glOrthof(-scaledWidth / 2, scaledWidth / 2, scaledHeight, 0, -100, 100);
    }
    
    @Override
    public void onDrawFrame(GL10 gl)
    {
        GLES11.glClearColor(0, .25f, 0, 1);
        GLES11.glClear(GLES11.GL_COLOR_BUFFER_BIT);
        // enable features
        GLES11.glEnableClientState(GLES11.GL_VERTEX_ARRAY);
        GLES11.glEnableClientState(GLES11.GL_TEXTURE_COORD_ARRAY);
        // bind and point to stuff
        //GLES11.glBindTexture(GLES11.GL_TEXTURE_2D, this.textures[0]);
        GLES11.glTexCoordPointer(2, GLES11.GL_FLOAT, 0, this.coords);
        GLES11.glVertexPointer(2, GLES11.GL_FLOAT, 0, this.vertices);
        GLES11.glMatrixMode(GLES11.GL_MODELVIEW);
        GLES11.glPushMatrix();
        // draw!
        GLES11.glBindTexture(GLES11.GL_TEXTURE_2D, this.textures[0]);
        GLES11.glColor4f(1, 0, 0, 1);
        GLES11.glTranslatef(0, 32, 0);
        GLES11.glDrawArrays(GLES11.GL_TRIANGLE_STRIP, 0, 4);
        GLES11.glBindTexture(GLES11.GL_TEXTURE_2D, this.textures[0]);
        GLES11.glColor4f(0, 1, 0, 1);
        GLES11.glTranslatef(-64, 32, 0);
        GLES11.glDrawArrays(GLES11.GL_TRIANGLE_STRIP, 0, 4);
        GLES11.glBindTexture(GLES11.GL_TEXTURE_2D, this.textures[0]);
        GLES11.glColor4f(0, 0, 1, 1);
        GLES11.glTranslatef(-64, 32, 0);
        GLES11.glDrawArrays(GLES11.GL_TRIANGLE_STRIP, 0, 4);
        GLES11.glBindTexture(GLES11.GL_TEXTURE_2D, this.textures[0]);
        GLES11.glColor4f(1, 1, 1, 1);
        GLES11.glTranslatef(-64, 32, 0);
        GLES11.glDrawArrays(GLES11.GL_TRIANGLE_STRIP, 0, 4);
        GLES11.glBindTexture(GLES11.GL_TEXTURE_2D, this.textures[0]);
        GLES11.glColor4f(0, 0, 0, 0.75f);
        GLES11.glTranslatef(-64, 32, 0);
        GLES11.glDrawArrays(GLES11.GL_TRIANGLE_STRIP, 0, 4);
        GLES11.glPopMatrix();
        // disable features
        GLES11.glDisableClientState(GLES11.GL_VERTEX_ARRAY);
        GLES11.glDisableClientState(GLES11.GL_TEXTURE_COORD_ARRAY);
    }
}
