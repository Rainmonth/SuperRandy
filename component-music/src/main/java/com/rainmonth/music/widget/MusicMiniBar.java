package com.rainmonth.music.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rainmonth.music.R;
import com.socks.library.KLog;

/**
 * @author 张豪成
 * @date 2019-11-22 17:06
 */
public class MusicMiniBar extends RelativeLayout implements View.OnClickListener {

    public static final String TAG = "MusicMiniBar";
    public static final int STATE_INIT = 0;
    public static final int STATE_PLAY = 1;
    public static final int STATE_PAUSE = 2;
    public static final int STATE_LOADING = 3;

    private int mCurrentState = STATE_INIT;
    private int mExpectState = STATE_INIT;

    ImageView cover;
    TextView title;

    ImageView btnState;
    ImageView btnList;

    private View.OnClickListener mOnClickListener;

    public MusicMiniBar(Context context) {
        this(context, null);
    }

    public MusicMiniBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MusicMiniBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        View.inflate(context, R.layout.music_mini_bar, this);

        cover = findViewById(R.id.iv_cover);
        title = findViewById(R.id.tv_title);
        btnState = findViewById(R.id.iv_play_state);
        btnList = findViewById(R.id.iv_play_list);

        setOnClickListener(this);
        btnState.setOnClickListener(this);
        btnList.setOnClickListener(this);
    }

    private void tryToExpectState(int expectState) {
        if (expectState != mCurrentState) {
            mCurrentState = expectState;
        }
        switch (mCurrentState) {
            case STATE_INIT:
            default:
                KLog.d(TAG, "初始化状态");
                break;
            case STATE_PLAY:
                KLog.d(TAG, "播放状态");
                break;
            case STATE_LOADING:
                KLog.d(TAG, "加载状态");
                break;
            case STATE_PAUSE:
                KLog.d(TAG, "暂停状态");
                break;

        }
    }

    public void setMiniBarClickListener(OnClickListener onClickListener) {
        this.mOnClickListener = onClickListener;
    }

    @Override
    public void onClick(View v) {
        if (mOnClickListener != null) {
            mOnClickListener.onClick(v);
        }
    }
}
