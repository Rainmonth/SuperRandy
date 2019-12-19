package com.rainmonth.music.core.render.glrender;

import android.opengl.GLSurfaceView;

/**
 * Shader接口
 *
 * @author 张豪成
 * @date 2019-12-19 09:39
 */
public interface IShader {
    /**
     * 获取GLSurfaceView的shader
     *
     * @param glSurfaceView GLSurfaceView实例
     * @return shader字符串
     */
    String getShader(GLSurfaceView glSurfaceView);
}
