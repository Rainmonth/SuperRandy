package com.rainmonth.image.selector.config;

import android.support.annotation.StyleRes;

/**
 * 图片选择配置
 * 1、选择的文件类型
 * 2、是否显示相机按钮（显示的话点击可以进行拍照或录像）
 * 3、拍摄的照片或视频的显示路径
 * 4、压缩文件的保存路径
 * 5、文件的后缀类型
 * 6、主题样式配置
 * 7、选择模式（单选还是多选）
 * 8、多选模式下最多选择文件数
 * 9、多选模式下最少选择文件数
 * 10、视频质量
 * 11、裁剪及压缩质量
 * 12、视频最大长度（单位s）
 * 13、视频最小长度（单位s）
 * 14、录像时长设置（单位s）
 * 15、最小压缩值
 * 16、选择页面一行显示的个数
 * 17、
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

    public int cropCompressQuality;
    public int videoMaxSecond;
    public int videoMinSecond;
    public int recordVideoSecond;
    public int minimumCompressSize;
    public int imageSpanCount;
    public int overrideWidth;
    public int overrideHeight;
    public int aspect_ratio_x;
    public int aspect_ratio_y;
    public float sizeMultiplier;
    public int cropWidth;
    public int cropHeight;
    public boolean zoomAnim;
    public boolean isCompress;
    public boolean isCamera;
    public boolean isGif;
    public boolean enablePreview;
    public boolean enPreviewVideo;
    public boolean enablePreviewAudio;
    public boolean checkNumMode;
    public boolean openClickSound;
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
