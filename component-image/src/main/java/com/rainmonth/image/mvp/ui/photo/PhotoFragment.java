package com.rainmonth.image.mvp.ui.photo;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.rainmonth.common.base.BaseLazyFragment;
import com.rainmonth.common.base.BaseWebActivity;
import com.rainmonth.common.di.component.AppComponent;
import com.rainmonth.common.http.imageloader.ImageLoader;
import com.rainmonth.common.http.imageloader.glide.GlideImageConfig;
import com.rainmonth.common.utils.ComponentUtils;
import com.rainmonth.common.widgets.RoundedImageView;
import com.rainmonth.image.R;
import com.rainmonth.image.api.Consts;
import com.rainmonth.image.mvp.model.bean.PhotoBean;
import com.rainmonth.image.mvp.model.bean.UserBean;

public class PhotoFragment extends BaseLazyFragment implements View.OnClickListener {
    private ImageLoader imageLoader;


    private RoundedImageView ivAuthorImage;
    private TextView tvAuthorName;
    private PhotoBean photoBean;

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
        photoBean = (PhotoBean) getArguments().getSerializable(Consts.PHOTO_BEAN);
        ImageView photoImage = view.findViewById(R.id.photoImage);
        ivAuthorImage = view.findViewById(R.id.iv_author_image);
        tvAuthorName = view.findViewById(R.id.tv_author_name);

        ivAuthorImage.setOnClickListener(this);
        tvAuthorName.setOnClickListener(this);
        imageLoader = ComponentUtils.getAppComponent().imageLoader();
        if (photoBean != null) {
            imageLoader.loadImage(mContext, GlideImageConfig
                    .builder()
                    .url(photoBean.getUrls().getSmall())
                    .imageView(photoImage).build());
            UserBean userBean = photoBean.getUser();
            if (userBean != null) {
                ImageLoader imageLoader = ComponentUtils.getAppComponent().imageLoader();
                if (userBean.getProfile_image() != null) {
                    imageLoader.loadImage(mContext, GlideImageConfig
                            .builder()
                            .isAsBitmap(true)
                            .url(userBean.getProfile_image().getMedium())
                            .imageView(ivAuthorImage).build());
                }
                tvAuthorName.setText(userBean.getName());
            }
        }
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.image_fragment_photo;
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.iv_author_image || i == R.id.tv_author_name) {
            if (photoBean != null && photoBean.getUser() != null) {
                Bundle bundle = new Bundle();
                bundle.putString(BaseWebActivity.BUNDLE_KEY_URL, photoBean.getUser().getLinks().getHtml());
                bundle.putString(BaseWebActivity.BUNDLE_KEY_TITLE, photoBean.getUser().getName());
                readyGo(BaseWebActivity.class, bundle);
            }

        }
    }
}
