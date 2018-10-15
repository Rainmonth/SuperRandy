package com.hhdd.kada.main.views;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.hhdd.core.db.DatabaseManager;
import com.hhdd.kada.Constants;
import com.hhdd.kada.KaDaApplication;
import com.hhdd.kada.R;
import com.hhdd.kada.android.library.utils.LocalDisplay;
import com.hhdd.kada.main.listener.OnChildViewClickListener;
import com.hhdd.kada.main.model.BookCollectionDetailInfo;
import com.hhdd.kada.main.model.BookInfo;
import com.hhdd.kada.main.settings.Settings;
import com.hhdd.kada.main.utils.Extflag;
import com.hhdd.kada.main.utils.FrescoUtils;
import com.hhdd.kada.main.utils.SafeHandler;
import com.hhdd.kada.main.utils.ScreenUtil;
import com.hhdd.kada.widget.BookCollectionSubscribeView;
import com.hhdd.kada.widget.support.KdGridLayoutManager;
import com.hhdd.kada.widget.support.KdStaggeredGridLayoutManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lj on 15/7/12.
 */
public class LastPageLayout extends RelativeLayout {

    private int second = 5;   //倒计时几秒

    private int[] images = {
            R.drawable.continue_play_1,
            R.drawable.continue_play_2,
            R.drawable.continue_play_3,
            R.drawable.continue_play_4,
            R.drawable.continue_play_5};


    private int mItemWidth;  //推荐列表item的宽度
    private int mItemHeight;  //推荐列表item的高度
    private LayoutParams bottomLayoutParams;
    private ImageView mWellDoneIv;
    private View mHeadOptionLayout;
    private View mPlayNextBookView;
    private BookCollectionSubscribeView subscribeView;

    private ScaleAnimation scaleAnimation;

    private boolean isShowIntroSubscribeLayout = false;
    private View shareBtn;
    private ImageView ivContinuePlay;

    public LastPageLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    FrameLayout bottomContainer;
    RecyclerView mRecyclerView;
    HomeAdapter mAdapter;

    private SafeHandler countDownHandler;
    private SafeHandler mFadeIntroSubscribeLayoutHandler;


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        bottomContainer = (FrameLayout) findViewById(R.id.bottom_container);

        mWellDoneIv = (ImageView) findViewById(R.id.iv_last_page_head_welldone);
        mWellDoneIv.setImageResource(R.drawable.welldone);

        shareBtn = findViewById(R.id.share);
        mHeadOptionLayout = findViewById(R.id.last_page_head_option_layout);
        subscribeView = (BookCollectionSubscribeView) findViewById(R.id.subscribeView_last_page);

        mPlayNextBookView = findViewById(R.id.ll_read_next_last_page_layout);

        ivContinuePlay = (ImageView) findViewById(R.id.iv_continue_play);
        initializeIvContinueStatus();

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        mRecyclerView.setAdapter(mAdapter = new HomeAdapter());

        mFadeIntroSubscribeLayoutHandler = new SafeHandler();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
//        mRecyclerView.getItemAnimator().setChangeDuration(0);// 通过设置动画执行时间为0来解决recyclerView刷新闪烁问题
        ((SimpleItemAnimator) mRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
    }

    public void showSubscribeView() {
        subscribeView.setVisibility(View.VISIBLE);
        mFadeIntroSubscribeLayoutHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                subscribeView.setVisibility(View.GONE);
            }
        }, 5000);
    }

    public void setSubscribeViewListener(OnChildViewClickListener listener) {
        if (subscribeView == null) {
            return;
        }
        subscribeView.setOnChildViewClickListener(listener);
    }

    public void updateSubscribeView(int extflag, double price, double originalPrice) {
        if (subscribeView == null) {
            return;
        }
        subscribeView.update(extflag, 0, price, originalPrice);
    }

    public void notifyDataSetChanged() {
        if(null != mAdapter) {
            mAdapter.notifyDataSetChanged();
        }
    }


    /**
     * 开始倒计时
     */
    public void startCountDown() {
        if (countDownHandler == null) {
            countDownHandler = new SafeHandler();
        }
        if (currentPlayPosition + 1 < recommendBookVOs.size() && runnable != null) {
            BookInfo bookInfo = recommendBookVOs.get(currentPlayPosition + 1);
            if ((mDetailInfo != null && mDetailInfo.getSubscribe() == 1) || (bookInfo.getExtFlag() & Extflag.EXT_FLAG_8192) == Extflag.EXT_FLAG_8192 || bookInfo.getCollectId() == 0) {
                //选中自动连播后，推荐内容滚动到倒计时内容
                mRecyclerView.smoothScrollToPosition(currentPlayPosition + 1);
                second = 5;
                countDownHandler.post(runnable);
            }
        } else if (currentPlayPosition + 1 == recommendBookVOs.size()) {
            mRecyclerView.smoothScrollToPosition(currentPlayPosition);
        }
    }

    /**
     * 停止倒计时
     */
    public void stopCountDown() {
        if (countDownHandler != null && runnable != null) {
            countDownHandler.removeCallbacks(runnable);
            second = 5;
            mAdapter.notifyItemChanged(currentPlayPosition + 1);
        }
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (second > 0) {
                mAdapter.notifyItemChanged(currentPlayPosition + 1);
                countDownHandler.postDelayed(runnable, 1000);
                second--;
            } else {
                listener.onCountdownEnd(getNextRecommendBook());
            }
        }
    };

    /**
     * 是否播放到推荐的最后一本
     *
     * @return
     */
    public boolean isLastRecommendBook() {
        return recommendBookVOs != null && (currentPlayPosition + 1) == recommendBookVOs.size();
    }

    public BookInfo getNextRecommendBook() {

        if (currentPlayPosition + 1 < recommendBookVOs.size()) {
            return recommendBookVOs.get(currentPlayPosition + 1);
        }

        return null;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            if (newInRangeOfView(mRecyclerView, ev)) {
                getParent().requestDisallowInterceptTouchEvent(true);
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    private boolean newInRangeOfView(View view, MotionEvent ev) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        if (ev.getRawX() < location[0] || ev.getRawX() > (location[0] + view.getWidth()) || ev.getRawY() < location[1] || ev.getRawY() > (location[1] + view.getHeight())) {
            return false;
        }
        return true;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (scaleAnimation != null) {
            scaleAnimation.cancel();
            scaleAnimation = null;
        }
        if (countDownHandler != null) {
            countDownHandler.destroy();
            countDownHandler = null;
        }
    }

    public void setLayoutWithConfiguration() {
        resetRelevantParams(isShowIntroSubscribeLayout);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            if (showList) {
                bottomContainer.setVisibility(VISIBLE);
                mRecyclerView.setLayoutManager(new KdStaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL));
                mAdapter.notifyDataSetChanged();
            }

        } else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            if (showList) {
                bottomContainer.setVisibility(VISIBLE);
                GridLayoutManager myGridLayoutManager = new KdGridLayoutManager(getContext(), 3, GridLayoutManager.VERTICAL, false);
                mRecyclerView.setLayoutManager(myGridLayoutManager);
                mAdapter.notifyDataSetChanged();
            }
        }
        if (recommendBookVOs != null && recommendBookVOs.size() > 0 && currentPlayPosition >= recommendBookVOs.size()) {
            currentPlayPosition = recommendBookVOs.size() - 1;
        }
        mRecyclerView.scrollToPosition(currentPlayPosition + 1);
    }

    /**
     * 计算和设置相关的布局参数
     */
    private void resetRelevantParams(boolean isShowIntroSubscribeLayout) {
        bottomLayoutParams = (LayoutParams) bottomContainer.getLayoutParams();
        FrameLayout.LayoutParams shareParam = (FrameLayout.LayoutParams) shareBtn.getLayoutParams();
        FrameLayout.LayoutParams welldownParam = (FrameLayout.LayoutParams) mWellDoneIv.getLayoutParams();

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            //计算推荐列表item的宽高
            mItemHeight = ScreenUtil.getDimensionPixelOffset(R.dimen.recommend_book_item_height);
            mItemWidth = (int) (mItemHeight * Constants.BOOK_COVER_RATIO);

            int paddingBottom = 0;
            if (isShowIntroSubscribeLayout) {
                paddingBottom = LocalDisplay.dp2px(50);
            }

            //设置推荐列表的高度
            bottomLayoutParams.height = mItemHeight + ScreenUtil.getDimensionPixelOffset(R.dimen.recommend_list_margin) + paddingBottom;

            welldownParam.topMargin = LocalDisplay.dp2px(40);

            FrameLayout.LayoutParams headOptionLayoutParams = (FrameLayout.LayoutParams) mHeadOptionLayout.getLayoutParams();
            headOptionLayoutParams.topMargin = LocalDisplay.dp2px(100);
            mHeadOptionLayout.setLayoutParams(headOptionLayoutParams);

            mRecyclerView.setPadding(0, 0, 0, 0);

            shareParam.topMargin = 0;

        } else {
            //计算推荐列表item的宽高
            mItemWidth = ScreenUtil.getScreenWidth() / 3;
            mItemHeight = (int) (mItemWidth / Constants.BOOK_COVER_RATIO);

            //设置推荐列表的高度
            bottomLayoutParams.height = mItemHeight * 2 + ScreenUtil.getDimensionPixelOffset(R.dimen.recommend_list_margin);

            welldownParam.topMargin = LocalDisplay.SCREEN_HEIGHT_PIXELS / 7;

            FrameLayout.LayoutParams headOptionLayoutParams = (FrameLayout.LayoutParams) mHeadOptionLayout.getLayoutParams();
            headOptionLayoutParams.topMargin = LocalDisplay.SCREEN_HEIGHT_PIXELS / 4;
            mHeadOptionLayout.setLayoutParams(headOptionLayoutParams);

            if (isShowIntroSubscribeLayout) {
                mRecyclerView.setPadding(0, 0, 0, LocalDisplay.dp2px(50));
            } else {
                mRecyclerView.setPadding(0, 0, 0, 0);
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                shareParam.topMargin = LocalDisplay.SCREEN_STATUS_HEIGHT;
            }
        }

        bottomContainer.setLayoutParams(bottomLayoutParams);
        mRecyclerView.getLayoutParams().height = bottomLayoutParams.height - LocalDisplay.dp2px(5);
    }

    private BookCollectionDetailInfo mDetailInfo;
    private int currentPlayPosition;

    public void setDetailInfo(BookCollectionDetailInfo detailInfo) {
        mDetailInfo = detailInfo;
    }

    boolean showList;
    List<BookInfo> recommendBookVOs = new ArrayList<>();

    public void setRecommendBookList(List<BookInfo> recommendBookList) {
        recommendBookVOs.clear();
        if (recommendBookList != null && recommendBookList.size() > 0) {
            showList = true;
            mRecyclerView.setVisibility(VISIBLE);
            recommendBookVOs.addAll(recommendBookList);

            mPlayNextBookView.setVisibility(VISIBLE);
        } else {
            showList = false;
            mRecyclerView.setVisibility(INVISIBLE);

            mPlayNextBookView.setVisibility(GONE);
        }
    }

    public void setCurrentPlayPosition(int position) {
        currentPlayPosition = position;
    }


    class HomeAdapter extends RecyclerView.Adapter<MyViewHolder> {

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View rootView = LayoutInflater.from(
                    getContext()).inflate(R.layout.view_holder_lastpager_item, parent,
                    false);
            MyViewHolder holder = new MyViewHolder(rootView);

            return holder;
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {
            holder.layout.getLayoutParams().width = mItemWidth;
            holder.layout.getLayoutParams().height = mItemHeight;

            BookInfo bookInfo = recommendBookVOs.get(position);
            int extFlag = bookInfo.getExtFlag();
            holder.countdown.clearAnimation();
            holder.guideNext.clearAnimation();
            if (Settings.getInstance().isBookContinuePlay()) {
                holder.guideNext.setVisibility(GONE);
                if ((mDetailInfo != null && mDetailInfo.getSubscribe() == 1) || (extFlag & Extflag.EXT_FLAG_8192) == Extflag.EXT_FLAG_8192 || bookInfo.getCollectId() == 0) {
                    if (position == currentPlayPosition + 1 && currentPlayPosition < recommendBookVOs.size()) {
                        if (second != 5) {
                            holder.countdown.setVisibility(VISIBLE);
                            holder.countdown.setImageResource(images[second]);
                            playCountdownAnimation(holder.countdown);
                        } else {
                            holder.countdown.setVisibility(GONE);
                        }
                    } else {
                        holder.countdown.setVisibility(GONE);
                    }
                    holder.lockView.setVisibility(GONE);
                } else {
                    holder.lockView.setVisibility(VISIBLE);
                    holder.countdown.setVisibility(GONE);
                }
            } else {
                holder.countdown.setVisibility(GONE);
                if ((mDetailInfo != null && mDetailInfo.getSubscribe() == 1) || (extFlag & Extflag.EXT_FLAG_8192) == Extflag.EXT_FLAG_8192 || bookInfo.getCollectId() == 0) {
                    holder.lockView.setVisibility(GONE);
                    if (mDetailInfo != null && mDetailInfo.getItems() != null) {
                        //手指提示
                        if (position == currentPlayPosition + 1 && position < mDetailInfo.getItems().size()) {
                            holder.guideNext.setVisibility(VISIBLE);
                            playGuideNextAnimation(holder.guideNext);
                        } else {
                            holder.guideNext.setVisibility(GONE);
                        }
                    }
                } else {
                    holder.lockView.setVisibility(VISIBLE);
                    holder.guideNext.setVisibility(GONE);
                }
            }

            //已读标志 加锁不显示已读标志
            if (holder.lockView.getVisibility() == GONE && DatabaseManager.getInstance().historyDB().exist(bookInfo.getBookId(), bookInfo.getCollectId())) {
                holder.hasReadFlag.setVisibility(VISIBLE);
            } else {
                holder.hasReadFlag.setVisibility(GONE);
            }

            if (bookInfo.isLimitFreeInCollection()) {
                holder.lockView.setVisibility(GONE);
            }

            String coverUrl = bookInfo.getCoverUrl();
            boolean needResetImageUrl = true;
            if (holder.cover.getTag(R.id.list_item_image_url) != null) {
                String url = (String) holder.cover.getTag(R.id.list_item_image_url);
                if (TextUtils.equals(url, coverUrl)) {
                    needResetImageUrl = false;
                }
            }
            if (needResetImageUrl) {
                holder.cover.setTag(R.id.list_item_image_url, coverUrl);
                FrescoUtils.showUrl(coverUrl, holder.cover, mItemWidth, mItemHeight);
            }

            holder.cover.setTag(R.id.cover2, position);
            holder.cover.setTag(R.id.cover, bookInfo);
            if (listener != null) {
                holder.cover.setOnClickListener(itemListener);
            }
        }

        @Override
        public int getItemCount() {
            if (recommendBookVOs != null && recommendBookVOs.size() > 0) {
                return recommendBookVOs.size();
            }
            return 0;
        }
    }


    static class MyViewHolder extends RecyclerView.ViewHolder {

        View layout;
        ScaleDraweeView cover;
        View lockView;
        View guideNext;
        View hasReadFlag;
        ImageView countdown;

        public MyViewHolder(View view) {
            super(view);
            cover = (ScaleDraweeView) view.findViewById(R.id.cover);
            layout = view.findViewById(R.id.layout);
            lockView = view.findViewById(R.id.lock_view_container);
            guideNext = view.findViewById(R.id.guide_next);
            hasReadFlag = view.findViewById(R.id.last_page_hasRead);
            countdown = (ImageView) view.findViewById(R.id.iv_countdown);
        }
    }

    KaDaApplication.OnClickWithAnimListener itemListener = new KaDaApplication.OnClickWithAnimListener() {

        @Override
        public void OnClickWithAnim(View v) {
            BookInfo bookInfo = (BookInfo) v.getTag(R.id.cover);
            int position = (int) v.getTag(R.id.cover2);
            if (recommendBookVOs != null && recommendBookVOs.size() > position) {
                listener.onRecommendBookItemClick(bookInfo, v, position);
            }
        }
    };

    private void initScaleAnim() {
        if (scaleAnimation == null) {
            scaleAnimation = new ScaleAnimation(1f, 0.8f, 1f, 0.8f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            scaleAnimation.setRepeatCount(100);
            scaleAnimation.setDuration(500);
            scaleAnimation.setRepeatMode(Animation.REVERSE);
        }
    }

    //提示下一本手指动画
    private void playGuideNextAnimation(View view) {
        initScaleAnim();
        view.startAnimation(scaleAnimation);
    }

    //自动连播倒计时动画
    private void playCountdownAnimation(final ImageView imageView) {
        initScaleAnim();
        imageView.startAnimation(scaleAnimation);
    }

    public void updateContinuePlayStatus(boolean isSelected) {
        if (scaleAnimation != null) {
            scaleAnimation.cancel();
        }
        if (isSelected) {
            startCountDown();
        } else {
            stopCountDown();
        }
    }

    public void initializeIvContinueStatus() {
        ivContinuePlay.setSelected(Settings.getInstance().isBookContinuePlay());
    }

    @Override
    protected void onVisibilityChanged(@NonNull View changedView, int visibility) {
        if (visibility == View.GONE) {
            stopCountDown();
        }
        super.onVisibilityChanged(changedView, visibility);
    }

    public interface Listener {
        //推荐列表点击事件监听
        void onRecommendBookItemClick(BookInfo bookInfo, View view, int index);

        //倒计时结束
        void onCountdownEnd(BookInfo bookInfo);
    }

    Listener listener;

    public Listener getListener() {
        return listener;
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

}
