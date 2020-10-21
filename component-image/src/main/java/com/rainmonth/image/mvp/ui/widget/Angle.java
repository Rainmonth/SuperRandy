package com.rainmonth.image.mvp.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class Angle extends View {
    private int width;
    private int heigth;
    private Paint mPaintNormal;
    private Paint mPaintPoint;
    private Path mPathRect;
    private Path mPathCircle;
    private Path mPathBser;
    private Path mPathBserLang;
    public static final int REFRESH = 0x55;
    private int count = 0;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case REFRESH:
                    count += 5;//count的作用是为了让波浪线动起来
                    if (count > 100) {
                        count = 0;
                    }
                    handler.sendEmptyMessageDelayed(REFRESH, 50);
                    invalidate();
                    break;
            }
        }
    };

    public Angle(Context context) {
        super(context);
    }

    public Angle(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaintNormal = new Paint();
        mPaintNormal.setColor(Color.BLACK);
        mPaintNormal.setStyle(Paint.Style.STROKE);
        mPaintNormal.setAntiAlias(true);
        mPaintNormal.setTextSize(20);

        mPaintPoint = new Paint();
        mPaintPoint.setColor(Color.BLUE);
        mPaintPoint.setAntiAlias(true);
        mPaintPoint.setStyle(Paint.Style.STROKE);
        mPaintPoint.setStrokeWidth(8);

        mPathRect = new Path();
        mPathCircle = new Path();
        mPathBser = new Path();
        mPathBserLang = new Path();

        handler.sendEmptyMessage(REFRESH);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
        heigth = getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec);
        setMeasuredDimension(width, heigth);
        Log.d("length", "宽度是" + width);
        Log.d("length", "高度是" + heigth);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画一个三角形或者多边形
        mPathRect.moveTo(200, 0);//起点
        mPathRect.lineTo(0, 200);//起点到这个点（第二个点）
        mPathRect.lineTo(300, 200);//第二个点到第三个点
        mPathRect.close();//效果和mPath.lineTo(200,200);//将最后到达的点和第一个点连接起来
        canvas.drawPath(mPathRect, mPaintNormal);//画出路径
        //画一个圆
        mPathCircle.addCircle(500, 120, 100, Path.Direction.CCW);//最后一个参数是方向
        canvas.drawPath(mPathCircle, mPaintNormal);
        canvas.drawTextOnPath("在路径上写字", mPathCircle, 0, 0, mPaintNormal);//在指定的路径上写上文字
        //绘制贝塞尔曲线
        mPathBser.moveTo(100, 350);//设置起点
        mPathBser.quadTo(400, 100, 500, 300);//前两个参数是设置参考点，后两个参数是设置结束点
        canvas.drawPath(mPathBser, mPaintNormal);
        canvas.drawPoint(100, 400, mPaintPoint);//画点
        canvas.drawPoint(400, 100, mPaintPoint);
        canvas.drawPoint(500, 300, mPaintPoint);
        //利用贝塞尔曲线绘制动态波浪
        mPathBserLang.reset();
        mPathBserLang.moveTo(count, 500);
        //循环绘制波浪线
        for (int i = 0; i < 10; i++) {
            //这两个绘制贝塞尔曲线会连接成为一个波浪线
            mPathBserLang.rQuadTo(20, 6, 50, 0);//rquadTo是按照该点（即count，500这个点）为原点进行绘制操作
            mPathBserLang.rQuadTo(20, -6, 50, 0);//上一个绘制完成后的终点，成为该rQuadTo的起点（即原点）重新开始绘制
        }
        canvas.drawPath(mPathBserLang, mPaintNormal);
        canvas.drawCircle(360, 500, 60, mPaintPoint);
    }
}