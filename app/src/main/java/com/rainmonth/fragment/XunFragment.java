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
import com.rainmonth.activity.ViewPagerExploreActivity;
import com.rainmonth.adapter.base.ListViewDataAdapter;
import com.rainmonth.adapter.base.ViewHolderBase;
import com.rainmonth.adapter.base.ViewHolderCreator;
import com.rainmonth.bean.XunNavigationInfo;
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

    private ListViewDataAdapter<XunNavigationInfo> mXunNavListAdapter = null;
    public final static int TYPE_CARD = 1, TYPE_GRID = 2, TYPE_VIEW_PAGER = 3, TYPE_LIST = 4;

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
    public void initViews(List<XunNavigationInfo> xunNavigationInfoList) {
        mXunNavListAdapter = new ListViewDataAdapter<XunNavigationInfo>(new ViewHolderCreator<XunNavigationInfo>() {
            @Override
            public ViewHolderBase<XunNavigationInfo> createViewHolder(int position) {
                return new ViewHolderBase<XunNavigationInfo>() {
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
                    public void showData(int position, XunNavigationInfo itemData) {
                        ivNavImg.setImageResource(itemData.getNavIconResId());
                        tvNavName.setText(itemData.getNavName());

                    }
                };
            }
        });
        mXunNavListAdapter.getDataList().addAll(xunNavigationInfoList);
        gvContent.setAdapter(mXunNavListAdapter);
        gvContent.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // nav to detail activity
                XunNavigationInfo xunNavigationInfo = mXunNavListAdapter.getDataList().get(position);
                if (null != xunNavigationInfo) {
                    xunFragmentPresenter.navToDetail(xunNavigationInfo);
                }
            }
        });
    }

    @Override
    public void navToDetail(XunNavigationInfo xunNavigationInfo) {
        int type = xunNavigationInfo.getType();
        switch (type) {
            case TYPE_CARD:
                // card 形式展现
                readyGo(CardExploreActivity.class);
                break;

            case TYPE_GRID:
                // grid 形式展现
                readyGo(GridExploreActivity.class);
                break;
            case TYPE_LIST:
                // list 形式展现
                readyGo(ListExploreActivity.class);
                break;
            case TYPE_VIEW_PAGER:
            default:
                // viewPager形式展现
                readyGo(ViewPagerExploreActivity.class);
                break;

        }
    }
}
