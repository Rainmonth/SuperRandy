package com.rainmonth.image.mvp.ui.search;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.recyclerview.widget.RecyclerView;

import com.rainmonth.common.base.BaseActivity;
import com.rainmonth.common.di.component.AppComponent;
import com.rainmonth.utils.StringUtils;
import com.rainmonth.utils.log.LogUtils;
import com.rainmonth.image.R;
import com.rainmonth.image.api.Consts;
import com.rainmonth.image.di.component.DaggerSearchComponent;
import com.rainmonth.image.di.module.SearchModule;
import com.rainmonth.image.mvp.contract.SearchContract;
import com.rainmonth.image.mvp.model.bean.PhotoBean;
import com.rainmonth.image.mvp.model.bean.SearchResult;
import com.rainmonth.image.mvp.presenter.SearchPresenter;

/**
 * 搜索中间页
 * 1.显示历史搜索记录
 * 2.显示推荐搜索（这个需要自己写接口，Unsplash没有提供接口）
 * Created by RandyZhang on 2018/8/14.
 */
public class SearchActivity extends BaseActivity<SearchPresenter> implements SearchContract.View {
    private EditText     etSearchKeys;
    private Button       btnSearch;
    private RecyclerView rvSearchResults;

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerSearchComponent.builder()
                .appComponent(appComponent)
                .searchModule(new SearchModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void initToolbar(int colorResId) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.image_activity_search;
    }

    @Override
    protected void initViewsAndEvents() {
        etSearchKeys = findViewById(R.id.image_et_search_keys);
        etSearchKeys.setText("computer");
        etSearchKeys.setSelection(etSearchKeys.getText().length());
        btnSearch = findViewById(R.id.image_btn_search);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchKey = etSearchKeys.getText().toString();
                if (StringUtils.isEmpty(searchKey)) {
                    showToast("搜索关键词为空！");
                    return;
                }
//                mPresenter.search(1, etSearchKeys.getText().toString(), 1,
//                        10, "", "");
                Bundle bundle = new Bundle();
                bundle.putString(Consts.SEARCH_KEY, searchKey);
                readyGo(SearchResultActivity.class, bundle);
            }
        });

    }

    @Override
    public <T> void initResultList(SearchResult<T> searchResult) {
        LogUtils.d(searchResult.toString());
        SearchResult<PhotoBean> photoBeanSearchResult = (SearchResult<PhotoBean>) searchResult;
        LogUtils.d("Randy", photoBeanSearchResult.getResults().size());
    }
}
