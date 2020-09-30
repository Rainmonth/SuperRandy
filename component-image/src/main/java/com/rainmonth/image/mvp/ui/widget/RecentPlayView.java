package com.rainmonth.image.mvp.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.rainmonth.common.utils.DensityUtils;
import com.rainmonth.common.utils.ToastUtils;
import com.rainmonth.image.R;

import java.util.ArrayList;
import java.util.List;

/**
 * todo 采用merge标签替换
 *
 * @author RandyZhang
 * @date 2020/9/30 4:44 PM
 */
public class RecentPlayView extends ConstraintLayout {

    TextView tvLabel;
    LinearLayout llContinuePlayContainer;
    RecyclerView rvList;
    private List<RecentPlayBean> data = new ArrayList<>();

    public RecentPlayView(Context context) {
        super(context);
        init(context);
    }

    public RecentPlayView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public RecentPlayView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);


    }

    private void init(Context context) {
        View.inflate(context, R.layout.image_recent_play_view, this);

        tvLabel = findViewById(R.id.tv_label);
        llContinuePlayContainer = findViewById(R.id.ll_continue_play_container);
        rvList = findViewById(R.id.rv_list);

        for (int i = 0; i < 10; i++) {
            data.add(new RecentPlayBean());
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        rvList.setLayoutManager(linearLayoutManager);

        RecentPlayAdapter recentPlayAdapter = new RecentPlayAdapter(context, data);
        rvList.setAdapter(recentPlayAdapter);
    }


    private static class RecentPlayAdapter extends RecyclerView.Adapter<RecentPlayViewHolder> {

        private List<RecentPlayBean> mData = new ArrayList<>();

        private Context mContext;
        int coverWidth;

        public RecentPlayAdapter(Context context, List<RecentPlayBean> list) {
            mContext = context;
            coverWidth = (int) ((DensityUtils.getScreenWidth(mContext) - 3 * DensityUtils.dip2px(mContext, 20) - 3 * DensityUtils.dip2px(mContext, 12)) / 3.5);
            if (list != null) {
                mData = list;
            }
        }

        @NonNull
        @Override
        public RecentPlayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_recent_play_item_view, parent, false);
            return new RecentPlayViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull RecentPlayViewHolder holder, int position) {
            holder.ivCover.getLayoutParams().width = coverWidth;
            holder.ivCover.setImageResource(R.drawable.image_coin_coin);
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }
    }

    private static class RecentPlayViewHolder extends RecyclerView.ViewHolder {
        ImageView ivCover;

        public RecentPlayViewHolder(@NonNull View itemView) {
            super(itemView);
            ivCover = itemView.findViewById(R.id.iv_cover);
        }
    }

    public void onRealRender(BaseViewHolder holder, RecentPlayBean data) {

        ImageView ivCover = holder.getView(R.id.iv_cover);
        ivCover.setImageResource(R.drawable.image_coin_coin);
    }

    public void onRealItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        ToastUtils.showShortToast(getContext(), "Item " + i + " clicked");
    }

    private int getItemLayoutId() {
        return R.layout.image_recent_play_item_view;
    }

}
