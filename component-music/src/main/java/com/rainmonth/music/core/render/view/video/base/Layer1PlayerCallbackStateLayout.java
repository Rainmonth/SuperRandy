package com.rainmonth.music.core.render.view.video.base;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.TintContextWrapper;

import com.rainmonth.common.utils.AppUtils;
import com.rainmonth.common.utils.NetworkUtils;
import com.rainmonth.music.R;
import com.rainmonth.music.core.bridge.IVideoViewMgrBridge;
import com.rainmonth.music.core.listener.RandyPlayerListener;
import com.rainmonth.music.core.listener.VideoViewCallback;
import com.socks.library.KLog;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * 视频回调及状态管理层
 * 实现了
 * {@link com.rainmonth.music.core.helper.MeasureHelper.MeasureFormVideoParamsListener}、
 * {@link Layer0PlayerDrawLayout#showPauseCover()}、
 * {@link Layer0PlayerDrawLayout#releasePauseCover()}等方法
 *
 * @author 张豪成
 * @date 2019-12-17 18:00
 */
public abstract class Layer1PlayerCallbackStateLayout extends Layer0PlayerDrawLayout implements RandyPlayerListener {
    private static final String TAG = Layer1PlayerCallbackStateLayout.class.getSimpleName();

    // 网络状态——正常
    public static final String NET_STATE_NORMAL = "NORMAL";

    public static final int STATE_NORMAL = 0;
    // 准备状态
    public static final int STATE_PREPARING = 1;
    // 播放状态
    public static final int STATE_PLAYING = 2;
    // 开始缓存播放
    public static final int STATE_PLAYING_BUFFERING_START = 3;
    // 暂停状态
    public static final int STATE_PAUSE = 4;
    // 自动完成状态
    public static final int STATE_AUTO_COMPLETE = 5;
    // 错误状态
    public static final int STATE_ERROR = 6;
    // 调用setup方法的间隔2000毫秒
    public static final int SET_UP_VIEW_DELAY_TIME = 2000;

    //<editor-fold>变量定义
    // 当前的播放状态
    protected int mCurrentState = -1;
    protected int mPlayPosition = -22;
    // 当前是否全屏播放
    protected boolean mIsCurrentFullscreen;
    // 宽、高
    protected int mScreenWidth, mScreenHeight;
    // 缓存的进度
    protected int mBufferPoint;
    // 记录缓存前的播放状态
    protected int mStateBeforeBuffering = -1;
    // 开始播放的位置
    protected long mStartPlayPosition = -1;
    // 当前的播放位置
    protected long mCurrentPosition;
    // 保存切换时的时间，避免频繁契合
    protected long mSetupViewTimeMillis = 0;
    // 播放速度
    protected float mSpeed = 1f;
    // 是否边播放边缓存
    protected boolean mIsCachePlay = false;
    // 是否循环播放
    protected boolean mIsLoop = false;
    // 是否播放过
    protected boolean mIsHadPlay = false;
    // 是否发送了网络改变
    protected boolean mIsNetChanged = false;
    // 是否不变调
    protected boolean mSoundTouch = false;
    // 是否需要显示暂停锁定效果
    protected boolean mShowPauseCover = true;
    // 是否准备完成前调用了暂停
    protected boolean mPauseBeforePrepared = false;
    // Prepared之后是否自动开始播放
    protected boolean mStartAfterPrepared = true;
    // Prepared
    protected boolean mHadPrepared = false;
    // 当失去音频焦点是否释放播放器
    protected boolean mReleaseWhenLossAudio = true;
    // 播放的tag，防止错误，因为普通的url也可能重复
    protected String mPlayTag = "";
    // 上下文
    protected Context mContext;
    // 原始的的url
    protected String mOriginUrl;
    //转化后的URL
    protected String mUrl;
    //标题
    protected String mTitle;
    //网络状态
    protected String mNetSate = NET_STATE_NORMAL;
    // 是否需要覆盖拓展类型
    protected String mOverrideExtension;
    //缓存路径，可不设置
    protected File mCachePath;
    // 视频回调
    protected VideoViewCallback mVideoViewCallBack;
    // http request header
    protected Map<String, String> mMapHeadData = new HashMap<>();
    // todo 网络监听
//    protected NetInfoModule mNetInfoModule;
    // 音频焦点管理
    protected AudioManager mAudioManager;
    //</editor-fold>

    public Layer1PlayerCallbackStateLayout(Context context) {
        super(context);
        init(context);
    }

    public Layer1PlayerCallbackStateLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public Layer1PlayerCallbackStateLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public Layer1PlayerCallbackStateLayout(@NonNull Context context, boolean isFullscreen) {
        super(context);
        this.mIsCurrentFullscreen = isFullscreen;
        init(context);
    }

    protected void init(Context context) {
        if (getActivityCtx(context) != null) {
            this.mContext = getActivityCtx(context);
        } else {
            this.mContext = context;
        }

        initInflate(mContext);

        mRenderViewParent = findViewById(R.id.player_render_view_container);
        if (isInEditMode()) {
            return;
        }
        mScreenWidth = mContext.getResources().getDisplayMetrics().widthPixels;
        mScreenHeight = mContext.getResources().getDisplayMetrics().heightPixels;
        mAudioManager = (AudioManager) mContext.getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
    }

    protected Activity getActivityCtx(Context context) {
        if (context == null) {
            return null;
        } else if (context instanceof Activity) {
            return (Activity) context;
        } else if (context instanceof TintContextWrapper) {
            return getActivityCtx(((TintContextWrapper) context).getBaseContext());
        } else if (context instanceof ContextWrapper) {
            return getActivityCtx(((ContextWrapper) context).getBaseContext());
        }

        return null;
    }

    protected void initInflate(Context context) {
        try {
            View.inflate(context, getLayoutId(), this);
        } catch (Exception e) {
            KLog.e(TAG, e);
        }
    }

    /**
     * 初始化处理（带header
     */
    public boolean setUp(String url, boolean cacheWithPlay, File cachePath, Map<String, String> mapHeadData, String title) {
        if (setUp(url, cacheWithPlay, cachePath, title)) {
            if (this.mMapHeadData != null) {
                this.mMapHeadData.clear();
            } else {
                this.mMapHeadData = new HashMap<>();
            }
            if (mapHeadData != null) {
                this.mMapHeadData.putAll(mapHeadData);
            }
            return true;
        }
        return false;
    }

    /**
     * 初始化处理（不带header，默认更新UI状态）
     */
    protected boolean setUp(String url, boolean cacheWithPlay, File cachePath, String title) {
        return setUp(url, cacheWithPlay, cachePath, title, true);
    }

    /**
     * 初始化处理（不带header，可以控制是否更新UI状态）
     */
    protected boolean setUp(String url, boolean cacheWithPlay, File cachePath, String title, boolean changeState) {
        this.mOriginUrl = url;
        mIsCachePlay = cacheWithPlay;
        mCachePath = cachePath;
        if (isCurrentPlayerListener() && (System.currentTimeMillis() - mSetupViewTimeMillis) < SET_UP_VIEW_DELAY_TIME) {
            return false;
        }
        mCurrentState = STATE_NORMAL;
        this.mUrl = url;
        this.mTitle = title;
        if (changeState) {
            updateStateAndUi(mCurrentState);
        }
        return true;
    }

    /**
     * 当前的播放监听器是不是自身
     *
     * @return
     */
    protected boolean isCurrentPlayerListener() {
        return getVideoViewMgrBridge().listener() != null && getVideoViewMgrBridge().listener() == this;
    }

    /**
     * 开始播放逻辑处理
     */
    protected void startButtonLogic() {
        if (mVideoViewCallBack != null && mCurrentState == STATE_NORMAL) {
            KLog.d(TAG, "startButtonLogic()->onClickStartIcon");
            mVideoViewCallBack.onClickStartIcon(mOriginUrl, mTitle, this);
        } else if (mVideoViewCallBack != null) {
            KLog.d(TAG, "startButtonLogic()->onClickStartError");
            mVideoViewCallBack.onClickStartError(mOriginUrl, mTitle, this);
        }
        prepareVideo();
    }

    protected void prepareVideo() {
        startPrepare();
    }

    protected void startPrepare() {
        if (getVideoViewMgrBridge().listener() != null) {
            getVideoViewMgrBridge().listener().onCompletion();
        }
        if (mVideoViewCallBack != null) {
            mVideoViewCallBack.onStartPrepared(mOriginUrl, mTitle, this);
        }
        getVideoViewMgrBridge().setListener(this);
        getVideoViewMgrBridge().setPlayTag(mPlayTag);
        getVideoViewMgrBridge().setPlayPosition(mPlayPosition);

        mAudioManager.requestAudioFocus(mAudioFocusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
        // 保持屏幕常亮
        AppUtils.requestKeepScreenOn(mContext);
        mStateBeforeBuffering = -1;
        getVideoViewMgrBridge().prepare(mUrl, mMapHeadData, mIsLoop, mSpeed, mIsCachePlay, mCachePath, mOverrideExtension);

        updateStateAndUi(STATE_PREPARING);
    }

    //<editor-fold>音频焦点处理
    protected AudioManager.OnAudioFocusChangeListener mAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            switch (focusChange) {
                case AudioManager.AUDIOFOCUS_GAIN:
                    onGainAudio();
                    break;
                case AudioManager.AUDIOFOCUS_LOSS:
                    onLossAudio();
                    break;
                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                    onLossTransientAudio();
                    break;
                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                    onLossTransientCanDuck();
                    break;
            }
        }
    };

    /**
     * 获得焦点处理
     */
    protected void onGainAudio() {
        KLog.d(TAG, "onGainAudio");
    }

    /**
     * 失去焦点，并不知道何时能重新获取到焦点
     */
    protected void onLossAudio() {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                if (mReleaseWhenLossAudio) {
                    releaseAllVideos();
                } else {
                    onVideoPause();
                }
            }
        });
    }

    /**
     * 失去焦点，但很快就会重新获取焦点（如通知音到来的时候）
     */
    protected void onLossTransientAudio() {
        try {
            onVideoPause();
        } catch (Exception e) {
            KLog.e(TAG, e);
        }
    }

    /**
     * 失去焦点，但可以继续定音量播放
     */
    protected void onLossTransientCanDuck() {
        KLog.d(TAG, "onLossTransientCanDuck");
    }
    //</editor-fold>

    //<editor-fold>RandyPlayerListener实现
    @Override
    public void onPrepared() {
        if (mCurrentState != STATE_PREPARING) {
            return;
        }
        mHadPrepared = true;
        if (mVideoViewCallBack != null && isCurrentPlayerListener()) {
            KLog.d(TAG, "onPrepared");
            mVideoViewCallBack.onPrepared(mOriginUrl, mTitle, this);
        }
        if (!mStartAfterPrepared) {
            updateStateAndUi(STATE_PAUSE);
            onVideoPause();
            return;
        }
        startAfterPrepared();
    }

    public long getDuration() {
        long duration = 0;
        try {
            duration = getVideoViewMgrBridge().getDuration();
        } catch (Exception e) {
            KLog.e(TAG, e);
        }
        return duration;
    }

    public void release() {
        mSetupViewTimeMillis = 0;
        if (isCurrentPlayerListener() && System.currentTimeMillis() - mSetupViewTimeMillis > 0) {
            releaseAllVideos();
        }
    }

    public void startAfterPrepared() {
        if (!mHadPrepared) {
            prepareVideo();
        }

        try {
            if (getVideoViewMgrBridge() != null) {
                getVideoViewMgrBridge().start();
            }
            updateStateAndUi(STATE_PLAYING);
            if (getVideoViewMgrBridge() != null && mStartPlayPosition > 0) {
                getVideoViewMgrBridge().seekTo(mStartPlayPosition);
                mStartPlayPosition = 0;// 播放位置重置
            }
        } catch (Exception e) {
            KLog.e(TAG, e);
        }
        addRenderView();

        // 创建网络监听
        createNetworkListener();
        // 开启网络监听
        registerNetworkListener();
        mIsHadPlay = true;

        if (mRenderView != null) {
            // GLSurfaceView的特殊处理
            mRenderView.onResume();
        }
        if (mPauseBeforePrepared) {
            onVideoPause();
            mPauseBeforePrepared = false;
        }
    }

    /**
     * todo 创建网路监听
     */
    public void createNetworkListener() {

    }

    /**
     * todo 注册网络监听
     */
    public void registerNetworkListener() {

    }

    /**
     * todo 取消注册网路监听
     */
    public void unRegisterNetworkListener() {

    }

    /**
     * todo 释放网络监听
     */
    public void releaseNetworkListener() {

    }

    @Override
    public void onAutoCompletion() {
        updateStateAndUi(STATE_AUTO_COMPLETE);
        mSetupViewTimeMillis = 0;
        mCurrentPosition = 0;

        if (mRenderViewParent != null && mRenderViewParent.getChildCount() > 0) {
            mRenderViewParent.removeAllViews();
        }
        if (!mIsCurrentFullscreen) {
            getVideoViewMgrBridge().setLastListener(null);
        }
        // 释放音频焦点，让之前有获取音频焦点请求的获取焦点
        mAudioManager.abandonAudioFocus(mAudioFocusChangeListener);
        // 取消屏幕常亮
        AppUtils.cancelKeepScreenOn(mContext);
        // 取消注册网络监听
        unRegisterNetworkListener();
        // 释放网络监听
        releaseNetworkListener();
        // 发送通知
        if (mVideoViewCallBack != null && isCurrentPlayerListener()) {
            KLog.d(TAG, "onAutoCompletion");
            mVideoViewCallBack.onAutoComplete(mOriginUrl, mTitle, this);
        }
    }

    @Override
    public void onCompletion() {
        updateStateAndUi(STATE_NORMAL);
        mSetupViewTimeMillis = 0;
        mCurrentPosition = 0;

        if (mRenderViewParent != null && mRenderViewParent.getChildCount() > 0) {
            mRenderViewParent.removeAllViews();
        }
        if (!mIsCurrentFullscreen) {
            getVideoViewMgrBridge().setListener(null);
            getVideoViewMgrBridge().setLastListener(null);
        }
        getVideoViewMgrBridge().setCurrentVideoWidth(0);
        getVideoViewMgrBridge().setCurrentVideoHeight(0);

        // 释放音频焦点，让之前有获取音频焦点请求的获取焦点
        mAudioManager.abandonAudioFocus(mAudioFocusChangeListener);
        // 取消屏幕常亮
        AppUtils.cancelKeepScreenOn(mContext);
        // 取消注册网络监听
        unRegisterNetworkListener();
        // 释放网络监听
        releaseNetworkListener();
    }

//    @Override
//    public void onBufferingUpdate(int percentage) {
//
//    }

    @Override
    public void onSeekComplete() {
        // do nothing
        KLog.d(TAG, "onSeekComplete");
    }

    @Override
    public void onError(int what, int extra) {
        if (mIsNetChanged) {
            mIsNetChanged = false;
            netWorkErrorLogic();
            if (mVideoViewCallBack != null) {
                mVideoViewCallBack.onPlayError(mOriginUrl, mTitle, this);
            }
            return;
        }
        if (what != 38 && what != -38) {
            updateStateAndUi(STATE_ERROR);
            deleteCacheFileWhenError();
            if (mVideoViewCallBack != null) {
                mVideoViewCallBack.onPlayError(mOriginUrl, mTitle, this);
            }
        }
    }

    /**
     * 处理因切换网络而导致的问题
     */
    protected void netWorkErrorLogic() {
        final long currentPosition = getCurrentPositionWhenPlaying();
        KLog.e(TAG, "******* Net State Changed. renew player to connect *******" + currentPosition);
        getVideoViewMgrBridge().release();
        postDelayed(() -> {
            setStartPlayPosition(currentPosition);
            startPlayLogic();
        }, 500);
    }

    /**
     * 获取当前的播放进度
     *
     * @return 当前的播放进度
     */
    public long getCurrentPositionWhenPlaying() {
        long position = 0;
        if (mCurrentState == STATE_PLAYING || mCurrentState == STATE_PAUSE) {
            try {
                position = getVideoViewMgrBridge().getCurrentPosition();
            } catch (Exception e) {
                KLog.e(TAG, e);
                return position;
            }
        }
        // 如果获取到的position为0但记录的当前播放位置不为0，则去记录的当前位置
        if (position == 0 && mCurrentPosition > 0) {
            return mCurrentPosition;
        }
        return position;
    }

    /**
     * 播放错误的时候，删除缓存文件
     */
    protected void deleteCacheFileWhenError() {
        clearCurrentCache();
        KLog.e(TAG, "Link Or mCache Error, Please Try Again " + mOriginUrl);
        if (mIsCachePlay) {
            KLog.e(TAG, "mCache Link " + mUrl);
        }
        mUrl = mOriginUrl;
    }

    /**
     * 删除当前缓存
     */
    public void clearCurrentCache() {
        if (getVideoViewMgrBridge().isCurrentUrlCached() && mIsCachePlay) {
            mUrl = mOriginUrl;
            getVideoViewMgrBridge().clearCache(mContext, mCachePath, mOriginUrl);
        } else if (mUrl.contains("127.0.0.1")) {
            // todo 目的是什么
            getVideoViewMgrBridge().clearCache(getContext(), mCachePath, mOriginUrl);
        }
    }

    @Override
    public void onInfo(int what, int extra) {
        if (what == MediaPlayer.MEDIA_INFO_BUFFERING_START) {
            // 缓存前保存之前的状态
            mStateBeforeBuffering = mCurrentState;
            if (mIsHadPlay && mCurrentState != STATE_PREPARING && mCurrentState > 0) {
                updateStateAndUi(STATE_PLAYING_BUFFERING_START);
            }
        } else if (what == MediaPlayer.MEDIA_INFO_BUFFERING_END) {
            if (mStateBeforeBuffering != -1) {
                // 如果缓冲之前保存的状态就是缓冲，则在缓冲完成后更新为播放状态
                if (mStateBeforeBuffering == STATE_PLAYING_BUFFERING_START) {
                    mStateBeforeBuffering = STATE_PLAYING;
                }
                if (mIsHadPlay && mCurrentState != STATE_PREPARING && mCurrentState > 0) {
                    // 缓冲完毕后恢复到之前的状态
                    updateStateAndUi(mStateBeforeBuffering);
                }
            }
        } else if (what == getVideoViewMgrBridge().getRotateInfoFlag()) {
            mRotate = extra;
            KLog.d(TAG, "Video rotate info " + extra);
            if (mRenderView != null) {
                mRenderView.setRotation(mRotate);
            }
        }
    }

    @Override
    public void onVideoSizeChanged() {
        int mVideoWidth = getVideoViewMgrBridge().getCurrentVideoWidth();
        int mVideoHeight = getVideoViewMgrBridge().getCurrentVideoHeight();
        if (mRenderView != null && mVideoWidth != 0 && mVideoHeight != 0) {
            mRenderView.requestLayout();
        }
    }

//    @Override
//    public void onBackFullscreen() {
//
//    }

    public void onVideoReset() {
        updateStateAndUi(STATE_NORMAL);
    }

    @Override
    public void onVideoPause() {
        if (mCurrentState == STATE_PREPARING) {
            mPauseBeforePrepared = true;
        }
        try {
            if (getVideoViewMgrBridge() != null && getVideoViewMgrBridge().isPlaying()) {
                updateStateAndUi(STATE_PAUSE);
                mCurrentPosition = getVideoViewMgrBridge().getCurrentPosition();
                getVideoViewMgrBridge().pause();
            }
        } catch (Exception e) {
            KLog.e(TAG, e);
        }
    }

    @Override
    public void onVideoResume() {
        onVideoResume(true);
    }

    @Override
    public void onVideoResume(boolean seek) {
        mPauseBeforePrepared = false;
        try {
            if (mCurrentState == STATE_PAUSE) {
                if (mCurrentPosition >= 0 && getVideoViewMgrBridge() != null) {
                    if (seek) {
                        getVideoViewMgrBridge().seekTo(mCurrentPosition);
                    }
                    getVideoViewMgrBridge().start();
                    updateStateAndUi(STATE_PLAYING);
                    if (mAudioManager != null && !mReleaseWhenLossAudio) {
                        // 请求焦点
                        mAudioManager.requestAudioFocus(mAudioFocusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                    }
                    mCurrentPosition = 0;
                }
            }
        } catch (Exception e) {
            KLog.e(TAG, e);
        }
    }

    //</editor-fold>

    //<editor-fold>VideoDrawLayerContainer抽象方法实现
    @Override
    protected void showPauseCover() {
        if (mCurrentState == STATE_PAUSE
                && mShowPauseCover
                && mFullPauseBitmap != null && !mFullPauseBitmap.isRecycled()
                && mSurface != null && mSurface.isValid()) {
            if (getVideoViewMgrBridge().isSurfaceSupportLockCanvas()) {
                try {
                    RectF rectF = new RectF(0, 0, mRenderView.getWidth(), mRenderView.getHeight());
                    Canvas canvas = mSurface.lockCanvas(new Rect(0, 0, mRenderView.getWidth(), mRenderView.getHeight()));
                    if (canvas != null) {
                        canvas.drawBitmap(mFullPauseBitmap, null, rectF, null);
                        mSurface.unlockCanvasAndPost(canvas);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    protected void releasePauseCover() {
        if (mCurrentState != STATE_PAUSE
                && mShowPauseCover
                && mFullPauseBitmap != null && !mFullPauseBitmap.isRecycled()) {
            mFullPauseBitmap.recycle();
            mFullPauseBitmap = null;
        }
    }

    @Override
    protected void setDisplay(Surface surface) {
        getVideoViewMgrBridge().setDisplay(surface);
    }

    @Override
    protected void releaseSurface(Surface surface) {
        getVideoViewMgrBridge().releaseSurface(surface);
    }
    //</editor-fold>

    protected void updatePauseCover() {
        if ((mFullPauseBitmap == null || mFullPauseBitmap.isRecycled()) && mShowPauseCover) {
            try {
                initCover();
            } catch (Exception e) {
                e.printStackTrace();
                mFullPauseBitmap = null;
            }
        }
    }

    //<editor-fold> MeasureHelper.MeasureFormVideoParamsListener实现
    @Override
    public int getCurrentVideoWidth() {
        if (getVideoViewMgrBridge() != null) {
            return getVideoViewMgrBridge().getVideoWidth();
        }
        return 0;
    }

    @Override
    public int getCurrentVideoHeight() {
        if (getVideoViewMgrBridge() != null) {
            return getVideoViewMgrBridge().getVideoHeight();
        }
        return 0;
    }

    @Override
    public int getVideoSarNum() {
        if (getVideoViewMgrBridge() != null) {
            return getVideoViewMgrBridge().getVideoSarNum();
        }
        return 0;
    }

    @Override
    public int getVideoSarDen() {
        if (getVideoViewMgrBridge() != null) {
            return getVideoViewMgrBridge().getVideoSarDen();
        }
        return 0;
    }
    //</editor-fold>

    //<editor-fold>子类要实现的方法

    /**
     * 获取View和Mgr的桥接管理器
     *
     * @return 实现了IVideoViewMgrBridge的PlayerManager
     */
    protected abstract IVideoViewMgrBridge getVideoViewMgrBridge();

    /**
     * 获取布局id
     *
     * @return 布局文件id
     */
    protected abstract @LayoutRes
    int getLayoutId();

    /**
     * 开始播放的逻辑处理
     */
    protected abstract void startPlayLogic();

    /**
     * 从全屏推出
     *
     * @param context ctx
     */
    protected abstract boolean backFromFull(Context context);

    /**
     * 更新状态和UI
     *
     * @param state 当前状态
     */
    protected abstract void updateStateAndUi(int state);

    protected abstract void releaseAllVideos();
    //</editor-fold>

    //<editor-fold>对外暴露的getter和setter方法
    public int getCurrentState() {
        return mCurrentState;
    }

    public boolean isPlayingState() {
        return mCurrentState >= 0 && mCurrentState != STATE_NORMAL
                && mCurrentState != STATE_PAUSE
                && mCurrentState != STATE_AUTO_COMPLETE
                && mCurrentState != STATE_ERROR;
    }

    public String getPlayTag() {
        return mPlayTag;
    }

    public void setPlayTag(String playTag) {
        this.mPlayTag = playTag;
    }

    public int getPlayPosition() {
        return mPlayPosition;
    }

    public void setPlayPosition(int playPosition) {
        this.mPlayPosition = playPosition;
    }

    public long getNetSpeed() {
        return getVideoViewMgrBridge().getNetSpeed();
    }

    public String getNetSpeedText() {
        long speed = getNetSpeed();
        return NetworkUtils.getNetSpeedText(speed);
    }

    public long getStartPlayPosition() {
        return mStartPlayPosition;
    }

    public void setStartPlayPosition(long startPlayPosition) {
        this.mStartPlayPosition = startPlayPosition;
    }

    public int getBufferPoint() {
        return mBufferPoint;
    }

    public boolean isCurrentFullscreen() {
        return mIsCurrentFullscreen;
    }

    public void setIsCurrentFullscreen(boolean isCurrentFullscreen) {
        this.mIsCurrentFullscreen = isCurrentFullscreen;
    }

    public boolean isLoop() {
        return mIsLoop;
    }

    public void setIsLoop(boolean isLoop) {
        this.mIsLoop = isLoop;
    }

    public void setVideoViewCallback(VideoViewCallback callback) {
        this.mVideoViewCallBack = callback;
    }

    public float getSpeed() {
        return mSpeed;
    }

    public void setSpeed(float speed) {
        setSpeed(speed, false);
    }

    /**
     * 设置播放速度
     *
     * @param speed        播放速度
     * @param isSoundTouch 是否对6.0下开启变速不变调
     */
    public void setSpeed(float speed, boolean isSoundTouch) {
        this.mSpeed = speed;
        this.mSoundTouch = isSoundTouch;
        if (getVideoViewMgrBridge() != null) {
            getVideoViewMgrBridge().setSpeed(speed, isSoundTouch);
        }
    }

    public boolean isShowPauseCover() {
        return mShowPauseCover;
    }

    public void setShowPauseCover(boolean mShowPauseCover) {
        this.mShowPauseCover = mShowPauseCover;
    }

    public boolean isStartAfterPrepared() {
        return mStartAfterPrepared;
    }

    public void setStartAfterPrepared(boolean startAfterPrepared) {
        this.mStartAfterPrepared = startAfterPrepared;
    }

    public boolean isReleaseWhenLossAudio() {
        return mReleaseWhenLossAudio;
    }

    public void setReleaseWhenLossAudio(boolean releaseWhenLossAudio) {
        this.mReleaseWhenLossAudio = releaseWhenLossAudio;
    }

    public String getOverrideExtension() {
        return mOverrideExtension;
    }

    public void setOverrideExtension(String overrideExtension) {
        this.mOverrideExtension = overrideExtension;
    }

    public Map<String, String> getmMapHeadData() {
        return mMapHeadData;
    }

    public void setmMapHeadData(Map<String, String> mMapHeadData) {
        this.mMapHeadData = mMapHeadData;
    }

    //</editor-fold>
}
