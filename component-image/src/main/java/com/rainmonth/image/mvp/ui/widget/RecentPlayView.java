package com.rainmonth.image.mvp.ui.widget;

import android.content.Context;
import android.graphics.Rect;
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

import com.rainmonth.adapter.base.BaseQuickAdapter;
import com.rainmonth.adapter.base.BaseViewHolder;
import com.rainmonth.utils.DensityUtils;
import com.rainmonth.utils.ToastUtils;
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
        int hp = DensityUtils.dip2px(context, 20), vp = DensityUtils.dip2px(context, 8);
        setPadding(hp, vp, hp, vp);

        tvLabel = findViewById(R.id.tv_label);
        llContinuePlayContainer = findViewById(R.id.ll_continue_play_container);
        rvList = findViewById(R.id.rv_list);

        for (int i = 0; i < 10; i++) {
            RecentPlayBean bean = new RecentPlayBean();
            bean.index = i;
            if (i % 2 == 0) {
                bean.type = 1;
            } else {
                bean.type = 2;
            }
            data.add(bean);
        }

//        tvLabel.setOnClickListener(v -> handleLabelClick());
        llContinuePlayContainer.setOnClickListener(v -> {
            if (v.getTag() instanceof RecentPlayBean) {
//                handleContinueClick((RecentPlayBean) v.getTag());
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        rvList.setLayoutManager(linearLayoutManager);
        rvList.addItemDecoration(new LinearSpaceItemDecoration.Builder()
                .setFirstSpaceSize(DensityUtils.dip2px(context, 20))
                .setOrientation(RecyclerView.HORIZONTAL)
                .setSpaceSize(DensityUtils.dip2px(context, 12))
                .setLastSpace(DensityUtils.dip2px(context, 20))
                .build());

        RecentPlayAdapter mAdapter = new RecentPlayAdapter(context, data);
//        mAdapter.setOnItemClickListener(this::handleListItemClick);
        rvList.setAdapter(mAdapter);
    }

    public static class LinearSpaceItemDecoration extends RecyclerView.ItemDecoration {

        private static final int DEFAULT_HEIGHT = 10;
        private int mSpaceSize;
        private int mFirstSpaceSize;
        private int mOrientation;
        private int mLastSpaceSize;

        LinearSpaceItemDecoration(Builder builder) {
            mFirstSpaceSize = builder.firstSpaceSize;
            mSpaceSize = builder.spaceSize;
            mOrientation = builder.orientation;
            mLastSpaceSize = builder.lastSpaceSize;
        }

        @Override
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            RecyclerView.Adapter adapter = parent.getAdapter();
            if (adapter == null) {
                return;
            }
            int itemCount = adapter.getItemCount();
            int position = parent.getChildAdapterPosition(view);
            int spaceSize;
            if (position == itemCount - 1) {
                if (mLastSpaceSize > 0) {
                    spaceSize = mLastSpaceSize;
                } else {
                    spaceSize = 0;
                }
            } else {
                spaceSize = mSpaceSize;
            }
            final int left = position == 0 ? mFirstSpaceSize : 0;
            if (mOrientation == LinearLayoutManager.VERTICAL) {
                outRect.set(left, 0, 0, spaceSize);
            } else {
                outRect.set(left, 0, spaceSize, 0);
            }
        }

        public static class Builder {

            private int spaceSize = DEFAULT_HEIGHT;
            private int firstSpaceSize = 0;
            private int orientation = LinearLayoutManager.VERTICAL;
            private int lastSpaceSize = 0;

            public Builder setLastSpace(int lastSpaceSize) {
                this.lastSpaceSize = lastSpaceSize;
                return this;
            }

            public Builder setFirstSpaceSize(int firstSpaceSize) {
                this.firstSpaceSize = firstSpaceSize;
                return this;
            }

            public Builder setSpaceSize(int spaceSize) {
                this.spaceSize = spaceSize;
                return this;
            }

            public Builder setOrientation(@RecyclerView.Orientation int orientation) {
                this.orientation = orientation;
                return this;
            }

            public LinearSpaceItemDecoration build() {
                return new LinearSpaceItemDecoration(this);
            }
        }
    }


    private static class RecentPlayAdapter extends RecyclerView.Adapter<BaseRecentPlayViewHolder> {
        private final int VIEW_TYPE_BOOK = 11;
        private final int VIEW_TYPE_STORY = 12;

        private List<RecentPlayBean> mData = new ArrayList<>();

        private Context mContext;
        private OnListItemClickListener mOnItemClickListener;

        public RecentPlayAdapter(Context context, List<RecentPlayBean> list) {
            mContext = context;
            if (list != null) {
                mData = list;
            }
        }

        public void replaceAll(List<RecentPlayBean> list) {
            mData.clear();
            mData.addAll(list);
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public BaseRecentPlayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            BaseRecentPlayViewHolder holder;
            View itemView;
            if (viewType == VIEW_TYPE_BOOK) {
                itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_recent_play_book_item_view, parent, false);
                holder = new BookRecentPlayViewHolder(itemView);
            } else {
                itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_recent_play_story_item_view, parent, false);
                holder = new StoryRecentPlayViewHolder(itemView);
            }
            if (mOnItemClickListener != null) {
                holder.itemView.setOnClickListener(v -> {
                    int position = holder.getAdapterPosition();
                    if (mData != null && mData.size() > position) {
                        RecentPlayBean historyInfo = mData.get(position);
                        mOnItemClickListener.onListItemClick(position, historyInfo);
                    }
                });
            }
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull BaseRecentPlayViewHolder holder, int position) {
            if (mData.size() > position) {
                RecentPlayBean historyInfo = mData.get(position);
                if (historyInfo == null) {
                    return;
                }
                if (holder instanceof BookRecentPlayViewHolder && historyInfo.isBookType()) {
                    ((BookRecentPlayViewHolder) holder).bookItemView.update(historyInfo);
                } else if (holder instanceof StoryRecentPlayViewHolder && historyInfo.isStoryType()) {
                    ((StoryRecentPlayViewHolder) holder).storyItemView.update(historyInfo);
                }
            }
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }

        @Override
        public int getItemViewType(int position) {
            if (mData.size() > position) {
                if (mData.get(position).isBookType()) {
                    return VIEW_TYPE_BOOK;
                } else {
                    return VIEW_TYPE_STORY;
                }
            } else {
                return super.getItemViewType(position);
            }
        }

        public void setOnItemClickListener(OnListItemClickListener listener) {
            this.mOnItemClickListener = listener;
        }

        private interface OnListItemClickListener {
            void onListItemClick(int position, RecentPlayBean historyInfo);
        }
    }

    private static class BaseRecentPlayViewHolder extends RecyclerView.ViewHolder {
        public BaseRecentPlayViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    private static class BookRecentPlayViewHolder extends BaseRecentPlayViewHolder {
        HouseBookItemView bookItemView;

        public BookRecentPlayViewHolder(@NonNull View itemView) {
            super(itemView);
            bookItemView = itemView.findViewById(R.id.book_item_view);
        }
    }

    private static class StoryRecentPlayViewHolder extends BaseRecentPlayViewHolder {
        HouseStoryItemView storyItemView;

        public StoryRecentPlayViewHolder(@NonNull View itemView) {
            super(itemView);
            storyItemView = itemView.findViewById(R.id.story_item_view);
        }
    }

}
