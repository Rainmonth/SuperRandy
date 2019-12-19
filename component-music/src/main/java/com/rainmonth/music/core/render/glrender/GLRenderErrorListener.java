package com.rainmonth.music.core.render.glrender;

/**
 * 渲染错误监听器
 *
 * @author 张豪成
 * @date 2019-12-17 11:45
 */
public interface GLRenderErrorListener {
    /**
     * @param render               渲染器
     * @param Error                错误文本
     * @param code                 错误代码
     * @param byChangedRenderError 错误是因为切换effect导致的
     */
    void onError(GLSurfaceViewBaseRenderer render, String Error, int code, boolean byChangedRenderError);
}
