package com.rainmonth.app.mvp.model;

import com.rainmonth.app.mvp.contract.AppContract;
import com.rainmonth.common.base.mvp.BaseModel;
import com.rainmonth.common.integration.IRepositoryManager;

/**
 * 获取应用数据
 * Created by RandyZhang on 2018/5/24.
 */

public class AppModel extends BaseModel implements AppContract.Model {
    public AppModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }
}
