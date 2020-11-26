package com.rainmonth.player.gsy.base.player;


import com.rainmonth.player.gsy.model.GSYModel;

// todo 替换 IMediaPlayer
import tv.danmaku.ijk.media.player.IMediaPlayer;

/**
 播放器初始化成果回调
 */
public interface IPlayerInitSuccessListener {
    void onPlayerInitSuccess(IMediaPlayer player, GSYModel model);
}
