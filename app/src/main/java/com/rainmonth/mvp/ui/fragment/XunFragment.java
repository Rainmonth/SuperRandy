package com.rainmonth.mvp.ui.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.rainmonth.R;
import com.rainmonth.common.adapter.ListViewDataAdapter;
import com.rainmonth.common.adapter.ViewHolderBase;
import com.rainmonth.common.adapter.ViewHolderCreator;
import com.rainmonth.common.base.BaseLazyFragment;
import com.rainmonth.common.di.component.AppComponent;
import com.rainmonth.common.eventbus.EventCenter;
import com.rainmonth.di.component.DaggerXunComponent;
import com.rainmonth.di.module.XunModule;
import com.rainmonth.mvp.contract.XunContract;
import com.rainmonth.mvp.model.bean.XunNavigationBean;
import com.rainmonth.mvp.presenter.XunPresenter;
import com.rainmonth.mvp.ui.activity.GridExploreActivity;
import com.rainmonth.mvp.ui.activity.ListExploreActivity;
import com.rainmonth.mvp.ui.activity.ViewPagerExploreActivity;
import com.rainmonth.router.RouterConstant;
import com.rainmonth.router.RouterUtils;
import com.socks.library.KLog;

import java.util.List;

import butterknife.BindView;
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
public class XunFragment extends BaseLazyFragment<XunPresenter> implements XunContract.View {
    @BindView(R.id.gv_content)
    GridView gvContent;

    private ListViewDataAdapter<XunNavigationBean> mXunNavListAdapter = null;
    public final static int TYPE_ARTICLE = 1,
            TYPE_IMAGE = 2, TYPE_MUSIC = 3,
            TYPE_FILM = 4, TYPE_APP = 5;

    @Override
    public void onFirstUserVisible() {

    }

    @Override
    public int getContentViewLayoutID() {
        return R.layout.fragment_xun;
    }

    @Override
    protected void onEventComing(EventCenter eventCenter) {

    }

    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }

    @Override
    public void onUserVisible() {

    }

    @Override
    public void onUserInvisible() {

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
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    protected void initViewsAndEvents(View view) {
        mPresenter.initialize();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void initViews(List<XunNavigationBean> xunNavigationBeanList) {
        mXunNavListAdapter = new ListViewDataAdapter<>(new ViewHolderCreator<XunNavigationBean>() {
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
                    mPresenter.navToDetail(xunNavigationBean);
                }
            }
        });
    }

    @Override
    public void navToDetail(XunNavigationBean xunNavigationBean) {
        int type = xunNavigationBean.getType();
        KLog.e("Randy", type);
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
//                readyGo(MusicHomeActivity.class);
                RouterUtils.getInstance().build(RouterConstant.PATH_MUSIC_HOME).navigation();
                break;
            case TYPE_FILM:
                // 电影首页
                readyGo(ListExploreActivity.class);
                break;
            case TYPE_APP:
                // app首页
//                readyGo(CardExploreActivity.class);
                RouterUtils.getInstance().build(RouterConstant.PATH_APP_HOME).navigation();
                break;
            default:
                // viewPager形式展现
                readyGo(ViewPagerExploreActivity.class);
                break;

        }
    }
}
