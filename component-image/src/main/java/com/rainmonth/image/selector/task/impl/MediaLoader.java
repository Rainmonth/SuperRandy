package com.rainmonth.image.selector.task.impl;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.util.Log;

import com.rainmonth.common.thread.SrThreadService;
import com.rainmonth.image.selector.task.IMediaLoader;
import com.rainmonth.image.selector.task.callback.IMediaTaskCallback;

/**
 * @author RandyZhang
 * @date 2020/8/12 3:39 PM
 */
public class MediaLoader implements IMediaLoader {
    public static final String TAG = MediaLoader.class.getSimpleName();

    private static final String[] PROJECTION = {
            MediaStore.Files.FileColumns._ID,
            MediaStore.MediaColumns.DATA,
            COLUMN_BUCKET_ID,
            COLUMN_BUCKET_DISPLAY_NAME,
            MediaStore.MediaColumns.MIME_TYPE,
            "COUNT(*) AS " + COLUMN_COUNT};

    /**
     * Image
     */


    private static final String SELECTION_FOR_SINGLE_MEDIA_TYPE = "(" + MediaStore.Files.FileColumns.MEDIA_TYPE + "=? )"
            + AND + MediaStore.MediaColumns.SIZE + ">0)" + GROUP_BY_BUCKET_ID;

    /**
     * 所有包括图片、视频、音频
     */
    private static final String SELECTION_FOR_ALL_MEDIA_TYPE = "(" + MediaStore.Files.FileColumns.MEDIA_TYPE + "=?"
            + AND + MediaStore.Files.FileColumns.MEDIA_TYPE + "=?"
            + AND + MediaStore.Files.FileColumns.MEDIA_TYPE + "=?"
            + AND + MediaStore.Files.FileColumns.MEDIA_TYPE + "=?"
            + ")" + MediaStore.MediaColumns.SIZE + ">0)" + GROUP_BY_BUCKET_ID;

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
            cursor = cr.query(QUERY_URI, PROJECTION, selection, buildQueryArgs(), "");

            // todo 处理数据，先拿到文件夹，在拿文件


        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
    }

    /**
     * 按要求拼接sql查询语句
     */
    private String getSelectionForAllMediaType() {
        return SELECTION_FOR_SINGLE_MEDIA_TYPE;
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
