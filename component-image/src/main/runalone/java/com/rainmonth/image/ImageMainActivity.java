package com.rainmonth.image;

import android.widget.Button;

import com.rainmonth.common.base.BaseActivity;
import com.rainmonth.common.di.component.AppComponent;
import com.rainmonth.image.mvp.ui.activity.CollectionHomeActivity;
import com.rainmonth.image.mvp.ui.activity.PhotoHomeActivity;
import com.rainmonth.image.mvp.ui.activity.SearchActivity;

import butterknife.BindView;

public class ImageMainActivity extends BaseActivity {

    @BindView(R.id.image_btn_photo)
    Button imageBtnPhoto;
    @BindView(R.id.image_btn_collection)
    Button imageBtnCollection;
    @BindView(R.id.image_btn_search)
    Button imageBtnSearch;

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.image_activity_main;
    }

    @Override
    protected void initViewsAndEvents() {
        imageBtnPhoto.setOnClickListener(v -> readyGo(PhotoHomeActivity.class));
        imageBtnCollection.setOnClickListener(v -> readyGo(CollectionHomeActivity.class));
        imageBtnSearch.setOnClickListener(v -> readyGo(SearchActivity.class));
    }

    @Override
    public void initToolbar(int colorResId) {

    }
}
