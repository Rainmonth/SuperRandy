package com.rainmonth.player.activity.other.video.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.TextView;

import com.rainmonth.common.base.BaseLazyFragment;
import com.rainmonth.common.di.component.AppComponent;
import com.rainmonth.player.BaseCleanFragment;
import com.rainmonth.player.R;
import com.rainmonth.utils.BarUtils;
import com.rainmonth.utils.PathUtils;
import com.rainmonth.utils.ScreenUtils;
import com.rainmonth.utils.SizeUtils;
import com.rainmonth.utils.TimeUtils;
import com.rainmonth.utils.log.LogUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 通过Camera和SurfaceView
 */
public class CameraFragment extends BaseLazyFragment {
    private SurfaceView svPreview;
    private TextView tvStartRecord;
    private TextView tvStopRecord;

    private Camera mCamera;
    private SurfaceHolder mSurfaceHolder;
    private int mSurfaceWidth, mSurfaceHeight;
    private boolean mSurfaceCreated;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.player_fragment_video_get_data_by_camera;
    }

    @Override
    protected void initViewsAndEvents(View view) {
        TAG = "Camera";
        LogUtils.d(TAG, "initViewsAndEvents");
        mSurfaceWidth = ScreenUtils.getScreenWidth();
        mSurfaceHeight = ScreenUtils.getScreenHeight() - BarUtils.getStatusBarHeight() - SizeUtils.dp2px(48);
        svPreview = view.findViewById(R.id.sv_preview);
        tvStartRecord = view.findViewById(R.id.tv_start_record);
        tvStopRecord = view.findViewById(R.id.tv_stop_record);

        svPreview.setOnClickListener(v -> onSurfaceClick());
        tvStartRecord.setOnClickListener(v -> onStartRecordClick());
        tvStopRecord.setOnClickListener(v -> onStopRecordClick());

    }

    private void initCameraAfterSurfaceAvailable() {
        LogUtils.d(TAG, "initCameraAfterSurfaceAvailable");
        mCamera = Camera.open();
        mCamera.setDisplayOrientation(90);
        if (mCamera == null) {
            return;
        }
        try {
            Camera.Parameters parameters = mCamera.getParameters();
            // 设置预览照片大小
            parameters.setPreviewSize(mSurfaceWidth, mSurfaceHeight);
            // 设置预览照片帧数
            parameters.setPreviewFpsRange(4, 10);
            // 设置图片格式
            parameters.setPictureFormat(ImageFormat.JPEG);
            // 设置图片质量
            parameters.setJpegQuality(90);
//        parameters.set("jpeg-quality", 90)
            // 设置照片大小
            parameters.setPictureSize(mSurfaceWidth, mSurfaceHeight);
            // todo 设置自动对焦
            // todo 设置闪光等
            // 设置目标
            mCamera.setPreviewDisplay(mSurfaceHolder);
            // 开始预览
            mCamera.startPreview();
        } catch (IOException e) {
            LogUtils.printStackTrace(TAG, e);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtils.d(TAG, "onResume, mCamera==null: " + (mCamera == null) + ", mSurfaceHolder==null: " + (mSurfaceHolder == null));
    }

    @Override
    public void onPause() {
        super.onPause();
        LogUtils.d(TAG, "onPause");
        if (mCamera != null) {
            mCamera.stopPreview();
        }
    }

    @Override
    protected void onFirstUserVisible() {
        LogUtils.d(TAG, "onFirstUserVisible");
        mSurfaceHolder = svPreview.getHolder();
        // 无需自己的缓冲区
        mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        mSurfaceHolder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                LogUtils.d(TAG, "surfaceCreated");
                mSurfaceCreated = true;
                initCameraAfterSurfaceAvailable();
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                LogUtils.d(TAG, "surfaceChanged, width=" + width + ", height=" + height);
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                LogUtils.d(TAG, "surfaceDestroyed");
                mSurfaceCreated = false;
                if (mCamera != null) {
                    mCamera.stopPreview();
                    mCamera = null;
                }
            }
        });
    }

    @Override
    protected void onUserVisible() {
        LogUtils.d(TAG, "onUserVisible");
    }

    @Override
    protected void onUserInvisible() {
        LogUtils.d(TAG, "onUserInvisible");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        LogUtils.d(TAG, "onDestroyView");
        if (mCamera != null) {
            mCamera.stopPreview();
            mCamera = null;
        }
    }

    private void onSurfaceClick() {
        LogUtils.i(TAG, "onSurfaceClick");
        if (mCamera == null) {
            return;
        }
        mCamera.autoFocus(mAutoFocusCallback);
    }

    private Camera.AutoFocusCallback mAutoFocusCallback = new Camera.AutoFocusCallback() {
        @Override
        public void onAutoFocus(boolean success, Camera camera) {
            if (success) {
                camera.takePicture(new Camera.ShutterCallback() {
                    @Override
                    public void onShutter() {
                        LogUtils.d(TAG, "onShutter");
                    }
                }, new Camera.PictureCallback() { // 是否处理原始数据
                    @Override
                    public void onPictureTaken(byte[] data, Camera camera) {
                        LogUtils.d(TAG, "raw onPictureTaken");
                    }
                }, mOutPictureCallback);
            }
        }
    };

    // 最终数据处理回调
    private Camera.PictureCallback mOutPictureCallback = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            //
            LogUtils.d(TAG, "output onPictureTaken");
            Bitmap resource = BitmapFactory.decodeByteArray(data, 0, data.length);

            try {
                FileOutputStream outputStream = new FileOutputStream(PathUtils.getExternalDcimPath() + File.separator + TimeUtils.getNowString() + ".jpg");
                resource.compress(Bitmap.CompressFormat.JPEG, 85, outputStream);
            } catch (FileNotFoundException e) {
                LogUtils.printStackTrace(TAG, e);
            }
        }
    };

    private void onStartRecordClick() {

    }

    private void onStopRecordClick() {

    }

}
