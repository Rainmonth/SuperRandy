package com.rainmonth.common.component;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseViewHolder;
import com.rainmonth.common.R;
import com.rainmonth.common.bean.BannerBean;
import com.rainmonth.common.bean.RandyLogicBean;
import com.rainmonth.common.bean.RandyMultiBean;
import com.rainmonth.common.http.imageloader.glide.GlideImageConfig;
import com.rainmonth.common.utils.ComponentUtils;
import com.rainmonth.common.widgets.RandyViewPager;

/**
 * @author 张豪成
 * @date 2019-11-25 13:46
 */
public class RandyBannerItemProvider extends RandyBaseItemProvider<RandyMultiBean<BannerBean>> {
    private RandyViewPager<BannerBean> randyViewPager;

    public RandyBannerItemProvider(RandyLogicBean mLogicBean) {
        super(mLogicBean);
    }

    @Override
    public int viewType() {
        return Const.Type.RANDY_BANNER;
    }

    @Override
    public int layout() {
        return Const.Layout.RANDY_BANNER;
    }

    @Override
    public void randyConvert(@NonNull BaseViewHolder helper, RandyMultiBean<BannerBean> data, int position) {
        randyViewPager = helper.getView(R.id.vp_banner);

        randyViewPager.setPages(data.items, () -> new RandyViewPager.ViewHolder<BannerBean>() {
            private ImageView ivBanner;
            private TextView tvTitle;

            @Override
            public View createView(Context context) {
                View view = LayoutInflater.from(context)
                        .inflate(R.layout.music_banner_item, null);
                ivBanner = view.findViewById(R.id.iv_thumb);
                tvTitle = view.findViewById(R.id.tv_title);
                return view;
            }

            @Override
            public void onBind(Context context, int position1, BannerBean bannerBean) {
                tvTitle.setText(bannerBean.title);
                ComponentUtils.getAppComponent().imageLoader().loadImage(context, GlideImageConfig
                        .builder()
                        .url(bannerBean.imageUrl)
                        .imageView(ivBanner)
                        .build());
            }
        });
    }
}
