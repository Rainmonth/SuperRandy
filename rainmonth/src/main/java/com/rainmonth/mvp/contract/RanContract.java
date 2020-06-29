package com.rainmonth.mvp.contract;

import com.rainmonth.common.base.mvp.IBaseModel;
import com.rainmonth.common.base.mvp.IBaseView;
import com.rainmonth.common.http.PageData;
import com.rainmonth.common.http.PageResult;
import com.rainmonth.mvp.model.bean.MemAlbumBean;

import io.reactivex.Flowable;

/**
 * Created by RandyZhang on 2018/5/24.
 */
public interface RanContract {
    interface Model extends IBaseModel {
        Flowable<PageResult<MemAlbumBean>> getRanContentList(String category,
                                                             int page,
                                                             int pageSize);
    }

    interface View extends IBaseView {
        void initViews(PageData<MemAlbumBean> memAlbumBeanList);

        void navToDetail(MemAlbumBean memAlbumBean);
    }
}
