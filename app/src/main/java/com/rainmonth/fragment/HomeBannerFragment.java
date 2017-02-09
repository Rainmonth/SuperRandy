package com.rainmonth.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.rainmonth.R;
import com.rainmonth.bean.BannerBean;
import com.rainmonth.library.base.BaseLazyFragment;
import com.rainmonth.library.eventbus.EventCenter;
import com.rainmonth.utils.ToastUtils;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by RandyZhang on 2017/2/7.
 */

public class HomeBannerFragment extends BaseLazyFragment {
    @Bind(R.id.iv_banner_image)
    ImageView ivBannerImage;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.fl_banner_container)
    FrameLayout flBannerContainer;

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
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    protected void initViewsAndEvents(View view) {
        Bundle bundle = getArguments();
        if (null != bundle) {
            final BannerBean bannerBean = (BannerBean) bundle.getSerializable(RenFragment.BANNER_BEAN);
            Picasso.with(getActivity()).load(bannerBean.getBanner_thumb_url()).into(ivBannerImage);
            tvTitle.setText(bannerBean.getTitle());
            flBannerContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // nav to banner detail
//                    Bundle bundle = new Bundle();
//                    bundle.putString(WebExploreActivity.BUNDLE_KEY_TITLE, bannerBean.getTitle());
//                    bundle.putString(WebExploreActivity.BUNDLE_KEY_URL, bannerBean.getUrl());
//                    readyGo(BaseWebActivity.class, bundle);
                    ToastUtils.showToast(getActivity(), "即将进入" + bannerBean.getUrl());
                }
            });
        }

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_home_banner;
    }

    @Override
    protected void onEventComing(EventCenter eventCenter) {

    }

    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
