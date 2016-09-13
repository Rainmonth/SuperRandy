package com.rainmonth.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rainmonth.R;
import com.rainmonth.activity.LoginActivity;
import com.rainmonth.activity.RegisterActivity;
import com.rainmonth.activity.WelcomeActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by RandyZhang on 16/6/30.
 */
public class YouFragment extends BaseLazyFragment implements View.OnClickListener {
    @Bind(R.id.iv_user_avatar)
    ImageView ivUserAvatar;
    @Bind(R.id.tv_user_latest_state)
    TextView tvUserLatestState;
    @Bind(R.id.ll_user_info_container)
    LinearLayout llUserInfoContainer;
    @Bind(R.id.btn_register)
    Button btnRegister;
    @Bind(R.id.btn_login)
    Button btnLogin;
    @Bind(R.id.btn_welcome)
    Button btnWelcome;

    @Override
    public void onFirstUserVisible() {

    }

    @Override
    public int getContentViewLayoutID() {
        return R.layout.fragment_you;
    }

    @Override
    public void onUserVisible() {

    }

    @Override
    public void onUserInvisible() {

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
        ButterKnife.unbind(this);
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
