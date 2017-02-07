package com.rainmonth.fragment;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.rainmonth.R;
import com.rainmonth.adapter.BannerViewPagerAdapter;
import com.rainmonth.base.ui.adapter.ListViewDataAdapter;
import com.rainmonth.base.ui.adapter.ViewHolderBase;
import com.rainmonth.base.ui.adapter.ViewHolderCreator;
import com.rainmonth.bean.ArticleBean;
import com.rainmonth.bean.BannerBean;
import com.rainmonth.library.base.BaseLazyFragment;
import com.rainmonth.library.eventbus.EventCenter;
import com.rainmonth.presenter.RenPresenter;
import com.rainmonth.view.RenFragmentView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by RandyZhang on 16/6/30.
 */
public class RenFragment extends BaseLazyFragment implements RenFragmentView {

    public static final String BANNER_BEAN = "banner_bean";
    @Bind(R.id.lv_content)
    ListView lvContent;

    private RenPresenter renPresenter = null;
    private ListViewDataAdapter<ArticleBean> mRenContentListAdapter = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);

        renPresenter = new RenPresenter(this);
        return rootView;
    }

    @Override
    public void onFirstUserVisible() {

    }

    @Override
    public int getContentViewLayoutID() {
        return R.layout.fragment_ren;
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
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    protected void initViewsAndEvents(View view) {
        initContentList(renPresenter.getContentListFake());
        initHomeBanners(renPresenter.getHomeBannerFake());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void initHomeBanners(List<BannerBean> bannerBeanList) {
        List<BaseLazyFragment> bannerFragmentList = new ArrayList<>();
        if (null != bannerBeanList && bannerBeanList.size() > 0) {
            for (int i = 0; i < bannerBeanList.size(); i++) {
                BaseLazyFragment fragment = new HomeBannerFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable(BANNER_BEAN, bannerBeanList.get(i));
                fragment.setArguments(bundle);
                bannerFragmentList.add(fragment);
            }
        }
        View headView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_ren_list_head, null);
        ViewPager viewPager = (ViewPager) headView.findViewById(R.id.vp_ren_fragment);
        viewPager.setAdapter(new BannerViewPagerAdapter(getSupportFragmentManager(), bannerFragmentList));
        lvContent.addHeaderView(headView);
    }

    @Override
    public void initContentList(List<ArticleBean> articleBeanList) {
        mRenContentListAdapter = new ListViewDataAdapter<ArticleBean>(new ViewHolderCreator<ArticleBean>() {
            @Override
            public ViewHolderBase<ArticleBean> createViewHolder(int position) {
                return new ViewHolderBase<ArticleBean>() {
                    ImageView ivBg;
                    //                    TextView tvTagType;
                    TextView tvTagName;
                    TextView tvTitle;

                    @Override
                    public View createView(LayoutInflater layoutInflater) {
                        View convertView = layoutInflater.inflate(R.layout.adapter_ren_lv_content_item, null);
                        ivBg = ButterKnife.findById(convertView, R.id.iv_bg);
                        tvTagName = ButterKnife.findById(convertView, R.id.tv_tag_name);
                        tvTitle = ButterKnife.findById(convertView, R.id.tv_title);
                        return convertView;
                    }

                    @Override
                    public void showData(int position, ArticleBean itemData) {
//                        ivBg.setImageResource(itemData.getImageResId());
//                        tvTagName.setText(itemData.getTagName());
//                        tvTitle.setText(itemData.getTitle());

                    }
                };
            }
        });
        mRenContentListAdapter.getDataList().addAll(articleBeanList);
        lvContent.setAdapter(mRenContentListAdapter);
        lvContent.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // nav to detail activity
                ArticleBean articleBean = mRenContentListAdapter.getDataList().get(position);
                if (null != articleBean) {
                    renPresenter.navToDetail(articleBean);
                }
            }
        });
    }

    @Override
    public void navToDetail(ArticleBean articleBean) {
        // todo 进入二级界面
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
