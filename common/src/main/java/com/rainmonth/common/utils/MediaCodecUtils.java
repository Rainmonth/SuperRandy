package com.rainmonth.common.utils;

import android.media.MediaCodec;
import android.media.MediaExtractor;
import android.media.MediaFormat;

import com.rainmonth.utils.StringUtils;
import com.rainmonth.utils.log.LogUtils;

import java.io.File;
import java.io.IOException;

/**
 * 参考链接： https://github.com/isuperqiang/MultiMediaLearning
 * @author RandyZhang
 * @date 2021/1/15 11:41 PM
 */
public class MediaCodecUtils {

    public static final String TAG = MediaCodecUtils.class.getSimpleName();

    /**
     * 将 AAC 格式解码成 pcm 数据
     *
     * @param aacFile aac文件
     * @param pcmFile pcm文件
     * @throws IOException
     */
    public static void decodeAacToPcm(File aacFile, File pcmFile) throws IOException {
        MediaExtractor mediaExtractor = new MediaExtractor();
        mediaExtractor.setDataSource(aacFile.getAbsolutePath());
        MediaFormat mediaFormat = null;
        int trackIndex = -1;
        getMediaFormatAndTrackIndex(mediaExtractor, mediaFormat, trackIndex, "audio/");
        if (mediaFormat == null || trackIndex == -1) {
            mediaExtractor.release();
            return;
        }
        mediaExtractor.selectTrack(trackIndex);
        String mime = mediaFormat.getString(MediaFormat.KEY_MIME);
        LogUtils.i(TAG, "decodeAacToPcm: mimeType: " + mime);
        MediaCodec codec = MediaCodec.createDecoderByType(mime); // 新建之后
        codec.configure(mediaFormat, null, null, 0);
        codec.start();
    }

    private static void getMediaFormatAndTrackIndex(MediaExtractor mediaExtractor, MediaFormat outMediaFormat, int outTrackIndex, String mimeType) {
        int trackCount = mediaExtractor.getTrackCount();
        for (int i = 0; i < trackCount; i++) {
            MediaFormat mediaFormat = mediaExtractor.getTrackFormat(i);
            String mime = mediaFormat.getString(MediaFormat.KEY_MIME);
            if (!StringUtils.isEmpty(mime) && mime.startsWith(mimeType)) {
                outMediaFormat = mediaFormat;
                outTrackIndex = i;
                break;
            }
        }
        if (outMediaFormat != null) {
            LogUtils.d(TAG, "trackIndex: " + outTrackIndex);
            LogUtils.d(TAG, "mediaFormat: " + outMediaFormat.toString());
        }
    }


}
