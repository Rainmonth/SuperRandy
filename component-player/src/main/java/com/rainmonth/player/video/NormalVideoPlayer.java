package com.rainmonth.player.video;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.rainmonth.player.R;


/**
 * 使用正常播放按键和loading的播放器
 */
public class NormalVideoPlayer extends StandardVideoPlayer {

    public NormalVideoPlayer(Context context, Boolean fullFlag) {
        super(context, fullFlag);
    }

    public NormalVideoPlayer(Context context) {
        super(context);
    }

    public NormalVideoPlayer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public int getLayoutId() {
        return R.layout.player_normal_player_view;
    }

    @Override
    protected void updateStartImage() {
        if (mStartButton instanceof ImageView) {
            ImageView imageView = (ImageView) mStartButton;
            if (mCurrentState == CURRENT_STATE_PLAYING) {
                imageView.setImageResource(R.drawable.player_video_click_pause_selector);
            } else if (mCurrentState == CURRENT_STATE_ERROR) {
                imageView.setImageResource(R.drawable.player_video_click_play_selector);
            } else {
                imageView.setImageResource(R.drawable.player_video_click_play_selector);
            }
        }
    }
}