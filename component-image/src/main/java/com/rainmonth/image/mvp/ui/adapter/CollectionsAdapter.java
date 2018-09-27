package com.rainmonth.image.mvp.ui.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.rainmonth.common.http.imageloader.ImageLoader;
import com.rainmonth.common.http.imageloader.glide.GlideImageConfig;
import com.rainmonth.common.utils.ComponentUtils;
import com.rainmonth.image.R;
import com.rainmonth.image.mvp.model.bean.CollectionBean;
import com.rainmonth.image.mvp.model.bean.PhotoBean;

import java.util.List;

/**
 * @author: Randy Zhang
 * @description: 图片适配器
 * @created: 2018/8/14
 **/
public class CollectionsAdapter extends BaseQuickAdapter<CollectionBean, BaseViewHolder> {
    private ImageLoader imageLoader;
    private Context context;

    public CollectionsAdapter(Context context, int layoutResId) {
        super(layoutResId);
        this.context = context;
        imageLoader = ComponentUtils.getAppComponent().imageLoader();
    }

    public CollectionsAdapter(Context context, int layoutResId, @Nullable List<CollectionBean> data) {
        super(layoutResId, data);
        this.context = context;
        imageLoader = ComponentUtils.getAppComponent().imageLoader();
    }

    @Override
    protected void convert(BaseViewHolder helper, CollectionBean item) {
        ImageView ivCover = helper.getView(R.id.image_iv_collection_cover);
        TextView tvDes = helper.getView(R.id.image_tv_collection_des);
        if (item == null)
            return;
        imageLoader.loadImage(context, GlideImageConfig
                .builder()
                .url(item.getCover_photo().getUrls().getSmall())
                .imageView(ivCover).build());
        tvDes.setText(item.getDescription());
    }
}
