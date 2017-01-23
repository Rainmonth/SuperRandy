package com.rainmonth.model;

import com.rainmonth.bean.BannerBean;
import com.rainmonth.bean.RenContentInfo;

import java.util.List;

/**
 * Created by RandyZhang on 16/7/5.
 */
public interface IRenFragmentModel {
    List<RenContentInfo> getRenContentList();

    List<BannerBean> getHomeBanner();
}
