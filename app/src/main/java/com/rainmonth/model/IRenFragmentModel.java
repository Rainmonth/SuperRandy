package com.rainmonth.model;

import com.rainmonth.bean.BannerBean;
import com.rainmonth.bean.RenContentInfo;
import com.rainmonth.utils.http.RequestCallback;

import java.util.List;

import rx.Subscription;

/**
 * Created by RandyZhang on 16/7/5.
 */
public interface IRenFragmentModel<T> {
    List<RenContentInfo> getRenContentList();

    Subscription login(RequestCallback<T> callback, String username, String psw);
    List<BannerBean> getHomeBanner(RequestCallback<BannerBean> callback);
}
