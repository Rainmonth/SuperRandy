package com.rainmonth.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.rainmonth.R;
import com.rainmonth.base.ui.activity.BaseActivity;
import com.rainmonth.bean.UserInfo;
import com.rainmonth.library.base.BaseAppCompatActivity;
import com.rainmonth.library.eventbus.EventCenter;
import com.rainmonth.library.utils.NetworkUtils;
import com.rainmonth.presenter.IRegisterPresenter;
import com.rainmonth.presenter.impl.RegisterPresenterImpl;
import com.rainmonth.utils.ToastUtils;
import com.rainmonth.utils.http.UserLoginResponse;
import com.rainmonth.view.IRegisterView;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 注册页面
 */
public class RegisterActivity extends BaseActivity implements IRegisterView {
    @Bind(R.id.iv_user_avatar)
    ImageView ivUserAvatar;
    @Bind(R.id.et_phone)
    EditText etPhone;
    @Bind(R.id.et_user_name)
    EditText etUserName;
    @Bind(R.id.et_email)
    EditText etEmail;
    @Bind(R.id.et_psw)
    EditText etPsw;
    @Bind(R.id.tv_create_account)
    TextView tvCreateAccount;

    IRegisterPresenter mPresenter;

    @Override
    public void initToolbar() {
        mToolbar.setLogo(R.drawable.ic_launcher);
        mToolbar.setTitle("注册");
        mToolbar.setBackgroundResource(R.color.transparent);
    }

    @OnClick({R.id.iv_user_avatar, R.id.tv_create_account})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_user_avatar:
                break;
            case R.id.tv_create_account:
                UserInfo userInfo = new UserInfo();
                userInfo.setMobile(etPhone.getText().toString());
                userInfo.setUsername(etUserName.getText().toString());
                userInfo.setEmail(etEmail.getText().toString());
                userInfo.setPsw(etPsw.getText().toString());
                mPresenter.register(userInfo);
                break;
        }
    }

    @Override
    public void naveToAfterRegister(UserLoginResponse response) {
        if (response.getCode().equals("1")) {
            readyGo(MainActivity.class);
            finish();
        } else {
            ToastUtils.showLongToast(mContext, response.getMessage());
        }
    }

    @Override
    public void toast(String msg) {
        ToastUtils.showLongToast(mContext, msg);
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_register;
    }

    @Override
    protected void onEventComing(EventCenter eventCenter) {

    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    protected void initViewsAndEvents() {
        mPresenter = new RegisterPresenterImpl(this);
    }

    @Override
    protected void onNetworkConnected(NetworkUtils.NetType type) {

    }

    @Override
    protected void onNetworkDisConnected() {

    }

    @Override
    protected boolean isApplyStatusBarTranslucency() {
        return true;
    }

    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }

    @Override
    protected boolean toggleOverridePendingTransition() {
        return false;
    }

    @Override
    protected TransitionMode getOverridePendingTransitionMode() {
        return null;
    }
}
