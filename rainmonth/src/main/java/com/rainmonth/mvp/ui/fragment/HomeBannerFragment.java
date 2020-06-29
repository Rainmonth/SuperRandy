package com.rainmonth.mvp.ui.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.rainmonth.R;
import com.rainmonth.common.base.BaseLazyFragment;
import com.rainmonth.common.base.BaseWebActivity;
import com.rainmonth.common.di.component.AppComponent;
import com.rainmonth.common.eventbus.EventCenter;
import com.rainmonth.mvp.model.bean.BannerBean;

import butterknife.BindView;

/**
 * Banner Fragment
 * Created by RandyZhang on 2017/2/7.
 */

public class HomeBannerFragment extends BaseLazyFragment {
    @BindView(R.id.iv_banner_image)
    ImageView ivBannerImage;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.fl_banner_container)
    FrameLayout flBannerContainer;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_home_banner;
    }

    @Override
    protected void onFirstUserVisible() {

    }

    @Override
    protected void onUserVisible() {

    }

    @Override
    protected void onUserInvisible() {

    }

    @Override
    protected void setupFragmentComponent(AppComponent appComponent) {

    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    protected void initViewsAndEvents(View view) {
        Bundle bundle = getArguments();
        if (null != bundle) {
            final BannerBean bannerBean = (BannerBean) bundle.getSerializable(RenFragment.BANNER_BEAN);
            if (null == bannerBean) return;
            Glide.with(getActivity()).load(bannerBean.getThumb()).into(ivBannerImage);
            tvTitle.setText(bannerBean.getTitle());
            flBannerContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString(BaseWebActivity.BUNDLE_KEY_URL, bannerBean.getUrl());
                    bundle.putString(BaseWebActivity.BUNDLE_KEY_TITLE, bannerBean.getTitle());
                    readyGo(BaseWebActivity.class, bundle);
                }
            });
        }

    }

    @Override
    protected void onEventComing(EventCenter eventCenter) {

    }

    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
