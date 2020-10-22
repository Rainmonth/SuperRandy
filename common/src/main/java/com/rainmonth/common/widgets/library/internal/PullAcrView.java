package com.rainmonth.common.widgets.library.internal;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.rainmonth.common.R;
import com.rainmonth.common.utils.DensityUtils;
import com.socks.library.KLog;

/**
 * @author RandyZhang
 * @date 2020/10/21 3:59 PM
 */
public class PullAcrView extends LinearLayout {

    TextView tvTipLabel;

    private int mLabelWidth = DensityUtils.dip2px(getContext(), 12);

    private int mWidth, mHeight;
    private PointF mStartPoint = new PointF(100, 200);
    private PointF mEndPoint = new PointF(100, 500);

    private PointF mControlPoint = new PointF(50, 350);

    private OnPullListener mOnPullListener;

    /**
     * 屏幕宽度
     */
    private int mScreenWidth;
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
    private int mFillColor = Color.GREEN;

    /**
     * 最大滑动的距离
     */
    private int mMaxScrollDistance = DensityUtils.dip2px(getContext(), 150);


    public PullAcrView(Context context) {
        this(context, null);

    }

    public PullAcrView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PullAcrView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mScreenWidth = context.getResources().getDisplayMetrics().widthPixels;

        View.inflate(context, R.layout.image_pull_arc_view, this);
        tvTipLabel = findViewById(R.id.tv_tip_label);
        setGravity(Gravity.CENTER_VERTICAL);
        setOrientation(VERTICAL);

        mArcPath = new Path();

        mArcPaint = new Paint();
        mArcPaint.setAntiAlias(true);
        mArcPaint.setColor(mFillColor);
        mArcPaint.setStyle(Paint.Style.FILL);

    }

    private float mDownX, mDownY;

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownX = event.getX();
                mDownY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                // move 处理
                float moveX = event.getX();
                float moveY = event.getY();
                float deltaX = mDownX - moveX;
                if (deltaX >= 0) {// 从右向左
                    deltaX = Math.min(mMaxScrollDistance, mDownX - moveX);
                    mControlPoint.x = mWidth - deltaX;

                    tvTipLabel.setTranslationX(mWidth - (deltaX / mMaxScrollDistance * mWidth));
                    if (mOnPullListener != null) {
                        mOnPullListener.onPull(deltaX);
                    }
                    invalidate();
                } else { // 从左向右

                }

                break;
            case MotionEvent.ACTION_UP:
                // up 处理
                reset();
                float upX = event.getX();
                if (mDownX - upX >= mMaxScrollDistance) {
                    if (mOnPullListener != null) {
                        mOnPullListener.onPullRelease();
                    }
                } else {
                    if (mOnPullListener != null) {
                        mOnPullListener.onPullCancel();
                    }
                }

                break;
        }

        return true;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mWidth = w;
        mHeight = h;
        KLog.d("RandyTest", "onSizeChanged(), w:" + w + ",h:" + h);
        mStartPoint.x = w;
        mStartPoint.y = h / 4f;

        mControlPoint.x = w;
        mControlPoint.y = h / 2f;

        mEndPoint.x = w;
        mEndPoint.y = h * 3 / 4f;

        mLabelWidth = tvTipLabel.getMeasuredWidth();

        KLog.d("RandyTest", "translationX:" + tvTipLabel.getTranslationX());
        tvTipLabel.setTranslationX(mLabelWidth);
        KLog.d("RandyTest", "translationX:" + tvTipLabel.getTranslationX());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mArcPath.reset();
        // 移到起点
        KLog.e("RandyTest", "startPoint:" + mStartPoint);
        KLog.e("RandyTest", "controlPoint:" + mControlPoint);
        KLog.e("RandyTest", "endPoint:" + mEndPoint);

        mArcPath.moveTo(mStartPoint.x, mStartPoint.y);
        mArcPath.quadTo(mControlPoint.x, mControlPoint.y, mEndPoint.x, mEndPoint.y);

        mArcPath.close();
        canvas.drawPath(mArcPath, mArcPaint);

        canvas.drawPoint(mControlPoint.x, mControlPoint.y, mArcPaint);
    }

    private void reset() {
        mStartPoint.x = mWidth;
        mStartPoint.y = 0;

        mControlPoint.x = mWidth;
        mControlPoint.y = mHeight / 2f;

        mEndPoint.x = mWidth;
        mEndPoint.y = mHeight;

        tvTipLabel.setTranslationX(mLabelWidth);
        invalidate();
    }

    public void onPull(float pullDistance) {
        KLog.d("RandyTest", pullDistance);


        if (pullDistance >= 0) {// 从右向左
            pullDistance = Math.min(mWidth, pullDistance);
            mControlPoint.x = mWidth - pullDistance;

            tvTipLabel.setTranslationX(mWidth - (pullDistance / mMaxScrollDistance * mWidth));
            invalidate();
        }
    }

    public void onReset() {
        reset();
    }

    public void onRelease() {

    }

    public void setOnPullListener(OnPullListener listener) {
        this.mOnPullListener = listener;
    }

    public interface OnPullListener {

        void onPull(float pullDistance);

        void onPullRelease();

        void onPullCancel();
    }
}
