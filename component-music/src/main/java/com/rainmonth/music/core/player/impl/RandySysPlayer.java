package com.rainmonth.music.core.player.impl;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.text.TextUtils;
import android.view.Surface;
import android.view.SurfaceHolder;

import com.rainmonth.music.core.player.BasePlayer;
import com.rainmonth.music.core.player.IPlayer;
import com.socks.library.KLog;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.Map;

/**
 * BasePlayer的系统自带播放器实现
 * 系统播放器的特点：
 * - 差异性明显，这个从不同版本MediaPlayer的源代码就可以看出
 * - 基于状态管理的，一旦状态没处理好就会发生{@link IllegalStateException}
 *
 * @author 张豪成
 * @date 2019-12-05 20:20
 */
public class RandySysPlayer extends BasePlayer {
    public static final String Tag = RandySysPlayer.class.getSimpleName();
    private MediaPlayer mInternalPlayer;
    private RandySysPlayerListenerHolder listenerHolder;

    public RandySysPlayer() {
        mInternalPlayer = new MediaPlayer();

        listenerHolder = new RandySysPlayerListenerHolder(this);
        // 监听器设置
        attachPlayerListeners();
    }

    @Override
    public void setDisplay(SurfaceHolder surfaceHolder) {
        try {
            mInternalPlayer.setDisplay(surfaceHolder);
        } catch (IllegalStateException e) {
            KLog.e(Tag, e);
        }
    }

    @Override
    public void setSurface(Surface surface) {
        try {
            mInternalPlayer.setSurface(surface);
        } catch (IllegalStateException e) {
            KLog.e(Tag, e);
        }
    }

    @Override
    public void setDataSource(String path) {
        try {
            Uri uri = Uri.parse(path);
            String scheme = uri.getScheme();
            if (!TextUtils.isEmpty(scheme) && "file".equalsIgnoreCase(scheme)) {
                mInternalPlayer.setDataSource(uri.getPath());
            } else {
                mInternalPlayer.setDataSource(path);
            }
        } catch (IOException e) {
            KLog.e(Tag, e);
        }
    }

    @Override
    public void setDataSource(Context context, Uri uri) {
        try {
            mInternalPlayer.setDataSource(context, uri);
        } catch (IOException e) {
            KLog.e(Tag, e);
        }
    }

    @Override
    public void setDataSource(Context context, Uri uri, Map<String, String> headers) {
        try {
            mInternalPlayer.setDataSource(context, uri, headers);
        } catch (IOException e) {
            KLog.e(Tag, e);
        }
    }

    @Override
    public void prepare() {
        try {
            mInternalPlayer.prepare();
        } catch (IllegalStateException | IOException e) {
            KLog.e(Tag, e);
        }
    }

    @Override
    public void prepareAsync() {
        try {
            mInternalPlayer.prepareAsync();
        } catch (IllegalStateException e) {
            KLog.e(Tag, e);
        }
    }

    @Override
    public void start() {
        try {
            mInternalPlayer.start();
        } catch (IllegalStateException e) {
            KLog.e(Tag, e);
        }
    }

    @Override
    public void pause() {
        try {
            mInternalPlayer.pause();
        } catch (IllegalStateException e) {
            KLog.e(Tag, e);
        }
    }

    @Override
    public void stop() {
        try {
            mInternalPlayer.stop();
        } catch (IllegalStateException e) {
            KLog.e(Tag, e);
        }
    }

    @Override
    public int getVideoWidth() {
        try {
            return mInternalPlayer.getVideoWidth();
        } catch (IllegalStateException e) {
            KLog.e(Tag, e);
            return 0;
        }
    }

    @Override
    public int getVideoHeight() {
        try {
            return mInternalPlayer.getVideoHeight();
        } catch (IllegalStateException e) {
            KLog.e(Tag, e);
            return 0;
        }
    }

    @Override
    public boolean isPlaying() {
        try {
            return mInternalPlayer.isPlaying();
        } catch (IllegalStateException e) {
            KLog.e(Tag, e);
            return false;
        }
    }

    @Override
    public void seekTo(long milliseconds) {
        try {
            mInternalPlayer.seekTo((int) milliseconds);
        } catch (IllegalStateException e) {
            KLog.e(Tag, e);
        }
    }

    @Override
    public void release() {
        mInternalPlayer.release();
        resetListeners();
        // todo 重置播放资源
        attachPlayerListeners();
    }

    @Override
    public void reset() {
        mInternalPlayer.reset();

        resetListeners();
        // todo 重置播放资源
        attachPlayerListeners();
    }

    @Override
    public long getCurrentPosition() {
        try {
            return mInternalPlayer.getCurrentPosition();
        } catch (IllegalStateException e) {
            KLog.e(Tag, e);
            return 0;
        }
    }

    @Override
    public long getDuration() {
        try {
            return mInternalPlayer.getDuration();
        } catch (IllegalStateException e) {
            KLog.e(Tag, e);
            return 0;
        }
    }

    @Override
    public void getSourceInfo() {
        // todo 获取播放源的信息，如果流的格式、码率等
    }

    /**
     * 绑定播放监听器
     */
    private void attachPlayerListeners() {
        setOnPreparedListener(listenerHolder);
        setOnCompletionListener(listenerHolder);
        setOnBufferingUpdateListener(listenerHolder);
        setOnSeekCompleteListener(listenerHolder);
        setOnVideoSizeChangedListener(listenerHolder);
        setOnInfoListener(listenerHolder);
        setOnErrorListener(listenerHolder);
    }

    private class RandySysPlayerListenerHolder implements
            IPlayer.OnPreparedListener,
            IPlayer.OnCompletionListener,
            IPlayer.OnBufferingUpdateListener,
            IPlayer.OnSeekCompleteListener,
            IPlayer.OnVideoSizeChangedListener,
            IPlayer.OnInfoListener,
            IPlayer.OnErrorListener {

        private final WeakReference<RandySysPlayer> mWeakPlayer;

        RandySysPlayerListenerHolder(RandySysPlayer player) {
            this.mWeakPlayer = new WeakReference<>(player);
        }

        @Override
        public void onPrepared(IPlayer player) {
            RandySysPlayer self = mWeakPlayer.get();
            if (self == null) {
                return;
            }
            notifyOnPrepared();
        }

        @Override
        public void onCompletion(IPlayer player) {
            RandySysPlayer self = mWeakPlayer.get();
            if (self == null) {
                return;
            }
            notifyOnCompletion();
        }

        @Override
        public void onBufferingUpdate(IPlayer player, int percentage) {
            RandySysPlayer self = mWeakPlayer.get();
            if (self == null) {
                return;
            }
            notifyOnBufferingUpdate(percentage);
        }

        @Override
        public void onSeekComplete(IPlayer player) {
            RandySysPlayer self = mWeakPlayer.get();
            if (self == null) {
                return;
            }
            notifyOnSeekComplete();
        }

        @Override
        public void onVideoSizeChanged(IPlayer player, int width, int height) {
            RandySysPlayer self = mWeakPlayer.get();
            if (self == null) {
                return;
            }
            notifyOnVideoSizeChanged(width, height);
        }

        @Override
        public boolean onInfo(IPlayer player, int what, int extra) {
            RandySysPlayer self = mWeakPlayer.get();
            return self != null && notifyOnInfo(what, extra);
        }

        @Override
        public boolean onError(IPlayer player, int errorCode, int extra) {
            RandySysPlayer self = mWeakPlayer.get();
            return self != null && notifyOnError(errorCode, extra);
        }

    }
}
