package com.rainmonth.music.core.render.view.video.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.WindowManager;
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
import com.rainmonth.common.utils.log.LogUtils;
import com.rainmonth.music.R;
import com.rainmonth.music.core.render.view.listener.LockClickListener;
import com.rainmonth.music.core.render.view.listener.VideoProgressListener;

import java.io.File;
import java.util.Map;

/**
 * 播放UI的显示、控制及手势处理
 *
 * @author 张豪成
 * @date 2019-12-17 19:56
 */
public abstract class Layer2PlayerControlLayout extends Layer1PlayerCallbackStateLayout implements
        View.OnClickListener, View.OnTouchListener, SeekBar.OnSeekBarChangeListener {
    public static final String  TAG                  = Layer2PlayerControlLayout.class.getSimpleName();
    //<editor-fold>成员变量
    //手指放下的位置
    protected           long    mDownPosition;
    //手势调节音量的大小
    protected           int     mGestureDownVolume;
    //手势偏差值
    protected           int     mThreshold           = 80;
    //手动改变滑动的位置
    protected           int     mSeekTimePosition;
    //手动滑动的起始偏移位置
    protected           int     mSeekEndOffset;
    //退出全屏显示的案件图片
    protected           int     mShrinkImageRes      = -1;
    //全屏显示的案件图片
    protected           int     mEnlargeImageRes     = -1;
    //触摸的X
    protected           float   mDownX;
    //触摸的Y
    protected           float   mDownY;
    //移动的Y
    protected           float   mMoveY;
    //亮度
    protected           float   mBrightnessData      = -1;
    //触摸滑动进度的比例系数
    protected           float   mSeekRatio           = 1;
    //触摸的是否进度条
    protected           boolean mTouchingProgressBar = false;
    //是否改变音量
    protected           boolean mChangeVolume        = false;
    //是否改变播放进度
    protected           boolean mChangePosition      = false;
    //触摸显示虚拟按键
    protected           boolean mShowVKey            = false;
    //是否改变亮度
    protected           boolean mBrightness          = false;
    //是否首次触摸
    protected           boolean mFirstTouch          = false;
    //是否隐藏虚拟按键
    protected           boolean mHideKey             = true;
    //是否需要显示流量提示
    protected           boolean mNeedShowWifiTip     = true;
    //是否支持非全屏滑动触摸有效
    protected           boolean mIsTouchWidget       = true;
    //是否支持全屏滑动触摸有效
    protected           boolean mIsTouchWidgetFull   = true;
    //是否点击封面播放
    protected           boolean mThumbPlay;
    //锁定屏幕点击
    protected           boolean mIsCurrentScreenLock;
    //是否需要锁定屏幕
    protected           boolean mNeedLockFull;
    //lazy的setup
    protected           boolean mSetUpLazy           = false;
    //seek touch
    protected           boolean mHadSeekTouch        = false;

    //</editor-fold>

    //<editor-fold>View成员
    protected View      mStartBtn;
    protected View      mLoadingProgressView;
    protected SeekBar   mProgressBar;
    protected ImageView mFullscreenBtn;
    protected ImageView mBackBtn;
    protected ImageView mLockScreenBtn;
    protected TextView  mCurrentTimeView, mTotalTimeView;
    protected TextView  mTitleView;
    protected ViewGroup mTopContainer, mBottomContainer;
    protected View           mThumbImageView;
    protected RelativeLayout mThumbImageContainer;
    protected ProgressBar    mBottomProgressBar;

    protected LockClickListener     mLockClickListener;
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
        LogUtils.d(TAG, "onDetachedFromWindow");
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
                    LogUtils.d(TAG, "Net speed: " + getNetSpeedText() + " percent " + percentage);
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
                    LogUtils.d(TAG, Layer2PlayerControlLayout.this.hashCode() + "---------dismiss STATE_NORMAL");
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
                    LogUtils.d(TAG, Layer2PlayerControlLayout.this.hashCode() + "--------- STATE_PLAYING");
                    startProgressTimer();
                }
                break;
            case STATE_PLAYING_BUFFERING_START:

                break;
            case STATE_PAUSE:
                if (isCurrentPlayerListener()) {
                    LogUtils.d(TAG, Layer2PlayerControlLayout.this.hashCode() + "--------- STATE_PAUSE");
                }
                break;
            case STATE_AUTO_COMPLETE:
                LogUtils.d(TAG, Layer2PlayerControlLayout.this.hashCode() + "---------dismiss STATE_NORMAL");
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
                LogUtils.d(TAG, "onClickStartError()");
                mVideoViewCallBack.onClickStartError(mOriginUrl, mTitle, this);
            }
            prepareVideo();
        } else if (id == R.id.player_render_view_container) {
            if (mVideoViewCallBack != null && isCurrentPlayerListener()) {
                if (mIsCurrentFullscreen) {
                    LogUtils.d(TAG, "onClickBlankFullscreen()");
                    mVideoViewCallBack.onClickBlankFullscreen(mOriginUrl, mTitle, Layer2PlayerControlLayout.this);
                } else {
                    LogUtils.d(TAG, "onClickStartError()");
                    mVideoViewCallBack.onClickStartError(mOriginUrl, mTitle, Layer2PlayerControlLayout.this);
                }
            }
            startWidgetDismissTimer();
        } else if (id == R.id.player_thumb_container) {
            if (!mThumbPlay) {
                return;
            }
            if (TextUtils.isEmpty(mUrl)) {
                LogUtils.d(TAG, "播放地址无效");
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
            LogUtils.e(TAG, "播放地址无效");
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
                LogUtils.e(TAG, e);
            }
            updateStateAndUi(STATE_PAUSE);
            if (mVideoViewCallBack != null && isCurrentPlayerListener()) {
                if (mIsCurrentFullscreen) {
                    LogUtils.d(TAG, "onClickStopFullscreen");
                    mVideoViewCallBack.onClickStopFullscreen(mOriginUrl, mTitle, this);
                } else {
                    LogUtils.d(TAG, "onClickStop");
                    mVideoViewCallBack.onClickStop(mOriginUrl, mTitle, this);
                }
            }
        } else if (mCurrentState == STATE_PAUSE) {
            if (mVideoViewCallBack != null && isCurrentPlayerListener()) {
                if (mIsCurrentFullscreen) {
                    LogUtils.d(TAG, "onClickStopFullscreen");
                    mVideoViewCallBack.onClickResumeFullscreen(mOriginUrl, mTitle, this);
                } else {
                    LogUtils.d(TAG, "onClickResume");
                    mVideoViewCallBack.onClickResume(mOriginUrl, mTitle, this);
                }
            }
            if (!mIsHadPlay && !mStartAfterPrepared) {
                startAfterPrepared();
            }
            try {
                getVideoViewMgrBridge().start();
            } catch (Exception e) {
                LogUtils.e(TAG, e);
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
                    LogUtils.d(TAG, "View with hashCode " + this.hashCode() + " renderView action down");
                    touchSurfaceDown(x, y);
                    break;
                case MotionEvent.ACTION_MOVE:
                    float deltaX = x - mDownX;
                    float deltaY = y - mDownY;
                    float absDeltaX = Math.abs(deltaX);
                    float absDeltaY = Math.abs(deltaY);

                    if ((mIsCurrentFullscreen && mIsTouchWidgetFull)
                            || (mIsTouchWidget && !mIsCurrentFullscreen)) {
                        if (!mChangePosition && !mChangeVolume && !mBrightness) {
                            touchSurfaceMoveFullLogic(absDeltaX, absDeltaY);
                        }
                    }
                    touchSurfaceMove(deltaX, deltaY, y);
                    break;
                case MotionEvent.ACTION_UP:
                    LogUtils.d(TAG, "View with hashCode " + this.hashCode() + " renderView action up");
                    startWidgetDismissTimer();
                    touchSurfaceUp();

                    startProgressTimer();
                    //不要和隐藏虚拟按键后，滑出虚拟按键冲突
                    if (mHideKey && mShowVKey) {
                        return true;
                    }
                    break;
            }
            gestureDetector.onTouchEvent(event);
        } else if (id == R.id.player_progress) {
            // 移动进度条处理
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    LogUtils.d(TAG, "View with hashCode " + this.hashCode() + " progress action down");
                    cancelWidgetDismissTimer();
                    break;
                case MotionEvent.ACTION_MOVE:
                    cancelProgressTimer();
                    // 此时需要防止父容器拦截时间
                    ViewParent vpMove = getParent();
                    while (vpMove != null) {
                        vpMove.requestDisallowInterceptTouchEvent(true);
                        vpMove = vpMove.getParent();
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    LogUtils.d(TAG, "View with hashCode " + this.hashCode() + " progress action up");
                    startWidgetDismissTimer();
                    startProgressTimer();
                    // 此时需要防止父容器拦截时间
                    ViewParent vpUp = getParent();
                    while (vpUp != null) {
                        vpUp.requestDisallowInterceptTouchEvent(false);
                        vpUp = vpUp.getParent();
                    }
                    mBrightnessData = -1f;
                    break;
            }
        }
        return false;
    }

    protected void touchSurfaceDown(float x, float y) {
        mTouchingProgressBar = true;
        mDownX = x;
        mDownY = y;
        mMoveY = 0;
        mChangeVolume = false;
        mChangePosition = false;
        mShowVKey = false;
        mBrightness = false;
        mFirstTouch = true;
    }

    protected void touchSurfaceMoveFullLogic(float absDeltaX, float absDeltaY) {
        int curWidth = 0;
        if (getActivityCtx(getContext()) != null) {
            curWidth = DensityUtils.isCurrentScreenLandscape((Activity) getActivityCtx(getContext())) ? mScreenHeight : mScreenWidth;
        }
        if (absDeltaX > mThreshold || absDeltaY > mThreshold) {
            cancelProgressTimer();
            if (absDeltaX >= mThreshold) {
                //防止全屏虚拟按键
                int screenWidth = DensityUtils.getScreenWidth(getContext());
                if (Math.abs(screenWidth - mDownX) > mSeekEndOffset) {
                    mChangePosition = true;
                    mDownPosition = getCurrentPositionWhenPlaying();
                } else {
                    mShowVKey = true;
                }
            } else {
                int screenHeight = DensityUtils.getScreenHeight(getContext());
                boolean noEnd = Math.abs(screenHeight - mDownY) > mSeekEndOffset;
                if (mFirstTouch) {
                    mBrightness = (mDownX < curWidth * 0.5f) && noEnd;
                    mFirstTouch = false;
                }
                if (!mBrightness) {
                    mChangeVolume = noEnd;
                    mGestureDownVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
                }
                mShowVKey = !noEnd;
            }
        }
    }

    protected void touchSurfaceMove(float deltaX, float deltaY, float y) {
        int curWidth = 0;
        int curHeight = 0;
        if (getActivityCtx(getContext()) != null) {
            curWidth = DensityUtils.isCurrentScreenLandscape((Activity) getActivityCtx(getContext())) ? mScreenHeight : mScreenWidth;
            curHeight = DensityUtils.isCurrentScreenLandscape((Activity) getActivityCtx(getContext())) ? mScreenWidth : mScreenHeight;
        }
        if (mChangePosition) {
            long totalTimeDuration = getDuration();
            mSeekTimePosition = (int) (mDownPosition + (deltaX * totalTimeDuration / curWidth) / mSeekRatio);
            if (mSeekTimePosition > totalTimeDuration)
                mSeekTimePosition = (int) totalTimeDuration;
            String seekTime = DateUtils.stringForTime(mSeekTimePosition);
            String totalTime = DateUtils.stringForTime(totalTimeDuration);
            showProgressDialog(deltaX, seekTime, mSeekTimePosition, totalTime, totalTimeDuration);
        } else if (mChangeVolume) {
            deltaY = -deltaY;
            int max = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
            int deltaV = (int) (max * deltaY * 3 / curHeight);
            mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, mGestureDownVolume + deltaV, 0);
            int volumePercent = (int) (mGestureDownVolume * 100 / max + deltaY * 3 * 100 / curHeight);

            showVolumeDialog(-deltaY, volumePercent);
        } else if (mBrightness) {
            if (Math.abs(deltaY) > mThreshold) {
                float percent = (-deltaY / curHeight);
                onBrightnessSlide(percent);
                mDownY = y;
            }
        }
    }

    /**
     * 滑动改变亮度
     *
     * @param percent
     */
    protected void onBrightnessSlide(float percent) {
        mBrightnessData = ((Activity) (mContext)).getWindow().getAttributes().screenBrightness;
        if (mBrightnessData <= 0.00f) {
            mBrightnessData = 0.50f;
        } else if (mBrightnessData < 0.01f) {
            mBrightnessData = 0.01f;
        }
        WindowManager.LayoutParams lpa = ((Activity) (mContext)).getWindow().getAttributes();
        lpa.screenBrightness = mBrightnessData + percent;
        if (lpa.screenBrightness > 1.0f) {
            lpa.screenBrightness = 1.0f;
        } else if (lpa.screenBrightness < 0.01f) {
            lpa.screenBrightness = 0.01f;
        }
        showBrightnessDialog(lpa.screenBrightness);
        ((Activity) (mContext)).getWindow().setAttributes(lpa);
    }

    protected void touchSurfaceUp() {
        if (mChangePosition) {
            long duration = getDuration();
            int progress = (int) (mSeekTimePosition * 100 / (duration == 0 ? 1 : duration));
            if (mBottomProgressBar != null) {
                mBottomProgressBar.setProgress(progress);
            }
        }
        mTouchingProgressBar = false;
        dismissProgressDialog();
        dismissVolumeDialog();
        dismissBrightnessDialog();
        if (mChangePosition && getVideoViewMgrBridge() != null && (mCurrentState == STATE_PLAYING || mCurrentState == STATE_PAUSE)) {
            try {
                // 移动特定位置
                getVideoViewMgrBridge().seekTo(mSeekTimePosition);
            } catch (Exception e) {
                LogUtils.e(TAG, e);
            }
            // 同步进度
            long duration = getDuration();
            int progress = (int) (mSeekTimePosition * 100 / (duration == 0 ? 1 : duration));
            if (mProgressBar != null) {
                mProgressBar.setProgress(progress);
            }
            if (mVideoViewCallBack != null && isCurrentPlayerListener()) {
                LogUtils.d(TAG, "onTouchScreenSeekPosition");
                mVideoViewCallBack.onTouchScreenSeekPosition(mOriginUrl, mTitle, this);
            }
        } else if (mBrightness) {
            if (mVideoViewCallBack != null) {
                LogUtils.d(TAG, "onTouchScreenSeekLight");
                mVideoViewCallBack.onTouchScreenSeekLight(mOriginUrl, mTitle, this);
            }
        } else if (mChangeVolume) {
            if (mVideoViewCallBack != null) {
                LogUtils.d(TAG, "onTouchScreenSeekVolume");
                mVideoViewCallBack.onTouchScreenSeekVolume(mOriginUrl, mTitle, this);
            }
        }
    }

    //<editor-fold>SeekBar.OnSeekBarChangeListener实现
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        mHadSeekTouch = true;
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        if (mVideoViewCallBack != null && isCurrentPlayerListener()) {
            if (isCurrentFullscreen()) {
                LogUtils.d(TAG, "onClickSeekbarFullscreen");
                mVideoViewCallBack.onClickSeekbarFullscreen(mOriginUrl, mTitle, this);
            } else {
                LogUtils.d(TAG, "onClickSeekbar");
                mVideoViewCallBack.onClickSeekbar(mOriginUrl, mTitle, this);
            }
        }
        if (getVideoViewMgrBridge() != null && mIsHadPlay) {
            try {
                int time = (int) (seekBar.getProgress() * getDuration() / 100);
                getVideoViewMgrBridge().seekTo(time);
            } catch (Exception e) {
                LogUtils.e(TAG, e);
            }
        }
        mHadSeekTouch = false;
    }
    //</editor-fold>

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
        if (mProgressBar == null || mCurrentTimeView == null || mTotalTimeView == null) {
            return;
        }
        mProgressBar.setProgress(0);
        mProgressBar.setSecondaryProgress(0);
        mCurrentTimeView.setText(DateUtils.stringForTime(0));
        if (mBottomProgressBar != null)
            mBottomProgressBar.setProgress(0);
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


    protected boolean mPostDismiss        = false;
    //触摸显示后隐藏的时间
    protected int     mDismissControlTime = 2500;
    Runnable widgetDismissTask = new Runnable() {
        @Override
        public void run() {
            if (mCurrentState != STATE_NORMAL
                    && mCurrentState != STATE_ERROR
                    && mCurrentState != STATE_AUTO_COMPLETE) {
                if (getActivityCtx(getContext()) != null) {
                    hideAllWidgets();
                    setViewShowState(mLockScreenBtn, GONE);
                    if (mHideKey && mIsCurrentFullscreen && mShowVKey) {
                        SmartBarUtils.hideNavKey(mContext);
                    }
                }
                if (mPostDismiss) {
                    postDelayed(this, mDismissControlTime);
                }
            }
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
     * 设置View的显示状态
     */
    protected void setViewShowState(View view, int visibility) {
        if (view != null) {
            view.setVisibility(visibility);
        }
    }

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
     * 显示进度弹窗
     */
    protected abstract void showProgressDialog(float deltaX, String seekTime, int seekPosition, String totalTime, long totalTimeDuration);

    /**
     * 隐藏进度弹框
     */
    protected abstract void dismissProgressDialog();

    /**
     * 显示音量弹框
     */
    protected abstract void showVolumeDialog(float deltaY, int percentage);

    /**
     * 隐藏音量弹窗
     */
    protected abstract void dismissVolumeDialog();

    /**
     * 显示亮度弹框
     */
    protected abstract void showBrightnessDialog(float brightnessPercentage);

    /**
     * 隐藏亮度弹窗
     */
    protected abstract void dismissBrightnessDialog();

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

    public boolean setUpLazy(String url, boolean isCachePlay, File cachePath, Map<String, String> mapHeadData, String title) {
        mOriginUrl = url;
        mIsCachePlay = isCachePlay;
        mCachePath = cachePath;
        mSetUpLazy = true;
        mTitle = title;
        mMapHeadData = mapHeadData;
        if (isCurrentPlayerListener() && System.currentTimeMillis() - mSetupViewTimeMillis < SET_UP_VIEW_DELAY_TIME) {
            return false;
        }
        mUrl = "waiting";
        mCurrentState = STATE_NORMAL;
        return true;
    }

    public void setIsTouchWidgetFull(boolean isTouchWidgetFull) {
        this.mIsTouchWidgetFull = isTouchWidgetFull;
    }


    public View getStartBtn() {
        return mStartBtn;
    }

    public ImageView getFullscreeBtn() {
        return mFullscreenBtn;
    }

    public ImageView getBackBtn() {
        return mBackBtn;
    }

    public ImageView getLockScreenBtn() {
        return mLockScreenBtn;
    }

    public int getEnlargeImageRes() {
        if (mEnlargeImageRes == -1) {
            return R.drawable.video_enlarge;
        }
        return mEnlargeImageRes;
    }

    /**
     * 设置右下角 显示切换到全屏 的按键资源
     * 必须在setUp之前设置
     * 不设置使用默认
     */
    public void setEnlargeImageRes(int mEnlargeImageRes) {
        this.mEnlargeImageRes = mEnlargeImageRes;
    }

    public int getShrinkImageRes() {
        if (mShrinkImageRes == -1) {
            return R.drawable.video_shrink;
        }
        return mShrinkImageRes;
    }

    /**
     * 设置右下角 显示退出全屏 的按键资源
     * 必须在setUp之前设置
     * 不设置使用默认
     */
    public void setShrinkImageRes(int mShrinkImageRes) {
        this.mShrinkImageRes = mShrinkImageRes;
    }
}
