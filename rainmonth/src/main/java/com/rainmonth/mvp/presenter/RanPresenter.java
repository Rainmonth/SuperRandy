package com.rainmonth.mvp.presenter;

import com.rainmonth.common.base.mvp.BasePresenter;
import com.rainmonth.common.http.CommonSubscriber;
import com.rainmonth.common.http.PageResult;
import com.rainmonth.common.utils.RxUtils;
import com.rainmonth.mvp.contract.RanContract;
import com.rainmonth.mvp.model.bean.MemAlbumBean;

import javax.inject.Inject;

/**
 * Created by RandyZhang on 16/7/5.
 */
public class RanPresenter extends BasePresenter<RanContract.Model, RanContract.View> {

    @Inject
    public RanPresenter(RanContract.Model model, RanContract.View view) {
        super(model, view);
    }

    public void getAlbumList(String category, int page, int pageSize) {
        addSubscribe(mModel.getRanContentList(category,page,pageSize)
        .compose(RxUtils.<PageResult<MemAlbumBean>>getFlowableTransformer())
        .subscribeWith(new CommonSubscriber<PageResult<MemAlbumBean>>(mView) {
            @Override
            public void onNext(PageResult<MemAlbumBean> memAlbumBeanPageResult) {
                if(memAlbumBeanPageResult.isSuccess()) {
                    mView.initViews(memAlbumBeanPageResult.getData());
                }
            }
        }));

    }
    public void navToDetail(MemAlbumBean memAlbumBean) {
        mView.navToDetail(memAlbumBean);
    }
}
