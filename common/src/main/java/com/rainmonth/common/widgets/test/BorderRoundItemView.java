package com.rainmonth.common.widgets.test;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.rainmonth.common.R;
import com.rainmonth.common.bean.BaseBean;
import com.rainmonth.common.utils.DensityUtils;
import com.socks.library.KLog;

/**
 * @author RandyZhang
 * @date 2020/10/15 11:29 AM
 */
public class BorderRoundItemView<T> extends FrameLayout {
    private static final String TAG = BorderRoundItemView.class.getSimpleName();
    /**
     * 宽度占屏幕百分比， -1表示不根据百分比计算
     */
    private float mWidthPercentage = -1;
    /**
     * 宽高比
     */
    private float mRate = 32 / 25f;

    private float mTopLeftRadius = DensityUtils.dip2px(getContext(), 10);
    private float mBottomLeftRadius = DensityUtils.dip2px(getContext(), 10);
    private float mTopRightRadius = DensityUtils.dip2px(getContext(), 18);
    private float mBottomRightRadius = DensityUtils.dip2px(getContext(), 18);

    /**
     * 边框宽度
     */
    private float mBorderWidth = DensityUtils.dip2px(getContext(), 3);

    /**
     * 边框颜色
     */
    private int mBorderColor = Color.RED;

    /**
     * 整个View对应的Path
     */
    private Path mViewPath;
    /**
     * border外边缘对应的Path
     */
    private Path mOutlinePath;
    /**
     * border内边缘对应的path
     */
    private Path mInnerPath;

    private float[] mRadii;

    private Paint mRadiusPaint;
    private Paint mBorderPaint;


    private ImageView ivCover;
    private ImageView ivTopRightFlag;
    private ImageView ivBottomRightFlag;
    private TextView tvCount;

    public BorderRoundItemView(Context context) {
        super(context);
        initAttr(context, null);
        initView(context);
    }

    public BorderRoundItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initAttr(context, attrs);
        initView(context);
    }

    public BorderRoundItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttr(context, attrs);
        initView(context);
    }

    private void initAttr(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.BorderRoundItemView);
        try {
            mWidthPercentage = ta.getFloat(R.styleable.BorderRoundItemView_borderRoundItemViewWidthPercentage, -1);
            mRate = ta.getFloat(R.styleable.BorderRoundItemView_borderRoundItemViewRate, mRate);

            mTopLeftRadius = ta.getDimension(R.styleable.BorderRoundItemView_borderRoundItemViewTopLeftRadius, DensityUtils.dip2px(context, 10));
            mBottomLeftRadius = ta.getDimension(R.styleable.BorderRoundItemView_borderRoundItemViewBottomLeftRadius, DensityUtils.dip2px(context, 10));
            mTopRightRadius = ta.getDimension(R.styleable.BorderRoundItemView_borderRoundItemViewTopRightRadius, DensityUtils.dip2px(context, 18));
            mBottomRightRadius = ta.getDimension(R.styleable.BorderRoundItemView_borderRoundItemViewBottomRightRadius, DensityUtils.dip2px(context, 18));

            mBorderWidth = ta.getDimension(R.styleable.BorderRoundItemView_borderRoundItemViewBorderWidth, DensityUtils.dip2px(context, 3));
            mBorderColor = ta.getColor(R.styleable.BorderRoundItemView_borderRoundItemViewBorderColor, Color.WHITE);

            mRadii = new float[8];
            mRadii[0] = mTopLeftRadius;
            mRadii[1] = mTopLeftRadius;
            mRadii[2] = mTopRightRadius;
            mRadii[3] = mTopRightRadius;
            mRadii[4] = mBottomRightRadius;
            mRadii[5] = mBottomRightRadius;
            mRadii[6] = mBottomLeftRadius;
            mRadii[7] = mBottomLeftRadius;

        } finally {
            if (ta != null) {
                ta.recycle();
            }
        }
    }

    private void initView(Context context) {

        View.inflate(context, R.layout.test_view, this);
        ivCover = findViewById(R.id.iv_cover);
        ivTopRightFlag = findViewById(R.id.iv_top_right_flag);
        ivBottomRightFlag = findViewById(R.id.iv_bottom_right_flag);
        tvCount = findViewById(R.id.tv_count);

        KLog.d(TAG, "initView: " + "w=" + getWidth() + ",h=" + getHeight() + ",mw=" + getMeasuredWidth() + ",mh=" + getMeasuredHeight());
        mViewPath = new Path();
        mOutlinePath = new Path();
        mInnerPath = new Path();

        mRadiusPaint = new Paint();
        mRadiusPaint.setColor(Color.WHITE);
        mRadiusPaint.setAntiAlias(true);
        mRadiusPaint.setStyle(Paint.Style.FILL);

        mBorderPaint = new Paint();
        // 不要带透明的颜色都可
        mBorderPaint.setColor(mBorderColor);
        mBorderPaint.setAntiAlias(true);
        mBorderPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        KLog.d(TAG, "onFinishInflate: " + "w=" + getWidth() + ",h=" + getHeight() + ",mw=" + getMeasuredWidth() + ",mh=" + getMeasuredHeight());
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        KLog.d(TAG, "onAttachedToWindow: " + "w=" + getWidth() + ",h=" + getHeight() + ",mw=" + getMeasuredWidth() + ",mh=" + getMeasuredHeight());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int sw = getResources().getDisplayMetrics().widthPixels;
        KLog.d(TAG, "onMeasure: " + "screenWidth=" + getResources().getDisplayMetrics().widthPixels + ",screeHeight=" + getResources().getDisplayMetrics().heightPixels);
        KLog.d(TAG, "onMeasure: " + "w=" + getWidth() + ",h=" + getHeight() + ",mw=" + getMeasuredWidth() + ",mh=" + getMeasuredHeight());
        if (mWidthPercentage == -1) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            return;
        }

        if (mWidthPercentage > 0) {
            mWidthPercentage = Math.min(mWidthPercentage, 1);
        }
        final int width = MeasureSpec.getSize(widthMeasureSpec);
        KLog.e(TAG, "onMeasure: " + "w=" + getWidth() + ",h=" + getHeight() + ",mw=" + getMeasuredWidth() + ",mh=" + getMeasuredHeight() + ",width=" + width);
        int newWidth = Math.round(sw * mWidthPercentage);

        if (mRate == -1) {
            super.onMeasure(MeasureSpec.makeMeasureSpec(newWidth, MeasureSpec.EXACTLY), heightMeasureSpec);
            KLog.e(TAG, "onMeasure: " + "w=" + getWidth() + ",h=" + getHeight() + ",mw=" + getMeasuredWidth() + ",mh=" + getMeasuredHeight() + ",width=" + width);
            setMeasuredDimension(newWidth, MeasureSpec.getSize(heightMeasureSpec));
            KLog.e(TAG, "onMeasure: " + "w=" + getWidth() + ",h=" + getHeight() + ",mw=" + getMeasuredWidth() + ",mh=" + getMeasuredHeight() + ",width=" + width);
            return;
        }

        if (mRate > 0) {
            int newHeight = Math.round(newWidth * mRate);
            super.onMeasure(MeasureSpec.makeMeasureSpec(newWidth, MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(newHeight, MeasureSpec.EXACTLY));
            KLog.e(TAG, "onMeasure: " + "w=" + getWidth() + ",h=" + getHeight() + ",mw=" + getMeasuredWidth() + ",mh=" + getMeasuredHeight() + ",width=" + width);
            setMeasuredDimension(newWidth, newHeight);
            KLog.e(TAG, "onMeasure: " + "w=" + getWidth() + ",h=" + getHeight() + ",mw=" + getMeasuredWidth() + ",mh=" + getMeasuredHeight() + ",width=" + width);
            return;
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        KLog.d(TAG, "onSizeChanged: " + "w=" + getWidth() + ",h=" + getHeight() + ",mw=" + getMeasuredWidth() + ",mh=" + getMeasuredHeight());
        // 重置
        mViewPath.reset();
        // 初始化View原始path
        mViewPath.addRect(0, 0, w, h, Path.Direction.CW);

        mOutlinePath.addRoundRect(new RectF(0, 0, w, h), mRadii, Path.Direction.CW);
        mInnerPath.addRoundRect(new RectF(0, mBorderWidth, w - mBorderWidth, h - mBorderWidth), mRadii, Path.Direction.CW);

        mViewPath.op(mOutlinePath, Path.Op.DIFFERENCE);
        mOutlinePath.op(mInnerPath, Path.Op.DIFFERENCE);

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        KLog.d(TAG, "onLayout: " + "w=" + getWidth() + ",h=" + getHeight() + ",mw=" + getMeasuredWidth() + ",mh=" + getMeasuredHeight());
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        KLog.d(TAG, "draw: " + "w=" + getWidth() + ",h=" + getHeight() + ",mw=" + getMeasuredWidth() + ",mh=" + getMeasuredHeight());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        KLog.d(TAG, "onDraw: " + "w=" + getWidth() + ",h=" + getHeight() + ",mw=" + getMeasuredWidth() + ",mh=" + getMeasuredHeight());
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {

        KLog.d(TAG, "dispatchDraw: " + "w=" + getWidth() + ",h=" + getHeight() + ",mw=" + getMeasuredWidth() + ",mh=" + getMeasuredHeight());
        int count = canvas.saveLayer(new RectF(0, 0, canvas.getWidth(), canvas.getHeight()), null, Canvas
                .ALL_SAVE_FLAG);
        super.dispatchDraw(canvas);
        mRadiusPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
        canvas.drawPath(mViewPath, mRadiusPaint);
        mRadiusPaint.setXfermode(null);
        canvas.restoreToCount(count);

        // 绘制边框
        canvas.drawPath(mOutlinePath, mBorderPaint);
    }


    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        KLog.d(TAG, "onDetachedFromWindow: " + "w=" + getWidth() + ",h=" + getHeight() + ",mw=" + getMeasuredWidth() + ",mh=" + getMeasuredHeight());
    }

    public void update(T data) {

    }

    public void showCover(int resId) {
        ivCover.setImageResource(resId);
    }

    public void handleCount(String text, boolean isShow) {
        tvCount.setVisibility(isShow ? VISIBLE : GONE);
        if (isShow && !TextUtils.isEmpty(text)) {
            tvCount.setText(text);
        }
    }
}