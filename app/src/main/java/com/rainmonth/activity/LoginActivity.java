package com.rainmonth.activity;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.rainmonth.R;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 登录页面
 */
public class LoginActivity extends BaseActivity {

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
        // todo


    }

    @OnClick({R.id.iv_user_avatar, R.id.tv_login, R.id.tv_no_account, R.id.iv_qq, R.id.iv_sina})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_user_avatar:
                break;
            case R.id.tv_login:
                break;
            case R.id.tv_no_account:
                break;
            case R.id.iv_qq:
                break;
            case R.id.iv_sina:
                break;
        }
    }
}
