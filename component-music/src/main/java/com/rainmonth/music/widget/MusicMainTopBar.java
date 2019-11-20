package com.rainmonth.music.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.DrawableRes;

import com.rainmonth.music.R;

/**
 * 首页顶部标题栏
 *
 * @author 张豪成
 * @date 2019-11-20 15:50
 */
public class MusicMainTopBar extends RelativeLayout {
    private Context mContext;
    private String title;
    private int iconRes;

    private TextView tvTitle;
    private ImageView ivIcon;

    public MusicMainTopBar(Context context) {
        this(context, null);
    }

    public MusicMainTopBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MusicMainTopBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;

        handleTypedArray(mContext, attrs);
        init();
    }

    private void handleTypedArray(Context context, AttributeSet attrs) {
        if (attrs == null) {
            return;
        }
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MusicMainTopBar);
        title = typedArray.getString(R.styleable.MusicMainTopBar_music_title);
        iconRes = typedArray.getResourceId(R.styleable.MusicMainTopBar_music_icon, 0);
        typedArray.recycle();
    }

    private void init() {
        View.inflate(mContext, R.layout.music_main_top_bar, this);

        tvTitle = findViewById(R.id.title);
        ivIcon = findViewById(R.id.icon);

        if (!TextUtils.isEmpty(title)) {
            setTitle(title);
        }
        if (iconRes != 0 && iconRes > 0) {
            setIcon(iconRes);
        }
    }

    private void setTitle(String title) {
        if (tvTitle != null) {
            tvTitle.setText(title);
        }
    }

    private void setIcon(@DrawableRes int resId) {
        if (ivIcon != null) {
            ivIcon.setImageResource(resId);
        }
    }
}
