package com.rainmonth.music.core.render.glrender;

import android.graphics.Bitmap;
import android.graphics.SurfaceTexture;
import android.opengl.GLES20;
import android.opengl.GLException;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.os.Handler;
import android.view.Surface;

import com.rainmonth.common.utils.log.LogUtils;
import com.rainmonth.music.core.render.view.listener.GLSurfaceListener;
import com.rainmonth.music.core.render.view.listener.VideoShotListener;

import java.nio.IntBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * GLSurfaceView.Renderer基类
 *
 * @author 张豪成
 * @date 2019-12-17 19:38
 */
public abstract class GLSurfaceViewBaseRenderer implements GLSurfaceView.Renderer, SurfaceTexture.OnFrameAvailableListener {

    public static final String TAG = GLSurfaceViewBaseRenderer.class.getSimpleName();

    protected boolean               mIsNeedHightShot = false;             // 是否需要高清截图
    protected GLSurfaceView         mGLSurfaceView;                 // 持有的GLSurfaceView实例
    protected GLSurfaceListener     mGLSurfaceListener;         // GLSurfaceView监听器
    protected GLRenderErrorListener mGLRenderErrorListener; // 渲染错误监听器

    protected float[] mMvpMatrix = new float[16];           //
    protected float[] mStMatrix  = new float[16];

    protected int mCurrentViewWidth   = 0;
    protected int mCurrentViewHeight  = 0;
    protected int mCurrentVideoWidth  = 0;
    protected int mCurrentVideoHeight = 0;

    protected boolean mChangeProgram             = false;
    protected boolean mChangeProgramSupportError = false;

    protected Handler mHandler = new Handler();

    public abstract void releaseAll();

    public void initRenderSize() {
        if (mCurrentVideoWidth != 0 && mCurrentViewHeight != 0 && mGLSurfaceView != null &&
                mGLSurfaceView.getWidth() != 0 && mGLSurfaceView.getHeight() != 0) {
            Matrix.scaleM(mMvpMatrix, 0, mCurrentViewWidth * 1.0f / mGLSurfaceView.getWidth(),
                    mCurrentViewHeight * 1.0f / mGLSurfaceView.getHeight(), 1);
        } else {
            LogUtils.e(TAG, "initRenderSize failed for some args is wrong, please check!!!");
        }
    }

    public void setSurfaceView(GLSurfaceView surfaceView) {
        this.mGLSurfaceView = surfaceView;
    }

    public void setSurfaceForPlayer(Surface surface) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (mGLSurfaceListener != null) {
                    mGLSurfaceListener.onSurfaceCreated(surface);
                }
            }
        });
    }

    /**
     * 加载shader
     *
     * @param shaderType shader类型{@link GLES20#GL_VERTEX_SHADER} etc
     * @param source     shader source
     */
    protected int loadShader(int shaderType, String source) {
        int shader = GLES20.glCreateShader(shaderType);
        if (shader != 0) {
            GLES20.glShaderSource(shader, source);
            GLES20.glCompileShader(shader);
            int[] compiled = new int[1];
            GLES20.glGetShaderiv(shader, GLES20.GL_COMPILE_STATUS,
                    compiled, 0);
            if (compiled[0] == 0) {
                LogUtils.e(TAG, "Could not compile shader " + shaderType + ":" +
                        GLES20.glGetShaderInfoLog(shader));
                GLES20.glDeleteShader(shader);
                shader = 0;
            }
        }
        return shader;
    }

    protected int createProgram(String vertexSource, String fragmentSource) {
        int vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, vertexSource);
        if (vertexShader == 0) {
            return 0;
        }
        int pixelShader = loadShader(GLES20.GL_FRAGMENT_SHADER,
                fragmentSource);
        if (pixelShader == 0) {
            return 0;
        }

        int program = GLES20.glCreateProgram();
        if (program != 0) {
            GLES20.glAttachShader(program, vertexShader);
            checkGlError("glAttachShader");
            GLES20.glAttachShader(program, pixelShader);
            checkGlError("glAttachShader");
            GLES20.glLinkProgram(program);
            int[] linkStatus = new int[1];
            GLES20.glGetProgramiv(program, GLES20.GL_LINK_STATUS,
                    linkStatus, 0);
            if (linkStatus[0] != GLES20.GL_TRUE) {
                LogUtils.e(TAG, "Could not link program: " +
                        GLES20.glGetProgramInfoLog(program));
                GLES20.glDeleteProgram(program);
                program = 0;
            }
        }
        return program;
    }

    /**
     * 检查错误，并通知相应监听器处理
     *
     * @param op 引起错误的操作
     */
    protected void checkGlError(final String op) {
        final int error;
        if ((error = GLES20.glGetError()) != GLES20.GL_NO_ERROR) {
            LogUtils.e(TAG, op + ": glError " + error);
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (mGLRenderErrorListener != null) {
                        mGLRenderErrorListener.onError(GLSurfaceViewBaseRenderer.this, op + ": glError " + error, error, mChangeProgramSupportError);
                    }
                    mChangeProgramSupportError = false;
                }
            });
            //throw new RuntimeException(op + ": glError " + error);
        }
    }

    /**
     * 通过位图创建GLSurface
     *
     * @param x
     * @param y
     * @param w
     * @param h
     * @param gl
     */
    protected Bitmap createBitmapFromGLSurface(int x, int y, int w, int h, GL10 gl) {
        int[] bitmapBuffer = new int[w * h];
        int[] bitmapSource = new int[w * h];
        IntBuffer intBuffer = IntBuffer.wrap(bitmapBuffer);
        intBuffer.position(0);
        try {
            gl.glReadPixels(x, y, w, h, GL10.GL_RGBA, GL10.GL_UNSIGNED_BYTE, intBuffer);
            int offset1, offset2;
            for (int i = 0; i < h; i++) {
                offset1 = i * w;
                offset2 = (h - i - 1) * w;
                for (int j = 0; j < w; j++) {
                    int texturePixel = bitmapBuffer[offset1 + j];
                    int blue = (texturePixel >> 16) & 0xff;
                    int red = (texturePixel << 16) & 0x00ff0000;
                    int pixel = (texturePixel & 0xff00ff00) | red | blue;
                    bitmapSource[offset2 + j] = pixel;
                }
            }
        } catch (GLException e) {
            return null;
        }
        if (mIsNeedHightShot) {
            return Bitmap.createBitmap(bitmapSource, w, h, Bitmap.Config.ARGB_8888);
        } else {
            return Bitmap.createBitmap(bitmapSource, w, h, Bitmap.Config.RGB_565);
        }
    }

    public void takeShotPic() {
        // do nothing, empty implements
    }

    public void setShaderEffect(IShader shaderEffect) {
        // to be override
    }

    public IShader getShaderEffect() {
        return null;
    }

    public void setVideoShotListener(VideoShotListener listener, boolean showHigh) {
        // to be override
    }

    public void setGLSurfaceListener(GLSurfaceListener glSurfaceListener) {
        this.mGLSurfaceListener = glSurfaceListener;
    }

    public void setGLRenderErrorListener(GLRenderErrorListener errorListener) {
        this.mGLRenderErrorListener = errorListener;
    }

    public void setMvpMatrix(float[] matrix) {
        this.mMvpMatrix = matrix;
    }

    public float[] getMvpMatrix() {
        return mMvpMatrix;
    }

    public int getCurrentViewWidth() {
        return mCurrentViewWidth;
    }

    public void setCurrentViewWidth(int currentViewWidth) {
        this.mCurrentViewWidth = currentViewWidth;
    }

    public int getCurrentViewHeight() {
        return mCurrentViewHeight;
    }

    public void setCurrentViewHeight(int currentViewHeight) {
        this.mCurrentViewHeight = currentViewHeight;
    }

    public int getCurrentVideoWidth() {
        return mCurrentVideoWidth;
    }

    public void setCurrentVideoWidth(int currentVideoWidth) {
        this.mCurrentVideoWidth = currentVideoWidth;
    }

    public int getCurrentVideoHeight() {
        return mCurrentVideoHeight;
    }

    public void setCurrentVideoHeight(int currentVideoHeight) {
        this.mCurrentVideoHeight = currentVideoHeight;
    }

    //<editor-fold>GLSurfaceView.Renderer实现
    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {

    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {

    }

    @Override
    public void onDrawFrame(GL10 gl) {

    }
    //</editor-fold>

    //<editor-fold>SurfaceTexture.OnFrameAvailableListener实现

    @Override
    public void onFrameAvailable(SurfaceTexture surfaceTexture) {

    }


    //</editor-fold>
}
