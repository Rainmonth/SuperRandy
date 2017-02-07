package com.rainmonth.model;

import com.rainmonth.bean.BannerBean;
import com.rainmonth.bean.RenContentBean;
import com.rainmonth.utils.http.RequestCallback;

import java.util.List;

import retrofit2.Response;
import rx.Subscription;

/**
 * Created by RandyZhang on 16/7/5.
 */
public interface IRenFragmentModel<T> {
    /**
     * 获取首页banner
     *
     * @param callback 回调对象
     * @return subscription 对象
     */
    Subscription getHomeBannerList(RequestCallback<Response<List<BannerBean>>> callback);

    /**
     * 获取content 列表内容
     * @param callback callback
     * @return subscription 对象
     */
    Subscription getRenContentList(RequestCallback<Response<List<RenContentBean>>> callback);
}
