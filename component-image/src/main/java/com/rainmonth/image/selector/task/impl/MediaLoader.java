package com.rainmonth.image.selector.task.impl;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.rainmonth.common.thread.SrThreadService;
import com.rainmonth.common.utils.log.LogUtils;
import com.rainmonth.image.selector.bean.MediaFileBean;
import com.rainmonth.image.selector.task.IMediaLoader;
import com.rainmonth.image.selector.task.callback.IMediaTaskCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * 参考项目
 * https://github.com/LuckSiege/PictureSelector.git
 *
 * MediaStore 里面有四张表：
 * - video，对应的表字段{@link MediaStore.Video.VideoColumns}
 * - audio, 对应的表字段{@link MediaStore.Audio.AudioColumns}
 * - image, 对应的表字段{@link MediaStore.Images.ImageColumns}
 * - file,  对应的表字段{@link MediaStore.Files.FileColumns}
 *
 * @author RandyZhang
 * @date 2020/8/12 3:39 PM
 */
public class MediaLoader implements IMediaLoader {
    public static final String TAG = MediaLoader.class.getSimpleName();

    public static final int LOAD_TYPE_COMMON = 0;           // 加载普通文件
    public static final int LOAD_TYPE_IMAGE = 1;            // 加载图片
    public static final int LOAD_TYPE_VIDEO = 2;            // 加载视频
    public static final int LOAD_TYPE_AUDIO = 3;            // 加载音频

    // 视频表uri
    private Uri tableVideoUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
    // 图像表uri
    private Uri tableImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
    // 音频表Uri
    private Uri tableAudioUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
    // 文件表Uri
    private Uri tableFileUri = MediaStore.Files.getContentUri("external");


    public void loadByType(ContentResolver cr, int type, String... formatTypes) {

    }

    /**
     * 加载指定文件类型的文件
     *
     * @param cr          ContentResolver
     * @param formatTypes 文件类型
     */
    public void loadFileByType(ContentResolver cr, String... formatTypes) {

    }

    public void loadImageByType(ContentResolver cr, String... formatTypes) {

    }

    public void loadAudioByType(ContentResolver cr, String... formatTypes) {

    }

    public void loadVideoByType(ContentResolver cr, String... formatTypes) {

    }

    public String[] getProjectByType(int loadType) {
        return new String[]{};
    }

    public void getSelectionByType(int loadType) {

    }

    /**
     * 图片文件要获取的属性
     */
    private String[] IMAGE_PROJECTION = {
            MediaStore.MediaColumns._ID,
//            MediaStore.MediaColumns._COUNT,
            MediaStore.Images.ImageColumns.BUCKET_ID,
            MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME,
            MediaStore.MediaColumns.DISPLAY_NAME,
            MediaStore.MediaColumns.SIZE,
            MediaStore.MediaColumns.DATA,
            MediaStore.MediaColumns.DATE_ADDED,
            MediaStore.MediaColumns.DATE_MODIFIED,
            MediaStore.MediaColumns.MIME_TYPE,
//            "COUNT(*) AS " + COLUMN_COUNT
    };

    /**
     * 视频文件要获取的属性
     */
    private String[] VIDEO_PROJECTION = {
            MediaStore.MediaColumns._ID,
//            MediaStore.MediaColumns._COUNT,
            MediaStore.Video.VideoColumns.BUCKET_ID,
            MediaStore.Video.VideoColumns.BUCKET_DISPLAY_NAME,
            MediaStore.MediaColumns.DISPLAY_NAME,
            MediaStore.MediaColumns.SIZE,
            MediaStore.MediaColumns.DATA,
            MediaStore.MediaColumns.DATE_ADDED,
            MediaStore.MediaColumns.DATE_MODIFIED,
            MediaStore.MediaColumns.MIME_TYPE
    };

    /**
     * 音频文件要获取的属性
     */
    private String[] AUDIO_PROJECTION = {
            MediaStore.MediaColumns._ID,
//            MediaStore.MediaColumns._COUNT,
            MediaStore.Audio.Media.ALBUM,
            MediaStore.Audio.Media.ALBUM_ID,
            MediaStore.Audio.Media.DURATION,
            MediaStore.MediaColumns.DISPLAY_NAME,
            MediaStore.MediaColumns.SIZE,
            MediaStore.MediaColumns.DATA,
            MediaStore.MediaColumns.DATE_ADDED,
            MediaStore.MediaColumns.DATE_MODIFIED,
            MediaStore.MediaColumns.MIME_TYPE
    };

    /**
     * 普通文件（音频、视频、图片等）要获取的属性
     */
    private String[] FILE_PROJECTION = {

    };

    /**
     * 获取视频Selection语句
     */
    private String getVideoSelection() {
        return "";
    }

    /**
     * 获取音频Selection语句
     */
    private String getAudioSelection() {
        return "";
    }

    /**
     * 获取图片Selection语句
     *
     * @return
     */
    private String getImageSelection() {
        return "";
    }

    private static final String[] PROJECTION = {
            MediaStore.Files.FileColumns._ID,
            MediaStore.MediaColumns.DATA,
            COLUMN_BUCKET_ID,
//            COLUMN_BUCKET_DISPLAY_NAME,
            MediaStore.MediaColumns.MIME_TYPE,
            "COUNT(*) AS " + COLUMN_COUNT};

    private static final String SELECTION_FOR_SINGLE_MEDIA_TYPE = "(" + MediaStore.Files.FileColumns.MEDIA_TYPE + "=? )"
            + AND + MediaStore.MediaColumns.SIZE + ">0)" + GROUP_BY_BUCKET_ID;

    /**
     * 所有包括图片、视频、音频
     */
    private static final String SELECTION_FOR_ALL_MEDIA_TYPE = "("
            + MediaStore.Files.FileColumns.MEDIA_TYPE + "=?"
            + AND + MediaStore.Files.FileColumns.MEDIA_TYPE + "=?"
            + AND + MediaStore.Files.FileColumns.MEDIA_TYPE + "=?"
            + ")" + AND + MediaStore.MediaColumns.SIZE + ">0)" + GROUP_BY_BUCKET_ID;

    private static final String SELECTION_29 = MediaStore.Files.FileColumns.MEDIA_TYPE + "=? "
            + AND + MediaStore.MediaColumns.SIZE + ">0";

    private static final String SELECTION_NOT_GIF = "(" + MediaStore.Files.FileColumns.MEDIA_TYPE + "=?"
            + AND + MediaStore.MediaColumns.MIME_TYPE + NOT_GIF + ") AND " + MediaStore.MediaColumns.SIZE + ">0)" + GROUP_BY_BUCKET_ID;

    private static final String SELECTION_NOT_GIF_29 = MediaStore.Files.FileColumns.MEDIA_TYPE + "=?"
            + AND + MediaStore.MediaColumns.MIME_TYPE + NOT_GIF + " AND " + MediaStore.MediaColumns.SIZE + ">0";
    /**
     * Queries for images with the specified suffix
     */
    private static final String SELECTION_SPECIFIED_FORMAT = "(" + MediaStore.Files.FileColumns.MEDIA_TYPE + "=?"
            + AND + MediaStore.MediaColumns.MIME_TYPE;

    /**
     * Queries for images with the specified suffix targetSdk>=29
     */
    private static final String SELECTION_SPECIFIED_FORMAT_29 = MediaStore.Files.FileColumns.MEDIA_TYPE + "=?"
            + AND + MediaStore.MediaColumns.MIME_TYPE;

    @Override
    public void load(ContentResolver cr, IMediaTaskCallback callback) {
        if (cr == null) {
            return;
        }
        // todo 切换到子线程
        Cursor cursor = null;
        try {
            String selection = getSelectionForAllMediaType();
            LogUtils.d(TAG, "#load, selection:" + selection);
            cursor = cr.query(tableImageUri, IMAGE_PROJECTION, null, null, null);

            // todo 处理数据，先拿到文件夹，再拿文件
            if (cursor != null) {
                int count = cursor.getCount();
                List<MediaFileBean> mediaFileBeans = new ArrayList<>();
                if (count > 0) {
                    cursor.moveToFirst();
                    do {
                        MediaFileBean mediaFileBean = new MediaFileBean();
                        long id = cursor.getLong(cursor.getColumnIndex(IMAGE_PROJECTION[0]));
                        long bucketId = cursor.getLong(cursor.getColumnIndex(IMAGE_PROJECTION[1]));
                        String bucketDisplayName = cursor.getString(cursor.getColumnIndex(IMAGE_PROJECTION[2]));
                        String displayName = cursor.getString(cursor.getColumnIndex(IMAGE_PROJECTION[3]));
                        long size = cursor.getLong(cursor.getColumnIndex(IMAGE_PROJECTION[4]));
                        String url = cursor.getString(cursor.getColumnIndex(IMAGE_PROJECTION[5]));

                        mediaFileBean.id = id;
                        mediaFileBean.bucketId = bucketId;
                        mediaFileBean.bucketDisplayName = bucketDisplayName;
                        mediaFileBean.fileName = displayName;
                        mediaFileBean.size = size;
                        mediaFileBean.path = url;
                        mediaFileBeans.add(mediaFileBean);

                        LogUtils.d(TAG, mediaFileBean.toString());
                    } while (cursor.moveToNext());
                }
            }

        } catch (Exception e) {
            LogUtils.e(TAG, e.getMessage());
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
    }

    public void loadVideos(ContentResolver cr, IMediaTaskCallback callback) {
        if (cr == null) {
            return;
        }

        Cursor cursor = null;
        try {
            cursor = cr.query(tableVideoUri, VIDEO_PROJECTION, null, null, null);
            if (cursor != null) {
                int count = cursor.getCount();
                List<MediaFileBean> mediaFileBeans = new ArrayList<>();
                if (count > 0) {
                    cursor.moveToFirst();
                    do {
                        MediaFileBean mediaFileBean = new MediaFileBean();
                        long id = cursor.getLong(cursor.getColumnIndex(VIDEO_PROJECTION[0]));
                        long bucketId = cursor.getLong(cursor.getColumnIndex(VIDEO_PROJECTION[1]));
                        String bucketDisplayName = cursor.getString(cursor.getColumnIndex(VIDEO_PROJECTION[2]));
                        String displayName = cursor.getString(cursor.getColumnIndex(VIDEO_PROJECTION[3]));
                        long size = cursor.getLong(cursor.getColumnIndex(VIDEO_PROJECTION[4]));
                        String url = cursor.getString(cursor.getColumnIndex(VIDEO_PROJECTION[5]));

                        mediaFileBean.id = id;
                        mediaFileBean.bucketId = bucketId;
                        mediaFileBean.bucketDisplayName = bucketDisplayName;
                        mediaFileBean.fileName = displayName;
                        mediaFileBean.size = size;
                        mediaFileBean.path = url;
                        mediaFileBeans.add(mediaFileBean);

                        LogUtils.d(TAG, mediaFileBean.toString());
                    } while (cursor.moveToNext());
                }
            }
        } catch (Exception e) {
            LogUtils.d(TAG, e.getMessage());
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
    }


    public void loadAudios(ContentResolver cr, IMediaTaskCallback callback) {
        if (cr == null) {
            return;
        }
        Cursor cursor = null;
        SrThreadService.getInstance().executeDaemonTask(new Runnable() {
            @Override
            public void run() {

            }
        }, "loadAudiosRunnable");

        try {
            cursor = cr.query(tableAudioUri, AUDIO_PROJECTION, null, null, null);
            if (cursor != null) {
                int count = cursor.getCount();
                List<MediaFileBean> mediaFileBeans = new ArrayList<>();
                if (count > 0) {
                    cursor.moveToFirst();
                    do {
                        MediaFileBean mediaFileBean = new MediaFileBean();
                        long id = cursor.getLong(cursor.getColumnIndex(AUDIO_PROJECTION[0]));
                        String album = cursor.getString(cursor.getColumnIndex(AUDIO_PROJECTION[1]));
                        long albumId = cursor.getLong(cursor.getColumnIndex(AUDIO_PROJECTION[2]));
                        long duration = cursor.getLong(cursor.getColumnIndex(AUDIO_PROJECTION[3]));
                        String displayName = cursor.getString(cursor.getColumnIndex(AUDIO_PROJECTION[4]));
                        long size = cursor.getLong(cursor.getColumnIndex(AUDIO_PROJECTION[5]));
                        String url = cursor.getString(cursor.getColumnIndex(AUDIO_PROJECTION[6]));

                        mediaFileBean.id = id;
                        mediaFileBean.album = album;
                        mediaFileBean.albumId = albumId;
                        mediaFileBean.duration = duration;
                        mediaFileBean.fileName = displayName;
                        mediaFileBean.size = size;
                        mediaFileBean.path = url;

                        LogUtils.d(TAG, mediaFileBean.toString());

                        mediaFileBeans.add(mediaFileBean);
                    } while (cursor.moveToNext());
                }
                if (callback != null) {
                    callback.onMediaLoaded(mediaFileBeans);
                }
            }
        } catch (Exception e) {
            LogUtils.d(TAG, e.getMessage());
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
    }


    private String getFirstUrl(Cursor cursor) {
        return cursor.getString(cursor.getColumnIndex(MediaStore.Files.FileColumns.DATA));
    }

    /**
     * 通过线程池来进行加载
     */
    public void loadByThreadPool(ContentResolver cr, IMediaTaskCallback callback) {

    }

    /**
     * 通过RxJava来进行加载
     */
    public void loadByRxJava(ContentResolver cr, IMediaTaskCallback callback) {

    }

    /**
     * 通过AsyncTask来加载
     */
    public void loadByAsyncTask(ContentResolver cr, IMediaTaskCallback callback) {

    }

    /**
     * 按要求拼接sql查询语句
     */
    private String getSelectionForAllMediaType() {
        return SELECTION_FOR_ALL_MEDIA_TYPE;
    }

    @Override
    public void load(Context context, IMediaTaskCallback callback) {
        if (context == null) {
            return;
        }

        load(context.getContentResolver(), callback);
    }

    @Override
    public String[] buildQueryArgs() {
        // 原则上一个？对应一个args
        return new String[]{
                String.valueOf(MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE),
                String.valueOf(MediaStore.Files.FileColumns.MEDIA_TYPE_AUDIO),
                String.valueOf(MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO),
        };
    }

    public static final int TABLE_FILE = 0;
    public static final int TABLE_IMAGE = 1;
    public static final int TABLE_ALBUMS = 2;
    public static final int TABLE_ARTIST = 3;
    public static final int TABLE_AUDIO = 4;
    public static final int TABLE_VIDEO = 5;

    public void printTableInfo(Context context, int tableType) {
        printTableInfo(context.getContentResolver(), tableType);
    }

    public void printTableInfo(ContentResolver cr, int tableType) {
        Uri uri;
        switch (tableType) {
            case TABLE_IMAGE:
            case TABLE_ALBUMS:
            case TABLE_ARTIST:
                // uri = MediaStore.Images.Media.getContentUri()
                uri = tableImageUri;
                break;
            case TABLE_AUDIO:
                uri = tableAudioUri;
                break;
            case TABLE_VIDEO:
                uri = tableVideoUri;
                break;
            case TABLE_FILE:
            default:
                uri = tableFileUri;
                break;
        }
        printTableInfo(cr, uri);
    }

    /**
     * 打印MediaStore表信息的方法
     *
     * @param cr  ContentResolver obj
     * @param uri 表对应的uri
     */
    private void printTableInfo(ContentResolver cr, Uri uri) {
        Cursor cursor = null;
        try {
            cursor = cr.query(uri, null, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                String[] columns = cursor.getColumnNames();
                for (String string : columns) {
                    LogUtils.d(TAG, cursor.getColumnIndex(string) + " = " + string);
                }
            }
        } catch (Exception e) {
            LogUtils.d(TAG, e.getMessage());
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
    }
}
