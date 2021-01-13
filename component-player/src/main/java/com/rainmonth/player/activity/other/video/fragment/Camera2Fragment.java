package com.rainmonth.player.activity.other.video.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CameraMetadata;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.media.CamcorderProfile;
import android.media.Image;
import android.media.ImageReader;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.util.Log;
import android.util.Size;
import android.util.SparseIntArray;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;

import com.rainmonth.common.base.BaseLazyFragment;
import com.rainmonth.player.R;
import com.rainmonth.utils.AppUtils;
import com.rainmonth.utils.FileUtils;
import com.rainmonth.utils.PathUtils;
import com.rainmonth.utils.ResUtil;
import com.rainmonth.utils.StringUtils;
import com.rainmonth.utils.ThreadUtils;
import com.rainmonth.utils.TimeUtils;
import com.rainmonth.utils.ToastUtils;
import com.rainmonth.utils.log.LogUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * 通过Camera2 和 SurfaceView 来获取视频数据
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class Camera2Fragment extends BaseLazyFragment {

    private final int SENSOR_ORIENTATION_DEFAULT_DEGREES = 90;
    private final int SENSOR_ORIENTATION_INVERSE_DEGREES = 270;

    private static final SparseIntArray DEFAULT_ORIENTATIONS = new SparseIntArray();
    private static final SparseIntArray INVERSE_ORIENTATIONS = new SparseIntArray();

    static {
        DEFAULT_ORIENTATIONS.append(Surface.ROTATION_0, 90);
        DEFAULT_ORIENTATIONS.append(Surface.ROTATION_90, 0);
        DEFAULT_ORIENTATIONS.append(Surface.ROTATION_180, 270);
        DEFAULT_ORIENTATIONS.append(Surface.ROTATION_270, 180);
    }

    static {
        INVERSE_ORIENTATIONS.append(Surface.ROTATION_0, 270);
        INVERSE_ORIENTATIONS.append(Surface.ROTATION_90, 180);
        INVERSE_ORIENTATIONS.append(Surface.ROTATION_180, 90);
        INVERSE_ORIENTATIONS.append(Surface.ROTATION_270, 0);
    }

    /**
     * A {@link Semaphore} to prevent the app from exiting before closing the camera.
     */
    private Semaphore mCameraOpenCloseLock = new Semaphore(1);

    private SurfaceView svPreview;
    private TextView tvStartRecord;
    private TextView tvChangeCamera;
    private TextView tvStopRecord;
    private TextView tvTakePhoto;

    private SurfaceHolder mSurfaceHolder;
    private ImageReader mImageReader;
    private boolean mSurfaceCreated;


    private CameraManager mCameraManager;
    private Size mPreviewSize;                                                          // 预览的大小
    private Size mVideoSize;                                                            // 输出视频的大小
    private String mCameraId = String.valueOf(CameraCharacteristics.LENS_FACING_FRONT); // 默认后置摄像头

    private CameraDevice mCameraDevice;
    private CameraCaptureSession mCameraPreviewCaptureSession;
    private Integer mSensorOrientation = 0;


    private MediaRecorder mMediaRecorder;                               // 用于录像
    private boolean mIsRecording = false;
    private HandlerThread mBackgroundThread;
    private Handler mMainHandler, mChildHandler;
    private CaptureRequest.Builder mPreviewBuilder;
    private String outputPath;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.player_fragment_video_get_data_by_camera2;
    }

    @Override
    protected void initViewsAndEvents(View view) {
        LogUtils.d(TAG, "initViewsAndEvents");
        svPreview = view.findViewById(R.id.sv_preview);
        tvStartRecord = view.findViewById(R.id.tv_start_record);
        tvChangeCamera = view.findViewById(R.id.tv_change_camera);
        tvStopRecord = view.findViewById(R.id.tv_stop_record);
        tvTakePhoto = view.findViewById(R.id.tv_take_photo);

        tvTakePhoto.setOnClickListener(v -> onTakePhotoClick());
        tvStartRecord.setOnClickListener(v -> onStartRecordClick());
        tvChangeCamera.setOnClickListener(v -> onChangeCameraClick());
        tvStopRecord.setOnClickListener(v -> onStopRecordClick());
    }

    private void checkCameraIds() {
        LogUtils.d(TAG, "checkCameraIds");
        String[] cameraIds = null;
        try {
            cameraIds = mCameraManager.getCameraIdList();
            CameraCharacteristics characteristics = mCameraManager.getCameraCharacteristics(String.valueOf(mCameraId));
            StreamConfigurationMap map = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
            mSensorOrientation = characteristics.get(CameraCharacteristics.SENSOR_ORIENTATION);
        } catch (CameraAccessException e) {
            LogUtils.printStackTrace(TAG, e);
            ToastUtils.showShort("获取cameraIds失败");
        }
        if (cameraIds == null) {
            return;
        }
        LogUtils.d(TAG, "cameraIds: " + Arrays.toString(cameraIds));
    }

    private void startBackgroundThread() {
        LogUtils.d(TAG, "开启子线程");
        mBackgroundThread = new HandlerThread("CameraBackground");
        mBackgroundThread.start();
        mChildHandler = new Handler(mBackgroundThread.getLooper());
        if (mMainHandler == null) {
            mMainHandler = new Handler(Looper.getMainLooper());
        }
    }

    private void stopBackgroundThread() {
        mBackgroundThread.quitSafely();
        try {
            mBackgroundThread.join();
            mBackgroundThread = null;
            mChildHandler = null;
        } catch (InterruptedException e) {

        }
    }

    private void prepareImageReader() {
        mImageReader = ImageReader.newInstance(1080, 1920, ImageFormat.JPEG, 1);
        mImageReader.setOnImageAvailableListener(reader -> {
            // 拿到拍照照片数据
            Image image = reader.acquireNextImage();
            ByteBuffer buffer = image.getPlanes()[0].getBuffer();
            byte[] bytes = new byte[buffer.remaining()];
            buffer.get(bytes);//由缓冲区存入字节数组
            final Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

            try {
                FileOutputStream outputStream = new FileOutputStream(PathUtils.getExternalDcimPath() + File.separator + TimeUtils.getNowString() + ".jpg");
                bitmap.compress(Bitmap.CompressFormat.JPEG, 85, outputStream);
                LogUtils.d(TAG, "Camera2拍照成功");
            } catch (FileNotFoundException e) {
                LogUtils.printStackTrace(TAG, e);
            }
        }, mMainHandler);
    }

    /**
     * 准备开启相机
     */
    private void openCamera() {
        final Activity activity = getActivity();
        if (activity == null || activity.isFinishing()) {
            return;
        }
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            // 权限检查
            return;
        }
        if (mCameraManager == null) {
            mCameraManager = (CameraManager) mContext.getSystemService(Context.CAMERA_SERVICE);
        }
        try {
            // 加入超时锁，防止打开异常导致一直卡在这里
            if (!mCameraOpenCloseLock.tryAcquire(2500, TimeUnit.MILLISECONDS)) {
                throw new RuntimeException("Time out waiting to lock camera opening.");
            }
            mCameraId = mCameraManager.getCameraIdList()[0]; // 默认后置摄像头
            CameraCharacteristics characteristics = mCameraManager.getCameraCharacteristics(mCameraId);
            StreamConfigurationMap streamConfigurationMap = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
            if (streamConfigurationMap == null) {
                throw new RuntimeException("Cannot get available preview/video sizes");
            }
            boolean isSupportMediaRecorder = StreamConfigurationMap.isOutputSupportedFor(MediaRecorder.class);
            boolean isSupportSurfaceHolder = StreamConfigurationMap.isOutputSupportedFor(SurfaceHolder.class);
            LogUtils.d(TAG, "openCamera, isSupportMediaRecorder: " + isSupportMediaRecorder + ", isSupportSurfaceHolder: " + isSupportSurfaceHolder);

            LogUtils.d(TAG, "outputSizeFor MediaRecorder: " + (streamConfigurationMap.getOutputSizes(MediaRecorder.class) == null ? "" :
                    Arrays.toString(streamConfigurationMap.getOutputSizes(MediaRecorder.class))));
            LogUtils.d(TAG, "outputSizeFor SurfaceHolder: " + (streamConfigurationMap.getOutputSizes(SurfaceHolder.class) == null ? "" :
                    Arrays.toString(streamConfigurationMap.getOutputSizes(SurfaceHolder.class))));
            // 获取输出视频最合适大小
            mVideoSize = chooseVideoSize(streamConfigurationMap.getOutputSizes(MediaRecorder.class));// 获取预览最合适大小
            mPreviewSize = chooseOptimalSize(streamConfigurationMap.getOutputSizes(SurfaceHolder.class), svPreview.getWidth(), svPreview.getHeight(), mVideoSize);
            LogUtils.d(TAG, "mVideoSize: " + mVideoSize.toString() + ", mPreviewSize: " + mPreviewSize.toString());
            mMediaRecorder = new MediaRecorder();
            mCameraManager.openCamera(String.valueOf(mCameraId), mStateCallback, null);
        } catch (CameraAccessException e) {
            LogUtils.printStackTrace(TAG, e);
            ToastUtils.showShort("无法获取相机");
            activity.finish();
        } catch (NullPointerException e) {
            LogUtils.e(TAG, "该设备不支持Camera2 API");
        } catch (InterruptedException e) {
            throw new RuntimeException("Interrupted while trying to lock camera opening.");
        }
    }

    private Size chooseVideoSize(Size[] choices) {
        for (Size size : choices) {
            if (size.getWidth() == size.getHeight() * 4 / 3 && size.getWidth() <= 1080) {
                return size;
            }
        }
        Log.e(TAG, "Couldn't find any suitable video size");
        return choices[choices.length - 1];
    }

    private Size chooseOptimalSize(Size[] choices, int width, int height, Size aspectRatio) {
// Collect the supported resolutions that are at least as big as the preview Surface
        List<Size> bigEnough = new ArrayList<>();
        int w = aspectRatio.getWidth();
        int h = aspectRatio.getHeight();
        for (Size option : choices) {
            if (option.getHeight() == option.getWidth() * h / w &&
                    option.getWidth() >= width && option.getHeight() >= height) {
                bigEnough.add(option);
            }
        }

        // Pick the smallest of those, assuming we found any
        if (bigEnough.size() > 0) {
            return Collections.min(bigEnough, new CompareSizesByArea());
        } else {
            Log.e(TAG, "Couldn't find any suitable preview size");
            return choices[0];
        }
    }

    /**
     * Compares two {@code Size}s based on their areas.
     */
    static class CompareSizesByArea implements Comparator<Size> {

        @Override
        public int compare(Size lhs, Size rhs) {
            // We cast here to ensure the multiplications won't overflow
            return Long.signum((long) lhs.getWidth() * lhs.getHeight() -
                    (long) rhs.getWidth() * rhs.getHeight());
        }

    }

    private CameraDevice.StateCallback mStateCallback = new CameraDevice.StateCallback() {
        @Override
        public void onOpened(@NonNull CameraDevice camera) {
            LogUtils.d(TAG, "openCamera onOpened");

            mCameraDevice = camera;
            // 开启预览
            startPreview();
//                createCameraPreviewSession();
            mCameraOpenCloseLock.release();
        }

        @Override
        public void onDisconnected(@NonNull CameraDevice camera) {
            LogUtils.d(TAG, "openCamera onDisconnected");
            mCameraOpenCloseLock.release();
            mCameraDevice = camera;
            if (mCameraDevice != null) {
                mCameraDevice.close();
                mCameraDevice = null;
            }
        }

        @Override
        public void onError(@NonNull CameraDevice camera, int error) {
            LogUtils.e(TAG, "openCamera onError, error code: " + error);
            mCameraOpenCloseLock.release();
            mCameraDevice = camera;
            if (mCameraDevice != null) {
                mCameraDevice.close();
                mCameraDevice = null;
            }
        }
    };

    private void closeCamera() {
        try {
            mCameraOpenCloseLock.acquire();
            closePreviewSession();
            if (null != mCameraDevice) {
                mCameraDevice.close();
                mCameraDevice = null;
            }
            if (null != mMediaRecorder) {
                mMediaRecorder.release();
                mMediaRecorder = null;
            }
        } catch (InterruptedException e) {
            throw new RuntimeException("Interrupted while trying to lock camera closing.");
        } finally {
            mCameraOpenCloseLock.release();
        }
    }

    private void startPreview() {
        if (mCameraDevice == null || !mSurfaceCreated || null == mPreviewSize) {
            return;
        }
        try {
            closePreviewSession();
            // 创建预览CaptureRequest.Builder并且配置
            mPreviewBuilder = mCameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
            // 将SurfaceView作为展示容器
            mPreviewBuilder.addTarget(mSurfaceHolder.getSurface());

            mCameraDevice.createCaptureSession(Collections.singletonList(mSurfaceHolder.getSurface()), new CameraCaptureSession.StateCallback() {
                @Override
                public void onConfigured(@NonNull CameraCaptureSession session) {
                    LogUtils.d(TAG, "createCaptureSession, onConfigured");
                    mCameraPreviewCaptureSession = session;
                    updatePreview();
                }

                @Override
                public void onConfigureFailed(@NonNull CameraCaptureSession session) {
                    LogUtils.d(TAG, "createCaptureSession, onConfigureFailed");
                }
            }, mChildHandler);
        } catch (CameraAccessException e) {
            LogUtils.printStackTrace(TAG, e);
        }
    }

    /**
     * 更改预览配置
     */
    private void updatePreview() {
        try {
//            // 自动对焦
//            mPreviewBuilder.set(CaptureRequest.CONTROL_AF_MODE, CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE);
//            // 打开闪光灯
//            mPreviewBuilder.set(CaptureRequest.CONTROL_AE_MODE, CaptureRequest.CONTROL_AE_MODE_ON_AUTO_FLASH);

            mPreviewBuilder.set(CaptureRequest.CONTROL_MODE, CameraMetadata.CONTROL_MODE_AUTO);
            // 显示预览
            mCameraPreviewCaptureSession.setRepeatingRequest(mPreviewBuilder.build(), null, mChildHandler);
        } catch (CameraAccessException e) {
            LogUtils.printStackTrace(TAG, e);
        }
    }

    private void closePreviewSession() {
        if (mCameraPreviewCaptureSession != null) {
            mCameraPreviewCaptureSession.close();
            mCameraPreviewCaptureSession = null;
        }
    }

    // 拍照
    private void onTakePhoto() {
        if (mCameraDevice == null) {
            return;
        }
        final CaptureRequest.Builder captureBuilder;
        try {
            captureBuilder = mCameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_STILL_CAPTURE);
            // 设置谱表
            captureBuilder.addTarget(mImageReader.getSurface());
            // 自动对焦
            captureBuilder.set(CaptureRequest.CONTROL_AF_MODE, CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE);
            // 打开闪光灯
            captureBuilder.set(CaptureRequest.CONTROL_AE_MODE, CaptureRequest.CONTROL_AE_MODE_ON_AUTO_FLASH);
            // 获取手机的方向
            if (getActivity() != null) {
                int rotation = getActivity().getWindowManager().getDefaultDisplay().getRotation();
                captureBuilder.set(CaptureRequest.JPEG_ORIENTATION, rotation);
            }
            CaptureRequest captureRequest = captureBuilder.build();
            mCameraPreviewCaptureSession.capture(captureRequest, null, mChildHandler);
        } catch (CameraAccessException e) {
            LogUtils.printStackTrace(TAG, e);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtils.d(TAG, "onResume, mCameraManager==null: " + (mCameraManager == null) + ", mSurfaceHolder==null: " + (mSurfaceHolder == null));
        startBackgroundThread();
    }

    @Override
    public void onPause() {
        LogUtils.d(TAG, "onPause");
        closeCamera();
        stopBackgroundThread();
        super.onPause();
    }

    @Override
    protected void onFirstUserVisible() {
        LogUtils.d(TAG, "onFirstUserVisible");
        mSurfaceHolder = svPreview.getHolder();
        mSurfaceHolder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                mSurfaceCreated = true;
                LogUtils.d(TAG, "surfaceCreated, mSurfaceCreated = true");
                openCamera();
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                mSurfaceCreated = false;
                LogUtils.d(TAG, "surfaceDestroyed, mSurfaceCreated = false");
                if (mCameraDevice != null) {
                    mCameraDevice.close();
                    mCameraDevice = null;
                }

            }
        });
    }

    @Override
    protected void onUserVisible() {
        LogUtils.d(TAG, "onUserVisible, mSurfaceCreated: " + mSurfaceCreated);
        if (mSurfaceCreated) {
            openCamera();
        }
    }

    @Override
    protected void onUserInvisible() {
        LogUtils.d(TAG, "onUserInvisible, mSurfaceCreated: " + mSurfaceCreated);
        if (mCameraDevice != null) {
            LogUtils.d(TAG, "onUserInvisible, mCameraDevice.close()");
            mCameraDevice.close();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        LogUtils.d(TAG, "onDestroyView, mCameraDevice==null: " + (mCameraDevice == null));
        if (mCameraDevice != null) {
            mCameraDevice.close();
            mCameraDevice = null;
        }
    }

    private void onTakePhotoClick() {
        // todo 拍照
        closePreviewSession();
//        onTakePhoto();
    }

    private void onStartRecordClick() {
        LogUtils.d(TAG, "onStartRecordClick, mIsRecording: " + mIsRecording);
        try {
            if (mIsRecording) {
                stopRecord();
            } else {
                startRecord();
            }
        } catch (Exception e) {
            LogUtils.printStackTrace(TAG, e);
        }
    }

    private void startRecord() {
        LogUtils.d(TAG, "startRecord, mIsRecording: " + mIsRecording);
        if (null == mCameraDevice || !mSurfaceCreated) {
            return;
        }
        try {
            closePreviewSession();
            setupMediaRecorder();
            mPreviewBuilder = mCameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_RECORD);

            List<Surface> surfaces = new ArrayList<>();
            // Set up Surface for the camera preview
            mPreviewBuilder.addTarget(mSurfaceHolder.getSurface());
            surfaces.add(mSurfaceHolder.getSurface());
            // Set up Surface for the MediaRecorder
            Surface recorderSurface = mMediaRecorder.getSurface();
            surfaces.add(recorderSurface);
            mPreviewBuilder.addTarget(recorderSurface);
            // 重新建立session
            mCameraDevice.createCaptureSession(surfaces, new CameraCaptureSession.StateCallback() {
                @Override
                public void onConfigured(@NonNull CameraCaptureSession session) {
                    mCameraPreviewCaptureSession = session;
                    updatePreview();
                    ThreadUtils.runOnUiThread(() -> {
                        mIsRecording = true;
                        mMediaRecorder.start();

                        tvStartRecord.setText("结束录制");
                        tvStartRecord.setBackground(ResUtil.getDrawable(R.drawable.common_shape_16round_green));
                    });
                }

                @Override
                public void onConfigureFailed(@NonNull CameraCaptureSession session) {
                    ToastUtils.showLong("startRecord, onConfigureFailed");
                }
            }, mChildHandler);
        } catch (CameraAccessException e) {
            LogUtils.printStackTrace(TAG, e);
        }


    }

    private void stopRecord() {
        LogUtils.d(TAG, "stopRecord, mIsRecording: " + mIsRecording);
        tvStartRecord.setText("开始录制");
        tvStartRecord.setBackground(ResUtil.getDrawable(R.drawable.common_shape_16round_red));
        if (mMediaRecorder != null) {
            mIsRecording = false;
            mMediaRecorder.stop();
            mMediaRecorder.reset();
        }
        ToastUtils.showLong("Video saved: " + outputPath);
        outputPath = null;
        startPreview();
    }

    private void setupMediaRecorder() {
        if (StringUtils.isEmpty(outputPath)) {
            outputPath = getOutputFilePath();
        }
        LogUtils.d(TAG, "setupMediaRecorder, outputPath: " + outputPath);
        final Activity activity = getActivity();
        if (null == activity) {
            return;
        }
        mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mMediaRecorder.setVideoSource(MediaRecorder.VideoSource.SURFACE);
        mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        if (outputPath == null || outputPath.isEmpty()) {
            outputPath = getOutputFilePath();
        }
        mMediaRecorder.setOutputFile(outputPath);
        mMediaRecorder.setVideoEncodingBitRate(10000000);
        mMediaRecorder.setVideoFrameRate(30);
        mMediaRecorder.setVideoSize(mVideoSize.getWidth(), mVideoSize.getHeight());
        mMediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);
        mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
        int rotation = activity.getWindowManager().getDefaultDisplay().getRotation();
        switch (mSensorOrientation) {
            case SENSOR_ORIENTATION_DEFAULT_DEGREES:
                mMediaRecorder.setOrientationHint(DEFAULT_ORIENTATIONS.get(rotation));
                break;
            case SENSOR_ORIENTATION_INVERSE_DEGREES:
                mMediaRecorder.setOrientationHint(INVERSE_ORIENTATIONS.get(rotation));
                break;
        }
        try {
            mMediaRecorder.prepare();
        } catch (IOException e) {
            LogUtils.printStackTrace(TAG, e);
        }
    }

    private String getOutputFilePath() {
        File dir = FileUtils.makeDirs(PathUtils.getExternalDcimPath() + File.separator + AppUtils.getAppPackageName());
        return dir.getPath() + File.separator + TimeUtils.getNowString() + ".mp4";
    }

    private void onChangeCameraClick() {
        if (String.valueOf(CameraCharacteristics.LENS_FACING_BACK).equals(mCameraId)) {
            mCameraId = String.valueOf(CameraCharacteristics.LENS_FACING_FRONT); // 后置摄像头
        } else {
            mCameraId = String.valueOf(CameraCharacteristics.LENS_FACING_BACK); // 前置摄像头
        }

        // todo 相机的切换
    }

    private void onStopRecordClick() {

    }
}
