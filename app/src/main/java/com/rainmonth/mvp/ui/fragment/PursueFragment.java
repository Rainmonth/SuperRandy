package com.rainmonth.mvp.ui.fragment;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.rainmonth.R;
import com.rainmonth.common.base.BaseLazyFragment;
import com.rainmonth.common.di.component.AppComponent;
import com.rainmonth.mvp.ui.adapter.PursueContentAdapter;

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
        rvPursueContent = view.findViewById(R.id.rv_pursue_content);
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
