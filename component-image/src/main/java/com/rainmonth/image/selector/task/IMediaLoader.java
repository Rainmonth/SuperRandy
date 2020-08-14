package com.rainmonth.image.selector.task;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.provider.MediaStore;

import com.rainmonth.image.selector.task.callback.IMediaTaskCallback;

/**
 * @author RandyZhang
 * @date 2020/8/12 1:56 PM
 */
public interface IMediaLoader {

    Uri QUERY_URI = MediaStore.Files.getContentUri("external");

    String AND = " AND ";
    String NOT_GIF = "!= 'image/gif'";
    String OR = " OR ";
    String ORDER_BY = MediaStore.Files.FileColumns._ID + " DESC";
    String GROUP_BY_BUCKET_ID = " GROUP BY (bucket_id";
    String COLUMN_COUNT = "count";
    String COLUMN_BUCKET_ID = "bucket_id";
    String COLUMN_BUCKET_DISPLAY_NAME = "display_name";

    /**
     * 查询多媒体数据库要返回的字段名称数组
     */
    String[] PROJECTION_PAGE = {
            MediaStore.Files.FileColumns._ID,
            MediaStore.MediaColumns.DATA,
            MediaStore.MediaColumns.MIME_TYPE,
            MediaStore.MediaColumns.WIDTH,
            MediaStore.MediaColumns.HEIGHT,
            MediaStore.MediaColumns.SIZE,
            MediaStore.MediaColumns.DISPLAY_NAME,
//            MediaStore.MediaColumns.BUCKET_DISPLAY_NAME,
//            MediaStore.MediaColumns.DURATION,
//            MediaStore.MediaColumns.BUCKET_ID
    };

    /**
     * Android Q查询多媒体数据库要返回的字段名称数组
     */
    String[] PROJECTION_PAGE_FOR_Q = {

    };

    void load(ContentResolver cr, IMediaTaskCallback callback);

    void load(Context context, IMediaTaskCallback callback);

    String[] buildQueryArgs();
}
