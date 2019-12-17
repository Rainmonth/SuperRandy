package com.rainmonth.music.core.manager;


/**
 * @author 张豪成
 * @date 2019-12-05 20:18
 */
public abstract class BasePlayerManager implements IPlayerManager {
    protected OnPlayerInitSuccessListener mListener;

    public OnPlayerInitSuccessListener getOnPlayerInitSuccessListener() {
        return mListener;
    }

    public void setOnPlayerInitSuccessListener(OnPlayerInitSuccessListener listener) {
        this.mListener = listener;
    }

    protected void notifyInitPlayerSuccess() {
        if (mListener != null) {
            mListener.onPlayerInitSuccess();
        }
    }
}
