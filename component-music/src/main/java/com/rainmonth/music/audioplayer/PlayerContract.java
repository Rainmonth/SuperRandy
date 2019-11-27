package com.rainmonth.music.audioplayer;

/**
 * @date: 2019-01-07
 * @author: randy
 * @description: AudioPlayerContract
 */
public interface PlayerContract {
    interface View {

        void updatePlayBtn(int playState);

        void updatePlayModeBtn(int playMode);

        void updateCollectBtn(boolean isCollect);

        void startCoverAnim();

        void stopCoverAnim();

    }

    interface Presenter {
        void startPlay();

        void playPre();

        void playNext();

        void pausePlay();

        void seekTo();

        void startUpdatePlayTimeTask();

        void stopUpdatePlayTimeTask();
    }

}
