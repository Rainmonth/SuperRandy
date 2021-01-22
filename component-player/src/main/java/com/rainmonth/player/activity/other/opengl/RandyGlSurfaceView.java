package com.rainmonth.player.activity.other.opengl;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

public class RandyGlSurfaceView extends GLSurfaceView {
    public RandyGlSurfaceView(Context context) {
        this(context, null);
    }

    public RandyGlSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setEGLContextClientVersion(2);
        setRenderer(new RandyGlSurfaceRender());
    }
}
