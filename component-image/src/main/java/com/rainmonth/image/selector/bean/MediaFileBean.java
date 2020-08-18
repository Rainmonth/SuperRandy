package com.rainmonth.image.selector.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 媒体文件（视频、音乐、图片）实体类
 *
 * @author 张豪成
 * @date 2019-05-30 10:00
 */
public class MediaFileBean implements Parcelable {

    /**
     * 文件id
     */
    public long id;
    /**
     * 文件路径
     */
    public String path;
    /**
     * 真实路径，AndroidQ上无法获取
     */
    public String realPath;
    /**
     *
     */
    public String originalPath;
    /**
     * 压缩后的文件保存路径
     */
    public String compressedPath;
    /**
     * 裁剪保存的路径
     */
    public String cutPath;
    public String androidQPath;
    /**
     * 音视频的长度
     */
    public long duration;

    //<editor-fold>
    public boolean isChecked;
    public boolean isCut;
    public int position;
    public int num;
    //</editor-fold>

    public String mimeType;
    public int chooseModel;
    public boolean compressed;

    public int width;
    public int height;

    public long size;
    public boolean isOriginal;
    public String fileName;
    public String parentFolderName;
    public int orientation = -1;

    public int loadLongImageStatus;
    public boolean isLongImage;

    private long bucketId = -1;

    private boolean isMaxSelectedEnabledMask;

    public MediaFileBean() {
    }

    public MediaFileBean(String path, long duration, String mimeType, int chooseModel) {
        this.path = path;
        this.duration = duration;
        this.mimeType = mimeType;
        this.chooseModel = chooseModel;
    }

    public MediaFileBean(long id, String path, String fileName, String parentFolderName, long duration, int chooseModel,
                      String mimeType, int width, int height, long size) {
        this.id = id;
        this.path = path;
        this.fileName = fileName;
        this.parentFolderName = parentFolderName;
        this.duration = duration;
        this.chooseModel = chooseModel;
        this.mimeType = mimeType;
        this.width = width;
        this.height = height;
        this.size = size;
    }

    public MediaFileBean(long id, String path, String absolutePath, String fileName, String parentFolderName, long duration, int chooseModel,
                      String mimeType, int width, int height, long size, long bucketId) {
        this.id = id;
        this.path = path;
        this.realPath = absolutePath;
        this.fileName = fileName;
        this.parentFolderName = parentFolderName;
        this.duration = duration;
        this.chooseModel = chooseModel;
        this.mimeType = mimeType;
        this.width = width;
        this.height = height;
        this.size = size;
        this.bucketId = bucketId;
    }

    public MediaFileBean(String path, long duration,
                      boolean isChecked, int position, int num, int chooseModel) {
        this.path = path;
        this.duration = duration;
        this.isChecked = isChecked;
        this.position = position;
        this.num = num;
        this.chooseModel = chooseModel;
    }

    protected MediaFileBean(Parcel in) {
        id = in.readLong();
        path = in.readString();
        realPath = in.readString();
        originalPath = in.readString();
        compressedPath = in.readString();
        cutPath = in.readString();
        androidQPath = in.readString();
        duration = in.readLong();
        isChecked = in.readByte() != 0;
        isCut = in.readByte() != 0;
        position = in.readInt();
        num = in.readInt();
        mimeType = in.readString();
        chooseModel = in.readInt();
        compressed = in.readByte() != 0;
        width = in.readInt();
        height = in.readInt();
        size = in.readLong();
        isOriginal = in.readByte() != 0;
        fileName = in.readString();
        parentFolderName = in.readString();
        orientation = in.readInt();
        loadLongImageStatus = in.readInt();
        isLongImage = in.readByte() != 0;
        bucketId = in.readLong();
        isMaxSelectedEnabledMask = in.readByte() != 0;
    }

    public static final Creator<MediaFileBean> CREATOR = new Creator<MediaFileBean>() {
        @Override
        public MediaFileBean createFromParcel(Parcel in) {
            return new MediaFileBean(in);
        }

        @Override
        public MediaFileBean[] newArray(int size) {
            return new MediaFileBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(path);
        dest.writeString(realPath);
        dest.writeString(originalPath);
        dest.writeString(compressedPath);
        dest.writeString(cutPath);
        dest.writeString(androidQPath);
        dest.writeLong(duration);
        dest.writeByte((byte) (isChecked ? 1 : 0));
        dest.writeByte((byte) (isCut ? 1 : 0));
        dest.writeInt(position);
        dest.writeInt(num);
        dest.writeString(mimeType);
        dest.writeInt(chooseModel);
        dest.writeByte((byte) (compressed ? 1 : 0));
        dest.writeInt(width);
        dest.writeInt(height);
        dest.writeLong(size);
        dest.writeByte((byte) (isOriginal ? 1 : 0));
        dest.writeString(fileName);
        dest.writeString(parentFolderName);
        dest.writeInt(orientation);
        dest.writeInt(loadLongImageStatus);
        dest.writeByte((byte) (isLongImage ? 1 : 0));
        dest.writeLong(bucketId);
        dest.writeByte((byte) (isMaxSelectedEnabledMask ? 1 : 0));
    }
}
