package com.rainmonth.mvp.model;

import com.rainmonth.api.AlbumService;
import com.rainmonth.common.base.mvp.BaseModel;
import com.rainmonth.common.di.scope.FragmentScope;
import com.rainmonth.common.http.PageResult;
import com.rainmonth.common.integration.IRepositoryManager;
import com.rainmonth.mvp.contract.RanContract;
import com.rainmonth.mvp.model.bean.MemAlbumBean;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import retrofit2.Response;

/**
 * RanFragment 数据获取
 * Created by RandyZhang on 2018/5/31.
 */
@FragmentScope
public class RanModel extends BaseModel implements RanContract.Model {
    @Inject
    public RanModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Flowable<PageResult<MemAlbumBean>> getRanContentList(String category,
                                                                final int page,
                                                                int pageSize) {
        return mRepositoryManager.obtainRetrofitService(AlbumService.class)
                .getMemoryAlbumList(category, page, pageSize)
                .map(new Function<Response<PageResult<MemAlbumBean>>, PageResult<MemAlbumBean>>() {
                    @Override
                    public PageResult<MemAlbumBean> apply(Response<PageResult<MemAlbumBean>> pageResultResponse) throws Exception {
                        return pageResultResponse.body();
                    }
                });
    }

}
