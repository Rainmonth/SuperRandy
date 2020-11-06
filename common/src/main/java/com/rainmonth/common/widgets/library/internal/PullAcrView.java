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
    private Paint mLinePaint;
    /**
     * #16C3FF
     * 填充色
     */
    private int mFillColor = Color.parseColor("#16C3FF");

    /**
     * 最大滑动的距离
     */
    private int mMaxScrollDistance = DensityUtils.dip2px(getContext(), 76);


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
        setWillNotDraw(false);
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

        mLinePaint = new Paint();
        mLinePaint.setAntiAlias(true);
        mArcPaint.setColor(Color.MAGENTA);
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
                    deltaX = Math.min(1.5f * mWidth, deltaX);
                    mControlPoint.x = (mWidth - deltaX) * 2;

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

        mLabelWidth = tvTipLabel.getMeasuredWidth();

        KLog.d("RandyTest", "translationX:" + tvTipLabel.getTranslationX());
        tvTipLabel.setTranslationX(mLabelWidth);
        KLog.d("RandyTest", "translationX:" + tvTipLabel.getTranslationX());
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mArcPath.reset();
        // 移到起点
        KLog.d("RandyTest", "startPoint:" + mStartPoint + ", controlPoint:" + mControlPoint + ", endPoint:" + mEndPoint);

        mArcPath.moveTo(mStartPoint.x, mStartPoint.y);
        mArcPath.quadTo(mControlPoint.x, mControlPoint.y, mEndPoint.x, mEndPoint.y);
        canvas.drawLine(mStartPoint.x, mStartPoint.y, mControlPoint.x, mControlPoint.y, mLinePaint);
        canvas.drawLine(mControlPoint.x, mControlPoint.y, mEndPoint.x, mEndPoint.y, mLinePaint);

        mArcPath.close();
        canvas.drawPath(mArcPath, mArcPaint);
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

    public void onPullToRefresh(float pullDistance) {
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

    public void onReleaseToRefresh() {

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
