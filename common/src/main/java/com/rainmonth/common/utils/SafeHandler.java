package com.rainmonth.common.utils;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.rainmonth.common.utils.log.LogUtils;

/**
 * @author RandyZhang
 * @date 2020/9/24 10:57 AM
 */
public class SafeHandler extends Handler {

    private boolean isDestroyed = false;

    public SafeHandler() {
    }

    public SafeHandler(Callback callback) {
        super(callback);
    }

    public SafeHandler(Looper looper) {
        super(looper);
    }

    public SafeHandler(Looper looper, Callback callback) {
        super(looper, callback);
    }

    @Override
    public boolean sendMessageAtTime(Message msg, long uptimeMillis) {
        if (isDestroyed) {
            return false;
        }
        return super.sendMessageAtTime(msg, uptimeMillis);
    }

    @Override
    public void dispatchMessage(Message msg) {
        if (isDestroyed) {
            return;
        }
        try {
            super.dispatchMessage(msg);
        } catch (Exception e) {
            LogUtils.e(e.getMessage());
        }
        clearMessages(msg);
    }

    private void clearMessages(Message msg) {
        if (msg == null) {
            return;
        }
        msg.what = msg.arg1 = msg.arg2 = 0;
        msg.obj = null;
        msg.replyTo = null;
        msg.setTarget(null);
    }

    public void destroy() {
        removeCallbacksAndMessages(null);
        isDestroyed = true;
    }
}
