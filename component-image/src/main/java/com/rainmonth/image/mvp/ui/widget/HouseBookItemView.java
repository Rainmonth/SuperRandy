package com.rainmonth.image.mvp.ui.widget;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.rainmonth.common.utils.DensityUtils;

/**
 * 1、支持不规则边框
 * 2、支持不规则圆角
 * 3、支持设置宽高比
 *
 * @author RandyZhang
 * @date 2020/10/13 3:39 PM
 */
public class HouseBookItemView extends FrameLayout {


    ImageView cover;
    View newFlag;
    View countFlag;
    View bottomRightFlag;       // 可能是限免、推荐、VIP、精品之一

    /**
     * 是否统一圆角（即四个角圆角大小一致）
     */
    private boolean isUniformRadius = true;

    /**
     * 内部统一圆角，优先级高于 mInnerRadiiArray 中的圆角设置 *
     */
    private float mInnerUniformRadius;
    /**
     * 外部统一圆角，优先级高于 mOuterRadiiArray 中的圆角设置
     */
    private float mOuterUniformRadius;
    /**
     * 内部封面圆角数据
     */
    private float[] mInnerRadiiArray;
    /**
     * 外部边框圆角数组
     */
    private float[] mOuterRadiiArray;
    /**
     * 左 边框宽度
     */
    private float mLeftBorderWidth = DensityUtils.dip2px(getContext(), 3);
    /**
     * 上 边框宽度
     */
    private float mTopBorderWidth = DensityUtils.dip2px(getContext(), 3);
    /**
     * 右 边框宽度
     */
    private float mRightBorderWidth = DensityUtils.dip2px(getContext(), 3);
    /**
     * 下 边框宽度
     */
    private float mBottomBorderWidth = DensityUtils.dip2px(getContext(), 3);
    /**
     * 边框颜色
     */
    private int mOutBorderColor = Color.WHITE;

    private Context mContext;

    public HouseBookItemView(@NonNull Context context) {
        this(context, null);
    }

    public HouseBookItemView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HouseBookItemView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        Log.d("Randy", "itemView init");

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
