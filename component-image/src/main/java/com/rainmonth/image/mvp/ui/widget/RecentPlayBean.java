package com.rainmonth.image.mvp.ui.widget;

import com.rainmonth.common.bean.BaseBean;

/**
 * @author RandyZhang
 * @date 2020/9/30 5:09 PM
 */
public class RecentPlayBean extends BaseBean {
    public int resId;
    public int type;// 1 book；2 story
    public int index;

    public boolean isBookType() {
        return type == 1;
    }

    public boolean isStoryType() {
        return type == 2;
    }

}
