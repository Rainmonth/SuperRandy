package com.rainmonth.player.activity.other.video.fragment;

import android.hardware.Camera;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.TextView;

import com.rainmonth.player.BaseCleanFragment;
import com.rainmonth.player.R;
import com.rainmonth.utils.PermissionUtils;
import com.rainmonth.utils.ToastUtils;
import com.rainmonth.utils.constant.PermissionConstants;
import com.rainmonth.utils.log.LogUtils;

import java.io.IOException;

/**
 * 通过Camera和SurfaceView
 */
public class CameraFragment extends BaseCleanFragment implements SurfaceHolder.Callback {
    private SurfaceView svPreview;
    private TextView tvStartRecord;
    private TextView tvStopRecord;

    private Camera camera;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.player_fragment_video_get_data_by_camera;
    }

    @Override
    protected void initViewsAndEvents(View view) {
        super.initViewsAndEvents(view);
        svPreview = view.findViewById(R.id.sv_preview);
        tvStartRecord = view.findViewById(R.id.tv_start_record);
        tvStopRecord = view.findViewById(R.id.tv_stop_record);

        initSurfaceView();

        tvStartRecord.setOnClickListener(v -> onStartRecordClick());
        tvStopRecord.setOnClickListener(v -> onStopRecordClick());
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    private void initSurfaceView() {
        svPreview.getHolder().addCallback(this);
        camera = Camera.open();
        camera.setDisplayOrientation(90);
        camera.setPreviewCallback(new Camera.PreviewCallback() {
            @Override
            public void onPreviewFrame(byte[] data, Camera camera) {

            }
        });
    }

    private void onStartRecordClick() {

    }

    private void onStopRecordClick() {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try {
            camera.setPreviewDisplay(holder);
            camera.startPreview();
        } catch (IOException e) {
            LogUtils.printStackTrace(TAG, e);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        camera.release();
    }
}
