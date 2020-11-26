package com.rainmonth.player.base.player;

import com.rainmonth.player.model.GSYModel;

/**
 * 播放器差异管理接口
 */
public abstract class BasePlayerManager implements IPlayerManager {

    protected IPlayerInitSuccessListener mPlayerInitSuccessListener;

    public IPlayerInitSuccessListener getPlayerPreparedSuccessListener() {
        return mPlayerInitSuccessListener;
    }

    public void setPlayerInitSuccessListener(IPlayerInitSuccessListener listener) {
        this.mPlayerInitSuccessListener = listener;
    }

    protected void initSuccess(GSYModel gsyModel) {
        if (mPlayerInitSuccessListener != null) {
            mPlayerInitSuccessListener.onPlayerInitSuccess(getMediaPlayer(), gsyModel);
        }
    }
}
