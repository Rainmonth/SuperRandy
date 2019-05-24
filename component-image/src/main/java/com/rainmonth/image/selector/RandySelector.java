package com.rainmonth.image.selector;

/**
 * @author 张豪成
 * @date 2019-05-24 17:08
 */
public class RandySelector {
    /**
     * 选择图片和视频
     */
    public static final int FILE_TYPE_BOTH = 0;
    /**
     * 选择图片
     */
    public static final int FILE_TYPE_IMAGE = 1;
    /**
     * 选择视频
     */
    public static final int FILE_TYPE_VIDEO = 2;

    /**
     * 采用Glide图片加载框架
     */
    public static final int IMAGE_LOADER_TYPE_GLIDE = 0;
    /**
     * 采用Fresco图片加载框架
     */
    public static final int IMAGE_LOADER_TYPE_FRESCO = 1;

    /**
     * 文件类型，图片、视频
     * {@value FILE_TYPE_BOTH}
     * {@value FILE_TYPE_IMAGE}
     * {@value FILE_TYPE_VIDEO}
     */
    int fileType = FILE_TYPE_IMAGE;
    /**
     * 是否多选
     */
    boolean isMultiChoose = false;
    /**
     * 多选最多最多选取文件个数
     */
    int maxSelectSize = 9;
    /**
     * 展示多少栏
     */
    int displayColumn = 3;

    /**
     * 图片显示用的框架
     * {@value IMAGE_LOADER_TYPE_GLIDE}、{@value IMAGE_LOADER_TYPE_GLIDE}
     */
    int imageLoaderType = IMAGE_LOADER_TYPE_GLIDE;

    /**
     * 是否支持预览
     */
    boolean isSupportPreview = false;

    /**
     * 开始拍照
     */
    public void startTakePhoto() {

    }

    /**
     * 开始录制
     */
    public void startRecordVideo() {

    }
}
