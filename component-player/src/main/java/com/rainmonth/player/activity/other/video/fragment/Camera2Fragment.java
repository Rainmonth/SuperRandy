package com.rainmonth.player.activity.other.video.fragment;

import android.Manifest;
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
import android.hardware.camera2.CaptureRequest;
import android.media.Image;
import android.media.ImageReader;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;

import com.rainmonth.common.base.BaseLazyFragment;
import com.rainmonth.player.R;
import com.rainmonth.utils.PathUtils;
import com.rainmonth.utils.TimeUtils;
import com.rainmonth.utils.ToastUtils;
import com.rainmonth.utils.log.LogUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * 通过Camera2 和 SurfaceView 来获取视频数据
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class Camera2Fragment extends BaseLazyFragment {
    private SurfaceView svPreview;
    private TextView tvStartRecord;
    private TextView tvChangeCamera;
    private TextView tvStopRecord;

    private SurfaceHolder mSurfaceHolder;
    private ImageReader mImageReader;
    private boolean mSurfaceCreated;

    private Handler mMainHandler, mChildHandler;
    private CameraManager mCameraManager;
    private CameraDevice mCameraDevice;
    private CameraCaptureSession mCameraCaptureSession;
    private int mCameraId = CameraCharacteristics.LENS_FACING_FRONT; // 默认后置摄像头

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

        svPreview.setOnClickListener(v -> onSurfaceViewClick());
        tvStartRecord.setOnClickListener(v -> onStartRecordClick());
        tvChangeCamera.setOnClickListener(v -> onChangeCameraClick());
        tvStopRecord.setOnClickListener(v -> onStopRecordClick());
    }

    private void checkCameraIds() {
        LogUtils.d(TAG, "checkCameraIds");
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
    }

    /**
     * 在SurfaceView 可用后初始化Camera2
     */
    private void initCamera2AfterSurfaceAvailable() {
        if (mChildHandler == null) {
            LogUtils.d(TAG, "获取带Looper的子线程");
            HandlerThread handlerThread = new HandlerThread("captureSessionThread");
            handlerThread.start();
            mChildHandler = new Handler(handlerThread.getLooper());
        }
        if (mMainHandler == null) {
            mMainHandler = new Handler(Looper.getMainLooper());
        }
        mCameraId = CameraCharacteristics.LENS_FACING_FRONT; // 默认后置摄像头
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

        if (mCameraManager == null) {
            mCameraManager = (CameraManager) mContext.getSystemService(Context.CAMERA_SERVICE);
        }
        try {
            if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            checkCameraIds();
            // todo 加入超时锁，防止打开异常导致一直卡在这里
            mCameraManager.openCamera(String.valueOf(mCameraId), mStateCallback, mChildHandler);
        } catch (CameraAccessException e) {
            LogUtils.printStackTrace(TAG, e);
        }
    }

    private CameraDevice.StateCallback mStateCallback = new CameraDevice.StateCallback() {
        @Override
        public void onOpened(@NonNull CameraDevice camera) {
            LogUtils.d(TAG, "openCamera onOpened");
            mCameraDevice = camera;
            // 开启预览

            try {
                onStartPreview();
//                createCameraPreviewSession();
            } catch (CameraAccessException e) {
                LogUtils.printStackTrace(TAG, e);
            }
        }

        @Override
        public void onDisconnected(@NonNull CameraDevice camera) {
            LogUtils.d(TAG, "openCamera onDisconnected");
            mCameraDevice = camera;
            if (mCameraDevice != null) {
                mCameraDevice.close();
                mCameraDevice = null;
            }
        }

        @Override
        public void onError(@NonNull CameraDevice camera, int error) {
            LogUtils.e(TAG, "openCamera onError, error code: " + error);
            mCameraDevice = camera;
            if (mCameraDevice != null) {
                mCameraDevice.close();
                mCameraDevice = null;
            }
        }
    };

    private void onStartPreview() throws CameraAccessException {
        if (mCameraDevice == null) {
            return;
        }
        // 创建预览CaptureRequest.Builder并且配置
        final CaptureRequest.Builder previewCaptureReqBuilder = mCameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
        // 将SurfaceView作为展示容器
        previewCaptureReqBuilder.addTarget(mSurfaceHolder.getSurface());
        mCameraDevice.createCaptureSession(Arrays.asList(mSurfaceHolder.getSurface(), mImageReader.getSurface()), new CameraCaptureSession.StateCallback() {
            @Override
            public void onConfigured(@NonNull CameraCaptureSession session) {
                LogUtils.d(TAG, "createCaptureSession, onConfigured");
                mCameraCaptureSession = session;
                try {
                    // 自动对焦
                    previewCaptureReqBuilder.set(CaptureRequest.CONTROL_AF_MODE, CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE);
                    // 打开闪光灯
                    previewCaptureReqBuilder.set(CaptureRequest.CONTROL_AE_MODE, CaptureRequest.CONTROL_AE_MODE_ON_AUTO_FLASH);
                    // 显示预览
                    CaptureRequest previewRequest = previewCaptureReqBuilder.build();
                    mCameraCaptureSession.setRepeatingRequest(previewRequest, null, mChildHandler);
                } catch (CameraAccessException e) {
                    LogUtils.printStackTrace(TAG, e);
                }
            }

            @Override
            public void onConfigureFailed(@NonNull CameraCaptureSession session) {
                LogUtils.d(TAG, "createCaptureSession, onConfigureFailed");
            }
        }, mChildHandler);
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
            mCameraCaptureSession.capture(captureRequest, null, mChildHandler);
        } catch (CameraAccessException e) {
            LogUtils.printStackTrace(TAG, e);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtils.d(TAG, "onResume, mCameraManager==null: " + (mCameraManager == null) + ", mSurfaceHolder==null: " + (mSurfaceHolder == null));
    }

    @Override
    public void onPause() {
        super.onPause();
        LogUtils.d(TAG, "onPause");
        mCameraDevice.close();
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
                initCamera2AfterSurfaceAvailable();
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
            initCamera2AfterSurfaceAvailable();
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

    private void onSurfaceViewClick() {
        onTakePhoto();
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
}
