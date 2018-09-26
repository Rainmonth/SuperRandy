package com.rainmonth.image.mvp.ui.activity;

import android.view.View;
import android.widget.Button;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.rainmonth.common.base.BaseActivity;
import com.rainmonth.common.di.component.AppComponent;
import com.rainmonth.image.R;
import com.rainmonth.router.RouterConstant;

@Route(path = RouterConstant.PATH_IMAGE_HOME)
public class ImageMainActivity extends BaseActivity {

    Button imageBtnPhoto;
    Button imageBtnCollection;
    Button imageBtnSearch;
    Button imageBtnUserCenter;
    Button imageBtnAscii;
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

    }

    @Override
    public void initToolbar(int colorResId) {

    }
}
