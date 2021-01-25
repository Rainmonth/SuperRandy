package com.rainmonth.player.activity.other.opengl;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.opengl.Matrix;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.appcompat.app.AppCompatActivity;

import com.rainmonth.utils.log.LogUtils;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class OpenGlDemoActivity extends AppCompatActivity {
    private RandyGlSurfaceView glSurfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        glSurfaceView = new RandyGlSurfaceView(this);

        setContentView(glSurfaceView);

        glSurfaceView.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }


    public static class RandyGlSurfaceView extends GLSurfaceView {
        private final float TOUCH_SCALE_FACTOR = 180.0f / 320;
        private float previousX, previousY;

        private RandyGlSurfaceRender renderer;

        public RandyGlSurfaceView(Context context) {
            this(context, null);
        }

        public RandyGlSurfaceView(Context context, AttributeSet attrs) {
            super(context, attrs);
            init();
        }

        private void init() {
            setEGLContextClientVersion(2);
            renderer = new RandyGlSurfaceRender();
            setRenderer(renderer);
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            float x = event.getX();
            float y = event.getY();

            switch (event.getAction()) {
                case MotionEvent.ACTION_MOVE:
                    float dx = x - previousX;
                    float dy = x - previousY;
                    if (y > getHeight() / 2.0f) {
                        dx = dx * -1;
                    }
                    if (x < getWidth() / 2.f) {
                        dy = dy * -1;
                    }

                    renderer.setAngle(renderer.getAngle() + ((dx + dy) * TOUCH_SCALE_FACTOR));
                    requestRender();
            }

            previousX = x;
            previousY = y;
            return true;
        }
    }

    public static class RandyGlSurfaceRender implements GLSurfaceView.Renderer {

        private final float[] vPMatrix = new float[16];
        private final float[] projectionMatrix = new float[16];
        private final float[] viewMatrix = new float[16];

        private float[] rotationMatrix = new float[16];

        private Triangle mTriangle;
        private Square mSquare;

        public volatile float mAngle;

        public float getAngle() {
            return mAngle;
        }

        public void setAngle(float angle) {
            mAngle = angle;
        }

        /**
         * 加载并编译着色器程序
         *
         * @param type       类型
         * @param shaderCode shader 代码
         * @return shader
         */
        public static int loadShader(int type, String shaderCode) {
            int shader = GLES20.glCreateShader(type);
            GLES20.glShaderSource(shader, shaderCode);
            GLES20.glCompileShader(shader);
            return shader;
        }

        @Override
        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            LogUtils.d("OpenGL", "onSurfaceCreated");
            GLES20.glClearColor(.0f, .0f, .0f, .0f);

            mTriangle = new Triangle();
            mSquare = new Square();
        }

        @Override
        public void onSurfaceChanged(GL10 gl, int width, int height) {
            LogUtils.d("OpenGL", "onSurfaceChanged");
            GLES20.glViewport(0, 0, width, height);
            float ration = (float) width / height;
            Matrix.frustumM(projectionMatrix, 0, -ration, ration, -1, 1, 3, 7);

//            applyProjectionMatrixByES10(gl, width, height);
        }

        @Override
        public void onDrawFrame(GL10 gl) {
            LogUtils.d("OpenGL", "onDrawFrame");

            float[] scratch = new float[16];

            GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

            Matrix.setLookAtM(viewMatrix, 0, 0, 0, -3, 0f, 0f, 0f, 0f, 1.0f, 0.0f);

            Matrix.multiplyMM(vPMatrix, 0, projectionMatrix, 0, viewMatrix, 0);

//            long time = SystemClock.uptimeMillis() % 4000L;
//            float angle = 0.090f * ((int) time);
            Matrix.setRotateM(rotationMatrix, 0, mAngle, 0, 0, -1.0f);

            Matrix.multiplyMM(scratch, 0, vPMatrix, 0, rotationMatrix, 0);

//            applyCameraMatrixByES10(gl);

            mTriangle.draw(scratch);
        }

        /**
         * ES 1.0 API 创建投影矩阵
         */
        private void applyProjectionMatrixByES10(GL10 gl, int width, int height) {

            gl.glViewport(0, 0, width, height);
            // 根据屏幕的比例调整
            float ratio = (float) width / height;
            // 设置矩阵为投影矩阵
            gl.glMatrixMode(GL10.GL_PROJECTION);
            // 重置矩阵到默认状态
            gl.glLoadIdentity();
            // 应用投影矩阵
            gl.glFrustumf(-ratio, ratio, -1, 1, 3, 7);
        }

        /**
         * ES 1.0 API 相机转换矩阵
         */
        private void applyCameraMatrixByES10(GL10 gl) {
            // 切换模式
            gl.glMatrixMode(GL10.GL_MODELVIEW);
            // 重置
            gl.glLoadIdentity();
            // 设置相机视图
            GLU.gluLookAt(gl, 0, 0, -5, 0, 0, 0, 0, 1, 1);
        }

    }
}
