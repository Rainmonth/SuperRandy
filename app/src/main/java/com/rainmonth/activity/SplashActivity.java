package com.rainmonth.activity;

import android.content.Intent;
import android.widget.ImageView;
import android.widget.TextView;

import com.rainmonth.R;
import com.rainmonth.bean.SplashInfo;
import com.rainmonth.presenter.BasePresenter;
import com.rainmonth.presenter.SplashPresenter;
import com.rainmonth.view.SplashView;

import butterknife.Bind;

/**
 * 启动页主要负责以下工作：
 * 1、页面图片展示（默认的或网络的）
 * 2、页面逻辑跳转，
 */
public class SplashActivity extends BaseActivity implements SplashView {

    @Bind(R.id.tv_splash_text)
    TextView tvSplashText;
    @Bind(R.id.iv_splash)
    ImageView ivSplash;

    private BasePresenter splashPresenter = null;

    @Override
    public boolean isApplyTranslucentStatusBar() {
        return false;
    }

    @Override
    public int getContentViewLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public void initViewsAndEvent() {
        splashPresenter = new SplashPresenter(this, this);
        splashPresenter.initialize();
        /**
         *  todo 请求网络判断是否有新的Splash图片（即新的活动）:
         *  1、有，显示Splash图片，走有新Splash图片的逻辑（如点击图片进入相关活动页面、显示倒计时等）
         *  2、没有，显示默认Splash图片，走默认逻辑（即一段时间后进入主页）
         *
         */
    }

    @Override
    public void initWithSplashInfo(SplashInfo splashInfo) {
        if (null != splashInfo) {
            if (splashInfo.isHasRemoteSplash()) {
                // todo replace with UIL 显示图片
                ivSplash.setImageDrawable(getResources().getDrawable(splashInfo.getRemoteSplashUrl()));
                tvSplashText.setText(splashInfo.getRemoteSplashText());
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
}
