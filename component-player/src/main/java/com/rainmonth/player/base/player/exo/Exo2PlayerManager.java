package com.rainmonth.player.base.player.exo;

import android.content.Context;
import android.media.AudioManager;
import android.net.TrafficStats;
import android.net.Uri;
import android.os.Message;
import android.view.Surface;

import androidx.annotation.Nullable;

import com.google.android.exoplayer2.SeekParameters;
import com.google.android.exoplayer2.video.DummySurface;
import com.rainmonth.player.base.player.BasePlayerManager;
import com.rainmonth.player.base.cache.ICacheManager;
import com.rainmonth.player.model.GSYModel;
import com.rainmonth.player.model.VideoOptionModel;
import com.rainmonth.player.utils.Debugger;

import java.util.List;

import tv.danmaku.ijk.media.player.IMediaPlayer;

/**
 * EXOPlayer2
 * Exo 内核实现的播放器
 */
public class Exo2PlayerManager extends BasePlayerManager {
    private static final String TAG = Exo2PlayerManager.class.getSimpleName();
    private Context context;

    private IjkExo2MediaPlayer mediaPlayer;

    private Surface surface;

    private DummySurface dummySurface;

    private long lastTotalRxBytes = 0;

    private long lastTimeStamp = 0;

    @Override
    public IMediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    @Override
    public void initVideoPlayer(Context context, Message msg, List<VideoOptionModel> optionModelList, ICacheManager cacheManager) {
        this.context = context.getApplicationContext();
        mediaPlayer = new IjkExo2MediaPlayer(context);
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        if (dummySurface == null) {
            dummySurface = DummySurface.newInstanceV17(context, false);
        }
        //使用自己的cache模式
        GSYModel gsyModel = (GSYModel) msg.obj;
        try {
            mediaPlayer.setLooping(gsyModel.isLooping());
            mediaPlayer.setPreview(gsyModel.getMapHeadData() != null && gsyModel.getMapHeadData().size() > 0);
            if (gsyModel.isCache() && cacheManager != null) {
                //通过管理器处理
                cacheManager.doCacheLogic(context, mediaPlayer, gsyModel.getUrl(), gsyModel.getMapHeadData(), gsyModel.getCachePath());
            } else {
                //通过自己的内部缓存机制
                mediaPlayer.setCache(gsyModel.isCache());
                mediaPlayer.setCacheDir(gsyModel.getCachePath());
                mediaPlayer.setOverrideExtension(gsyModel.getOverrideExtension());
                mediaPlayer.setDataSource(context, Uri.parse(gsyModel.getUrl()), gsyModel.getMapHeadData());
            }
            if (gsyModel.getSpeed() != 1 && gsyModel.getSpeed() > 0) {
                mediaPlayer.setSpeed(gsyModel.getSpeed(), 1);
            }
        } catch (Exception e) {
            Debugger.printStackTrace(TAG, e);
        }
        initSuccess(gsyModel);
    }

    @Override
    public void showDisplay(final Message msg) {
        if (mediaPlayer == null) {
            return;
        }
        if (msg.obj == null) {
            mediaPlayer.setSurface(dummySurface);
        } else {
            Surface holder = (Surface) msg.obj;
            surface = holder;
            mediaPlayer.setSurface(holder);
        }
    }

    @Override
    public void setSpeed(final float speed, final boolean soundTouch) {
        if (mediaPlayer != null) {
            try {
                mediaPlayer.setSpeed(speed, 1);
            } catch (Exception e) {
                Debugger.printStackTrace(TAG, e);
            }
        }
    }

    @Override
    public void setNeedMute(final boolean needMute) {
        if (mediaPlayer != null) {
            if (needMute) {
                mediaPlayer.setVolume(0, 0);
            } else {
                mediaPlayer.setVolume(1, 1);
            }
        }
    }

    @Override
    public void setVolume(float left, float right) {
        if (mediaPlayer != null) {
            mediaPlayer.setVolume(left, right);
        }
    }

    @Override
    public void releaseSurface() {
        Debugger.printfLog(TAG, "releaseSurface()");
        if (surface != null) {
            //surface.release();
            surface = null;
        }
    }

    @Override
    public void release() {
        Debugger.printfLog(TAG, "release()");
        if (mediaPlayer != null) {
            mediaPlayer.setSurface(null);
            mediaPlayer.release();
            mediaPlayer = null;
        }
        if (dummySurface != null) {
            dummySurface.release();
            dummySurface = null;
        }
        lastTotalRxBytes = 0;
        lastTimeStamp = 0;
    }

    @Override
    public int getBufferedPercentage() {
        if (mediaPlayer != null) {
            return mediaPlayer.getBufferedPercentage();
        }
        return 0;
    }

    @Override
    public long getNetSpeed() {
        if (mediaPlayer != null) {
            return getNetSpeed(context);
        }
        return 0;
    }


    @Override
    public void setSpeedPlaying(float speed, boolean soundTouch) {

    }


    @Override
    public void start() {
        Debugger.printfLog(TAG, "start()");
        if (mediaPlayer != null) {
            mediaPlayer.start();
        }
    }

    @Override
    public void stop() {
        Debugger.printfLog(TAG, "stop()");
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }

    @Override
    public void pause() {
        Debugger.printfLog(TAG, "pause()");
        if (mediaPlayer != null) {
            mediaPlayer.pause();
        }
    }

    @Override
    public int getVideoWidth() {
        if (mediaPlayer != null) {
            return mediaPlayer.getVideoWidth();
        }
        return 0;
    }

    @Override
    public int getVideoHeight() {
        if (mediaPlayer != null) {
            return mediaPlayer.getVideoHeight();
        }
        return 0;
    }

    @Override
    public boolean isPlaying() {
        if (mediaPlayer != null) {
            return mediaPlayer.isPlaying();
        }
        return false;
    }

    @Override
    public void seekTo(long time) {
        if (mediaPlayer != null) {
            mediaPlayer.seekTo(time);
        }
    }

    @Override
    public long getCurrentPosition() {
        if (mediaPlayer != null) {
            return mediaPlayer.getCurrentPosition();
        }
        return 0;
    }

    @Override
    public long getDuration() {
        if (mediaPlayer != null) {
            return mediaPlayer.getDuration();
        }
        return 0;
    }

    @Override
    public int getVideoSarNum() {
        if (mediaPlayer != null) {
            return mediaPlayer.getVideoSarNum();
        }
        return 1;
    }

    @Override
    public int getVideoSarDen() {
        if (mediaPlayer != null) {
            return mediaPlayer.getVideoSarDen();
        }
        return 1;
    }

    @Override
    public boolean isSurfaceSupportLockCanvas() {
        return false;
    }


    /**
     * 设置seek 的临近帧。
     **/
    public void setSeekParameter(@Nullable SeekParameters seekParameters) {
        if (mediaPlayer != null) {
            mediaPlayer.setSeekParameter(seekParameters);
        }
    }


    private long getNetSpeed(Context context) {
        if (context == null) {
            return 0;
        }
        long nowTotalRxBytes = TrafficStats.getUidRxBytes(context.getApplicationInfo().uid) == TrafficStats.UNSUPPORTED ? 0 : (TrafficStats.getTotalRxBytes() / 1024);//转为KB
        long nowTimeStamp = System.currentTimeMillis();
        long calculationTime = (nowTimeStamp - lastTimeStamp);
        if (calculationTime == 0) {
            return calculationTime;
        }
        //毫秒转换
        long speed = ((nowTotalRxBytes - lastTotalRxBytes) * 1000 / calculationTime);
        lastTimeStamp = nowTimeStamp;
        lastTotalRxBytes = nowTotalRxBytes;
        return speed;
    }

}
