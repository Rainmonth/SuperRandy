package com.hhdd.kada.main.playback;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.database.ContentObserver;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.alexvasilkov.gestures.GestureController;
import com.hhdd.android.common.ServiceProxyFactory;
import com.hhdd.android.ref.StrongReference;
import com.hhdd.core.model.BookDetailInfo;
import com.hhdd.core.model.QuestionDetailInfo;
import com.hhdd.core.service.BookService;
import com.hhdd.core.service.DefaultCallback;
import com.hhdd.core.service.FavoriteService;
import com.hhdd.core.service.PlayActionService;
import com.hhdd.core.service.UserHabitService;
import com.hhdd.core.service.UserService;
import com.hhdd.cryptokada.CryptoKadaLib;
import com.hhdd.kada.Constants;
import com.hhdd.kada.KaDaApplication;
import com.hhdd.kada.R;
import com.hhdd.kada.android.library.app.ActivityHelper;
import com.hhdd.kada.android.library.utils.LocalDisplay;
import com.hhdd.kada.api.API;
import com.hhdd.kada.api.BookAPI;
import com.hhdd.kada.api.PayAPI;
import com.hhdd.kada.app.serviceproxy.ServiceProxyName;
import com.hhdd.kada.base.BaseActivity;
import com.hhdd.kada.coin.ScreenObserver;
import com.hhdd.kada.dialog.DialogFactory;
import com.hhdd.kada.download.DownloadItemType;
import com.hhdd.kada.download.DownloadManager;
import com.hhdd.kada.download.IDownloadTaskListener;
import com.hhdd.kada.main.common.DataLoadingView;
import com.hhdd.kada.main.controller.BookCollectionFragmentController;
import com.hhdd.kada.main.controller.MainActivityController;
import com.hhdd.kada.main.event.BookFinishDownloadEvent;
import com.hhdd.kada.main.event.BookLimitFreeEndedEvent;
import com.hhdd.kada.main.event.BookSubscribeStatusEvent;
import com.hhdd.kada.main.event.ConnectivityChangedEvent;
import com.hhdd.kada.main.event.EventCenter;
import com.hhdd.kada.main.event.FavoriteEvent;
import com.hhdd.kada.main.event.PauseEvent;
import com.hhdd.kada.main.listen.ListenActivity;
import com.hhdd.kada.main.listen.ListenService2;
import com.hhdd.kada.main.listener.OnChildViewClickListener;
import com.hhdd.kada.main.manager.PermissionManager;
import com.hhdd.kada.main.manager.TimeOutManager;
import com.hhdd.kada.main.mediaserver.MediaServer2;
import com.hhdd.kada.main.model.BookCollectionDetailInfo;
import com.hhdd.kada.main.model.BookInfo;
import com.hhdd.kada.main.model.ShareInfo;
import com.hhdd.kada.main.playback.event.UpdateBookCollectionDetailEvent;
import com.hhdd.kada.main.settings.Settings;
import com.hhdd.kada.main.ui.activity.LoginOrRegisterActivity;
import com.hhdd.kada.main.ui.activity.RedirectActivity;
import com.hhdd.kada.main.ui.adapter.MyBaseAdapter;
import com.hhdd.kada.main.ui.dialog.ChildrenLockDialog;
import com.hhdd.kada.main.ui.dialog.ContentIsDeletedDialog;
import com.hhdd.kada.main.utils.ActivityUtil;
import com.hhdd.kada.main.utils.AppUtils;
import com.hhdd.kada.main.utils.Extflag;
import com.hhdd.kada.main.utils.ListUtils;
import com.hhdd.kada.main.utils.MemorySizeUtils;
import com.hhdd.kada.main.utils.NetworkUtils;
import com.hhdd.kada.main.utils.SafeHandler;
import com.hhdd.kada.main.utils.StringUtil;
import com.hhdd.kada.main.utils.TimeUtil;
import com.hhdd.kada.main.utils.ToastUtils;
import com.hhdd.kada.main.views.LastPageLayout;
import com.hhdd.kada.main.views.NoDoubleClickListener;
import com.hhdd.kada.main.views.PlaybackBookPageLayout;
import com.hhdd.kada.main.views.QuestionPageLayout;
import com.hhdd.kada.main.views.animator.ScaleAnimator;
import com.hhdd.kada.main.views.gestureimageview.GestureImageView;
import com.hhdd.kada.medal.UserTrack;
import com.hhdd.kada.module.audio.AudioName;
import com.hhdd.kada.module.player.AudioInfo;
import com.hhdd.kada.module.player.IMediaPlayer;
import com.hhdd.kada.module.player.OnPlayListener;
import com.hhdd.kada.module.player.PlayMode;
import com.hhdd.kada.module.talentplan.playback.TalentPlanPlaybackActivity;
import com.hhdd.kada.module.userhabit.StaCtrName;
import com.hhdd.kada.module.userhabit.StaPageName;
import com.hhdd.kada.pay.PayManager;
import com.hhdd.kada.pay.controller.BasePayController;
import com.hhdd.kada.record.callback.ChildrenDialogCallback;
import com.hhdd.kada.share.ShareProvider;
import com.hhdd.kada.share.ShareUtils;
import com.hhdd.kada.store.model.OrderFragParamData;
import com.hhdd.kada.widget.SwitchButton;
import com.hhdd.logger.LogHelper;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.greenrobot.event.EventBus;
import se.emilsjolander.flipview.FlipView;
import se.emilsjolander.flipview.OverFlipMode;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

/**
 * Created by simon on 15/6/15.
 */
public class PlaybackActivity extends BaseActivity {

    private static final int FROM_EVERYDAY_TASK = 1; // 每日任务

    public static final String ACTION_PLAY_BOOKID = "bookId";
    public static final String ACTION_PLAY_SOURCE_VERSION = "sourceVersion";
    public static final String ACTION_IS_ON_BACKGROUND = "isOnBackground";

    public final String TAG = "PlaybackActivity";

    public static final String TYPE_SUBSCRIBE_COLLECTION = "PlaybackActivitySubscribeCollect";

    private BookCollectionDetailInfo bookCollectionDetailInfo;
    private boolean mIsContinuePlay = false;  //是否自动连播
    private String sessionId;   //用于播放行为提交服务器的唯一标识
    private SwitchButton continuePlayCheckView;
    private TextView continueTextView;
    // 是否来源于合集详情继续播放，如果是合集详情继续播放进入，直接播放
    private boolean isContinuePlay;
    private boolean isEnterLastPage;//当前页是否进入了最后一页。用于修复1004011

    //------儿童锁相关 start----------
    private FrameLayout flScreenLockLayer;
    private ImageView ivScreenLockBtn;
    private ObjectAnimator ivScreenLockBtnAnimator;
    private Runnable ivScreenLockBtnHideRunnable = new Runnable() {
        @Override
        public void run() {
            if (ivScreenLockBtnAnimator != null) {
                ivScreenLockBtnAnimator.cancel();
                ivScreenLockBtnAnimator = null;
            }
            ivScreenLockBtnAnimator = ObjectAnimator.ofFloat(ivScreenLockBtn, "alpha", 1.0f, 0.0f);
            ivScreenLockBtnAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    ivScreenLockBtn.setVisibility(View.INVISIBLE);
                }
            });
            ivScreenLockBtnAnimator.start();
        }
    };
    private Runnable mSwitchToSensorOriWithoutRecordRunnable = new Runnable() {
        @Override
        public void run() {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
        }
    };
    private Runnable mSwitchToSensorOriWidthRecordRunnable = new Runnable() {
        @Override
        public void run() {
            orientation = Configuration.ORIENTATION_LANDSCAPE;
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
        }
    };
    //------儿童锁相关 end----------

    public static final void startActivity(Context context, int bookId) {
        if (context == null) {
            return;
        }

        Intent intent = new Intent(context, PlaybackActivity.class);
        intent.putExtra("bookId", bookId);
        context.startActivity(intent);
    }

    public static final void startActivity(Context context, int bookId, BookCollectionDetailInfo bookCollectionDetailInfo, int history) {
        startActivity(context, bookId, bookCollectionDetailInfo, history, false);
    }

    public static final void startActivity(Context context, int bookId, BookCollectionDetailInfo bookCollectionDetailInfo, int history, boolean isContinuePlay) {
        if (context == null) {
            return;
        }

        Intent intent = new Intent(context, PlaybackActivity.class);
        intent.putExtra("bookId", bookId);
        intent.putExtra("bookCollectionDetailInfo", bookCollectionDetailInfo);
        intent.putExtra("history", history);
        intent.putExtra(Constants.INTENT_KEY_BOOK_COLLECTION_CONTINUE_PLAY, isContinuePlay);
        context.startActivity(intent);
    }

    public static final void startActivity(Context context, int bookId, int fromFlag) {
        if (context == null) {
            return;
        }

        Intent intent = new Intent(context, PlaybackActivity.class);
        intent.putExtra("bookId", bookId);
        intent.putExtra("fromFlag", fromFlag);
        context.startActivity(intent);
    }

    public static final void startActivity(Context context, int bookId, int extFlag, int version) {
        if (context == null) {
            return;
        }

        Intent intent = new Intent(context, PlaybackActivity.class);
        intent.putExtra("bookId", bookId);
        intent.putExtra(ACTION_PLAY_SOURCE_VERSION, version);
        context.startActivity(intent);
    }

    public static final void startActivity(Context context, int bookId, int version, String bookCover, int history) {
        if (context == null) {
            return;
        }

        Intent intent = new Intent(context, PlaybackActivity.class);
        intent.putExtra("bookId", bookId);
        intent.putExtra("bookCover", bookCover);
        intent.putExtra("history", history);
        intent.putExtra(ACTION_PLAY_SOURCE_VERSION, version);
        context.startActivity(intent);
    }

    public void onEvent(ConnectivityChangedEvent event) {
        if (mPlaybackController == null) {
            return;
        }

        if (mPlayBtn == null || mFlipView == null) {
            return;
        }

        if (!event.isConnectedWifi) {
            return;
        }

        if (mPlayBtn.isSelected()) {
            if (mFlipView.getCurrentPage() >= 2) {
                //回退两页，重新加载
                mFlipView.flipTo(mFlipView.getCurrentPage() - 2);
            }
        }
    }

    /**
     * 1、{@link com.hhdd.kada.main.ui.book.BookCollectionFragment} 在收到登陆成功消息后会请求接口刷新数据，
     * 接口请求成功后{@link BookCollectionFragmentController#initData()}}向这里发送数据更新通知消息 走继续订阅流程
     * <p>
     * 2、付款成功后，{@link com.hhdd.kada.main.ui.book.BookCollectionFragment} 会请求接口刷新数据，
     * 接口请求成功后{@link com.hhdd.kada.main.controller.BookCollectionFragmentController#initData()}向这里发送数据更新通知消息 刷新UI
     *
     * @param event
     */
    public void onEvent(UpdateBookCollectionDetailEvent event) {
        if (event == null || event.bookCollectionDetailInfo == null) {
            return;
        }

        if (event.type == 1) { // 继续订阅流程
            doSubscribeCollection(event.bookCollectionDetailInfo);
        } else if (event.type == 2) { // 订阅成功 刷新UI
            refreshUiByCostSubscribeSuccess(event.bookCollectionDetailInfo);
        }
    }

    public void onEvent(BookSubscribeStatusEvent event) {
        if (event == null) {
            return;
        }

        if (event.getStatus() != 1) { // 没有订阅成功 就不处理刷新了
            return;
        }

        /**
         * 再次展示进度框 等待{@link com.hhdd.kada.main.ui.book.BookCollectionFragment}刷新数据后通知数据更新
         */
        showProgressDialog();
    }

    BookDetailInfo mBookDetailInfo;
    BookInfo mBookInfo;
    QuestionDetailInfo mQuestionDetailInfo;
    List<Integer> questionPageIndex = new ArrayList<Integer>();
    int mBookId;
    int mSourceVersion = 0;
    String mBookName;
    String mBookCover;
    int mExtflag;
    int mDirection;
    String muploadUser;
    int mType;
    int historyReadCurrentPage;
    List<BookInfo> mFullRecommendBookList = new ArrayList<>(); // 接口返回数据和合集列表数据合并后的推荐列表
    List<BookInfo> mRecommendBookList = new ArrayList<>(); // 接口返回的推荐列表
    //    int productId;
    int mHhcurrency; //需要多少咔哒币
    String mLogo; //cp标志
//    String bookDetail;

    boolean mIsFullScreenMode = false;
    View mFlipStartPlayContainer;
    View mFlipBg;
    FlipView mFlipView;
    FlipPageAdapter mFlipPageAdapter;

    ImageView backBtn;
    Button mPlayBtn;
    TextView mPageIndicator;
    SeekBar mSeekBar;
    ImageView mFlipControl;
    ImageView mVoiceControl;
    ImageView mLanguageControl;

    ImageView share;
    ImageView downloadLandscape;
    ImageView favorLandscape;

    ImageView downloadPortrait;
    ImageView favorPortrait;

    View smallScreenContainer;
    TextView smallScreenIndicator;
    SeekBar smallScreenSeekbar;
    private int collectId;
    private int flipPosition = -1;

    int fromFlag;
    private List<BookInfo> bookList = new ArrayList<>();

    private RelativeLayout mBottomContainer;

    private DataLoadingView mLoadingView;

    private boolean isShow = false; //底部控制栏是否显示
    private boolean hasQuestion = false;
    private int orientation = Configuration.ORIENTATION_PORTRAIT;

    ScreenObserver playScreeObserver = new ScreenObserver(this);

    private long stayInLastPageTime = 0;
    private boolean isStayInLastPage = false;

    String mAESST = "";
    int totalPageCount;
    int pageCount;//浏览页面数

    boolean mReadingFinished = false; //阅读完成
    boolean mReading85Percent = false;   //阅读完成85%
    boolean isSetting = false;
    boolean isScreenOff = false;//是否锁屏
    boolean isOnbackground = false; //是否在后台
    boolean isPageReadFinish; //当前页阅读完成
    boolean isOnQuestionPage = false;   //当前页是否是问题页
    boolean isGoToShopCart;
    boolean isFirstEnterLastPage = true;
    private ImageView defaultImageView;

    private boolean isNeedMomSubscribePlaying = false;
    private boolean mIsEnteringFullScreen = false;//是否正在进入全屏的过程中。（用于判断返回键按下时是否响应）
    private boolean mIsExitingFullScreen = false;//是否正在退出全屏的过程中。（用于判断返回键按下时是否响应）

    boolean isTracked = false;  //是否打过beginreading点

    private StrongReference<DefaultCallback<BookInfo>> mGetBookInfoCallbackRef;
    private StrongReference<DefaultCallback<BookDetailInfo>> mGetBookDetailCallbackRef;
    private StrongReference<DefaultCallback<QuestionDetailInfo>> mGetQuestionDetailCallbackRef;
    private StrongReference<DefaultCallback<List<BookInfo>>> mGetRecommendListCallbackRef;
    private StrongReference<DefaultCallback> mFreeSubscribeStrongReference;

    private IMediaPlayer mShortMediaPlayer = (IMediaPlayer) ServiceProxyFactory.getProxy().getService(ServiceProxyName.KD_MEDIA_PLAYER);

    /**
     * OSS存储的图片文件本身有问题时，当运营人员更换图片后，会产生新的图片URL，由于本地已缓存了book2/getDetail.json接口数据，下次播放的时候就不再请求了
     * 会造成加载的还是旧的图片URL，所以一直会显示图片加载失败
     * 现在判断如果有图片加载失败，在Activity#onDestroy的时候，把本地缓存的book2/getDetail.json接口数据清除
     *
     * @see TalentPlanPlaybackActivity#isClearLocalDetailData
     */
    private boolean isClearLocalDetailData = false;

    @Override
    public void onPause() {
        super.onPause();

        // 经产品确认，v3.7.5绘本播放页面所有短音频在关屏和跳转页面中均不关闭
        // releaseSoundPlayback();

        // 进入后台后，移除Flag
        dealWithKeepScreenOnFlag(false);

        isOnbackground = true;
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        if (hasFocus) {
            setSystemUiStatusByOrientation(getResources().getConfiguration().orientation);
        }
        super.onWindowFocusChanged(hasFocus);
    }

    @Override
    public void onResume() {
        super.onResume();

        isOnbackground = false;
        if (isGoToShopCart) {
            isGoToShopCart = false;
            togglePlayBtn();
        } else {
            if (mPlayBtn != null && mPlayBtn.isSelected()) {
                // 重新进入，如果是播放状态，添加Flag
                if (currentPlayingIndex < totalPageCount - 1) {
                    dealWithKeepScreenOnFlag(true);
                }
            } else {
                pauseImpl();
            }
        }

        //fix 【Bug转需求】1001917 : 横屏绘本后台播放，切换到前台，界面布局错乱;
        final Configuration configuration = getResources().getConfiguration();
        // 修复 多次进入绘本后onDestroy回调延迟严重问题
        performChangeConfig(configuration);

        //上面的补丁，也解决了下面的问题，所以下面的注释掉了
//        /**
//         * Fixed bug: 1002286
//         * 【红米Note3】【必现】手机保持横握状态，绘本播放完成页调取其它页面再返回，结果页面显示异常
//         * https://www.tapd.cn/21794391/bugtrace/bugs/view?bug_id=1121794391001002286&url_cache_key=f0a0150ed7d946c84e4e4ca158dc533f
//         *
//         * 在红米Note3手机上，点击简介打开简介页面（简介页面是竖屏显示的），在返回时切到横屏没有回调 {@link #onConfigurationChanged(Configuration)}，就会引起这个问题
//         */
//        if (mFlipView != null) {
//            int childCount = mFlipView.getChildCount();
//
//            for (int index = childCount - 1; index >= 0; index--) {
//                View page = mFlipView.getChildAt(index);
//
//                if (page instanceof LastPageLayout
//                        && page.getTag(R.id.playback_page_index) instanceof Integer
//                        && ((Integer) page.getTag(R.id.playback_page_index)).intValue() == currentPlayingIndex) {
//                    ((LastPageLayout) page).setLayoutWithConfiguration();
//                    break;
//                }
//            }
//
//        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_player);

        Intent intent = getIntent();
        if (intent == null) {
            finish();
            return;
        }

        ((TimeOutManager) ServiceProxyFactory.getProxy().getService(ServiceProxyName.TIME_OUT_MANAGER)).startCheck();

        //通过推送或其它外部方式进入绘本播放，若之前页面为听书播放页面或优才计划绘本播放页面，暂停之前播放
        EventBus.getDefault().post(new MainActivityController.OnHideFloatingWindowEvent());
        boolean shouldPostPauseEvent = false;
        List<Activity> activityList = ActivityHelper.getActivities();
        for (Activity activity : activityList) {
            if (activity instanceof ListenActivity || activity instanceof TalentPlanPlaybackActivity) {
                shouldPostPauseEvent = true;
                break;
            }
        }
        if (shouldPostPauseEvent) {
            EventBus.getDefault().post(new PauseEvent());
        } else {

            EventBus.getDefault().post(new MainActivityController.OnHideFloatingWindowEvent());

            try {
                Intent listenIntent = new Intent(KaDaApplication.getInstance(), ListenService2.class);
                listenIntent.setAction(ListenService2.INTENT_ACTION);
                listenIntent.putExtra(ListenService2.INTENT_PARAM_TYPE, ListenService2.Types.STOP);
                listenIntent.putExtra(ListenService2.INTENT_PARAM_NEED_COMMIT, true);
                listenIntent.putExtra(ListenService2.INTENT_PARAM_REMOVE_NOTIFICATION, true);
                KaDaApplication.getInstance().startService(listenIntent);
            } catch (Throwable e) {
                LogHelper.printStackTrace(e);
            }
        }

        EventBus.getDefault().register(this);

        mShortMediaPlayer.addOnPlayListener(mOnPlayListener);

        isOnbackground = intent.getBooleanExtra(ACTION_IS_ON_BACKGROUND, false);
        if (isOnbackground) {
            //如果从后台启动，就不跳入前台
            moveTaskToBack(true);
        }
        mSourceVersion = intent.getIntExtra(ACTION_PLAY_SOURCE_VERSION, 1); //从2.1版本后默认为1
        mBookId = intent.getIntExtra(ACTION_PLAY_BOOKID, 0);
//        mBookDetailInfo = (BookDetailInfo) intent.getSerializableExtra("bookDetail");
//        bookDetail = intent.getStringExtra("bookDetail");
//        productId = intent.getIntExtra("productId", 0);
        fromFlag = intent.getIntExtra("fromFlag", 0);
        mIsContinuePlay = Settings.getInstance().isBookContinuePlay();
        bookCollectionDetailInfo = (BookCollectionDetailInfo) intent.getSerializableExtra("bookCollectionDetailInfo");
        if (bookCollectionDetailInfo != null) {
            List<BookInfo> bookInfos = bookCollectionDetailInfo.getItems();
            if (bookInfos != null && !bookInfos.isEmpty()) {
                bookList.addAll(bookInfos);
            }

            for (BookInfo info : bookList) {
                info.setCollectId(bookCollectionDetailInfo.getCollectId());
            }
            collectId = bookCollectionDetailInfo.getCollectId();
        } else {
            collectId = 0;
        }
        Serializable serializable = intent.getSerializableExtra("bookInfo");
        if (serializable != null && serializable instanceof BookInfo) {
            BookInfo bookInfo = (BookInfo) serializable;
            collectId = bookInfo.getCollectId();
        }

        if (fromFlag == FROM_EVERYDAY_TASK) {
            mShortMediaPlayer.addPlayQueue(R.raw.daily_book, PlayMode.IMMEDIATELY_PLAY_MODE, AudioName.DAILY_BOOK_AUDIO);
        }

        if (mBookId == 0) {
            finish();

            return;
        }

        if (mIsContinuePlay) {
            UserHabitService.getInstance().trackHabit(UserHabitService.newUserHabit(String.valueOf(mBookId), StaPageName.book_continuity_mode_play, TimeUtil.currentTime()));
        } else {
            UserHabitService.getInstance().trackHabit(UserHabitService.newUserHabit(String.valueOf(mBookId), StaPageName.book_uncontinuity_mode_play, TimeUtil.currentTime()));
        }

        UserHabitService.getInstance().track(UserHabitService.newUserHabit(String.valueOf(mBookId), StaPageName.book_play_preview_view, TimeUtil.currentTime()));

        historyReadCurrentPage = intent.getIntExtra("history", 0);
        isContinuePlay = intent.getBooleanExtra(Constants.INTENT_KEY_BOOK_COLLECTION_CONTINUE_PLAY, false);

        playScreeObserver.requestScreenStateUpdate(new ScreenObserver.ScreenStateListener() {
            @Override
            public void onScreenOn() {
                isScreenOff = false;
                ((TimeOutManager) ServiceProxyFactory.getProxy().getService(ServiceProxyName.TIME_OUT_MANAGER)).startCheck();
            }

            @Override
            public void onScreenOff() {
                isScreenOff = true;
                ((TimeOutManager) ServiceProxyFactory.getProxy().getService(ServiceProxyName.TIME_OUT_MANAGER)).stopCheck();
            }
        });

        initViews();
        initFlipViewSize();

        mBottomContainer.setVisibility(View.INVISIBLE);
        getPlaybackHandler().postDelayed(new Runnable() {
            @Override
            public void run() {

                loadData();

            }
        }, 100);


        if (!mVoiceControl.isSelected()) {
            PlaybackServiceBase.get(new PlaybackServiceBase.ServiceCallback() {
                @Override
                public void handleServiceInstanced(PlaybackServiceBase service) {
                    service.closeVolume();
                }
            });
        }

        ((DownloadManager) ServiceProxyFactory.getProxy().getService(ServiceProxyName.DOWNLOAD_MANAGER)).addListener(downloadTaskListener);
    }

    private void dealWithKeepScreenOnFlag(boolean isAddFlag) {
        if (isAddFlag) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        } else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        finish();
        Context context = KaDaApplication.applicationContext();
        intent.setClass(context, PlaybackActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    private void pauseShortSound() {
        releaseSoundPlayback();
    }

    private void releaseSoundPlayback() {
        if (mShortMediaPlayer != null) {
            List<AudioInfo> audioInfos = mShortMediaPlayer.getCurrentPlayAudio();
            for (AudioInfo audioInfo : audioInfos) {
                if (audioInfo != null) {
                    mShortMediaPlayer.stop(audioInfo.mPlayMode, audioInfo.mAudioTag);
                }
            }
        }

        isNeedMomSubscribePlaying = false;
    }

    void initViews() {

        backBtn = (ImageView) findViewById(R.id.back);
        backBtn.setOnClickListener(new KaDaApplication.OnClickWithAnimListener() {
            @Override
            public void OnClickWithAnim(View v) {
                onBackPressed();
            }
        });

        continuePlayCheckView = (SwitchButton) findViewById(R.id.continueCheckBox);
        continueTextView = (TextView) findViewById(R.id.continueTextView);
        continuePlayCheckView.setChecked(mIsContinuePlay);

        mFlipControl = (ImageView) findViewById(R.id.flip_control);
        mVoiceControl = (ImageView) findViewById(R.id.voice_control);
        mLanguageControl = (ImageView) findViewById(R.id.language_control);

        mLanguageControl.setSelected(Settings.getInstance().isTranslation());
        mVoiceControl.setSelected(Settings.getInstance().isSoundEnable());
        mFlipControl.setSelected(!Settings.getInstance().isFlipByUser());

        smallScreenContainer = findViewById(R.id.small_screen_container);
        smallScreenIndicator = (TextView) findViewById(R.id.small_screen_indicator);
        smallScreenSeekbar = (SeekBar) findViewById(R.id.small_screen_seekbar);
        smallScreenIndicator.setText("0/0");
        smallScreenSeekbar.setMax(0);

        share = (ImageView) findViewById(R.id.share);

        downloadLandscape = (ImageView) findViewById(R.id.download_landscape);
        favorLandscape = (ImageView) findViewById(R.id.favor_landscape);
        downloadLandscape.setOnClickListener(downloadListener);
        favorLandscape.setOnClickListener(favorListener);

        downloadPortrait = (ImageView) findViewById(R.id.download_portrait);
        favorPortrait = (ImageView) findViewById(R.id.favor_portrait);
        downloadPortrait.setOnClickListener(downloadListener);
        favorPortrait.setOnClickListener(favorListener);

        if (((FavoriteService) ServiceProxyFactory.getProxy().getService(ServiceProxyName.FAVORITE_SERVICE)).isContainBookId(mBookId)) {
            favorPortrait.setSelected(true);
            favorLandscape.setSelected(true);
        } else {
            favorPortrait.setSelected(false);
            favorLandscape.setSelected(false);
        }
        if (((DownloadManager) ServiceProxyFactory.getProxy().getService(ServiceProxyName.DOWNLOAD_MANAGER)).isBookDownloadFinished(mBookId)) {
            downloadLandscape.setSelected(true);
            downloadPortrait.setSelected(true);
//        } else if (DatabaseManager.getInstance().bookCollectionItemStatusDB().isDownloadFinish(mBookId)) {
//            downloadLandscape.setSelected(true);
//            downloadPortrait.setSelected(true);
        } else {
            downloadLandscape.setSelected(false);
            downloadPortrait.setSelected(false);
        }

        int marginTop = (int) KaDaApplication.getInstance().getResources().getDimension(R.dimen.playback_button_margin_top);
        RelativeLayout.LayoutParams favorPortraitLayoutParams = (RelativeLayout.LayoutParams) favorPortrait.getLayoutParams();
        favorPortraitLayoutParams.topMargin = marginTop + LocalDisplay.SCREEN_STATUS_HEIGHT;
        RelativeLayout.LayoutParams downloadPortraitLayoutParams = (RelativeLayout.LayoutParams) downloadPortrait.getLayoutParams();
        downloadPortraitLayoutParams.topMargin = marginTop + LocalDisplay.SCREEN_STATUS_HEIGHT;
        updateRelatedButtonMargin();

        mFlipControl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mIsFullScreenMode) {
                    showBottomView();
                    hideBottomViewAnim(4000);
                }

                ScaleAnimator scaleAnimator = new ScaleAnimator(0.8f);
                scaleAnimator.setTarget(mFlipControl).setDuration(100).start();
                scaleAnimator.addAnimatorListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);

                        mFlipControl.setSelected(!mFlipControl.isSelected());
                        if (mFlipControl.isSelected()) {
                            UserHabitService.getInstance().trackHabit(UserHabitService.newUserHabit("" + mBookId, StaCtrName.clickpagebeginautoplay, TimeUtil.currentTime()));
                            ToastUtils.showToast("自动翻书", Gravity.CENTER);

                            if (isPageReadFinish) {
                                getPlaybackHandler().post(mSwitchToNextPageRunnable);
                            }

                            EventBus.getDefault().post(new BookService.StartReadingEvent(mBookDetailInfo, currentPlayingIndex, sessionId));
                        } else {
                            UserHabitService.getInstance().trackHabit(UserHabitService.newUserHabit("" + mBookId, StaCtrName.clickpagestopautoplay, TimeUtil.currentTime()));
                            ToastUtils.showToast("手动翻书", Gravity.CENTER);

                            EventBus.getDefault().post(new BookService.PauseReadingEvent(mBookDetailInfo, currentPlayingIndex, pageCount, sessionId));
                        }
                        Settings.getInstance().setFlipByUser(!mFlipControl.isSelected());
                    }
                });
            }
        });
        mVoiceControl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mIsFullScreenMode) {
                    showBottomView();
                    hideBottomViewAnim(4000);
                }

                ScaleAnimator scaleAnimator = new ScaleAnimator(0.8f);
                scaleAnimator.setTarget(mVoiceControl).setDuration(100).start();
                scaleAnimator.addAnimatorListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        if (mVoiceControl.isSelected()) {
                            UserHabitService.getInstance().track(UserHabitService.newUserHabit("" + mBookId, StaCtrName.clickpageclosesound, TimeUtil.currentTime()));
                            mVoiceControl.setSelected(false);
                            if (mPlaybackController != null) {
                                mPlaybackController.closeVolume();
                            }
                            ToastUtils.showToast("静音模式", Gravity.CENTER);
                        } else {
                            UserHabitService.getInstance().track(UserHabitService.newUserHabit("" + mBookId, StaCtrName.clickpagerecoversound, TimeUtil.currentTime()));
                            mVoiceControl.setSelected(true);
                            if (mPlaybackController != null) {
                                mPlaybackController.openVolume();
                            }
                            ToastUtils.showToast("关闭静音", Gravity.CENTER);
                        }
                        Settings.getInstance().setSoundEnable(mVoiceControl.isSelected());
                    }
                });
            }
        });

        mLanguageControl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mIsFullScreenMode) {
                    showBottomView();
                    hideBottomViewAnim(4000);
                }

                ScaleAnimator scaleAnimator = new ScaleAnimator(0.8f);
                scaleAnimator.setTarget(mLanguageControl).setDuration(100).start();
                scaleAnimator.addAnimatorListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);

                        if (mLanguageControl.isSelected()) {
                            mLanguageControl.setSelected(false);
                            ToastUtils.showToast("无翻译", Gravity.CENTER);
                        } else {
                            mLanguageControl.setSelected(true);
                            ToastUtils.showToast("中文翻译", Gravity.CENTER);
                        }
                        onTranslate();
                        Settings.getInstance().setTranslation(mLanguageControl.isSelected());
                    }
                });
            }
        });

        share.setOnClickListener(new KaDaApplication.OnClickWithAnimListener() {
            @Override
            public void OnClickWithAnim(View v) {
                doShare();

            }
        });

        mBottomContainer = (RelativeLayout) findViewById(R.id.bottom_container);

        mPageIndicator = (TextView) findViewById(R.id.page_indicator);
        mPageIndicator.setText("0/0");
        mSeekBar = (SeekBar) findViewById(R.id.seekbar);
        mSeekBar.setMax(0);


        smallScreenSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (i >= 0 && i < totalPageCount
                        && mFlipView.getCurrentPage() != i) {
                    getHandler().removeMessages(SEEK_TO_PAGE);
                    Message msg = getHandler().obtainMessage(SEEK_TO_PAGE);
                    msg.arg1 = i;
                    getHandler().sendMessageDelayed(msg, 10);

                    mPageIndicator.setText(i + 1 + "/" + totalPageCount);
                    smallScreenIndicator.setText(i + 1 + "/" + totalPageCount);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                LogHelper.d("MediaServer-PlaybackActivity", "pauseImpl()-onStartTrackingTouch()");
                LogHelper.d("Playback_lock", "smallScreenSeekbar show");
                pauseImpl();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                UserHabitService.getInstance().trackHabit(UserHabitService.newUserHabit(String.valueOf(mBookId), StaCtrName.book_reading_page_smallscreen_slider, TimeUtil.currentTime()));

                LogHelper.d("MediaServer-PlaybackActivity", "pauseImpl()-onStopTrackingTouch()");
                pauseImpl();
                int i = seekBar.getProgress();
                if (i >= 0 && i < totalPageCount
                        && mFlipView.getCurrentPage() != i) {
                    LogHelper.d("MediaServer-PlaybackActivity", "pauseImpl()-onStopTrackingTouch()-SEEK_TO_PAGE:i=" + i);
                    getHandler().removeMessages(SEEK_TO_PAGE);
                    Message msg = getHandler().obtainMessage(SEEK_TO_PAGE);
                    msg.arg1 = i;
                    getHandler().sendMessage(msg);
                } else {
                    if (mPlayBtn.isSelected()) {
                        LogHelper.d("MediaServer-PlaybackActivity", "pauseImpl()-onStopTrackingTouch()-startPlayWithDelay:i=" + i);
                        startPlayWithDelay(i);
                    }
                }
            }
        });

        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (i >= 0 && i < totalPageCount
                        && mFlipView.getCurrentPage() != i) {
                    getHandler().removeMessages(SEEK_TO_PAGE);
                    Message msg = getHandler().obtainMessage(SEEK_TO_PAGE);
                    msg.arg1 = i;
                    getHandler().sendMessageDelayed(msg, 10);

                    mPageIndicator.setText(i + 1 + "/" + totalPageCount);
                    smallScreenIndicator.setText(i + 1 + "/" + totalPageCount);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                LogHelper.d("MediaServer-PlaybackActivity", "pauseImpl()-onStartTrackingTouch()");
                LogHelper.d("Playback_lock", "Seekbar show");
                pauseImpl();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                LogHelper.d("MediaServer-PlaybackActivity", "pauseImpl()-onStopTrackingTouch()");
                pauseImpl();
                int i = seekBar.getProgress();
                if (i >= 0 && i < totalPageCount
                        && mFlipView.getCurrentPage() != i) {
                    LogHelper.d("MediaServer-PlaybackActivity", "pauseImpl()-onStopTrackingTouch()-SEEK_TO_PAGE:i=" + i);
                    getHandler().removeMessages(SEEK_TO_PAGE);
                    Message msg = getHandler().obtainMessage(SEEK_TO_PAGE);
                    msg.arg1 = i;
                    getHandler().sendMessage(msg);
                } else {
                    if (mPlayBtn.isSelected()) {
                        LogHelper.d("MediaServer-PlaybackActivity", "pauseImpl()-onStopTrackingTouch()-startPlayWithDelay:i=" + i);
                        startPlayWithDelay(i);
                    }
                }

            }
        });


        mIsFullScreenMode = false;
        mFlipBg = findViewById(R.id.flip_bg);
        mFlipStartPlayContainer = findViewById(R.id.flip_start_play_container);

        mFlipStartPlayContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserHabitService.getInstance().trackHabit(UserHabitService.newUserHabit(String.valueOf(mBookId), StaCtrName.book_reading_page_smallscreen_play, TimeUtil.currentTime()));
                enterFullscreen();
            }
        });

        //for screen lock
        flScreenLockLayer = (FrameLayout) findViewById(R.id.fl_layer);
        ivScreenLockBtn = (ImageView) findViewById(R.id.iv_btn_lock_screen);
        flScreenLockLayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleShowLockScreenBtn();
            }
        });
        ivScreenLockBtn.setOnClickListener(new KaDaApplication.OnClickWithAnimListener() {
            @Override
            public void OnClickWithAnim(View v) {
                if (!isScreenLocked()) {
                    lockScreen();
                } else {
                    unLockScreen(true);
                }
            }
        });

        mFlipPageAdapter = new FlipPageAdapter(this);

        mFlipView = (FlipView) findViewById(R.id.flip_view);
        mFlipView.peakNext(true);
        mFlipView.setOverFlipMode(OverFlipMode.RUBBER_BAND);
        mFlipView.setAdapter(mFlipPageAdapter);
        mFlipView.setOnFlipListener(new FlipView.OnFlipListener() {

            @Override
            public void onFlippedToPage(FlipView v, int position, long id) {
                if (position == flipPosition) {
                    return;
                }

                flipPosition = position;
                LogHelper.d("MediaServer-PlaybackActivity", "startPlayWithDelay(): onFlipListener " + mFlipView.getCurrentPage());
                isPageReadFinish = false;

                onTranslate();
                getPlaybackHandler().removeCallbacks(mSwitchToNextPageRunnable);

                startPlayWithDelay(position);

                mPageIndicator.setText(position + 1 + "/" + totalPageCount);
                smallScreenIndicator.setText(position + 1 + "/" + totalPageCount);

                if (mSeekBar.getProgress() != position) {
                    mSeekBar.setProgress(position);
                }

                if (smallScreenSeekbar.getProgress() != position) {
                    smallScreenSeekbar.setProgress(position);
                }

                if (isStayInLastPage && position != totalPageCount - 1) {
                    isStayInLastPage = false;
                    if (isSetting) {
                        isSetting = false;
                        stayInLastPageTime = System.currentTimeMillis() - stayInLastPageTime;
                    }
                }

                boolean isScreenUnLocked = false;//

                if (hasQuestion && questionPageIndex.contains(position) && position != 0) {
                    for (int child = 0; child < mFlipView.getChildCount(); child++) {
                        View questionPage = mFlipView.getChildAt(child);
                        if (questionPage instanceof QuestionPageLayout
                                && questionPage.getTag(R.id.playback_page_index) instanceof Integer
                                && (Integer) questionPage.getTag(R.id.playback_page_index) == position) {
                            //进入答题页
                            ((QuestionPageLayout) questionPage).setQuestionDetailInfo(mQuestionDetailInfo, questionPageIndex.indexOf(position));
                            ((QuestionPageLayout) questionPage).setLayoutwithConfiguration();
                            isOnQuestionPage = true;

                            if (mIsFullScreenMode) {
                                backBtn.setVisibility(View.INVISIBLE);
                            }

                            //关掉儿童锁屏
                            unLockScreen(false);
                            isScreenUnLocked = true;
                            //隐藏儿童锁按钮
                            ivScreenLockBtn.setVisibility(View.INVISIBLE);
                            break;
                        }
                    }
                } else {
                    isOnQuestionPage = false;
                }

                if (hasQuestion && mHandler != null) {

                    mHandler.removeCallbacks(mStopQuestionAudioRunnable);

                    if (!questionPageIndex.contains(position)) {
                        mHandler.postDelayed(mStopQuestionAudioRunnable, 500); // 延时停止 防止翻页效果卡顿
                    }
                }

                if (position + 1 >= totalPageCount * 0.85) {
                    mReading85Percent = true;
                }

                if (position == totalPageCount - 1 && position != 0) {
                    mReadingFinished = true;
                    isStayInLastPage = true;

                    if (!isSetting) {
                        isSetting = true;
                        stayInLastPageTime = stayInLastPageTime + System.currentTimeMillis();
                    }

                    //到最后一页 隐藏底部控制栏
                    getHandler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            showButtonsForLastPage();
                        }
                    }, 10);

                    //解开儿童锁
                    unLockScreen(false);
                    isScreenUnLocked = true;
                    //隐藏儿童锁按钮
                    ivScreenLockBtn.setVisibility(View.INVISIBLE);

                    isEnterLastPage = true;


                    for (int child = 0; child < mFlipView.getChildCount(); child++) {
                        View lastPage = mFlipView.getChildAt(child);
                        if (lastPage instanceof LastPageLayout
                                && lastPage.getTag(R.id.playback_page_index) instanceof Integer
                                && ((Integer) lastPage.getTag(R.id.playback_page_index)).intValue() == position) {
                            ((LastPageLayout) lastPage).setLayoutWithConfiguration();
                            ((LastPageLayout) lastPage).initializeIvContinueStatus();
                            if (Settings.getInstance().isBookContinuePlay() && mIsFullScreenMode) {
                                ((LastPageLayout) lastPage).startCountDown();
                            }
                            break;
                        }
                    }

                    if (isFirstEnterLastPage) {
                        isFirstEnterLastPage = false;
                        UserTrack.track(UserTrack.brfc);
                    }
                    UserHabitService.getInstance().trackHabit(
                            UserHabitService.newUserHabit(String.valueOf(mBookId),
                                    StaPageName.book_finish_reading_normal_view,
                                    TimeUtil.currentTime()));
                } else {
                    if (!isOnQuestionPage) {
                        //进入书本页面，判断是否刚从最后一页离开
                        if (isEnterLastPage) {
                            //上一页是最后一页，这一页是正常书本页。此时隐藏返回按钮
                            if (mIsFullScreenMode) {
                                backBtn.setVisibility(View.INVISIBLE);
                            }
                        }
                    }
                    isEnterLastPage = false;
                }

                if (!isScreenUnLocked && Settings.getInstance().needLockScreenWhenBookPlay && mIsFullScreenMode) {
                    lockScreen();//一键锁屏时，进入答题页会自动解锁，此时再回到书本页面时，要重新上锁。
                }
            }
        });

        mPlayBtn = (Button) findViewById(R.id.play_cover);
        mPlayBtn.setSelected(false);
        mPlayBtn.setOnClickListener(new KaDaApplication.OnClickWithAnimListener() {
            @Override
            public void OnClickWithAnim(View v) {
                if (mIsFullScreenMode) {
                    togglePlayBtn();
                    showBottomView();
                    hideBottomViewAnim(4000);
                }
            }
        });

        mSeekBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                getBottomHandler().removeCallbacks(hideBottomViewRunnable);
                mSeekBar.setVisibility(View.VISIBLE);

                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    hideBottomViewAnim(4000);
                }
                return false;
            }
        });

        continuePlayCheckView.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void OnCheckedChanged(boolean isChecked) {
                UserHabitService.getInstance().trackHabit(UserHabitService.newUserHabit(String.valueOf(mBookId), isChecked ? StaCtrName.book_preview_auto_continue_select : StaCtrName.book_preview_auto_continue_unselect, TimeUtil.currentTime()));
                updateContinueStatus(isChecked);
            }
        });

        //注册
        registerScreenContentObserver();

        defaultImageView = (ImageView) findViewById(R.id.defaultImageView);

        mLoadingView = (DataLoadingView) findViewById(R.id.loading_view);
        mLoadingView.setBackgroundColor(0);
        mLoadingView.setOnRetryClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
//                mLoadingView.showLoading();
                mLoadingView.showNone();
                defaultImageView.setVisibility(View.VISIBLE);
                loadData();
            }
        });
    }

    /**
     * 更新自动连播状态
     * <p>
     * 与产品确认过，当前实现逻辑如下：
     * 1、当开启自动连播时，问答页需要remove掉
     * 2、当关闭自动连播时，问答页不需要add回来（因为在阅读完成页add时，会出现页面闪烁问题）
     */
    private void updateContinueStatus(boolean isContinue) {
        mIsContinuePlay = isContinue;
        Settings.getInstance().setBookContinuePlay(mIsContinuePlay);
        if (isContinue) {
            ToastUtils.showToast(getResources().getString(R.string.last_page_continue_play_selected_toast));
            if (hasQuestion && questionPageIndex != null && !questionPageIndex.isEmpty()) {
                for (int i = 0; i < questionPageIndex.size(); i++) {
                    mFlipPageAdapter.remove(questionPageIndex.get(i));
                }
                hasQuestion = false;
                totalPageCount = totalPageCount - questionPageIndex.size();
                mFlipPageAdapter.notifyDataSetChanged();
                mSeekBar.setMax(totalPageCount - 1);
                smallScreenSeekbar.setMax(totalPageCount - 1);
                mPageIndicator.setText((mFlipView.getCurrentPage() + 1) + "/" + totalPageCount);
                smallScreenIndicator.setText((mFlipView.getCurrentPage() + 1) + "/" + totalPageCount);
            }
        }
    }

    private void toggleShowLockScreenBtn() {
        if (ivScreenLockBtnAnimator != null) {
            ivScreenLockBtnAnimator.cancel();
            ivScreenLockBtnAnimator = null;
        }
        if (ivScreenLockBtn.getVisibility() != View.VISIBLE) {
            //打点
            UserHabitService.getInstance().trackHabit(
                    UserHabitService.newUserHabit(
                            "", StaCtrName.book_reading_page_lock_view, TimeUtil.currentTime()));
        }
        ivScreenLockBtn.setVisibility(View.VISIBLE);
        ivScreenLockBtn.setAlpha(1.f);
        mHandler.removeCallbacks(ivScreenLockBtnHideRunnable);
        mHandler.postDelayed(ivScreenLockBtnHideRunnable, 1500);
    }

    //------儿童锁相关 start----------
    private void unLockScreen(boolean opByUser) {
        //打点
        UserHabitService.getInstance().trackHabit(
                UserHabitService.newUserHabit(
                        "", StaCtrName.book_reading_page_unlock_click, TimeUtil.currentTime()));
        if (getRotationStatus(PlaybackActivity.this) != 0) {
            //用户没有在手机系统的设置界面锁定旋转
            //恢复旋转功能
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
        }
        if (isScreenLocked() && !opByUser) {
            Settings.getInstance().needLockScreenWhenBookPlay = true;
        }
        flScreenLockLayer.setVisibility(View.GONE);
        ivScreenLockBtn.setImageResource(R.drawable.icon_playback_unlock);
        mHandler.removeCallbacks(ivScreenLockBtnHideRunnable);
        if (opByUser) {
            toggleBottomViewAnim(true);
            Settings.getInstance().needLockScreenWhenBookPlay = false;
        }
    }

    //进行锁屏。
    @SuppressLint("WrongConstant")
    private void lockScreen() {
        //打点
        UserHabitService.getInstance().trackHabit(
                UserHabitService.newUserHabit(
                        "", StaCtrName.book_reading_page_lock_click, TimeUtil.currentTime()));
        //如果正在“手动翻页”，切换到自动翻页。新需求不用了
//        if (!mFlipControl.isSelected()) {
//            mFlipControl.setSelected(true);
//            ToastUtils.showToast("自动翻书", Gravity.CENTER);
//
//            if (isPageReadFinish) {
//                getPlaybackHandler().post(mSwitchToNextPageRunnable);
//            }
//
//            EventBus.getDefault().post(new BookService.StartReadingEvent(mBookDetailInfo, currentPlayingIndex, sessionId));
//        }
//        toggleBottomViewAnim(false);
        flScreenLockLayer.setVisibility(View.VISIBLE);
        ivScreenLockBtn.setImageResource(R.drawable.icon_playback_lock);
        //锁定屏幕的旋转
        getHandler().removeCallbacks(mSwitchToSensorOriWidthRecordRunnable);
        getHandler().removeCallbacks(mSwitchToSensorOriWithoutRecordRunnable);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
    }

    private boolean isScreenLocked() {
        return flScreenLockLayer.getVisibility() != View.GONE;
    }
    //------儿童锁相关 end----------

    /**
     * 分享
     */
    private void doShare() {
        if (mBookInfo.getStatus() != Constants.ONLINE && mBookInfo.getStatus() != 0) {
            ToastUtils.showToast(getResources().getString(R.string.content_is_offline_can_not_share));
            return;
        }
        String title, content, targetUrl;
        long id;
        if (collectId == 0) {
            id = mBookId;
            title = TextUtils.isEmpty(mBookName) ? AppUtils.getString(R.string.share_book_default_title) : mBookName;
            content = AppUtils.getString(R.string.share_single_book_content);
            targetUrl = API.BOOK_SINGLE_SHARE_URL + id;
        } else {
            id = bookCollectionDetailInfo.getCollectId();
            String bookCollectionName = bookCollectionDetailInfo.getName();
            String bookCollectionRecommend = bookCollectionDetailInfo.getRecommend();
            title = TextUtils.isEmpty(bookCollectionName) ? AppUtils.getString(R.string.share_book_default_title) : bookCollectionName;
            content = TextUtils.isEmpty(bookCollectionRecommend) ? AppUtils.getString(R.string.share_default_content) : bookCollectionRecommend;
            targetUrl = API.BOOK_COLLECTION_SHARE_URL() + id;
        }
        ShareInfo shareInfo = new ShareInfo();
        shareInfo.setTitle(title);
        shareInfo.setContent(content);
        shareInfo.setImageUrl(collectId == 0 ? mBookCover : bookCollectionDetailInfo.getCoverUrl());
        shareInfo.setTargetUrl(targetUrl);
        if (collectId > 0) {
            String smallAppSquare = bookCollectionDetailInfo.getSmallAppSquare();
            if (TextUtils.isEmpty(smallAppSquare)) {
                smallAppSquare = shareInfo.getImageUrl();
            }
            shareInfo.setSmallAppSquare(smallAppSquare);
            shareInfo.setWxMinPath(ShareUtils.getWxMinBookPagePath(String.valueOf(id)));
        }
        ShareProvider.share(PlaybackActivity.this, shareInfo, new MyShareListener(id, collectId > 0));
    }

    private ContentObserver observer;

    /**
     * 屏幕自动旋转设置监听
     */
    private void registerScreenContentObserver() {
        observer = new ContentObserver(getHandler()) {
            @Override
            public void onChange(boolean selfChange) {
                if (isScreenLocked()) {
                    return;
                }
                //屏幕旋转是否锁定
                boolean isLock = getRotationStatus(PlaybackActivity.this) == 0;
                boolean isPortrait = getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
                if (isLock) {
                    setRequestedOrientation(isPortrait ? ActivityInfo.SCREEN_ORIENTATION_PORTRAIT : ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                } else {
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
                }
            }
        };

        try {
            getContentResolver().registerContentObserver(
                    android.provider.Settings.System.getUriFor(android.provider.Settings.System.ACCELEROMETER_ROTATION), false, observer);
        } catch (Throwable e) {
            LogHelper.printStackTrace(e);
        }
    }

    /**
     * 取消屏幕自动旋转设置监听
     */
    private void unRegisterScreenContentObserver() {
        try {
            if (observer != null) {
                getContentResolver().unregisterContentObserver(observer);
            }
        } catch (Throwable e) {
            LogHelper.printStackTrace(e);
        }
    }

    //目前竖屏情况为了自动连播布局的需要，X与Y的缩放比率分别设置，但两个值的差距不宜过大，否则会导致布局明显压缩
    private final static float FLIP_VIEW_SCALE_PORT_X = 0.69f;
    private final static float FLIP_VIEW_SCALE_PORT_Y = 0.62f;

    private final static float FLIP_VIEW_SCALE_LAND = 0.6f;

    private void initFlipViewSize() {

        int screenWidth = LocalDisplay.getScreenWidthIncludeSystemView(PlaybackActivity.this);
        int screenHeight = LocalDisplay.getScreenHeightIncludeSystemView(PlaybackActivity.this);

        defaultImageView.setScaleX(FLIP_VIEW_SCALE_PORT_X);
        defaultImageView.setScaleY(FLIP_VIEW_SCALE_PORT_Y);

        ViewGroup.LayoutParams flipViewLp = mFlipView.getLayoutParams();
        flipViewLp.width = screenWidth;
        flipViewLp.height = screenHeight;
        mFlipView.setLayoutParams(flipViewLp);

        mFlipView.setScaleX(FLIP_VIEW_SCALE_PORT_X);
        mFlipView.setScaleY(FLIP_VIEW_SCALE_PORT_Y);

        ViewGroup.LayoutParams flipBgViewLp = mFlipBg.getLayoutParams();
        flipBgViewLp.width = screenWidth;
        flipBgViewLp.height = screenHeight;
        mFlipBg.setLayoutParams(flipBgViewLp);

        portraitLayout();
    }

    void showError() {
        defaultImageView.setVisibility(View.GONE);
        mLoadingView.showError();
    }

    KaDaApplication.OnClickWithAnimListener downloadListener = new KaDaApplication.OnClickWithAnimListener() {
        @Override
        public void OnClickWithAnim(View v) {
            if (mBookInfo.getStatus() != Constants.ONLINE && mBookInfo.getStatus() != 0) {
                ToastUtils.showToast(getResources().getString(R.string.content_is_offline_can_not_download));
                return;
            }
            if (bookCollectionDetailInfo != null && bookCollectionDetailInfo.isLimitFree()) {
                ToastUtils.showToast(AppUtils.getString(R.string.content_is_limit_free_can_not_download));
                return;
            }
            UserHabitService.getInstance().trackHabit(UserHabitService.newUserHabit(mBookId + "," + mBookName, "book_reading_page_download", TimeUtil.currentTime()));
            if (downloadPortrait.isSelected() || downloadLandscape.isSelected()) {
                ToastUtils.showToast("下载已完成", Gravity.CENTER);
            } else {
                if (ContextCompat.checkSelfPermission(PlaybackActivity.this, WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    //申请WRITE_EXTERNAL_STORAGE权限
                    ((PermissionManager) ServiceProxyFactory.getProxy().getService(ServiceProxyName.PERMISSION_MANAGER)).requestPermission(PlaybackActivity.this, PermissionManager.PERMISSION_WRITE_EXTERNAL_STORAGE);
                } else {
                    if (MemorySizeUtils.getAvailableMemoryByMb() >= 200) {
                        ToastUtils.showToast("已加入下载队列", Gravity.CENTER);
                        ((DownloadManager) ServiceProxyFactory.getProxy().getService(ServiceProxyName.DOWNLOAD_MANAGER)).download(mBookInfo);
                    } else {
                        //存储空间不足
                        ((PermissionManager) ServiceProxyFactory.getProxy().getService(ServiceProxyName.PERMISSION_MANAGER)).requestPermission(PlaybackActivity.this, PermissionManager.STORAGE_NOT_ENOUGH);
                    }

                }

            }
        }
    };

    KaDaApplication.OnClickWithAnimListener favorListener = new KaDaApplication.OnClickWithAnimListener() {
        @Override
        public void OnClickWithAnim(View v) {
            if (favorPortrait.isSelected()) {
                if (mBookInfo.getStatus() != Constants.ONLINE && mBookInfo.getStatus() != 0) {
                    ToastUtils.showToast(getResources().getString(R.string.content_is_offline_can_not_cancel_collect));
                    return;
                }
                favorPortrait.setSelected(false);
                favorLandscape.setSelected(false);
                UserHabitService.getInstance().trackHabit(UserHabitService.newUserHabit(mBookId + "," + mBookName + "," + 0, "book_reading_page_favorite", TimeUtil.currentTime()));

                BookAPI.bookAPI_collectBook(mBookId, 1, 2, new DefaultCallback() {
                    @Override
                    public void onDataReceived(Object data) {
                        ((FavoriteService) ServiceProxyFactory.getProxy().getService(ServiceProxyName.FAVORITE_SERVICE)).removeBookId(mBookId);

                        getHandler().post(new Runnable() {
                            @Override
                            public void run() {
                                ToastUtils.showToast("取消收藏");
                                EventCenter.fireEvent(new FavoriteEvent(FavoriteEvent.TYPE_BOOK));
                            }
                        });
                    }

                    @Override
                    public void onException(String reason) {
                        ToastUtils.showToast("取消收藏失败");
                    }
                });
            } else {
                if (mBookInfo.getStatus() != Constants.ONLINE && mBookInfo.getStatus() != 0) {
                    ToastUtils.showToast(getResources().getString(R.string.content_is_offline_can_not_collect));
                    return;
                }
                if (bookCollectionDetailInfo != null && bookCollectionDetailInfo.isLimitFree()) {
                    ToastUtils.showToast(AppUtils.getString(R.string.content_is_limit_free_can_not_collect));
                    return;
                }
                favorPortrait.setSelected(true);
                favorLandscape.setSelected(true);
                UserHabitService.getInstance().trackHabit(UserHabitService.newUserHabit(mBookId + "," + mBookName + "," + 1, "book_reading_page_favorite", TimeUtil.currentTime()));

                BookAPI.bookAPI_collectBook(mBookId, 1, 1, new DefaultCallback() {
                    @Override
                    public void onDataReceived(Object data) {
                        ((FavoriteService) ServiceProxyFactory.getProxy().getService(ServiceProxyName.FAVORITE_SERVICE)).addBookId(mBookId);

                        getHandler().post(new Runnable() {
                            @Override
                            public void run() {
                                ToastUtils.showToast("已收藏");

                                EventCenter.fireEvent(new FavoriteEvent(FavoriteEvent.TYPE_BOOK));
                            }
                        });
                    }

                    @Override
                    public void onException(String reason) {
                        ToastUtils.showToast("收藏失败");
                    }
                });

                UserTrack.track(UserTrack.fcb);
            }
        }
    };

    private void loadData() {
        DefaultCallback<BookInfo> callback = new DefaultCallback<BookInfo>() {
            @Override
            public void onDataReceived(BookInfo info) {
                LogHelper.d(TAG, "book2/getList.json onSuccess");
                if (info != null) {
                    mSourceVersion = info.getVersion();
                    mBookInfo = info;
                    mBookName = info.getName();
                    mBookCover = info.getCoverUrl();
                    muploadUser = info.getUploadUser();
                    mType = info.getType();
                    mExtflag = info.getExtFlag();
                    mDirection = info.getDirection();
                    mHhcurrency = info.getHhcurrency();
                    mLogo = info.getLogo();
                    if (mSourceVersion > 0) {
                        mAESST = CryptoKadaLib.getInstance().getBookAESST(mBookId, mSourceVersion);
                    }
                    loadDataImpl();
                } else {
                    /**
                     * v3.4.0需求：打开单本后如果改单本已经下架或者删除，弹出提示框
                     * */
                    showContentDialog(1, 2, mBookId);
//                        Toast.makeText(PlaybackActivity.this, "加载绘本信息为空", Toast.LENGTH_SHORT).show();
                    showError();
                }
            }

            @Override
            public void onException(String reason) {
                LogHelper.d(TAG, "book2/getList.json failed: " + reason);

                String showText = getResources().getString(R.string.load_book_detail_failed);
                if ("-1".equals(reason)) {
                    showText = "网络异常，请检查网络";
                }
                Toast.makeText(PlaybackActivity.this, showText, Toast.LENGTH_SHORT).show();
                showError();

            }
        };

        if (mGetBookInfoCallbackRef == null) {
            mGetBookInfoCallbackRef = new StrongReference<>();
        }
        mGetBookInfoCallbackRef.set(callback);
        BookService.getBookInfo(mGetBookInfoCallbackRef, mBookId, mSourceVersion);
        sessionId = PlayActionService.getSessionId();
//        }
    }

    private void loadDataImpl() {
        DefaultCallback<BookDetailInfo> callback = new DefaultCallback<BookDetailInfo>() {
            @Override
            public void onDataReceived(BookDetailInfo data) {
                LogHelper.d(TAG, "book2/getDetail.json onSuccess");
                if (!TextUtils.isEmpty(data.getAsset())) {
                    mAESST = data.getAsset();
                }
                mBookDetailInfo = data;
                mBookDetailInfo.setCollectionId(collectId);
                loadRecommendList(mBookId);
            }

            @Override
            public void onException(String reason) {
                LogHelper.d(TAG, "book2/getDetail.json failed: " + reason);

                Toast.makeText(PlaybackActivity.this, reason, Toast.LENGTH_SHORT).show();
                showError();
            }
        };

        if (mGetBookDetailCallbackRef == null) {
            mGetBookDetailCallbackRef = new StrongReference<>();
        }
        mGetBookDetailCallbackRef.set(callback);
        BookService.getBookDetail(mGetBookDetailCallbackRef, mBookId, collectId, mSourceVersion, mAESST);
    }

    public void loadRecommendList(int bookId) {
        //网络不可访问时跳过推荐列表的拉取
        if (!NetworkUtils.isReachable()) {
            loadQuestionDetail();
            return;
        }
        Serializable serializable = getIntent().getSerializableExtra("recommendList");
        if (serializable != null && serializable instanceof List) {
            mFullRecommendBookList.clear();
            mFullRecommendBookList.addAll((List<BookInfo>) serializable);
            loadQuestionDetail();
        } else {
            DefaultCallback<List<BookInfo>> callback = new DefaultCallback<List<BookInfo>>() {
                @Override
                public void onDataReceived(List<BookInfo> responseData) {
                    LogHelper.d(TAG, "book2/getRecommendByItem.json onSuccess");

                    if (responseData != null && responseData.size() > 0) {
                        mRecommendBookList.clear();
                        mRecommendBookList.addAll(responseData);

                        mFullRecommendBookList.clear();
                        if (bookList != null && bookList.size() > 0) {
                            mFullRecommendBookList.addAll(bookList);
                        }
                        mFullRecommendBookList.addAll(mRecommendBookList);
                    }

                    loadQuestionDetail();
                }

                @Override
                public void onException(String reason) {
                    LogHelper.d(TAG, "book2/getRecommendByItem.json failed: " + reason);

                    mFullRecommendBookList.clear();
                    if (bookList != null && !bookList.isEmpty()) {
                        mFullRecommendBookList.addAll(bookList);
                    }

                    loadQuestionDetail();
                }
            };
            if (mGetRecommendListCallbackRef == null) {
                mGetRecommendListCallbackRef = new StrongReference<>();
            }
            mGetRecommendListCallbackRef.set(callback);

            BookAPI.getRecommendList(bookId, 30, mGetRecommendListCallbackRef);
        }
    }


    public void loadQuestionDetail() {
        //问题页展示条件：1、未开启自动连播  2、设置页面问答开关开启  3、该绘本有问答题
        if (!mIsContinuePlay && Settings.getInstance().isShowQuestion() && (mExtflag & Extflag.EXT_FLAG_16) == Extflag.EXT_FLAG_16) {
            DefaultCallback<QuestionDetailInfo> callback = new DefaultCallback<QuestionDetailInfo>() {
                @Override
                public void onDataReceived(QuestionDetailInfo data) {
                    LogHelper.d(TAG, "book/getQuestionsDetail.json onSuccess");

                    mQuestionDetailInfo = data;
                    loadDetailSuccess();
                }

                @Override
                public void onException(String reason) {
                    LogHelper.d(TAG, "book/getQuestionsDetail.json failed: " + reason);

                    loadDetailSuccess();
                }
            };

            if (mGetQuestionDetailCallbackRef == null) {
                mGetQuestionDetailCallbackRef = new StrongReference<>();
            }
            mGetQuestionDetailCallbackRef.set(callback);
            BookService.getQuestionDetail(mGetQuestionDetailCallbackRef, mBookId);
        } else {
            getPlaybackHandler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    loadDetailSuccess();
                }
            }, 500);
        }
    }

    private void portraitLayout() {
        float flipBgScaleX = FLIP_VIEW_SCALE_PORT_X + (float) LocalDisplay.dp2px(42) / (float) LocalDisplay.getScreenWidthIncludeSystemView(PlaybackActivity.this);
        float flipBgScaleY = FLIP_VIEW_SCALE_PORT_Y + (float) LocalDisplay.dp2px(32) / (float) LocalDisplay.getScreenHeightIncludeSystemView(PlaybackActivity.this);
        mFlipBg.setScaleX(flipBgScaleX);
        mFlipBg.setScaleY(flipBgScaleY);

        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mFlipStartPlayContainer.getLayoutParams();
        layoutParams.bottomMargin = (int) ((LocalDisplay.getScreenHeightIncludeSystemView(PlaybackActivity.this)) * (1 - FLIP_VIEW_SCALE_PORT_Y)) / 2;
        mFlipStartPlayContainer.setLayoutParams(layoutParams);

        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) smallScreenContainer.getLayoutParams();
        lp.width = (int) (LocalDisplay.getScreenWidthIncludeSystemView(PlaybackActivity.this) * mFlipBg.getScaleX()) - LocalDisplay.dp2px(46);
        lp.topMargin = (int) ((LocalDisplay.getScreenHeightIncludeSystemView(PlaybackActivity.this)) * (1 + flipBgScaleY) / 2) + LocalDisplay.dp2px(8);
        smallScreenContainer.setLayoutParams(lp);
    }

    private void landscapeLayout() {
        float flipBgScaleX = FLIP_VIEW_SCALE_LAND + (float) LocalDisplay.dp2px(42) / (float) LocalDisplay.getScreenHeightIncludeSystemView(PlaybackActivity.this);
        float flipBgScaleY = FLIP_VIEW_SCALE_LAND + (float) LocalDisplay.dp2px(32) / (float) LocalDisplay.getScreenWidthIncludeSystemView(PlaybackActivity.this);
        mFlipBg.setScaleX(flipBgScaleX);
        mFlipBg.setScaleY(flipBgScaleY);

        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mFlipStartPlayContainer.getLayoutParams();
        layoutParams.bottomMargin = ((int) ((LocalDisplay.getScreenWidthIncludeSystemView(PlaybackActivity.this)) * (1 - FLIP_VIEW_SCALE_LAND))) / 2;
        mFlipStartPlayContainer.setLayoutParams(layoutParams);

        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) smallScreenContainer.getLayoutParams();
        lp.width = (int) (LocalDisplay.getScreenHeightIncludeSystemView(PlaybackActivity.this) * mFlipBg.getScaleX()) - LocalDisplay.dp2px(46);
        lp.topMargin = (int) ((LocalDisplay.getScreenWidthIncludeSystemView(PlaybackActivity.this)) * (1 + flipBgScaleY) / 2) + LocalDisplay.dp2px(4);
        smallScreenContainer.setLayoutParams(lp);
    }

    private void showOptionBtn() {
        Configuration configuration = this.getResources().getConfiguration();
        if (configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            backBtn.setVisibility(View.VISIBLE);
            backBtn.setAlpha(1.f);

            share.setVisibility(View.VISIBLE);

            downloadLandscape.setVisibility(View.INVISIBLE);
            favorLandscape.setVisibility(View.INVISIBLE);

            downloadPortrait.setVisibility(View.VISIBLE);
            favorPortrait.setVisibility(View.VISIBLE);

            mFlipStartPlayContainer.setVisibility(View.VISIBLE);
            smallScreenContainer.setVisibility(View.VISIBLE);
        } else {
            backBtn.setVisibility(View.VISIBLE);
            backBtn.setAlpha(1.f);

            share.setVisibility(View.VISIBLE);

            downloadLandscape.setVisibility(View.VISIBLE);
            favorLandscape.setVisibility(View.VISIBLE);

            downloadPortrait.setVisibility(View.INVISIBLE);
            favorPortrait.setVisibility(View.INVISIBLE);

            mFlipStartPlayContainer.setVisibility(View.VISIBLE);
            smallScreenContainer.setVisibility(View.VISIBLE);
        }
    }

    private void updateFlipViewSize() {
        Configuration configuration = this.getResources().getConfiguration();
        if (configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            mFlipView.setScaleX(FLIP_VIEW_SCALE_PORT_X);
            mFlipView.setScaleY(FLIP_VIEW_SCALE_PORT_Y);
        } else {
            mFlipView.setScaleX(FLIP_VIEW_SCALE_LAND);
            mFlipView.setScaleY(FLIP_VIEW_SCALE_LAND);
        }
    }

    private synchronized void exitFullscreen() {
        if (isRotatingScreenToLandscape) {
            return;
        }

        if (mIsExitingFullScreen) {
            return;
        }

        mIsExitingFullScreen = true;
        mIsEnteringFullScreen = false;

        UserHabitService.getInstance().track(UserHabitService.newUserHabit(String.valueOf(mBookId), StaPageName.book_play_preview_view, TimeUtil.currentTime()));

        setPageNumberVisibility(View.GONE);

        mFlipBg.setVisibility(View.VISIBLE);

        continuePlayCheckView.setVisibility(View.VISIBLE);
        continueTextView.setVisibility(View.VISIBLE);

        mIsFullScreenMode = false;

        backBtn.setImageResource(R.drawable.icon_home2);

        boolean beforePlayBtnSelected = mPlayBtn.isSelected();
        // 移除屏幕常亮Flag
        dealWithKeepScreenOnFlag(false);
        pauseImpl();
        mPlayBtn.setSelected(false);
        getHandler().removeMessages(PLAY_AT_INDEX);

        hidenBottomView();

        mFlipControl.setVisibility(View.INVISIBLE);
        mVoiceControl.setVisibility(View.INVISIBLE);
        mLanguageControl.setVisibility(View.INVISIBLE);
        ivScreenLockBtn.setVisibility(View.INVISIBLE);

        int childCount = mFlipView.getChildCount();
        for (int i = childCount - 1; i >= 0; i--) {
            View view = mFlipView.getChildAt(i);
            // 存在中文翻译的绘本仅全屏模式下展示底部翻译条，预览模式下不展示
            if (view instanceof PlaybackBookPageLayout && (mExtflag & Extflag.EXT_FLAG_2) == Extflag.EXT_FLAG_2) {
                View translateView = view.findViewById(R.id.subtitle);
                if (translateView != null) {
                    translateView.setVisibility(View.GONE);
                }
            }
            //最后一页时退出全屏时停止倒计时
            if (view instanceof LastPageLayout) {
                ((LastPageLayout) view).initializeIvContinueStatus();
                if (Settings.getInstance().isBookContinuePlay()) {
                    ((LastPageLayout) view).stopCountDown();
                    break;
                }
            }
        }

        Configuration configuration = this.getResources().getConfiguration();
        if (configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {

            portraitLayout();

            ObjectAnimator scalex = ObjectAnimator.ofFloat(mFlipView, View.SCALE_X, mFlipView.getScaleX(), FLIP_VIEW_SCALE_PORT_X);
            ObjectAnimator scaley = ObjectAnimator.ofFloat(mFlipView, View.SCALE_Y, mFlipView.getScaleY(), FLIP_VIEW_SCALE_PORT_Y);

            AnimatorSet animSet = new AnimatorSet();
            animSet.playTogether(scalex, scaley);  //同时执行
            animSet.setDuration(240);
            animSet.setInterpolator(new LinearInterpolator());
            animSet.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {

                    animation.removeAllListeners();

                    showOptionBtn();
                    updateFlipViewSize();
                    mIsExitingFullScreen = false;
                    mIsEnteringFullScreen = false;
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                    mIsExitingFullScreen = false;
                    mIsEnteringFullScreen = false;
                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
            animSet.start();
        } else {

            landscapeLayout();

            ObjectAnimator scalex = ObjectAnimator.ofFloat(mFlipView, View.SCALE_X, mFlipView.getScaleX(), FLIP_VIEW_SCALE_LAND);
            ObjectAnimator scaley = ObjectAnimator.ofFloat(mFlipView, View.SCALE_Y, mFlipView.getScaleY(), FLIP_VIEW_SCALE_LAND);

            AnimatorSet animSet = new AnimatorSet();
            animSet.playTogether(scalex, scaley);  //同时执行
            animSet.setDuration(240);
            animSet.setInterpolator(new LinearInterpolator());
            animSet.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {

                    animation.removeAllListeners();

                    showOptionBtn();
                    updateFlipViewSize();

                    mIsExitingFullScreen = false;
                    mIsEnteringFullScreen = false;
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                    mIsExitingFullScreen = false;
                    mIsEnteringFullScreen = false;
                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
            animSet.start();
        }

        if (beforePlayBtnSelected) {
            EventBus.getDefault().post(new BookService.PauseReadingEvent(mBookDetailInfo, currentPlayingIndex, pageCount, sessionId));
        }
    }

    private void setPageNumberVisibility(int visibility) {
        if (mFlipView == null) {
            return;
        }

        for (int i = 0; i < mFlipView.getChildCount(); i++) {
            View view = mFlipView.getChildAt(i);
            View pageNumber = view.findViewById(R.id.page_number);
            if (pageNumber != null && pageNumber.getVisibility() == View.VISIBLE) {
                pageNumber.setVisibility(visibility);
            }
        }
    }

    boolean isFirstEnterFullscreen = true;

    boolean isRotatingScreenToLandscape = false;

    synchronized void enterFullscreen() {
        if (mIsEnteringFullScreen) {
            return;
        }

        mIsEnteringFullScreen = true;
        mIsExitingFullScreen = false;
        if (Settings.getInstance().needLockScreenWhenBookPlay && !isEnterLastPage && !isOnQuestionPage) {
            lockScreen();
        }

        continuePlayCheckView.setVisibility(View.GONE);
        continueTextView.setVisibility(View.GONE);
        mFlipBg.setVisibility(View.GONE);
        mIsFullScreenMode = true;
        isRotatingScreenToLandscape = false;

        if (!isTracked) {
            isTracked = true;
            UserHabitService.getInstance().track(UserHabitService.newUserHabit(mBookId + "," + 1, StaCtrName.beginreading, TimeUtil.currentTime()));
        }

        UserHabitService.getInstance().track(UserHabitService.newUserHabit(String.valueOf(mBookId), StaCtrName.book_play_preview_play_btn_click, TimeUtil.currentTime()));

        releaseSoundPlayback();

        for (int i = 0; i < mFlipView.getChildCount(); i++) {
            View view = mFlipView.getChildAt(i);
            // 存在中文翻译的绘本仅全屏模式下展示底部翻译条，预览模式下不展示
            if (view instanceof PlaybackBookPageLayout && (mExtflag & Extflag.EXT_FLAG_2) == Extflag.EXT_FLAG_2 && mLanguageControl.isSelected()) {
                View translateView = view.findViewById(R.id.subtitle);
                if (translateView != null) {
                    translateView.setVisibility(View.VISIBLE);
                }
            }
            View pageNumber = view.findViewById(R.id.page_number);
            if (pageNumber != null && pageNumber.getVisibility() == View.VISIBLE) {
                pageNumber.setVisibility(View.GONE);
            }
            //最后一页时，进入全屏开始倒计时
            if (view instanceof LastPageLayout) {
                ((LastPageLayout) view).initializeIvContinueStatus();
                if (Settings.getInstance().isBookContinuePlay()) {
                    ((LastPageLayout) view).startCountDown();
                }
            }
        }

        if (isFirstEnterFullscreen) {
            isFirstEnterFullscreen = false;
            UserTrack.track(UserTrack.brc);
        }


        mPlayBtn.setSelected(true);
        // 添加屏幕常亮Flag
        dealWithKeepScreenOnFlag(true);
        startPlayWithDelay(mFlipView.getCurrentPage());

        backBtn.setImageResource(R.drawable.btn_more_back);

        mFlipStartPlayContainer.setVisibility(View.INVISIBLE);
        share.setVisibility(View.INVISIBLE);
        downloadLandscape.setVisibility(View.INVISIBLE);
        favorLandscape.setVisibility(View.INVISIBLE);
        downloadPortrait.setVisibility(View.INVISIBLE);
        favorPortrait.setVisibility(View.INVISIBLE);
        smallScreenContainer.setVisibility(View.INVISIBLE);


        ObjectAnimator scalex = ObjectAnimator.ofFloat(mFlipView, View.SCALE_X, mFlipView.getScaleX(), 1.f);
        ObjectAnimator scaley = ObjectAnimator.ofFloat(mFlipView, View.SCALE_Y, mFlipView.getScaleY(), 1.f);
        AnimatorSet animSet = new AnimatorSet();
        animSet.playTogether(scalex, scaley);
        animSet.setDuration(270);
        animSet.setInterpolator(new LinearInterpolator());
        animSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationCancel(Animator animation) {
                mIsEnteringFullScreen = false;
                mIsExitingFullScreen = false;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                // do end;

                if (mFlipView.getCurrentPage() == totalPageCount - 1) {
                    //...
                } else {
                    showBottomView();
                    hideBottomViewAnim(2000);
                }

                if (mDirection == 1) { //绘本属性
                    isRotatingScreenToLandscape = true;
                    if (getRotationStatus(PlaybackActivity.this) == 0) { //横竖屏是否被锁定
                        getHandler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                orientation = Configuration.ORIENTATION_LANDSCAPE;
                                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                                isRotatingScreenToLandscape = false;

                            }
                        }, 500);
                    } else {
                        getHandler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                orientation = Configuration.ORIENTATION_PORTRAIT;
                                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                                isRotatingScreenToLandscape = false;
                            }
                        }, 500);
                        if (!isScreenLocked()) {
                            getHandler().postDelayed(mSwitchToSensorOriWithoutRecordRunnable, 4000);
                        }
                    }
                } else {
                    if (getRotationStatus(PlaybackActivity.this) == 0) { //横竖屏是否被锁定
                        getHandler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                orientation = Configuration.ORIENTATION_PORTRAIT;
                                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

                            }
                        }, 500);
                    } else {
                        if (!isScreenLocked()) {
                            getHandler().postDelayed(mSwitchToSensorOriWidthRecordRunnable, 4000);
                        }
                    }
                }

                EventBus.getDefault().post(new BookService.StartReadingEvent(mBookDetailInfo, currentPlayingIndex, sessionId));
                mIsEnteringFullScreen = false;
                mIsExitingFullScreen = false;
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }

            @Override
            public void onAnimationStart(Animator animation) {
            }
        });
        animSet.start();
    }


    public void loadDetailSuccess() {
        defaultImageView.setVisibility(View.GONE);
        if (mBookDetailInfo != null) {
            totalPageCount = 0;

            int detailPageCount = mBookDetailInfo.getPages().size();

            if (mQuestionDetailInfo != null) {
                List<QuestionDetailInfo.QuestionInfo> questionInfos = mQuestionDetailInfo.getQuestions();
                if (questionInfos != null && !questionInfos.isEmpty()) {
                    hasQuestion = true;
                    questionPageIndex.clear();

                    int questionCount = questionInfos.size();
                    for (int i = 0; i < questionCount; i++) {
                        int showInPage = detailPageCount + i;
                        questionPageIndex.add(showInPage); // 直接将问答页面放在绘本页后面
                    }

                    totalPageCount = detailPageCount + 1 + questionPageIndex.size();

                } else {
                    totalPageCount = detailPageCount + 1;
                }
            } else {
                totalPageCount = detailPageCount + 1;
            }

            mSeekBar.setMax(totalPageCount - 1);
            mPageIndicator.setText("1/" + totalPageCount);
            mPageIndicator.setVisibility(View.VISIBLE);

            smallScreenSeekbar.setMax(totalPageCount - 1);
            smallScreenIndicator.setText("1/" + totalPageCount);
            smallScreenIndicator.setVisibility(View.VISIBLE);

            mFlipPageAdapter.clear();
            mFlipPageAdapter.addAll(mBookDetailInfo.getPages());

            if (hasQuestion && questionPageIndex != null && !questionPageIndex.isEmpty()) {
                for (int i = 0; i < questionPageIndex.size(); i++) {
                    mFlipPageAdapter.add(questionPageIndex.get(i), new QuestionPageInfo());
                }
            }
            mFlipPageAdapter.add(new LastPageInfo());
            mFlipPageAdapter.notifyDataSetChanged();

            uptimeMillis = 0;

            if (historyReadCurrentPage > 0 && historyReadCurrentPage < mBookDetailInfo.getPageCount()) {
                getHandler().removeMessages(SEEK_TO_PAGE);
                mFlipView.flipTo(historyReadCurrentPage);
            }

            if (mPlaybackController == null) {
                mPlaybackController = new PlaybackController(PlaybackActivity.this, mBookDetailInfo.getBookId(), mBookDetailInfo.getSoundUrl());
            }

            mFlipControl.setVisibility(View.INVISIBLE);
            mVoiceControl.setVisibility(View.INVISIBLE);
            mLanguageControl.setVisibility(View.INVISIBLE);

            share.setVisibility(View.VISIBLE);
            downloadPortrait.setVisibility(View.VISIBLE);
            favorPortrait.setVisibility(View.VISIBLE);
            smallScreenContainer.setVisibility(View.VISIBLE);

            diffTimeMillis = 0;

            if (mLoadingView != null) {
                mLoadingView.hide();
            }
            //开启自动连播后，直接播放 或者从合集详情继续播放进入，也直接播放
            if (mIsContinuePlay || isContinuePlay) {
                enterFullscreen();
            }
        }
    }

    //得到屏幕旋转的状态
    private int getRotationStatus(Context context) {
        int status = 0;
        try {
            status = android.provider.Settings.System.getInt(context.getContentResolver(),
                    android.provider.Settings.System.ACCELEROMETER_ROTATION);
        } catch (android.provider.Settings.SettingNotFoundException e) {
            LogHelper.printStackTrace(e);
        } catch (Throwable e) {
            LogHelper.printStackTrace(e);
        }
        return status;
    }

    /**
     * 更新相关按钮的margin参数(包括返回按钮、静音按钮、翻页按钮)
     * 竖屏时，marginTop = originMarginTop + LocalDisplay.SCREEN_STATUS_HEIGHT,适应沉浸式状态栏
     * 横屏时，marginTop = originMarginTop
     */
    private void updateRelatedButtonMargin() {
        int originMarginTop = (int) KaDaApplication.getInstance().getResources().getDimension(R.dimen.playback_button_margin_top);
        RelativeLayout.LayoutParams backBtnLayoutParams = (RelativeLayout.LayoutParams) backBtn.getLayoutParams();
        RelativeLayout.LayoutParams flipControlLayoutParams = (RelativeLayout.LayoutParams) mFlipControl.getLayoutParams();
        RelativeLayout.LayoutParams voiceControlLayoutParams = (RelativeLayout.LayoutParams) mVoiceControl.getLayoutParams();
        RelativeLayout.LayoutParams languageControlLayoutParams = (RelativeLayout.LayoutParams) mLanguageControl.getLayoutParams();
        RelativeLayout.LayoutParams shareLayoutParams = (RelativeLayout.LayoutParams) share.getLayoutParams();
        RelativeLayout.LayoutParams continuePlayCheckViewParams = (RelativeLayout.LayoutParams) continuePlayCheckView.getLayoutParams();
        RelativeLayout.LayoutParams continueTextViewParams = (RelativeLayout.LayoutParams) continueTextView.getLayoutParams();
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            backBtnLayoutParams.topMargin = originMarginTop + LocalDisplay.SCREEN_STATUS_HEIGHT;
            flipControlLayoutParams.topMargin = originMarginTop + LocalDisplay.SCREEN_STATUS_HEIGHT;
            voiceControlLayoutParams.topMargin = originMarginTop + LocalDisplay.SCREEN_STATUS_HEIGHT;
            languageControlLayoutParams.topMargin = originMarginTop + LocalDisplay.SCREEN_STATUS_HEIGHT;
            shareLayoutParams.topMargin = originMarginTop + LocalDisplay.SCREEN_STATUS_HEIGHT;
            continuePlayCheckViewParams.bottomMargin = LocalDisplay.dp2px(30);
            continuePlayCheckViewParams.rightMargin = LocalDisplay.dp2px(36);
            continueTextViewParams.bottomMargin = LocalDisplay.dp2px(10);
            continueTextViewParams.rightMargin = LocalDisplay.dp2px(40);
        } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            backBtnLayoutParams.topMargin = originMarginTop;
            flipControlLayoutParams.topMargin = originMarginTop;
            voiceControlLayoutParams.topMargin = originMarginTop;
            languageControlLayoutParams.topMargin = originMarginTop;
            shareLayoutParams.topMargin = originMarginTop;
            continuePlayCheckViewParams.bottomMargin = LocalDisplay.dp2px(50);
            continuePlayCheckViewParams.rightMargin = LocalDisplay.dp2px(10);
            continueTextViewParams.bottomMargin = LocalDisplay.dp2px(30);
            continueTextViewParams.rightMargin = LocalDisplay.dp2px(14);
        }
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        performChangeConfig(newConfig);
    }

    /**
     * view 重置
     *
     * @param newConfig
     */
    private void performChangeConfig(Configuration newConfig) {
        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            orientation = Configuration.ORIENTATION_PORTRAIT;
        } else {
            orientation = Configuration.ORIENTATION_LANDSCAPE;
        }

        setSystemUiStatusByOrientation(orientation);
        updateRelatedButtonMargin();

        int tempWidth = LocalDisplay.getScreenWidthIncludeSystemView(PlaybackActivity.this);
        int tempHeight = LocalDisplay.getScreenHeightIncludeSystemView(PlaybackActivity.this);
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            ViewGroup.LayoutParams flipViewLp = mFlipView.getLayoutParams();
            flipViewLp.width = tempWidth;
            flipViewLp.height = tempHeight;
            mFlipView.setLayoutParams(flipViewLp);

            ViewGroup.LayoutParams flipBgViewLp = mFlipBg.getLayoutParams();
            flipBgViewLp.width = tempWidth;
            flipBgViewLp.height = tempHeight;
            mFlipBg.setLayoutParams(flipBgViewLp);
        } else {
            ViewGroup.LayoutParams flipViewLp = mFlipView.getLayoutParams();
            flipViewLp.width = tempHeight;
            flipViewLp.height = tempWidth;
            mFlipView.setLayoutParams(flipViewLp);

            ViewGroup.LayoutParams flipBgViewLp = mFlipBg.getLayoutParams();
            flipBgViewLp.width = tempHeight;
            flipBgViewLp.height = tempWidth;
            mFlipBg.setLayoutParams(flipBgViewLp);
        }

        //fix bug 1: [ViVoX9]开始播放自动切到横屏时->往后滑动5页->滑回第一页->切到竖屏->滑到第三页，第三页异常
        //fix bug 2: ViVoX9偶现、魅族Pro必现:(TAPD ID 1001917)：横屏回到后台,等播放三页以上再回来，界面会错乱。
        fixLayoutOnOrientationChanged();

        if (mIsFullScreenMode) {

            share.setVisibility(View.INVISIBLE);
            downloadLandscape.setVisibility(View.INVISIBLE);
            favorLandscape.setVisibility(View.INVISIBLE);
            downloadPortrait.setVisibility(View.INVISIBLE);
            favorPortrait.setVisibility(View.INVISIBLE);

            smallScreenContainer.setVisibility(View.INVISIBLE);
            mFlipStartPlayContainer.setVisibility(View.INVISIBLE);

            if (orientation == Configuration.ORIENTATION_PORTRAIT) {

                mFlipView.setScaleX(1.f);
                mFlipView.setScaleY(1.f);

            } else {
                mFlipView.setScaleX(1.f);
                mFlipView.setScaleY(1.f);
            }
        } else {
            share.setVisibility(View.VISIBLE);
            smallScreenContainer.setVisibility(View.VISIBLE);
            mFlipStartPlayContainer.setVisibility(View.VISIBLE);

            if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                downloadLandscape.setVisibility(View.INVISIBLE);
                favorLandscape.setVisibility(View.INVISIBLE);
                downloadPortrait.setVisibility(View.VISIBLE);
                favorPortrait.setVisibility(View.VISIBLE);

                mFlipView.setScaleX(FLIP_VIEW_SCALE_PORT_X);
                mFlipView.setScaleY(FLIP_VIEW_SCALE_PORT_Y);

                portraitLayout();

            } else {
                downloadLandscape.setVisibility(View.VISIBLE);
                favorLandscape.setVisibility(View.VISIBLE);
                downloadPortrait.setVisibility(View.INVISIBLE);
                favorPortrait.setVisibility(View.INVISIBLE);

                mFlipView.setScaleX(FLIP_VIEW_SCALE_LAND);
                mFlipView.setScaleY(FLIP_VIEW_SCALE_LAND);

                landscapeLayout();
            }
        }
    }

    //fix bug 1: [ViVoX9]开始播放自动切到横屏时->往后滑动5页->滑回第一页->切到竖屏->滑到第三页，第三页异常
    //fix bug 2: ViVoX9偶现、魅族Pro必现:(TAPD ID 1001917)：横屏回到后台,等播放三页以上再回来，界面会错乱。
    private void fixLayoutOnOrientationChanged() {
        for (View page : mFlipView.getPageViewArr()) {
            if (page instanceof QuestionPageLayout
                    && page.getTag(R.id.playback_page_index) instanceof Integer
                    && ((Integer) page.getTag(R.id.playback_page_index)) == currentPlayingIndex) {

                ((QuestionPageLayout) page).setLayoutwithConfiguration();
                continue;
            }

            if (page instanceof LastPageLayout
                    && page.getTag(R.id.playback_page_index) instanceof Integer
                    && ((Integer) page.getTag(R.id.playback_page_index)) == currentPlayingIndex) {

                ((LastPageLayout) page).setLayoutWithConfiguration();
                continue;
            }

            if (page instanceof PlaybackBookPageLayout) {
                if (page.getTag(R.id.playback_page_index) instanceof Integer) {
                    int index = (Integer) page.getTag(R.id.playback_page_index);
                    PageViewHolder viewHolder = (PageViewHolder) page.getTag(R.id.book_page);
                    if (viewHolder != null && mBookDetailInfo.getPages().size() > index) {
                        final BookDetailInfo.PageInfo pageInfo = mBookDetailInfo.getPages().get(index);
                        String pageUrl;
                        pageUrl = pageInfo.getImgUrl(KaDaApplication.USE_BIG_IMAGE);

                        ImageView imgView = viewHolder.getImageView();
                        initFlipImageViewSize(imgView);

                        if (!TextUtils.isEmpty(pageUrl)) {
                            ImageLoader.getInstance().displayImage(pageUrl, viewHolder.getImageView(), getListOptions(mBookId, mSourceVersion, mAESST), null);
                        }

                        if (imgView instanceof GestureImageView) {
                            GestureImageView imageView = (GestureImageView) imgView;
                            imageView.getController().resetStateByChange();
                        }
                    }
                }
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }


    /**
     * 中英翻译
     */
    public void onTranslate() {
        if ((mExtflag & Extflag.EXT_FLAG_2) != Extflag.EXT_FLAG_2) {

        } else {
            for (int child = 0; child < mFlipView.getChildCount(); child++) {
                View view = mFlipView.getChildAt(child);
                if (view.getTag(R.id.playback_page_index) instanceof Integer) {
                    int index = (Integer) view.getTag(R.id.playback_page_index);

                    BookDetailInfo.PageInfo pageInfo = mFlipPageAdapter.getItemAt(index);
                    if (pageInfo instanceof LastPageInfo || pageInfo instanceof QuestionPageInfo) {

                    } else {
                        TextView textView = (TextView) view.findViewById(R.id.subtitle);
                        int j = 0;
//                        if (hasQuestion && questionPageIndex.size() > 0) {
//                            for (int i = 0; i < questionPageIndex.size(); i++) {
//                                if (questionPageIndex.get(i) <= index) {
//                                    j = j + 1;
//                                }
//                            }
//                        }
                        if (index - j >= 0) {
                            textView.setText(mBookDetailInfo.getPages().get(index - j).getText());
                        }
                        // 存在中文翻译的绘本仅全屏模式下展示底部翻译条，预览模式下不展示
                        if (mLanguageControl.isSelected() && mIsFullScreenMode) {
                            textView.setVisibility(View.VISIBLE);
                        } else {
                            textView.setVisibility(View.GONE);
                        }
                    }
                }
            }
        }
    }

    private Runnable mSwitchToNextPageRunnable = new Runnable() {
        @Override
        public void run() {
            switchToNextPage();
        }
    };


    private final int SEEK_TO_PAGE = 100;
    private final int PLAY_AT_INDEX = 200;

    @Override
    public boolean handleMessage(Message message) {
        if (message.what == SEEK_TO_PAGE) {
            LogHelper.d("MediaServer-PlaybackActivity", "handleMessage()-SEEK_TO_PAGE:i=" + message.arg1);
            if (!mFlipView.isFlipping()) {
                LogHelper.d("MediaServer-PlaybackActivity", "handleMessage()-SEEK_TO_PAGE:!isFlipping() i=" + message.arg1);
                mFlipView.flipTo(message.arg1);
            }
        } else if (message.what == PLAY_AT_INDEX) {
            startPlay(message.arg1);
        }
        return super.handleMessage(message);
    }

    private static class LastPageInfo extends BookDetailInfo.PageInfo {

    }

    private static class QuestionPageInfo extends BookDetailInfo.PageInfo {

    }

    SafeHandler mPlaybackHandler;

    SafeHandler getPlaybackHandler() {
        if (mPlaybackHandler == null) {
            mPlaybackHandler = new SafeHandler();
        }
        return mPlaybackHandler;
    }

    void startPlayWithDelay(int pageIndex) {
        LogHelper.d("MediaServer-PlaybackActivity", "startPlayWithDelay():" + pageIndex);
        if (mFlipView.getCurrentPage() == pageIndex && mPlayBtn.isSelected()) {
            LogHelper.d("MediaServer-PlaybackActivity", "startPlayWithDelay()-after:" + pageIndex);
            getHandler().removeMessages(PLAY_AT_INDEX);
            Message msg = getHandler().obtainMessage(PLAY_AT_INDEX);
            msg.arg1 = pageIndex;
            getHandler().sendMessageDelayed(msg, 100);
        }
    }

    int currentPlayingIndex = -1;

    boolean startPlay(final int pageIndex) {
        if (!mPlayBtn.isSelected()) {
            return false;
        }

        getPlaybackHandler().removeCallbacks(mSwitchToNextPageRunnable);

        if (pageIndex >= 0 && pageIndex < totalPageCount - 1) {

            if (pageIndex == mFlipView.getCurrentPage()) {
                pageCount = pageCount + 1;
                if (hasQuestion && questionPageIndex.contains(pageIndex)) {
                    //开始播放问题声音
                    getPlaybackHandler().removeCallbacksAndMessages(null);
                    pauseImpl();
                    //关屏或者在后台 直接跳过问题页
                    if (isScreenOff || isOnbackground) {
                        getPlaybackHandler().post(mSwitchToNextPageRunnable);
                    } else {
                        if (currentPlayingIndex != pageIndex) {
                            currentPlayingIndex = pageIndex;
                            if (mQuestionDetailInfo != null) {
                                if (!mQuestionDetailInfo.getQuestions().get(questionPageIndex.indexOf(pageIndex)).getisAnswered()) {
                                    playQuestionSound(mQuestionDetailInfo);
                                } else {
                                    pauseShortSound();

                                    getPlaybackHandler().postDelayed(mSwitchToNextPageRunnable, 2500);
                                }
                            }
                        }
                    }

                } else {
                    pauseShortSound();

                    int index = 0;
                    if (hasQuestion && questionPageIndex.contains(pageIndex)) {
                        for (int i = 0; i < questionPageIndex.size(); i++) {
                            if (questionPageIndex.get(i) <= pageIndex) {
                                index = index + 1;
                            }
                        }
                    }
                    if (pageIndex - index < 0 || pageIndex - index >= mBookDetailInfo.getPages().size()) {
                        return false;
                    }

                    final BookDetailInfo.PageInfo pageInfo = mBookDetailInfo.getPages().get(pageIndex - index);
                    if (currentPlayingIndex != pageIndex || currentPageTimeInfo == null) {

                        getPlaybackHandler().removeCallbacksAndMessages(null);
                        LogHelper.d("MediaServer-PlaybackActivity", "pauseImpl()-startPlay:" + pageIndex);
                        pauseImpl();

                        currentPageTimeInfo = null;
                        currentPlayingIndex = -1;

                        if (mPageImagesRefsPos != null
                                && mPageImagesRefsPos.get(pageIndex) != null) {

                            currentPlayingIndex = pageIndex;
                            currentPageTimeInfo = new PlaybackPageTimeInfo(pageIndex, pageInfo);
                            LogHelper.d("MediaServer-PlaybackActivity", "playWithPageTimeInfo-pageAt:" + pageIndex);
                            playWithPageTimeInfo(currentPageTimeInfo, true);
                        } else {
                            LogHelper.d("MediaServer-PlaybackActivity", "playWithPageTimeInfo-pageAt:-else" + pageIndex);
                        }
                    } else {
                        if (mPageImagesRefsPos != null
                                && mPageImagesRefsPos.get(pageIndex) != null) {
                            LogHelper.d("MediaServer-PlaybackActivity", "playWithPageTimeInfo-pageAt:" + pageIndex);
                            playWithPageTimeInfo(currentPageTimeInfo, false);
                        } else {

                        }
                    }
                }
            }
        } else {
            getPlaybackHandler().removeCallbacksAndMessages(null);
            // 播放完了，到最后一页了，取消常亮
            dealWithKeepScreenOnFlag(false);
            pauseImpl();
            //关屏 直接播放推荐书单
            if (isScreenOff && mFullRecommendBookList != null && mFullRecommendBookList.size() > 0) {
                //在前台才切换到下一首
                RedirectActivity.startActivity(PlaybackActivity.this, "kada://openbook?bookId=" + mFullRecommendBookList.get(0).getBookId());
                UserHabitService.getInstance().trackHabit(UserHabitService.newUserHabit(mFullRecommendBookList.get(0).getBookId() + "," + mFullRecommendBookList.get(0).getTraceId() + "," + 0, "clickreadingrecommendbook", TimeUtil.currentTime()));
                finish();
            } else {
                if (pageIndex == totalPageCount - 1
                        && pageIndex == mFlipView.getCurrentPage()
                        && currentPlayingIndex != pageIndex) {

                    playLastSound();
                }
                currentPlayingIndex = pageIndex;
            }
        }
        return false;
    }


    void showButtonsForLastPage() {
        if (mIsFullScreenMode) {
            backBtn.setAlpha(1.f);
            backBtn.setVisibility(View.VISIBLE);
            mFlipControl.setVisibility(View.INVISIBLE);
            mVoiceControl.setVisibility(View.INVISIBLE);
            mLanguageControl.setVisibility(View.INVISIBLE);
            hideBottomViewAnim(80);
        }
    }

    //底部状态栏显示和隐藏动画
    AnimatorSet bottomSet = new AnimatorSet();

    void hidenBottomView() {
        if (bottomSet != null) {
            bottomSet.cancel();
            bottomSet = null;
        }
        getBottomHandler().removeCallbacks(hideBottomViewRunnable);

        isShow = false;
        mBottomContainer.setVisibility(View.INVISIBLE);
        backBtn.setVisibility(View.INVISIBLE);

        mFlipControl.setVisibility(View.INVISIBLE);
        mVoiceControl.setVisibility(View.INVISIBLE);
        mLanguageControl.setVisibility(View.INVISIBLE);
    }

    void showBottomView() {
        if (bottomSet != null) {
            bottomSet.cancel();
            bottomSet = null;
        }
        getBottomHandler().removeCallbacks(hideBottomViewRunnable);

        isShow = true;
        mBottomContainer.setVisibility(View.VISIBLE);
        mBottomContainer.setAlpha(1.f);
        backBtn.setVisibility(View.VISIBLE);
        backBtn.setAlpha(1.f);

        mFlipControl.setVisibility(View.VISIBLE);
        mFlipControl.setAlpha(1.f);
        mVoiceControl.setVisibility(View.VISIBLE);
        mVoiceControl.setAlpha(1.f);
        //显示儿童锁按钮
        if (!isOnQuestionPage && !isEnterLastPage) {
            ivScreenLockBtn.setVisibility(View.VISIBLE);
            ivScreenLockBtn.setAlpha(1.f);
        }
        if ((mExtflag & Extflag.EXT_FLAG_2) == Extflag.EXT_FLAG_2) {
            mLanguageControl.setVisibility(View.VISIBLE);
            mLanguageControl.setAlpha(1.f);
        }
    }

    void hideBottomViewAnim(int delayMilli) {
        if (bottomSet != null) {
            bottomSet.cancel();
            bottomSet = null;
        }
        getBottomHandler().removeCallbacks(hideBottomViewRunnable);
        if (isShow) {
            getBottomHandler().postDelayed(hideBottomViewRunnable, delayMilli);
        }
    }

    SafeHandler mBottomHandler;

    SafeHandler getBottomHandler() {
        if (mBottomHandler == null) {
            mBottomHandler = new SafeHandler();
        }
        return mBottomHandler;
    }

    Runnable hideBottomViewRunnable = new Runnable() {
        @Override
        public void run() {
            toggleBottomViewAnim(false);
        }
    };

    void toggleBottomViewAnim() {
        toggleBottomViewAnim(!isShow);
    }

    void toggleBottomViewAnim(boolean show) {
        if (show) {
            if (mBottomContainer.getVisibility() != View.VISIBLE || mBottomContainer.getAlpha() != 1.f) {
                for (int i = 0; i < mFlipView.getChildCount(); i++) {
                    View view = mFlipView.getChildAt(i);
                    View pageNumber = view.findViewById(R.id.page_number);
                    if (pageNumber != null && pageNumber.getVisibility() == View.VISIBLE) {
                        pageNumber.setVisibility(View.GONE);
                    }
                }

                if (bottomSet != null) {
                    bottomSet.cancel();
                    bottomSet = null;
                }
                bottomSet = new AnimatorSet();
                BookDetailInfo.PageInfo nowPageInfo = mFlipPageAdapter.getItemAt(mFlipView.getCurrentPage());
                if (nowPageInfo instanceof LastPageInfo || nowPageInfo instanceof QuestionPageInfo) {
                    mFlipControl.setVisibility(View.INVISIBLE);
                    mVoiceControl.setVisibility(View.INVISIBLE);
                    mLanguageControl.setVisibility(View.INVISIBLE);
                    ivScreenLockBtn.setVisibility(View.INVISIBLE);

                    if (nowPageInfo instanceof QuestionPageInfo) {
                        bottomSet.playTogether(ObjectAnimator.ofFloat(mBottomContainer, "alpha", 0.0f, 1.0f),
                                ObjectAnimator.ofFloat(backBtn, "alpha", 0.0f, 1.0f));
                    } else {
                        bottomSet.playTogether(ObjectAnimator.ofFloat(mBottomContainer, "alpha", 0.0f, 1.0f));
                    }
                } else {
                    mFlipControl.setVisibility(View.VISIBLE);
                    mVoiceControl.setVisibility(View.VISIBLE);

                    if ((mExtflag & Extflag.EXT_FLAG_2) == Extflag.EXT_FLAG_2) {
                        mLanguageControl.setVisibility(View.VISIBLE);
                        bottomSet.playTogether(ObjectAnimator.ofFloat(mBottomContainer, "alpha", 0.0f, 1.0f),
                                ObjectAnimator.ofFloat(backBtn, "alpha", 0.0f, 1.0f),
                                ObjectAnimator.ofFloat(mFlipControl, "alpha", 0.0f, 1.0f),
                                ObjectAnimator.ofFloat(mVoiceControl, "alpha", 0.0f, 1.0f),
                                ObjectAnimator.ofFloat(mLanguageControl, "alpha", 0.0f, 1.0f));
                    } else {
                        bottomSet.playTogether(ObjectAnimator.ofFloat(mBottomContainer, "alpha", 0.0f, 1.0f),
                                ObjectAnimator.ofFloat(backBtn, "alpha", 0.0f, 1.0f),
                                ObjectAnimator.ofFloat(mFlipControl, "alpha", 0.0f, 1.0f),
                                ObjectAnimator.ofFloat(mVoiceControl, "alpha", 0.0f, 1.0f));
                    }
                    if (ivScreenLockBtn.getVisibility() != View.VISIBLE) {
                        ivScreenLockBtn.setVisibility(View.VISIBLE);
                        bottomSet.playTogether(ObjectAnimator.ofFloat(ivScreenLockBtn, "alpha", 0.0f, 1.0f));
                    }
                }

                mBottomContainer.setVisibility(View.VISIBLE);
                backBtn.setVisibility(View.VISIBLE);
                bottomSet.start();
                bottomSet.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        bottomSet = null;
                        isShow = true;
                        hideBottomViewAnim(4000);
                    }
                });
            }

        } else {
            if (mBottomContainer.getVisibility() == View.VISIBLE) {
                for (int i = 0; i < mFlipView.getChildCount(); i++) {
                    View view = mFlipView.getChildAt(i);
                    View pageNumber = view.findViewById(R.id.page_number);
                    if (pageNumber != null && pageNumber.getVisibility() == View.GONE) {
                        pageNumber.setVisibility(View.VISIBLE);
                    }
                }

                if (bottomSet != null) {
                    bottomSet.cancel();
                    bottomSet = null;
                }
                bottomSet = new AnimatorSet();

                bottomSet.playTogether(ObjectAnimator.ofFloat(mBottomContainer, "alpha", 1.0f, 0.0f),
                        ObjectAnimator.ofFloat(mFlipControl, "alpha", 1.0f, 0.0f),
                        ObjectAnimator.ofFloat(mVoiceControl, "alpha", 1.0f, 0.0f),
                        ObjectAnimator.ofFloat(mLanguageControl, "alpha", 1.0f, 0.0f),
                        ObjectAnimator.ofFloat(ivScreenLockBtn, "alpha", 1.0f, 0.0f));
                if (flipPosition != totalPageCount - 1) {
                    bottomSet.playTogether(ObjectAnimator.ofFloat(backBtn, "alpha", 1.0f, 0.0f));
                }
                bottomSet.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        bottomSet = null;
                        isShow = false;
                        mBottomContainer.setVisibility(View.INVISIBLE);
                        if (flipPosition != totalPageCount - 1) {
                            backBtn.setVisibility(View.INVISIBLE);
                        }
                        mFlipControl.setVisibility(View.INVISIBLE);
                        mVoiceControl.setVisibility(View.INVISIBLE);
                        mLanguageControl.setVisibility(View.INVISIBLE);
                        ivScreenLockBtn.setVisibility(View.INVISIBLE);
                    }
                });
                bottomSet.start();
            }
        }
    }


    class LastPageViewHolder {
        LastPageLayout lastPageLayout;

        private void playAgain() {

            UserHabitService.getInstance().trackHabit(UserHabitService.newUserHabit(String.valueOf(mBookId), StaCtrName.book_reading_retry, TimeUtil.currentTime()));

            if (mFlipView == null) {
                return;
            }

            if (isStayInLastPage) {
                stayInLastPageTime = System.currentTimeMillis() - stayInLastPageTime;
            }
            EventBus.getDefault().post(
                    new BookService.StopReadingEvent(
                            mBookDetailInfo, mReading85Percent, mReadingFinished, mFlipView.getCurrentPage(),
                            stayInLastPageTime, pageCount, mBookCover, mSourceVersion, sessionId, 1));

            sessionId = PlayActionService.getSessionId();

            releaseSoundPlayback();


            if (!mIsFullScreenMode) {
                enterFullscreen(); // 打开全屏模式
            } else {
                //放在else里避免多次打beginreading点以及多次发出StartReadingEvent
                UserHabitService.getInstance().track(UserHabitService.newUserHabit(mBookId + "," + 1, StaCtrName.beginreading, TimeUtil.currentTime()));
                EventBus.getDefault().post(new BookService.StartReadingEvent(mBookDetailInfo, 0, sessionId));
            }

            if (mIsContinuePlay) {
                UserHabitService.getInstance().trackHabit(UserHabitService.newUserHabit(String.valueOf(mBookId), StaPageName.book_continuity_mode_play, TimeUtil.currentTime()));
            } else {
                UserHabitService.getInstance().trackHabit(UserHabitService.newUserHabit(String.valueOf(mBookId), StaPageName.book_uncontinuity_mode_play, TimeUtil.currentTime()));
            }

            mFlipView.flipTo(0); // 跳到第一页播放
            mReading85Percent = false;
            mReadingFinished = false;
            isStayInLastPage = false;
            stayInLastPageTime = 0;
            hidenBottomView();
        }

        public LastPageViewHolder(View convertView) {
            final View share = convertView.findViewById(R.id.share);
            View playAgainView = convertView.findViewById(R.id.ll_read_again_last_page_layout);
            View playNextView = convertView.findViewById(R.id.ll_read_next_last_page_layout);
            final ImageView ivContinuePlay = (ImageView) convertView.findViewById(R.id.iv_continue_play);
            View continuePlayLayout = convertView.findViewById(R.id.continue_play_layout);
            if (mIsContinuePlay) {
                share.setVisibility(View.GONE);
            } else {
                share.setVisibility(View.VISIBLE);
            }

            playAgainView.setVisibility(View.VISIBLE);

            continuePlayLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!mIsFullScreenMode) {
                        enterFullscreen();
                        return;
                    }
                    boolean isContinued = !ivContinuePlay.isSelected();
                    ivContinuePlay.setSelected(isContinued);
                    share.setVisibility(isContinued ? View.INVISIBLE : View.VISIBLE);
                    if (isContinued) {
                        UserHabitService.getInstance().trackHabit(UserHabitService.newUserHabit(String.valueOf(mBookId), StaCtrName.book_finish_reading_continuity_box_select, TimeUtil.currentTime()));
                    } else {
                        UserHabitService.getInstance().trackHabit(UserHabitService.newUserHabit(String.valueOf(mBookId), StaCtrName.book_finish_reading_continuity_box_unselect, TimeUtil.currentTime()));
                    }
                    continuePlayCheckView.setChecked(mIsContinuePlay);
                    lastPageLayout.updateContinuePlayStatus(isContinued);
                    updateContinueStatus(isContinued);
                }
            });

            share.setOnClickListener(new KaDaApplication.OnClickWithAnimListener() {
                @Override
                public void OnClickWithAnim(View v) {
                    doShare();
                }
            });


            playAgainView.setOnClickListener(new KaDaApplication.OnClickWithAnimListener() {
                @Override
                public void OnClickWithAnim(View v) {
                    if (!mIsFullScreenMode) {
                        enterFullscreen();
                        return;
                    }
                    playAgain();
                }
            });

            playNextView.setOnClickListener(new KaDaApplication.OnClickWithAnimListener() {
                @Override
                public void OnClickWithAnim(View v) {
                    if (!mIsFullScreenMode) {
                        enterFullscreen();
                        return;
                    }
                    UserHabitService.getInstance().trackHabit(UserHabitService.newUserHabit(String.valueOf(mBookId), StaCtrName.book_reading_finish_page_next, TimeUtil.currentTime()));

                    int childCount = mFlipView.getChildCount();
                    for (int i = childCount - 1; i >= 0; i--) {
                        View view = mFlipView.getChildAt(i);
                        if (view instanceof LastPageLayout) {
                            LastPageLayout lastPageLayout = (LastPageLayout) view;

                            // 先停止倒计时
                            if (Settings.getInstance().isBookContinuePlay()) {
                                lastPageLayout.stopCountDown();
                            }

                            if (lastPageLayout.isLastRecommendBook()) {
                                ToastUtils.showToast(R.string.last_book_toast);
                                return;
                            }

                            playNextRecommendBook(lastPageLayout, lastPageLayout.getNextRecommendBook());

                            break;
                        }
                    }
                }
            });

            lastPageLayout = (LastPageLayout) convertView.findViewById(R.id.goto_home_container);
            lastPageLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!mIsFullScreenMode) {
                        enterFullscreen();
                    }
                }
            });

            lastPageLayout.setListener(new LastPageLayout.Listener() {
                @Override
                public void onRecommendBookItemClick(BookInfo recommendBookInfo, View view, int index) {
                    if (!mIsFullScreenMode) {
                        enterFullscreen();
                        return;
                    }
                    if (recommendBookInfo == null) {
                        return;
                    }
                    UserHabitService.getInstance().trackHabit(
                            UserHabitService.newUserHabit(recommendBookInfo.getBookId() + "," + recommendBookInfo.getCollectId(),
                                    "click_recommendbook_inlastpage_in_collection_pos" + index,
                                    TimeUtil.currentTime()));

                    UserHabitService.getInstance().trackHabit(UserHabitService.newUserHabit("" + recommendBookInfo.getBookId(), "click_recommendbook_inlastpage_pos" + index, TimeUtil.currentTime()));
                    UserHabitService.getInstance().trackHabit(UserHabitService.newUserHabit(recommendBookInfo.getBookId() + "," + recommendBookInfo.getTraceId() + "," + index, "clickreadingrecommendbook", TimeUtil.currentTime()));

                    if (recommendBookInfo.getCollectId() == 0) {
                        RedirectActivity.startActivity(PlaybackActivity.this, "kada://openbook?bookId=" + recommendBookInfo.getBookId());
                        finish();
                    } else {
                        if (bookCollectionDetailInfo != null && bookCollectionDetailInfo.isLimitFree()) {
                            Intent intent = new Intent(PlaybackActivity.this, PlaybackActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            intent.putExtra("bookId", recommendBookInfo.getBookId());
                            intent.putExtra("bookCollectionDetailInfo", bookCollectionDetailInfo);
                            finish();
                            overridePendingTransition(0, 0);
                            startActivity(intent);
                            return;
                        }
                        if ((bookCollectionDetailInfo != null && bookCollectionDetailInfo.getSubscribe() == 1)
                                || (recommendBookInfo.getExtFlag() & Extflag.EXT_FLAG_8192) == Extflag.EXT_FLAG_8192) {
                            Intent intent = new Intent(PlaybackActivity.this, PlaybackActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            intent.putExtra("bookId", recommendBookInfo.getBookId());
                            intent.putExtra("bookCollectionDetailInfo", bookCollectionDetailInfo);
                            finish();
                            overridePendingTransition(0, 0);
                            startActivity(intent);
                        } else {

                            /**
                             * https://www.tapd.cn/21794391/prong/stories/view/1121794391001002043?url_cache_key=94970b5b787f68b17b259199d62fd552&action_entry_type=story_tree_list
                             * 2、提示订阅音频出现期间再次点击带锁内容，提示订阅音频和底部按钮显示不重新计时。提示订阅音频结束后再次点击带锁内容，提示订阅音频和底部按钮显示从0开始重新计时。
                             */
                            if (isNeedMomSubscribePlaying) {
                                return;
                            }

                            playNeedMomSubscribe();

                            lastPageLayout.showSubscribeView();

                            UserHabitService.getInstance().trackHabit(UserHabitService.newUserHabit(
                                    recommendBookInfo.getBookId() + "," + recommendBookInfo.getCollectId(),
                                    StaCtrName.book_finish_reading_lock_click,
                                    TimeUtil.currentTime()));

                        }
                    }
                }

                @Override
                public void onCountdownEnd(BookInfo bookInfo) {
                    playNextRecommendBook(lastPageLayout, bookInfo);
                }

            });

            if (bookCollectionDetailInfo != null && bookCollectionDetailInfo.getSubscribe() != 1) { // 如果没有订阅
                lastPageLayout.updateSubscribeView(bookCollectionDetailInfo.getExtFlag(), bookCollectionDetailInfo.getPrice(), bookCollectionDetailInfo.getOriginalPrice());
                lastPageLayout.setSubscribeViewListener(new OnChildViewClickListener() {
                    @Override
                    public void onChildViewClick(View childView, int action, Object obj) {
                        switch (childView.getId()) {
                            case R.id.introduceLayout:
                                // 点击了简介
                                if (bookCollectionDetailInfo == null) {
                                    return;
                                }

                                UserHabitService.getInstance().trackHabit(UserHabitService.newUserHabit(mBookId + "," + collectId, StaCtrName.book_finish_reading_introduction_click, TimeUtil.currentTime()));

                                ActivityUtil.nextBookCollectionTitleWebViewActivity(PlaybackActivity.this, bookCollectionDetailInfo);
                                break;
                            case R.id.subscribeLayout:
                                // 点击了订阅
                                UserHabitService.getInstance().trackHabit(UserHabitService.newUserHabit(mBookId + "," + collectId, StaCtrName.book_finish_reading_subscribe_click, TimeUtil.currentTime()));

                                subscribeCollection();
                            default:
                                break;
                        }
                    }
                });
            }
        }
    }

    /**
     * 合辑内绘本播放完成页 如果合辑没有订阅，会有订阅入口，如果当前状态没有登陆 则订阅时先登陆后订阅;
     * <p>
     * 1、如果是未登陆状态，登陆成功后，{@link com.hhdd.kada.main.ui.book.BookCollectionFragment}会收到登陆成功event事件，并重新加载刷新合辑数据，
     * 然后通知{@link PlaybackActivity#onEvent(UpdateBookCollectionDetailEvent)}更新数据；如果已订阅则只刷新UI，隐藏订阅入口；
     * 如果未订阅，则先请求订阅，然后刷新UI，并通知{@link com.hhdd.kada.main.ui.book.BookCollectionFragment}更新状态；
     * <p>
     * 2、如果是已登陆状态，则直接请求订阅，然后刷新UI，并通知{@link com.hhdd.kada.main.ui.book.BookCollectionFragment}更新状态；
     *
     * @see #onEvent(UpdateBookCollectionDetailEvent)
     */
    private void subscribeCollection() {
        if (bookCollectionDetailInfo == null) {
            return;
        }

        if ((bookCollectionDetailInfo.getExtFlag() & Extflag.EXT_FLAG_1024) == Extflag.EXT_FLAG_1024) { // 付费合辑
//            pauseShortSound();

            ChildrenLockDialog lockDialog = new ChildrenLockDialog(PlaybackActivity.this);
            lockDialog.setCallback(new ChildrenDialogCallback() {
                @Override
                public void onAnswerRight() {
                    if (!UserService.getInstance().isLogining()) {
                        // 如果没有登陆，则先登陆
                        LoginOrRegisterActivity.startActivity(PlaybackActivity.this, TYPE_SUBSCRIBE_COLLECTION);
                    } else {
                        doSubscribeCollection(bookCollectionDetailInfo);
                    }
                }

                @Override
                public void onDirectDismiss() {

                }
            });
            lockDialog.show();
        } else { // 免费合辑
            doSubscribeCollection(bookCollectionDetailInfo);

//            if (!UserService.getInstance().isLogining()) {
//                // 如果没有登陆，则先登陆
//                LoginOrRegisterActivity.startActivity(PlaybackActivity.this, TYPE_SUBSCRIBE_COLLECTION);
//            } else {
//                doSubscribeCollection(bookCollectionDetailInfo);
//            }
        }
    }

    /**
     * 合集订阅
     *
     * @param collectionDetailInfo
     */
    private void doSubscribeCollection(BookCollectionDetailInfo collectionDetailInfo) {
        if (collectionDetailInfo == null) {
            return;
        }

        int status = collectionDetailInfo.getStatus();
        if (status != Constants.ONLINE && status != 0) { // 内容非上线状态
            ToastUtils.showToast(getResources().getString(R.string.content_is_offline_can_not_subscribe));
            return;
        }

        if ((collectionDetailInfo.getExtFlag() & Extflag.EXT_FLAG_1024) == Extflag.EXT_FLAG_1024) { // 付费合集
            subscribeCostCollection(collectionDetailInfo);
        } else { // 免费合集
            subscribeFreeCollection(collectionDetailInfo);
        }
    }

    /**
     * 免费合集订阅
     *
     * @param collectionDetailInfo
     */
    private void subscribeFreeCollection(BookCollectionDetailInfo collectionDetailInfo) {
        if (collectionDetailInfo == null) {
            return;
        }

        if (collectionDetailInfo.getSubscribe() == 1) { // 如果已订阅
            return;
        }

        showProgressDialog();

        if (mFreeSubscribeStrongReference == null) {
            mFreeSubscribeStrongReference = new StrongReference<>();
        }

        DefaultCallback subscribeCallback = new DefaultCallback() {
            @Override
            public void onDataReceived(final Object data) {
                getHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        dismissProgressDialog();

                        if (data != null) {
                            ToastUtils.showToast(R.string.subscribe_success);

                            /**
                             * 通知{@link com.hhdd.kada.main.ui.book.BookCollectionFragment}更新状态；
                             */
                            EventCenter.fireEvent(new BookSubscribeStatusEvent(collectId, 1));

                        } else {
                            ToastUtils.showToast(R.string.subscribe_failed);
                        }
                    }
                });
            }

            @Override
            public void onException(String reason) {
                dismissProgressDialog();

                ToastUtils.showToast(R.string.subscribe_failed);
            }
        };

        mFreeSubscribeStrongReference.set(subscribeCallback);
        BookAPI.bookAPI_subscribe(collectId, 1, collectionDetailInfo.getType(), mFreeSubscribeStrongReference);
    }

    /**
     * 付费合集订阅
     *
     * @param collectionDetailInfo
     */
    private void subscribeCostCollection(BookCollectionDetailInfo collectionDetailInfo) {
        if (collectionDetailInfo == null) {
            return;
        }

        if (collectionDetailInfo.getSubscribe() == 1) { // 如果已订阅隐藏订阅入口；
            refreshUiByCostSubscribeSuccess(collectionDetailInfo);
            return;
        }

        createOrder(collectionDetailInfo);
    }


    /**
     * 创建订单
     *
     * @param collectionDetailInfo
     */
    private void createOrder(final BookCollectionDetailInfo collectionDetailInfo) {
        if (collectionDetailInfo == null) {
            return;
        }
        OrderFragParamData orderFragParamData = new OrderFragParamData(0,
                collectionDetailInfo.getCollectId(), collectionDetailInfo);
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(BasePayController.KEY_ORDER_TYPE, PayAPI.TYPE_ORDER_BOOK);
        paramMap.put(BasePayController.KEY_ORDER_COLLECTION_ID, collectionDetailInfo.getCollectId());
        paramMap.put(BasePayController.KEY_ORDER_ATTACH_PARAM, orderFragParamData);
        BasePayController controller = PayManager.getPayController(this);
        if (controller != null) {
            controller.createOrder(paramMap);
        }
    }

    /**
     * 付费合集订阅成功后 刷新UI
     */
    private void refreshUiByCostSubscribeSuccess(BookCollectionDetailInfo collectionDetailInfo) {
        dismissProgressDialog();

        if (collectionDetailInfo == null) {
            return;
        }

        bookCollectionDetailInfo = collectionDetailInfo;

        bookList.clear();
        List<BookInfo> bookInfos = collectionDetailInfo.getItems();
        if (bookInfos != null && !bookInfos.isEmpty()) {
            bookList.addAll(bookInfos);
        }

        for (BookInfo info : bookList) {
            info.setCollectId(collectionDetailInfo.getCollectId());
        }

        mFullRecommendBookList.clear();
        if (!bookList.isEmpty()) {
            mFullRecommendBookList.addAll(bookList);
        }
        if (!mRecommendBookList.isEmpty()) {
            mFullRecommendBookList.addAll(mRecommendBookList);
        }

        if (mFlipPageAdapter != null) {
            mFlipPageAdapter.notifyDataSetChanged();
        }
    }


    private void showProgressDialog() {
        DialogFactory.getCustomDialogManager().showDialog(this);
    }

    private void dismissProgressDialog() {
        DialogFactory.getCustomDialogManager().dismissDialog(this);
    }

    /**
     * 播放语音：小朋友，妈妈订阅之后才能解锁哦
     */
    private void playNeedMomSubscribe() {
        isNeedMomSubscribePlaying = true;

        mShortMediaPlayer.addPlayQueue(R.raw.need_mom_subscribe, PlayMode.IMMEDIATELY_PLAY_MODE, AudioName.NEET_MOM_SUBSCRIBE_AUDIO);
    }

    private void playNextRecommendBook(LastPageLayout lastPageLayout, BookInfo bookInfo) {
        if (bookInfo == null) {
            return;
        }

        boolean canPlay = false;

        if (bookInfo.getCollectId() == 0) {
            canPlay = true;
        } else {
            // 已经订阅 或者 待读绘本可试读
            if ((bookCollectionDetailInfo != null && bookCollectionDetailInfo.getSubscribe() == 1)
                    || (bookInfo.getExtFlag() & Extflag.EXT_FLAG_8192) == Extflag.EXT_FLAG_8192) {
                canPlay = true;
            }
        }

        // 限免合集可以播放
        if (bookCollectionDetailInfo != null && bookCollectionDetailInfo.isLimitFree()) {
            canPlay = true;
        }

        if (!canPlay) {
            if (isNeedMomSubscribePlaying) {
                return;
            }

            playNeedMomSubscribe();

            lastPageLayout.showSubscribeView();

            return;
        }

        Intent intent = new Intent(PlaybackActivity.this, PlaybackActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        if (bookCollectionDetailInfo != null) {
            intent.putExtra("bookCollectionDetailInfo", bookCollectionDetailInfo);
        }
        intent.putExtra("bookId", bookInfo.getBookId());
        intent.putExtra("bookInfo", bookInfo);
        intent.putExtra("recommendList", (Serializable) mFullRecommendBookList);
        intent.putExtra(ACTION_IS_ON_BACKGROUND, isOnbackground);
        finish();
        overridePendingTransition(0, 0);
        startActivity(intent);
    }

    boolean answerRight = false;

    class QuestionPageViewHolder {
        QuestionPageLayout questionPageLayout;
        TextView pageNumber;

        public QuestionPageViewHolder(View convertView, final int index) {
            questionPageLayout = (QuestionPageLayout) convertView.findViewById(R.id.question_layout);
            questionPageLayout.setDisplayImageOptions(getListOptionsForQuestion(mBookId));
            questionPageLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mIsFullScreenMode) {
                        toggleBottomViewAnim();
                    }
                }
            });
            if (mQuestionDetailInfo != null
                    && mQuestionDetailInfo.getQuestions() != null
                    && mQuestionDetailInfo.getQuestions().size() > 0
                    && index >= 0) {
                final List<Integer> useranswers = new ArrayList<Integer>();
                final List<Integer> standardanswers = mQuestionDetailInfo.getQuestions().get(index).getAnswer();

                questionPageLayout.setListner(new QuestionPageLayout.Listner() {
                    @Override
                    public void onOptionClick(View view, int position) {
                        if (!useranswers.contains(position + 1)) {
                            useranswers.add(position + 1);
                        }
                        if (useranswers.size() == standardanswers.size()) {
                            Collections.sort(useranswers);
                            Collections.sort(useranswers);
                            if (useranswers.containsAll(standardanswers)) {
                                UserHabitService.getInstance().trackHabit(UserHabitService.newUserHabit(mBookId + "", "clickquestionright", TimeUtil.currentTime()));
                                playAnswerSound(true);
                                answerRight = true;
                                Toast.makeText(getApplicationContext(), "回答正确！", Toast.LENGTH_SHORT).show();
                                questionPageLayout.setListViewEnabled(false);
                                if (!mQuestionDetailInfo.getQuestions().get(index).isFinish()) {
                                    UserService.getInstance().addCurrectNumber(1);
                                    mQuestionDetailInfo.getQuestions().get(index).setIsFinish(true);
                                    BookService.saveQuestionDetailToLocal(mQuestionDetailInfo);
                                }
                                mQuestionDetailInfo.getQuestions().get(index).setIsAnswered(true);
                                view.findViewById(R.id.check).setVisibility(View.VISIBLE);
                            } else {
                                UserHabitService.getInstance().trackHabit(UserHabitService.newUserHabit(mBookId + "", "clickquestionwrong", TimeUtil.currentTime()));
                                playAnswerSound(false);
                                ValueAnimator animator = ObjectAnimator.ofFloat(view, "Rotation", 0, 10, 0, -10, 0);
                                animator.setDuration(200);
                                animator.setRepeatCount(2);
                                animator.start();
                                useranswers.clear();
                            }
                        }
                    }
                });

                questionPageLayout.setIconListener(new QuestionPageLayout.iconListener() {
                    @Override
                    public void onIconClick(View view) {
                        getPlaybackHandler().removeCallbacks(mSwitchToNextPageRunnable);

                        playQuestionSound(mQuestionDetailInfo);
                    }
                });

                pageNumber = (TextView) convertView.findViewById(R.id.page_number);
            }
        }

        public void update(QuestionDetailInfo questionDetailInfo, int index) {
            if (questionPageLayout != null
                    && questionDetailInfo.getQuestions() != null
                    && questionDetailInfo.getQuestions().size() > 0
                    && index >= 0) {
                questionPageLayout.setType(questionDetailInfo.getQuestions().get(index).getKind());
                questionPageLayout.setQuestionDetailInfo(questionDetailInfo, index);
                questionPageLayout.setLayoutwithConfiguration();
            }
        }
    }

    Map<Integer, Integer> mPageImagesRefsPos = new HashMap<Integer, Integer>();
//    Map<Integer, Bitmap> mPageImagesRefs =  new TreeMap<Integer, Bitmap>(
//            new MapKeyComparator());

    //    class PageViewHolder extends com.android.volley.Listener<Bitmap> {
    class PageViewHolder {
        View convertView;
        private int position = -1;
        final ImageView imageView;
        private TextView pageNumber;
//        WaterView mProgressbar;
//        ProgressBar mProgressbar;
//        final ImageView loadimage;


        public ImageView getImageView() {
            return imageView;
        }

        PageViewHolder(View convertView) {
            this.convertView = convertView;

            imageView = (ImageView) convertView.findViewById(R.id.image);
//            mProgressbar = (WaterView) convertView.findViewById(R.id.progress_bar);
//            mProgressbar.startWave();

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mIsFullScreenMode) {
                        toggleBottomViewAnim();
                    }

                }
            });

            pageNumber = (TextView) convertView.findViewById(R.id.page_number);
//            loadimage = (ImageView) convertView.findViewById(R.id.loading_image);

            if (imageView instanceof GestureImageView) {

                GestureImageView gestureView = (GestureImageView) imageView;
                gestureView.getController().getSettings()
                        .setMaxZoom(3)
                        .setPanEnabled(true)
                        .setZoomEnabled(true)
                        .setDoubleTapEnabled(true)
                        .setRotationEnabled(false)
                        .setRestrictBounds(true)
                        .setOverscrollDistance(0f, 0f)
                        .setOverzoomFactor(2f)
                        .setFillViewport(false)
                        .setFitMethod(com.alexvasilkov.gestures.Settings.Fit.INSIDE)
                        .setGravity(Gravity.CENTER);

                gestureView.getController().setOnGesturesListener(new GestureController.OnGestureListener() {
                    @Override
                    public void onDown(@NonNull MotionEvent e) {

                    }

                    @Override
                    public void onUpOrCancel(@NonNull MotionEvent e) {

                    }

                    @Override
                    public boolean onSingleTapUp(@NonNull MotionEvent e) {
                        if (mIsFullScreenMode) {
                            return false;
                        } else {
                            if (!mIsExitingFullScreen && !mIsEnteringFullScreen) {
                                enterFullscreen();
                            }
                            return true;
                        }
                    }

                    @Override
                    public boolean onSingleTapConfirmed(@NonNull MotionEvent e) {
                        if (mIsFullScreenMode) {
                            if (!mIsExitingFullScreen && !mIsEnteringFullScreen) {
                                toggleBottomViewAnim();
                            }
                        }
                        return false;
                    }

                    @Override
                    public void onLongPress(@NonNull MotionEvent e) {

                    }

                    @Override
                    public boolean onDoubleTap(@NonNull MotionEvent e) {
                        if (mIsFullScreenMode) {
                            UserHabitService.getInstance().trackHabit(UserHabitService.newUserHabit("" + mBookId, StaCtrName.enlargepage, TimeUtil.currentTime()));
                            return false;
                        } else {
                            return true;
                        }
                    }
                });

                gestureView.getController().enableScrollInFlipView(mFlipView);
            }
        }

        public void updateWithPageInfo(final int positionTmp, final BookDetailInfo.PageInfo pageInfo) {
            this.position = positionTmp;
            String pageUrl;
            pageUrl = pageInfo.getImgUrl(KaDaApplication.USE_BIG_IMAGE);

            String coverUrl = (String) imageView.getTag(R.id.book_page_image_url);

            if (!TextUtils.equals(pageUrl, coverUrl)) {
                imageView.setTag(R.id.book_page_image_url, pageUrl);

                ImageLoader.getInstance().displayImage(pageUrl, imageView, getListOptions(mBookId, mSourceVersion, mAESST), new ImageLoadingListener() {
                    @Override
                    public void onLoadingStarted(String imageUri, View view) {
                    }

                    @Override
                    public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                        LogHelper.e(TAG, "onLoadingFailed: " + imageUri);

                        isClearLocalDetailData = true;

                        // 图片加载失败 删除本地缓存图片文件
                        if (!StringUtil.isEmpty(imageUri)) {
                            ImageLoader.getInstance().getDiskCache().remove(imageUri, mDisplayImageOptions);
                        }

                        if (mPageImagesRefsPos.get(position) == null) {
                            mPageImagesRefsPos.put(position, position);
                        }

                        startPlayWithDelay(PageViewHolder.this.position);

                        String userId = UserService.getInstance().getCurrentUserId();
                        StringBuilder sb = new StringBuilder();
                        sb.append("userId=").append(userId);
                        sb.append(",mode=").append(Build.BRAND).append("@").append(Build.MODEL);
                        sb.append(",bookId=").append(mBookId);
                        sb.append(",index=").append(positionTmp);
                        sb.append(",url=").append(imageUri);
                        if (failReason != null) {
                            FailReason.FailType failType = failReason.getType();
                            if (failType != null) {
                                sb.append(",failType=").append(failType.name());
                            }
                            Throwable throwable = failReason.getCause();
                            if (throwable != null) {
                                sb.append(",failReason=").append(throwable.getMessage());
                            }
                        }
                        UserHabitService.getInstance().trackHabit2Umeng(UserHabitService.newUserHabit(sb.toString(), StaCtrName.book_img_decode_error, TimeUtil.currentTime()));
                    }

                    @Override
                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                        // 图片加载完成后在播放音频，如果图片加载失败 认为是图片加载结束，这时也播放绘本，防止在加载页面一直等待而不执行下去
                        LogHelper.d(TAG, "onLoadingComplete: " + imageUri);
                        if (mPageImagesRefsPos.get(position) == null) {
                            mPageImagesRefsPos.put(position, position);
                        }

                        LogHelper.d("MediaServer-PlaybackActivity", "pageOnResponse " + PageViewHolder.this.position);
                        startPlayWithDelay(PageViewHolder.this.position);
                    }

                    @Override
                    public void onLoadingCancelled(String imageUri, View view) {
                        LogHelper.d("MediaServer-PlaybackActivity", "pageOnResponse " + "canceled");
                    }
                });

            } else {
//                LogHelper.d("BUG","did not display img ,index ="+positionTmp);
                //fix bug 1: [ViVoX9]开始播放自动切到横屏时->往后滑动5页->滑回第一页->切到竖屏->滑到第三页，第三页异常
                //fix bug 2: ViVoX9偶现、魅族Pro必现:(TAPD ID 1001917)：横屏回到后台,等播放三页以上再回来，界面会错乱。
                //这里再显示一下图片，可以防止缩放问题
                if (!TextUtils.isEmpty(pageUrl)) {
                    ImageLoader.getInstance().displayImage(pageUrl, imageView, getListOptions(mBookId, mSourceVersion, mAESST), null);
                }
            }
        }
    }

    class FlipPageAdapter extends MyBaseAdapter<BookDetailInfo.PageInfo> {

        public static final int ITEM_TYPE_PAGE = 0;
        public static final int ITEM_TYPE_FIRST_PAGE = 1;
        public static final int ITEM_TYPE_LAST_PAGE = 2;
        public static final int ITEM_TYPE_QUESTION_PAGE = 3;
        public static final int ITEM_TYPE_COUNT = 4;

        public FlipPageAdapter(Context context) {
            super(context);
        }

        @Override
        public int getItemViewType(int position) {
            int size = mItems.size();
            position = position < 0 ? 0 : position;
            position = position >= size ? size - 1 : position;

            if (mItems.get(position) instanceof LastPageInfo) {
                return ITEM_TYPE_LAST_PAGE;
            } else if (mItems.get(position) instanceof QuestionPageInfo) {
                return ITEM_TYPE_QUESTION_PAGE;
            } else {
                return ITEM_TYPE_PAGE;
            }
        }

        @Override
        public int getViewTypeCount() {
            return ITEM_TYPE_COUNT;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup viewGroup) {
            View view = convertView;
            if (view != null) {
                view.setTag(R.id.playback_page_index, -1);
            }
            int type = getItemViewType(position);
            if (type == ITEM_TYPE_LAST_PAGE) {
                if (view == null) {
                    view = LayoutInflater.from(PlaybackActivity.this).inflate(R.layout.playback_last_page, null);
                }
                LastPageViewHolder viewHolder = (LastPageViewHolder) view.getTag(R.id.book_last_page);
                if (viewHolder == null) {
                    viewHolder = new LastPageViewHolder(view);
                    view.setTag(R.id.book_last_page, viewHolder);
                }

                viewHolder.lastPageLayout.setCurrentPlayPosition(ListUtils.getPositionInCollection(mFullRecommendBookList, mBookId));
                viewHolder.lastPageLayout.setRecommendBookList(mFullRecommendBookList);
                viewHolder.lastPageLayout.setLayoutWithConfiguration();
                viewHolder.lastPageLayout.setDetailInfo(bookCollectionDetailInfo);
            } else if (type == ITEM_TYPE_QUESTION_PAGE) {
                if (view == null) {
                    view = LayoutInflater.from(PlaybackActivity.this).inflate(R.layout.playback_question_page, null);
                }
                QuestionPageViewHolder viewHolder = (QuestionPageViewHolder) view.getTag(R.id.book_question_page);
                if (viewHolder == null) {
                    viewHolder = new QuestionPageViewHolder(view, questionPageIndex.indexOf(position));
                    view.setTag(R.id.book_question_page, viewHolder);
                }
                if (mQuestionDetailInfo != null) {
                    viewHolder.update(mQuestionDetailInfo, questionPageIndex.indexOf(position));
                }
                //当底部控制栏显示时 或者预览屏显示时 页码不显示
                if (viewHolder.pageNumber != null) {
                    if (isShow || !mIsFullScreenMode) {
                        viewHolder.pageNumber.setVisibility(View.GONE);
                    } else {
                        viewHolder.pageNumber.setVisibility(View.VISIBLE);
                    }
                    if (totalPageCount != 0) {
                        viewHolder.pageNumber.setText((position + 1) + "/" + totalPageCount);
                    }
                }
            } else {
                int index = 0;
                if (hasQuestion && questionPageIndex.size() > 0) {
                    for (int i = 0; i < questionPageIndex.size(); i++) {
                        if (questionPageIndex.get(i) <= position) {
                            index = index + 1;
                        }
                    }
                }
                int newIndex = position - index;

                // https://bugly.qq.com/v2/crash-reporting/crashes/3c90f7f868/1833?pid=1
                // https://bugly.qq.com/v2/crash-reporting/crashes/3c90f7f868/1675?pid=1
                // https://bugly.qq.com/v2/crash-reporting/crashes/3c90f7f868/1567?pid=1
                // 线上运行情况 在翻页的时候会出现异常 这里对下标做一个修正 保证view不会为NULL
                int pageSize = mBookDetailInfo.getPages().size();
                if (newIndex < 0) {
                    newIndex = 0;
                }
                if (newIndex >= pageSize) {
                    newIndex = pageSize - 1;
                }

                final BookDetailInfo.PageInfo pageInfo = mBookDetailInfo.getPages().get(newIndex);
                if (view == null) {
                    view = LayoutInflater.from(PlaybackActivity.this).inflate(R.layout.playback_page_layout, null);
                }
                PageViewHolder viewHolder = (PageViewHolder) view.getTag(R.id.book_page);
                if (viewHolder == null) {
                    viewHolder = new PageViewHolder(view);
                    view.setTag(R.id.book_page, viewHolder);
                } else {
                    ImageView imageView = viewHolder.getImageView();
                    initFlipImageViewSize(imageView);
//                    if (imageView instanceof GestureImageView) {
//                        GestureImageView iv = (GestureImageView) imageView;
//                        iv.getController().resetStateByChange();
//                    }
                }
                viewHolder.updateWithPageInfo(position, pageInfo);
                //当底部控制栏显示时 或者预览屏显示时 页码不显示
                if (isShow || !mIsFullScreenMode) {
                    viewHolder.pageNumber.setVisibility(View.GONE);
                } else {
                    viewHolder.pageNumber.setVisibility(View.VISIBLE);
                }
                if (totalPageCount != 0) {
                    viewHolder.pageNumber.setText((position + 1) + "/" + totalPageCount);
                }
            }

            if (view != null) {
                view.setTag(R.id.playback_page_index, position);
            }
            return view;
        }
    }

    private void initFlipImageViewSize(ImageView imageView) {
        ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
        if (layoutParams != null) {
            int orientation = KaDaApplication.getInstance().getResources().getConfiguration().orientation;
            int width = orientation == Configuration.ORIENTATION_PORTRAIT ? LocalDisplay.getScreenWidthIncludeSystemView(PlaybackActivity.this) : LocalDisplay.getScreenHeightIncludeSystemView(PlaybackActivity.this);
            int height = orientation == Configuration.ORIENTATION_PORTRAIT ? LocalDisplay.getScreenHeightIncludeSystemView(PlaybackActivity.this) : LocalDisplay.getScreenWidthIncludeSystemView(PlaybackActivity.this);
            layoutParams.width = width;
            layoutParams.height = height;
            imageView.setLayoutParams(layoutParams);
        }
    }

    void switchToNextPage() {
        getPlaybackHandler().removeCallbacks(mSwitchToNextPageRunnable);
        isPageReadFinish = true;
        if (mPlayBtn.isSelected()) {
            if (mFlipControl.isSelected() || isOnQuestionPage) {
                if (mFlipPageAdapter != null && mFlipView.getCurrentPage() < totalPageCount - 1) {
                    LogHelper.d("MediaServer-PlaybackActivity", "switchToNextPage: position = " + (mFlipView.getCurrentPage() + 1));
//                    if (isLockBook && mFlipView.getCurrentPage() >= canFreeReadPageCount) {
//                        showLockDialog();
//                        return;
//                    }
                    if (isScreenOff || isOnbackground) {
                        mFlipView.flipTo(mFlipView.getCurrentPage() + 1);
                    } else {
                        mFlipView.smoothFlipTo(mFlipView.getCurrentPage() + 1);
                    }
                } else {
                    mFlipView.peakPrevious(true);
                }
            }
        }
    }

    void playAnswerSound(final boolean isRight) {
        if (isRight) {
            mShortMediaPlayer.addPlayQueue(R.raw.answer_right, PlayMode.IMMEDIATELY_PLAY_MODE, AudioName.ANSWER_RIGHT_AUDIO);
        } else {
            mShortMediaPlayer.addPlayQueue(R.raw.answer_wrong, PlayMode.IMMEDIATELY_PLAY_MODE, AudioName.ANSWER_WRONG_AUDIO);
        }
    }

    void playQuestionSound(QuestionDetailInfo questionDetailInfo) {
        if (questionDetailInfo != null
                && questionDetailInfo.getQuestions() != null
                && questionDetailInfo.getQuestions().size() > 0
                && questionDetailInfo.getQuestions().get(0).getSoundUrl() != null
                && questionDetailInfo.getQuestions().get(0).getSoundUrl().length() > 0) {

            String soundUrl = mShortMediaPlayer.getProxyUrl(
                    questionDetailInfo.getQuestions().get(0).getSoundUrl(),
                    questionDetailInfo.getQuestions().get(0).getQuestionId(),
                    false,
                    0,
                    0,
                    0,
                    BookService.getBookCachePath(mBookId), "");

            mShortMediaPlayer.addPlayQueue(soundUrl, PlayMode.IMMEDIATELY_PLAY_MODE, AudioName.TAG_BOOK_QUESTION_AUDIO);

        }
    }

    void playLastSound() {
        //如果绘本语音还在播放，就暂停绘本语音
        if (mPlaybackController.isPlaying()) {
            mPlaybackController.pause();
        }

        if (hasQuestion) {
            if (answerRight) {
                if (UserService.getInstance().currentCorrectNumber() <= 1) {
                    mShortMediaPlayer.addPlayQueue(R.raw.first_answer, PlayMode.IMMEDIATELY_PLAY_MODE, AudioName.FIRST_ANSWER_AUDIO);
                } else if (UserService.getInstance().currentCorrectNumber() > 1) {
                    mShortMediaPlayer.addPlayQueue(R.raw.answer, PlayMode.IMMEDIATELY_PLAY_MODE, AudioName.ANSWER_AUDIO);
                }
            } else {
                mShortMediaPlayer.addPlayQueue(R.raw.finish_read, PlayMode.IMMEDIATELY_PLAY_MODE, AudioName.READ_FINISH_AUDIO);
            }
        } else {
            mShortMediaPlayer.addPlayQueue(R.raw.finish_read, PlayMode.IMMEDIATELY_PLAY_MODE, AudioName.READ_FINISH_AUDIO);
        }
    }


    public interface Listener {
        public void handleStartPlaying(PlaybackController controller); //开始播放

        public void handlePaused(PlaybackController controller); //播放暂停一般是由手动触发

        public void handleCompletion(PlaybackController controller); //这一段播放完成了
    }

    class PlaybackController implements Handler.Callback {
        SafeHandler handler;
        PlaybackServiceBase service;
        final Context context;
        final int bookId;
        final String filePath;
        int soundBegin;
        int soundEnd;
        Listener listener;

        private final int PLAYBACK_TIMER = 8080;

        @Override
        public boolean handleMessage(Message message) {
            if (message.what == PLAYBACK_TIMER) {
                if (service != null) {
                    if (service.getCurrentPosition() >= soundEnd) {
                        LogHelper.d("MediaServer-PlaybackActivity", "handleCompletion-beforePause:" + service.getCurrentPosition());
                        service.pause();
                        LogHelper.d("MediaServer-PlaybackActivity", "handleCompletion-afterPause:" + service.getCurrentPosition());
                        if (listener != null) {
                            listener.handleCompletion(PlaybackController.this);
                        }
                    } else {
//                        Log.d("MediaServer-PlaybackActivity", "nothandleCompletion, service currentposition=" + service.getCurrentPosition()+";soundEnd="+soundEnd);
                        Message msg = handler.obtainMessage();
                        msg.what = PLAYBACK_TIMER;
                        handler.sendMessageDelayed(msg, 1);
                    }
                }
            }
            return false;
        }


        public PlaybackController(Context context, final int bookId, final String filePath) {
            this.context = context;
            this.handler = new SafeHandler(this);
            this.bookId = bookId;
            this.filePath = filePath;
            PlaybackServiceBase.get(new PlaybackServiceBase.ServiceCallback() {
                @Override
                public void handleServiceInstanced(PlaybackServiceBase service) {
                    PlaybackController.this.service = service;
                    if (!TextUtils.isEmpty(filePath)) {
                        service.prepare(filePath, bookId, mSourceVersion, mAESST, mPlayEventCallback);
                    }
                }
            });
        }

        public void cleanup() {
            if (service != null) {
                service.removeCallback(mPlayEventCallback);
            }
            if (handler != null) {
                handler.destroy();
            }
            listener = null;
        }

        public boolean isPlaying() {
            return service != null ? service.isPlaying() : false;
        }

        public boolean equalWithBeginEnd(final int soundBegin, final int soundEnd) {
            return this.soundBegin == soundBegin && this.soundEnd == soundEnd;
        }

        public long getCurrentPosition() {
            return service != null ? service.getCurrentPosition() : 0;
        }

        public void playWithListener(final int soundBegin, final int soundEnd, Listener listener) {
            playWithListener(soundBegin, soundEnd, listener, false);
        }

        public void playWithListener(final int soundBegin, final int soundEnd, Listener listener, boolean reset) {
            this.shouldPause = false;
            this.listener = listener;
            if (MediaServer2.cachedFileExist(this.filePath, this.bookId, mSourceVersion)) {
                if (this.soundBegin != soundBegin
                        || this.soundEnd != soundEnd || reset) {
                    this.soundBegin = soundBegin;
                    this.soundEnd = soundEnd;
                    if (service != null) {
                        service.reset();
                    }
                } else {
                    doStartPlay(false);
                }
            } else {
                this.soundBegin = soundBegin;
                this.soundEnd = soundEnd;
                doStartPlay(false);
            }
        }

        boolean shouldPause = false; //缓冲有延迟

        public void pause() {
            shouldPause = true;
            handler.removeCallbacksAndMessages(null);
            if (service != null
                    && service.isPlaying()) {
                service.pause();
            }
        }

        boolean hasBeenReset = false;

        public void reset() {
            this.listener = null;
            this.soundBegin = 0;
            this.soundEnd = 0;
            handler.removeCallbacksAndMessages(null);
            if (service != null
                    && service.isPlaying()) {
                service.pause();
            }
            hasBeenReset = true;
        }

        public void stop() {
            handler.removeCallbacksAndMessages(null);
            if (service != null) {
                service.removeCallback(mPlayEventCallback);
                service.stop();
            }
        }

        public void openVolume() {
            if (service != null) {
                service.openVolume();
            }
        }

        public void closeVolume() {
            if (service != null) {
                service.closeVolume();
            }
        }

        long prePlayingPosition = 0;

        PlaybackServiceBase.PlayEventCallback mPlayEventCallback = new PlaybackServiceBase.PlayEventCallback() {
            @Override
            public void handleStateChanged(PlaybackServiceBase service, int state) {
                if (state == PlaybackServiceBase.STATE_PREPARING) {
                    LogHelper.d("MediaServer-PlaybackActivity-Callback", "STATE_PREPARING");
                } else if (state == PlaybackServiceBase.STATE_BUFFERING) {
                    LogHelper.d("MediaServer-PlaybackActivity-Callback", "STATE_BUFFERING");
                    handler.removeCallbacksAndMessages(null);
                    service.pause();
                    prePlayingPosition = service.getCurrentPosition();
                } else if (state == PlaybackServiceBase.STATE_READY) {
                    LogHelper.d("MediaServer-PlaybackActivity-Callback", "STATE_READY");
                    if (!shouldPause) {
                        if (prePlayingPosition == 0 || service.getCurrentPosition() == prePlayingPosition) {
                            LogHelper.d("MediaServer-PlaybackActivity-Callback", "STATE_READY a");
                            if (service.isPlaying()) {

                            } else {
                                handler.removeCallbacksAndMessages(null);
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        doStartPlay(false);
                                    }
                                });
                            }
                        } else {
                            LogHelper.d("MediaServer-PlaybackActivity-Callback", "STATE_READY seek " + prePlayingPosition);
                            service.seek((int) prePlayingPosition);
                            prePlayingPosition = 0;
                        }
                    }
                } else if (state == PlaybackServiceBase.STATE_ENDED) {
                    LogHelper.d("MediaServer-PlaybackActivity-Callback", "STATE_ENDED");
                    handler.removeCallbacksAndMessages(null);
                    //判断,开始播放
                    if (listener != null) {
                        listener.handleCompletion(PlaybackController.this);
                    }
                } else if (state == PlaybackServiceBase.STATE_PLAYING) {
                    LogHelper.d("MediaServer-PlaybackActivity-Callback", "STATE_PLAYING");
                    handler.removeCallbacksAndMessages(null);
                    Message msg = handler.obtainMessage();
                    msg.what = PLAYBACK_TIMER;
                    handler.sendMessage(msg);
                    if (listener != null) {
                        listener.handleStartPlaying(PlaybackController.this);
                    }
                } else if (state == PlaybackServiceBase.STATE_PAUSED) {
                    LogHelper.d("MediaServer-PlaybackActivity-Callback", "STATE_PAUSED");
                    handler.removeCallbacksAndMessages(null);
                    LogHelper.d("MediaServer-PlaybackActivity-Callback", "listener-handlePaused");
                    if (listener != null) {
                        listener.handlePaused(PlaybackController.this);
                    }
                }
            }

            @Override
            public void handleErrorOccured(PlaybackServiceBase service) {
                Toast.makeText(context, "加载绘本音频失败", Toast.LENGTH_SHORT).show();
            }
        };

        private void doStartPlay(boolean reset) {
            if (currentPlayingIndex >= totalPageCount) {
                return;
            }
            if (service != null
                    && service.isCanPlay()
                    && soundBegin >= 0 && soundEnd > 0
                    && mPlayBtn.isSelected()) {
                if (service.getCurrentPosition() < soundBegin || service.getCurrentPosition() >= soundEnd || reset || hasBeenReset) {
                    LogHelper.d("MediaServer-PlaybackActivity", "doStartPlay currentPosition = " + service.getCurrentPosition() + "  soundBegin= " + soundBegin);
                    if (service.getCurrentPosition() == soundBegin) {
                        if (!service.isPlaying()) {
                            LogHelper.d("MediaServer-PlaybackActivity", "doStartPlay-play-1");
                            handler.removeCallbacksAndMessages(null);
                            service.play();
                        }
                    } else {
                        handler.removeCallbacksAndMessages(null);
                        if (service.isPlaying()) {
                            LogHelper.d("MediaServer-PlaybackActivity", "doStartPlay-pause");
                            service.pause();
                        }
                        LogHelper.d("MediaServer-PlaybackActivity", "doStartPlay-seek");
                        service.seek(soundBegin);
                    }
                } else {
                    if (!service.isPlaying()) {
                        LogHelper.d("MediaServer-PlaybackActivity", "doStartPlay-play-2");
                        handler.removeCallbacksAndMessages(null);
                        service.play();
                    }
                }
                hasBeenReset = false;
            }
        }

        void onDestory() {
            if (service != null) {
                service.removeCallback(mPlayEventCallback);
            }
        }
    }

    PlaybackController mPlaybackController = null;

    void playWithPageTimeInfo(final PlaybackPageTimeInfo pageTimeInfo, boolean pageChanged) {
//        long duration = -1;
        //这里需要规避第一页，因为在第一页时getDuration()方法不一定能获取到时长，引起mediaPlayer错误码为-38的错误。后期可以考虑由服务器将整个音频的时长从接口下发
//        if (pageTimeInfo.pageIndex != 0) {
//            duration = mPlaybackController.service.getDuration();
//        }
        LogHelper.d("MediaServer-PlaybackActivity-playWithPageTimeInfo", "pageChanged:" + pageChanged);
        if (pageChanged) {
            getPlaybackHandler().removeCallbacksAndMessages(null);
            LogHelper.d("MediaServer-PlaybackActivity", "removeCallbacksAndMessages 1");
            mPlaybackController.reset();
            LogHelper.d("MediaServer-PlaybackActivity", "pause 1");
            if (pageTimeInfo.soundEnd > pageTimeInfo.soundBegin) {
                final long delayMillis = pageTimeInfo.beforeStop;
                getPlaybackHandler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (mPlayBtn.isSelected()) {
                            mPlaybackController.playWithListener(pageTimeInfo.soundBegin, pageTimeInfo.soundEnd, new Listener() {
                                @Override
                                public void handleStartPlaying(PlaybackController controller) {

                                }

                                @Override
                                public void handlePaused(PlaybackController controller) {

                                }

                                @Override
                                public void handleCompletion(PlaybackController controller) {
                                    //这一段播放完成了
                                    getPlaybackHandler().postDelayed(mSwitchToNextPageRunnable, pageTimeInfo.endStop > 0 ? pageTimeInfo.endStop : 1000);
                                }
                            }, true);
                        }
                    }
                }, delayMillis);
            } else {
                long delayMillis = pageTimeInfo.beforeStop + pageTimeInfo.endStop;
                if (delayMillis < 0) delayMillis = 0;
                if (mPlayBtn.isSelected()) {
                    //息屏或者后台则直接切到下一页
                    if (isScreenOff || isOnbackground) {
                        getPlaybackHandler().post(mSwitchToNextPageRunnable);
                    } else {
                        getPlaybackHandler().postDelayed(mSwitchToNextPageRunnable, pageTimeInfo.endStop > 0 ? pageTimeInfo.endStop : 1000);
                    }
                }
            }
        } else {
            //if (mPlaybackController.equalWithBeginEnd(pageTimeInfo.soundBegin,pageTimeInfo.soundEnd))
            if ((pageTimeInfo.soundEnd > pageTimeInfo.soundBegin)) {
                if (mPlaybackController.getCurrentPosition() >= pageTimeInfo.soundBegin
                        && mPlaybackController.equalWithBeginEnd(pageTimeInfo.soundBegin, pageTimeInfo.soundEnd)
                        && mPlaybackController.getCurrentPosition() < pageTimeInfo.soundEnd) {
                    //已经开始播放了,从中间播放
                    //play
                    getPlaybackHandler().removeCallbacksAndMessages(null);
                    LogHelper.d("MediaServer-PlaybackActivity", "removeCallbacksAndMessages 2");
                    if (mPlayBtn.isSelected()) {
                        mPlaybackController.playWithListener((int) mPlaybackController.getCurrentPosition(), pageTimeInfo.soundEnd, new Listener() {
                            @Override
                            public void handleStartPlaying(PlaybackController controller) {

                            }

                            @Override
                            public void handlePaused(PlaybackController controller) {

                            }

                            @Override
                            public void handleCompletion(PlaybackController controller) {
                                //这一段播放完成了
                                getPlaybackHandler().postDelayed(mSwitchToNextPageRunnable, pageTimeInfo.endStop > 0 ? pageTimeInfo.endStop : 1000);
                            }
                        });
                    }
                } else {
                    // 还未开始播放
                    getPlaybackHandler().removeCallbacksAndMessages(null);
                    LogHelper.d("MediaServer-PlaybackActivity", "removeCallbacksAndMessages 3");
                    long delayMillis = pageTimeInfo.beforeStop - diffTimeMillis;
                    if (delayMillis < 0) {
                        delayMillis = 0;
                    }

                    getPlaybackHandler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (mPlayBtn.isSelected()) {
                                mPlaybackController.playWithListener(pageTimeInfo.soundBegin, pageTimeInfo.soundEnd, new Listener() {
                                    @Override
                                    public void handleStartPlaying(PlaybackController controller) {

                                    }

                                    @Override
                                    public void handlePaused(PlaybackController controller) {

                                    }

                                    @Override
                                    public void handleCompletion(PlaybackController controller) {
                                        //这一段播放完成了
                                        getPlaybackHandler().postDelayed(mSwitchToNextPageRunnable, pageTimeInfo.endStop > 0 ? pageTimeInfo.endStop : 1000);
                                    }
                                }, true);
                            }
                        }
                    }, delayMillis);
                }
            } else {
                getPlaybackHandler().removeCallbacksAndMessages(null);
                LogHelper.d("MediaServer-PlaybackActivity", "removeCallbacksAndMessages 5");
                mPlaybackController.pause();
                LogHelper.d("MediaServer-PlaybackActivity", "pause 3");
                long delayMillis = pageTimeInfo.beforeStop + pageTimeInfo.endStop - diffTimeMillis;
                if (delayMillis < 0) delayMillis = 0;

                if (mPlayBtn.isSelected()) {
                    getPlaybackHandler().postDelayed(mSwitchToNextPageRunnable, pageTimeInfo.endStop > 0 ? pageTimeInfo.endStop : 1000);
                }
            }
        }
        uptimeMillis = SystemClock.uptimeMillis();
    }

    void pauseImpl() {
        if (mPlaybackController != null) {
            mPlaybackController.pause();
        }
    }

    void togglePlayBtn() {
        if (mPlayBtn.isSelected()) {
            // 移除Flag
            dealWithKeepScreenOnFlag(false);
            stopPlay();
        } else {
            if (mPlaybackController != null) {
                uptimeMillis = 0;
                mPlayBtn.setSelected(true);
                // 添加Flag
                dealWithKeepScreenOnFlag(true);
                startPlay(mFlipView.getCurrentPage());
                diffTimeMillis = 0;

                EventBus.getDefault().post(new BookService.ResumeReadingEvent(mBookDetailInfo, currentPlayingIndex, sessionId));
            }
        }
    }

    public void onEvent(PauseEvent event) {
        //当前是否处于问题页(处于问题页，暂停播放的话，选完答案后不会自动跳转到完成页)
        boolean currentQuestionPosition = hasQuestion && questionPageIndex.contains(flipPosition);
        //当前不处于问题页，且不处于完成页
        if (flipPosition >= 0 && !currentQuestionPosition && flipPosition < totalPageCount - 1) {
            if (mPlayBtn.isSelected()) {
                stopPlay();

                if (event.isLockPause) {
                    UserHabitService.getInstance().trackHabit(UserHabitService.newUserHabit("", StaCtrName.book_play_stop_by_lock, TimeUtil.currentTime()));
                }
            }
            //全屏状态下才需要显示播放操作栏
            if (mIsFullScreenMode) {
                showBottomView();
            }
        }
    }

    public void onEvent(BookLimitFreeEndedEvent event) {
        // 播放绘本的时候，限免时间结束，限免结束后合集的ExtFlag
        if (bookCollectionDetailInfo != null && bookCollectionDetailInfo.getCollectId() ==
                event.getBookCollectionId()) {
            // 更新当前合集的extFlag
            int flag = (int) (bookCollectionDetailInfo.getExtFlag()
                    ^ Extflag.BOOK_FLAG_FREE_NEW    // 移除新用户限免
                    ^ Extflag.BOOK_FLAG_FREE_ALL);  // 移除全用户限免
            bookCollectionDetailInfo.setExtFlag(flag);
            // 更新里面recommendList属于合集的item对应的Flag
            for (BookInfo bookInfo : bookList) {
                bookInfo.setCollectionExtFlag(flag);
            }

            // 找到LastPageLayout
            if (mFlipView != null) {
                int childCount = mFlipView.getChildCount();
                for (int index = childCount - 1; index >= 0; index--) {
                    View page = mFlipView.getChildAt(index);
                    if (page instanceof LastPageLayout
                            && page.getTag(R.id.playback_page_index) instanceof Integer
                            && (Integer) page.getTag(R.id.playback_page_index) == currentPlayingIndex) {
                        ((LastPageLayout) page).setLayoutWithConfiguration();
                        ((LastPageLayout) page).stopCountDown();// 如果限免结束的时后开启倒计时了，则关闭
                        ((LastPageLayout) page).notifyDataSetChanged();
                        break;
                    }
                }

            }
            LogHelper.d("Randy", "合集" + bookCollectionDetailInfo.getName()
                    + "限免结束");
        }
    }

    /**
     * 停止播放
     */
    private void stopPlay() {
        if (mPlaybackController != null) {
            getPlaybackHandler().removeCallbacksAndMessages(null);
            mPlayBtn.setSelected(false);
            if (mPlaybackController.isPlaying()) {
                mPlaybackController.pause();
            }
            diffTimeMillis = uptimeMillis > 0 ? SystemClock.uptimeMillis() - uptimeMillis : 0;

            EventBus.getDefault().post(new BookService.PauseReadingEvent(mBookDetailInfo, currentPlayingIndex, pageCount, sessionId));
        }
    }


    long diffTimeMillis = 0;
    long uptimeMillis = 0;

    protected static class PlaybackPageTimeInfo {
        public int pageIndex;
        public int soundBegin;
        public int soundEnd;
        public int beforeStop;
        public int endStop;

        public PlaybackPageTimeInfo(int pageIndex, BookDetailInfo.PageInfo pageInfo) {
            this.pageIndex = pageIndex;
            soundBegin = (int) (TimeUtil.audioMilliTime(pageInfo.getSoundBegin()));
            soundEnd = (int) (TimeUtil.audioMilliTime(pageInfo.getSoundEnd()));
            if (soundEnd > soundBegin) {
//                beforeStop = 2*1000; //开始播放后，有声音的页，先停一个时间。
//                endStop = 2 * 1000;
                beforeStop = 1000;//pageInfo.getBeforeStop()*1000;
                endStop = pageInfo.getEndStop() * 1000;
            } else {
                beforeStop = 0;
                endStop = 3 * 1000;
            }
        }

        @Override
        public String toString() {
            return "PlaybackPageTimeInfo{" +
                    "pageIndex=" + pageIndex +
                    ", soundBegin=" + soundBegin +
                    ", soundEnd=" + soundEnd +
                    ", beforeStop=" + beforeStop +
                    ", endStop=" + endStop +
                    '}';
        }
    }

    protected PlaybackPageTimeInfo currentPageTimeInfo = null;

    @Override
    public void onBackPressed() {
        if (mIsEnteringFullScreen || mIsExitingFullScreen) {
            return;//正处于进入全屏或退出全屏的动画过程中，不响应返回键。否则会产生界面异常。
        }
        if (isScreenLocked()) {
            toggleShowLockScreenBtn();//锁屏状态下，返回键只显示一下锁屏图标
            return;
        }

        if (mIsFullScreenMode) {
            UserHabitService.getInstance().trackHabit(UserHabitService.newUserHabit(mBookId + ",0,1", StaCtrName.book_reading_page_fullscreen_back, TimeUtil.currentTime()));
        } else {
            UserHabitService.getInstance().trackHabit(UserHabitService.newUserHabit(String.valueOf(mBookId), StaCtrName.clickbookreadingviewhomebutton, TimeUtil.currentTime()));
        }

        if (mIsFullScreenMode) {
            exitFullscreen();
            return;
        }
        finish();
    }

    @Override
    public void finish() {
        PlaybackServiceBase.stopService();
        if (isStayInLastPage) {
            stayInLastPageTime = System.currentTimeMillis() - stayInLastPageTime;
        }
        if (mBookId != 0) {
            if (collectId > 0) {
                mBookCover = bookCollectionDetailInfo.getCoverUrl();
            }
            EventBus.getDefault().post(
                    new BookService.StopReadingEvent(
                            mBookDetailInfo, mReading85Percent, mReadingFinished, mFlipView.getCurrentPage(),
                            stayInLastPageTime, pageCount, mBookCover, mSourceVersion, sessionId, 1));
        }
        //解决某些手机（如华为P20pro）回到合辑页面后，合辑页面变成横屏异常
        if (getResources().getConfiguration().orientation != Configuration.ORIENTATION_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        super.finish();
    }

    @Override
    protected void onDestroy() {
        unRegisterScreenContentObserver();

        PayManager.destroy(this);
        DialogFactory.dismissAllDialog(this);
        if (mPlaybackController != null) {
            mPlaybackController.stop();
            mPlaybackController.onDestory();
            mPlaybackController = null;
        }

        super.onDestroy();

        releaseSoundPlayback();

//        PlaybackServiceBase.stopService();//挪到了finish()中

        if (mPlaybackHandler != null) {
            mPlaybackHandler.destroy();
        }

        if (mShortMediaPlayer != null) {
            mShortMediaPlayer.stop(PlayMode.IMMEDIATELY_PLAY_MODE, AudioName.TAG_BOOK_QUESTION_AUDIO);
            mShortMediaPlayer.removeOnPlayListener(mOnPlayListener);
        }

        if (mBottomHandler != null) {
            mBottomHandler.destroy();
        }

        ((DownloadManager) ServiceProxyFactory.getProxy().getService(ServiceProxyName.DOWNLOAD_MANAGER)).removeListener(downloadTaskListener);

        if (playScreeObserver != null) {
            playScreeObserver.stopScreenStateUpdate();
        }

        EventBus.getDefault().unregister(this);

        if (mGetBookInfoCallbackRef != null) {
            mGetBookInfoCallbackRef.clear();
            mGetBookInfoCallbackRef = null;
        }

        if (mGetBookDetailCallbackRef != null) {
            mGetBookDetailCallbackRef.clear();
            mGetBookDetailCallbackRef = null;
        }

        if (mGetQuestionDetailCallbackRef != null) {
            mGetQuestionDetailCallbackRef.clear();
            mGetQuestionDetailCallbackRef = null;
        }
        if (mGetRecommendListCallbackRef != null) {
            mGetRecommendListCallbackRef.clear();
            mGetRecommendListCallbackRef = null;
        }
        if (mFreeSubscribeStrongReference != null) {
            mFreeSubscribeStrongReference.clear();
            mFreeSubscribeStrongReference = null;
        }
//        if (mLoadStoreIdCallbackRef != null) {
//            mLoadStoreIdCallbackRef.clear();
//            mLoadStoreIdCallbackRef = null;
//        }

        if (isClearLocalDetailData) {
            BookService.clearBookDetailFromLocal(mBookId, mSourceVersion);
        }
    }

    /*
     * 需求上是，首先这一页的声音和图片都下好了，才会自动翻页。
     * 2 开始播放后，有声音的页，先停一个时间。然后开始播放声音。播完声音在停一个时间，然后自动翻页。
     * 3 没有声音的页，停5秒
     * 4 夜间模式没有停顿
     * 差不多就这么多需求
     * 需要注意的是
     * 1 停顿的时候用户也可能翻页，退回主屏幕
     * 2 下载过程中网络状况可能变化
     * 3 停顿时间可以是book detail里描述的beginstop endstop，也可以和ios一样规定个计算方法
     * */

    IDownloadTaskListener downloadTaskListener = new IDownloadTaskListener() {
        @Override
        public void onStart(int itemType, int itemId) {
            LogHelper.d(TAG, "onStart,info.id=" + itemId);
        }

        @Override
        public void onProgress(int itemType, int itemId, int p) {
            LogHelper.d(TAG, "onProgress id=" + itemId+"," + p);
        }

        @Override
        public void onError(int itemType, int itemId, Throwable error) {
            LogHelper.d(TAG, "onError ,info.id=" + itemId + ",err=" + error.toString());
        }

        @Override
        public void onFinish(int itemType, int itemId, boolean complete) {
            LogHelper.d(TAG, "onFinish,info.id=" + itemId);
            if (itemType == DownloadItemType.BOOK && itemId == mBookId) {
                downloadPortrait.setSelected(true);
                downloadLandscape.setSelected(true);
                ToastUtils.showToast("已下载完成");
                EventCenter.fireEvent(new BookFinishDownloadEvent());
            }
        }
    };


    DisplayImageOptions mDisplayImageOptions = null;

    protected DisplayImageOptions getListOptionsForQuestion(int bookId) {

        DisplayImageOptions options = new DisplayImageOptions.Builder()
                // 设置图片在下载期间显示的图片
                .showImageOnLoading(R.drawable.loading_image)
                // 设置图片Uri为空或是错误的时候显示的图片
                .cacheInMemory(false)
                // 设置下载的图片是否缓存在SD卡中
                .cacheOnDisk(true)
                // 保留Exif信息
                .considerExifParams(true)
                // 设置图片以如何的编码方式显示
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
                // 设置图片的解码类型
                .bitmapConfig(Bitmap.Config.RGB_565)
                // .decodingOptions(android.graphics.BitmapFactory.Options
                // decodingOptions)//设置图片的解码配置
//                    .considerExifParams(true)
                // 设置图片下载前的延迟
                .delayBeforeLoading(5)// int
                .encryptItemId(0)
                .encryptVersion(0)
                .cacheTargetPath(BookService.getBookCachePath(bookId))
                // delayInMillis为你设置的延迟时间
                // 设置图片加入缓存前，对bitmap进行设置
                // .preProcessor(BitmapProcessor preProcessor)
//                    .resetViewBeforeLoading(true)// 设置图片在下载前是否重置，复位
                // .displayer(new RoundedBitmapDisplayer(20))//是否设置为圆角，弧度为多少
//                    .displayer(new FadeInBitmapDisplayer(1000))// 淡入
                .build();
        return options;
    }

    protected DisplayImageOptions getListOptions(int bookId, int encryptVersion, String AESST) {
        if (mDisplayImageOptions == null) {
            DisplayImageOptions options = new DisplayImageOptions.Builder()
                    // 设置图片在下载期间显示的图片
                    .showImageOnLoading(R.drawable.loading_image)
                    // 设置图片Uri为空或是错误的时候显示的图片
                    .cacheInMemory(false)
                    // 设置下载的图片是否缓存在SD卡中
                    .cacheOnDisk(true)
                    // 保留Exif信息
                    .considerExifParams(true)
                    // 设置图片以如何的编码方式显示
                    .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
                    // 设置图片的解码类型
                    .bitmapConfig(Bitmap.Config.RGB_565)
                    // .decodingOptions(android.graphics.BitmapFactory.Options
                    // decodingOptions)//设置图片的解码配置
//                    .considerExifParams(true)
                    // 设置图片下载前的延迟
                    .delayBeforeLoading(20)// int
                    .encryptItemId(bookId)
                    .encryptVersion(encryptVersion)
                    .AESSTString(AESST)
                    .cacheTargetPath(BookService.getBookCachePath(bookId))
                    // delayInMillis为你设置的延迟时间
                    // 设置图片加入缓存前，对bitmap进行设置
                    // .preProcessor(BitmapProcessor preProcessor)
//                    .resetViewBeforeLoading(true)// 设置图片在下载前是否重置，复位
                    // .displayer(new RoundedBitmapDisplayer(20))//是否设置为圆角，弧度为多少
//                    .displayer(new FadeInBitmapDisplayer(1000))// 淡入
                    .build();
            mDisplayImageOptions = options;

        }
        return mDisplayImageOptions;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PermissionManager.PERMISSION_WRITE_EXTERNAL_STORAGE) {
            if (grantResults != null && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (MemorySizeUtils.getAvailableMemoryByMb() >= 200) {
                    ToastUtils.showToast("已加入下载队列", Gravity.CENTER);
                    ((DownloadManager) ServiceProxyFactory.getProxy().getService(ServiceProxyName.DOWNLOAD_MANAGER)).download(mBookInfo);
                } else {
                    ((PermissionManager) ServiceProxyFactory.getProxy().getService(ServiceProxyName.PERMISSION_MANAGER)).requestPermission(PlaybackActivity.this, PermissionManager.STORAGE_NOT_ENOUGH);
                }
            } else {
                // Permission Denied
                ToastUtils.showToast("没有权限,无法下载", Gravity.CENTER);
            }
            return;
        }
    }

    void showContentDialog(int kind, int type, int id) {
        if (isFinishing()) {
            return;
        }
        ContentIsDeletedDialog dialog = new ContentIsDeletedDialog(PlaybackActivity.this);
        dialog.setCallback(new ContentIsDeletedDialog.ContentDialogCallback() {
            @Override
            public void doYes() {
                finish();
            }
        });
        dialog.setData(kind, type, id);
        dialog.show();
    }

    private static class MyShareListener implements ShareProvider.Listener {

        private long mId;
        private boolean isCollect; //是否为合辑

        public MyShareListener(long id, boolean isCollect) {
            mId = id;
            this.isCollect = isCollect;
        }

        @Override
        public void onComplete(boolean success, SHARE_MEDIA share_media) {

            if (success) {
                if (!isCollect) {
                    UserHabitService.getInstance().trackHabit(UserHabitService.newUserHabit(mId + ",final," + share_media.toString() + ",yes", "book_reading_page_share", TimeUtil.currentTime()));
                }
            } else {
                if (!isCollect) {
                    UserHabitService.getInstance().trackHabit(UserHabitService.newUserHabit(mId + ",final," + share_media.toString() + ",no", "book_reading_page_share", TimeUtil.currentTime()));
                }
            }
        }
    }

    private OnPlayListener mOnPlayListener = new OnPlayListener() {
        @Override
        public void onPrepared(String audioTag, int playMode) {

        }

        @Override
        public void onCompletion(String audioTag, int playMode) {
            if (AudioName.DAILY_BOOK_AUDIO.equals(audioTag) || AudioName.NEET_MOM_SUBSCRIBE_AUDIO.equals(audioTag)) {
                isNeedMomSubscribePlaying = false;
            } else if (AudioName.ANSWER_RIGHT_AUDIO.equals(audioTag)) {
                getPlaybackHandler().postDelayed(mSwitchToNextPageRunnable, 1000);
            }
        }

        @Override
        public void onError(String audioTag, int playMode) {
            if (AudioName.DAILY_BOOK_AUDIO.equals(audioTag) || AudioName.NEET_MOM_SUBSCRIBE_AUDIO.equals(audioTag)) {
                isNeedMomSubscribePlaying = false;
            } else if (AudioName.ANSWER_RIGHT_AUDIO.equals(audioTag)) {
                getPlaybackHandler().postDelayed(mSwitchToNextPageRunnable, 1000);
            }
        }

        @Override
        public void onStop(String audioTag, int playMode) {
            if (AudioName.DAILY_BOOK_AUDIO.equals(audioTag) || AudioName.NEET_MOM_SUBSCRIBE_AUDIO.equals(audioTag)) {
                isNeedMomSubscribePlaying = false;
            }
        }
    };

    private Runnable mStopQuestionAudioRunnable = new Runnable() {
        @Override
        public void run() {
            if (mShortMediaPlayer != null) {
                mShortMediaPlayer.stop(PlayMode.IMMEDIATELY_PLAY_MODE, AudioName.TAG_BOOK_QUESTION_AUDIO);
            }
        }
    };
}


//目前传入三个参数
//        DT：设备类型（iphone、ipad、Android等）
//        SV：操作系统版本
//        AV：app版本
//在header里面为一个键值对“RDI:DT=iPad4,1;SV=8.1.2;AV=1.2.4”
