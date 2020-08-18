package com.rainmonth.image.mvp.ui.common;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.rainmonth.common.base.BaseActivity;
import com.rainmonth.common.base.BaseWebActivity;
import com.rainmonth.common.di.component.AppComponent;
import com.rainmonth.image.R;
import com.rainmonth.image.api.Consts;
import com.rainmonth.image.mvp.ui.collection.CollectionHomeActivity;
import com.rainmonth.image.mvp.ui.photo.PhotoHomeActivity;
import com.rainmonth.image.mvp.ui.search.SearchActivity;
import com.rainmonth.image.mvp.ui.usercenter.UserCenterActivity;
import com.rainmonth.image.selector.FileSelectActivity;
import com.rainmonth.router.RouterConstant;

/**
 * 主展示页面
 * 功能简介
 * -
 */
@Route(path = RouterConstant.PATH_IMAGE_HOME)
public class ImageMainActivity extends BaseActivity {

    Button imageBtnPhoto;
    Button imageBtnCollection;
    Button imageBtnSearch;
    Button imageBtnUserCenter;
    Button imageBtnAscii;
    Button imageBtnDraw;
    Button btnAuth;
    Button btnLoadMedia;
    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.image_activity_main;
    }

    @Override
    protected void initViewsAndEvents() {
        imageBtnPhoto = findViewById(R.id.image_btn_photo);
        imageBtnCollection = findViewById(R.id.image_btn_collection);
        imageBtnSearch = findViewById(R.id.image_btn_search);
        imageBtnUserCenter = findViewById(R.id.image_btn_user_center);
        imageBtnAscii = findViewById(R.id.image_btn_ascii);
        imageBtnDraw = findViewById(R.id.image_btn_draw);
        btnAuth = findViewById(R.id.btn_auth);
        btnLoadMedia = findViewById(R.id.btn_load_media);
        imageBtnPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readyGo(PhotoHomeActivity.class);
            }
        });
        imageBtnCollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readyGo(CollectionHomeActivity.class);
            }
        });
        imageBtnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readyGo(SearchActivity.class);
            }
        });
        imageBtnUserCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readyGo(UserCenterActivity.class);
            }
        });
        imageBtnAscii.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readyGo(PicToAsciiActivity.class);
            }
        });

        imageBtnDraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readyGo(ComponentTestActivity.class);
            }
        });

        btnAuth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString(BaseWebActivity.BUNDLE_KEY_URL, Consts.GRANT_URL);
                bundle.putString(BaseWebActivity.BUNDLE_KEY_TITLE, "授权");
                readyGo(BaseWebActivity.class, bundle);
            }
        });

        btnLoadMedia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readyGo(FileSelectActivity.class);
            }
        });
    }

    @Override
    public void initToolbar(int colorResId) {

    }
}
