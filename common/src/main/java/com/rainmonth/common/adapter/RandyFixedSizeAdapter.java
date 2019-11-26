package com.rainmonth.common.adapter;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;


/**
 * ItemView 为固定宽高的
 * @param <T>
 * @param <VH>
 */
public class RandyFixedSizeAdapter<T, VH extends BaseViewHolder> extends BaseQuickAdapter<T, VH> {

    /**
     * 固定宽度
     */
    private int mWidth;

    /**
     * 固定高度
     */
    private int mHeight;

    public RandyFixedSizeAdapter(List<T> data, int layoutId, int width, int height) {
        super(layoutId, data);
        mWidth = width;
        mHeight = height;
    }

    @Override
    protected void convert(@NonNull VH helper, T item) {
        helper.itemView.getLayoutParams().width = mWidth;
        helper.itemView.getLayoutParams().height = mHeight;
    }
}