package com.rainmonth.activity;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.rainmonth.R;

import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 欢迎页面
 */
public class WelcomeActivity extends BaseActivity {

    @Bind(R.id.vp_welcome)
    ViewPager vpWelcome;
    @Bind(R.id.btn_login)
    Button btnLogin;
    @Bind(R.id.btn_register)
    Button btnRegister;
    @Bind(R.id.ll_action_container)
    LinearLayout llActionContainer;
    Integer[] channels = new Integer[]{R.drawable.bg_welcome0, R.drawable.bg_welcome1, R.drawable.bg_welcome2,
            R.drawable.bg_welcome3, R.drawable.bg_welcome4};
    private List<Integer> mDataList = Arrays.asList(channels);

    private PagerAdapter mAdapter = new PagerAdapter() {

        @Override
        public int getCount() {
            return mDataList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = new ImageView(container.getContext());
            imageView.setImageResource(mDataList.get(position));
            imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    };

    @Override
    public boolean isApplyTranslucentStatusBar() {
        return false;
    }

    @Override
    public int getContentViewLayoutId() {
        return R.layout.activity_welcome;
    }

    @Override
    public void initViewsAndEvent() {
        vpWelcome.setAdapter(mAdapter);
    }

    @OnClick({R.id.btn_login, R.id.btn_register})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                readyGo(LoginActivity.class);
                break;
            case R.id.btn_register:
                readyGo(RegisterActivity.class);
                break;
        }
    }
}
