package com.rainmonth.mvp.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.rainmonth.common.adapter.base.BaseQuickAdapter;
import com.rainmonth.common.adapter.base.BaseViewHolder;
import com.rainmonth.R;
import com.rainmonth.common.http.imageloader.ImageLoader;
import com.rainmonth.common.http.imageloader.glide.GlideImageConfig;
import com.rainmonth.common.utils.ComponentUtils;
import com.rainmonth.common.widgets.RandyViewPager;
import com.rainmonth.common.widgets.RandyViewPager.ViewHolder;
import com.rainmonth.mvp.model.bean.PursueBean;
import com.rainmonth.mvp.model.bean.PursueGroupBean;

/**
 * Created by RandyZhang on 2018/6/15.
 */

public class PursueContentAdapter extends BaseQuickAdapter<PursueGroupBean, BaseViewHolder> {
    private Context context;
    private TextView tvAlbumTitle;
    private RandyViewPager<PursueBean> rvpPursueAlbum;
    private TextView tvAlbumPublishTime;

    private ImageLoader imageLoader;

    public PursueContentAdapter(int layoutResId, Context context) {
        super(layoutResId);
        this.context = context;
        imageLoader = ComponentUtils.getAppComponent().imageLoader();
    }

    @Override
    protected void convert(BaseViewHolder helper, PursueGroupBean item) {
        tvAlbumTitle = helper.getView(R.id.tv_group_title);
        rvpPursueAlbum = helper.getView(R.id.rvp_pursue_album);
        tvAlbumPublishTime = helper.getView(R.id.tv_album_publish_time);
        tvAlbumTitle.setText(item.getGroupTitle());
        tvAlbumPublishTime.setText(item.getPublish_time());
        rvpPursueAlbum.setPages(item.getList(), new RandyViewPager.ViewHolderCreator() {
            private TextView tvTitle;
            private ImageView ivThumb;

            @Override
            public ViewHolder createViewHolder() {
                return new ViewHolder<PursueBean>() {
                    @Override
                    public View createView(Context context) {
                        View view = LayoutInflater.from(context)
                                .inflate(R.layout.pursue_banner_item, null);
                        tvTitle = view.findViewById(R.id.tv_title);
                        ivThumb = view.findViewById(R.id.iv_thumb);
                        return view;
                    }

                    @Override
                    public void onBind(Context context, int position, PursueBean data) {
                        tvTitle.setText(data.getTitle());
                        imageLoader.loadImage(context, GlideImageConfig
                                .builder()
                                .url(data.getThumb())
                                .imageView(ivThumb)
                                .build());
                    }
                };
            }
        });
    }
}
