package com.rainmonth.player.activity.other;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.widget.TextView;

import com.rainmonth.common.base.BaseActivity;
import com.rainmonth.common.utils.PathUtils;
import com.rainmonth.common.utils.PermissionUtils;
import com.rainmonth.common.utils.ToastUtils;
import com.rainmonth.common.utils.constant.PermissionConstants;
import com.rainmonth.player.R;

/**
 * {@link AudioRecord}、{@link android.media.MediaRecorder}
 *
 * @author RandyZhang
 * @date 2020/12/21 4:36 PM
 */
public class AudioRecordExampleActivity extends BaseActivity {

    private TextView tvStartRecord;
    private TextView tvStopRecord;

    private RandyAudioRecorder mRecorder;

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

        tvStartRecord.setOnClickListener(v -> onStartRecordClick());
        tvStopRecord.setOnClickListener(v -> onStopRecordClick());
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
}
