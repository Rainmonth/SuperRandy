package com.rainmonth.fragment;

import android.animation.Animator;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rainmonth.R;
import com.rainmonth.adapter.CardPagerAdapter;
import com.rainmonth.bean.CardInfo;
import com.rainmonth.utils.AnimatorUtils;
import com.rainmonth.utils.HexUtils;
import com.rainmonth.utils.NetWorkHelper;
import com.rainmonth.widgets.IRhythmItemListener;
import com.rainmonth.widgets.ProgressHUD;
import com.rainmonth.widgets.PullToRefreshViewPager;
import com.rainmonth.widgets.RhythmAdapter;
import com.rainmonth.widgets.RhythmLayout;
import com.rainmonth.widgets.ViewPagerScroller;
import com.rainmonth.widgets.library.PullToRefreshBase;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * User: shine
 * Date: 2014-12-13
 * Time: 19:45
 * Description:
 */
public class CardViewPagerFragment extends BaseLazyFragment implements PullToRefreshBase.OnRefreshListener<ViewPager> {

    private TextView mTimeFirstText;
    private TextView mTimeSecondText;

    /**
     * 最外层的View，为了设置背景颜色而使用
     */
    private View mMainView;
    private ImageButton mRocketToHeadBtn;
    private Button mSideMenuOrBackBtn;
    /**
     * 钢琴布局
     */
    private RhythmLayout mRhythmLayout;
    /**
     * 可以侧拉刷新的ViewPager，其实是一个LinearLayout控件
     */
    private PullToRefreshViewPager mPullToRefreshViewPager;
    /**
     * 接收PullToRefreshViewPager中的ViewPager控件
     */
    private ViewPager mViewPager;
    /**
     * ViewPager的适配器
     */
    private CardPagerAdapter mCardPagerAdapter;
    /**
     * 记录上一个选项卡的颜色值
     */
    private int mPreColor;

    private boolean mHasNext = true;

    private boolean mIsRequesting;

    private boolean isAdapterUpdated;

    private int mCurrentViewPagerPage;


    private List<CardInfo> mCardInfoList;

    private ProgressHUD mProgressHUD;

    /**
     * 钢琴布局的适配器
     */
    private RhythmAdapter mRhythmAdapter;

    private static CardViewPagerFragment mFragment;

    /**
     * 自定义钢琴控件的监听器
     */
    private IRhythmItemListener rhythmItemListener = new IRhythmItemListener() {
        public void onRhythmItemChanged(int paramInt) {
        }

        public void onSelected(final int paramInt) {
            CardViewPagerFragment.this.mHandler.postDelayed(new Runnable() {
                public void run() {
                    mViewPager.setCurrentItem(paramInt);
                }
            }, 100L);
        }

        public void onStartSwipe() {
        }
    };

    private ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        public void onPageScrollStateChanged(int paramInt) {
        }

        public void onPageScrolled(int paramInt1, float paramFloat, int paramInt2) {
        }

        public void onPageSelected(int position) {
            onAppPagerChange(position);
            if (mHasNext && (position > -10 + mCardInfoList.size()) && !mIsRequesting && NetWorkHelper.isWifiDataEnable(getActivity())) {
                fetchData();
            }
        }
    };

    public static CardViewPagerFragment getInstance() {
        if (mFragment == null) {
            mFragment = new CardViewPagerFragment();
        }
        return mFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        initViews(rootView);
        initActions();
        initData();
        return rootView;
    }

    protected View initViews(View view) {
        //初始化控件
        mTimeFirstText = (TextView) view.findViewById(R.id.text_time_first);
        mTimeSecondText = (TextView) view.findViewById(R.id.text_time_second);
        mMainView = view.findViewById(R.id.main_view);
        mRocketToHeadBtn = (ImageButton) view.findViewById(R.id.btn_rocket_to_head);
        mSideMenuOrBackBtn = (Button) view.findViewById(R.id.btn_side_menu_or_back);
        mRhythmLayout = (RhythmLayout) view.findViewById(R.id.box_rhythm);
        mPullToRefreshViewPager = (PullToRefreshViewPager) view.findViewById(R.id.pager);
        mViewPager = mPullToRefreshViewPager.getRefreshableView();
        //设置ViewPager的滚动速度
        setViewPagerScrollSpeed(mViewPager, 400);
        //设置ScrollView滚动动画延迟执行的时间
        mRhythmLayout.setScrollRhythmStartDelayTime(400);
        //设置钢琴布局的高度 高度为钢琴布局item的宽度+10dp
        int height = (int) mRhythmLayout.getRhythmItemWidth() + (int) TypedValue.applyDimension(1, 10.0F, getResources().getDisplayMetrics());
        mRhythmLayout.getLayoutParams().height = height;
        ((RelativeLayout.LayoutParams) mPullToRefreshViewPager.getLayoutParams()).bottomMargin = height;

        mTimeSecondText.setText("12月\n星期六");

        return view;
    }


    protected void initActions() {
        //设置控件的监听
        mRhythmLayout.setRhythmListener(rhythmItemListener);
        mPullToRefreshViewPager.setOnRefreshListener(this);
        mViewPager.setOnPageChangeListener(onPageChangeListener);
        mRocketToHeadBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramView) {
                CardViewPagerFragment.this.mViewPager.setCurrentItem(0, true);
            }
        });

    }

    protected void initData() {
        mCardInfoList = new ArrayList<>();
    }

    /**
     * 设置ViewPager的滚动速度，即每个选项卡的切换速度
     *
     * @param viewPager ViewPager控件
     * @param speed     滚动速度，毫秒为单位
     */
    private void setViewPagerScrollSpeed(ViewPager viewPager, int speed) {
        try {
            Field field = ViewPager.class.getDeclaredField("mScroller");
            field.setAccessible(true);
            ViewPagerScroller viewPagerScroller = new ViewPagerScroller(viewPager.getContext(), new OvershootInterpolator(0.6F));
            field.set(viewPager, viewPagerScroller);
            viewPagerScroller.setDuration(speed);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 改变当前选中钢琴按钮
     *
     * @param position viewPager的位置
     */
    private void onAppPagerChange(int position) {
        //执行动画，改变升起的钢琴按钮
        mRhythmLayout.showRhythmAtPosition(position);
        toggleRocketBtn(position);
        CardInfo post = this.mCardInfoList.get(position);
        //得到当前的背景颜色
        int currColor = HexUtils.getHexColor(post.getBackgroundColor());
        //执行颜色转换动画
        AnimatorUtils.showBackgroundColorAnimation(this.mMainView, mPreColor, currColor, 400);
        mPreColor = currColor;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        fetchData();
        onAppPagerChange(0);
    }

    @Override
    public void onFirstUserVisible() {

    }

    @Override
    public int getContentViewLayoutID() {
        return R.layout.fragment_niceapp;
    }

    @Override
    public void onUserVisible() {

    }

    @Override
    public void onUserInvisible() {

    }

    /**
     * 加载数据
     */
    private void fetchData() {
        ArrayList<CardInfo> cardInfoList = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            int m = i % 8;
            CardInfo cardInfo = addData(m);
            cardInfoList.add(cardInfo);
        }
        mPreColor = HexUtils.getHexColor(cardInfoList.get(0).getBackgroundColor());
        updateAppAdapter(cardInfoList);
    }


    private void updateAppAdapter(List<CardInfo> cardInfoList) {
        if ((getActivity() == null) || (getActivity().isFinishing())) {
            return;
        }
        if (mProgressHUD != null && mProgressHUD.isShowing()) {
            this.mProgressHUD.dismiss();
            this.isAdapterUpdated = true;
        }
        if (cardInfoList.isEmpty()) {
            this.mMainView.setBackgroundColor(this.mPreColor);
            return;
        }
        int size = mCardInfoList.size();

        if (mCardPagerAdapter == null) {
            mCurrentViewPagerPage = 0;
            mCardPagerAdapter = new CardPagerAdapter(getActivity().getSupportFragmentManager(), cardInfoList);
            mViewPager.setAdapter(mCardPagerAdapter);
        } else {
            mCardPagerAdapter.addCardList(cardInfoList);
            mCardPagerAdapter.notifyDataSetChanged();
        }
        addCardIconsToDock(cardInfoList);

        this.mCardInfoList = mCardPagerAdapter.getCardList();

        if (mViewPager.getCurrentItem() == size - 1)
            mViewPager.setCurrentItem(1 + mViewPager.getCurrentItem(), true);
    }

    private void addCardIconsToDock(final List<CardInfo> cardInfoList) {
        if (mRhythmAdapter == null) {
            resetRhythmLayout(cardInfoList);
            return;
        }
        mRhythmAdapter.addCardList(cardInfoList);
        mRhythmAdapter.notifyDataSetChanged();
    }

    //重置钢琴控件数据源
    private void resetRhythmLayout(List<CardInfo> cardInfoList) {
        if (getActivity() == null)
            return;
        if (cardInfoList == null)
            cardInfoList = new ArrayList<>();
        mRhythmAdapter = new RhythmAdapter(getActivity(), mRhythmLayout, cardInfoList);
        mRhythmLayout.setAdapter(mRhythmAdapter);
    }

    /**
     * viewPager刷新或加载更多监听
     *
     * @param pullToRefreshBase
     */
    public void onRefresh(PullToRefreshBase<ViewPager> pullToRefreshBase) {
        if (this.mIsRequesting)
            return;
        if (pullToRefreshBase.getCurrentMode() == PullToRefreshBase.Mode.PULL_FROM_END) {//最右
            mIsRequesting = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    fetchData();
                    mPullToRefreshViewPager.onRefreshComplete();
                    mIsRequesting = false;
                }
            }, 2000);

        } else if (pullToRefreshBase.getCurrentMode() == PullToRefreshBase.Mode.PULL_FROM_START) {//最左
            mIsRequesting = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mPullToRefreshViewPager.onRefreshComplete();
                    mIsRequesting = false;
                }
            }, 2000);
        }
    }

    /**
     * 根据当前viewPager的位置决定右上方的火箭图案是否显示
     *
     * @param position
     */
    private void toggleRocketBtn(int position) {
        if (position > 1) {
            if (mRocketToHeadBtn.getVisibility() == View.GONE) {
                mRocketToHeadBtn.setVisibility(View.VISIBLE);
                AnimatorUtils.animViewFadeIn(this.mRocketToHeadBtn);
            }
        } else if (this.mRocketToHeadBtn.getVisibility() == View.VISIBLE) {
            AnimatorUtils.animViewFadeOut(this.mRocketToHeadBtn).addListener(new Animator.AnimatorListener() {
                public void onAnimationCancel(Animator paramAnimator) {
                }

                public void onAnimationEnd(Animator paramAnimator) {
                    CardViewPagerFragment.this.mRocketToHeadBtn.setVisibility(View.GONE);
                }

                public void onAnimationRepeat(Animator paramAnimator) {
                }

                public void onAnimationStart(Animator paramAnimator) {
                }
            });
        }
        mTimeFirstText.setText((position + 1) + "");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mFragment = null;
    }

    private CardInfo addData(int i) {
        CardInfo cardInfo = new CardInfo();
        switch (i) {
            case 0:
                cardInfo.setTitle("God of Light");
                cardInfo.setSubTitle("点亮世界之光");
                cardInfo.setDigest("当下制造精致的游戏往往超越了常规概念中对游戏的界定。通关的过程更像是在欣赏一部电影大片。通过镜片反射，即使只有一道光芒，我们也能点亮世界");
                cardInfo.setUpNum(124);
                cardInfo.setAuthorName("小美");
                cardInfo.setBackgroundColor("#00aac6");
                cardInfo.setCoverImageUrl("card_cover1");
                cardInfo.setIconUrl("card_icon1");
                break;
            case 1:
                cardInfo.setTitle("我的手机与众不同");
                cardInfo.setSubTitle("专题");
                cardInfo.setDigest("谁说美化一定要Root?选对了应用一样可以美美哒～有个性，爱折腾，我们不爱啃苹果，我们是大安卓用户!都说「世界上没有相同的叶子」，想让自己的手机与众不同?让小美告诉你");
                cardInfo.setUpNum(299);
                cardInfo.setAuthorName("小美");
                cardInfo.setBackgroundColor("#dc4e97");
                cardInfo.setCoverImageUrl("card_cover2");
                cardInfo.setIconUrl("card_icon2");
                break;
            case 2:
                cardInfo.setTitle("BlackLight");
                cardInfo.setSubTitle("做最纯粹的微博客户端");
                cardInfo.setDigest("Android的官方微博客户端显得太过臃肿，这让不少人转而投向第三方客户端。「Fuubo」、「四次元」、「Smooth」，一个个耳熟能详的名字，它们各有千秋，也吸引了一大票追随者，而今天推荐的BlackLight，又是一个被重复造出的「轮子」，然而这个后来者可不一般");
                cardInfo.setUpNum(241);
                cardInfo.setAuthorName("小最");
                cardInfo.setBackgroundColor("#00aac6");
                cardInfo.setCoverImageUrl("card_cover3");
                cardInfo.setIconUrl("card_icon3");
                break;
            case 3:
                cardInfo.setTitle("BuzzFeed");
                cardInfo.setSubTitle("最好玩的新闻在这里");
                cardInfo.setDigest("BuzzFeed是一款聚合新闻阅读应用，这款应用来自美国用户增长流量最快，内容最能吸引大众眼球的互联网新闻网站，当然我们不必知道BuzzFeed的创始人多么流弊，BuzzFeed本身是多么具有颠覆性，我们只需要知道这款应用的内容绝对有料，而去也是十分精致，简洁");
                cardInfo.setUpNum(119);
                cardInfo.setAuthorName("小最");
                cardInfo.setBackgroundColor("#e76153");
                cardInfo.setCoverImageUrl("card_cover4");
                cardInfo.setIconUrl("card_icon4");
                break;
            case 4:
                cardInfo.setTitle("Nester");
                cardInfo.setSubTitle("专治各种熊孩子");
                cardInfo.setDigest("Nester简单的说是一款用于家长限制孩子玩手机的应用，这只可爱的圆滚滚的小鸟不仅可以设置孩子可以使用的应用，还可以用定时器控释孩子玩手机的时长。在小最看来，Nester最直白的描述就是专治各种熊孩子");
                cardInfo.setUpNum(97);
                cardInfo.setAuthorName("小最");
                cardInfo.setBackgroundColor("#9a6dbb");
                cardInfo.setCoverImageUrl("card_cover5");
                cardInfo.setIconUrl("card_icon5");
                break;
            case 5:
                cardInfo.setTitle("二次元专题");
                cardInfo.setSubTitle("啊喂，别总想去四维空间啦");
                cardInfo.setDigest("为了满足美友中不少二次元少年的需求，小最前几日特(bei)意(po)被拍扁为二维状，去那个神奇的世界走了一遭。在被深深震撼之后，为大家带来本次「二次元专题」");
                cardInfo.setUpNum(317);
                cardInfo.setAuthorName("小最");
                cardInfo.setBackgroundColor("#51aa53");
                cardInfo.setCoverImageUrl("card_cover6");
                cardInfo.setIconUrl("card_icon6");
                break;
            case 6:
                cardInfo.setTitle("Music Player");
                cardInfo.setSubTitle("闻其名，余音绕梁");
                cardInfo.setDigest("一款App，纯粹到极致，便是回到原点「Music Player」，一款音乐播放器，一个干净到显得敷衍的名字。它所打动的，是哪些需要音乐，才可以慰借心灵的人。");
                cardInfo.setUpNum(385);
                cardInfo.setAuthorName("小最");
                cardInfo.setBackgroundColor("#ea5272");
                cardInfo.setCoverImageUrl("card_cover7");
                cardInfo.setIconUrl("card_icon7");
                break;
            case 7:
                cardInfo.setTitle("el");
                cardInfo.setSubTitle("剪纸人の唯美旅程");
                cardInfo.setDigest("断崖之上，孤牢中醒来的他，意外地得到一把能乘风翱翔的伞，于是在悠扬的钢琴曲中，剪纸人开始了漫无目的的漂泊之旅。脚下的重峦叠嶂，飞行中遇到的种种障碍，不日又遇到了他，将会有一段怎样的旅程?");
                cardInfo.setUpNum(622);
                cardInfo.setAuthorName("小美");
                cardInfo.setBackgroundColor("#e76153");
                cardInfo.setCoverImageUrl("card_cover8");
                cardInfo.setIconUrl("card_icon8");
                break;
        }
        return cardInfo;
    }
}
