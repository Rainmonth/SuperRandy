package com.rainmonth.app.mvp.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rainmonth.app.R;
import com.rainmonth.app.mvp.model.bean.CardBean;
import com.rainmonth.common.base.BaseLazyFragment;
import com.rainmonth.common.eventbus.EventCenter;
import com.rainmonth.common.utils.CommonUtils;
import com.rainmonth.common.utils.DensityUtils;
import com.rainmonth.common.widgets.HtmlTextView;

import butterknife.ButterKnife;

public class CardFragment extends BaseLazyFragment {
    protected CardBean mCardBean;

    protected TextView mAuthorText;
    protected ImageView mBottomEdgeImageView;
    protected TextView mBravoNumText;
    protected RelativeLayout mCardLayout;
    protected ImageView mCoverImageView;
    protected HtmlTextView mDigestText;
    protected TextView mSubTitleText;
    protected TextView mTitleText;

    public static CardFragment getInstance(CardBean cardBean) {
        CardFragment localCardFragment = new CardFragment();
        Bundle localBundle = new Bundle();
        localBundle.putSerializable("cardBean", cardBean);
        localCardFragment.setArguments(localBundle);
        return localCardFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);

        return rootView;
    }

    protected View initViews(View view) {
        mCardLayout = ((RelativeLayout) view.findViewById(R.id.box_card));
        mBottomEdgeImageView = ((ImageView) view.findViewById(R.id.image_bottom_edge));
        mCoverImageView = ((ImageView) view.findViewById(R.id.image_cover));
        mTitleText = ((TextView) view.findViewById(R.id.text_title));
        mSubTitleText = ((TextView) view.findViewById(R.id.text_subtitle));
        mDigestText = ((HtmlTextView) view.findViewById(R.id.text_digest));
        mAuthorText = ((TextView) view.findViewById(R.id.text_author));
        mBravoNumText = ((TextView) view.findViewById(R.id.text_bravos));

        if (null != mCardBean) {
            mTitleText.setText(this.mCardBean.getTitle());
            mSubTitleText.setText(this.mCardBean.getSubTitle());
            this.mBravoNumText.setText("  " + this.mCardBean.getUpNum());
            this.mDigestText.setTextViewHtml(mCardBean.getDigest());
            this.mAuthorText.setText(Html.fromHtml("<B>" + this.mCardBean.getAuthorName() + "</B>"));
            initAndDisplayCoverImage();
        }

        return view;
    }

    protected void initAndDisplayCoverImage() {
        int coverWidth = DensityUtils.getScreenWidth(getActivity()) - 2 * getResources().getDimensionPixelSize(R.dimen.m4);
        int coverHeight = (int) (180.0F * (coverWidth / 320.0F));
        ViewGroup.LayoutParams localLayoutParams = this.mCoverImageView.getLayoutParams();
        localLayoutParams.height = Float.valueOf(coverHeight).intValue();
        //加载图片
        int picResource = CommonUtils.getDrawableIdByName(getActivity(), mCardBean.getCoverImageUrl());
        mCoverImageView.setBackgroundResource(picResource);
    }

    protected void initData() {
        this.mCardBean = (CardBean) getArguments().getSerializable("cardBean");
    }

    protected void initActions(View paramView) {
    }

    public void onDestroy() {
        this.mCoverImageView.setImageBitmap(null);
        super.onDestroy();
    }

    @Override
    public void onFirstUserVisible() {

    }

    @Override
    public int getContentViewLayoutID() {
        return R.layout.app_fragment_card;
    }

    @Override
    protected void onEventComing(EventCenter eventCenter) {

    }

    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }

    @Override
    public void onUserVisible() {

    }

    @Override
    public void onUserInvisible() {

    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    public void initViewsAndEvents(View view) {
        initData();
        initViews(view);
        initActions(view);
    }

    public void onDestroyView() {
        this.mCoverImageView.setImageBitmap(null);
        super.onDestroyView();
    }
}