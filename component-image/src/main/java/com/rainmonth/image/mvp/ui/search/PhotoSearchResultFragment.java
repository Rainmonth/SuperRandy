package com.rainmonth.image.mvp.ui.search;

import android.os.Bundle;
import android.view.View;

import com.rainmonth.common.base.BaseLazyFragment;
import com.rainmonth.common.di.component.AppComponent;
import com.rainmonth.image.R;
import com.rainmonth.image.api.Consts;

public class PhotoSearchResultFragment extends BaseLazyFragment {

    public static PhotoSearchResultFragment getInstance(String searchKey) {
        PhotoSearchResultFragment photoSearchResultFragment = new PhotoSearchResultFragment();
        Bundle localBundle = new Bundle();
        localBundle.putString(Consts.SEARch_KEY, searchKey);
        photoSearchResultFragment.setArguments(localBundle);
        return photoSearchResultFragment;
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

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.image_fragment_photo_search_result;
    }
}
