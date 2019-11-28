package com.rainmonth.music.audioplayer;

import com.rainmonth.common.base.mvp.BaseModel;
import com.rainmonth.common.integration.IRepositoryManager;
import com.rainmonth.music.bean.SongDetailBean;
import com.rainmonth.music.bean.SongRelatedBean;

import io.reactivex.Observable;

/**
 * @author 张豪成
 * @date 2019-11-28 09:15
 */
public class PlayerModel extends BaseModel implements PlayerContract.Model {

    public PlayerModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<SongDetailBean> getSongDetailInfo() {
        return null;
    }

    @Override
    public Observable<SongRelatedBean> getSongRelatedInfo() {
        return null;
    }
}
