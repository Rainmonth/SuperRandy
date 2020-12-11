package com.rainmonth.player.activity.detail;

import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaMetadataRetriever;
import android.opengl.Matrix;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.core.widget.NestedScrollView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.rainmonth.common.utils.FileUtils;
import com.rainmonth.common.utils.ImageUtils;
import com.rainmonth.common.utils.PathUtils;
import com.rainmonth.common.utils.PermissionUtils;
import com.rainmonth.common.utils.ToastUtils;
import com.rainmonth.common.utils.constant.PermissionConstants;
import com.rainmonth.player.R;
import com.rainmonth.player.builder.GSYVideoOptionBuilder;
import com.rainmonth.player.listener.GSYVideoGifSaveListener;
import com.rainmonth.player.listener.GSYVideoShotListener;
import com.rainmonth.player.render.effect.AutoFixEffect;
import com.rainmonth.player.render.effect.BarrelBlurEffect;
import com.rainmonth.player.render.effect.BitmapIconEffect;
import com.rainmonth.player.render.effect.BlackAndWhiteEffect;
import com.rainmonth.player.render.effect.BrightnessEffect;
import com.rainmonth.player.render.effect.ContrastEffect;
import com.rainmonth.player.render.effect.CrossProcessEffect;
import com.rainmonth.player.render.effect.DocumentaryEffect;
import com.rainmonth.player.render.effect.DuotoneEffect;
import com.rainmonth.player.render.effect.FillLightEffect;
import com.rainmonth.player.render.effect.GammaEffect;
import com.rainmonth.player.render.effect.GaussianBlurEffect;
import com.rainmonth.player.render.effect.GrainEffect;
import com.rainmonth.player.render.effect.HueEffect;
import com.rainmonth.player.render.effect.InvertColorsEffect;
import com.rainmonth.player.render.effect.LamoishEffect;
import com.rainmonth.player.render.effect.NoEffect;
import com.rainmonth.player.render.effect.OverlayEffect;
import com.rainmonth.player.render.effect.PixelationEffect;
import com.rainmonth.player.render.effect.PosterizeEffect;
import com.rainmonth.player.render.effect.SampleBlurEffect;
import com.rainmonth.player.render.effect.SaturationEffect;
import com.rainmonth.player.render.effect.SepiaEffect;
import com.rainmonth.player.render.effect.SharpnessEffect;
import com.rainmonth.player.render.effect.TemperatureEffect;
import com.rainmonth.player.render.effect.TintEffect;
import com.rainmonth.player.render.effect.VignetteEffect;
import com.rainmonth.player.render.glrender.GSYVideoGLViewCustomRender;
import com.rainmonth.player.render.view.GSYVideoGLView;
import com.rainmonth.player.utils.Debugger;
import com.rainmonth.player.utils.GSYVideoType;
import com.rainmonth.player.utils.GifCreateHelper;
import com.rainmonth.player.video.StandardVideoPlayer;
import com.rainmonth.player.video.base.VideoPlayer;
import com.rainmonth.player.view.ConfigVideoPlayerView;

import java.io.File;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import static com.rainmonth.common.utils.SizeUtils.dp2px;

/**
 * 带控制的视频播放
 * - 可以控制播放速度
 * - 可以截图
 * - 可以跳转
 * - 可以录制gif
 * - 添加滤镜效果，注意：如果应用滤镜效果，需要使用{@link android.opengl.GLSurfaceView}
 * - 支持水印效果，需要使用{@link android.opengl.GLSurfaceView}
 * - 支持自定义渲染器，需要使用{@link android.opengl.GLSurfaceView}
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
    Button changeFilter;
    Button glAnimation;
    View loadingView;

    private String mPlayUrl = "http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f20.mp4";
    //private String mPlayUrl = "http://livecdn1.news.cn/Live_MajorEvent01Phone/manifest.m3u8";
    //private String mPlayUrl = "https://ruigongkao.oss-cn-shenzhen.aliyuncs.com/transcode/video/source/video/8908d124aa839d0d3fa9593855ef5957.m3u8";
    //private String mPlayUrl = "http://ruigongkao.oss-cn-shenzhen.aliyuncs.com/transcode/video/source/video/3aca1a0db8db9418dcbc765848c8903e.m3u8";
    ImageView mCoverImage;
    MediaMetadataRetriever mCoverRetriever;
    private GifCreateHelper mGifCreateHelper;
    private float mSpeed = 1;                           // 倍速播放
    private int mFilterType = 0;                        // 路径类型
    private final float deep = 0.8f;                    // 某些shader的控制参数
    private int mRenderViewType;                        // 渲染视图类型
    private int percentage = 1;                         // GLSurfaceView 参数
    private int percentageType = 1;                     // GLSurfaceView 对应的动画类型
    private Timer mGlAnimateTimer = new Timer();
    private MatrixRotateTask mGlAnimateTask;
    private WaterMarkAnimTask mWaterMarkTask;
    private boolean mMoveWaterMark = false;
    private BitmapIconEffect mWaterMarkEffect;
    private GSYVideoGLViewCustomRender mCustomRender;

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
        changeFilter = findViewById(R.id.change_filter);
        glAnimation = findViewById(R.id.gl_animation);
        loadingView = findViewById(R.id.loadingView);

        changeSpeed.setOnClickListener(v -> onChangeSpeedClick());

        jump.setOnClickListener(v -> onJumpClick());

        shot.setOnClickListener(v -> onScreenShotClick());

        startGif.setOnClickListener(v -> onStartGifClick());

        stopGif.setOnClickListener(v -> onStopGifClick());

        changeFilter.setOnClickListener(v -> onChangeFilterClick());

        glAnimation.setOnClickListener(v -> onGlAnimationClick());

        mRenderViewType = GSYVideoType.getRenderType();
        GSYVideoType.setRenderType(GSYVideoType.GLSURFACE);

        resolveNormalVideoUI();
        initVideoBuilderMode();
        initGifHelper();

        //自定义render需要在播放器开始播放之前，播放过程中不允许切换render

        //水印图效果
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        mCustomRender = new GSYVideoGLViewCustomRender();
        mWaterMarkEffect = new BitmapIconEffect(bitmap, dp2px(50), dp2px(50), 0.6f);
        mCustomRender.setBitmapEffect(mWaterMarkEffect);
        mDetailPlayer.setCustomGLRenderer(mCustomRender);
        mDetailPlayer.setGLRenderMode(GSYVideoGLView.MODE_RENDER_SIZE);

        //多窗口播放效果
        //mDetailPlayer.setEffectFilter(new GammaEffect(0.8f));
        //mDetailPlayer.setCustomGLRenderer(new GSYVideoGLViewCustomRender2());

        //图片穿孔透视播放
        //mDetailPlayer.setCustomGLRenderer(new GSYVideoGLViewCustomRender3());

        //高斯拉伸视频铺满背景，替换黑色，前台正常比例播放
//        mDetailPlayer.setEffectFilter(new GaussianBlurEffect(6.0f, GaussianBlurEffect.TYPEXY));
        //mDetailPlayer.setCustomGLRenderer(new GSYVideoGLViewCustomRender4());
        //mDetailPlayer.setGLRenderMode(GSYVideoGLView.MODE_RENDER_SIZE);

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

    private void onJumpClick() {
        readyGo(ControlDetailVideoPlayerActivity.class);
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

    private void onChangeFilterClick() {
        if (GSYVideoType.getRenderType() != GSYVideoType.GLSURFACE) {
            ToastUtils.showLong("mRenderViewType 必须为 GLSurfaceView类型的才能应用该效果");
            // todo 切换RenderView 动态替换RenderView
//            mDetailPlayer.getRenderProxy().addView(this, );
            return;
        }
        GSYVideoGLView.ShaderInterface effect;
        switch (mFilterType) {
            case 0:
                effect = new AutoFixEffect(deep);
                break;
            case 1:
                effect = new PixelationEffect();
                break;
            case 2:
                effect = new BlackAndWhiteEffect();
                break;
            case 3:
                effect = new ContrastEffect(deep);
                break;
            case 4:
                effect = new CrossProcessEffect();
                break;
            case 5:
                effect = new DocumentaryEffect();
                break;
            case 6:
                effect = new DuotoneEffect(Color.BLUE, Color.YELLOW);
                break;
            case 7:
                effect = new FillLightEffect(deep);
                break;
            case 8:
                effect = new GammaEffect(deep);
                break;
            case 9:
                effect = new GrainEffect(deep);
                break;
            case 10:
                effect = new BrightnessEffect(deep);
                break;
            case 11:
                effect = new HueEffect(deep);
                break;
            case 12:
                effect = new InvertColorsEffect();
                break;
            case 13:
                effect = new LamoishEffect();
                break;
            case 14:
                effect = new PosterizeEffect();
                break;
            case 15:
                effect = new BarrelBlurEffect();
                break;
            case 16:
                effect = new SaturationEffect(deep);
                break;
            case 17:
                effect = new SepiaEffect();
                break;
            case 18:
                effect = new SharpnessEffect(deep);
                break;
            case 19:
                effect = new TemperatureEffect(deep);
                break;
            case 20:
                effect = new TintEffect(Color.GREEN);
                break;
            case 21:
                effect = new VignetteEffect(deep);
                break;
            case 22:
                effect = new GaussianBlurEffect(6.0f, GaussianBlurEffect.TYPEXY);
                break;
            case 23:
                effect = new OverlayEffect();
                break;
            case 24:
                effect = new SampleBlurEffect(4.0f);
                break;
            case 25:
            default:
                effect = new NoEffect();
                break;
        }
        mDetailPlayer.setEffectFilter(effect);
        mFilterType++;
        if (mFilterType > 25) {
            mFilterType = mFilterType % 26;
        }
    }

    /**
     * {@link GSYVideoGLView} 才支持的OpenGL动画
     */
    private void onGlAnimationClick() {
        cancelAnimateTask();
        mGlAnimateTask = new MatrixRotateTask();
        mGlAnimateTimer.schedule(mGlAnimateTask, 0, 50);
        percentageType++;
        if (percentageType > 4) {
            percentageType = 1;
        }

        // 水印移动
        cancelWaterMarkTask();
        mWaterMarkTask = new WaterMarkAnimTask();
        mGlAnimateTimer.schedule(mWaterMarkTask, 0, 400);
        mMoveWaterMark = !mMoveWaterMark;
    }

    private void cancelAnimateTask() {
        if (mGlAnimateTask != null) {
            mGlAnimateTask.cancel();
            mGlAnimateTask = null;
        }
    }

    private void cancelWaterMarkTask() {
        if (mWaterMarkTask != null) {
            mWaterMarkTask.cancel();
            mWaterMarkTask = null;
        }
    }

    /**
     * GLSurfaceView 矩阵选择 TimerTask
     * 设置GLRender的VertexShader的transformMatrix
     * 注意，这是android.opengl.Matrix
     */
    private class MatrixRotateTask extends TimerTask {
        @Override
        public void run() {
            float[] transform = new float[16];
            switch (percentageType) {
                default:
                case 1:
                    //给予x变化
                    Matrix.setRotateM(transform, 0, 360 * percentage / 100f, 1.0f, 0, 0.0f);
                    break;
                case 2:
                    //给予y变化
                    Matrix.setRotateM(transform, 0, 360 * percentage / 100f, 0.0f, 1.0f, 0.0f);
                    break;
                case 3:
                    //给予z变化
                    Matrix.setRotateM(transform, 0, 360 * percentage / 100f, 0.0f, 0, 1.0f);
                    break;
                case 4:
                    Matrix.setRotateM(transform, 0, 360, 0.0f, 0, 1.0f);
                    break;
            }
            //设置渲染transform
            mDetailPlayer.setMatrixGL(transform);
            percentage++;
            if (percentage > 100) {
                percentage = 1;
            }
        }
    }

    /**
     * 视频水印移动 TimerTask
     */
    private class WaterMarkAnimTask extends TimerTask {

        @Override
        public void run() {
            float[] transform = new float[16];
            //旋转到正常角度
            Matrix.setRotateM(transform, 0, 180f, 0.0f, 0, 1.0f);
            //调整大小比例
            Matrix.scaleM(transform, 0, mWaterMarkEffect.getScaleW(), mWaterMarkEffect.getScaleH(), 1);
            if (mMoveWaterMark) {
                //调整位置
                Matrix.translateM(transform, 0, mWaterMarkEffect.getPositionX(), mWaterMarkEffect.getPositionY(), 0f);
            } else {
                float maxX = mWaterMarkEffect.getMaxPositionX();
                float minX = mWaterMarkEffect.getMinPositionX();
                float maxY = mWaterMarkEffect.getMaxPositionY();
                float minY = mWaterMarkEffect.getMinPositionY();
                float x = (float) Math.random() * (maxX - minX) + minX;
                float y = (float) Math.random() * (maxY - minY) + minY;
                //调整位置
                Matrix.translateM(transform, 0, x, y, 0f);
                mCustomRender.setCurrentMVPMatrix(transform);
            }
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
