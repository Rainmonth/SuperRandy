package com.rainmonth.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.rainmonth.R;
import com.rainmonth.base.mvp.BaseResponse;
import com.rainmonth.base.ui.activity.BaseActivity;
import com.rainmonth.library.eventbus.EventCenter;
import com.rainmonth.library.utils.NetworkUtils;
import com.rainmonth.library.widgets.ClearEditText;
import com.rainmonth.presenter.ILoginPresenter;
import com.rainmonth.presenter.LoginPresenter;
import com.rainmonth.utils.ToastUtils;
import com.rainmonth.view.ILoginView;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 登录页面
 */
public class LoginActivity extends BaseActivity implements ILoginView {

    @Bind(R.id.iv_user_avatar)
    ImageView ivUserAvatar;
    @Bind(R.id.et_user_name)
    ClearEditText etUserName;
    @Bind(R.id.et_psw)
    ClearEditText etPsw;
    @Bind(R.id.tv_login)
    TextView tvLogin;
    @Bind(R.id.tv_no_account)
    TextView tvNoAccount;
    @Bind(R.id.iv_qq)
    ImageView ivQq;
    @Bind(R.id.iv_sina)
    ImageView ivSina;

    private ILoginPresenter loginPresenter;

    @Override
    public void initToolbar() {
        mToolbar.setLogo(R.drawable.ic_action_bar_logo);
        mToolbar.setTitle("登录");
//        mToolbar.setBackgroundResource(R.color.transparent);
    }

    @OnClick({R.id.iv_user_avatar, R.id.tv_login, R.id.tv_no_account, R.id.iv_qq, R.id.iv_sina})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_user_avatar:

                break;
            case R.id.tv_login:
                loginPresenter.login(etUserName.getText().toString(), etPsw.getText().toString());
                break;
            case R.id.tv_no_account:
                readyGo(TestActivity.class);
                break;
            case R.id.iv_qq:

                break;
            case R.id.iv_sina:

                break;
        }
    }

    // implements from ILoginView
    @Override
    public void naveToAfterLogin(BaseResponse response) {
        if (response.getCode().equals("1")) {
            readyGo(MainActivity.class);
            ToastUtils.showLongToast(mContext, response.getMessage());
            finish();
        } else {
            ToastUtils.showLongToast(mContext, response.getMessage());
        }
    }

    // implements from BaseView
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
        return R.layout.activity_login;
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
        // todo to be delete
        etUserName.setText("randy");
        etPsw.setText("123456");
        loginPresenter = new LoginPresenter(this);
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
