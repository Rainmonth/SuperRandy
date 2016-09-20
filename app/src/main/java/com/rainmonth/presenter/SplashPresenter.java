package com.rainmonth.presenter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.rainmonth.model.SplashModel;
import com.rainmonth.model.SplashModelImpl;
import com.rainmonth.utils.http.BasePresenterImpl;
import com.rainmonth.view.SplashView;

/**
 * Created by RandyZhang on 16/7/1.
 */
public class SplashPresenter extends BasePresenterImpl<SplashView, Object> {
    private Context context = null;
    private SplashModel splashModel = null;
    private SplashView splashView = null;

    public SplashPresenter(Context context, SplashView splashView) {
        super(splashView);
        if (null == splashView) {
            throw new IllegalArgumentException("splash view should not be null");
        }
        this.context = context;
        this.splashView = splashView;
        splashModel = new SplashModelImpl();
    }

    public void initialize() {
        /**
         * todo 先采用splashModel去获取信息:
         * a.获取成功，在成功回调中用获取的数据来通过splashView进行初始化
         * b.获取失败，在失败回调中构造一个默认的SplashInfo来初始化
         */

        splashView.initWithSplashInfo(splashModel.getSplashInfo());
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

    public Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                splashView.navigateTo(splashModel.getSplashInfo().getNaveTo());
            }
        }
    };
}
