package com.rainmonth.common.widgets.test;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;

/**
 * @author RandyZhang
 * @date 2020/10/15 11:43 AM
 */
public class TestViewGroup extends ViewGroup {
    private static final String TAG = TestViewGroup.class.getSimpleName();

    public TestViewGroup(Context context) {
        super(context);
        init(context);
    }

    public TestViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public TestViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        Log.d(TAG, "init: " + "w=" + getWidth() + ",h=" + getHeight() + ",mw=" + getMeasuredWidth() + ",mh=" + getMeasuredHeight());
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        Log.d(TAG, "onFinishInflate: " + "w=" + getWidth() + ",h=" + getHeight() + ",mw=" + getMeasuredWidth() + ",mh=" + getMeasuredHeight());
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        Log.d(TAG, "onAttachedToWindow: " + "w=" + getWidth() + ",h=" + getHeight() + ",mw=" + getMeasuredWidth() + ",mh=" + getMeasuredHeight());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.d(TAG, "onMeasure: " + "w=" + getWidth() + ",h=" + getHeight() + ",mw=" + getMeasuredWidth() + ",mh=" + getMeasuredHeight());
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.d(TAG, "onSizeChanged: " + "w=" + getWidth() + ",h=" + getHeight() + ",mw=" + getMeasuredWidth() + ",mh=" + getMeasuredHeight());
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        Log.d(TAG, "onLayout: " + "w=" + getWidth() + ",h=" + getHeight() + ",mw=" + getMeasuredWidth() + ",mh=" + getMeasuredHeight());
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        Log.d(TAG, "draw: " + "w=" + getWidth() + ",h=" + getHeight() + ",mw=" + getMeasuredWidth() + ",mh=" + getMeasuredHeight());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d(TAG, "onDraw: " + "w=" + getWidth() + ",h=" + getHeight() + ",mw=" + getMeasuredWidth() + ",mh=" + getMeasuredHeight());
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        Log.d(TAG, "dispatchDraw: " + "w=" + getWidth() + ",h=" + getHeight() + ",mw=" + getMeasuredWidth() + ",mh=" + getMeasuredHeight());
    }


    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Log.d(TAG, "onDetachedFromWindow: " + "w=" + getWidth() + ",h=" + getHeight() + ",mw=" + getMeasuredWidth() + ",mh=" + getMeasuredHeight());
    }
}
