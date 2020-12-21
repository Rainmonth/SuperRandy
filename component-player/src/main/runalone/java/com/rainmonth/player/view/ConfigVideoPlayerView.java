package com.rainmonth.player.view;

import android.content.Context;
import android.graphics.Matrix;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.rainmonth.player.R;
import com.rainmonth.player.dialog.SwitchVideoTypeDialog;
import com.rainmonth.player.model.VideoListBean;
import com.rainmonth.player.render.view.IRenderView;
import com.rainmonth.player.utils.GSYVideoType;
import com.rainmonth.player.video.StandardVideoPlayer;
import com.rainmonth.player.video.base.BaseVideoPlayer;
import com.rainmonth.player.video.base.VideoPlayer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 可以配置的视频播放View
 * 配置项包括：
 * 画面旋转、画面比例、画面镜像、清晰度
 * 1.画面旋转，最终调用的是{@link View#setRotation(float)} 方法
 * 2.画面比例，这个设置最终影响的是 {@link IRenderView} 的实现类的 onMeasure 执行
 * 3.画面镜像，最懂调用的时{@link android.view.TextureView#setTransform(Matrix)}，这个目前只有
 * {@link android.view.TextureView} 支持，{@link android.view.SurfaceView}、{@link android.opengl.GLSurfaceView} 不支持
 * 4. 清晰度切换就是同一个视频内容，不同的播放源（url）
 * <p>
 * 5. 倍速播放
 * 6. 滤镜播放
 * 7. 声音调节
 * 8. 亮度调节
 *
 * @author RandyZhang
 * @date 2020/12/3 10:31 AM
 */
public class ConfigVideoPlayerView extends StandardVideoPlayer {
    private TextView mSwitchClarity;                // 切换清晰度
    private TextView mChangeTransform;              // 切换镜像
    private TextView mChangeRotation;               // 切换旋转
    private TextView mChangeSize;                   // 切换显示比例

    private int mShowSize = 0;                      // 当前显示的比例
    private int mShowTransform = 0;                 // 当前变换的值
    private int mCurrentSourceIndex = 0;            // 当前播放源对应的index
    private String mCurrentClarity = "标准";          // 当前清晰度

    private List<VideoListBean> mUrlList = new ArrayList<>();

    public ConfigVideoPlayerView(Context context, Boolean fullFlag) {
        super(context, fullFlag);
    }

    public ConfigVideoPlayerView(Context context) {
        super(context);
    }

    public ConfigVideoPlayerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public int getLayoutId() {
        return R.layout.player_view_config_video;
    }

    @Override
    protected void init(Context context) {
        super.init(context);

        mSwitchClarity = findViewById(R.id.player_switch_clarity);
        mChangeRotation = findViewById(R.id.player_rotate);
        mChangeTransform = findViewById(R.id.player_transform);
        mChangeSize = findViewById(R.id.player_ration);

        mSwitchClarity.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showSwitchClarityDialog();
            }
        });

        mChangeRotation.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mHadPlay) {
                    return;
                }
                if (mTextureView.getRotation() - mRotate == 270) {
                    mTextureView.setRotation(mRotate);
                } else {
                    mTextureView.setRotation(mTextureView.getRotation() + 90);
                }
                mTextureView.requestLayout();
            }
        });

        mChangeTransform.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mHadPlay) {
                    return;
                }
                if (mShowTransform == 0) {
                    mShowTransform = 1;
                } else if (mShowTransform == 1) {
                    mShowTransform = 2;
                } else if (mShowTransform == 2) {
                    mShowTransform = 0;
                }
                changeShowTransform();
            }
        });
        mChangeSize.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mHadPlay) {
                    return;
                }
                if (mShowSize == 0) {
                    mShowSize = 1;
                } else if (mShowSize == 1) {
                    mShowSize = 2;
                } else if (mShowSize == 2) {
                    mShowSize = 3;
                } else if (mShowSize == 3) {
                    mShowSize = 4;
                } else if (mShowSize == 4) {
                    mShowSize = 0;
                }
                changeShowSize();
            }
        });
    }

    /**
     * 显示切换清晰度弹窗
     */
    private void showSwitchClarityDialog() {
        if (!mHadPlay) {
            return;
        }
        showSwitchDialog();
    }

    private void changeShowTransform() {
        switch (mShowTransform) {
            case 1: {
                Matrix transform = new Matrix();
                transform.setScale(-1, 1, mTextureView.getWidth() / 2f, 0);
                mTextureView.setTransform(transform);
                mChangeTransform.setText("左右镜像");
                mTextureView.invalidate();
            }
            break;
            case 2: {
                Matrix transform = new Matrix();
                transform.setScale(1, -1, 0, mTextureView.getHeight() / 2f);
                mTextureView.setTransform(transform);
                mChangeTransform.setText("上下镜像");
                mTextureView.invalidate();
            }
            break;
            case 0: {
                Matrix transform = new Matrix();
                transform.setScale(1, 1, mTextureView.getWidth() / 2f, 0);
                mTextureView.setTransform(transform);
                mChangeTransform.setText("旋转镜像");
                mTextureView.invalidate();
            }
            break;
        }
    }

    /**
     * 更新显示比例
     */
    private void changeShowSize() {
        if (!mHadPlay) {
            return;
        }
        if (mShowSize == 1) {
            mChangeSize.setText("16:9");
            GSYVideoType.setShowType(GSYVideoType.SCREEN_TYPE_16_9);
        } else if (mShowSize == 2) {
            mChangeSize.setText("4:3");
            GSYVideoType.setShowType(GSYVideoType.SCREEN_TYPE_4_3);
        } else if (mShowSize == 3) {
            mChangeSize.setText("全屏");
            GSYVideoType.setShowType(GSYVideoType.SCREEN_TYPE_FULL);
        } else if (mShowSize == 4) {
            mChangeSize.setText("拉伸全屏");
            GSYVideoType.setShowType(GSYVideoType.SCREEN_MATCH_FULL);
        } else if (mShowSize == 0) {
            mChangeSize.setText("默认比例");
            GSYVideoType.setShowType(GSYVideoType.SCREEN_TYPE_DEFAULT);
        }
        changeTextureViewShowType();
        if (mTextureView != null)
            mTextureView.requestLayout();
        mSwitchClarity.setText(mCurrentClarity);
    }

    @Override
    public void onSurfaceAvailable(Surface surface) {
        super.onSurfaceAvailable(surface);
        changeShowSize();
        changeShowTransform();
    }

    @Override
    public void onSurfaceSizeChanged(Surface surface, int width, int height) {
        super.onSurfaceSizeChanged(surface, width, height);
        changeShowTransform();
    }

    /**
     * 全屏时将对应处理参数逻辑赋给全屏播放器
     *
     * @param context
     * @param actionBar
     * @param statusBar
     * @return
     */
    @Override
    public BaseVideoPlayer startWindowFullscreen(Context context, boolean actionBar, boolean statusBar) {
        ConfigVideoPlayerView sampleVideo = (ConfigVideoPlayerView) super.startWindowFullscreen(context, actionBar, statusBar);
        sampleVideo.mCurrentSourceIndex = mCurrentSourceIndex;
        sampleVideo.mShowTransform = mShowTransform;
        sampleVideo.mChangeSize = mChangeSize;
        sampleVideo.mUrlList = mUrlList;
        sampleVideo.mCurrentClarity = mCurrentClarity;
        //sampleVideo.resolveTransform();
        sampleVideo.changeShowSize();
        //sampleVideo.resolveRotateUI();
        //这个播放器的demo配置切换到全屏播放器
        //这只是单纯的作为全屏播放显示，如果需要做大小屏幕切换，请记得在这里耶设置上视频全屏的需要的自定义配置
        //比如已旋转角度之类的等等
        //可参考super中的实现
        return sampleVideo;
    }

    /**
     * 推出全屏时将对应处理参数逻辑返回给非播放器
     *
     * @param oldF
     * @param vp
     * @param gsyVideoPlayer
     */
    @Override
    protected void resolveNormalVideoShow(View oldF, ViewGroup vp, VideoPlayer gsyVideoPlayer) {
        super.resolveNormalVideoShow(oldF, vp, gsyVideoPlayer);
        if (gsyVideoPlayer != null) {
            ConfigVideoPlayerView sampleVideo = (ConfigVideoPlayerView) gsyVideoPlayer;
            mCurrentSourceIndex = sampleVideo.mCurrentSourceIndex;
            mChangeSize = sampleVideo.mChangeSize;
            mShowTransform = sampleVideo.mShowTransform;
            mCurrentClarity = sampleVideo.mCurrentClarity;
            setUp(mUrlList, mCache, mCachePath, mTitle);
            changeShowSize();
        }
    }

    /**
     * 弹出切换清晰度
     */
    private void showSwitchDialog() {
        if (!mHadPlay) {
            return;
        }
        SwitchVideoTypeDialog switchVideoTypeDialog = new SwitchVideoTypeDialog(getContext());
        switchVideoTypeDialog.initList(mUrlList, new SwitchVideoTypeDialog.OnListItemClickListener() {
            @Override
            public void onItemClick(int position) {
                final String name = mUrlList.get(position).getName();
                if (mCurrentSourceIndex != position) {
                    if ((mCurrentState == VideoPlayer.CURRENT_STATE_PLAYING
                            || mCurrentState == VideoPlayer.CURRENT_STATE_PAUSE)) {
                        final String url = mUrlList.get(position).getUrl();
                        onVideoPause();
                        final long currentPosition = mCurrentPosition;
                        getVideoManager().releaseMediaPlayer();
                        cancelProgressTimer();
                        hideAllWidget();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                setUp(url, mCache, mCachePath, mTitle);
                                setSeekOnStart(currentPosition);
                                startPlayLogic();
                                cancelProgressTimer();
                                hideAllWidget();
                            }
                        }, 500);
                        mCurrentClarity = name;
                        mSwitchClarity.setText(name);
                        mCurrentSourceIndex = position;
                    }
                } else {
                    Toast.makeText(getContext(), "已经是 " + name, Toast.LENGTH_LONG).show();
                }
            }
        });
        switchVideoTypeDialog.show();
    }

    /**
     * 设置播放URL
     *
     * @param url           播放url
     * @param cacheWithPlay 是否边播边缓存
     * @param title         title
     * @return
     */
    public boolean setUp(List<VideoListBean> url, boolean cacheWithPlay, String title) {
        mUrlList = url;
        return setUp(url.get(mCurrentSourceIndex).getUrl(), cacheWithPlay, title);
    }

    /**
     * 设置播放URL
     *
     * @param url           播放url
     * @param cacheWithPlay 是否边播边缓存
     * @param cachePath     缓存路径，如果是M3U8或者HLS，请设置为false
     * @param title         title
     * @return
     */
    public boolean setUp(List<VideoListBean> url, boolean cacheWithPlay, File cachePath, String title) {
        mUrlList = url;
        return setUp(url.get(mCurrentSourceIndex).getUrl(), cacheWithPlay, cachePath, title);
    }
}
