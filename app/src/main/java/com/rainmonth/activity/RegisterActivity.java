package com.rainmonth.activity;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.rainmonth.R;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 注册页面
 */
public class RegisterActivity extends BaseActivity {


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

    @Override
    public boolean isApplyTranslucentStatusBar() {
        return false;
    }

    @Override
    public int getContentViewLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    public void initViewsAndEvent() {
    }

    @OnClick({R.id.iv_user_avatar, R.id.tv_create_account})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_user_avatar:
                break;
            case R.id.tv_create_account:
                break;
        }
    }
}
