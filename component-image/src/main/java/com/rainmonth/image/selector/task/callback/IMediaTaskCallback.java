package com.rainmonth.image.selector.task.callback;

import com.rainmonth.image.selector.bean.MediaFileBean;

import java.util.List;

/**
 * @author RandyZhang
 * @date 2020/8/12 2:01 PM
 */
public interface IMediaTaskCallback {
    void onMediaLoaded(List<MediaFileBean> mediaFileBeans);
}
