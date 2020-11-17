package com.rainmonth.image.mvp.ui.common;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ImageView;

import com.rainmonth.common.base.BaseActivity;
import com.rainmonth.common.di.component.AppComponent;
import com.rainmonth.common.utils.FileUtils;
import com.rainmonth.image.R;
import com.rainmonth.common.utils.log.LogUtils;

/**
 * 实现效果:将视频逐帧取出，转化成Ascii图像连续播放
 * 实现思路:
 * 1. 视频逐帧图片提取
 * 2. 图片到Ascii图片的转换
 * 3. 将转换生成的图片按序播放
 */
public class PicToAsciiActivity extends BaseActivity {

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.image_activity_pic_to_ascii;
    }

    @Override
    protected void initViewsAndEvents() {
        ImageView ivAscii = findViewById(R.id.iv_ascii);
        String path = FileUtils.getStorageFilePath("DCIM/BeautyCam/test.jpg", this).getPath();
//        Bitmap bitmap = BitmapFactory.decodeFile(path);
        try {
            Bitmap bitmap = createAsciiPic(path, this);

            ivAscii.setImageBitmap(bitmap);
        } catch (Exception e) {
            LogUtils.e(e);
        }

    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
    }

    @Override
    public void initToolbar(int colorResId) {

    }

    /**
     * 从视频中逐帧提取图片
     *
     * @param videoFilePath
     */
    private void extractFramePicFromVedio(String videoFilePath) {
    }

    /**
     * 将图片转换成Ascii图片
     */
    public static Bitmap createAsciiPic(final String path, Context context) {
        final String base = "#8XOHLTI)i=+;:,.";// 字符串由复杂到简单，字符串过多会导致不清楚
//        final String base = "#,.0123456789:;@ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";// 字符串由复杂到简单
        StringBuilder text = new StringBuilder();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        Bitmap image = BitmapFactory.decodeFile(path);  //读取图片
        int width0 = image.getWidth();
        int height0 = image.getHeight();
        int width1, height1;
        int scale = 7;
        if (width0 <= width / scale) {
            width1 = width0;
            height1 = height0;
        } else {
            width1 = width / scale;
            height1 = width1 * height0 / width0;
        }
        image = scale(path, width1, height1);  //读取图片
        //输出到指定文件中
        for (int y = 0; y < image.getHeight(); y += 2) {
            for (int x = 0; x < image.getWidth(); x++) {
                final int pixel = image.getPixel(x, y);
                final int r = (pixel & 0xff0000) >> 16, g = (pixel & 0xff00) >> 8, b = pixel & 0xff;
                final float gray = 0.299f * r + 0.578f * g + 0.114f * b;
                final int index = Math.round(gray * (base.length() + 1) / 255);
                String s = index >= base.length() ? " " : String.valueOf(base.charAt(index));
                text.append(s);
            }
            text.append("\n");
        }
        return textAsBitmap(text, context);
    }

    public static Bitmap textAsBitmap(StringBuilder text, Context context) {

        TextPaint textPaint = new TextPaint();

// textPaint.setARGB(0x31, 0x31, 0x31, 0);

        textPaint.setColor(Color.BLACK);

        textPaint.setAntiAlias(true);
        textPaint.setTypeface(Typeface.MONOSPACE);

        textPaint.setTextSize(12);
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;         //
        StaticLayout layout = new StaticLayout(text, textPaint, width,
                Layout.Alignment.ALIGN_CENTER, 1f, 0.0f, true);
        Bitmap bitmap = Bitmap.createBitmap(layout.getWidth() + 20,
                layout.getHeight() + 20, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        canvas.translate(10, 10);
        canvas.drawColor(Color.WHITE);
//        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);//绘制透明色
        layout.draw(canvas);

        Log.d("textAsBitmap",
                String.format("1:%d %d", layout.getWidth(), layout.getHeight()));

        return bitmap;
    }

    public static Bitmap scale(String src, int newWidth, int newHeight) {
        Bitmap ret = Bitmap.createScaledBitmap(BitmapFactory.decodeFile(src), newWidth, newHeight,
                true);
        return ret;
    }

    public static Bitmap getCompressPhoto(String path) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = false;
        options.inSampleSize = 10; // 图片的大小设置为原来的十分之一
        Bitmap bmp = BitmapFactory.decodeFile(path, options);
        options = null;
        return bmp;
    }

    /**
     * 按序列播放Ascii图片
     */
    private void playAsciiPicsSequence() {

    }
}
