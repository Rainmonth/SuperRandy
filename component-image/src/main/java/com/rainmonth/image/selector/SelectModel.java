package com.rainmonth.image.selector;

import com.rainmonth.image.selector.bean.MediaFileBean;

import java.util.List;

import io.reactivex.Observable;

/**
 * 数据获取
 *
 * @author 张豪成
 * @date 2019-05-24 18:27
 */
public interface SelectModel {

    Observable<List<MediaFileBean>> loadMediaFileList();
}
