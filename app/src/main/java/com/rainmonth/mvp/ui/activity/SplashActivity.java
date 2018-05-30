package com.rainmonth.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.rainmonth.R;
import com.rainmonth.common.base.BaseActivity;
import com.rainmonth.common.di.component.AppComponent;
import com.rainmonth.di.component.DaggerSplashComponent;
import com.rainmonth.di.module.SplashModule;
import com.rainmonth.mvp.contract.SplashContract;
import com.rainmonth.mvp.model.bean.SplashBean;
import com.rainmonth.mvp.presenter.SplashPresenter;

import butterknife.BindView;

/**
 * 启动页主要负责以下工作：
 * 1、页面图片展示（默认的或网络的）
 * 2、页面逻辑跳转，
 */
public class SplashActivity extends BaseActivity<SplashPresenter> implements SplashContract.View {

    @BindView(R.id.tv_splash_text)
    TextView tvSplashText;
    @BindView(R.id.iv_splash)
    ImageView ivSplash;

    @Override
    public void initToolbar() {

    }


    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerSplashComponent
                .builder()
                .appComponent(appComponent)
                .splashModule(new SplashModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void initWithSplashInfo(SplashBean splashBean) {
        if (null != splashBean) {
            if (splashBean.isHasRemoteSplash()) {
                // todo replace with UIL 显示图片
                ivSplash.setImageDrawable(getResources().getDrawable(splashBean.getRemoteSplashUrl()));
                tvSplashText.setText(splashBean.getRemoteSplashText());
            } else {
                ivSplash.setImageDrawable(getResources().getDrawable(R.drawable.default_splash));
                tvSplashText.setText(getString(R.string.default_splash_text));
            }
        }
    }

    @Override
    public void navigateTo(String navStr) {
        if ("main".equals(navStr)) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_splash;
    }


    @Override
    protected void initViewsAndEvents() {
//        splashPresenter = new SplashPresenter(this);
//        splashPresenter.initialize();
        /**
         *  todo 请求网络判断是否有新的Splash图片（即新的活动）:
         *  1、有，显示Splash图片，走有新Splash图片的逻辑（如点击图片进入相关活动页面、显示倒计时等）
         *  2、没有，显示默认Splash图片，走默认逻辑（即一段时间后进入主页）
         *
         */
        mPresenter.getSplashInfo();
    }
}
