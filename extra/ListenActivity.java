package com.hhdd.kada.main.listen;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.hhdd.android.common.ServiceProxyFactory;
import com.hhdd.core.model.StoryInfo;
import com.hhdd.core.service.AuthService;
import com.hhdd.core.service.DefaultCallback;
import com.hhdd.core.service.FavoriteService;
import com.hhdd.core.service.StoryService;
import com.hhdd.core.service.UserHabitService;
import com.hhdd.kada.CdnUtils;
import com.hhdd.kada.Constants;
import com.hhdd.kada.KaDaApplication;
import com.hhdd.kada.R;
import com.hhdd.kada.android.library.app.ActivityHelper;
import com.hhdd.kada.android.library.utils.LocalDisplay;
import com.hhdd.kada.api.API;
import com.hhdd.kada.api.StoryAPI;
import com.hhdd.kada.app.serviceproxy.ServiceProxyName;
import com.hhdd.kada.base.BaseActivity;
import com.hhdd.kada.dialog.CustomDialogManager;
import com.hhdd.kada.dialog.DialogFactory;
import com.hhdd.kada.download.DownloadItemType;
import com.hhdd.kada.download.DownloadManager;
import com.hhdd.kada.download.IDownloadTaskListener;
import com.hhdd.kada.main.common.DataLoadingView;
import com.hhdd.kada.main.controller.MainActivityController;
import com.hhdd.kada.main.controller.StoryCollectionSubscribeController;
import com.hhdd.kada.main.event.EventCenter;
import com.hhdd.kada.main.event.FavoriteEvent;
import com.hhdd.kada.main.event.ListenActivityAudioTimeEvent;
import com.hhdd.kada.main.event.LoginEvent;
import com.hhdd.kada.main.event.PauseEvent;
import com.hhdd.kada.main.event.StoryFinishDownloadEvent;
import com.hhdd.kada.main.event.StoryLimitFreeEndedEvent;
import com.hhdd.kada.main.event.SubscribeSuccessEvent;
import com.hhdd.kada.main.listener.OnChildViewClickListener;
import com.hhdd.kada.main.manager.DialogManager;
import com.hhdd.kada.main.manager.PermissionManager;
import com.hhdd.kada.main.model.ShareInfo;
import com.hhdd.kada.main.model.StoryCollectionDetail;
import com.hhdd.kada.main.model.StoryListItem;
import com.hhdd.kada.main.playback.PlaybackActivity;
import com.hhdd.kada.main.ui.adapter.ViewpagerAdapter;
import com.hhdd.kada.main.ui.dialog.ContentIsDeletedDialog;
import com.hhdd.kada.main.utils.AppUtils;
import com.hhdd.kada.main.utils.BitmapUtils;
import com.hhdd.kada.main.utils.Extflag;
import com.hhdd.kada.main.utils.MemorySizeUtils;
import com.hhdd.kada.main.utils.NetworkUtils;
import com.hhdd.kada.main.utils.SafeHandler;
import com.hhdd.kada.main.utils.ScreenUtil;
import com.hhdd.kada.main.utils.TimeUtil;
import com.hhdd.kada.main.utils.ToastUtils;
import com.hhdd.kada.main.views.CustomStoryView;
import com.hhdd.kada.main.views.FixedSpeedScroller;
import com.hhdd.kada.medal.Medal;
import com.hhdd.kada.medal.MedalDialog;
import com.hhdd.kada.medal.MedalGrantedEvent;
import com.hhdd.kada.medal.MedalManager;
import com.hhdd.kada.medal.UserTrack;
import com.hhdd.kada.module.audio.AudioName;
import com.hhdd.kada.module.player.AudioInfo;
import com.hhdd.kada.module.player.IMediaPlayer;
import com.hhdd.kada.module.player.OnPlayListener;
import com.hhdd.kada.module.player.PlayMode;
import com.hhdd.kada.module.talentplan.playback.TalentPlanPlaybackActivity;
import com.hhdd.kada.module.userhabit.StaCtrName;
import com.hhdd.kada.module.userhabit.StaPageName;
import com.hhdd.kada.share.ShareProvider;
import com.hhdd.kada.widget.ListenActivitySubscribeView;
import com.hhdd.kada.widget.support.KdLinearLayoutManager;
import com.hhdd.logger.LogHelper;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static com.hhdd.kada.Constants.PAUSE_MODE;
import static com.hhdd.kada.Constants.PLAY_MODE;
import static com.hhdd.kada.Constants.STOP_MODE;


/**
 * Created by zhengkaituo on 15/12/22.
 */
public class ListenActivity extends BaseActivity {

    private StoryCollectionSubscribeController subscribeController;

    private IMediaPlayer mShortMediaPlayer = (IMediaPlayer) ServiceProxyFactory.getProxy().getService(ServiceProxyName.KD_MEDIA_PLAYER);

    private static final String CREATE_ORDER = ListenActivity.class.getSimpleName() + "_CREATE_ORDER";
    private static final String SUBSCRIBE = ListenActivity.class.getSimpleName() + "_SUBSCRIBE";
    private ListenActivitySubscribeView subscribeView;
    private boolean needContinuePlay;  //音频打断后是否需要继续播放
    private boolean isNeedPlayNext = false; // 提示短音频播放结束后是否播放下一首听书
    private IDownloadTaskListener mDownloadListener;

    public static final void startActivity(Context context, boolean startByIcon) {
        if (context == null) {
            return;
        }

        Intent intent = new Intent(context, ListenActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("startByIcon", startByIcon);
        context.startActivity(intent);
    }

    public static final void startActivity(Context context, int storyId) {
        if (context == null) {
            return;
        }

        Intent intent = new Intent(context, ListenActivity.class);
        intent.putExtra("storyId", storyId);
        intent.putExtra("needLoad", true);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static final void startActivity(Context context, int storyId, List<StoryInfo> storyList) {
        if (context == null) {
            return;
        }

        Intent intent = new Intent(context, ListenActivity.class);
        intent.putExtra("storyId", storyId);
        intent.putExtra("storyList", (Serializable) storyList);
        context.startActivity(intent);
    }

    public static final void startActivity(Context context, int storyId, List<StoryInfo> storyList, int collectionExtFlag) {
        if (context == null) {
            return;
        }

        Intent intent = new Intent(context, ListenActivity.class);
        intent.putExtra("storyId", storyId);
        intent.putExtra("storyList", (Serializable) storyList);
        intent.putExtra("collectionExtFlag", collectionExtFlag);
        context.startActivity(intent);
    }

    public static final void startActivity(Context context, int storyId, long listenPosition, List<StoryInfo> storyList) {
        if (context == null) {
            return;
        }

        Intent intent = new Intent(context, ListenActivity.class);
        intent.putExtra("storyId", storyId);
        intent.putExtra("listenPosition", listenPosition);
        intent.putExtra("storyList", (Serializable) storyList);
        context.startActivity(intent);
    }

    public static final void startActivity(Context context, int storyId, List<StoryInfo> storyList, boolean isFreeListen, int collectionExtflag) {
        if (context == null) {
            return;
        }

        Intent intent = new Intent(context, ListenActivity.class);
        intent.putExtra("storyId", storyId);
        intent.putExtra("storyList", (Serializable) storyList);
        intent.putExtra("isFreeListen", isFreeListen);
        intent.putExtra("collectionExtFlag", collectionExtflag);
        context.startActivity(intent);
    }

    private RecyclerView mNextPlayRecyclerView;
    private RecyclerView mAlbumRecyclerView;
    private NextPlayAdapter mNextPlayAdapter;
    private AlbumPlayAdapter mAlbumPlayAdapter;
    private int mNextPlayItemSize;
    private int mAlbumPlayItemSize;

    private FrameLayout frameLayout;
    private ViewPager mViewPager;
    private LinearLayout mBottomContainer;
    private ViewpagerAdapter mViewpagerAdapter;
    private View back;
    private SeekBar mSeekBar;
    private TextView nowTime;
    private TextView allTime;
    private View btnNext;//下一首
    private View btnPrev;//上一首
    private View btnPlay;//播放暂停按钮
    //    private View btnPlayMode; //循环按钮
    private ImageView imgPlayMode;
    private ImageView btnCollect;//收藏
    private ImageView btnDownload;//下载
    private View btnShare;
    private View loadingContainer;
    private TextView storyTitle;
    private DataLoadingView mLoadingView;

    private int playIndex = 0;//viewpager 播放index
    private int storyIndex = 0;//合集列表 选中index
    private long mListenPosition = 0;
    int storyId;
    int collectionId;
    boolean isStartByIcon; //从小人启动
    boolean needLoadData; //传入单个storyId时 需要加载storyInfo
    boolean isFreeListen; //是否为试听模式
    private int mCollectionExtFlag;  //合辑的extflag


    List<StoryInfo> storyList;//合集和故事混排列表
    List<StoryInfo> storys = new ArrayList<StoryInfo>();//viewpager故事播放列表
    List<StoryInfo> albumstoryList = new ArrayList<StoryInfo>();//合集内故事列表
    StoryInfo nowPlayStoryInfo; //正在播放的故事
//    List<String> rgbList = new ArrayList<String>();

    List<Drawable> bgList = new ArrayList<Drawable>();
    private ValueAnimator animator1;
    private ValueAnimator animator2;
    private ValueAnimator rotateAnimator;

    private int status;// 单本状态

    private static final int RETRY_REQUEST_COUNT = 3;
    private int mRetryRequestCount = RETRY_REQUEST_COUNT;

    private OnPlayListener mOnPlayListener = new OnPlayListener() {
        @Override
        public void onPrepared(String audioTag, int playMode) {
        }

        @Override
        public void onCompletion(String audioTag, int playMode) {
            if (AudioName.NEET_MOM_SUBSCRIBE_AUDIO.equals(audioTag)) {
                ListenManager.getInstance().setShortAudioOnStoppedContinueListen(true);

                if (!ListenManager.getInstance().isPlaying()) {
                    if (isNeedPlayNext) {
                        if (!storyList.isEmpty() && playIndex + 1 < storyList.size()) {
                            StoryInfo info = storyList.get(playIndex + 1);
                            if (isLimitFreeCollection(mCollectionExtFlag)) {
                                stopPlay();
                                resetTime();
                                playAtIndex(playIndex + 1, 0, false);
                                // 加上这个和原来的保持一致
                                isNeedPlayNext = false;
                                return;
                            }
                            if (info.getType() == StoryInfo.TYPE_STORY && info.getCollectionId() != 0 && info.getSubscribe() != 1 && (info.getExtFlag() & Extflag.STORY_EXT_FLAG_256) != Extflag.STORY_EXT_FLAG_256) {
                                // 带锁标识的听书 不可听
                            } else {
                                stopPlay();
                                resetTime();
                                playAtIndex(playIndex + 1, 0, false);
                            }
                        }

                    } else if (needContinuePlay) {
                        needContinuePlay = false;
                        getPlaybackHandler().removeCallbacksAndMessages(null);
                        btnPlay.setSelected(true);
                        playAtIndex(playIndex, mListenPosition, false);
                    }

                    isNeedPlayNext = false;
                }

            }
        }

        @Override
        public void onError(String audioTag, int playMode) {
            if (AudioName.NEET_MOM_SUBSCRIBE_AUDIO.equals(audioTag)) {
                ListenManager.getInstance().setShortAudioOnStoppedContinueListen(true);

                if (!ListenManager.getInstance().isPlaying()) {
                    if (isNeedPlayNext) {
                        if (!storyList.isEmpty() && playIndex + 1 < storyList.size()) {
                            StoryInfo info = storyList.get(playIndex + 1);
                            if (isLimitFreeCollection(mCollectionExtFlag)) {
                                stopPlay();
                                resetTime();
                                playAtIndex(playIndex + 1, 0, false);
                                // 加上这个和原来的保持一致
                                isNeedPlayNext = false;
                                return;
                            }
                            if (info.getType() == StoryInfo.TYPE_STORY && info.getCollectionId() != 0 && info.getSubscribe() != 1 && (info.getExtFlag() & Extflag.STORY_EXT_FLAG_256) != Extflag.STORY_EXT_FLAG_256) {
                                // 带锁标识的听书 不可听
                            } else {
                                stopPlay();
                                resetTime();
                                playAtIndex(playIndex + 1, 0, false);
                            }
                        }

                    } else if (needContinuePlay) {
                        needContinuePlay = false;
                        getPlaybackHandler().removeCallbacksAndMessages(null);
                        btnPlay.setSelected(true);
                        playAtIndex(playIndex, mListenPosition, false);
                    }

                    isNeedPlayNext = false;
                }
            }
        }

        @Override
        public void onStop(String audioTag, int playMode) {

            if (AudioName.NEET_MOM_SUBSCRIBE_AUDIO.equals(audioTag)) {
                ListenManager.getInstance().setShortAudioOnStoppedContinueListen(true);
            } else if (AudioName.STORY_END_AUDIO.equals(audioTag) || AudioName.STORY_END_FREE_AUDIO.equals(audioTag)) {
                isNeedPlayNext = true;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listen_player);

        initIntentData(getIntent());

        //通过推送或其它外部方式进入绘本播放，若之前页面为绘本播放页面或优才计划绘本播放页面，暂停之前播放
        boolean shouldPostPauseEvent = false;
        List<Activity> activityList = ActivityHelper.getActivities();
        for (Activity activity : activityList) {
            if (activity instanceof PlaybackActivity || activity instanceof TalentPlanPlaybackActivity) {
                shouldPostPauseEvent = true;
                break;
            }
        }
        if (shouldPostPauseEvent) {
            EventBus.getDefault().post(new PauseEvent());
        }

        ListenManager.getInstance().setFreeListen(isFreeListen);
        //隐藏悬浮框
        EventBus.getDefault().post(new MainActivityController.OnHideFloatingWindowEvent());

        UserHabitService.getInstance().track(UserHabitService.newUserHabit("", "enterstory", TimeUtil.currentTime()));

        EventBus.getDefault().register(this);

        List<AudioInfo> audioInfos = mShortMediaPlayer.getCurrentPlayAudio();
        if (!audioInfos.isEmpty()) {
            for (AudioInfo audioInfo : audioInfos) {
                mShortMediaPlayer.stop(audioInfo.mPlayMode, audioInfo.mAudioTag);
            }
        }

        mShortMediaPlayer.addOnPlayListener(mOnPlayListener);


        frameLayout = (FrameLayout) findViewById(R.id.layout);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            frameLayout.setPadding(0, LocalDisplay.SCREEN_STATUS_HEIGHT, 0, 0);
        }
        mViewPager = (ViewPager) findViewById(R.id.listen_viewpager);
        mViewPager.setOffscreenPageLimit(1);
        mViewPager.getLayoutParams().height = (int) (ScreenUtil.getScreenWidth(this) * 6 / 7.0f) + LocalDisplay.dp2px(30);

        back = findViewById(R.id.back);
        btnShare = findViewById(R.id.share);
        mSeekBar = (SeekBar) findViewById(R.id.seekbar);
        nowTime = (TextView) findViewById(R.id.time_now);
        allTime = (TextView) findViewById(R.id.time_all);
        btnNext = findViewById(R.id.next);
        btnPrev = findViewById(R.id.prev);
        btnPlay = findViewById(R.id.play_control);
//        btnPlayMode = findViewById(R.id.play_mode);
        imgPlayMode = (ImageView) findViewById(R.id.play_mode_img);
        btnDownload = (ImageView) findViewById(R.id.download);
        btnCollect = (ImageView) findViewById(R.id.collect);
        mNextPlayRecyclerView = (RecyclerView) findViewById(R.id.nextplay_listview);
        mAlbumRecyclerView = (RecyclerView) findViewById(R.id.albumdetail_listview);
        mNextPlayAdapter = new NextPlayAdapter();
        mAlbumPlayAdapter = new AlbumPlayAdapter();
        mNextPlayItemSize = (int) (ScreenUtil.getScreenWidth() / 5f);
        mAlbumPlayItemSize = (int) (ScreenUtil.getScreenWidth() / 7f);
        mBottomContainer = (LinearLayout) findViewById(R.id.bottom_container);
        loadingContainer = findViewById(R.id.loading_container);
        storyTitle = (TextView) findViewById(R.id.story_title);
        mLoadingView = (DataLoadingView) findViewById(R.id.loading_view);
        subscribeView = (ListenActivitySubscribeView) findViewById(R.id.subscribe_view);

        initBgList();
        initView();
        loadData();

        controlViewPagerSpeed();

        mDownloadListener = new IDownloadTaskListener() {
            @Override
            public void onStart(int itemType, int itemId) {
                if (itemType == DownloadItemType.STORY
                        && nowPlayStoryInfo != null
                        && nowPlayStoryInfo.getId() == itemId) {
                    LogHelper.d("ListenActivity", "onStart " + itemType + " " + itemId);
                }
            }

            @Override
            public void onProgress(int itemType, int itemId, int p) {
                if (itemType == DownloadItemType.STORY
                        && nowPlayStoryInfo != null
                        && nowPlayStoryInfo.getId() == itemId) {
                    LogHelper.d("ListenActivity", "onProgress " + itemType + " " + itemId + " " + p);
                }
            }

            @Override
            public void onError(int itemType, int itemId, Throwable error) {
                if (itemType == DownloadItemType.STORY
                        && nowPlayStoryInfo != null
                        && nowPlayStoryInfo.getId() == itemId) {
                    LogHelper.d("ListenActivity", "onError " + itemType + " " + itemId + " " + error.toString());
                }
            }

            @Override
            public void onFinish(int itemType, int itemId, boolean complete) {
                if (itemType == DownloadItemType.STORY
                        && nowPlayStoryInfo != null
                        && nowPlayStoryInfo.getId() == itemId) {
                    btnDownload.setSelected(true);
                    ToastUtils.showToast("已下载完成");
                    EventCenter.fireEvent(new StoryFinishDownloadEvent());
                }
            }
        };
        ((DownloadManager) ServiceProxyFactory.getProxy().getService(ServiceProxyName.DOWNLOAD_MANAGER)).addListener(mDownloadListener);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        finish();
        Context context = KaDaApplication.applicationContext();
        intent.setClass(context, ListenActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    private void initIntentData(Intent intent) {
        if (intent == null) {
            return;
        }

        storyId = getIntent().getIntExtra("storyId", 0);
        mListenPosition = getIntent().getLongExtra("listenPosition", 0);
        collectionId = getIntent().getIntExtra("collectionId", 0);
        storyList = (List<StoryInfo>) getIntent().getSerializableExtra("storyList");
        needLoadData = getIntent().getBooleanExtra("needLoad", false);
        isStartByIcon = getIntent().getBooleanExtra("startByIcon", false);
        isFreeListen = getIntent().getBooleanExtra("isFreeListen", false);
        if (isStartByIcon) {
            mCollectionExtFlag = ListenManager.getInstance().getCollectionExtFlag();
        } else {
            mCollectionExtFlag = getIntent().getIntExtra("collectionExtFlag", 0);
            ListenManager.getInstance().setCollectionExtFlag(mCollectionExtFlag);
        }
    }

    FixedSpeedScroller mScroller = null;

    private void controlViewPagerSpeed() {
        try {
            Field mField;

            mField = ViewPager.class.getDeclaredField("mScroller");
            mField.setAccessible(true);

            mScroller = new FixedSpeedScroller(
                    mViewPager.getContext(),
                    new LinearInterpolator());
            mScroller.setmDuration(500);
            mField.set(mViewPager, mScroller);
        } catch (Exception e) {
            LogHelper.printStackTrace(e);
        }
    }

    private ViewPager.OnPageChangeListener mOnPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            if (bgList.size() > 0) {
                int bgindex = position % bgList.size();
                frameLayout.setBackgroundDrawable(bgList.get(bgindex));
            }

            if (playIndex != position) {
                StoryInfo info = storyList.get(position);

                if (info.getType() == StoryInfo.TYPE_STORY && info.getCollectionId() != 0 && info.getSubscribe() != 1 && (info.getExtFlag() & Extflag.STORY_EXT_FLAG_256) != Extflag.STORY_EXT_FLAG_256) {
                    mViewPager.setCurrentItem(playIndex, true);
                } else {
                    stopPlay();
                    resetTime();
                    playAtIndex(position, 0, false);
                }
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    void initView() {

        frameLayout.setBackgroundDrawable(bgList.get(0));

        mNextPlayRecyclerView.setAdapter(mNextPlayAdapter);
        mNextPlayRecyclerView.setLayoutManager(new KdLinearLayoutManager(ListenActivity.this, LinearLayoutManager.HORIZONTAL, false));
        mAlbumRecyclerView.setAdapter(mAlbumPlayAdapter);
        mAlbumRecyclerView.setLayoutManager(new KdLinearLayoutManager(ListenActivity.this, LinearLayoutManager.HORIZONTAL, false));


        back.setOnClickListener(new KaDaApplication.OnClickWithAnimListener() {
            @Override
            public void OnClickWithAnim(View v) {
                finish();
            }
        });

        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                pauseImpl();
            }

            @Override
            public void onStopTrackingTouch(final SeekBar seekBar) {
                pauseImpl();
                final long listenPosition = seekBar.getProgress();
                nowTime.setText(TimeUtil.formatLongToTimeStr(listenPosition));
                seek(playIndex, listenPosition, false);
            }
        });

        mViewpagerAdapter = new ViewpagerAdapter(this);
        mViewPager.setAdapter(mViewpagerAdapter);
        mViewPager.addOnPageChangeListener(mOnPageChangeListener);

        btnNext.setOnClickListener(new KaDaApplication.OnClickWithAnimListener() {
            @Override
            public void OnClickWithAnim(View v) {
                if (playIndex >= 0) {
                    if (storyList != null && storyList.size() > playIndex) {
                        UserHabitService.getInstance().track(UserHabitService.newUserHabit("" + storyList.get(playIndex).getId(), "clickplaynextstory", TimeUtil.currentTime()));
                    }
                    if (storyList.size() > 0 && playIndex + 1 < storyList.size()) {
                        StoryInfo info = storyList.get(playIndex + 1);
                        if (isLimitFreeCollection(mCollectionExtFlag)) {
                            stopPlay();
                            playAtIndex(playIndex + 1, 0, false);
                            return;
                        }

                        if (info.getType() == StoryInfo.TYPE_STORY && info.getCollectionId() != 0 && info.getSubscribe() != 1 && (info.getExtFlag() & Extflag.STORY_EXT_FLAG_256) != Extflag.STORY_EXT_FLAG_256) {
                            toggleSubscribeView(true);
                        } else {
                            stopPlay();
                            playAtIndex(playIndex + 1, 0, false);
                        }
                    }
                }
            }
        });

        btnPrev.setOnClickListener(new KaDaApplication.OnClickWithAnimListener() {
            @Override
            public void OnClickWithAnim(View v) {
                if (playIndex >= 0) {
                    if (storyList != null && storyList.size() > playIndex) {
                        UserHabitService.getInstance().track(UserHabitService.newUserHabit("" + storyList.get(playIndex).getId(), "clickplaypreviousstory", TimeUtil.currentTime()));
                    }

                    if (playIndex - 1 >= 0 && storyList.size() > 0) {
                        StoryInfo info = storyList.get(playIndex - 1);
                        if (isLimitFreeCollection(mCollectionExtFlag)) {
                            stopPlay();
                            playAtIndex(playIndex - 1, 0, false);
                            return;
                        }

                        if (info.getType() == StoryInfo.TYPE_STORY && info.getCollectionId() != 0 && info.getSubscribe() != 1 && (info.getExtFlag() & Extflag.STORY_EXT_FLAG_256) != Extflag.STORY_EXT_FLAG_256) {
                            toggleSubscribeView(true);
                        } else {
                            stopPlay();
                            playAtIndex(playIndex - 1, 0, false);
                        }
                    }
                }
            }
        });

        btnPlay.setOnClickListener(new KaDaApplication.OnClickWithAnimListener() {
            @Override
            public void OnClickWithAnim(View v) {
                getPlaybackHandler().removeCallbacksAndMessages(null);
                if (btnPlay.isSelected()) {
                    stopListen();
                } else {
                    if (mShortMediaPlayer != null && mShortMediaPlayer.getCurrentPlayAudio() != null && mShortMediaPlayer.getCurrentPlayAudio().size() > 0) {
                        if (TextUtils.equals(mShortMediaPlayer.getCurrentPlayAudio().get(0).mAudioTag, AudioName.STORY_BEGIN_AUDIO)) {
                            setListAnimMode(nowPlayStoryInfo, PLAY_MODE);
                        }
                    }
                    mShortMediaPlayer.stop(PlayMode.IMMEDIATELY_PLAY_MODE, AudioName.NEET_MOM_SUBSCRIBE_AUDIO);
                    btnPlay.setSelected(true);
                    playAtIndex(playIndex, mListenPosition, false);
                }
            }
        });

        if (ListenManager.getInstance().isSingleCycle) {
            imgPlayMode.setSelected(true);
        }

        imgPlayMode.setOnClickListener(new KaDaApplication.OnClickWithAnimListener() {
            @Override
            public void OnClickWithAnim(View v) {

                if (imgPlayMode.isSelected()) {
                    if (storyList != null && storyList.size() > playIndex) {
                        UserHabitService.getInstance().track(UserHabitService.newUserHabit("" + storyList.get(playIndex).getId(), "clickstoploopplay", TimeUtil.currentTime()));
                    }
                    ToastUtils.showToast("已切换到顺序播放");
                    imgPlayMode.setSelected(false);
                    ListenManager.getInstance().setIsSingleCycle(false);
                } else {
                    if (storyList != null && storyList.size() > playIndex) {
                        UserHabitService.getInstance().track(UserHabitService.newUserHabit("" + storyList.get(playIndex).getId(), "clickbeginloopplay", TimeUtil.currentTime()));
                    }
                    ToastUtils.showToast("已切换到单曲循环");
                    imgPlayMode.setSelected(true);
                    ListenManager.getInstance().setIsSingleCycle(true);
                }
            }
        });

        btnDownload.setOnClickListener(new KaDaApplication.OnClickWithAnimListener() {
            @Override
            public void OnClickWithAnim(View v) {
                if (!NetworkUtils.isReachable()) {
                    ToastUtils.showToast(getResources().getString(R.string.network_error));
                    return;
                }

                if (nowPlayStoryInfo != null) {
                    UserHabitService.getInstance().trackHabit(UserHabitService.newUserHabit(nowPlayStoryInfo.getId() + "," + nowPlayStoryInfo.getName(), "story_reading_page_download", TimeUtil.currentTime()));
                    if (btnDownload.isSelected()) {
                        ToastUtils.showToast("下载已完成", Gravity.CENTER);
                    } else {
                        if (status != Constants.ONLINE && status != 0) {
                            ToastUtils.showToast(getResources().getString(R.string.content_is_offline_can_not_download));
                            return;
                        }
                        // 没有订阅并且处于限免状态，阻止下载
                        if (nowPlayStoryInfo.getSubscribe() != 1 &&
                                isLimitFreeCollection(mCollectionExtFlag)) {
                            ToastUtils.showToast(AppUtils.getString(R.string.content_is_limit_free_can_not_download));
                            return;
                        }
                        if (ContextCompat.checkSelfPermission(ListenActivity.this, WRITE_EXTERNAL_STORAGE)
                                != PackageManager.PERMISSION_GRANTED) {
                            //申请WRITE_EXTERNAL_STORAGE权限
                            ((PermissionManager) ServiceProxyFactory.getProxy().getService(ServiceProxyName.PERMISSION_MANAGER)).requestPermission(ListenActivity.this, PermissionManager.PERMISSION_WRITE_EXTERNAL_STORAGE);
                        } else {
                            if (MemorySizeUtils.getAvailableMemoryByMb() >= 200) {
                                ToastUtils.showToast("已加入下载队列", Gravity.CENTER);
                                com.hhdd.kada.main.model.StoryInfo storyInfo = new com.hhdd.kada.main.model.StoryInfo(0, nowPlayStoryInfo.getName(), nowPlayStoryInfo.getCoverUrl(), nowPlayStoryInfo.getExtFlag(),
                                        (int) nowPlayStoryInfo.getId(), nowPlayStoryInfo.getSoundUrl(), TimeUtil.audioMilliTime(nowPlayStoryInfo.getTime()), nowPlayStoryInfo.getAuthor(), nowPlayStoryInfo.getMinAge(), nowPlayStoryInfo.getMaxAge(), nowPlayStoryInfo.getVersion(), nowPlayStoryInfo.getClickCount());
                                ((DownloadManager) ServiceProxyFactory.getProxy().getService(ServiceProxyName.DOWNLOAD_MANAGER)).download(storyInfo);
                            } else {
                                ((PermissionManager) ServiceProxyFactory.getProxy().getService(ServiceProxyName.PERMISSION_MANAGER)).requestPermission(ListenActivity.this, PermissionManager.STORAGE_NOT_ENOUGH);
                            }
                        }

                    }
                }
            }
        });

        btnShare.setOnClickListener(new KaDaApplication.OnClickWithAnimListener() {
            @Override
            public void OnClickWithAnim(View v) {
                if (!NetworkUtils.isReachable()) {
                    ToastUtils.showToast(getResources().getString(R.string.network_error));
                    return;
                }

                if (storyList != null && storyList.size() > playIndex) {
                    UserHabitService.getInstance().track(UserHabitService.newUserHabit("" + storyList.get(playIndex).getId(), "click_story_playpage_share", TimeUtil.currentTime()));
                    StoryInfo info = storyList.get(playIndex);
                    if (status != Constants.ONLINE && status != 0) {
                        ToastUtils.showToast(getResources().getString(R.string.content_is_offline_can_not_share));
                        return;
                    }

                    if (info.getType() == Constants.TYPE_STORY_SINGLE && info.getCollectionId() == 0) {
                        String title = info.getName();
                        if (TextUtils.isEmpty(title)) {
                            title = getResources().getString(R.string.share_story_default_title);
                        }

                        long storyId = info.getId();
                        ShareInfo shareInfo = new ShareInfo();
                        shareInfo.setTitle(title);
                        shareInfo.setContent(AppUtils.getString(R.string.share_single_story_content));
                        shareInfo.setImageUrl(info.getCoverUrl());
                        shareInfo.setTargetUrl(API.STORY_SINGLE_SHARE_URL + storyId);
                        ShareProvider.share(ListenActivity.this, shareInfo, new MyShareListener(storyId));
                    } else {
                        //是否为新合集
                        boolean isNewCollectionType = info.getCollectType() == Constants.TYPE_STORY_COLLECT;
                        String tempTitle = info.getName();
                        if (TextUtils.isEmpty(tempTitle)) {
                            tempTitle = getResources().getString(R.string.share_story_default_title);
                        }

                        String title = isNewCollectionType ? tempTitle : AppUtils.getString(R.string.share_default_title);
                        long collectionId = info.getCollectionId();
                        ShareInfo shareInfo = new ShareInfo();
                        shareInfo.setTitle(title);
                        shareInfo.setContent(isNewCollectionType ? " " : " " + info.getName());
                        shareInfo.setImageUrl(info.getCollectCoverUrl());
                        shareInfo.setTargetUrl((isNewCollectionType ? API.STORY_COLLECTION_SHARE_URL() : API.STORY_SINGLE_SHARE_URL) + collectionId);
                        ShareProvider.share(ListenActivity.this, shareInfo, new MyShareListener(collectionId));
                    }
                }
            }
        });

        btnCollect.setOnClickListener(new KaDaApplication.OnClickWithAnimListener() {
            @Override
            public void OnClickWithAnim(View v) {
                if (!NetworkUtils.isReachable()) {
                    ToastUtils.showToast(getResources().getString(R.string.network_error));
                    return;
                }

                if (btnCollect.isSelected()) {
                    if (nowPlayStoryInfo != null) {
                        UserHabitService.getInstance().trackHabit(UserHabitService.newUserHabit(nowPlayStoryInfo.getId() + "," + nowPlayStoryInfo.getName() + "," + 0, "story_reading_page_favorite", TimeUtil.currentTime()));
                        if (status != Constants.ONLINE && status != 0) {
                            ToastUtils.showToast(getResources().getString(R.string.content_is_offline_can_not_cancel_collect));
                            return;
                        }
                        final CustomDialogManager customDialogManager = DialogFactory.getCustomDialogManager();
                        customDialogManager.showDialog(ListenActivity.this);
                        StoryAPI.storyAPI_collectStory((int) nowPlayStoryInfo.getId(), 2, nowPlayStoryInfo.getVersion()).post(new API.ResponseHandler() {
                            @Override
                            public void onSuccess(Object responseData) {
                                ((FavoriteService) ServiceProxyFactory.getProxy().getService(ServiceProxyName.FAVORITE_SERVICE)).removeStoryId((int) nowPlayStoryInfo.getId());

                                getHandler().post(new Runnable() {
                                    @Override
                                    public void run() {
                                        ToastUtils.showToast("取消收藏");
                                        EventCenter.fireEvent(new FavoriteEvent(FavoriteEvent.TYPE_STORY));
                                        btnCollect.setSelected(false);
                                        customDialogManager.dismissDialog(ListenActivity.this);
                                    }
                                });
                            }

                            @Override
                            public void onFailure(int code, String message) {
//                                ToastUtils.showToast(message);
                                customDialogManager.dismissDialog(ListenActivity.this);
                                ToastUtils.showToast("取消收藏失败");
                            }
                        });
                    }
                } else {
                    if (nowPlayStoryInfo != null) {
                        // 没有订阅且处于限免状态，阻止收藏
                        if (nowPlayStoryInfo.getSubscribe() != 1 &&
                                isLimitFreeCollection(mCollectionExtFlag)) {
                            ToastUtils.showToast(AppUtils.getString(R.string.content_is_limit_free_can_not_collect));
                            return;
                        }
                        UserHabitService.getInstance().trackHabit(UserHabitService.newUserHabit(nowPlayStoryInfo.getId() + "," + nowPlayStoryInfo.getName() + "," + 1, "story_reading_page_favorite", TimeUtil.currentTime()));
                        if (status != Constants.ONLINE && status != 0) {
                            ToastUtils.showToast(getResources().getString(R.string.content_is_offline_can_not_collect));
                            return;
                        }
                        final CustomDialogManager customDialogManager = DialogFactory.getCustomDialogManager();
                        customDialogManager.showDialog(ListenActivity.this);
                        StoryAPI.storyAPI_collectStory((int) nowPlayStoryInfo.getId(), 1, nowPlayStoryInfo.getVersion()).post(new API.ResponseHandler() {
                            @Override
                            public void onSuccess(Object responseData) {

                                ((FavoriteService) ServiceProxyFactory.getProxy().getService(ServiceProxyName.FAVORITE_SERVICE)).addStoryId((int) nowPlayStoryInfo.getId());

                                getHandler().post(new Runnable() {
                                    @Override
                                    public void run() {
                                        customDialogManager.dismissDialog(ListenActivity.this);
                                        ToastUtils.showToast("已收藏");
                                        EventCenter.fireEvent(new FavoriteEvent(FavoriteEvent.TYPE_STORY));
                                        btnCollect.setSelected(true);
                                    }
                                });

                            }

                            @Override
                            public void onFailure(int code, String message) {
//                                ToastUtils.showToast(message);
                                customDialogManager.dismissDialog(ListenActivity.this);

                                ToastUtils.showToast("收藏失败");
                            }
                        });
                    }
                    UserTrack.track(UserTrack.fcs);
                }
            }
        });

        subscribeView.setOnChildViewClickListener(new OnChildViewClickListener() {
            @Override
            public void onChildViewClick(View childView, int action, Object obj) {
                switch (childView.getId()) {
                    case R.id.tv_subscribe:
                        if (nowPlayStoryInfo.getCollectionId() != 0 && nowPlayStoryInfo.getSubscribe() != 1) {
                            subscribeController = new StoryCollectionSubscribeController(ListenActivity.this, SUBSCRIBE, CREATE_ORDER);
                            subscribeController.setRelatedData(nowPlayStoryInfo.getCollectionId(), nowPlayStoryInfo.getSubscribe(), nowPlayStoryInfo.getStatus(), mCollectionExtFlag, nowPlayStoryInfo.getType());
                            if (subscribeController != null) {
                                subscribeController.doSubscribe(new DialogInterface.OnShowListener() {
                                    @Override
                                    public void onShow(DialogInterface dialog) {
                                    }
                                });
                            }
                        }
                        UserHabitService.getInstance().trackHabit(UserHabitService.newUserHabit(nowPlayStoryInfo.getId() + "," + nowPlayStoryInfo.getCollectionId(), StaCtrName.story_play_subsribe_close_click, TimeUtil.currentTime()));
                        break;
                    case R.id.iv_subscribe_close:
                        toggleSubscribeView(false);
                        UserHabitService.getInstance().trackHabit(UserHabitService.newUserHabit(nowPlayStoryInfo.getId() + "," + nowPlayStoryInfo.getCollectionId(), StaCtrName.story_play_subscribe_click, TimeUtil.currentTime()));
                        break;
                }
            }
        });
    }

    // 是否是限免合集
    private boolean isLimitFreeCollection(int collectionExtFlag) {
        boolean isLimitFree = (collectionExtFlag & Extflag.STORY_FLAG_FREE_NEW) == Extflag.STORY_FLAG_FREE_NEW ||
                (collectionExtFlag & Extflag.STORY_FLAG_FREE_ALL) == Extflag.STORY_FLAG_FREE_ALL;
        LogHelper.d("Randy", "collectionExtFlag:" + collectionExtFlag + " isLimitFree:" + isLimitFree);
        return isLimitFree;
    }

    private void initBgList() {
        bgList.add(new BitmapDrawable(BitmapUtils.readBitmap(this, R.drawable.bg_listen_activity_1)));
        bgList.add(new BitmapDrawable(BitmapUtils.readBitmap(this, R.drawable.bg_listen_activity_2)));
        bgList.add(new BitmapDrawable(BitmapUtils.readBitmap(this, R.drawable.bg_listen_activity_3)));
        bgList.add(new BitmapDrawable(BitmapUtils.readBitmap(this, R.drawable.bg_listen_activity_4)));

    }

    void loadData() {
        if (needLoadData) {
            mLoadingView.setVisibility(View.VISIBLE);
            mLoadingView.setBackgroundColor(getResources().getColor(R.color.alphablack3));
            mLoadingView.setOnRetryClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mLoadingView.showLoading();
                    loadStoryById(storyId);
                }
            });
            mLoadingView.showLoading();
            if (NetworkUtils.isReachable()) {
                loadStoryById(storyId);
            } else {
                mLoadingView.showNetError();
            }
        } else {
            if (isStartByIcon) {
                storys.clear();
                storys.addAll(ListenManager.getInstance().getStorys());
                storyList = new ArrayList<>();
                storyList.addAll(ListenManager.getInstance().getStoryList());
            } else {
                if (storyList != null && storyList.size() > 0) {
                    storys.addAll(storyList);
                } else {
                    mLoadingView.setVisibility(View.VISIBLE);
                    mLoadingView.showEmpty();
                }
            }
        }

        if (storyList != null && storyList.size() > 0) {
            mViewpagerAdapter.clear();
            mViewpagerAdapter.setStoryList(storyList);
            mViewpagerAdapter.notifyDataSetChanged();

            initPlay();
        }

        if (storys != null && storys.size() > 0) {
            mNextPlayAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onBackPressed() {
        tryStopAudio();
        super.onBackPressed();
    }

    public interface MyCallback {
        public void playAfterLoad();
    }

    void loadStoryById(int storyId) {
        StoryAPI.getStoryInfoByStoryId(storyId).get(new API.ResponseHandler<List<StoryListItem>>() {
            @Override
            public void onSuccess(List<StoryListItem> response) {
                if (response != null && response.size() > 0) {
                    StoryListItem item = response.get(0);

                    storys.clear();
                    storys.add(StoryInfo.createInfoByNewStory(item));
                    storyList = new ArrayList<>();
                    storyList.addAll(storys);

                    mViewpagerAdapter.clear();
                    mViewpagerAdapter.setStoryList(storyList);

                    getHandler().post(new Runnable() {
                        @Override
                        public void run() {
                            mLoadingView.hide();

                            mViewpagerAdapter.notifyDataSetChanged();

                            mNextPlayAdapter.notifyDataSetChanged();

                            initPlay();
                        }
                    });
                } else {
                    getHandler().post(new Runnable() {
                        @Override
                        public void run() {
                            mLoadingView.showError();
                        }
                    });
                }
            }

            @Override
            public void onFailure(int code, String message) {
                mLoadingView.showError();
                ToastUtils.showToast("网络开小差了哦，请检查您的网络");
            }
        });
    }

    void loadCollectionData(int position, final MyCallback callback) {
//        albumstoryList.clear();
//        mAlbumPlayAdapter.notifyDataSetChanged();

        if (position < 0 || position >= storys.size()) {
            return;
        }

        StoryInfo storyInfo = storys.get(position);
        final int collectId = (int) storyInfo.getCollectionId();
        int collectType = storyInfo.getCollectType();

        StoryService.loadCollection(collectId, collectType, new com.hhdd.core.service.DefaultCallback<StoryCollectionDetail>() {
            @Override
            public void onDataReceived(StoryCollectionDetail data) {
                if (data != null && data.getItems() != null && data.getItems().size() > 0) {
                    albumstoryList.clear();
                    mCollectionExtFlag = data.getExtFlag();
                    ListenManager.getInstance().setCollectionExtFlag(mCollectionExtFlag);
                    for (int i = 0; i < data.getItems().size(); i++) {
                        com.hhdd.kada.main.model.StoryInfo storyInfo = data.getItems().get(i);
                        StoryInfo info = StoryInfo.createInfoByStoryInfo(storyInfo, data);
                        if (data.getSubscribe() == 1) {
                            albumstoryList.add(info);
                        } else if ((storyInfo.getExtFlag() & Extflag.STORY_EXT_FLAG_256) == Extflag.STORY_EXT_FLAG_256) {
                            albumstoryList.add(info);
                        }
                    }
                    mAlbumPlayAdapter.notifyDataSetChanged();
                    if (callback != null) {
                        callback.playAfterLoad();
                    }
                } else {
                    showContentDialog(2, 1, collectId);
                }
            }

            @Override
            public void onException(String reason) {
                super.onException(reason);
            }
        });
    }

    public void initPlay() {
        ListenManager.getInstance().addCallback(uiListener);

        if (isStartByIcon) {
            mSeekBar.setEnabled(true);
            mListenPosition = ListenManager.getInstance().getCurrentPosition();
            playIndex = ListenManager.getInstance().getCurrentIndex();
            nowPlayStoryInfo = ListenManager.getInstance().getNowStory();
            mCollectionExtFlag = ListenManager.getInstance().getCollectionExtFlag();
            if (nowPlayStoryInfo == null) {
                return;
            }

            storyId = (int) nowPlayStoryInfo.getId();
            if (playIndex == -1) {
                playIndex = 0;
            }
            if (storyList != null && storyList.size() != 0 && playIndex < storyList.size()) {
                StoryInfo storyInfo = storyList.get(playIndex);
                for (int i = 0; i < storys.size(); i++) {
                    if (storys.get(i).equals(storyInfo) || (storys.get(i).getType() == StoryInfo.TYPE_COLLECTION && storys.get(i).getCollectionId() == storyInfo.getCollectionId())) {
                        storyIndex = i;
                        break;
                    }
                }
            }
            if (!ListenManager.getInstance().isPause() || ListenManager.getInstance().isPlaying()) {
                btnPlay.setSelected(true);
                getHandler().removeCallbacksAndMessages(null);
                Message msg = getHandler().obtainMessage();
                msg.what = STORY_TIMER;
                getHandler().sendMessage(msg);
            }

            //展开合集
            if (storys.size() > storyIndex) {
                if (storys.get(storyIndex).getType() == StoryInfo.TYPE_COLLECTION) {
                    loadCollectionData(storyIndex, new MyCallback() {
                        @Override
                        public void playAfterLoad() {
                            startAnim(storyIndex, 200);
                            if (albumstoryList != null) {
                                for (int i = 0; i < albumstoryList.size(); i++) {
                                    if (storyList.get(playIndex).equals(albumstoryList.get(i))) {
                                        scolltoCollectIndex(i);
                                    }
                                }
                            }

                        }
                    });
                }
            }

            getHandler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (mViewPager != null) {
                        mViewPager.setCurrentItem(playIndex, false);
                    }
                    if (!ListenManager.getInstance().isPause() || ListenManager.getInstance().isPlaying()) {
                        startViewpagerAnim(playIndex);
                    }
                }
            }, 50);
        } else {
            mSeekBar.setEnabled(false);
            btnPlay.setSelected(true);
            for (int i = 0; i < storyList.size(); i++) {
                if ((int) storyList.get(i).getId() == storyId) {
                    storyIndex = playIndex = i;
                    nowPlayStoryInfo = storyList.get(i);
                    if (nowPlayStoryInfo != null) {
                        ListenManager.getInstance().setCollectId((int) nowPlayStoryInfo.getCollectionId());
                    }
                    break;
                }
            }

            ListenManager.getInstance().setCollectionExtFlag(mCollectionExtFlag);

            boolean needReset = false;

            if (ListenManager.getInstance().getNowSelectId() == storyId) {
                if (ListenManager.getInstance().isPlaying() || !ListenManager.getInstance().isPause()) {
                    getHandler().removeCallbacksAndMessages(null);
                    Message msg = getHandler().obtainMessage();
                    msg.what = STORY_TIMER;
                    getHandler().sendMessage(msg);
                    if (playIndex >= 0 && playIndex < mViewPager.getAdapter().getCount()) {
                        initTime();
                        mViewPager.setCurrentItem(playIndex, false);
                        mSeekBar.setEnabled(true);
                        btnPlay.setSelected(true);
                        getPlaybackHandler().removeCallbacksAndMessages(null);
                        getPlaybackHandler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                startViewpagerAnim(playIndex);
                            }
                        }, 200);
                    }

                    needReset = false;
                } else {
                    needReset = true;
                }
            } else {
                stopPlay();
                needReset = true;
            }
            ListenManager.getInstance().setStoryList(storyList);
            ListenManager.getInstance().setStorys(storys);

            final boolean reset = needReset;
            if (reset) {
                getHandler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        playAtIndex(playIndex, mListenPosition, reset);
                    }
                }, 50);
            }
        }

        showDownloadAndFavoriteStatus(storyId);

        if (bgList.size() > 0) {
            if (playIndex == -1) {
                frameLayout.setBackgroundDrawable(bgList.get(0));
            } else {
                frameLayout.setBackgroundDrawable(bgList.get(playIndex % bgList.size()));
            }
        }
        mNextPlayRecyclerView.scrollToPosition(storyIndex);

        long time;
        if (nowPlayStoryInfo == null || nowPlayStoryInfo.getTime() == null) {
            time = 0;
        } else {
            time = nowPlayStoryInfo.getTime();
        }
        showStoryTitle(nowPlayStoryInfo);
        mSeekBar.setMax((int) time);
        allTime.setText(TimeUtil.formatLongToTimeStr(time));

        if (mListenPosition != 0) {
            nowTime.setText(TimeUtil.formatLongToTimeStr(mListenPosition));
            mSeekBar.setProgress((int) mListenPosition);
        }

    }


    void scolltoIndex(int index) {
        ((LinearLayoutManager) mNextPlayRecyclerView.getLayoutManager()).scrollToPositionWithOffset(index, 0);
        mNextPlayAdapter.notifyDataSetChanged();
    }

    void scolltoCollectIndex(final int index) {

        ((LinearLayoutManager) mAlbumRecyclerView.getLayoutManager()).scrollToPositionWithOffset(index, 0);
        mAlbumPlayAdapter.notifyDataSetChanged();
    }

    void resetTime() {
        mSeekBar.setProgress(0);
        mSeekBar.setMax(0);
        allTime.setText("00:00");
        nowTime.setText("00:00");
    }

    void initTime() {
        getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mViewpagerAdapter != null && playIndex >= 0 && playIndex < mViewpagerAdapter.getCount()) {
                    long time = mViewpagerAdapter.getItem(playIndex).getTime();//TimeUtil.audioMilliTime(mViewpagerAdapter.getItem(playIndex).getTime());
                    mSeekBar.setProgress(0);
                    mSeekBar.setMax((int) time);
                    allTime.setText(TimeUtil.formatLongToTimeStr(time));
                    nowTime.setText("00:00");
                } else {
                    mSeekBar.setProgress(0);
                    mSeekBar.setMax(0);
                    allTime.setText("00:00");
                    nowTime.setText("00:00");
                }
            }
        }, 50);
    }

    private void seek(final int position, final long listenPosition, final boolean reset) {

        final boolean isSelected = btnPlay.isSelected();
        if (!isSelected) {
            btnPlay.setSelected(true);
        }


        if (!reset) {
            getHandler().removeMessages(STORY_TIMER);
        }
        getPlaybackHandler().removeCallbacksAndMessages(null);
        getPlaybackHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (position >= 0 && position < mViewpagerAdapter.getCount()) {
                    startViewpagerAnim(position);

                    Context context = KaDaApplication.getInstance();
                    Intent listenIntent = new Intent(context, ListenService2.class);
                    listenIntent.setAction(ListenService2.INTENT_ACTION);
                    listenIntent.putExtra(ListenService2.INTENT_PARAM_TYPE, ListenService2.Types.SEEK);
                    listenIntent.putExtra(ListenService2.INTENT_PARAM_IS_PLAYING, isSelected);
                    listenIntent.putExtra(ListenService2.INTENT_PARAM_INDEX, position);
                    listenIntent.putExtra(ListenService2.INTENT_PARAM_START_POSITION, listenPosition);
                    listenIntent.putExtra(ListenService2.INTENT_PARAM_NOTIFICATION_INFO, nowPlayStoryInfo);
                    KaDaApplication.getInstance().startService(listenIntent);
                }
            }
        }, 200);
    }

    private void playAtIndex(final int position, final long listenPosition, final boolean reset) {
        if (position < 0 || storyList == null || storyList.isEmpty() || storyList.size() <= position) {
            return;
        } else if (mRetryRequestCount < 0) {
            // 播放之前先判断内容是否下线，如果状态未知，请求接口拿到数据后在判断是否可以播放；
            // 防止请求不到状态（接口未上线时会出现这种场景）出现递归死循环，这里做连续递归3次后 做finish操作
            finish();
            return;
        }

        if (position >= 0 && position < mViewpagerAdapter.getCount()) {
            if (playIndex != position) {
                initTime();
                mSeekBar.setEnabled(false);
            }
            playIndex = position;

            mViewPager.setCurrentItem(position, false);
        }
        btnPlay.setSelected(true);

        getHandler().removeMessages(STORY_TIMER);
        getPlaybackHandler().removeCallbacksAndMessages(null);

        final StoryInfo storyInfo = storyList.get(position);

        status = storyInfo.getStatus();
        if (status == Constants.AUDITING || status == Constants.NO_PIC || status == Constants.DELETE || status == Constants.TESTING || status == Constants.HIDE) {
            mRetryRequestCount = RETRY_REQUEST_COUNT;
            stopPlay();
            stopViewpagerAnim();
            showContentDialog(2, 2, (int) storyInfo.getCollectionId());
            return;
        } else if (status == Constants.ONLINE || status == Constants.OFFLINE) {
            mRetryRequestCount = RETRY_REQUEST_COUNT;
            // 上线或下线都继续播放
            startViewpagerAnim(position);
            status = 0;

        } else {
            if (NetworkUtils.isReachable() && storyInfo.getCollectType() == 1) {
                mRetryRequestCount--;
                // 重新请求接口刷新status
                getStoryStatusByIdAndPlay((int) storyInfo.getId(), position, listenPosition, reset);
                startViewpagerAnim(position);
//            onEvent(new StoryService.StartReadingEvent(storyInfo));
                return;
            }
        }
        getPlaybackHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (position >= 0 && position < mViewpagerAdapter.getCount()) {
                    Context context = KaDaApplication.getInstance();
                    Intent listenIntent = new Intent(context, ListenService2.class);
                    listenIntent.setAction(ListenService2.INTENT_ACTION);
                    listenIntent.putExtra(ListenService2.INTENT_PARAM_TYPE, ListenService2.Types.START);
                    listenIntent.putExtra(ListenService2.INTENT_PARAM_INDEX, position);
                    listenIntent.putExtra(ListenService2.INTENT_PARAM_RESET, reset);
                    listenIntent.putExtra(ListenService2.INTENT_PARAM_START_POSITION, listenPosition);
                    listenIntent.putExtra(ListenService2.INTENT_PARAM_NOTIFICATION_INFO, storyInfo);
                    KaDaApplication.getInstance().startService(listenIntent);
                }
            }
        }, 500);
    }

    Runnable hideSubscribeViewRunnable = new Runnable() {
        @Override
        public void run() {
            toggleSubscribeView(false);
        }
    };

    private void toggleSubscribeView(boolean isStart) {

        if (isStart) {
            if (subscribeView.getVisibility() == View.VISIBLE) {
                return;
            }
            UserHabitService.getInstance().trackHabit(UserHabitService.newUserHabit(nowPlayStoryInfo.getId() + "," + nowPlayStoryInfo.getCollectionId(), StaPageName.story_play_subsribe_tip_view, TimeUtil.currentTime()));
            playRawMedia();
            ValueAnimator alphaAnimation = ObjectAnimator.ofFloat(0.0F, 1.0F).setDuration(500);
            alphaAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float cVal = (Float) animation.getAnimatedValue();
                    subscribeView.setAlpha(cVal);
                    if (cVal == 1) {
                        subscribeView.setVisibility(View.VISIBLE);
                    }
                }
            });
            subscribeView.setVisibility(View.VISIBLE);
            alphaAnimation.start();
            getHandler().postDelayed(hideSubscribeViewRunnable, 5000);
        } else {
            ValueAnimator alphaAnimation = ObjectAnimator.ofFloat(1.0F, 0.0F).setDuration(500);
            alphaAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float cVal = (Float) animation.getAnimatedValue();
                    subscribeView.setAlpha(cVal);
                    if (cVal == 0) {
                        subscribeView.setVisibility(View.GONE);
                    }
                }
            });
            getHandler().removeCallbacks(hideSubscribeViewRunnable);
            alphaAnimation.start();
        }
    }

    private void showDownloadAndFavoriteStatus(int storyId) {
        //下载标志
//        if (DatabaseManager.getInstance().collectionItemStatusDB().isDownloadFinish(storyId)) {
        if (((DownloadManager) ServiceProxyFactory.getProxy().getService(ServiceProxyName.DOWNLOAD_MANAGER)).isStoryDownloadFinished(storyId)) {
            btnDownload.setSelected(true);
//        } else if (((DownloadManager) ServiceProxyFactory.getProxy().getService(ServiceProxyName.DOWNLOAD_MANAGER)).isStoryDownloadFinished(storyId)) {
//            btnDownload.setSelected(true);
        } else {
            btnDownload.setSelected(false);
        }

        //收藏标志
        if (((FavoriteService) ServiceProxyFactory.getProxy().getService(ServiceProxyName.FAVORITE_SERVICE)).isContainStoryId(storyId)) {
            btnCollect.setSelected(true);
        } else {
            btnCollect.setSelected(false);
        }
    }

    private void showStoryTitle(StoryInfo storyInfo) {
        if (null != storyTitle) {
            storyTitle.setVisibility(View.GONE);
        }
        if (null != storyInfo) {
            if (storyTitle != null && storyTitle.getVisibility() == View.GONE &&
                    !TextUtils.isEmpty(storyInfo.getName())) {
                storyTitle.setVisibility(View.VISIBLE);
                storyTitle.setText(storyInfo.getName());
            }
        }
    }

    private void hideStoryTitle() {
        if (null != storyTitle) {
            storyTitle.setVisibility(View.GONE);
        }
    }

    void stopPlay() {
        getPlaybackHandler().removeCallbacks(mSwitchToNextBookRunnable);
        getPlaybackHandler().removeCallbacksAndMessages(null);

        try {
            Context context = KaDaApplication.getInstance();
            Intent listenIntent = new Intent(context, ListenService2.class);
            listenIntent.setAction(ListenService2.INTENT_ACTION);
            listenIntent.putExtra(ListenService2.INTENT_PARAM_TYPE, ListenService2.Types.PAUSE);
            listenIntent.putExtra(ListenService2.INTENT_PARAM_NEED_COMMIT, true);
            listenIntent.putExtra(ListenService2.INTENT_PARAM_NOTIFICATION_INFO, nowPlayStoryInfo);
            KaDaApplication.getInstance().startService(listenIntent);
        } catch (Throwable e) {
            LogHelper.printStackTrace(e);
        }
    }

    public SafeHandler mPlaybackHandler;

    public SafeHandler getPlaybackHandler() {
        if (mPlaybackHandler == null) {
            mPlaybackHandler = new SafeHandler();
        }
        return mPlaybackHandler;
    }

    private Runnable mSwitchToNextBookRunnable = new Runnable() {
        @Override
        public void run() {
            switchToNextBook();
        }
    };

    void startViewpagerAnim(int position) {
        stopViewpagerAnim();
        for (int index = 0; index < mViewPager.getChildCount(); index++) {
            View view = mViewPager.getChildAt(index);
//            View playBtn = view.findViewById(R.id.play_cover);

            View cover = view.findViewById(R.id.cover_bg_layout);
            if (position == (Integer) view.getTag(R.id.listen_book_index)) {
                rotateAnimator = ObjectAnimator.ofFloat(cover, "rotation", 0, 360);
                rotateAnimator.setRepeatCount(-1);
                rotateAnimator.setDuration(30000);
                rotateAnimator.setInterpolator(new LinearInterpolator());
                rotateAnimator.start();
                break;
            }
        }
    }

    void stopViewpagerAnim() {
        if (rotateAnimator != null) {
            if (rotateAnimator.isRunning()) {
                rotateAnimator.cancel();
            } else {
                rotateAnimator.end();
            }
            rotateAnimator = null;
        }
    }

    void switchToNextBook() {
        getHandler().removeCallbacks(mSwitchToNextBookRunnable);
        getPlaybackHandler().removeCallbacksAndMessages(null);

        if (mViewpagerAdapter != null && playIndex + 1 < mViewpagerAdapter.getCount()) {
            StoryInfo info = storyList.get(playIndex + 1);

            // 限免逻辑处理
            if (isLimitFreeCollection(mCollectionExtFlag)) {
                playIndex = playIndex + 1;
                mViewPager.setCurrentItem(playIndex, true);
                initTime();
                mListenPosition = 0;
                startViewpagerAnim(playIndex);
                UserHabitService.getInstance().trackHabit(UserHabitService.newUserHabit("", "story_auto_next_play", TimeUtil.currentTime()));
                return;
            }
            //下一本为收费就停止播放
            if (info.getType() == StoryInfo.TYPE_STORY && info.getCollectionId() != 0 && info.getSubscribe() != 1 && (info.getExtFlag() & Extflag.STORY_EXT_FLAG_256) != Extflag.STORY_EXT_FLAG_256) {
                stopViewpagerAnim();
                btnPlay.setSelected(false);
                setListAnimMode(nowPlayStoryInfo, PAUSE_MODE);
            } else {
                playIndex = playIndex + 1;
                mViewPager.setCurrentItem(playIndex, true);
                initTime();
                mListenPosition = 0;
                startViewpagerAnim(playIndex);
                UserHabitService.getInstance().trackHabit(UserHabitService.newUserHabit("", "story_auto_next_play", TimeUtil.currentTime()));
            }
        } else {
            stopViewpagerAnim();
            btnPlay.setSelected(false);
            setListAnimMode(nowPlayStoryInfo, PAUSE_MODE);
        }
    }

    void pauseImpl() {
        Context context = KaDaApplication.getInstance();
        Intent listenIntent = new Intent(context, ListenService2.class);
        listenIntent.setAction(ListenService2.INTENT_ACTION);
        listenIntent.putExtra(ListenService2.INTENT_PARAM_TYPE, ListenService2.Types.PAUSE);
        listenIntent.putExtra(ListenService2.INTENT_PARAM_NEED_COMMIT, false);
        listenIntent.putExtra(ListenService2.INTENT_PARAM_NOTIFICATION_INFO, nowPlayStoryInfo);
        KaDaApplication.getInstance().startService(listenIntent);
    }

    public static final int STORY_TIMER = 8080;

    @Override
    public boolean handleMessage(Message message) {
        if (message.what == STORY_TIMER) {
            getHandler().removeMessages(STORY_TIMER);
            long time = ListenManager.getInstance().getNowTime();
            nowTime.setText(TimeUtil.formatLongToTimeStr(time));
            mSeekBar.setProgress((int) time);
            Message msg = getHandler().obtainMessage();
            msg.what = STORY_TIMER;
            getHandler().sendMessageDelayed(msg, 1000);
        }
        return false;
    }

    ListenManager.Listener uiListener = new ListenManager.Listener() {

        @Override
        public void handleLoadding() {
            loadingContainer.setVisibility(View.VISIBLE);
        }

        @Override
        public void handleStartPlaying() {
            if (nowPlayStoryInfo != null) {
                if (nowPlayStoryInfo.getCollectionId() == 0
                        && mAlbumRecyclerView.getVisibility() == View.VISIBLE) {
                    mAlbumRecyclerView.setVisibility(View.GONE);
                    resetAnim(200);
                } else if (nowPlayStoryInfo.getCollectionId() != 0
                        && albumstoryList != null && albumstoryList.size() > 0
                        && mAlbumRecyclerView.getVisibility() == View.GONE) {
                    startAnim(0, 200);
                }
            }

//            播放
            loadingContainer.setVisibility(View.GONE);
            showStoryTitle(nowPlayStoryInfo);
            btnPlay.setSelected(true);
            mSeekBar.setEnabled(true);
            LogHelper.d("timeChanged", "STORY_TIMER");
            getHandler().removeMessages(STORY_TIMER);
            Message msg = getHandler().obtainMessage();
            msg.what = STORY_TIMER;
            getHandler().sendMessageDelayed(msg, 1000);
        }

        @Override
        public void handlePaused() {
            showStoryTitle(nowPlayStoryInfo);
            //暂停
            LogHelper.d("timeChanged", "handlePaused");
//            getHandler().removeMessages(STORY_TIMER);
        }

        @Override
        public void handleCompletion() {
            //这一段播放完成了
            LogHelper.d("timeChanged", "handleCompletion");
            getHandler().removeMessages(STORY_TIMER);
            getPlaybackHandler().post(mSwitchToNextBookRunnable);
        }

        @Override
        public void handleNetworkError() {
            getPlaybackHandler().removeCallbacksAndMessages(null);

            loadingContainer.setVisibility(View.GONE);
            showStoryTitle(nowPlayStoryInfo);
            stopListen();
        }

        @Override
        public void handleError() {
            getPlaybackHandler().removeCallbacksAndMessages(null);

            loadingContainer.setVisibility(View.GONE);
            showStoryTitle(nowPlayStoryInfo);
            stopListen();
        }
    };

    // IGrantMedal
    public void onEvent(MedalGrantedEvent event) {
        //听书播放界面可以播放勋章
        doCheckIfNeedDisplayMedal();
    }

    @Override
    public void onResume() {
        super.onResume();
        //听书播放界面可以播放勋章
        doCheckIfNeedDisplayMedal();
    }

    void doCheckIfNeedDisplayMedal() {
        if (((AuthService) ServiceProxyFactory.getProxy().getService(ServiceProxyName.AUTH_SERVICE)).isAuthorized()) {
            getHandler().removeCallbacks(mDisplayMedalRunnable);
            getHandler().postDelayed(mDisplayMedalRunnable, 2000);
        }
    }

    private Runnable mDisplayMedalRunnable = new Runnable() {
        @Override
        public void run() {
            if (isVisible()) {
                Medal medalInfo = ((MedalManager) ServiceProxyFactory.getProxy().getService(ServiceProxyName.MEDAL_MANAGER)).fetchOneNewGrantedMealForDisplay();
                if (medalInfo != null) {
                    String imageUrl = medalInfo.isReceive() ? medalInfo.getGainImg() : medalInfo.getUnGainImg();
                    MedalDialog mMedalDialog = new MedalDialog(ListenActivity.this, medalInfo, (int) medalInfo.getMedalId(), CdnUtils.getImgCdnUrl(imageUrl), medalInfo.getName(), true, true);
//                    mMedalDialog.show();
                    //获得勋章 只在界面没有dialog展示时显示 否则不加入dialog显示列表
//                    if (!((DialogManager) ServiceProxyFactory.getProxy().getService(ServiceProxyName.DIALOG_MANAGER)).isDialogShow()) {
                    ((DialogManager) ServiceProxyFactory.getProxy().getService(ServiceProxyName.DIALOG_MANAGER)).showDialog(mMedalDialog);
//                    }
                }
            }
        }
    };

    //合集列表下载成功 刷新播放列表
    public void onEvent(OnRefreshViewEvent event) {
        LogHelper.d("ListenService", "OnRefreshViewEvent");
//        if (!event.getList().contains(storyList.get(playIndex))) {
        if (storyList.get(playIndex).getType() == StoryInfo.TYPE_COLLECTION) {
            storyList.clear();
            storyList.addAll(event.getList());
            playIndex = event.getPlayIndex();

            storys.clear();
            storys.addAll(event.getStorys());

            mViewpagerAdapter.clear();
            mViewpagerAdapter.setStoryList(storyList);
            mViewpagerAdapter.notifyDataSetChanged();

            mNextPlayAdapter.notifyDataSetChanged();

            while (true) {
                if (storyList.get(playIndex) == null) {
                    playIndex += 1;
                } else {
                    break;
                }
            }
            mViewPager.setCurrentItem(playIndex, false);
            if (!ListenManager.getInstance().isPause()) {
                startViewpagerAnim(playIndex);
            }
            Long time = storyList.get(playIndex).getTime();
            mSeekBar.setMax(Integer.valueOf(time.toString()));
            allTime.setText(TimeUtil.formatLongToTimeStr(time));
        }
    }


    public void onEvent(StoryService.StartReadingEvent event) {

        if (event == null || event.getStoryInfo() == null) {
            return;
        }
        nowPlayStoryInfo = event.getStoryInfo();

        //下载标志、收藏标志
        showDownloadAndFavoriteStatus((int) nowPlayStoryInfo.getId());

        int index = -1;
        if (storys.contains(nowPlayStoryInfo)) {
            index = storys.indexOf(nowPlayStoryInfo);
            scolltoIndex(index);
            if (index != storyIndex) {
                storyIndex = index;
                if (mAlbumRecyclerView.getVisibility() == View.VISIBLE) {
                    resetAnim(200);
                    mAlbumRecyclerView.setVisibility(View.GONE);
                }
            }
        } else {
            for (int i = 0; i < storys.size(); i++) {
                if (storys.get(i).getCollectionId() == nowPlayStoryInfo.getCollectionId()) {
                    index = i;
                    scolltoIndex(index);
                    break;
                }
            }

            if (mAlbumRecyclerView.getVisibility() != View.VISIBLE) {
                startAnim(index, 200);
            }
            if (index == storyIndex && albumstoryList != null && albumstoryList.size() > 0) {
                for (int i = 0; i < albumstoryList.size(); i++) {
                    if (nowPlayStoryInfo.getId() == albumstoryList.get(i).getId()) {
                        scolltoCollectIndex(i);
                    }
                }
            } else if (index != -1 && storys.get(index).getType() == StoryInfo.TYPE_COLLECTION) {
                loadCollectionData(index, new MyCallback() {
                    @Override
                    public void playAfterLoad() {
                        if (albumstoryList != null) {
                            for (int i = 0; i < albumstoryList.size(); i++) {
                                if (nowPlayStoryInfo.equals(albumstoryList.get(i))) {
                                    scolltoCollectIndex(i);
                                }
                            }
                        }
                    }
                });
                storyIndex = index;
            }
        }


        getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                setListAnimMode(nowPlayStoryInfo, PLAY_MODE);
            }
        }, 300);

        btnPlay.setSelected(true);

    }

    public void onEvent(StopEvent event) {
        stopListen();
        finish();
    }

    public void onEvent(PauseEvent event) {
        if (btnPlay.isSelected()) {
            stopListen();

            if (event.isLockPause) {
                UserHabitService.getInstance().trackHabit(UserHabitService.newUserHabit("", StaCtrName.story_play_stop_by_lock, TimeUtil.currentTime()));
            }
        }
    }

    /**
     * 播放小朋友，需要妈妈订阅哦音频
     */
    public void playRawMedia() {

        ListenManager.getInstance().setShortAudioOnStoppedContinueListen(false);

        if (ListenManager.getInstance().isPlaying() || ListenManager.getInstance().isBeginAudioPlaying() || ListenManager.getInstance().isEndAudioPlaying()) {
            getPlaybackHandler().removeCallbacksAndMessages(null);
            stopListen();
            needContinuePlay = true;
        }

        mShortMediaPlayer.addPlayQueue(R.raw.need_mom_subscribe, PlayMode.IMMEDIATELY_PLAY_MODE, AudioName.NEET_MOM_SUBSCRIBE_AUDIO);
    }

    /**
     * 更新所有播放列表（包括storys,storyList,albumStoryList）
     *
     * @param data
     */
    private void updatePlayList(StoryCollectionDetail data) {
        ListenManager.getInstance().setFreeListen(false);
        int subscribe = data.getSubscribe();
        int collectId = data.getCollectId();
        int storysSize = storys.size();
        for (int i = 0; i < storysSize; i++) {
            StoryInfo storyInfo = storys.get(i);
            if (storyInfo.getCollectionId() == collectId) {
                storyInfo.setSubscribe(subscribe);
                mNextPlayAdapter.notifyDataSetChanged();
            }
        }
        int albumSize = albumstoryList.size();
        for (int i = 0; i < albumSize; i++) {
            StoryInfo storyInfo = albumstoryList.get(i);
            if (storyInfo.getCollectionId() == collectId) {
                storyInfo.setSubscribe(subscribe);
                mAlbumPlayAdapter.notifyDataSetChanged();
            }
        }
        int storyListSize = storyList.size();
        for (int i = 0; i < storyListSize; i++) {
            StoryInfo storyInfo = storyList.get(i);
            if (storyInfo.getCollectionId() == collectId) {
                storyInfo.setSubscribe(subscribe);
            }
        }
    }

    public void onEvent(ListenActivityAudioTimeEvent event) {
        if (nowPlayStoryInfo.getTime() == 0) {
            nowPlayStoryInfo.setTime(event.getAllTime());
            mSeekBar.setMax((int) event.getAllTime());
            allTime.setText(TimeUtil.formatLongToTimeStr(event.getAllTime()));
        }
    }

    public void onEvent(SubscribeSuccessEvent event) {
        StoryService.loadCollection((int) nowPlayStoryInfo.getCollectionId(), nowPlayStoryInfo.getCollectType(), new DefaultCallback<StoryCollectionDetail>() {
            @Override
            public void onDataReceived(StoryCollectionDetail data) {
                if (data != null && data.getItems() != null && data.getItems().size() > 0) {
                    updatePlayList(data);
                }
            }
        });
    }

    //

    /**
     * todo 播放页限免结束处理
     * 1. 当前正在播放的Story，可继续播放完
     * 2. 非正在播放的Story，恢复成之前的状态，跟新UI的显示
     * 3. 更新ListenManager中相关字段的信息
     */
    public void onEvent(StoryLimitFreeEndedEvent event) {
        if (nowPlayStoryInfo != null &&
                nowPlayStoryInfo.getCollectionId() == event.getStoryCollectionId()) {
            int flag = (int) (ListenManager.getInstance().getCollectionExtFlag()
                    ^ Extflag.STORY_FLAG_FREE_ALL
                    ^ Extflag.STORY_FLAG_FREE_NEW);
            mCollectionExtFlag = flag;

            ListenManager.getInstance().setCollectionExtFlag(flag);
            // 更新UI，如果有正在播放的内容，为避免同时显示播放的View和LockView
        }
    }

    public void onEvent(final LoginEvent event) {
        if (nowPlayStoryInfo.getCollectionId() > 0) {
            getPlaybackHandler().removeCallbacksAndMessages(null);
            stopListen();
            StoryService.loadCollection((int) nowPlayStoryInfo.getCollectionId(), nowPlayStoryInfo.getCollectType(), new DefaultCallback<StoryCollectionDetail>() {
                @Override
                public void onDataReceived(StoryCollectionDetail data) {
                    if (data != null && data.getItems() != null && data.getItems().size() > 0) {
                        if (data.getSubscribe() == 1) {
                            updatePlayList(data);
                        } else {
                            //如果是未订阅状态，根据操作类型进行对应的操作
                            if (SUBSCRIBE.equals(event.getType())) {
                                if (subscribeController != null) {
                                    subscribeController.subscribe(1);
                                }
                            } else if (CREATE_ORDER.equals(event.getType())) {
                                if (subscribeController != null) {
                                    subscribeController.createOrder();
                                }
                            }
                        }

                    }
                }
            });
        }
    }

    /**
     * 停止播放
     */
    private void stopListen() {
        getHandler().removeMessages(STORY_TIMER);
        btnPlay.setSelected(false);
        if (storyList != null && storyList.size() > 0 && playIndex >= 0 && playIndex < storyList.size()) {
            EventBus.getDefault().post(new StoryService.PauseReadingEvent(storyList.get(playIndex), ListenManager.getInstance().getCurrentPosition(), ListenManager.getInstance().getSessionId()));
        }
        pauseImpl();
        stopViewpagerAnim();
        setListAnimMode(nowPlayStoryInfo, PAUSE_MODE);

        mListenPosition = ListenManager.getInstance().getCurrentPosition();
    }

    private void setListAnimMode(StoryInfo storyInfo, int playMode) {
        if (storys == null) {
            return;
        }

        int size = storys.size();

        for (int i = 0; i < mNextPlayRecyclerView.getChildCount(); i++) {
            View childView = mNextPlayRecyclerView.getChildAt(i);
            int position = mNextPlayRecyclerView.getChildAdapterPosition(childView);
            if (position < 0 || position >= size) {
                continue;
            }

            if (storys.get(position).equals(storyInfo) ||
                    (mAlbumRecyclerView.getVisibility() == View.VISIBLE && mAlbumPlayAdapter.getItemCount() > 0 && storyInfo.getCollectionId() != 0 && storys.get(position).getCollectionId() == storyInfo.getCollectionId())) {
                NextPlayerViewHolder viewHolder = (NextPlayerViewHolder) mNextPlayRecyclerView.getChildViewHolder(childView);
                viewHolder.setMode(playMode);
            } else {
                NextPlayerViewHolder viewHolder = (NextPlayerViewHolder) mNextPlayRecyclerView.getChildViewHolder(childView);
                viewHolder.setMode(STOP_MODE);
            }
        }

        int count = mAlbumRecyclerView.getChildCount();
        for (int i = 0; i < count; i++) {
            View childView = mAlbumRecyclerView.getChildAt(i);
            int pos = mAlbumRecyclerView.getChildAdapterPosition(childView);
            if (pos < 0 || pos >= albumstoryList.size()) {
                continue;
            }

            if (albumstoryList.get(pos).equals(storyInfo)) {
                AlbumPlayerViewHolder viewHolder = (AlbumPlayerViewHolder) mAlbumRecyclerView.getChildViewHolder(childView);
                viewHolder.setMode(playMode);
            } else {
                AlbumPlayerViewHolder viewHolder = (AlbumPlayerViewHolder) mAlbumRecyclerView.getChildViewHolder(childView);
                viewHolder.setMode(STOP_MODE);
            }
        }
    }

    public static class OnRefreshViewEvent {
        List<StoryInfo> list;
        List<StoryInfo> storys;
        int playIndex;

        public OnRefreshViewEvent(List<StoryInfo> list, List<StoryInfo> storys, int playIndex) {
            this.list = list;
            this.playIndex = playIndex;
            this.storys = storys;
        }

        public int getPlayIndex() {
            return playIndex;
        }

        public List<StoryInfo> getList() {
            return list;
        }

        public List<StoryInfo> getStorys() {
            return storys;
        }
    }

    public static class StopEvent {
    }


    //合集收回动画
    void resetAnim(long duration) {
        if (null != animator1 && animator1.isRunning()) {
            animator1.cancel();
        }
        final int originalheight = mBottomContainer.getMeasuredHeight();
        final FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) mBottomContainer.getLayoutParams();
        lp.height = originalheight;
        lp.gravity = Gravity.BOTTOM;
        mBottomContainer.setLayoutParams(lp);
        animator1 = ObjectAnimator.ofFloat(0.0F, 1.0F).setDuration(duration);
        animator1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float cVal = (Float) animation.getAnimatedValue();
                lp.height = (int) (originalheight - mAlbumPlayItemSize * cVal);
                mBottomContainer.setLayoutParams(lp);
            }
        });
        animator1.start();
    }

    //合集展开动画
    void startAnim(int position, long duration) {
        if (null != animator2 && animator2.isRunning()) {
            animator2.cancel();
        }
        final int originalheight = LocalDisplay.dp2px(170) + mNextPlayItemSize;
        animator2 = ObjectAnimator.ofFloat(0.0F, 1.0F).setDuration(duration);
        final FrameLayout.LayoutParams lp1 = (FrameLayout.LayoutParams) mBottomContainer.getLayoutParams();
        animator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float cVal = (Float) animation.getAnimatedValue();
                lp1.height = (int) (originalheight + mAlbumPlayItemSize * cVal);
                mBottomContainer.setLayoutParams(lp1);
                if (cVal == 1) {
                    mAlbumRecyclerView.setVisibility(View.VISIBLE);
                }
            }
        });
        animator2.start();
    }

    @Override
    public void finish() {
        tryStopAudio();
        super.finish();
    }

    @Override
    protected void onDestroy() {
        if (animator1 != null) {
            if (animator1.isRunning()) {
                animator1.cancel();
            }
            animator1 = null;
        }
        if (animator2 != null) {
            if (animator2.isRunning()) {
                animator2.cancel();
            }
            animator2 = null;
        }

        if (mShortMediaPlayer != null) {
            mShortMediaPlayer.removeOnPlayListener(mOnPlayListener);
        }

        stopViewpagerAnim();

        if (mViewpagerAdapter != null) {
            mViewpagerAdapter.recycle();
        }

        ListenManager.getInstance().removeCallback(uiListener);
        DialogFactory.dismissAllDialog(this);

        if (bgList != null) {
            bgList.clear();
        }

        EventBus.getDefault().unregister(this);

        UserHabitService.getInstance().track(UserHabitService.newUserHabit("", "exitstory", TimeUtil.currentTime()));

//        tryStopAudio();

        ((DownloadManager) ServiceProxyFactory.getProxy().getService(ServiceProxyName.DOWNLOAD_MANAGER)).removeListener(mDownloadListener);
        super.onDestroy();

        if (mViewPager != null) {
            mViewPager.removeOnPageChangeListener(mOnPageChangeListener);
        }
    }

    private void tryStopAudio() {
        if (ListenManager.getInstance().isPause()) {
            EventBus.getDefault().post(new MainActivityController.OnHideFloatingWindowEvent());
//            ListenManager.getInstance().stop();

            try {
                Context context = KaDaApplication.getInstance();
                Intent listenIntent = new Intent(context, ListenService2.class);
                listenIntent.setAction(ListenService2.INTENT_ACTION);
                listenIntent.putExtra(ListenService2.INTENT_PARAM_TYPE, ListenService2.Types.STOP);
                listenIntent.putExtra(ListenService2.INTENT_PARAM_REMOVE_NOTIFICATION, true);
                KaDaApplication.getInstance().startService(listenIntent);
            } catch (Throwable e) {
                LogHelper.printStackTrace(e);
            }

            ListenManager.getInstance().setCollectId(0);
        } else {
            EventBus.getDefault().post(new MainActivityController.OnShowFloatingWindowEvent());
        }
    }


    class NextPlayAdapter extends RecyclerView.Adapter<ListenActivity.NextPlayerViewHolder> {


        @Override
        public ListenActivity.NextPlayerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            ListenActivity.NextPlayerViewHolder holder = new ListenActivity.NextPlayerViewHolder(LayoutInflater.from(ListenActivity.this).inflate(R.layout.view_holder_next_play, parent,
                    false));

            return holder;
        }

        @Override
        public void onBindViewHolder(final ListenActivity.NextPlayerViewHolder holder, final int position) {

            StoryInfo info = storys.get(position);
            holder.layout.getLayoutParams().width = mNextPlayItemSize;
            holder.layout.getLayoutParams().height = mNextPlayItemSize;

            if (info.getType() == StoryInfo.TYPE_COLLECTION) {
                holder.customStoryView.showCollectionBg(R.drawable.bg_story_collect);
            } else {
                holder.customStoryView.hideCollectionBg();
            }
            String coverUrl = info.getCoverUrl();
            boolean needResetImageUrl = true;
            if (holder.customStoryView.getTag(R.id.list_item_image_url) != null) {
                String url = (String) holder.customStoryView.getTag(R.id.list_item_image_url);
                if (TextUtils.equals(url, coverUrl)) {
                    needResetImageUrl = false;
                }
            }
            if (needResetImageUrl) {
                holder.customStoryView.setTag(R.id.list_item_image_url, coverUrl);
                holder.customStoryView.showUrl(coverUrl, mNextPlayItemSize, mNextPlayItemSize);
            }

            //属于合集的单本 未订阅并且非试听 显示锁 added 且不处于限免状态
            if (isLimitFreeCollection(mCollectionExtFlag)) {
                holder.lockView.setVisibility(View.GONE);
            } else if (info.getType() == StoryInfo.TYPE_STORY && info.getCollectionId() != 0
                    && info.getSubscribe() != 1
                    && (info.getExtFlag() & Extflag.STORY_EXT_FLAG_256) != Extflag.STORY_EXT_FLAG_256) {
                holder.lockView.setVisibility(View.VISIBLE);
            } else {
                holder.lockView.setVisibility(View.GONE);
            }

            if ((nowPlayStoryInfo != null && nowPlayStoryInfo.equals(info))
                    || storys.get(storyIndex).equals(info)) {
                holder.setMode(PLAY_MODE);
                holder.lockView.setVisibility(View.GONE);
            } else {
                holder.setMode(STOP_MODE);
            }


            holder.customStoryView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (storys.size() > 0) {
                        UserHabitService.getInstance().track(UserHabitService.newUserHabit(storys.get(position).getId() + "", "clickstoryinplaylist", TimeUtil.currentTime()));
                        final int visibiliy = mAlbumRecyclerView.getVisibility();
                        //点击同一本 暂停就继续播放
                        if (storyIndex == position) {
                            if (ListenManager.getInstance().isPause()) {
                                playAtIndex(playIndex, mListenPosition, false);
                            }
                        } else {
                            if (storys.get(position).getType() == StoryInfo.TYPE_COLLECTION) {
                                stopPlay();
                                loadCollectionData(position, new MyCallback() {
                                    @Override
                                    public void playAfterLoad() {
                                        //合集列表数量等于0时切到下一本
                                        if (albumstoryList.size() > 0) {
                                            if (visibiliy != View.VISIBLE) {
                                                startAnim(position, 200);
                                            }
                                            if (storyList.containsAll(albumstoryList)) {
                                                pauseImpl();
                                                playAtIndex(storyList.indexOf(albumstoryList.get(0)), 0, false);
                                            } else {
                                                if (storyList.contains(storys.get(position))) {
                                                    int index = storyList.indexOf(storys.get(position));
                                                    storyList.remove(index);
                                                    storyList.addAll(index, albumstoryList);
                                                    ListenManager.getInstance().setStoryList(storyList);
                                                    mViewpagerAdapter.clear();
                                                    mViewpagerAdapter.setStoryList(storyList);
                                                    mViewpagerAdapter.notifyDataSetChanged();
                                                    pauseImpl();
                                                    playAtIndex(index, 0, false);
                                                }
                                            }
                                            storyIndex = position;
                                        } else {
                                            playAtIndex(storyList.indexOf(storys.get(position + 1)), 0, false);
                                        }
                                    }
                                });
                            } else {
                                StoryInfo storyInfo = storys.get(position);
                                if (storyList.contains(storyInfo)) {
                                    // 限免直接截断
                                    if (isLimitFreeCollection(mCollectionExtFlag)) {
                                        stopPlay();
                                        storyIndex = position;
                                        pauseImpl();
                                        playAtIndex(storyList.indexOf(storyInfo), 0, false);
                                        if (visibiliy == View.VISIBLE) {
                                            resetAnim(200);
                                            mAlbumRecyclerView.setVisibility(View.GONE);
                                        }
                                        return;
                                    }
                                    if (storyInfo.getType() == StoryInfo.TYPE_STORY && storyInfo.getCollectionId() != 0 && storyInfo.getSubscribe() != 1 && (storyInfo.getExtFlag() & Extflag.STORY_EXT_FLAG_256) != Extflag.STORY_EXT_FLAG_256) {
                                        toggleSubscribeView(true);
                                    } else {
                                        stopPlay();
                                        storyIndex = position;
                                        pauseImpl();
                                        playAtIndex(storyList.indexOf(storyInfo), 0, false);
                                        if (visibiliy == View.VISIBLE) {
                                            resetAnim(200);
                                            mAlbumRecyclerView.setVisibility(View.GONE);
                                        }
                                    }
                                }
                            }
                        }

                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            if (storys != null && storys.size() > 0) {
                return storys.size();
            }
            return 0;
        }

    }

    class NextPlayerViewHolder extends RecyclerView.ViewHolder {

        FrameLayout layout;
        CustomStoryView customStoryView;
        View lockView;

        public NextPlayerViewHolder(View view) {
            super(view);
            layout = (FrameLayout) view.findViewById(R.id.layout);
            customStoryView = (CustomStoryView) view.findViewById(R.id.cover_container);
            lockView = view.findViewById(R.id.alpha_container);
        }

        public void setMode(int mode) {
            if (mode == PLAY_MODE) {
                layout.setScaleX(1.0f);
                layout.setScaleY(1.0f);
            } else if (mode == STOP_MODE) {
                if (layout.getScaleX() != 0.9f && layout.getScaleY() != 0.9f) {
                    layout.setScaleX(0.9f);
                    layout.setScaleY(0.9f);
                }
            }
            customStoryView.setMode(mode, mNextPlayItemSize / 2,
                    mNextPlayItemSize / 2);
        }
    }


    class AlbumPlayAdapter extends RecyclerView.Adapter<ListenActivity.AlbumPlayerViewHolder> {
        @Override
        public ListenActivity.AlbumPlayerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            ListenActivity.AlbumPlayerViewHolder holder = new ListenActivity.AlbumPlayerViewHolder(LayoutInflater.from(ListenActivity.this).inflate(R.layout.view_holder_album_play, parent,
                    false));

            return holder;
        }

        @Override
        public void onBindViewHolder(ListenActivity.AlbumPlayerViewHolder holder, final int position) {

            StoryInfo info = albumstoryList.get(position);
            holder.layout.getLayoutParams().width = mAlbumPlayItemSize;
            holder.layout.getLayoutParams().height = mAlbumPlayItemSize;
            String coverUrl = info.getCoverUrl();
            boolean needResetImageUrl = true;
            if (holder.customStoryView.getTag(R.id.list_item_image_url) != null) {
                String url = (String) holder.customStoryView.getTag(R.id.list_item_image_url);
                if (TextUtils.equals(url, coverUrl)) {
                    needResetImageUrl = false;
                }
            }
            if (needResetImageUrl) {
                holder.customStoryView.setTag(R.id.list_item_image_url, coverUrl);
                holder.customStoryView.showUrl(coverUrl, mAlbumPlayItemSize - LocalDisplay.dp2px(6), mAlbumPlayItemSize - LocalDisplay.dp2px(6));
            }

            holder.customStoryView.hideBgNoMargin();

            // 限免不显示锁
            if (isLimitFreeCollection(mCollectionExtFlag)) {
                holder.lockView.setVisibility(View.GONE);
            } else if (info.getSubscribe() == 1 || (info.getExtFlag() & Extflag.STORY_EXT_FLAG_256) == Extflag.STORY_EXT_FLAG_256) {
                // 已订阅 或 免费绘本 不显示加锁
                holder.lockView.setVisibility(View.GONE);
            } else {
                holder.lockView.setVisibility(View.VISIBLE);
            }

            if (nowPlayStoryInfo != null && nowPlayStoryInfo.equals(info)) {
                holder.setMode(PLAY_MODE);
                holder.lockView.setVisibility(View.GONE);
            } else {
                holder.setMode(STOP_MODE);
            }
            holder.customStoryView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (albumstoryList != null) {
                        StoryInfo storyInfo = albumstoryList.get(position);
                        UserHabitService.getInstance().track(UserHabitService.newUserHabit(storyInfo.getId() + "", "clickstoryinplaylist", TimeUtil.currentTime()));
                        //点击同一本
                        if (storyList.get(playIndex).equals(storyInfo)) {
                            if (ListenManager.getInstance().isPause()) {
                                playAtIndex(playIndex, mListenPosition, false);
                            }
                        } else {
                            if (isLimitFreeCollection(mCollectionExtFlag)) {
                                stopPlay();
                                pauseImpl();
                                playAtIndex(storyList.indexOf(storyInfo), 0, false);
                                return;
                            }
                            if (storyInfo.getType() == StoryInfo.TYPE_STORY && storyInfo.getCollectionId() != 0 && storyInfo.getSubscribe() != 1 && (storyInfo.getExtFlag() & Extflag.STORY_EXT_FLAG_256) != Extflag.STORY_EXT_FLAG_256) {
                                toggleSubscribeView(true);
                            } else {
                                stopPlay();
                                pauseImpl();
                                playAtIndex(storyList.indexOf(storyInfo), 0, false);
                            }
                        }
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            if (albumstoryList != null && albumstoryList.size() > 0) {
                return albumstoryList.size();
            }
            return 0;
        }

    }

    class AlbumPlayerViewHolder extends RecyclerView.ViewHolder {

        FrameLayout layout;
        CustomStoryView customStoryView;
        View lockView;

        public AlbumPlayerViewHolder(View view) {
            super(view);
            layout = (FrameLayout) view.findViewById(R.id.layout);
            customStoryView = (CustomStoryView) view.findViewById(R.id.cover_container);
            lockView = view.findViewById(R.id.alpha_container);
        }

        public void setMode(int mode) {
            customStoryView.setMode(mode, mAlbumPlayItemSize / 2, mAlbumPlayItemSize / 2);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PermissionManager.PERMISSION_WRITE_EXTERNAL_STORAGE) {
            if (grantResults != null && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (MemorySizeUtils.getAvailableMemoryByMb() >= 200) {
                    ToastUtils.showToast("已加入下载队列", Gravity.CENTER);
                    com.hhdd.kada.main.model.StoryInfo storyInfo = new com.hhdd.kada.main.model.StoryInfo(0, nowPlayStoryInfo.getName(), nowPlayStoryInfo.getCoverUrl(), nowPlayStoryInfo.getExtFlag(),
                            (int) nowPlayStoryInfo.getId(), nowPlayStoryInfo.getSoundUrl(), TimeUtil.audioMilliTime(nowPlayStoryInfo.getTime()), nowPlayStoryInfo.getAuthor(), nowPlayStoryInfo.getMinAge(), nowPlayStoryInfo.getMaxAge(), nowPlayStoryInfo.getVersion(), nowPlayStoryInfo.getClickCount());
                    ((DownloadManager) ServiceProxyFactory.getProxy().getService(ServiceProxyName.DOWNLOAD_MANAGER)).download(storyInfo);
                } else {
                    ((PermissionManager) ServiceProxyFactory.getProxy().getService(ServiceProxyName.PERMISSION_MANAGER)).requestPermission(ListenActivity.this, PermissionManager.STORAGE_NOT_ENOUGH);
                }
            } else {
                // Permission Denied
                ToastUtils.showToast("没有权限,无法下载", Gravity.CENTER);
            }
            return;
        }
    }

    //每次进入听书播放页需要请求接口来获得当前内容的status
    void getStoryStatusByIdAndPlay(final int storyId, final int storyListPosition, final long listenPosition, final boolean reset) {
        StoryAPI.getStoryInfoByStoryId(storyId).get(new API.ResponseHandler<List<StoryListItem>>() {
            @Override
            public void onSuccess(final List<StoryListItem> response) {
                if (response != null && response.size() > 0) {
                    StoryListItem storyListItem = response.get(0);
                    final StoryInfo storyInfo = StoryInfo.createInfoByNewStory(storyListItem);
                    if (storyInfo == null || storyList == null || storyList.isEmpty() || storyListPosition >= storyList.size()) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                stopPlay();
                                showContentDialog(2, 2, storyId);
                                mLoadingView.showError();
                            }
                        });

                        return;
                    }

                    // 替换列表中原有的数据
                    StoryInfo si = storyList.get(storyListPosition);
                    si.setStatus(storyInfo.getStatus());
                    si.setName(storyInfo.getName());

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            switchToNextBook();
                            // 开始播放
                            playAtIndex(storyListPosition, listenPosition, reset);
                        }
                    });

                } else {
                    getHandler().post(new Runnable() {
                        @Override
                        public void run() {
                            stopPlay();
                            showContentDialog(2, 2, storyId);
                            mLoadingView.showError();
                        }
                    });
                }
            }

            @Override
            public void onFailure(int code, String message) {
                mLoadingView.showError();
//                ToastUtils.showToast("出现错误，请重新尝试");
            }
        });
    }

    void showContentDialog(int kind, int type, int id) {
        if (isFinishing()) {
            return;
        }
        ContentIsDeletedDialog dialog = new ContentIsDeletedDialog(this);
        dialog.setCallback(new ContentIsDeletedDialog.ContentDialogCallback() {
            @Override
            public void doYes() {
                if (playIndex < storyList.size() - 2) {
                    playAtIndex(playIndex + 1, 0, false);
                } else if (playIndex == storyList.size() - 1) {
                    finish();
                }
            }
        });

        dialog.setData(kind, type, id);
        dialog.show();
    }

    private static class MyShareListener implements ShareProvider.Listener {

        private long mId;

        public MyShareListener(long id) {
            mId = id;
        }

        @Override
        public void onComplete(boolean success, SHARE_MEDIA share_media) {
            if (success) {
                UserHabitService.getInstance().trackHabit(UserHabitService.newUserHabit(mId + "," + share_media.toString() + ",yes", "story_play_page_share", TimeUtil.currentTime()));
            } else {
                UserHabitService.getInstance().trackHabit(UserHabitService.newUserHabit(mId + "," + share_media.toString() + ",no", "story_play_page_share", TimeUtil.currentTime()));
            }
        }
    }
}
