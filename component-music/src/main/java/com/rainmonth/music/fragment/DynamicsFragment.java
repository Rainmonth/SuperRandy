package com.rainmonth.music.fragment;

import android.os.Bundle;

import com.rainmonth.common.bean.BannerBean;
import com.rainmonth.common.bean.CateBean;
import com.rainmonth.common.bean.RandyLogicBean;
import com.rainmonth.common.bean.RandyMultiBean;
import com.rainmonth.common.component.Const;
import com.rainmonth.common.component.RandyBannerItemProvider;
import com.rainmonth.common.component.RandyBaseItemProvider;
import com.rainmonth.music.BaseMultiTypeListFragment;
import com.rainmonth.music.bean.SongListBean;
import com.rainmonth.music.component.MusicCateItemProvider;
import com.rainmonth.music.component.MusicSongListItemProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * 推荐Fragment
 */
public class DynamicsFragment extends BaseMultiTypeListFragment<RandyMultiBean> {

    public static DynamicsFragment newInstance(Bundle args) {
        DynamicsFragment fragment = new DynamicsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getItemType(RandyMultiBean randyMultiBean) {
        return super.getItemType(randyMultiBean);
    }


    @Override
    protected List<RandyBaseItemProvider> getProviderList() {
        RandyLogicBean randyLogicBean = new RandyLogicBean();
        providerList.add(new RandyBannerItemProvider(randyLogicBean));
        providerList.add(new MusicCateItemProvider(randyLogicBean));
        providerList.add(new MusicSongListItemProvider(randyLogicBean));
        return providerList;
    }

    @Override
    protected void initData() {
        RandyMultiBean<BannerBean> bannerMultiBean = new RandyMultiBean<>(Const.Type.RANDY_BANNER, getBannerList());
        RandyMultiBean<CateBean> cateMultiBean = new RandyMultiBean<>(Const.Type.RANDY_CATE, getCateList());
        RandyMultiBean<SongListBean> songListMultiBean = new RandyMultiBean<>(Const.Type.RANDY_SONG_LIST, getSongList());

        datas.add(bannerMultiBean);
        datas.add(cateMultiBean);
        datas.add(songListMultiBean);
    }

    private List<BannerBean> getBannerList() {
        List<BannerBean> bannerList = new ArrayList<>();
        bannerList.add(new BannerBean(1, "banner 1", "https://y.gtimg.cn/music/common/upload/MUSIC_FOCUS/1865198.jpg?max_age=2592000"));
        bannerList.add(new BannerBean(2, "banner 2", "https://y.gtimg.cn/music/common/upload/MUSIC_FOCUS/1862141.jpg?max_age=2592000"));
        bannerList.add(new BannerBean(3, "banner 3", "https://y.gtimg.cn/music/common/upload/MUSIC_FOCUS/1865712.jpg?max_age=2592000"));
        bannerList.add(new BannerBean(4, "banner 4", "https://y.gtimg.cn/music/common/upload/MUSIC_FOCUS/1849150.jpg?max_age=2592000"));
        return bannerList;
    }

    private List<CateBean> getCateList() {
        List<CateBean> cateList = new ArrayList<>();
        cateList.add(new CateBean(1, "歌手", ""));
        cateList.add(new CateBean(2, "排行", ""));
        cateList.add(new CateBean(3, "分类歌单", ""));
        cateList.add(new CateBean(4, "电台", ""));
        cateList.add(new CateBean(5, "一起听", ""));
        return cateList;
    }

    private List<SongListBean> getSongList() {
        List<SongListBean> songList = new ArrayList<>();
        songList.add(new SongListBean(1, "song 1", "https://qpic.y.qq.com/music_cover/E5tljftwyuTJhcWy5SmsVeqsSghjdzrxstqAwa1ibFLmeYWH1CTFiawg/300?n=1"));
        songList.add(new SongListBean(2, "song 2", "https://qpic.y.qq.com/music_cover/s7d2SyNMvV9FtG35UU2tEPpD3gT1pwQJlboPBPj679kicyX3wyOC1lQ/300?n=1"));
        songList.add(new SongListBean(3, "song 3", "https://qpic.y.qq.com/music_cover/0yiaX8d9LSmnROyId1RsUUwklzSKKp7RSjfgpbIxRxBGia7Rbs5EjcLA/300?n=1"));
        songList.add(new SongListBean(4, "song 4", "https://qpic.y.qq.com/music_cover/INjfo1hfBB6rKe9QpZsljZNUHn8ibs23DqRVV6J0SADyLKmWRdibjwFQ/300?n=1"));
        songList.add(new SongListBean(5, "song 5", "https://qpic.y.qq.com/music_cover/jZF7DFPDuB0TKtRdGE1raXzicHRP4gDTibIhlvrSLIR6YmJX9OJ313oQ/600?n=1"));
        return songList;
    }

}
