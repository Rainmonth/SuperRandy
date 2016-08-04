package com.rainmonth.activity;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.rainmonth.R;

import butterknife.Bind;

/**
 * 登录页面
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener{

    @Bind(R.id.et_user_name)
    EditText etUserName;
    @Bind(R.id.et_psw)
    EditText etPsw;
    @Bind(R.id.tv_login)
    TextView tvLogin;

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

        tvLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_login:

                break;

            default:

                break;
        }
    }
}
