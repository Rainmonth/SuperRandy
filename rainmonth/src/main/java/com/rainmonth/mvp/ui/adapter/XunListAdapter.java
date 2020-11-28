package com.rainmonth.mvp.ui.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.rainmonth.common.adapter.base.BaseQuickAdapter;
import com.rainmonth.common.adapter.base.BaseViewHolder;
import com.rainmonth.R;
import com.rainmonth.common.http.imageloader.ImageLoader;
import com.rainmonth.common.http.imageloader.glide.GlideImageConfig;
import com.rainmonth.common.utils.ComponentUtils;
import com.rainmonth.mvp.model.bean.XunNavigationBean;

/**
 * @desprition: 寻列表适配器
 * @author: RandyZhang
 * @date: 2018/6/22 下午1:48
 */
public class XunListAdapter extends BaseQuickAdapter<XunNavigationBean, BaseViewHolder> {
    private Context context;
    private ImageLoader imageLoader;

    public XunListAdapter(int layoutResId, Context context) {
        super(layoutResId);
        this.context = context;
        imageLoader = ComponentUtils.getAppComponent().imageLoader();
    }

    @Override
    protected void convert(BaseViewHolder helper, XunNavigationBean item) {
        ImageView ivNavImg = helper.getView(R.id.iv_nav_img);
        TextView tvNavName = helper.getView(R.id.tv_nav_name);

        tvNavName.setText(item.getTitle());
        imageLoader.loadImage(context, GlideImageConfig
                .builder()
                .imageView(ivNavImg)
                .url(item.getIcon_url())
                .build());

    }
}