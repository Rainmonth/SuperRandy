package com.rainmonth.player.activity.other.video.fragment;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;

import com.rainmonth.player.BaseCleanFragment;
import com.rainmonth.player.R;
import com.rainmonth.utils.ToastUtils;
import com.rainmonth.utils.log.LogUtils;

import java.util.Arrays;

/**
 * 通过Camera2 和 SurfaceView 来获取视频数据
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class Camera2Fragment extends BaseCleanFragment implements SurfaceHolder.Callback {
    private SurfaceView svPreview;
    private TextView tvStartRecord;
    private TextView tvChangeCamera;
    private TextView tvStopRecord;

    CameraManager mCameraManager;
    CameraDevice mCameraDevice;

    CameraDevice.StateCallback mStateCallback;
    private int mCameraId = CameraCharacteristics.LENS_FACING_FRONT; // 默认后置摄像头

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.player_fragment_video_get_data_by_camera;
    }

    @Override
    protected void initViewsAndEvents(View view) {
        super.initViewsAndEvents(view);
        TAG = "Camera2";
        if (getActivity() == null) {
            return;
        }
        mCameraManager = (CameraManager) getActivity().getSystemService(Context.CAMERA_SERVICE);
        if (mCameraManager == null) {
            return;
        }

        svPreview = view.findViewById(R.id.sv_preview);
        tvStartRecord = view.findViewById(R.id.tv_start_record);
        tvChangeCamera = view.findViewById(R.id.tv_change_camera);
        tvStopRecord = view.findViewById(R.id.tv_stop_record);

        initSurfaceView();



        String[] cameraIds = null;
        try {
            cameraIds = mCameraManager.getCameraIdList();
        } catch (CameraAccessException e) {
            LogUtils.printStackTrace(TAG, e);
            ToastUtils.showShort("获取cameraIds失败");
        }
        if (cameraIds == null) {
            return;
        }
        LogUtils.d(TAG, "cameraIds: " + Arrays.toString(cameraIds));
        mStateCallback = new CameraDevice.StateCallback() {
            @Override
            public void onOpened(@NonNull CameraDevice camera) {
                mCameraDevice = camera;
                createCameraPreviewSession();
            }

            @Override
            public void onDisconnected(@NonNull CameraDevice camera) {

            }

            @Override
            public void onError(@NonNull CameraDevice camera, int error) {

            }
        };
        try {
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            // todo 加入超时锁，防止打开异常导致一直卡在这里
            mCameraManager.openCamera(String.valueOf(mCameraId), mStateCallback, mHandler);
        } catch (CameraAccessException e) {
            LogUtils.printStackTrace(TAG, e);
        }

        tvStartRecord.setOnClickListener(v -> onStartRecordClick());
        tvChangeCamera.setOnClickListener(v -> onChangeCameraClick());
        tvStopRecord.setOnClickListener(v -> onStopRecordClick());
    }

    private void createCameraPreviewSession() {

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

    }

    private void onStartRecordClick() {

    }

    private void onChangeCameraClick() {
        if (mCameraId == CameraCharacteristics.LENS_FACING_BACK) {
            mCameraId = CameraCharacteristics.LENS_FACING_FRONT; // 后置摄像头
        } else {
            mCameraId = CameraCharacteristics.LENS_FACING_BACK; // 前置摄像头
        }

        // todo 相机的切换
    }

    private void onStopRecordClick() {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }
}
