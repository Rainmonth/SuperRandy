package com.rainmonth.widgets;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.rainmonth.R;
import com.rainmonth.bean.CardInfo;
import com.rainmonth.library.utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;

public class RhythmAdapter extends BaseAdapter {

    /**
     * item的宽度
     */
    private float itemWidth;
    /**
     * 数据源
     */
    private List<CardInfo> mCardInfoList;

    private LayoutInflater mInflater;
    private Context mContext;
    private RhythmLayout mRhythmLayout;

    public RhythmAdapter(Context context, RhythmLayout rhythmLayout, List<CardInfo> cardInfoList) {
        this.mContext = context;
        this.mRhythmLayout = rhythmLayout;
        this.mCardInfoList = new ArrayList();
        this.mCardInfoList.addAll(cardInfoList);
        if (context != null)
            this.mInflater = LayoutInflater.from(context);
    }

    public List<CardInfo> getCardList() {
        return this.mCardInfoList;
    }

    public void addCardList(List<CardInfo> cardInfoList) {
        mCardInfoList.addAll(cardInfoList);
    }

    public int getCount() {
        return this.mCardInfoList.size();
    }

    public Object getItem(int position) {
        return this.mCardInfoList.get(position);
    }

    public long getItemId(int paramInt) {
        return (this.mCardInfoList.get(paramInt)).getUid();
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RelativeLayout relativeLayout = (RelativeLayout) this.mInflater.inflate(R.layout.adapter_rhythm_icon, null);
        //设置item布局的大小以及Y轴的位置
        relativeLayout.setLayoutParams(new RelativeLayout.LayoutParams((int) itemWidth, mContext.getResources().getDimensionPixelSize(R.dimen.rhythm_item_height)));
        relativeLayout.setTranslationY(itemWidth);

        //设置第二层RelativeLayout布局的宽和高
        RelativeLayout childRelativeLayout = (RelativeLayout) relativeLayout.getChildAt(0);
        int relativeLayoutWidth = (int) itemWidth - 2 * mContext.getResources().getDimensionPixelSize(R.dimen.rhythm_icon_margin);
        childRelativeLayout.setLayoutParams(new RelativeLayout.LayoutParams(relativeLayoutWidth, mContext.getResources().getDimensionPixelSize(R.dimen.rhythm_item_height) - 2 * mContext.getResources().getDimensionPixelSize(R.dimen.rhythm_icon_margin)));

        ImageView imageIcon = (ImageView) relativeLayout.findViewById(R.id.image_icon);
        //计算ImageView的大小
        int iconSize = (relativeLayoutWidth - 2 * mContext.getResources().getDimensionPixelSize(R.dimen.rhythm_icon_margin));
        ViewGroup.LayoutParams iconParams = imageIcon.getLayoutParams();
        iconParams.width = iconSize;
        iconParams.height = iconSize;
        imageIcon.setLayoutParams(iconParams);
        //设置背景图片
        imageIcon.setBackgroundResource(CommonUtils.getDrawableIdByName(mContext, mCardInfoList.get(position).getIconUrl()));

        return relativeLayout;
    }

    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        this.mRhythmLayout.invalidateData();
    }

    public void setCardList(List<CardInfo> paramList) {
        this.mCardInfoList = paramList;
    }

    /**
     * 设置每个item的宽度
     */
    public void setItemWidth(float width) {
        this.itemWidth = width;
    }
}