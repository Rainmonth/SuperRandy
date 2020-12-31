package com.rainmonth.player.activity.other;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Environment;
import android.text.format.DateFormat;
import android.widget.TextView;

import com.rainmonth.common.base.BaseActivity;
import com.rainmonth.utils.FileUtils;
import com.rainmonth.utils.PathUtils;
import com.rainmonth.utils.PermissionUtils;
import com.rainmonth.utils.ToastUtils;
import com.rainmonth.utils.constant.PermissionConstants;
import com.rainmonth.utils.log.LogUtils;
import com.rainmonth.player.R;

import java.io.File;
import java.util.Calendar;
import java.util.Locale;

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

    private RandyAudioRecorder mRecorder;

    private MediaRecorder mMediaRecorder;

    @Override
    public void initToolbar(int colorResId) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.player_activity_audio_record_example;
    }

    @Override
    protected void initViewsAndEvents() {
        mRecorder = new RandyAudioRecorder.Builder()
                .audioSource(MediaRecorder.AudioSource.MIC)
                .sampleRate(8000)
                .channel(AudioFormat.CHANNEL_IN_MONO)
                .audioFormat(AudioFormat.ENCODING_PCM_16BIT)
                .dirPath(PathUtils.getExternalAppAudioRecordPath())
                .srcFileName("test_" + System.currentTimeMillis() + ".pcm")
                .disFileName("test_" + System.currentTimeMillis() + ".wav")
                .build();

        tvStartRecord = findViewById(R.id.tv_start_record);
        tvStopRecord = findViewById(R.id.tv_stop_record);

        tvMedeaRecorderStart = findViewById(R.id.tv_start_record1);
        tvMediaRecorderStop = findViewById(R.id.tv_stop_record1);

        tvStartRecord.setOnClickListener(v -> onStartRecordClick());
        tvStopRecord.setOnClickListener(v -> onStopRecordClick());

        tvMedeaRecorderStart.setOnClickListener(v -> onMediaRecorderStartClick());
        tvMediaRecorderStop.setOnClickListener(v -> onMediaRecorderStopClick());
    }


    private void onStartRecordClick() {
        PermissionUtils.permission(PermissionConstants.MICROPHONE).callback(new PermissionUtils.SimpleCallback() {
            @Override
            public void onGranted() {
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

    private void onMediaRecorderStartClick() {
        startMediaRecorderRecord();
    }

    private File outPutFile;

    private void startMediaRecorderRecord() {
        if (mMediaRecorder == null) {
            mMediaRecorder = new MediaRecorder();
        }
        try {
            mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);// 设置麦克风
            mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
            mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
            String fileName = DateFormat.format("yyyyMMdd_HHmmss", Calendar.getInstance(Locale.CHINA)) + ".m4a";
            File outPutDir = FileUtils.makeDirs(PathUtils.getExternalAppAudioRecordPath());
            outPutFile = new File(outPutDir, fileName);

            mMediaRecorder.setOutputFile(outPutFile.getPath());
            mMediaRecorder.prepare();
            mMediaRecorder.start();
        } catch (Exception e) {
            LogUtils.printStackTrace(TAG, e);
        }
    }

    private void onMediaRecorderStopClick() {
        stopMediaRecorderRecord();
    }

    private void stopMediaRecorderRecord() {
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
}
