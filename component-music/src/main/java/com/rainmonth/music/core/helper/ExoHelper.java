package com.rainmonth.music.core.helper;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.RenderersFactory;
import com.google.android.exoplayer2.ext.rtmp.RtmpDataSourceFactory;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.source.dash.DashMediaSource;
import com.google.android.exoplayer2.source.dash.DefaultDashChunkSource;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.source.smoothstreaming.DefaultSsChunkSource;
import com.google.android.exoplayer2.source.smoothstreaming.SsMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.MappingTrackSelector;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.upstream.RawResourceDataSource;
import com.google.android.exoplayer2.upstream.cache.Cache;
import com.google.android.exoplayer2.upstream.cache.CacheDataSource;
import com.google.android.exoplayer2.upstream.cache.CacheDataSourceFactory;
import com.google.android.exoplayer2.upstream.cache.LeastRecentlyUsedCacheEvictor;
import com.google.android.exoplayer2.upstream.cache.SimpleCache;
import com.google.android.exoplayer2.util.Util;
import com.rainmonth.utils.log.LogUtils;

import java.io.File;
import java.util.Map;

/**
 * Exo播放器帮助类
 *
 * @author 张豪成
 * @date 2019-12-13 20:52
 */
public class ExoHelper {
    private static final String Tag = ExoHelper.class.getSimpleName();

    public static final  int  TYPE_RTMP              = 4;
    private static final long DEFAULT_MAX_CACHE_SIZE = 512 * 1024 * 1024;

    private Context             mContext;
    private Map<String, String> mHeaders;

    private static ExoHelper mInstance;

    /**
     * ExoPlayer自己实现的缓存{@link SimpleCache}
     */
    private static Cache mCache;

    public static ExoHelper newInstance(Context context, Map<String, String> headers) {
        synchronized (ExoHelper.class) {
            if (mInstance == null) {
                mInstance = new ExoHelper(context, headers);
            }
        }
        return mInstance;
    }

    private ExoHelper(Context context, Map<String, String> headers) {
        mContext = context;
        mHeaders = headers;
    }

    public MediaSource getMediaSource(String dataSourcePath, String overrideExtension, boolean cacheEnable, File cacheFile) {
        if (TextUtils.isEmpty(dataSourcePath)) {
            LogUtils.e(Tag, "dataSourcePath is empty");
            return null;
        }

        MediaSource mediaSource = null;
        // todo something

        if (mediaSource != null) {
            return mediaSource;
        }


        Uri contentUri = Uri.parse(dataSourcePath);
        int contentType = inferContentType(dataSourcePath, overrideExtension);
        // 处理本地类型
        if ("android.resource".equalsIgnoreCase(contentUri.getScheme())) {
            DataSpec dataSpec = new DataSpec(contentUri);
            RawResourceDataSource rawResourceDataSource = new RawResourceDataSource(mContext);
            try {
                rawResourceDataSource.open(dataSpec);
            } catch (RawResourceDataSource.RawResourceDataSourceException e) {
                LogUtils.e(Tag, e);
            }
            DataSource.Factory factory = new DataSource.Factory() {
                @Override
                public DataSource createDataSource() {
                    return rawResourceDataSource;
                }
            };
            return new ProgressiveMediaSource.Factory(factory).createMediaSource(contentUri);
        }
        // todo
        DataSource.Factory availableFactory = getDataSourceFactory(mContext, cacheEnable, cacheFile);
        switch (contentType) {
            case C.TYPE_SS:
                mediaSource = new SsMediaSource.Factory(
                        new DefaultSsChunkSource.Factory(availableFactory),
                        new DefaultDataSourceFactory(mContext, null, availableFactory))
                        .createMediaSource(contentUri);
                break;
            case C.TYPE_DASH:
                mediaSource = new DashMediaSource.Factory(
                        new DefaultDashChunkSource.Factory(availableFactory),
                        new DefaultDataSourceFactory(mContext, null, availableFactory)
                ).createMediaSource(contentUri);
                break;
            case C.TYPE_HLS:
                mediaSource = new HlsMediaSource.Factory(availableFactory).createMediaSource(contentUri);
                break;
            case TYPE_RTMP:
                RtmpDataSourceFactory rtmpDataSourceFactory = new RtmpDataSourceFactory(null);
                mediaSource = new ProgressiveMediaSource.Factory(rtmpDataSourceFactory).createMediaSource(contentUri);
                break;
            case C.TYPE_OTHER:
            default:
                mediaSource = new ProgressiveMediaSource.Factory(availableFactory).createMediaSource(contentUri);
                break;
        }
        return mediaSource;
    }

    /**
     * 获取DataSource.Factory
     *
     * @param context     context上下文
     * @param cacheEnable 是否开启缓存功能
     * @param cacheDir    缓存目录
     * @return DataSource.Factory
     */
    public static DataSource.Factory getDataSourceFactory(Context context, boolean cacheEnable, File cacheDir) {
        if (cacheEnable) {
            return getDataSourceFactoryWithCache(context, cacheDir);
        } else {
            return getDataSourceFactoryWithoutCache(context);
        }
    }

    /**
     * 获取有缓存功能的DataSource.Factory
     *
     * @param context  context上下文
     * @param cacheDir 缓存目录
     * @return 带缓存功能的DataSource.Factory
     */
    public static DataSource.Factory getDataSourceFactoryWithCache(Context context, File cacheDir) {
        Cache cache = getCacheSingleInstance(context, cacheDir);
        if (cache != null) {
            return new CacheDataSourceFactory(cache, getDataSourceFactoryWithoutCache(context), CacheDataSource.FLAG_IGNORE_CACHE_ON_ERROR);
        } else {
            return getDataSourceFactoryWithoutCache(context);
        }
    }

    /**
     * 获取不需缓存的DataSource.Factory
     *
     * @param context context上下文
     * @return 无缓存功能的DataSource.Factory
     */
    public static DataSource.Factory getDataSourceFactoryWithoutCache(Context context) {
        // todo 这里可以根据自己的需要定制
        return new DefaultDataSourceFactory(context, null, new DefaultHttpDataSourceFactory(Util.getUserAgent(context, context.getPackageName())));
    }

    public static Cache getCacheSingleInstance(Context context, File cacheDir) {
        String dirs = context.getCacheDir().getAbsolutePath();
        if (cacheDir != null) {
            dirs = cacheDir.getAbsolutePath();
        }
        if (mCache == null) {
            String path = dirs + File.separator + "exo";
            File temp = new File(path);
            boolean isLocked = SimpleCache.isCacheFolderLocked(temp);
            if (!isLocked) {
                mCache = new SimpleCache(temp, new LeastRecentlyUsedCacheEvictor(DEFAULT_MAX_CACHE_SIZE));
            }
        }

        return mCache;

    }

    /**
     * 获取内容的类型
     *
     * @param path              内容路径
     * @param overrideExtension 要覆盖的扩展名
     * @return 内容类型
     */
    public int inferContentType(String path, String overrideExtension) {
        path = Util.toLowerInvariant(path);
        if (path.startsWith("rtmp:")) {
            return TYPE_RTMP;
        } else {
            return inferContentType(Uri.parse(path), overrideExtension);
        }
    }

    public int inferContentType(Uri uri, String overrideExtension) {
        return Util.inferContentType(uri, overrideExtension);
    }

    public RenderersFactory getRendererFactory(Context context) {
        return new DefaultRenderersFactory(context);
    }

    public LoadControl getLoadControl() {
        return new DefaultLoadControl();
    }

    public MappingTrackSelector getTrackSelector() {
        return new DefaultTrackSelector();
    }
}
