package com.rainmonth.player.gsy.listener;

/**
 * 进度回调
 */
public interface GSYVideoProgressListener {
    /**
     * @param progress        当前播放进度（暂停后再播放可能会有跳动）
     * @param secProgress     当前内存缓冲进度（可能会有0值）
     * @param currentPosition 当前播放位置（暂停后再播放可能会有跳动）
     * @param duration        总时长
     */
    void onProgress(int progress, int secProgress, int currentPosition, int duration);
}
