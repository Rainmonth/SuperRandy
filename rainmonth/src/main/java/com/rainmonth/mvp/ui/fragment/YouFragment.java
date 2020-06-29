package com.rainmonth.mvp.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rainmonth.R;
import com.rainmonth.common.di.component.AppComponent;
import com.rainmonth.mvp.ui.activity.LoginActivity;
import com.rainmonth.mvp.ui.activity.RegisterActivity;
import com.rainmonth.mvp.ui.activity.WelcomeActivity;
import com.rainmonth.common.base.BaseLazyFragment;
import com.rainmonth.common.eventbus.EventCenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by RandyZhang on 16/6/30.
 */
public class YouFragment extends BaseLazyFragment implements View.OnClickListener {
    @BindView(R.id.iv_user_avatar)
    ImageView ivUserAvatar;
    @BindView(R.id.tv_user_latest_state)
    TextView tvUserLatestState;
    @BindView(R.id.ll_user_info_container)
    LinearLayout llUserInfoContainer;
    @BindView(R.id.btn_register)
    Button btnRegister;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.btn_welcome)
    Button btnWelcome;

    @Override
    public void onFirstUserVisible() {

    }

    @Override
    public int getContentViewLayoutID() {
        return R.layout.fragment_you;
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
    protected void setupFragmentComponent(AppComponent appComponent) {

    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    protected void initViewsAndEvents(View view) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);



        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


    @OnClick({R.id.btn_register, R.id.btn_login, R.id.btn_welcome})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_register:
                readyGo(RegisterActivity.class);
                break;
            case R.id.btn_login:
                readyGo(LoginActivity.class);
                break;
            case R.id.btn_welcome:
                readyGo(WelcomeActivity.class);
                break;
        }
    }
}
