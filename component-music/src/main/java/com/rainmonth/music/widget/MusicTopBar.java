package com.rainmonth.music.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;

import com.rainmonth.music.R;

/**
 * 音乐播放模块
 *
 * @author 张豪成
 * @date 2019-11-27 11:15
 */
public class MusicTopBar extends RelativeLayout implements View.OnClickListener {

    private String title;
    private ColorStateList titleColor;
    private int leftResId;
    private int rightResId;

    private ImageView ivLeft, ivRight;
    private TextView tvTitle;
    private View.OnClickListener mOnClickListener;

    public MusicTopBar(Context context) {
        this(context, null);
    }

    public MusicTopBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MusicTopBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs);
        initView(context);
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        if (attrs == null) {
            return;
        }
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MusicTopBar);
        leftResId = typedArray.getResourceId(R.styleable.MusicTopBar_music_top_left_icon, 0);
        title = typedArray.getString(R.styleable.MusicTopBar_music_top_title);
        titleColor = typedArray.getColorStateList(R.styleable.MusicTopBar_music_top_title_color);
        rightResId = typedArray.getResourceId(R.styleable.MusicTopBar_music_top_right_icon, 0);

        typedArray.recycle();
    }

    private void initView(Context context) {
        View.inflate(context, R.layout.music_top_bar, this);
        ivLeft = findViewById(R.id.iv_left);
        tvTitle = findViewById(R.id.tv_title);
        ivRight = findViewById(R.id.iv_right);


        setTitle(title);
        setTitleColor(titleColor);
        setLeftIcon(leftResId);
        setRightIcon(rightResId);

        ivLeft.setOnClickListener(this);
        ivRight.setOnClickListener(this);

    }

    public void setTitle(String title) {
        if (!TextUtils.isEmpty(title)) {
            if (tvTitle != null) {
                tvTitle.setText(title);
            }
        }
    }

    public void setTitleColor(@ColorInt int color) {
        ColorStateList colorStateList = ColorStateList.valueOf(color);
        setTitleColor(colorStateList);
    }

    public void setTitleColor(ColorStateList color) {
        if (tvTitle != null && color != null) {
            tvTitle.setTextColor(color);
        }
    }

    public void setLeftIcon(@DrawableRes int leftResId) {
        if (ivLeft != null) {
            ivLeft.setImageResource(leftResId);
        }
    }

    public void setRightIcon(@DrawableRes int rightResId) {
        if (ivRight != null) {
            ivRight.setImageResource(rightResId);
        }
    }

    public void setOnClickListener(OnClickListener listener) {
        this.mOnClickListener = listener;
    }

    @Override
    public void onClick(View v) {
        if (mOnClickListener != null) {
            mOnClickListener.onClick(v);
        }
    }
}
