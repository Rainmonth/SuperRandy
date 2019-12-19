package com.rainmonth.music.core.render.effect;

import android.opengl.GLSurfaceView;

import com.rainmonth.music.core.render.glrender.IShader;

/**
 * @author 张豪成
 * @date 2019-12-19 09:51
 */
public class NoEffectShader implements IShader {
    public NoEffectShader() {
    }

    @Override
    public String getShader(GLSurfaceView glSurfaceView) {
        String shader = "#extension GL_OES_EGL_image_external : require\n"
                + "precision mediump float;\n"
                + "varying vec2 vTextureCoord;\n"
                + "uniform samplerExternalOES sTexture;\n" + "void main() {\n"
                + "  gl_FragColor = texture2D(sTexture, vTextureCoord);\n"
                + "}\n";

        return shader;
    }
}
