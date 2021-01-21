package com.rainmonth.player.activity.other.video.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.TextView;

import com.rainmonth.common.base.BaseLazyFragment;
import com.rainmonth.player.R;
import com.rainmonth.utils.AppUtils;
import com.rainmonth.utils.BarUtils;
import com.rainmonth.utils.FileUtils;
import com.rainmonth.utils.PathUtils;
import com.rainmonth.utils.ResUtil;
import com.rainmonth.utils.ScreenUtils;
import com.rainmonth.utils.SizeUtils;
import com.rainmonth.utils.StringUtils;
import com.rainmonth.utils.TimeUtils;
import com.rainmonth.utils.ToastUtils;
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
    private TextView tvRecord;
    private TextView tvFlashLight;
    private TextView tvChangeCamera;
    private TextView tvTakePhoto;

    private Camera mCamera;
    private SurfaceHolder mSurfaceHolder;
    private int mSurfaceWidth, mSurfaceHeight;
    private boolean mSurfaceCreated;
    private boolean mIsRecording = false;

    private MediaRecorder mMediaRecorder;
    private String mOutputVideoFile;
    private boolean mIsFlashOn = false;

    private int mCameraId = Camera.CameraInfo.CAMERA_FACING_BACK;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.player_fragment_video_get_data_by_camera;
    }

    @Override
    protected void initViewsAndEvents(View view) {
        LogUtils.d(TAG, "initViewsAndEvents");
        mSurfaceWidth = ScreenUtils.getScreenWidth();
        mSurfaceHeight = ScreenUtils.getScreenHeight() - BarUtils.getStatusBarHeight() - SizeUtils.dp2px(48);
        svPreview = view.findViewById(R.id.sv_preview);
        tvRecord = view.findViewById(R.id.tv_record);
        tvFlashLight = view.findViewById(R.id.tv_flash_light);
        tvChangeCamera = view.findViewById(R.id.tv_change_camera);
        tvTakePhoto = view.findViewById(R.id.tv_take_photo);

        tvTakePhoto.setOnClickListener(v -> onTakePhotoClick());
        tvRecord.setOnClickListener(v -> onRecordClick());
        tvFlashLight.setOnClickListener(v -> onFlashLightClick());
        tvChangeCamera.setOnClickListener(v -> onChangeCameraClick());
    }

    private void initCameraAfterSurfaceAvailable() {
        LogUtils.d(TAG, "initCameraAfterSurfaceAvailable");
        openCamera();
    }

    private void openCamera() {
        if (!mSurfaceCreated || mSurfaceHolder == null) {
            return;
        }
        if (mCamera == null) {
            mCamera = Camera.open(mCameraId);
        }
        if (mCamera == null) {
            LogUtils.w(TAG, "调用Camera.open()后mCamera仍未空");
            return;
        }
        mCamera.setDisplayOrientation(90);

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
            // 设置目标
            mCamera.setPreviewDisplay(mSurfaceHolder);
            // 开始预览
            mCamera.startPreview();
        } catch (IOException e) {
            LogUtils.printStackTrace(TAG, e);
        }
    }

    private void closeCamera() {
        if (mCamera != null) {
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtils.d(TAG, "onResume, mCamera==null: " + (mCamera == null) + ", mSurfaceHolder==null: " + (mSurfaceHolder == null));
        openCamera();
    }

    @Override
    public void onPause() {
        super.onPause();
        LogUtils.d(TAG, "onPause");
        closeCamera();
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
                }
            }
        });
    }

    @Override
    protected void onUserVisible() {
        LogUtils.d(TAG, "onUserVisible");

        if (mCamera != null) {
            mCamera.startPreview();
        }
    }

    @Override
    protected void onUserInvisible() {
        LogUtils.d(TAG, "onUserInvisible");
        if (mCamera != null) {
            mCamera.stopPreview();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        LogUtils.d(TAG, "onDestroyView, mCamera==null: " + (mCamera == null));
        if (mCamera != null) {
            mCamera.stopPreview();
            mCamera = null;
        }
    }

    private void onTakePhotoClick() {
        LogUtils.i(TAG, "onTakePhotoClick");
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
//            final Matrix matrix = new Matrix();
//            matrix.setRotate(90);
            try {
                FileOutputStream outputStream = new FileOutputStream(PathUtils.getExternalDcimPath() + File.separator + TimeUtils.getNowString() + ".jpg");
                resource.compress(Bitmap.CompressFormat.JPEG, 85, outputStream);

                LogUtils.d(TAG, "Camera1拍照成功");
                if (mCamera != null) {
                    mCamera.stopPreview();
                    mCamera.startPreview();
                }
            } catch (FileNotFoundException e) {
                LogUtils.printStackTrace(TAG, e);
            }
        }
    };

    private void onRecordClick() {
        if (mCamera == null || !mSurfaceCreated || mSurfaceHolder == null) {
            return;
        }
        if (mIsRecording) { // 正在录制
            mIsRecording = false;
            tvRecord.setBackground(ResUtil.getDrawable(R.drawable.common_shape_16round_green));
            tvRecord.setText("开始录制");
            if (mMediaRecorder != null) {
                mMediaRecorder.stop();
                mMediaRecorder.release();
                mMediaRecorder = null;
            }
            mOutputVideoFile = null;
            mCamera.lock();
            mCamera.startPreview();
        } else {
            if (StringUtils.isEmpty(mOutputVideoFile)) {
                mOutputVideoFile = getOutputFilePath();
            }
            mIsRecording = true;
            tvRecord.setBackground(ResUtil.getDrawable(R.drawable.common_shape_16round_red));
            tvRecord.setText("结束录制");
            mCamera.stopPreview();
            mCamera.unlock();
            mMediaRecorder = new MediaRecorder();
            mMediaRecorder.setCamera(mCamera);
            mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mMediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
            CamcorderProfile profile = CamcorderProfile.get(CamcorderProfile.QUALITY_HIGH);
            mMediaRecorder.setProfile(profile);
            mMediaRecorder.setOutputFile(mOutputVideoFile);
            mMediaRecorder.setPreviewDisplay(mSurfaceHolder.getSurface());
            try {
                mMediaRecorder.prepare();
                mMediaRecorder.start();
            } catch (Exception e) {
                LogUtils.printStackTrace(TAG, e);
            }
        }
    }

    private String getOutputFilePath() {
        File dir = FileUtils.makeDirs(PathUtils.getExternalDcimPath() + File.separator + AppUtils.getAppPackageName());
        return dir.getPath() + File.separator + "camera_vid_" + TimeUtils.getNowString() + ".mp4";
    }

    private void onFlashLightClick() {
        if (mCamera == null) {
            return;
        }

        if (!isSupportFlashLight()) {
            LogUtils.w(TAG, "当前设备不支持闪光灯");
            return;
        }

        Camera.Parameters parameters = mCamera.getParameters();
        if (mIsFlashOn) {
            mIsFlashOn = false;
            ToastUtils.showShort("闪光灯关闭");
            parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
        } else {
            mIsFlashOn = true;
            ToastUtils.showShort("闪光灯开启");
            parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
        }
        mCamera.setParameters(parameters);
    }

    private boolean isSupportFlashLight() {
        if (mCamera == null) {
            return false;
        }

        return mCamera.getParameters().getSupportedFlashModes() != null;
    }

    private void onChangeCameraClick() {
        if (mCameraId == Camera.CameraInfo.CAMERA_FACING_BACK) {
            mCameraId = Camera.CameraInfo.CAMERA_FACING_FRONT;
        } else {
            mCameraId = Camera.CameraInfo.CAMERA_FACING_BACK;
        }
        closeCamera();
        openCamera();
    }

}
