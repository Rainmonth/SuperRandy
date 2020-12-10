package com.rainmonth.player.activity.detail;

import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.rainmonth.common.base.BaseActivity;
import com.rainmonth.common.utils.FileUtils;
import com.rainmonth.common.utils.ImageUtils;
import com.rainmonth.common.utils.PathUtils;
import com.rainmonth.common.utils.PermissionUtils;
import com.rainmonth.common.utils.ToastUtils;
import com.rainmonth.common.utils.constant.PermissionConstants;
import com.rainmonth.common.utils.log.LogUtils;
import com.rainmonth.player.DemoDataFactory;
import com.rainmonth.player.R;
import com.rainmonth.player.VideoManager;
import com.rainmonth.player.builder.GSYVideoOptionBuilder;
import com.rainmonth.player.listener.GSYSampleCallBack;
import com.rainmonth.player.listener.GSYVideoGifSaveListener;
import com.rainmonth.player.listener.GSYVideoShotListener;
import com.rainmonth.player.model.VideoListBean;
import com.rainmonth.player.utils.Debugger;
import com.rainmonth.player.utils.GifCreateHelper;
import com.rainmonth.player.utils.OrientationUtils;
import com.rainmonth.player.video.StandardVideoPlayer;
import com.rainmonth.player.video.base.VideoPlayer;
import com.rainmonth.player.view.ConfigVideoPlayerView;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

/**
 * 带控制的视频播放
 * - 可以控制播放速度
 * - 可以截图
 * - 可以跳转
 * - 可以录制gif
 *
 * @author RandyZhang
 * @date 2020/12/2 6:19 PM
 */
public class ControlDetailVideoPlayerActivity extends BaseDetailVideoPlayerActivity<StandardVideoPlayer> {

    NestedScrollView mNestedScrollView;
    ConfigVideoPlayerView mDetailPlayer;
    Button changeSpeed;
    Button jump;
    Button shot;
    Button startGif;
    Button stopGif;
    View loadingView;

    private String mPlayUrl = "http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f20.mp4";
    //private String mPlayUrl = "http://livecdn1.news.cn/Live_MajorEvent01Phone/manifest.m3u8";
    //private String mPlayUrl = "https://ruigongkao.oss-cn-shenzhen.aliyuncs.com/transcode/video/source/video/8908d124aa839d0d3fa9593855ef5957.m3u8";
    //private String mPlayUrl = "http://ruigongkao.oss-cn-shenzhen.aliyuncs.com/transcode/video/source/video/3aca1a0db8db9418dcbc765848c8903e.m3u8";
    ImageView mCoverImage;
    MediaMetadataRetriever mCoverRetriever;
    private GifCreateHelper mGifCreateHelper;
    private float mSpeed = 1;                         // 倍速播放

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.player_activity_control_detail_player_play;
    }

    @Override
    protected void initViewsAndEvents() {
        mNestedScrollView = findViewById(R.id.post_detail_nested_scroll);
        mDetailPlayer = findViewById(R.id.detail_player);
        changeSpeed = findViewById(R.id.change_speed);
        jump = findViewById(R.id.jump);
        shot = findViewById(R.id.shot);
        startGif = findViewById(R.id.start_gif);
        stopGif = findViewById(R.id.stop_gif);
        loadingView = findViewById(R.id.loadingView);

        changeSpeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onChangeSpeedClick();
            }
        });

        jump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readyGo(ControlDetailVideoPlayerActivity.class);
            }
        });

        shot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onScreenShotClick();
            }
        });

        startGif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onStartGifClick();
            }
        });

        stopGif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onStopGifClick();
            }
        });


        resolveNormalVideoUI();
        initVideoBuilderMode();

        initGifHelper();

//        List<VideoListBean> clarityList = DemoDataFactory.getSwitchClarityPlayList();
//        mDetailPlayer.setUp(clarityList, true, "");
//        if (clarityList != null && clarityList.size() > 0) {
//            loadFirstFrameCover(clarityList.get(0).getUrl());
//        }
    }

    private void onChangeSpeedClick() {
        if (mSpeed == 1) {
            mSpeed = 1.5f;
        } else if (mSpeed == 1.5f) {
            mSpeed = 2f;
        } else if (mSpeed == 2) {
            mSpeed = 0.5f;
        } else if (mSpeed == 0.5f) {
            mSpeed = 0.25f;
        } else if (mSpeed == 0.25f) {
            mSpeed = 1;
        }
        changeSpeed.setText("播放速度：" + mSpeed);
        mDetailPlayer.setSpeedPlaying(mSpeed, true);
    }

    private void onScreenShotClick() {
        PermissionUtils.permission(PermissionConstants.STORAGE).callback(new PermissionUtils.SimpleCallback() {
            @Override
            public void onGranted() {
                mDetailPlayer.taskShotPic(new GSYVideoShotListener() {
                    @Override
                    public void getBitmap(Bitmap bitmap) {
                        File file = new File(PathUtils.getExternalAppScreenShotPath(), "shot_" + System.currentTimeMillis() + ".jpg");
                        ImageUtils.save(bitmap, file, Bitmap.CompressFormat.JPEG);
                        // todo 放在一个异步任务中完成
                        ToastUtils.showLong("截屏成功");
                    }
                });
            }

            @Override
            public void onDenied() {
                ToastUtils.showLong("您拒绝了存储权限，无法保存截图");
            }
        }).request();
    }

    private void onStartGifClick() {
        if (mGifCreateHelper != null) {
            File tempFile = FileUtils.makeDirs(PathUtils.getExternalAppVideoRecordTempPath());
//            if (tempFile != null && !tempFile.exists()) {
//                boolean success = tempFile.mkdir();
//                LogUtils.d(TAG, "success: " + success);
//            }
            mGifCreateHelper.startGif(tempFile);
        }
    }

    private void onStopGifClick() {
        if (mGifCreateHelper != null) {
            loadingView.setVisibility(View.VISIBLE);
            mGifCreateHelper.stopGif(new File(PathUtils.getExternalAppVideoRecordPath(), "gif_" + System.currentTimeMillis() + ".gif"));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mCoverRetriever != null) {
            mCoverRetriever.release();
            mCoverRetriever = null;
        }
        if (mGifCreateHelper != null) {
            mGifCreateHelper.cancelTask();
        }
    }


    @Override
    public StandardVideoPlayer getGSYVideoPlayer() {
        return mDetailPlayer;
    }

    @Override
    public GSYVideoOptionBuilder getGSYVideoOptionBuilder() {
        //内置封面可参考SampleCoverVideo
        mCoverImage = new ImageView(this);
        loadCover(mCoverImage, mPlayUrl);
        return new GSYVideoOptionBuilder()
                .setThumbImageView(mCoverImage)
                .setUrl(mPlayUrl)
                .setCacheWithPlay(true)
                .setVideoTitle(" ")
                .setIsTouchWiget(true)
                .setRotateViewAuto(false)
                .setLockLand(false)
                .setShowFullAnimation(true)//打开动画
                .setNeedLockFull(true)
                .setSeekRatio(1);
    }

    private void loadCover(ImageView imageView, String url) {

        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setImageResource(R.drawable.player_sample_thumb1);

        Glide.with(this.getApplicationContext())
                .setDefaultRequestOptions(
                        new RequestOptions()
                                .frame(3000000)
                                .centerCrop()
                                .error(R.drawable.player_sample_thumb2)
                                .placeholder(R.drawable.player_sample_thumb2))
                .load(url)
                .into(imageView);
    }

    private void resolveNormalVideoUI() {
        mDetailPlayer.getTitleTextView().setVisibility(View.GONE);
        mDetailPlayer.getBackButton().setVisibility(View.GONE);
    }

    private void initGifHelper() {
        mGifCreateHelper = new GifCreateHelper(mDetailPlayer, new GSYVideoGifSaveListener() {
            @Override
            public void process(int curPosition, int total) {
                Debugger.printfLog(" current " + curPosition + " total " + total);
            }

            @Override
            public void result(boolean success, File file) {
                Debugger.printfLog(" success " + success + " file " + file.getAbsolutePath());
                mDetailPlayer.post(new Runnable() {
                    @Override
                    public void run() {
                        loadingView.setVisibility(View.GONE);
                        ToastUtils.showLong("创建成功");
                        mGifCreateHelper.clearTempFiles();
                    }
                });
            }
        });
    }

    @Override
    public void clickForFullScreen() {

    }

    /*******************************竖屏全屏开始************************************/

    @Override
    public void initVideo() {
        super.initVideo();
        //重载后实现点击，不横屏
        if (getGSYVideoPlayer().getFullscreenButton() != null) {
            getGSYVideoPlayer().getFullscreenButton().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //第一个true是否需要隐藏actionbar，第二个true是否需要隐藏statusbar
                    getGSYVideoPlayer().startWindowFullscreen(mContext, true, true);
                }
            });
        }
    }


    /**
     * 是否启动旋转横屏，true表示启动
     *
     * @return true
     */
    @Override
    public boolean getDetailOrientationRotateAuto() {
        return false;
    }

    //重载后关闭重力旋转
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mOrientationUtils.setEnable(false);
    }

    //重载后不做任何事情，实现竖屏全屏
    @Override
    public void onQuitFullscreen(String url, Object... objects) {
        super.onQuitFullscreen(url, objects);
    }

    /*******************************竖屏全屏结束************************************/


    /**
     * 这里只是演示，并不建议直接这么做
     * MediaMetadataRetriever最好做一个独立的管理器
     * 使用缓存
     * 注意资源的开销和异步等
     *
     * @param url 视频播放的地址
     */
    public void loadFirstFrameCover(String url) {

        //原始方法
        /*final MediaMetadataRetriever mediaMetadataRetriever = getMediaMetadataRetriever(url);
        //获取帧图片
        if (getMediaMetadataRetriever(url) != null) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    final Bitmap bitmap = mediaMetadataRetriever
                            .getFrameAtTime(1000, MediaMetadataRetriever.OPTION_CLOSEST);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (bitmap != null && !isRelease) {
                                Debuger.printfLog("time " + System.currentTimeMillis());
                                //显示
                                coverImageView.setImageBitmap(bitmap);
                            }
                        }
                    });
                }
            }).start();
        }*/

        //可以参考Glide，内部也是封装了MediaMetadataRetriever
        Glide.with(this.getApplicationContext())
                .setDefaultRequestOptions(
                        new RequestOptions()
                                .frame(1000000)
                                .centerCrop()
                                .error(R.drawable.player_sample_thumb1)
                                .placeholder(R.drawable.player_sample_thumb1))
                .load(url)
                .into(mCoverImage);
    }

    public MediaMetadataRetriever getMediaMetadataRetriever(String url) {
        if (mCoverRetriever == null) {
            mCoverRetriever = new MediaMetadataRetriever();
        }
        mCoverRetriever.setDataSource(url, new HashMap<String, String>());
        return mCoverRetriever;
    }

    private VideoPlayer getCurrentPlayer() {
        if (mDetailPlayer.getFullWindowPlayer() != null) {
            return mDetailPlayer.getFullWindowPlayer();
        }
        return mDetailPlayer;
    }
}
