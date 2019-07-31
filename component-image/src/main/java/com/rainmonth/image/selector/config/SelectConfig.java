package com.rainmonth.image.selector.config;

import androidx.annotation.StyleRes;

/**
 * 图片选择配置
 *
 * @author 张豪成
 * @date 2019-05-24 18:28
 */
public class SelectConfig {
    /**
     * 选择的文件类型
     */
    public int mimeType;
    /**
     * 是否显示相机按钮（显示的话点击可以进行拍照或录像）
     */
    public boolean camera;
    /**
     * 拍摄的照片或视频的显示路径
     */
    public String outputCameraPath;
    /**
     * 压缩文件的保存路径
     */
    public String compressSavePath;
    /**
     * 文件后缀类型
     */
    public String suffixType;
    /**
     * 主题配置
     */
    @StyleRes
    public int themeStyleId;
    /**
     * 选择模式（单选还是多选）
     */
    public int selectionMode;
    /**
     * 最多选择文件数
     */
    public int maxSelectNum;
    /**
     * 最少选择文件数
     */
    public int minSelectNum;
    /**
     * 视频质量（录屏）
     */
    public int videoQuality;
    /**
     * 裁剪压缩的质量
     */
    public int cropCompressQuality;
    /**
     * 视频最大长度
     */
    public int videoMaxSecond;
    /**
     * 视频最小长度
     */
    public int videoMinSecond;
    /**
     * 录屏秒数（长度）
     */
    public int recordVideoSecond;
    /**
     * 最小压缩值
     */
    public int minimumCompressSize;
    /**
     * RecyclerView的栏数
     */
    public int imageSpanCount;
    /**
     * 图片加载时的宽度（Glide）
     */
    public int overrideWidth;
    /**
     * 图片加载时的高度（Glide）
     */
    public int overrideHeight;
    /**
     * x方向裁剪比例
     */
    public int aspect_ratio_x;
    /**
     * y方向裁剪比例
     */
    public int aspect_ratio_y;

    public float sizeMultiplier;
    /**
     * 裁剪宽度
     */
    public int cropWidth;
    /**
     * 裁剪高度
     */
    public int cropHeight;
    /**
     * 是否缩放动效
     */
    public boolean zoomAnim;
    /**
     *
     */
    public boolean isCompress;
    /**
     *
     */
    public boolean isCamera;
    /**
     *
     */
    public boolean isGif;
    /**
     * 是否开启预览
     */
    public boolean enablePreview;
    /**
     * 是否开启视频预览
     */
    public boolean enPreviewVideo;
    /**
     * 是否开启音频预览
     */
    public boolean enablePreviewAudio;
    /**
     *
     */
    public boolean checkNumMode;
    /**
     * 是否开启点击音效
     */
    public boolean openClickSound;
    /**
     * 是否开启裁剪
     */
    public boolean enableCrop;
    public boolean freeStyleCropEnabled;
    public boolean circleDimmedLayer;
    public boolean showCropFrame;
    public boolean showCropGrid;
    public boolean hideBottomControls;
    public boolean rotateEnabled;
    public boolean scaleEnabled;
    public boolean previewEggs;
    public boolean synOrAsy;
    public boolean isDragFrame;
}
