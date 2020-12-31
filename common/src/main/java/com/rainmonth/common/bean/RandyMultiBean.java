package com.rainmonth.common.bean;


import com.rainmonth.adapter.entity.MultiItemEntity;

import java.util.List;

/**
 * @author 张豪成
 * @date 2019-11-25 13:41
 */
public class RandyMultiBean<Item extends BaseBean> extends BaseBean implements MultiItemEntity {
    public int itemType;

    public List<Item> items;

    public RandyMultiBean(int itemType, List<Item> items) {
        this.itemType = itemType;
        this.items = items;
    }

    @Override
    public int getItemType() {
        return itemType;
    }
}
