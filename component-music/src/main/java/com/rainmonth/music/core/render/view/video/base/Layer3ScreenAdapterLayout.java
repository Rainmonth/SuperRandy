package com.rainmonth.music.core.render.view.video.base;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.transition.TransitionManager;

import com.rainmonth.common.utils.DensityUtils;
import com.rainmonth.common.utils.SmartBarUtils;
import com.rainmonth.music.R;
import com.rainmonth.music.core.utils.OrientationUtils;
import com.socks.library.KLog;

import java.lang.reflect.Constructor;

/**
 * 大小屏幕切换、机型适配层
 *
 * @author 张豪成
 * @date 2019-12-17 20:00
 */
public abstract class Layer3ScreenAdapterLayout extends Layer2PlayerControlLayout {
    //保存系统状态ui
    protected int mSystemUiVisibility;

    //当前item框的屏幕位置
    protected int[] mListItemRect;

    //当前item的大小
    protected int[] mListItemSize;

    //是否需要在利用window实现全屏幕的时候隐藏actionbar
    protected boolean mActionBar = false;

    //是否需要在利用window实现全屏幕的时候隐藏statusbar
    protected boolean mStatusBar = false;

    //是否使用全屏动画效果
    protected boolean mShowFullAnimation = true;

    //是否自动旋转
    protected boolean mRotateViewAuto = true;

    //旋转使能后是否跟随系统设置
    protected boolean mRotateWithSystem = true;

    //当前全屏是否锁定全屏
    protected boolean mLockLand = false;

    //是否根据视频尺寸，自动选择竖屏全屏或者横屏全屏，注意，这时候默认旋转无效
    protected boolean mAutoFullWithSize = false;

    //是否需要竖屏全屏的时候判断状态栏
    protected boolean isNeedAutoAdaptation = false;

    //全屏动画是否结束了
    protected boolean mFullAnimEnd = true;

    //小窗口关闭按键
    protected View mSmallClose;

    //旋转工具类
    protected OrientationUtils mOrientationUtils;

    //全屏返回监听，如果设置了，默认返回无效
    protected View.OnClickListener mBackFromFullScreenListener;
    protected Handler mInnerHandler = new Handler();

    public Layer3ScreenAdapterLayout(Context context) {
        super(context);
    }

    public Layer3ScreenAdapterLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Layer3ScreenAdapterLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public Layer3ScreenAdapterLayout(@NonNull Context context, boolean isFullscreen) {
        super(context, isFullscreen);
    }

    @Override
    protected void init(Context context) {
        super.init(context);
        mSmallClose = findViewById(R.id.player_small_close);
    }

    @Override
    public void onBackFullscreen() {
        clearFullscreenLayout();
    }

    protected void clearFullscreenLayout() {

    }

    //<editor-fold>Layer0PlayerDrawLayout方法实现
    @Override
    protected void setSmallVideoTextureView() {
        if (mProgressBar != null) {
            mProgressBar.setOnTouchListener(null);
            mProgressBar.setVisibility(INVISIBLE);
        }
        if (mFullscreenBtn != null) {
            mFullscreenBtn.setOnTouchListener(null);
            mFullscreenBtn.setVisibility(INVISIBLE);
        }
        if (mCurrentTimeView != null) {
            mCurrentTimeView.setVisibility(INVISIBLE);
        }
        if (mRenderViewParent != null) {
            mRenderViewParent.setOnClickListener(null);
        }
        if (mSmallClose != null) {
            mSmallClose.setVisibility(VISIBLE);
            mSmallClose.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    hideSmallVideos();
                    releaseVideos();
                }
            });
        }

    }

    public void hideSmallVideos() {
        final ViewGroup vg = getViewGroup();
        Layer3ScreenAdapterLayout playerLayout = vg.findViewById(getSmallId());
        removeVideo(vg, getSmallId());
        // 状态保存
        mCurrentState = getVideoViewMgrBridge().getLastState();
        if (playerLayout != null) {
            cloneParams(playerLayout, this);
        }
        getVideoViewMgrBridge().setListener(getVideoViewMgrBridge().lastListener());
        getVideoViewMgrBridge().setLastListener(null);
        updateStateAndUi(mCurrentState);
        addRenderView();
        mSetupViewTimeMillis = System.currentTimeMillis();
        if (mVideoViewCallBack != null) {
            KLog.d(TAG, "onQuitSmallWidget");
            mVideoViewCallBack.onQuitSmallWidget(mOriginUrl, mTitle, this);
        }
    }

    /**
     * 克隆切换参数
     *
     * @param from
     * @param to
     */
    protected void cloneParams(Layer3ScreenAdapterLayout from, Layer3ScreenAdapterLayout to) {
        to.mIsHadPlay = from.mIsHadPlay;
        to.mPlayTag = from.mPlayTag;
        to.mPlayPosition = from.mPlayPosition;
        to.mEffectFilter = from.mEffectFilter;
        to.mFullPauseBitmap = from.mFullPauseBitmap;
        to.mNeedShowWifiTip = from.mNeedShowWifiTip;
        to.mShrinkImageRes = from.mShrinkImageRes;
        to.mEnlargeImageRes = from.mEnlargeImageRes;
        to.mRotate = from.mRotate;
        to.mShowPauseCover = from.mShowPauseCover;
        to.mDismissControlTime = from.mDismissControlTime;
        to.mSeekRatio = from.mSeekRatio;
        to.mIsNetChanged = from.mIsNetChanged;
        to.mNetSate = from.mNetSate;
        to.mRotateWithSystem = from.mRotateWithSystem;
        to.mStateBeforeBuffering = from.mStateBeforeBuffering;
        to.mRenderer = from.mRenderer;
        to.mMode = from.mMode;
        to.mBackFromFullScreenListener = from.mBackFromFullScreenListener;
        to.mVideoProgressListener = from.mVideoProgressListener;
        to.mHadPrepared = from.mHadPrepared;
        to.mStartAfterPrepared = from.mStartAfterPrepared;
        to.mPauseBeforePrepared = from.mPauseBeforePrepared;
        to.mReleaseWhenLossAudio = from.mReleaseWhenLossAudio;
        to.mVideoViewCallBack = from.mVideoViewCallBack;
        to.mActionBar = from.mActionBar;
        to.mStatusBar = from.mStatusBar;
        to.mAutoFullWithSize = from.mAutoFullWithSize;
        if (from.mSetUpLazy) {
            to.setUpLazy(from.mOriginUrl, from.mIsCachePlay, from.mCachePath, from.mMapHeadData, from.mTitle);
            to.mUrl = from.mUrl;
        } else {
            to.setUp(from.mOriginUrl, from.mIsCachePlay, from.mCachePath, from.mMapHeadData, from.mTitle);
        }
        to.setLooping(from.isLooping());
        to.setIsTouchWidgetFull(from.mIsTouchWidgetFull);
        to.setSpeed(from.getSpeed(), from.mSoundTouch);
        to.updateStateAndUi(from.mCurrentState);
    }

    private ViewGroup getViewGroup() {
        return getActivityCtx(getContext()).findViewById(Window.ID_ANDROID_CONTENT);
    }

    private void removeVideo(ViewGroup vg, int id) {
        View old = vg.findViewById(id);
        if (old != null) {
            if (old.getParent() != null) {
                ViewGroup vgParent = (ViewGroup) old.getParent();
                vgParent.removeView(vg);
            }
        }
    }

    //</editor-fold>

    @Override
    protected void lockTouchLogic() {
        super.lockTouchLogic();
        if (!mIsCurrentScreenLock) {
            if (mOrientationUtils != null) {
                mOrientationUtils.setEnable(isRotateVideoAuto());
            }
        } else {
            if (mOrientationUtils != null) {
                mOrientationUtils.setEnable(false);
            }
        }
    }

    public boolean isRotateVideoAuto() {
        if (mAutoFullWithSize) {
            return false;
        }
        return mRotateViewAuto;
    }

    @Override
    public void onPrepared() {
        super.onPrepared();
        checkAutoFullSizeWhenFull();
    }

    @Override
    public void onInfo(int what, int extra) {
        super.onInfo(what, extra);
        if (what == getVideoViewMgrBridge().getRotateInfoFlag()) {
            checkAutoFullSizeWhenFull();
        }
    }


    public Layer3ScreenAdapterLayout startWindowFullscreen(Context context, boolean actionBar, boolean statusBar) {
        mSystemUiVisibility = ((Activity) context).getWindow().getDecorView().getSystemUiVisibility();
        SmartBarUtils.hideSupportActionBar(context, actionBar, statusBar);
        if (mHideKey) {
            SmartBarUtils.hideNavKey(context);
        }
        this.mActionBar = actionBar;
        this.mStatusBar = statusBar;
        mListItemRect = new int[2];
        mListItemSize = new int[2];
        final ViewGroup vg = getViewGroup();
        removeVideo(vg, getFullId());
        //全屏前暂停处理逻辑
        pauseFullCoverLogic();
        if (mRenderViewParent.getChildCount() > 0) {
            mRenderViewParent.removeAllViews();
        }
        saveLocationStatus(context, actionBar, statusBar);
        //切换时关闭定时器
        cancelProgressTimer();
        boolean hasNewConstructor = true;
        try {
            Layer3ScreenAdapterLayout.this.getClass().getConstructor(Context.class, Boolean.class);
        } catch (Exception e) {
            hasNewConstructor = false;
            KLog.e(TAG, e);
        }
        try {
            Constructor<Layer3ScreenAdapterLayout> constructor;
            Layer3ScreenAdapterLayout player;
            if (!hasNewConstructor) {
                constructor = (Constructor<Layer3ScreenAdapterLayout>) Layer3ScreenAdapterLayout.this.getClass().getConstructor(Context.class);
                player = constructor.newInstance(mContext);
            } else {
                constructor = (Constructor<Layer3ScreenAdapterLayout>) Layer3ScreenAdapterLayout.this.getClass().getConstructor(Context.class, Boolean.class);
                player = constructor.newInstance(mContext);
            }
            player.setId(getFullId());
            player.setIsCurrentFullscreen(true);
            player.setVideoViewCallback(mVideoViewCallBack);

            cloneParams(this, player);

            if (player.getFullscreeBtn() != null) {
                player.getFullscreeBtn().setImageResource(getShrinkImageRes());
                player.getFullscreeBtn().setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mBackFromFullScreenListener == null) {
                            clearFullscreenLayout();
                        } else {
                            mBackFromFullScreenListener.onClick(v);
                        }
                    }
                });
            }

            if (player.getBackBtn() != null) {
                player.getBackBtn().setVisibility(VISIBLE);
                player.getBackBtn().setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mBackFromFullScreenListener == null) {
                            clearFullscreenLayout();
                        } else {
                            mBackFromFullScreenListener.onClick(v);
                        }
                    }
                });
            }

            final ViewGroup.LayoutParams lpParent = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            final FrameLayout frameLayout = new FrameLayout(context);
            frameLayout.setBackgroundColor(Color.BLACK);

            if (mShowFullAnimation) {
                mFullAnimEnd = false;
                LayoutParams lp = new LayoutParams(getWidth(), getHeight());
                lp.setMargins(mListItemRect[0], mListItemRect[1], 0, 0);
                frameLayout.addView(player, lp);
                vg.addView(frameLayout, lpParent);
                mInnerHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        TransitionManager.beginDelayedTransition(vg);
                        // todo
//                        resolveFullVideoShow(context, player, frameLayout);
                        mFullAnimEnd = true;
                    }
                }, 300);
            } else {
                LayoutParams lp = new LayoutParams(getWidth(), getHeight());
                frameLayout.addView(player, lp);
                vg.addView(frameLayout, lpParent);
                player.setVisibility(INVISIBLE);
                frameLayout.setVisibility(INVISIBLE);
                // todo
//                resolveFullVideoShow(context, player, frameLayout);
            }

            player.addRenderView();
            player.startProgressTimer();
            getVideoViewMgrBridge().setLastListener(this);
            getVideoViewMgrBridge().setListener(player);
            // todo
//            checkoutState();
            return player;
        } catch (Exception e) {
            KLog.e(TAG, e);
        }
        return null;
    }


    /**
     * 全屏的暂停的时候返回页面不黑色
     */
    private void pauseFullCoverLogic() {
        if (mCurrentState == Layer3ScreenAdapterLayout.STATE_PAUSE && mRenderView != null
                && (mFullPauseBitmap == null || mFullPauseBitmap.isRecycled()) && mShowPauseCover) {
            try {
                initCover();
            } catch (Exception e) {
                e.printStackTrace();
                mFullPauseBitmap = null;
            }
        }
    }

    /**
     * 全屏的暂停返回的时候返回页面不黑色
     */
    private void pauseFullBackCoverLogic(Layer3ScreenAdapterLayout gsyVideoPlayer) {
        //如果是暂停状态
        if (gsyVideoPlayer.mCurrentState == Layer3ScreenAdapterLayout.STATE_PAUSE
                && gsyVideoPlayer.mRenderView != null && mShowPauseCover) {
            //全屏的位图还在，说明没播放，直接用原来的
            if (gsyVideoPlayer.mFullPauseBitmap != null
                    && !gsyVideoPlayer.mFullPauseBitmap.isRecycled() && mShowPauseCover) {
                mFullPauseBitmap = gsyVideoPlayer.mFullPauseBitmap;
            } else if (mShowPauseCover) {
                //不在了说明已经播放过，还是暂停的话，我们拿回来就好
                try {
                    initCover();
                } catch (Exception e) {
                    e.printStackTrace();
                    mFullPauseBitmap = null;
                }
            }
        }
    }

    private void saveLocationStatus(Context context, boolean statusBar, boolean actionBar) {
        getLocationOnScreen(mListItemRect);
        if (context instanceof Activity) {
            int statusBarH = DensityUtils.getStatusBarHeight(context);
            int actionBarH = DensityUtils.getActionBarHeight((Activity) context);
            boolean isTranslucent = ((WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS & ((Activity) context).getWindow().getAttributes().flags)
                    == WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            if (statusBar && !isTranslucent) {
                mListItemRect[1] = mListItemRect[1] - statusBarH;
            }
            if (actionBar) {
                mListItemRect[1] = mListItemRect[1] - actionBarH;
            }
        }

        mListItemSize[0] = getWidth();
        mListItemSize[1] = getHeight();
    }

    protected void checkAutoFullSizeWhenFull() {
        if (mIsCurrentFullscreen) {
            // 确保开启竖屏检测的时候正常全屏
            boolean isVertical = isVerticalFullByVideoSize();
            KLog.d(TAG, "Layer3 onPrepared isVerticalFullByVideoSize " + isVertical);
            if (isVertical) {
                if (mOrientationUtils != null) {
                    mOrientationUtils.backToProtVideo();
                    checkAutoFullWithSizeAndAdaptation(this);
                }
            }
        }
    }

    protected void checkAutoFullWithSizeAndAdaptation(Layer3ScreenAdapterLayout player) {
        if (player != null) {
            if (isNeedAutoAdaptation && isAutoFullWithSize() &&
                    isVerticalVideo() && isFullHideStatusBar()) {
                mInnerHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        player.getCurrentPlayer().autoAdaptation();
                    }
                }, 100);
            }
        }
    }

    protected boolean isFullHideStatusBar() {
        return mStatusBar;
    }

    /**
     * 获取当前正在播放的播放控件
     *
     * @return 当前正在播放的控件
     */
    protected Layer3ScreenAdapterLayout getCurrentPlayer() {
        if (getFullWindowPlayer() != null) {
            return getFullWindowPlayer();
        }
        if (getSmallWindowPlayer() != null) {
            return getSmallWindowPlayer();
        }
        return this;
    }

    protected Layer3ScreenAdapterLayout getFullWindowPlayer() {
        ViewGroup vg = getActivityCtx(getContext()).findViewById(Window.ID_ANDROID_CONTENT);
        final View small = vg.findViewById(getSmallId());
        Layer3ScreenAdapterLayout player = null;
        if (small != null) {
            player = (Layer3ScreenAdapterLayout) small;
        }
        return player;
    }

    protected Layer3ScreenAdapterLayout getSmallWindowPlayer() {
        ViewGroup vg = getActivityCtx(getContext()).findViewById(Window.ID_ANDROID_CONTENT);
        final View full = vg.findViewById(getFullId());
        Layer3ScreenAdapterLayout player = null;
        if (full != null) {
            player = (Layer3ScreenAdapterLayout) full;
        }
        return player;
    }

    /**
     * 自动适配
     */
    protected void autoAdaptation() {
        Context context = getContext();
        if (isVerticalVideo()) {
            int[] location = new int[2];
            getLocationOnScreen(location);
            if (location[1] == 0) {
                KLog.d(TAG, "竖屏，系统未将布局下移，手动添加Padding将其下移");
                setPadding(0, DensityUtils.getStatusBarHeight(context), 0, 0);
            } else {
                KLog.d(TAG, "竖屏，系统将布局下移");
            }
        }
    }


    public void setRotateViewAuto(boolean rotateViewAuto) {
        this.mRotateViewAuto = rotateViewAuto;
        if (mOrientationUtils != null) {
            mOrientationUtils.setEnable(rotateViewAuto);
        }
    }

    public boolean isLooping() {
        return mIsLoop;
    }

    public void setLooping(boolean looping) {
        this.mIsLoop = looping;
    }

    //<editor-fold>子类要实现的方法
    protected abstract void releaseVideos();

    /**
     * 获取小屏播放id
     *
     * @return 小屏id
     */
    protected abstract int getSmallId();

    /**
     * 获取全屏播放id
     *
     * @return 全屏id
     */
    protected abstract int getFullId();
    //</editor-fold>

    public boolean isVerticalFullByVideoSize() {
        return isVerticalVideo() && isAutoFullWithSize();
    }

    public boolean isVerticalVideo() {
        boolean isVertical = false;
        int videoHeight = getCurrentVideoHeight();
        int videoWidth = getCurrentVideoWidth();
        KLog.d(TAG, "Layer3 isVerticalVideo videoHeight:" + videoHeight + " videoWidth:" + videoWidth);
        KLog.d(TAG, "Layer3 isVerticalVideo mRotate:" + mRotate);
        if (videoHeight > 0 && videoWidth > 0) {
            if (mRotate == 90 || mRotate == 270) {
                isVertical = videoWidth > videoHeight;
            } else {
                isVertical = videoHeight > videoWidth;
            }
        }

        return isVertical;
    }

    public boolean isAutoFullWithSize() {
        return mAutoFullWithSize;
    }
}
