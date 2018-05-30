package com.rainmonth.mvp.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.rainmonth.R;
import com.rainmonth.common.base.mvp.BaseResponse;
import com.rainmonth.common.base.BaseActivity;
import com.rainmonth.common.di.component.AppComponent;
import com.rainmonth.common.eventbus.EventCenter;
import com.rainmonth.common.utils.NetworkUtils;
import com.rainmonth.common.widgets.ClearEditText;
import com.rainmonth.mvp.contract.LoginContract;
import com.rainmonth.mvp.presenter.LoginPresenter;
import com.rainmonth.common.utils.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 登录页面
 */
public class LoginActivity extends BaseActivity implements LoginContract.View {

    @BindView(R.id.iv_user_avatar)
    ImageView ivUserAvatar;
    @BindView(R.id.et_user_name)
    ClearEditText etUserName;
    @BindView(R.id.et_psw)
    ClearEditText etPsw;
    @BindView(R.id.tv_login)
    TextView tvLogin;
    @BindView(R.id.tv_no_account)
    TextView tvNoAccount;
    @BindView(R.id.iv_qq)
    ImageView ivQq;
    @BindView(R.id.iv_sina)
    ImageView ivSina;

    private LoginPresenter loginPresenter;

    @Override
    public void initToolbar() {
        mToolbar.setLogo(R.drawable.ic_action_bar_logo);
        mToolbar.setTitle("登录");
//        mToolbar.setBackgroundResource(R.color.transparent);
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @OnClick({R.id.iv_user_avatar, R.id.tv_login, R.id.tv_no_account, R.id.iv_qq, R.id.iv_sina})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_user_avatar:

                break;
            case R.id.tv_login:
//                loginPresenter.login(etUserName.getText().toString(), etPsw.getText().toString());
                break;
            case R.id.tv_no_account:
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

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_login;
    }

    @Override
    protected void initViewsAndEvents() {
        // todo to be delete
        etUserName.setText("randy");
        etPsw.setText("123456");
//        loginPresenter = new LoginPresenter(this);
    }
}
