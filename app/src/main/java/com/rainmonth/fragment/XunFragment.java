package com.rainmonth.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.rainmonth.R;
import com.rainmonth.activity.CardExploreActivity;
import com.rainmonth.activity.GridExploreActivity;
import com.rainmonth.activity.ListExploreActivity;
import com.rainmonth.activity.MusicHomeActivity;
import com.rainmonth.activity.ViewPagerExploreActivity;
import com.rainmonth.base.ui.adapter.ListViewDataAdapter;
import com.rainmonth.base.ui.adapter.ViewHolderBase;
import com.rainmonth.base.ui.adapter.ViewHolderCreator;
import com.rainmonth.base.ui.fragment.BaseLazyFragment;
import com.rainmonth.bean.XunNavigationBean;
import com.rainmonth.presenter.XunFragmentPresenter;
import com.rainmonth.view.XunFragmentView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 寻 （采用动态配置）
 * 主要有以下内容：
 * ［文章、图片、音乐、电影、应用］
 * 注意：
 * 1、文章采用列表形式展现，点击进入文章详情界面，可分享
 * 2、图片采用瀑布流形式展现，点击进入展示界面，展示界面可以左右滑动进行浏览
 * 3、电影采用列表形式展现，点击进入电影介绍界面，可分享
 * 4、应用自界面采用类似最美应用展现形式；
 * <p/>
 * Created by RandyZhang on 16/6/30.
 */
public class XunFragment extends BaseLazyFragment implements XunFragmentView {
    @Bind(R.id.gv_content)
    GridView gvContent;

    private XunFragmentPresenter xunFragmentPresenter = null;

    private ListViewDataAdapter<XunNavigationBean> mXunNavListAdapter = null;
    public final static int TYPE_ARTICLE = 1, TYPE_IMAGE = 2, TYPE_MUSIC = 3, TYPE_FILM = 4, TYPE_APP = 5;

    @Override
    public void onFirstUserVisible() {

    }

    @Override
    public int getContentViewLayoutID() {
        return R.layout.fragment_xun;
    }

    @Override
    public void onUserVisible() {

    }

    @Override
    public void onUserInvisible() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);

        xunFragmentPresenter = new XunFragmentPresenter(mContext, this);
        xunFragmentPresenter.initialize();

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void initViews(List<XunNavigationBean> xunNavigationBeanList) {
        mXunNavListAdapter = new ListViewDataAdapter<XunNavigationBean>(new ViewHolderCreator<XunNavigationBean>() {
            @Override
            public ViewHolderBase<XunNavigationBean> createViewHolder(int position) {
                return new ViewHolderBase<XunNavigationBean>() {
                    ImageView ivNavImg;
                    TextView tvNavName;

                    @Override
                    public View createView(LayoutInflater layoutInflater) {
                        View convertView = layoutInflater.inflate(R.layout.adapter_xun_gv_content_item, null);
                        ivNavImg = ButterKnife.findById(convertView, R.id.iv_nav_img);
                        tvNavName = ButterKnife.findById(convertView, R.id.tv_nav_name);
                        return convertView;
                    }

                    @Override
                    public void showData(int position, XunNavigationBean itemData) {
                        ivNavImg.setImageResource(itemData.getNavIconResId());
                        tvNavName.setText(itemData.getNavName());

                    }
                };
            }
        });
        mXunNavListAdapter.getDataList().addAll(xunNavigationBeanList);
        gvContent.setAdapter(mXunNavListAdapter);
        gvContent.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // nav to detail activity
                XunNavigationBean xunNavigationBean = mXunNavListAdapter.getDataList().get(position);
                if (null != xunNavigationBean) {
                    xunFragmentPresenter.navToDetail(xunNavigationBean);
                }
            }
        });
    }

    @Override
    public void navToDetail(XunNavigationBean xunNavigationBean) {
        int type = xunNavigationBean.getType();
        switch (type) {
            case TYPE_ARTICLE:
                readyGo(ViewPagerExploreActivity.class);
                break;
            case TYPE_IMAGE:
                // 图片首页
                readyGo(GridExploreActivity.class);
                break;

            case TYPE_MUSIC:
                // 音乐首页
                readyGo(MusicHomeActivity.class);
                break;
            case TYPE_FILM:
                // 电影首页
                readyGo(ListExploreActivity.class);
                break;
            case TYPE_APP:
                // app首页
                readyGo(CardExploreActivity.class);
                break;
            default:
                // viewPager形式展现
                readyGo(ViewPagerExploreActivity.class);
                break;

        }
    }

    @Override
    public void toast(String msg) {

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }
}
