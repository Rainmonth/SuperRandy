package com.rainmonth.image.mvp.ui.photo;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.rainmonth.common.base.BaseLazyFragment;
import com.rainmonth.common.di.component.AppComponent;
import com.rainmonth.common.http.imageloader.ImageConfig;
import com.rainmonth.common.http.imageloader.ImageLoader;
import com.rainmonth.common.http.imageloader.glide.GlideImageConfig;
import com.rainmonth.common.utils.ComponentUtils;
import com.rainmonth.image.R;
import com.rainmonth.image.api.Consts;
import com.rainmonth.image.mvp.model.bean.PhotoBean;

public class PhotoFragment extends BaseLazyFragment {
    private ImageLoader imageLoader;

    public static PhotoFragment getInstance(PhotoBean photoBean) {
        PhotoFragment photoFragment = new PhotoFragment();
        Bundle localBundle = new Bundle();
        localBundle.putSerializable(Consts.PHOTO_BEAN, photoBean);
        photoFragment.setArguments(localBundle);
        return photoFragment;
    }

    @Override
    protected void onFirstUserVisible() {

    }

    @Override
    protected void onUserVisible() {

    }

    @Override
    protected void onUserInvisible() {

    }

    @Override
    protected void setupFragmentComponent(AppComponent appComponent) {

    }

    @Override
    protected void initViewsAndEvents(View view) {
        PhotoBean photoBean = (PhotoBean) getArguments().getSerializable(Consts.PHOTO_BEAN);
        ImageView photoImage = view.findViewById(R.id.photoImage);
        imageLoader = ComponentUtils.getAppComponent().imageLoader();
        imageLoader.loadImage(mContext, GlideImageConfig
                .builder()
                .url(photoBean.getUrls().getSmall())
                .imageView(photoImage).build());
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.image_fragment_photo;
    }
}
