package com.rainmonth.image.mvp.ui.activity;

import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.EditText;

import com.rainmonth.common.base.BaseActivity;
import com.rainmonth.common.di.component.AppComponent;
import com.rainmonth.common.utils.CommonUtils;
import com.rainmonth.image.R;
import com.rainmonth.image.di.component.DaggerPhotoHomeComponent;
import com.rainmonth.image.di.component.DaggerSearchComponent;
import com.rainmonth.image.di.module.SearchModule;
import com.rainmonth.image.mvp.contract.SearchContract;
import com.rainmonth.image.mvp.model.bean.PhotoBean;
import com.rainmonth.image.mvp.model.bean.SearchResult;
import com.rainmonth.image.mvp.presenter.SearchPresenter;
import com.socks.library.KLog;

/**
 * Created by RandyZhang on 2018/8/14.
 */
public class SearchActivity extends BaseActivity<SearchPresenter> implements SearchContract.View {
    private EditText etSearchKeys;
    private Button btnSearch;
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
        btnSearch = findViewById(R.id.image_btn_search);
        btnSearch.setOnClickListener(v -> {
            if (CommonUtils.isNullOrEmpty(etSearchKeys.getText().toString())) {
                showToast("搜索关键词为空！");
                return;
            }
            mPresenter.search(1, etSearchKeys.getText().toString(), 1, 10,
                    "", "");
        });

    }

    @Override
    public <T> void initResultList(SearchResult<T> searchResult) {
        KLog.d(searchResult.toString());
        SearchResult<PhotoBean> photoBeanSearchResult = (SearchResult<PhotoBean>) searchResult;
        KLog.d("Randy", photoBeanSearchResult.getResults().size());
    }
}
