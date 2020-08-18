package com.rainmonth.image.selector.task.impl;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import com.rainmonth.image.selector.bean.MediaFolderBean;
import com.rainmonth.image.selector.task.IMediaLoader;
import com.rainmonth.image.selector.task.callback.IMediaTaskCallback;

import java.util.ArrayList;
import java.util.List;

/**
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

    private Uri videoUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
    private Uri imageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
    private Uri audioUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
    private Uri fileUri = MediaStore.Files.getContentUri("external");

    /**
     * 视频文件要获取的属性
     */
    private String[] VIDEO_PROJECTION = {
            MediaStore.MediaColumns._ID,
            MediaStore.MediaColumns._COUNT,
            MediaStore.MediaColumns.DISPLAY_NAME,
            MediaStore.MediaColumns.SIZE,
            MediaStore.MediaColumns.DATA,
            MediaStore.MediaColumns.DATE_ADDED,
            MediaStore.MediaColumns.DATE_MODIFIED,
            MediaStore.MediaColumns.MIME_TYPE
    };

    /**
     * 图片文件要获取的属性
     */
    private String[] IMAGE_PROJECTION = {
            MediaStore.MediaColumns._ID,
//            MediaStore.MediaColumns._COUNT,
            MediaStore.Images.ImageColumns.BUCKET_ID,
            MediaStore.MediaColumns.DISPLAY_NAME,
            MediaStore.MediaColumns.SIZE,
            MediaStore.MediaColumns.DATA,
            MediaStore.MediaColumns.DATE_ADDED,
            MediaStore.MediaColumns.DATE_MODIFIED,
            MediaStore.MediaColumns.MIME_TYPE,
            "COUNT(*) AS " + COLUMN_COUNT
    };

    /**
     * 音频文件要获取的属性
     */
    private String[] AUDIO_PROJECTION = {
            MediaStore.MediaColumns._ID,
            MediaStore.MediaColumns._COUNT,
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
            Log.d(TAG, "#load, selection:" + selection);
            cursor = cr.query(imageUri, IMAGE_PROJECTION, null, null, null);

            // todo 处理数据，先拿到文件夹，在拿文件
            if (cursor != null) {
                int count = cursor.getCount();
                int totalCount = 0;
                List<MediaFolderBean> mediaFolders = new ArrayList<>();
                if (count > 0) {
                    cursor.moveToFirst();
                    do {
                        MediaFolderBean mediaFolder = new MediaFolderBean();
//                        private String[] IMAGE_PROJECTION = {
//                                MediaStore.MediaColumns._ID,
////            MediaStore.MediaColumns._COUNT,
//                                MediaStore.Images.ImageColumns.BUCKET_ID,
//                                MediaStore.MediaColumns.DISPLAY_NAME,
//                                MediaStore.MediaColumns.SIZE,
//                                MediaStore.MediaColumns.DATA,
//                                MediaStore.MediaColumns.DATE_ADDED,
//                                MediaStore.MediaColumns.DATE_MODIFIED,
//                                MediaStore.MediaColumns.MIME_TYPE,
//                                "COUNT(*) AS " + COLUMN_COUNT
//                        };
                        long id = cursor.getLong(cursor.getColumnIndex(IMAGE_PROJECTION[0]));
                        long bucketId = cursor.getLong(cursor.getColumnIndex(IMAGE_PROJECTION[1]));
                        String bucketDisplayName = cursor.getString(cursor.getColumnIndex(IMAGE_PROJECTION[2]));
                        long size = cursor.getLong(cursor.getColumnIndex(IMAGE_PROJECTION[3]));
                        String url = cursor.getString(cursor.getColumnIndex(IMAGE_PROJECTION[4]));
                        mediaFolder.setFirstImagePath(url);
                        int columnCount = cursor.getInt(cursor.getColumnIndex(COLUMN_COUNT));
                        mediaFolder.setBucketId(bucketId);

                        mediaFolder.setName(bucketDisplayName);
                        mediaFolder.setFolderMediaNum(columnCount);
                        mediaFolders.add(mediaFolder);
                        totalCount += columnCount;
                    } while (cursor.moveToNext());
                }

//                sortFolder(mediaFolders);

                // 相机胶卷
                MediaFolderBean allMediaFolder = new MediaFolderBean();
                allMediaFolder.setFolderMediaNum(totalCount);
                allMediaFolder.setSelected(true);
                allMediaFolder.setBucketId(-1);
                if (cursor.moveToFirst()) {
//                    String firstUrl = SdkVersionUtils.checkedAndroid_Q() ? getFirstUri(data) : getFirstUrl(data);
                    String firstUrl = getFirstUrl(cursor);
                    allMediaFolder.setFirstImagePath(firstUrl);
                }
//                String bucketDisplayName = config.chooseMode == PictureMimeType.ofAudio() ?
//                        mContext.getString(R.string.picture_all_audio)
//                        : mContext.getString(R.string.picture_camera_roll);
//                allMediaFolder.setName(bucketDisplayName);
//                allMediaFolder.setOfAllType(config.chooseMode);
                allMediaFolder.setCameraFolder(true);
                mediaFolders.add(0, allMediaFolder);

//                return mediaFolders;
            }

        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
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
}
