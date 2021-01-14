package com.rainmonth.player.activity.other.video;

import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.media.CamcorderProfile;
import android.media.MediaCodec;
import android.media.MediaExtractor;
import android.media.MediaFormat;
import android.media.MediaMuxer;
import android.media.MediaRecorder;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;

import com.rainmonth.common.base.BaseCleanActivity;
import com.rainmonth.player.R;
import com.rainmonth.utils.AppUtils;
import com.rainmonth.utils.FileUtils;
import com.rainmonth.utils.PathUtils;
import com.rainmonth.utils.ResUtil;
import com.rainmonth.utils.StringUtils;
import com.rainmonth.utils.ToastUtils;
import com.rainmonth.utils.log.LogUtils;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * 使用MediaExtractor 和 MediaMuxer API 解析和封装 MP4文件
 * <li>
 * {@link android.media.MediaExtractor}，用于把音视频文件中的音频和视频进行数据分离
 * </li>
 * <li>
 * {@link android.media.MediaMuxer}，用于生产音频文件和视频文件，同时还可以完成音频和视频的合成
 * </li>
 *
 * @author RandyZhang
 * @date 2020/12/21 4:38 PM
 */
public class VideoFileExtractorAndMuxerActivity extends BaseCleanActivity {

    private SurfaceView svPreview;
    private TextView tvRecord, tvStartExtractor, tvStartMuxer;

    private String mOutputDir;
    private String mOriginFilePath;

    private String mOriginFileName = File.separator + "vid_origin.mp4";
    private String mExtractorVideoFileName = File.separator + "vid_extractor.mp4";
    private String mExtractorAudioFileName = File.separator + "aud_extractor.mp3";
    private String mMuxerFileName;

    private SurfaceHolder mSurfaceHolder;
    private Camera mCamera;
    private MediaRecorder mMediaRecorder;
    private MediaExtractor mMediaExtractor;
    private MediaMuxer mMediaMuxer;

    private int mCameraId = Camera.CameraInfo.CAMERA_FACING_BACK;
    private boolean mSurfaceCreated = false;
    private boolean mIsRecording = false;


    @Override
    protected int getContentViewLayoutID() {
        return R.layout.player_activity_video_extract_and_muxer;
    }

    @Override
    protected void initViewsAndEvents() {
        super.initViewsAndEvents();
        svPreview = findViewById(R.id.sv_preview);
        tvRecord = findViewById(R.id.tv_record);
        tvStartExtractor = findViewById(R.id.tv_extractor);
        tvStartMuxer = findViewById(R.id.tv_muxer);

        tvRecord.setOnClickListener(v -> onRecordClick());
        tvStartExtractor.setOnClickListener(v -> onStartExtractorClick());
        tvStartMuxer.setOnClickListener(v -> onStartMuxerClick());

        mSurfaceHolder = svPreview.getHolder();
        mSurfaceHolder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                LogUtils.d(TAG, "surfaceCreated");
                mSurfaceCreated = true;
                openCamera();
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                LogUtils.d(TAG, "surfaceChanged");
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                LogUtils.d(TAG, "surfaceDestroyed");
                mSurfaceCreated = false;
                closeCamera();
            }
        });
    }

    private void openCamera() {
        LogUtils.d(TAG, "openCamera");
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
            parameters.setPreviewSize(svPreview.getWidth(), svPreview.getHeight());
            // 设置预览照片帧数
            parameters.setPreviewFpsRange(4, 10);
            // 设置图片格式
            parameters.setPictureFormat(ImageFormat.JPEG);
            // 设置图片质量
            parameters.setJpegQuality(90);
//        parameters.set("jpeg-quality", 90)
            // 设置照片大小
            parameters.setPictureSize(svPreview.getWidth(), svPreview.getHeight());
            // 设置目标
            mCamera.setPreviewDisplay(mSurfaceHolder);
            // 开始预览
            mCamera.startPreview();
        } catch (IOException e) {
            LogUtils.printStackTrace(TAG, e);
        }

    }

    private void closeCamera() {
        LogUtils.d(TAG, "closeCamera");
        if (mCamera != null) {
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }
    }

    private void onRecordClick() {
        Log.d(TAG, "onRecordClick, mIsRecording: " + mIsRecording);
        if (mIsRecording) { // 正在录制
            mIsRecording = false;
            tvRecord.setBackground(ResUtil.getDrawable(R.drawable.common_shape_16round_green));
            tvRecord.setText("开始录制");
            if (mMediaRecorder != null) {
                mMediaRecorder.stop();
                mMediaRecorder.release();
                mMediaRecorder = null;
            }
            LogUtils.d(TAG, "原始视频保存路径：" + mOriginFilePath);
            ToastUtils.showLong("原始视频保存路径：" + mOriginFilePath);
            mOriginFileName = mOriginFilePath;
            mOriginFilePath = null;
            mCamera.lock();
            mCamera.startPreview();
        } else {
            if (StringUtils.isEmpty(mOriginFilePath)) {
                mOriginFilePath = getOutputDir() + File.separator + "vid_origin.mp4";
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
            CamcorderProfile profile = CamcorderProfile.get(CamcorderProfile.QUALITY_LOW);
            mMediaRecorder.setProfile(profile);
            mMediaRecorder.setOutputFile(mOriginFilePath);
            mMediaRecorder.setPreviewDisplay(mSurfaceHolder.getSurface());
            try {
                mMediaRecorder.prepare();
                mMediaRecorder.start();
            } catch (Exception e) {
                LogUtils.printStackTrace(TAG, e);
            }
        }
    }

    private String getOutputDir() {
        File dir = FileUtils.makeDirs(PathUtils.getExternalDcimPath() + File.separator + AppUtils.getAppPackageName());
        mOutputDir = dir.getPath();
        return mOutputDir;
    }

    private void onStartExtractorClick() {
        LogUtils.d(TAG, "onStartExtractorClick");
        if (StringUtils.isEmpty(mOriginFileName)) {
            ToastUtils.showLong("原始文件不存在");
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean result = process();
                LogUtils.d(TAG, "process result: " + result);
            }
        }).start();
    }


    private boolean process() {
        mMediaExtractor = new MediaExtractor();
        try {
            mMediaExtractor.setDataSource(mOriginFileName);
            int trackCount = mMediaExtractor.getTrackCount();
            int mVideoTrackIndex = -1;
            int frameRate = 0;
            for (int i = 0; i < trackCount; i++) {
                MediaFormat mediaFormat = mMediaExtractor.getTrackFormat(i);
                String mime = mediaFormat.getString(MediaFormat.KEY_MIME);
                LogUtils.d(TAG, "mime: " + mime);
                if (StringUtils.isEmpty(mime) || !mime.startsWith("video/")) {
                    continue;
                }
                frameRate = mediaFormat.getInteger(MediaFormat.KEY_FRAME_RATE);
                mMediaExtractor.selectTrack(i);
                mMediaMuxer = new MediaMuxer(mOutputDir + mExtractorVideoFileName, MediaMuxer.OutputFormat.MUXER_OUTPUT_MPEG_4);
                mVideoTrackIndex = mMediaMuxer.addTrack(mediaFormat);
                mMediaMuxer.start();
            }
            if (mMediaMuxer == null) {
                return false;
            }

            MediaCodec.BufferInfo info = new MediaCodec.BufferInfo();
            info.presentationTimeUs = 0;
            ByteBuffer buffer = ByteBuffer.allocate(500 * 1024);
            int sampleSize = 0;
            while ((sampleSize = mMediaExtractor.readSampleData(buffer, 0)) > 0) {

                info.offset = 0;
                info.size = sampleSize;
                info.flags = MediaCodec.BUFFER_FLAG_SYNC_FRAME;
                info.presentationTimeUs += 1000 * 1000 / frameRate;
                mMediaMuxer.writeSampleData(mVideoTrackIndex, buffer, info);
                mMediaExtractor.advance();
            }

            mMediaExtractor.release();

            mMediaMuxer.stop();
            mMediaMuxer.release();

            return true;
        } catch (Exception e) {
            LogUtils.printStackTrace(TAG, e);
            return false;
        }
    }

    /**
     * 分离音频
     *
     * @return true if extract audio success
     */
    private boolean extractAudio() {

        return false;
    }

    /**
     * 分离视频
     *
     * @return true if extract audio success
     */
    private boolean extractVideo() {
        return false;
    }

    private void onStartMuxerClick() {
        LogUtils.d(TAG, "onStartMuxerClick");
    }
}
