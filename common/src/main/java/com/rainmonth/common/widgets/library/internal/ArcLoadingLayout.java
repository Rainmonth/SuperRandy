package com.rainmonth.common.widgets.library.internal;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;

import com.rainmonth.common.utils.DensityUtils;
import com.rainmonth.common.widgets.library.PullToRefreshBase;
import com.socks.library.KLog;

/**
 * @author RandyZhang
 * @date 2020/10/23 11:45 AM
 */
public class ArcLoadingLayout extends LoadingLayout {


    private int mWidth, mHeight;
    private PointF mStartPoint = new PointF(100, 200);
    private PointF mEndPoint = new PointF(100, 500);

    private PointF mControlPoint = new PointF(50, 350);
    private int mLabelWidth = DensityUtils.dip2px(getContext(), 12);
    /**
     * 最大滑动的距离
     */
    private int mMaxScrollDistance = DensityUtils.dip2px(getContext(), 38);
    /**
     * 弧路径
     */
    private Path mArcPath;
    /**
     * 弧画笔
     */
    private Paint mArcPaint;
    /**
     * #16C3FF
     * 填充色
     */
    private int mFillColor = Color.parseColor("#16C3FF");

    public ArcLoadingLayout(Context context, PullToRefreshBase.Mode mode, PullToRefreshBase.Orientation scrollDirection, TypedArray attrs) {
        super(context, mode, scrollDirection, attrs);
        setWillNotDraw(false);
        mArcPath = new Path();

        mArcPaint = new Paint();
        mArcPaint.setAntiAlias(true);
        mArcPaint.setColor(mFillColor);
        mArcPaint.setStyle(Paint.Style.FILL);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
        if (mWidth > 0) {
            mMaxScrollDistance = mWidth;
        }
        KLog.d("RandyTest", "onSizeChanged(), w:" + w + ",h:" + h + ",mMaxScroll:" + mMaxScrollDistance);
        mStartPoint.x = w;
        mStartPoint.y = 0;

        mControlPoint.x = w;
        mControlPoint.y = h / 2f;

        mEndPoint.x = w;
        mEndPoint.y = h;

//		mLabelWidth = tvTipLabel.getMeasuredWidth();
//
//		KLog.d("RandyTest", "translationX:" + tvTipLabel.getTranslationX());
//		tvTipLabel.setTranslationX(mLabelWidth);
//		KLog.d("RandyTest", "translationX:" + tvTipLabel.getTranslationX());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mArcPath.reset();
        // 移到起点
        KLog.d("RandyTest", "startPoint:" + mStartPoint + ", controlPoint:" + mControlPoint + ", endPoint:" + mEndPoint);

        mArcPath.moveTo(mStartPoint.x, mStartPoint.y);
        mArcPath.quadTo(mControlPoint.x, mControlPoint.y, mEndPoint.x, mEndPoint.y);

        mArcPath.close();
        canvas.drawPath(mArcPath, mArcPaint);
    }


    @Override
    protected int getDefaultDrawableResId() {
        return 0;
    }

    @Override
    protected void onLoadingDrawableSet(Drawable imageDrawable) {

    }

    @Override
    protected void onPullImpl(float scaleOfLayout) {
        float pullDistance = mWidth * scaleOfLayout;
        KLog.d("RandyTest", "onPull, mWidth:" + mWidth + ", pullDistance:" + pullDistance);
        if (pullDistance >= 0) {// 从右向左
            pullDistance = Math.min(mWidth, pullDistance);
            mControlPoint.x = mWidth - pullDistance;

//			tvTipLabel.setTranslationX(mWidth - (pullDistance / mMaxScrollDistance * mWidth));
            invalidate();
        }
    }

    @Override
    protected void pullToRefreshImpl() {

    }

    @Override
    protected void refreshingImpl() {

    }

    @Override
    protected void releaseToRefreshImpl() {

    }

    @Override
    protected void resetImpl() {

        mStartPoint.x = mWidth;
        mStartPoint.y = 0;

        mControlPoint.x = mWidth;
        mControlPoint.y = mHeight / 2f;

        mEndPoint.x = mWidth;
        mEndPoint.y = mHeight;

//		tvTipLabel.setTranslationX(mLabelWidth);
        invalidate();
    }
}
