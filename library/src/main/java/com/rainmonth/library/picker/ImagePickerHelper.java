package com.rainmonth.library.picker;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore.Images.Media;
import android.provider.MediaStore.Images.Thumbnails;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

class ImagePickerHelper {

    private Context context;
    private ContentResolver contentResolver;

    private HashMap<String, String> mThumbnailList = new HashMap<>();
    private HashMap<String, ImageBucket> mBucketList;

    @SuppressLint("StaticFieldLeak")
    private static ImagePickerHelper instance;

    private ImagePickerHelper() {
        mBucketList = new HashMap<>();
    }

    public static ImagePickerHelper getHelper() {
        if (instance == null) {
            instance = new ImagePickerHelper();
        }
        return instance;
    }

    public void init(Context context) {
        if (this.context == null) {
            this.context = context;
            this.contentResolver = context.getContentResolver();
        }
    }

    private void getThumbnail() {
        String[] projection = {Thumbnails._ID, Thumbnails.IMAGE_ID, Thumbnails.DATA};
        Cursor cursor = contentResolver.query(Thumbnails.EXTERNAL_CONTENT_URI, projection, null, null, null);
        getThumbnailColumnData(cursor);
    }

    private void getThumbnailColumnData(Cursor cur) {
        mThumbnailList.clear();
        if (cur.moveToFirst()) {
            int image_id;
            int image_idColumn = cur.getColumnIndex(Thumbnails.IMAGE_ID);
            int dataColumn = cur.getColumnIndex(Thumbnails.DATA);
            String image_path;

            do {
                image_id = cur.getInt(image_idColumn);
                image_path = cur.getString(dataColumn);

                mThumbnailList.put("" + image_id, image_path);
            } while (cur.moveToNext());
        }
    }

    private void buildImagesBucketList() {
        getThumbnail();
        mBucketList.clear();

        String columns[] = new String[]{Media._ID, Media.BUCKET_ID, Media.PICASA_ID, Media.DATA, Media.DISPLAY_NAME, Media.TITLE, Media.SIZE, Media.BUCKET_DISPLAY_NAME};
        Cursor cursor = contentResolver.query(Media.EXTERNAL_CONTENT_URI, columns, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            int photoIDIndex = cursor.getColumnIndexOrThrow(Media._ID);
            int photoPathIndex = cursor.getColumnIndexOrThrow(Media.DATA);
            int bucketDisplayNameIndex = cursor.getColumnIndexOrThrow(Media.BUCKET_DISPLAY_NAME);
            int bucketIdIndex = cursor.getColumnIndexOrThrow(Media.BUCKET_ID);

            do {
                String _id = cursor.getString(photoIDIndex);
                String path = cursor.getString(photoPathIndex);
                String bucketName = cursor.getString(bucketDisplayNameIndex);
                String bucketId = cursor.getString(bucketIdIndex);

                ImageBucket bucket = mBucketList.get(bucketId);
                if (bucket == null) {
                    bucket = new ImageBucket();
                    mBucketList.put(bucketId, bucket);
                    bucket.bucketList = new ArrayList<>();
                    bucket.bucketName = bucketName;
                }
                bucket.count++;
                ImageItem imageItem = new ImageItem();
                imageItem.setImageId(_id);
                imageItem.setImagePath(path);
                imageItem.setThumbnailPath(mThumbnailList.get(_id));
                bucket.bucketList.add(imageItem);

            } while (cursor.moveToNext());
        }
        boolean hasBuildImagesBucketList = true;
    }

    public List<ImageBucket> getImagesBucketList() {
        buildImagesBucketList();
        List<ImageBucket> tmpList = new ArrayList<>();
        for (Entry<String, ImageBucket> entry : mBucketList.entrySet()) {
            tmpList.add(entry.getValue());
        }
        return tmpList;
    }

}
