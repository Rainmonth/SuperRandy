package com.rainmonth.music.component;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.rainmonth.adapter.base.BaseQuickAdapter;
import com.rainmonth.adapter.base.BaseViewHolder;
import com.rainmonth.common.bean.CateBean;
import com.rainmonth.common.bean.RandyLogicBean;
import com.rainmonth.common.component.Const;
import com.rainmonth.common.component.RandyHListItemProvider;
import com.rainmonth.music.R;
import com.rainmonth.utils.log.LogUtils;

/**
 * 功能分类Item提供者
 *
 * @author 张豪成
 * @date 2019-11-25 13:53
 */
public class MusicCateItemProvider extends RandyHListItemProvider<CateBean> {
    private ImageView ivCateIcon;
    private TextView  tvCateTitle;

    public MusicCateItemProvider(RandyLogicBean mLogicBean) {
        super(mLogicBean);
    }

    @Override
    public int viewType() {
        return Const.Type.RANDY_CATE;
    }

    @Override
    public int getItemLayoutId() {
        return R.layout.music_list_item_cate;
    }

    @Override
    public void onRealRender(BaseViewHolder holder, CateBean data) {
        ivCateIcon = holder.getView(R.id.iv_cate_icon);
        tvCateTitle = holder.getView(R.id.tv_cate_title);

        if (data != null) {
            if (!TextUtils.isEmpty(data.title)) {
                tvCateTitle.setText(data.title);
            }
        }
    }

    @Override
    public void onRealItemClick(BaseQuickAdapter adapter, View view, int position) {
        LogUtils.d("position " + position + " clicked!");
    }

}
