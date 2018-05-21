package com.rainmonth.mvp.model.bean;

import java.io.Serializable;

/**
 * Created by RandyZhang on 16/7/5.
 */
public class RanContentBean implements Serializable {

    private int id;
    private int albumId;// 专辑id
    private String albumAuthor;// 作者
    private int albumFirstImageResId;// 第一张图片地址
    private int albumTotalNum;// 图片总数
    private String albumPublishTime;// 发布时间
    private String albumDescription;// 描述
    private String albumType;//所属分类
    private int albumLikeNum;//

    public RanContentBean(int id, int albumId, String albumAuthor, int albumFirstImageResId, int albumTotalNum, String albumPublishTime,
                          String albumDescription, String albumType, int albumLikeNum) {
        this.id = id;
        this.albumId = albumId;
        this.albumAuthor = albumAuthor;
        this.albumFirstImageResId = albumFirstImageResId;
        this.albumTotalNum = albumTotalNum;
        this.albumPublishTime = albumPublishTime;
        this.albumDescription = albumDescription;
        this.albumType = albumType;
        this.albumLikeNum = albumLikeNum;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAlbumId() {
        return albumId;
    }

    public void setAlbumId(int albumId) {
        this.albumId = albumId;
    }

    public String getAlbumAuthor() {
        return albumAuthor;
    }

    public void setAlbumAuthor(String albumAuthor) {
        this.albumAuthor = albumAuthor;
    }

    public int getAlbumFirstImageResId() {
        return albumFirstImageResId;
    }

    public void setAlbumFirstImageResId(int albumFirstImageResId) {
        this.albumFirstImageResId = albumFirstImageResId;
    }

    public int getAlbumTotalNum() {
        return albumTotalNum;
    }

    public void setAlbumTotalNum(int albumTotalNum) {
        this.albumTotalNum = albumTotalNum;
    }

    public String getAlbumPublishTime() {
        return albumPublishTime;
    }

    public void setAlbumPublishTime(String albumPublishTime) {
        this.albumPublishTime = albumPublishTime;
    }

    public String getAlbumDescription() {
        return albumDescription;
    }

    public void setAlbumDescription(String albumDescription) {
        this.albumDescription = albumDescription;
    }

    public String getAlbumType() {
        return albumType;
    }

    public void setAlbumType(String albumType) {
        this.albumType = albumType;
    }

    public int getAlbumLikeNum() {
        return albumLikeNum;
    }

    public void setAlbumLikeNum(int albumLikeNum) {
        this.albumLikeNum = albumLikeNum;
    }
}
