package com.rainmonth.mvp.ui.fragment;

import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.rainmonth.common.adapter.base.BaseQuickAdapter;
import com.rainmonth.R;
import com.rainmonth.common.base.BaseLazyFragment;
import com.rainmonth.common.di.component.AppComponent;
import com.rainmonth.common.utils.log.LogUtils;
import com.rainmonth.di.component.DaggerXunComponent;
import com.rainmonth.di.module.XunModule;
import com.rainmonth.mvp.contract.XunContract;
import com.rainmonth.mvp.model.bean.XunNavigationBean;
import com.rainmonth.mvp.presenter.XunPresenter;
import com.rainmonth.mvp.ui.activity.ListExploreActivity;
import com.rainmonth.mvp.ui.activity.ViewPagerExploreActivity;
import com.rainmonth.mvp.ui.adapter.XunListAdapter;
import com.rainmonth.router.RouterConstant;
import com.rainmonth.router.RouterUtils;

import java.util.List;

import butterknife.BindView;

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
public class XunFragment extends BaseLazyFragment<XunPresenter> implements XunContract.View {
    @BindView(R.id.srl_container)
    SwipeRefreshLayout srlContainer;
    @BindView(R.id.rv_xun_content)
    RecyclerView       rvContent;

    private             XunListAdapter mAdapter     = null;
    public final static int            TYPE_ARTICLE = 1,
            TYPE_IMAGE                              = 2, TYPE_MUSIC = 3,
            TYPE_FILM                               = 4, TYPE_APP = 5;

    @Override
    public void onFirstUserVisible() {
        srlContainer.setRefreshing(true);
        mPresenter.getNavigationList();
    }

    @Override
    public void onUserVisible() {

    }

    @Override
    public void onUserInvisible() {

    }

    @Override
    public int getContentViewLayoutID() {
        return R.layout.fragment_xun;
    }

    @Override
    protected void setupFragmentComponent(AppComponent appComponent) {
        DaggerXunComponent.builder()
                .appComponent(appComponent)
                .xunModule(new XunModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected void initViewsAndEvents(View view) {
        srlContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                srlContainer.setRefreshing(true);
                mPresenter.getNavigationList();
            }
        });
        mAdapter = new XunListAdapter(R.layout.adapter_xun_gv_content_item, mContext);
        GridLayoutManager manager = new GridLayoutManager(mContext, 2);
        rvContent.setLayoutManager(manager);
        rvContent.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                XunNavigationBean xunNavigationBean = mAdapter.getData().get(position);
                if (null != xunNavigationBean) {
                    mPresenter.navToDetail(xunNavigationBean);
                }
            }
        });
    }

    @Override
    public void initNavigationContent(List<XunNavigationBean> xunNavigationBeanList) {
        srlContainer.setRefreshing(false);
        mAdapter.setNewData(xunNavigationBeanList);
    }

    @Override
    public void navToDetail(XunNavigationBean xunNavigationBean) {
        int type = xunNavigationBean.getType();
        LogUtils.e("Randy", type);
        switch (type) {
            case TYPE_ARTICLE:
                readyGo(ViewPagerExploreActivity.class);
                break;
            case TYPE_IMAGE:
                // 图片首页
                RouterUtils.getInstance().build(RouterConstant.PATH_IMAGE_HOME).navigation();
                break;

            case TYPE_MUSIC:
                // 音乐首页
//                readyGo(MusicHomeActivity.class);
                RouterUtils.getInstance().build(RouterConstant.PATH_MUSIC_HOME).navigation();
                break;
            case TYPE_FILM:
                // 电影首页
                readyGo(ListExploreActivity.class);
                break;
            case TYPE_APP:
                // app首页
                RouterUtils.getInstance().build(RouterConstant.PATH_APP_HOME).navigation();
                break;
            default:
                // viewPager形式展现
                readyGo(ViewPagerExploreActivity.class);
                break;

        }
    }
}
