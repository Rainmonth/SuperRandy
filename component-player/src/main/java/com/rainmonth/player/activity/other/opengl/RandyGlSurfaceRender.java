package com.rainmonth.player.activity.other.opengl;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import com.rainmonth.utils.log.LogUtils;

import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class RandyGlSurfaceRender implements GLSurfaceView.Renderer {
    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        LogUtils.d("OpenGL", "onSurfaceCreated");
        GLES20.glClearColor(.0f, .0f, .0f, .0f);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        LogUtils.d("OpenGL", "onSurfaceChanged");
        GLES20.glViewport(0, 0, width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        LogUtils.d("OpenGL", "onDrawFrame");
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
    }


    public class Triangle {

        private FloatBuffer vertexBuffer;

    }
}
