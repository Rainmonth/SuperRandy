package com.rainmonth.music.component;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.rainmonth.common.bean.RandyLogicBean;
import com.rainmonth.common.component.Const;
import com.rainmonth.common.component.RandyHListItemProvider;
import com.rainmonth.common.http.imageloader.glide.GlideImageConfig;
import com.rainmonth.common.utils.ComponentUtils;
import com.rainmonth.music.R;
import com.rainmonth.music.bean.SongListBean;

/**
 * 水平方向ItemProvider
 *
 * @author 张豪成
 * @date 2019-11-25 13:50
 */
public class MusicSongListItemProvider extends RandyHListItemProvider<SongListBean> {

    public MusicSongListItemProvider(RandyLogicBean logicBean) {
        super(logicBean);
    }

    @Override
    public int viewType() {
        return Const.Type.RANDY_SONG_LIST;
    }

    @Override
    public int getItemLayoutId() {
        return R.layout.music_list_item_song_list;
    }

    @Override
    public void onRealRender(BaseViewHolder holder, SongListBean data) {
        ImageView ivSongListCover = holder.getView(R.id.iv_song_list_cover);
        TextView tvSongListTitle = holder.getView(R.id.tv_song_list_title);
        if (data == null) {
            return;
        }

        if (!TextUtils.isEmpty(data.title)) {
            tvSongListTitle.setText(data.title);
        }

        ComponentUtils.getAppComponent().imageLoader().loadImage(ivSongListCover.getContext(), GlideImageConfig
                .builder()
                .url(data.imageUrl)
                .imageView(ivSongListCover)
                .build());
    }

    @Override
    public void onRealItemClick(BaseQuickAdapter adapter, View view, int position) {

    }
}
