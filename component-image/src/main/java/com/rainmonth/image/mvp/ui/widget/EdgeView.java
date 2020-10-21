package com.rainmonth.image.mvp.ui.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.EdgeEffect;

public class EdgeView extends View {
    private EdgeEffect mRightEffect;
    private int mTouchSlop;
    private int mLastMotionX, mLastMotionY;
    private boolean mIsShownEdge = false;

    public EdgeView(Context context) {
        super(context);
        init(context);
    }

    public EdgeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void init(Context context) {
        mRightEffect = new EdgeEffect(context);
        mRightEffect.setColor(Color.RED);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getActionMasked();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mLastMotionX = (int) event.getX();
                mLastMotionY = (int) event.getY();
                mIsShownEdge = false;
                break;
            case MotionEvent.ACTION_MOVE:
                int x = (int) event.getX();
                int y = (int) event.getY();
                int deltax = x - mLastMotionX;
                int deltay = y - mLastMotionY;
                if (!mIsShownEdge) {
                    if (Math.abs(deltax) > mTouchSlop && deltax < 0 && Math.abs(deltax) > Math.abs(deltay)) {
                        mIsShownEdge = true;
                        mLastMotionX = x;
                        mLastMotionY = y;
                    }
                }
                if (mIsShownEdge) {
                    if (deltax < 0) {
                        // 这个方法确定每次拉伸边缘需要更新的参数
                        // 第一个参数：每次拉伸增加的距离（0——1）叠加的 ex：第一次传入0.1， 第二次传入0.2的话，总距离就会叠加0.1+0.2 = 0.3
                        // 第二个参数：边缘圆的位置，上下拉动时边缘鼓起的最大位置的坐标就是这个参数决定的，默认是0.5
                        mRightEffect.onPull((float) Math.abs(deltax) / getMeasuredWidth());
                        //mRightEffect.onPull((float)Math.abs(deltax) / getMeasuredWidth(), 0.2f);
                        //更新后刷新view
                        postInvalidateOnAnimation();
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                mIsShownEdge = false;
                mRightEffect.onRelease();
                postInvalidateOnAnimation();
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        // 默认边缘是画在最上面，在画图时需要对画布进行平移和旋转
        // 具体可参照listview的上下边缘和viewpager的左右边缘的实现方法。
        // 这里实现的是右边缘
        if (!mRightEffect.isFinished()) {
            int count = canvas.save();
            canvas.rotate(90);
            canvas.translate(0, -getMeasuredWidth());
            mRightEffect.setSize(getMeasuredHeight(), getMeasuredWidth());
            if (mRightEffect.draw(canvas)) {
                postInvalidateOnAnimation();
            }
            canvas.restoreToCount(count);
        } else {
            mRightEffect.finish();
        }
    }
}