package com.rainmonth.player.activity.other.opengl;

import android.opengl.GLSurfaceView;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.rainmonth.player.R;

public class OpenGlDemoActivity extends AppCompatActivity {
    private RandyGlSurfaceView glSurfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player_activity_open_gl_demo);

        glSurfaceView = findViewById(R.id.gl_surface_view);

        glSurfaceView.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }



}
