package com.rainmonth.common.base.mvp;

import com.rainmonth.common.integration.IRepositoryManager;

/**
 * 数据获取基类，提供了获取数据的Manager（可以以不同的方式获取数据）
 * Created by RandyZhang on 2018/5/24.
 */
public class BaseModel implements IBaseModel {
    protected IRepositoryManager mRepositoryManager;

    public BaseModel(IRepositoryManager repositoryManager) {
        this.mRepositoryManager = repositoryManager;
    }

    @Override
    public void onDestroy() {
        mRepositoryManager = null;
    }
}
