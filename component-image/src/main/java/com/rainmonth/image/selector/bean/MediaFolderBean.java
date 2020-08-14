package com.rainmonth.image.selector.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * 媒体文件夹实体类
 *
 * @author 张豪成
 * @date 2019-05-30 10:01
 */
public class MediaFolderBean implements Parcelable {

    public long bucketId;
    public String name;
    public String firstImagePath;
    public int folderMediaNum;
    public int folderSelectNum;
    public boolean isSelected;
    public boolean isCameraFolder;
    public int type;

    public List<MediaFileBean> data = new ArrayList<>();

    /**
     * 内部使用
     */
    public int currentPage;
    /**
     * 内部使用
     */
    public boolean isHasMore;

    public long getBucketId() {
        return bucketId;
    }

    public void setBucketId(long bucketId) {
        this.bucketId = bucketId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstImagePath() {
        return firstImagePath;
    }

    public void setFirstImagePath(String firstImagePath) {
        this.firstImagePath = firstImagePath;
    }

    public int getFolderMediaNum() {
        return folderMediaNum;
    }

    public void setFolderMediaNum(int folderMediaNum) {
        this.folderMediaNum = folderMediaNum;
    }

    public int getFolderSelectNum() {
        return folderSelectNum;
    }

    public void setFolderSelectNum(int folderSelectNum) {
        this.folderSelectNum = folderSelectNum;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean isCameraFolder() {
        return isCameraFolder;
    }

    public void setCameraFolder(boolean cameraFolder) {
        isCameraFolder = cameraFolder;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<MediaFileBean> getData() {
        return data;
    }

    public void setData(List<MediaFileBean> data) {
        this.data = data;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public boolean isHasMore() {
        return isHasMore;
    }

    public void setHasMore(boolean hasMore) {
        isHasMore = hasMore;
    }

    protected MediaFolderBean(Parcel in) {
        bucketId = in.readLong();
        name = in.readString();
        firstImagePath = in.readString();
        folderMediaNum = in.readInt();
        folderSelectNum = in.readInt();
        isSelected = in.readByte() != 0;
        isCameraFolder = in.readByte() != 0;
        type = in.readInt();
    }

    public static final Creator<MediaFolderBean> CREATOR = new Creator<MediaFolderBean>() {
        @Override
        public MediaFolderBean createFromParcel(Parcel in) {
            return new MediaFolderBean(in);
        }

        @Override
        public MediaFolderBean[] newArray(int size) {
            return new MediaFolderBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(bucketId);
        dest.writeString(name);
        dest.writeString(firstImagePath);
        dest.writeInt(folderMediaNum);
        dest.writeInt(folderSelectNum);
        dest.writeByte((byte) (isSelected ? 1 : 0));
        dest.writeByte((byte) (isCameraFolder ? 1 : 0));
        dest.writeInt(type);
    }
}
