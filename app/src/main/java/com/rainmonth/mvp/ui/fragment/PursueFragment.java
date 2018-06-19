package com.rainmonth.mvp.ui.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.rainmonth.R;
import com.rainmonth.common.base.BaseLazyFragment;
import com.rainmonth.common.di.component.AppComponent;
import com.rainmonth.mvp.model.bean.PursueBean;
import com.rainmonth.mvp.model.bean.PursueGroupBean;
import com.rainmonth.mvp.ui.adapter.PursueContentAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by RandyZhang on 16/6/30.
 */
public class PursueFragment extends BaseLazyFragment {
    private RecyclerView rvPursueContent;
    private PursueContentAdapter mAdapter;

    @Override
    protected void setupFragmentComponent(AppComponent appComponent) {

    }

    @Override
    public int getContentViewLayoutID() {
        return R.layout.fragment_pursue;
    }

    @Override
    protected void initViewsAndEvents(View view) {
        List<PursueGroupBean> data = getFakeData();
        mAdapter = new PursueContentAdapter(R.layout.pursue_content_rv_item, mContext);
        rvPursueContent = view.findViewById(R.id.rv_pursue_content);
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        rvPursueContent.setLayoutManager(manager);
        mAdapter.setNewData(data);
        rvPursueContent.setAdapter(mAdapter);

    }

    private List<PursueGroupBean> getFakeData() {
        List<PursueBean> pursueBeans = getPursueBeanList();
        List<PursueGroupBean> data = new ArrayList<>();
        data.add(new PursueGroupBean(1, "热门推荐1", pursueBeans));
        data.add(new PursueGroupBean(1, "热门推荐2", pursueBeans));
        data.add(new PursueGroupBean(1, "热门推荐3", pursueBeans));
        data.add(new PursueGroupBean(1, "热门推荐4", pursueBeans));
        return data;
    }

    private List<PursueBean> getPursueBeanList() {
        List<PursueBean> pursueBeans = new ArrayList<>();
        pursueBeans.add(new PursueBean(1, "追风少年1", "http://rainmonth.cn/public/assets/banner/byj.jpeg"));
        pursueBeans.add(new PursueBean(1, "追风少年2", "http://rainmonth.cn/public/assets/banner/girl2.jpg"));
        pursueBeans.add(new PursueBean(1, "追风少年3", "http://rainmonth.cn/public/assets/banner/byj.jpeg"));
        pursueBeans.add(new PursueBean(1, "追风少年4", "http://rainmonth.cn/public/assets/banner/byj.jpeg"));
        return pursueBeans;
    }

    @Override
    public void onFirstUserVisible() {

    }

    @Override
    public void onUserVisible() {

    }

    @Override
    public void onUserInvisible() {

    }
}
