package com.rainmonth.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rainmonth.R;
import com.rainmonth.bean.CardInfo;
import com.rainmonth.utils.CommonUtils;
import com.rainmonth.utils.DensityUtils;
import com.rainmonth.widgets.HtmlTextView;

import butterknife.ButterKnife;

public class CardFragment extends BaseLazyFragment {
    protected CardInfo mCard;

    protected TextView mAuthorText;
    protected ImageView mBottomEdgeImageView;
    protected TextView mBravoNumText;
    protected RelativeLayout mCardLayout;
    protected ImageView mCoverImageView;
    protected HtmlTextView mDigestText;
    protected TextView mSubTitleText;
    protected TextView mTitleText;

    public static CardFragment getInstance(CardInfo CardInfo) {
        CardFragment localCardFragment = new CardFragment();
        Bundle localBundle = new Bundle();
        localBundle.putSerializable("card", CardInfo);
        localCardFragment.setArguments(localBundle);
        return localCardFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        initViews(rootView);
        initActions(rootView);
        initData();
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

        if (null != mCard) {
            mTitleText.setText(this.mCard.getTitle());
            mSubTitleText.setText(this.mCard.getSubTitle());
            this.mBravoNumText.setText("  " + this.mCard.getUpNum());
            this.mDigestText.setTextViewHtml(mCard.getDigest());
            this.mAuthorText.setText(Html.fromHtml("<B>" + this.mCard.getAuthorName() + "</B>"));
            initAndDisplayCoverImage();
        }

        return view;
    }

    protected void initAndDisplayCoverImage() {
        int coverWidth = DensityUtils.getScreenWidth(getActivity()) - 2 * getResources().getDimensionPixelSize(R.dimen.card_margin);
        int coverHeight = (int) (180.0F * (coverWidth / 320.0F));
        ViewGroup.LayoutParams localLayoutParams = this.mCoverImageView.getLayoutParams();
        localLayoutParams.height = Float.valueOf(coverHeight).intValue();
        //加载图片
        int picResource = CommonUtils.getDrawableIdByName(getActivity(), mCard.getCoverImageUrl());
        mCoverImageView.setBackgroundResource(picResource);
    }

    protected void initData() {
        this.mCard = (CardInfo) getArguments().getSerializable("card");
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
        return R.layout.fragment_card;
    }

    @Override
    public void onUserVisible() {

    }

    @Override
    public void onUserInvisible() {

    }

    public void onDestroyView() {
        this.mCoverImageView.setImageBitmap(null);
        super.onDestroyView();
    }
}