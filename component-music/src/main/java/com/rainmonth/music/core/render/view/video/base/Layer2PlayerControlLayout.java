package com.rainmonth.music.core.render.view.video.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.rainmonth.common.utils.DateUtils;
import com.rainmonth.common.utils.DensityUtils;
import com.rainmonth.common.utils.SmartBarUtils;
import com.rainmonth.music.R;
import com.rainmonth.music.core.render.view.listener.LockClickListener;
import com.rainmonth.music.core.render.view.listener.VideoProgressListener;
import com.socks.library.KLog;

/**
 * 播放UI的显示、控制及手势处理
 *
 * @author 张豪成
 * @date 2019-12-17 19:56
 */
public abstract class Layer2PlayerControlLayout extends Layer1PlayerCallbackStateLayout implements
        View.OnClickListener, View.OnTouchListener, SeekBar.OnSeekBarChangeListener {
    public static final String TAG = Layer2PlayerControlLayout.class.getSimpleName();
    //<editor-fold>成员变量
    //手指放下的位置
    protected int mDownPosition;
    //手势调节音量的大小
    protected int mGestureDownVolume;
    //手势偏差值
    protected int mThreshold = 80;
    //手动改变滑动的位置
    protected int mSeekTimePosition;
    //手动滑动的起始偏移位置
    protected int mSeekEndOffset;
    //退出全屏显示的案件图片
    protected int mShrinkImageRes = -1;
    //全屏显示的案件图片
    protected int mEnlargeImageRes = -1;
    //触摸的X
    protected float mDownX;
    //触摸的Y
    protected float mDownY;
    //移动的Y
    protected float mMoveY;
    //亮度
    protected float mBrightnessData = -1;
    //触摸滑动进度的比例系数
    protected float mSeekRatio = 1;
    //触摸的是否进度条
    protected boolean mTouchingProgressBar = false;
    //是否改变音量
    protected boolean mChangeVolume = false;
    //是否改变播放进度
    protected boolean mChangePosition = false;
    //触摸显示虚拟按键
    protected boolean mShowVKey = false;
    //是否改变亮度
    protected boolean mBrightness = false;
    //是否首次触摸
    protected boolean mFirstTouch = false;
    //是否隐藏虚拟按键
    protected boolean mHideKey = true;
    //是否需要显示流量提示
    protected boolean mNeedShowWifiTip = true;
    //是否支持非全屏滑动触摸有效
    protected boolean mIsTouchWiget = true;
    //是否支持全屏滑动触摸有效
    protected boolean mIsTouchWigetFull = true;
    //是否点击封面播放
    protected boolean mThumbPlay;
    //锁定屏幕点击
    protected boolean mIsCurrentScreenLock;
    //是否需要锁定屏幕
    protected boolean mNeedLockFull;
    //lazy的setup
    protected boolean mSetUpLazy = false;
    //seek touch
    protected boolean mHadSeekTouch = false;

    //</editor-fold>

    //<editor-fold>View成员
    protected View mStartBtn;
    protected View mLoadingProgressView;
    protected SeekBar mProgressBar;
    protected ImageView mFullscreenBtn;
    protected ImageView mBackBtn;
    protected ImageView mLockScreenBtn;
    protected TextView mCurrentTimeView, mTotalTimeView;
    protected TextView mTitleView;
    protected ViewGroup mTopContainer, mBottomContainer;
    protected View mThumbImageView;
    protected RelativeLayout mThumbImageContainer;
    protected ProgressBar mBottomProgressBar;

    protected LockClickListener mLockClickListener;
    protected VideoProgressListener mVideoProgressListener;

    //</editor-fold>
    public Layer2PlayerControlLayout(Context context) {
        super(context);
    }

    public Layer2PlayerControlLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Layer2PlayerControlLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public Layer2PlayerControlLayout(@NonNull Context context, boolean isFullscreen) {
        super(context, isFullscreen);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void init(Context context) {
        super.init(context);

        mStartBtn = findViewById(R.id.player_start);
        mLoadingProgressView = findViewById(R.id.player_loading_view);
        mFullscreenBtn = findViewById(R.id.player_fullscreen);
        mBackBtn = findViewById(R.id.player_back);
        mLockScreenBtn = findViewById(R.id.player_lock_screen);
        mCurrentTimeView = findViewById(R.id.player_current_time);
        mTotalTimeView = findViewById(R.id.player_total_time);
        mTitleView = findViewById(R.id.player_title);
        mTopContainer = findViewById(R.id.player_top_container);
        mBottomContainer = findViewById(R.id.player_bottom_container);

        mThumbImageContainer = findViewById(R.id.player_thumb_container);
        mBottomProgressBar = findViewById(R.id.player_bottom_progress);

        if (isInEditMode()) {
            return;
        }

        if (mStartBtn != null) {
            mStartBtn.setOnClickListener(this);
        }
        if (mFullscreenBtn != null) {
            mFullscreenBtn.setOnClickListener(this);
            mFullscreenBtn.setOnTouchListener(this);
        }
        if (mProgressBar != null) {
            mProgressBar.setOnSeekBarChangeListener(this);
            mProgressBar.setOnTouchListener(this);
        }
        if (mBottomContainer != null) {
            mBottomContainer.setOnClickListener(this);
        }
        if (mRenderViewParent != null) {
            mRenderViewParent.setOnClickListener(this);
            mRenderViewParent.setOnTouchListener(this);
        }
        if (mThumbImageContainer != null) {
            mThumbImageContainer.setVisibility(GONE);
            mThumbImageContainer.setOnClickListener(this);
        }
        if (mThumbImageView != null && !mIsCurrentFullscreen && mThumbImageContainer != null) {
            mThumbImageContainer.removeAllViews();
            resolveThumbImage(mThumbImageView);
        }
        if (mBackBtn != null) {
            mBackBtn.setOnClickListener(this);
        }
        if (mLockScreenBtn != null) {
            mLockScreenBtn.setVisibility(GONE);
            mLockScreenBtn.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mCurrentState == STATE_AUTO_COMPLETE || mCurrentState == STATE_ERROR) {
                        // 自然而然播放结束的或者播放错误的状态下，不处理锁屏逻辑
                        return;
                    }
                    lockTouchLogic();
                    if (mLockClickListener != null) {
                        mLockClickListener.onClick(v, mIsCurrentScreenLock);
                    }
                }
            });
        }
        if (getActivityCtx(context) != null) {
            mSeekEndOffset = DensityUtils.dip2px(getActivityCtx(context), 50);
        }

    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        KLog.d(TAG, "onDetachedFromWindow");
        // 资源回收，主要是定时资源的回收
        // 回收更新进度条的time
        cancelProgressTimer();
        // 播放设置相关控件隐藏逻辑实现
        cancelWidgetDismissTimer();
    }

    @Override
    public void onAutoCompletion() {
        super.onAutoCompletion();
        if (mIsCurrentScreenLock) {
            lockTouchLogic();
            mLockScreenBtn.setVisibility(GONE);
        }
    }

    @Override
    public void onError(int what, int extra) {
        super.onError(what, extra);
        if (mIsCurrentScreenLock) {
            lockTouchLogic();
            mLockScreenBtn.setVisibility(GONE);
        }
    }

    @Override
    public void onBufferingUpdate(int percentage) {
        post(() -> {
            if (mCurrentState != STATE_NORMAL && mCurrentState != STATE_PREPARING) {
                if (percentage != 0) {
                    setProgressAndTime(percentage);
                    mBufferPoint = percentage;
                    KLog.d(TAG, "Net speed: " + getNetSpeedText() + " percent " + percentage);
                }
                if (mProgressBar == null) {
                    return;
                }
                //循环清除进度
                if (mIsLoop && mIsHadPlay && percentage == 0 &&
                        mProgressBar.getProgress() >= (mProgressBar.getMax() - 1)) {
                    loopSetProgressAndTime();
                }
            }
        });
    }

    @Override
    protected void updateStateAndUi(int state) {
        mCurrentState = state;
        if ((state == STATE_NORMAL && isCurrentPlayerListener())
                || state == STATE_ERROR
                || state == STATE_AUTO_COMPLETE) {
            mHadPrepared = false;
        }
        switch (mCurrentState) {
            case STATE_NORMAL:
                if (isCurrentPlayerListener()) {
                    KLog.d(TAG, Layer2PlayerControlLayout.this.hashCode() + "---------dismiss STATE_NORMAL");
                    cancelProgressTimer();
                    getVideoViewMgrBridge().release();
                    releasePauseCover();
                    mBufferPoint = 0;
                    mSetupViewTimeMillis = 0;
                    if (mAudioManager != null) {
                        mAudioManager.abandonAudioFocus(mAudioFocusChangeListener);
                    }
                }
                releaseNetworkListener();
                break;
            case STATE_PREPARING:
                resetProgressAndTime();
                break;
            case STATE_PLAYING:
                if (isCurrentPlayerListener()) {
                    KLog.d(TAG, Layer2PlayerControlLayout.this.hashCode() + "--------- STATE_PLAYING");
                    startProgressTimer();
                }
                break;
            case STATE_PLAYING_BUFFERING_START:

                break;
            case STATE_PAUSE:
                if (isCurrentPlayerListener()) {
                    KLog.d(TAG, Layer2PlayerControlLayout.this.hashCode() + "--------- STATE_PAUSE");
                }
                break;
            case STATE_AUTO_COMPLETE:
                KLog.d(TAG, Layer2PlayerControlLayout.this.hashCode() + "---------dismiss STATE_NORMAL");
                cancelProgressTimer();
                if (mProgressBar != null) {
                    mProgressBar.setProgress(100);
                }
                if (mCurrentTimeView != null && mTotalTimeView != null) {
                    mCurrentTimeView.setText(mTotalTimeView.getText());
                }
                if (mBottomProgressBar != null) {
                    mBottomProgressBar.setProgress(100);
                }
                break;
            case STATE_ERROR:
                if (isCurrentPlayerListener()) {
                    getVideoViewMgrBridge().release();
                }
                break;
        }
        resolveUiState(mCurrentState);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void setSmallVideoTextureView(OnTouchListener listener) {
        super.setSmallVideoTextureView(listener);
        if (mThumbImageContainer != null) {
            mThumbImageContainer.setOnTouchListener(listener);
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (mHideKey && mIsCurrentFullscreen) {
            SmartBarUtils.hideNavKey(mContext);
        }
        if (id == R.id.player_start) {
            clickStartIcon();
        } else if (id == R.id.player_render_view_container && mCurrentState == STATE_ERROR) {
            if (mVideoViewCallBack != null) {
                KLog.d(TAG, "onClickStartError()");
                mVideoViewCallBack.onClickStartError(mOriginUrl, mTitle, this);
            }
            prepareVideo();
        } else if (id == R.id.player_render_view_container) {
            if (mVideoViewCallBack != null && isCurrentPlayerListener()) {
                if (mIsCurrentFullscreen) {
                    KLog.d(TAG, "onClickBlankFullscreen()");
                    mVideoViewCallBack.onClickBlankFullscreen(mOriginUrl, mTitle, Layer2PlayerControlLayout.this);
                } else {
                    KLog.d(TAG, "onClickStartError()");
                    mVideoViewCallBack.onClickStartError(mOriginUrl, mTitle, Layer2PlayerControlLayout.this);
                }
            }
            startWidgetDismissTimer();
        } else if (id == R.id.player_thumb_container) {
            if (!mThumbPlay) {
                return;
            }
            if (TextUtils.isEmpty(mUrl)) {
                KLog.d(TAG, "播放地址无效");
                return;
            }
            if (mCurrentState == STATE_NORMAL) {
                if (isShowNetConfirm()) {
                    showWifiDialog();
                    return;
                }
                startPlayLogic();
            } else if (mCurrentState == STATE_AUTO_COMPLETE) {
                onClickUiToggle();
            }
        }
    }

    protected GestureDetector gestureDetector = new GestureDetector(mContext.getApplicationContext(), new GestureDetector.SimpleOnGestureListener() {
        @Override
        public boolean onDoubleTap(MotionEvent e) {
            touchDoubleUp();
            return super.onDoubleTap(e);
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            if (!mChangePosition && !mChangeVolume && !mBrightness) {
                onClickUiToggle();
            }
            return super.onSingleTapConfirmed(e);
        }
    });

    /**
     * 双击暂停/播放实现
     */
    protected void touchDoubleUp() {
        if (!mIsHadPlay) {
            return;
        }
        clickStartIcon();
    }

    /**
     * 是否需要弹出非WiFi环境播放确认框
     *
     * @return true 当前处于非网络环境且播放的是没有缓存的网络视频
     */
    protected boolean isShowNetConfirm() {
        boolean isLocalResource = mOriginUrl.startsWith("file") || mOriginUrl.startsWith("android.resource");
        boolean isCacheFile = getVideoViewMgrBridge().isTotalCached(mContext.getApplicationContext(), mCachePath, mOriginUrl);
        return mNeedShowWifiTip && !isCacheFile && !isLocalResource;
    }

    /**
     * 播放按钮点击处理
     */
    protected void clickStartIcon() {
        if (TextUtils.isEmpty(mUrl)) {
            KLog.e(TAG, "播放地址无效");
            return;
        }
        if (mCurrentState == STATE_NORMAL || mCurrentState == STATE_ERROR) {
            if (isShowNetConfirm()) {
                showWifiDialog();
                return;
            }
            startButtonLogic();
        } else if (mCurrentState == STATE_PLAYING) {
            try {
                onVideoPause();
            } catch (Exception e) {
                KLog.e(TAG, e);
            }
            updateStateAndUi(STATE_PAUSE);
            if (mVideoViewCallBack != null && isCurrentPlayerListener()) {
                if (mIsCurrentFullscreen) {
                    KLog.d(TAG, "onClickStopFullscreen");
                    mVideoViewCallBack.onClickStopFullscreen(mOriginUrl, mTitle, this);
                } else {
                    KLog.d(TAG, "onClickStop");
                    mVideoViewCallBack.onClickStop(mOriginUrl, mTitle, this);
                }
            }
        } else if (mCurrentState == STATE_PAUSE) {
            if (mVideoViewCallBack != null && isCurrentPlayerListener()) {
                if (mIsCurrentFullscreen) {
                    KLog.d(TAG, "onClickStopFullscreen");
                    mVideoViewCallBack.onClickResumeFullscreen(mOriginUrl, mTitle, this);
                } else {
                    KLog.d(TAG, "onClickResume");
                    mVideoViewCallBack.onClickResume(mOriginUrl, mTitle, this);
                }
            }
            if (!mIsHadPlay && !mStartAfterPrepared) {
                startAfterPrepared();
            }
            try {
                getVideoViewMgrBridge().start();
            } catch (Exception e) {
                KLog.e(TAG, e);
            }
            updateStateAndUi(STATE_PLAYING);
        } else if (mCurrentState == STATE_AUTO_COMPLETE) {
            startButtonLogic();
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int id = v.getId();
        float x = event.getX();
        float y = event.getY();
        if (mIsCurrentFullscreen && mIsCurrentScreenLock && mNeedLockFull) {
            onClickUiToggle();
            startWidgetDismissTimer();
            return true;// 消费事件
        }
        if (id == R.id.player_fullscreen) { // 触摸的是全屏按钮，交由子View处理）
            return false;
        }
        if (id == R.id.player_render_view_container) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    // todo
                    break;
                case MotionEvent.ACTION_MOVE:
                    // todo
                    break;
                case MotionEvent.ACTION_UP:
                    // todo
                    break;
            }
            gestureDetector.onTouchEvent(event);
        } else if (id == R.id.player_progress) {
            // todo
        }
        return false;
    }

    /**
     * UI处理
     *
     * @param state 当前状态
     */
    protected void resolveUiState(int state) {
        switch (state) {
            case STATE_NORMAL:
                changeUiToNormal();
                cancelWidgetDismissTimer();
                break;
            case STATE_PREPARING:
                changeUiToPreparingShow();
                startWidgetDismissTimer();
                break;
            case STATE_PLAYING:
                changeUiToPlayingShow();
                startWidgetDismissTimer();
                break;
            case STATE_PAUSE:
                changeUiToPauseShow();
                cancelWidgetDismissTimer();
                break;
            case STATE_ERROR:
                changeUiToError();
                break;
            case STATE_AUTO_COMPLETE:
                changeUiToCompleteShow();
                cancelWidgetDismissTimer();
                break;
            case STATE_PLAYING_BUFFERING_START:
                changeUiToPlayingBufferingShow();
                break;
        }
    }

    protected void setProgressAndTime(int secondProgress) {
        setProgressAndTime(secondProgress, false);
    }

    protected void setProgressAndTime(int secondProgress, boolean forceChange) {
        long position = getCurrentPositionWhenPlaying();
        long duration = getDuration();
        int progress = (int) (position * 100 / (duration == 0 ? 1 : duration));
        setProgressAndTime(progress, secondProgress, position, duration, forceChange);
    }

    /**
     * 设置进度和时间的显示
     *
     * @param progress       播放进度
     * @param secondProgress 缓存进度
     * @param currentTime    当前播放时间（单位毫秒）
     * @param totalTime      总的时间（单位毫秒）
     * @param forceChange    是否主动改变
     */
    protected void setProgressAndTime(int progress, int secondProgress,
                                      long currentTime, long totalTime, boolean forceChange) {
        if (mVideoProgressListener != null && mCurrentState == STATE_PLAYING) {
            mVideoProgressListener.onProgress(progress, secondProgress, currentTime, totalTime);
        }

        if (mProgressBar == null || mTotalTimeView == null || mCurrentTimeView == null) {
            return;
        }
        if (mHadSeekTouch) {
            return;
        }
        if (!mTouchingProgressBar) {
            if (progress != 0 || forceChange) mProgressBar.setProgress(progress);
        }
        if (getVideoViewMgrBridge().getBufferedPercentage() > 0) {
            secondProgress = getVideoViewMgrBridge().getBufferedPercentage();
        }
        if (secondProgress > 94) {
            secondProgress = 100;
        }
        setSecondaryProgress(secondProgress);
        mTotalTimeView.setText(DateUtils.stringForTime(totalTime));
        if (currentTime > 0) {
            mCurrentTimeView.setText(DateUtils.stringForTime(currentTime));
        }
        if (mBottomProgressBar != null) {
            if (progress != 0 || forceChange) mBottomProgressBar.setProgress(progress);
            setSecondaryProgress(secondProgress);
        }

    }

    /**
     * 设置缓冲显示
     *
     * @param secondProgress 缓冲进度
     */
    protected void setSecondaryProgress(int secondProgress) {
        // UI操作
        if (mProgressBar != null) {
            if (secondProgress != 0 && !getVideoViewMgrBridge().isCurrentUrlCached()) {
                mProgressBar.setSecondaryProgress(secondProgress);
            }
        }
        if (mBottomProgressBar != null) {
            if (secondProgress != 0 && !getVideoViewMgrBridge().isCurrentUrlCached()) {
                mBottomProgressBar.setSecondaryProgress(secondProgress);
            }
        }
    }

    /**
     * 重置进度和时间的显示
     */
    protected void resetProgressAndTime() {
        if (mProgressBar == null || mTotalTimeView == null || mCurrentTimeView == null) {
            return;
        }
        mProgressBar.setProgress(0);
        mProgressBar.setSecondaryProgress(0);
        mCurrentTimeView.setText(DateUtils.stringForTime(0));
        mTotalTimeView.setText(DateUtils.stringForTime(0));

        if (mBottomProgressBar != null) {
            mBottomProgressBar.setProgress(0);
            mBottomProgressBar.setSecondaryProgress(0);
        }
    }

    /**
     * 循环播放的时候进度和时间的显示
     */
    protected void loopSetProgressAndTime() {

    }

    //<editor-fold>定时任务相关

    protected boolean mPostProgress = false;
    Runnable progressTask = new Runnable() {
        @Override
        public void run() {
            if (mCurrentState == STATE_PLAYING || mCurrentState == STATE_PAUSE) {
                // 处理进度和时间的显示
                setProgressAndTime(0);
            }
            if (mPostProgress) {
                postDelayed(this, 1000);
            }
        }
    };

    protected void startProgressTimer() {
        cancelProgressTimer();
        mPostProgress = true;
        postDelayed(progressTask, 300);
    }

    protected void cancelProgressTimer() {
        mPostProgress = false;
        removeCallbacks(progressTask);
    }


    protected boolean mPostDismiss = false;
    //触摸显示后隐藏的时间
    protected int mDismissControlTime = 2500;
    Runnable widgetDismissTask = new Runnable() {
        @Override
        public void run() {

        }
    };

    /**
     * 隐藏控件
     */
    protected void startWidgetDismissTimer() {
        cancelWidgetDismissTimer();
        mPostDismiss = true;
        postDelayed(widgetDismissTask, mDismissControlTime);
    }

    protected void cancelWidgetDismissTimer() {
        mPostDismiss = false;
        removeCallbacks(widgetDismissTask);
    }

    //</editor-fold>

    /**
     * 将非全屏状态下的封面内容加入到封面容器中
     *
     * @param thumbView 待添加的封面
     */
    protected void resolveThumbImage(View thumbView) {
        if (mThumbImageContainer != null) {
            mThumbImageContainer.removeAllViews();
            ViewGroup.LayoutParams params = thumbView.getLayoutParams();
            params.width = ViewGroup.LayoutParams.MATCH_PARENT;
            params.height = ViewGroup.LayoutParams.MATCH_PARENT;
            mThumbImageContainer.addView(thumbView, params);
        }
    }

    /**
     * 锁屏逻辑处理
     */
    protected void lockTouchLogic() {
        if (mLockScreenBtn == null) {
            return;
        }
        if (mIsCurrentScreenLock) {
            mLockScreenBtn.setImageResource(R.drawable.unlock);
            mIsCurrentScreenLock = false;
        } else {
            mLockScreenBtn.setImageResource(R.drawable.lock);
            mIsCurrentScreenLock = true;
            hideAllWidgets();
        }
    }


    //<editor-fold>子类要实现的方法

    /**
     * 展示非WiFi环境播放弹出
     */
    protected abstract void showWifiDialog();

    /**
     * 点击时播放界面相关控件的显示与隐藏切换
     */
    protected abstract void onClickUiToggle();

    /**
     * 隐藏所有小控件，只保留播放视图
     */
    protected abstract void hideAllWidgets();

    protected abstract void changeUiToNormal();

    protected abstract void changeUiToPreparingShow();

    protected abstract void changeUiToPlayingShow();

    protected abstract void changeUiToPauseShow();

    protected abstract void changeUiToError();

    protected abstract void changeUiToCompleteShow();

    protected abstract void changeUiToPlayingBufferingShow();

    //</editor-fold>

}
