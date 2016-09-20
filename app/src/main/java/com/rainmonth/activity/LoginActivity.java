package com.rainmonth.activity;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.rainmonth.R;
import com.rainmonth.base.ui.activity.BaseActivity;
import com.rainmonth.presenter.ILoginPresenter;
import com.rainmonth.presenter.LoginPresenterImpl;
import com.rainmonth.utils.ToastUtils;
import com.rainmonth.utils.http.UserLoginResponse;
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
    EditText etUserName;
    @Bind(R.id.et_psw)
    EditText etPsw;
    @Bind(R.id.tv_login)
    TextView tvLogin;
    @Bind(R.id.tv_no_account)
    TextView tvNoAccount;
    @Bind(R.id.iv_qq)
    ImageView ivQq;
    @Bind(R.id.iv_sina)
    ImageView ivSina;

    private ILoginPresenter mPresenter;

    @Override
    public boolean isApplyTranslucentStatusBar() {
        return false;
    }

    @Override
    public int getContentViewLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initViewsAndEvent() {
        // todo to be delete
        etUserName.setText("15601949629");
        etPsw.setText("m123456");
        mPresenter = new LoginPresenterImpl(this);


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
    public void naveToAfterLogin(UserLoginResponse response) {
        if (response.getCode().equals("1")) {
            readyGo(MainActivity.class);
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
}
