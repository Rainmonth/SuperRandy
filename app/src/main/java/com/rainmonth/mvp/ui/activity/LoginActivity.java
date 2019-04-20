package com.rainmonth.mvp.ui.activity;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.rainmonth.R;
import com.rainmonth.common.base.BaseActivity;
import com.rainmonth.common.di.component.AppComponent;
import com.rainmonth.common.http.Result;
import com.rainmonth.common.utils.ToastUtils;
import com.rainmonth.common.widgets.ClearEditText;
import com.rainmonth.di.component.DaggerLoginComponent;
import com.rainmonth.di.module.LoginModule;
import com.rainmonth.mvp.contract.UserContract;
import com.rainmonth.mvp.presenter.UserPresenter;

import java.util.logging.Logger;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 登录页面
 */
public class LoginActivity extends BaseActivity<UserPresenter> implements UserContract.View {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
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

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_login;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerLoginComponent.builder()
                .appComponent(appComponent)
                .loginModule(new LoginModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected void initViewsAndEvents() {
        etUserName.setText("randy");
        etPsw.setText("randy123");
        etUserName.setSelection(etUserName.getText().toString().length());
        etPsw.setSelection(etPsw.getText().toString().length());
    }

    @Override
    public void initToolbar(int colorResId) {
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            mActionBar = getSupportActionBar();
            if (null != mActionBar) {
                mActionBar.setLogo(R.drawable.ic_action_bar_logo);
                mActionBar.setTitle("登录");
            }
        }
    }

    @OnClick({R.id.iv_user_avatar, R.id.tv_login, R.id.tv_no_account, R.id.iv_qq, R.id.iv_sina})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_user_avatar:

                break;
            case R.id.tv_login:
                mPresenter.login(etUserName.getText().toString(), etPsw.getText().toString());
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
    public void naveToAfterLogin(Result response) {
        if (response.getCode() == 1) {
            readyGo(MainActivity.class);

            ToastUtils.showLongToast(mContext, response.getMessage());
            finish();
        } else {
            ToastUtils.showLongToast(mContext, response.getMessage());
        }
    }
}
