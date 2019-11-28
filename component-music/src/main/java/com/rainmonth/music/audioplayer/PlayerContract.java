package com.rainmonth.music.audioplayer;

import com.rainmonth.common.base.mvp.IBaseModel;
import com.rainmonth.common.base.mvp.IBaseView;
import com.rainmonth.music.bean.SongDetailBean;
import com.rainmonth.music.bean.SongRelatedBean;

import io.reactivex.Observable;


/**
 * @date: 2019-01-07
 * @author: randy
 * @description: AudioPlayerContract
 */
public interface PlayerContract {
    interface View extends IBaseView {

        void updateWithSongDetailInfo(SongDetailBean songDetailInfo);

        void updateWithSongRelatedInfo(SongRelatedBean songRelatedInfo);

        void updatePlayBtn(int playState);

        void updatePlayModeBtn(int playMode);

        void updateCollectBtn(boolean isCollect);

        void startCoverAnim();

        void stopCoverAnim();

    }

    interface Model extends IBaseModel {
        Observable<SongDetailBean> getSongDetailInfo();

        Observable<SongRelatedBean> getSongRelatedInfo();
    }

}
