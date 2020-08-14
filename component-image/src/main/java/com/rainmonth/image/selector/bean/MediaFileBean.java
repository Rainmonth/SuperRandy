package com.rainmonth.image.selector.bean;

/**
 * 媒体文件（视频、音乐、图片）实体类
 *
 * @author 张豪成
 * @date 2019-05-30 10:00
 */
public class MediaFileBean {

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

}
