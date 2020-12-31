package com.rainmonth.mvp.ui.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.rainmonth.adapter.base.BaseQuickAdapter;
import com.rainmonth.adapter.base.BaseViewHolder;
import com.rainmonth.R;
import com.rainmonth.common.http.imageloader.ImageLoader;
import com.rainmonth.common.http.imageloader.glide.GlideImageConfig;
import com.rainmonth.common.utils.ComponentUtils;
import com.rainmonth.mvp.model.bean.MemAlbumBean;

/**
 * ran 列表适配器
 * Created by RandyZhang on 2018/6/20.
 */

public class RanListAdapter extends BaseQuickAdapter<MemAlbumBean, BaseViewHolder> {
    private Context context;
    private ImageLoader imageLoader;

    public RanListAdapter(int layoutResId, Context context) {
        super(layoutResId);
        this.context = context;
        imageLoader = ComponentUtils.getAppComponent().imageLoader();
    }

    @Override
    protected void convert(BaseViewHolder helper, MemAlbumBean item) {
        ImageView ivAlbumFirstImage = helper.getView(R.id.iv_album_first_image);
        TextView tvAlbumDescription = helper.getView(R.id.tv_album_des);
        TextView tvAlbumAuthor = helper.getView(R.id.tv_album_author);
        TextView tvAlbumPublishTime = helper.getView(R.id.tv_album_publish_time);
        TextView tvAlbumLikeNum = helper.getView(R.id.tv_album_like_num);
        TextView tvAlbumTotalNum = helper.getView(R.id.tv_album_total_num);
        TextView tvAlbumType = helper.getView(R.id.tv_album_type);

        if (null != item) {
            imageLoader.loadImage(context, GlideImageConfig.builder()
                    .imageView(ivAlbumFirstImage)
                    .url(item.getCover_url())
                    .build());
            tvAlbumDescription.setText(item.getDescription());
            tvAlbumAuthor.setText(item.getAuthor());
            tvAlbumPublishTime.setText(item.getPublish_time() + "");
            tvAlbumLikeNum.setText(item.getLike_num() + "人喜欢");
            tvAlbumTotalNum.setText("共" + item.getPhoto_num() + "张图片");
            tvAlbumType.setText(item.getCategory());
        }
    }
}
