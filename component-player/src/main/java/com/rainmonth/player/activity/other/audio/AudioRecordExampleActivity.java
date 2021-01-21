package com.rainmonth.player.activity.other.audio;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.MediaRecorder;
import android.widget.TextView;

import com.rainmonth.common.base.BaseActivity;
import com.rainmonth.player.R;
import com.rainmonth.utils.FileUtils;
import com.rainmonth.utils.PathUtils;
import com.rainmonth.utils.PermissionUtils;
import com.rainmonth.utils.TimeUtils;
import com.rainmonth.utils.ToastUtils;
import com.rainmonth.utils.constant.PermissionConstants;
import com.rainmonth.utils.log.LogUtils;

import java.io.File;

/**
 * {@link AudioRecord}、{@link android.media.MediaRecorder}
 *
 * @author RandyZhang
 * @date 2020/12/21 4:36 PM
 */
public class AudioRecordExampleActivity extends BaseActivity {

    private TextView tvStartRecord;
    private TextView tvStopRecord;

    private TextView tvMedeaRecorderStart;
    private TextView tvMediaRecorderStop;

    private TextView tvAudioTrackStart;
    private TextView tvAudioTrackStop;
    //注意： 初始化要放在权限请求之后，否则会抱java.lang.IllegalStateException: startRecording() called on an uninitialized AudioRecord.
    private AudioRecordWrapper mRecorder;
    //注意： 初始化要放在权限请求之后
    private MediaRecorder mMediaRecorder;
    //注意： 初始化要放在权限请求之后
    private AudioTrackWrapper mAudioTrackWrapper;     // 直接读取播放录制的PCM文件

    private File pcmOutputFile;
    private String pcmFileName;
    private String wavFileName;

    @Override
    public void initToolbar(int colorResId) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.player_activity_audio_record_example;
    }

    @Override
    protected void initViewsAndEvents() {
        pcmFileName = "test_" + TimeUtils.getNowString() + ".pcm";
        wavFileName = "test_" + TimeUtils.getNowString() + ".wav";
        pcmOutputFile = new File(PathUtils.getExternalAppAudioRecordPath(), pcmFileName);

        tvStartRecord = findViewById(R.id.tv_start_record);
        tvStopRecord = findViewById(R.id.tv_stop_record);

        tvMedeaRecorderStart = findViewById(R.id.tv_start_record1);
        tvMediaRecorderStop = findViewById(R.id.tv_stop_record1);

        tvAudioTrackStart = findViewById(R.id.tv_start_record2);
        tvAudioTrackStop = findViewById(R.id.tv_stop_record2);

        tvStartRecord.setOnClickListener(v -> onStartRecordClick());
        tvStopRecord.setOnClickListener(v -> onStopRecordClick());

        tvMedeaRecorderStart.setOnClickListener(v -> onMediaRecorderStartClick());
        tvMediaRecorderStop.setOnClickListener(v -> onMediaRecorderStopClick());

        tvAudioTrackStart.setOnClickListener(v -> onAudioTrackStartClick());
        tvAudioTrackStop.setOnClickListener(v -> onAudioTrackStopClick());
    }


    private void onStartRecordClick() {
        PermissionUtils.permission(PermissionConstants.MICROPHONE).callback(new PermissionUtils.SimpleCallback() {
            @Override
            public void onGranted() {
                if (mRecorder == null) {
                    mRecorder = new AudioRecordWrapper.Builder()
                            .audioSource(MediaRecorder.AudioSource.MIC)
                            .sampleRate(8000)
                            .channel(AudioFormat.CHANNEL_IN_MONO)
                            .audioFormat(AudioFormat.ENCODING_PCM_16BIT)
                            .dirPath(PathUtils.getExternalAppAudioRecordPath())
                            .srcFileName(pcmFileName)
                            .disFileName(wavFileName)
                            .build();
                }
                mRecorder.startRecord();
            }

            @Override
            public void onDenied() {
                ToastUtils.showLong("您拒绝了录音权限，无法录音！");
            }
        }).request();
    }

    private void onStopRecordClick() {
        mRecorder.stopRecord();
    }

    private File outPutFile;

    private void onMediaRecorderStartClick() {
        PermissionUtils.permission(PermissionConstants.MICROPHONE).callback(new PermissionUtils.SimpleCallback() {
            @Override
            public void onGranted() {
                if (mMediaRecorder == null) {
                    mMediaRecorder = new MediaRecorder();
                }
                try {
                    mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);// 设置麦克风
                    mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
                    mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
                    String fileName = TimeUtils.getNowString() + ".m4a";
                    File outPutDir = FileUtils.makeDirs(PathUtils.getExternalAppAudioRecordPath());
                    outPutFile = new File(outPutDir, fileName);

                    mMediaRecorder.setOutputFile(outPutFile.getPath());
                    mMediaRecorder.prepare();
                    mMediaRecorder.start();
                } catch (Exception e) {
                    LogUtils.printStackTrace(TAG, e);
                }
            }

            @Override
            public void onDenied() {
                ToastUtils.showLong("您拒绝了录音权限，无法录音！");
            }
        }).request();

    }

    private void onMediaRecorderStopClick() {
        LogUtils.d(TAG, "onMediaRecorderStopClick");
        try {
            if (mMediaRecorder != null) {
                mMediaRecorder.stop();
                mMediaRecorder.release();
                mMediaRecorder = null;
            }
        } catch (Exception e) {
            LogUtils.printStackTrace(TAG, e);
            if (mMediaRecorder != null) {
                mMediaRecorder.reset();
                mMediaRecorder.release();
                mMediaRecorder = null;
            }
            // 删除掉异常停止前产生的文件
            FileUtils.deleteFile(outPutFile);
        }
    }

    private void onAudioTrackStartClick() {
        LogUtils.d(TAG, "onAudioTrackStartClick");
        PermissionUtils.permission(PermissionConstants.MICROPHONE).callback(new PermissionUtils.SimpleCallback() {
            @Override
            public void onGranted() {
                if (mAudioTrackWrapper == null) {
                    mAudioTrackWrapper = new AudioTrackWrapper.Builder()
                            .setStreamType(AudioManager.STREAM_MUSIC)
                            .setSampleRateInHz(44100)
                            .setChannelConfig(AudioFormat.CHANNEL_OUT_MONO) // 这里需要使用CHANNEL_OUT_MONO，而不是CHANNEL_IN_MONO
                            .setAudioFormat(AudioFormat.ENCODING_PCM_16BIT)
                            .setMode(AudioTrack.MODE_STATIC)
                            .build();
                }

                // 设置PCM数据文件的路径
                // 将PCM数据读入到InputStream
                // 利用AudioTrack播放 上面的InputStream
                mAudioTrackWrapper.start(pcmOutputFile);
            }

            @Override
            public void onDenied() {
                ToastUtils.showLong("您拒绝了录音权限，无法录音！");
            }
        }).request();
    }

    private void onAudioTrackStopClick() {
        LogUtils.d(TAG, "onAudioTrackStopClick");
        if (mAudioTrackWrapper != null) {
            mAudioTrackWrapper.stop();
        }
    }

}