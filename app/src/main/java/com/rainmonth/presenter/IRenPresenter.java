package com.rainmonth.presenter;

import com.rainmonth.bean.BannerBean;
import com.rainmonth.bean.RenContentInfo;

import java.util.List;

/**
 * Created by RandyZhang on 2017/1/23.
 */

public interface IRenPresenter {

    List<RenContentInfo> getContentList();

    List<BannerBean> getHomeBanner();

    void navToDetail(RenContentInfo renContentInfo);
}
