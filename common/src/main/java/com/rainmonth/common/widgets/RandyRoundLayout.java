package com.rainmonth.common.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.rainmonth.common.R;
import com.rainmonth.common.utils.DensityUtils;

/**
 * 本身背景不会有圆角
 *
 * @author 张豪成
 * @date 2019-11-25 17:46
 */
public class RandyRoundLayout extends RandyRateLayout {

    private Paint mPaint;
    private Path mPath;
    protected float[] mRadii;
    protected float mRadiusPercent;
    private boolean isHighQuality = true;

    public RandyRoundLayout(@NonNull Context context) {
        super(context);
    }

    public RandyRoundLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RandyRoundLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public RandyRoundLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @CallSuper
    @Override
    protected void initView(Context context) {
        super.initView(context);
        mPaint = new Paint();
        // 不要带透明的颜色都可
        mPaint.setColor(Color.WHITE);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPath = new Path();
    }

    @Override
    protected void initAttr(Context context, AttributeSet attrs) {
        super.initAttr(context, attrs);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.RandyRoundLayout);
        final float radius = ta.getDimension(R.styleable.RandyRoundLayout_roundRadius, DensityUtils.dip2px(context, 12));
        final float radiusPercent = ta.getFloat(R.styleable.RandyRoundLayout_roundRadiusPercentage, 0);
        final float leftTopRadius = ta.getDimension(R.styleable.RandyRoundLayout_roundLeftTopRadius, radius);
        final float rightTopRadius = ta.getDimension(R.styleable.RandyRoundLayout_roundRightTopRadius, radius);
        final float leftBottomRadius = ta.getDimension(R.styleable.RandyRoundLayout_roundLeftBottomRadius, radius);
        final float rightBottomRadius = ta.getDimension(R.styleable.RandyRoundLayout_roundRightBottomRadius, radius);
        isHighQuality = ta.getBoolean(R.styleable.RandyRoundLayout_roundHighQuality, true);
        ta.recycle();
        if (radiusPercent <= 0) {
            mRadiusPercent = 0;
            mRadii = new float[]{leftTopRadius, leftTopRadius, rightTopRadius, rightTopRadius, leftBottomRadius, leftBottomRadius, rightBottomRadius, rightBottomRadius};
        } else if (radiusPercent >= 1) {
            mRadiusPercent = 1;
            mRadii = new float[8];
        } else {
            mRadiusPercent = radiusPercent;
            mRadii = new float[8];
        }
    }

    /**
     * 在layout过程中如果view的size发生了变化就会调用该方法（setLeft、setRight、setTop、setBottom、setFrame）
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (mRadiusPercent > 0 && mRadiusPercent <= 1) {
            final float radius = h * mRadiusPercent;
            mRadii[0] = radius;
            mRadii[1] = radius;
            mRadii[2] = radius;
            mRadii[3] = radius;
            mRadii[4] = radius;
            mRadii[5] = radius;
            mRadii[6] = radius;
            mRadii[7] = radius;
        }
        // 计算path
        mPath.reset();
        if (isHighQuality) {
            final Path path = new Path();
            mPath.addRect(0, 0, w, h, Path.Direction.CW);
            path.addRoundRect(new RectF(getPaddingLeft(), getPaddingTop(), w - getPaddingRight(), h - getPaddingBottom()),
                    mRadii,
                    Path.Direction.CW);
            mPath.op(path, Path.Op.DIFFERENCE);
        } else {
            mPath.addRoundRect(new RectF(getPaddingLeft(), getPaddingTop(), w - getPaddingRight(), h - getPaddingBottom()),
                    mRadii,
                    Path.Direction.CW);
        }
    }

    /**
     * 该方法在draw过程中被调用，子类重写该方法可以获取自己绘制之后其子View绘制之前的控制权
     */
    @Override
    protected void dispatchDraw(Canvas canvas) {
        if (isHighQuality) {
            int count = canvas.saveLayer(new RectF(0, 0, canvas.getWidth(), canvas.getHeight()), null, Canvas
                    .ALL_SAVE_FLAG);
            super.dispatchDraw(canvas);
            mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
            canvas.drawPath(mPath, mPaint);
            mPaint.setXfermode(null);
            canvas.restoreToCount(count);
            return;
        }
        int save = canvas.save();
        canvas.clipPath(mPath);
        super.dispatchDraw(canvas);
        canvas.restoreToCount(save);
    }

    public void setRoundCorner(float lt, float rt, float lb, float rb) {
        mRadii[0] = lt;
        mRadii[1] = lt;
        mRadii[2] = rt;
        mRadii[3] = rt;
        mRadii[4] = lb;
        mRadii[5] = lb;
        mRadii[6] = rb;
        mRadii[7] = rb;
        invalidate();
    }
}
