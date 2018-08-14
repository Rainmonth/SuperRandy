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
import com.rainmonth.image.mvp.model.bean.PhotoBean;

import java.util.List;

/**
 * @author: Randy Zhang
 * @description: 图片适配器
 * @created: 2018/8/14
 **/
public class PhotosAdapter extends BaseQuickAdapter<PhotoBean, BaseViewHolder> {
    private ImageLoader imageLoader;
    private Context context;

    public PhotosAdapter(Context context, int layoutResId) {
        super(layoutResId);
        this.context = context;
        imageLoader = ComponentUtils.getAppComponent().imageLoader();
    }

    public PhotosAdapter(Context context, int layoutResId, @Nullable List<PhotoBean> data) {
        super(layoutResId, data);
        this.context = context;
        imageLoader = ComponentUtils.getAppComponent().imageLoader();
    }

    @Override
    protected void convert(BaseViewHolder helper, PhotoBean item) {
        ImageView ivPhotos = helper.getView(R.id.image_iv_photos);
        TextView tvAuthor = helper.getView(R.id.image_tv_author);
        if (item == null)
            return;
        imageLoader.loadImage(context, GlideImageConfig
                .builder()
                .url(item.getUrls().getSmall())
                .imageView(ivPhotos).build());
        tvAuthor.setText(item.getUser().getUsername());
    }
}
