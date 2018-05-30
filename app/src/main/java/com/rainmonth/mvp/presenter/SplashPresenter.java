package com.rainmonth.mvp.presenter;

import android.os.Handler;
import android.os.Message;

import com.rainmonth.common.base.mvp.BasePresenter;
import com.rainmonth.common.di.scope.ActivityScope;
import com.rainmonth.mvp.contract.SplashContract;

import javax.inject.Inject;

/**
 * 启动页Presenter
 * Created by RandyZhang on 16/7/1.
 */
@ActivityScope
public class SplashPresenter extends BasePresenter<SplashContract.Model, SplashContract.View> {
    //    private ISplashModel splashModel = null;
    @Inject
    public SplashPresenter(SplashContract.Model model, SplashContract.View view) {
        super(model, view);
    }

    public void getSplashInfo() {
        //
        // todo 先采用splashModel去获取信息:
        // a.获取成功，在成功回调中用获取的数据来通过splashView进行初始化
        // b.获取失败，在失败回调中构造一个默认的SplashInfo来初始化
        //

        mView.initWithSplashInfo(mModel.getSplashInfo());
        new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(4000);
                    mHandler.sendEmptyMessage(0);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                mView.navigateTo(mModel.getSplashInfo().getNaveTo());
            }
        }
    };
}
