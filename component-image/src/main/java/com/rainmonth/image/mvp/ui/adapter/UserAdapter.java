package com.rainmonth.image.mvp.ui.adapter;

import android.content.Context;
import androidx.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.rainmonth.common.http.imageloader.ImageLoader;
import com.rainmonth.common.http.imageloader.glide.GlideImageConfig;
import com.rainmonth.common.utils.ComponentUtils;
import com.rainmonth.image.R;
import com.rainmonth.image.mvp.model.bean.UserBean;

import java.util.List;

/**
 * @author: Randy Zhang
 * @description: 用户适配器
 * @created: 2018/8/14
 **/
public class UserAdapter extends BaseQuickAdapter<UserBean, BaseViewHolder> {
    private ImageLoader imageLoader;
    private Context context;

    public UserAdapter(Context context, int layoutResId) {
        super(layoutResId);
        this.context = context;
        imageLoader = ComponentUtils.getAppComponent().imageLoader();
    }

    public UserAdapter(Context context, int layoutResId, @Nullable List<UserBean> data) {
        super(layoutResId, data);
        this.context = context;
        imageLoader = ComponentUtils.getAppComponent().imageLoader();
    }

    @Override
    protected void convert(BaseViewHolder helper, UserBean item) {
        ImageView ivPhotos = helper.getView(R.id.image_iv_photos);
        TextView tvAuthor = helper.getView(R.id.image_tv_photo_author);
        if (item == null)
            return;
        if (item.getProfile_image() != null) {
            imageLoader.loadImage(context, GlideImageConfig
                    .builder()
                    .url(item.getProfile_image().getLarge())
                    .imageView(ivPhotos).build());
        }
        tvAuthor.setText(item.getName());
    }
}
