package com.rainmonth.mvp.ui.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.rainmonth.R;
import com.rainmonth.adapter.base.BaseQuickAdapter;
import com.rainmonth.adapter.base.BaseViewHolder;
import com.rainmonth.common.http.imageloader.ImageLoader;
import com.rainmonth.common.http.imageloader.glide.GlideImageConfig;
import com.rainmonth.common.utils.ComponentUtils;
import com.rainmonth.mvp.model.bean.ArticleBean;

/**
 * 荏Fragment文章列表适配器
 * Created by RandyZhang on 2018/6/13.
 */

public class ArticleListAdapter extends BaseQuickAdapter<ArticleBean, BaseViewHolder> {
    private ImageLoader imageLoader;
    private Context context;

    public ArticleListAdapter(int layoutResId, Context context) {
        super(layoutResId);
        this.context = context;
        imageLoader = ComponentUtils.getAppComponent().imageLoader();
    }

    @Override
    protected void convert(BaseViewHolder helper, ArticleBean item) {
        ImageView ivThumb = helper.getView(R.id.iv_article_thumb);
        TextView tvTitle = helper.getView(R.id.tv_article_title);
        TextView tvBrief = helper.getView(R.id.tv_article_brief);

        TextView tvViewNum = helper.getView(R.id.tv_btn_view);
        TextView tvCollctNum = helper.getView(R.id.tv_btn_collect);
        TextView tvLikeNum = helper.getView(R.id.tv_btn_like);

        imageLoader.loadImage(context, GlideImageConfig
                .builder()
                .url("http://47.98.215.111/public/assets/banner/byj.jpeg")
                .imageView(ivThumb)
                .build()
        );
        tvTitle.setText(item.getTitle());
        tvBrief.setText(item.getBreif());
    }
}
