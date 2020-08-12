package com.rainmonth.music.core.render.view.video.impl;

import android.content.Context;
import android.widget.SeekBar;

import com.rainmonth.music.core.render.view.video.base.Layer4ViewMgrInteractLayout;

/**
 * @author 张豪成
 * @date 2019-12-17 20:26
 */
public class StandardPlayer extends Layer4ViewMgrInteractLayout {
    public StandardPlayer(Context context) {
        super(context);
    }

    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    protected void startPlayLogic() {

    }

    @Override
    protected boolean backFromFull(Context context) {
        return false;
    }

    @Override
    protected void releaseAllVideos() {

    }

    @Override
    protected void hideAllWidgets() {

    }

    @Override
    protected void changeUiToNormal() {

    }

    @Override
    protected void changeUiToPreparingShow() {

    }

    @Override
    protected void changeUiToPlayingShow() {

    }

    @Override
    protected void changeUiToPauseShow() {

    }

    @Override
    protected void changeUiToError() {

    }

    @Override
    protected void changeUiToCompleteShow() {

    }

    @Override
    protected void changeUiToPlayingBufferingShow() {

    }


    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    protected void showWifiDialog() {

    }

    @Override
    protected void showProgressDialog(float deltaX, String seekTime, int seekPosition, String totalTime, long totalTimeDuration) {

    }

    @Override
    protected void dismissProgressDialog() {

    }

    @Override
    protected void showVolumeDialog(float deltaY, int percentage) {

    }

    @Override
    protected void dismissVolumeDialog() {

    }

    @Override
    protected void showBrightnessDialog(float brightnessPercentage) {

    }

    @Override
    protected void dismissBrightnessDialog() {

    }

    @Override
    protected void onClickUiToggle() {

    }

    @Override
    public void onBackFullscreen() {

    }

    @Override
    protected void releaseVideos() {

    }

    @Override
    protected int getSmallId() {
        return 0;
    }

    @Override
    protected int getFullId() {
        return 0;
    }
}
