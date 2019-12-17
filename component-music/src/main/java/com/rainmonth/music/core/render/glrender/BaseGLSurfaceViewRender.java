package com.rainmonth.music.core.render.glrender;

import android.opengl.GLSurfaceView;

import com.rainmonth.music.core.render.view.listener.GLSurfaceListener;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * GLSurfaceView.Renderer基类
 *
 * @author 张豪成
 * @date 2019-12-17 19:38
 */
public abstract class BaseGLSurfaceViewRender implements GLSurfaceView.Renderer {

    protected GLSurfaceView mGLSurfaceView;
    protected GLSurfaceListener mGLSurfaceListener;
    protected GLRenderErrorListener mGLRenderErrorListener;

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {

    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {

    }

    @Override
    public void onDrawFrame(GL10 gl) {

    }
}
