package com.rainmonth.mvp.ui.activity;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.rainmonth.R;
import com.rainmonth.common.base.BaseActivity;
import com.rainmonth.common.base.mvp.BaseResponse;
import com.rainmonth.common.di.component.AppComponent;
import com.rainmonth.common.utils.ToastUtils;
import com.rainmonth.mvp.contract.RegisterContract;
import com.rainmonth.mvp.model.bean.UserBean;
import com.rainmonth.mvp.presenter.RegisterPresenter;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 注册页面
 */
public class RegisterActivity extends BaseActivity<RegisterPresenter>
        implements RegisterContract.View {
    @BindView(R.id.iv_user_avatar)
    ImageView ivUserAvatar;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_user_name)
    EditText etUserName;
    @BindView(R.id.et_email)
    EditText etEmail;
    @BindView(R.id.et_psw)
    EditText etPsw;
    @BindView(R.id.tv_create_account)
    TextView tvCreateAccount;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_register;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    protected void initViewsAndEvents() {
//        registerPresenter = new RegisterPresenter(this);
    }

    @Override
    public void initToolbar() {
        mActionBar.setLogo(R.drawable.ic_action_bar_logo);
        mActionBar.setTitle("注册");
//        mActionBar.setBackgroundResource(R.color.transparent);
    }

    @OnClick({R.id.iv_user_avatar, R.id.tv_create_account})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_user_avatar:
                break;
            case R.id.tv_create_account:
                UserBean userBean = new UserBean();
                userBean.setMobile(etPhone.getText().toString());
                userBean.setUsername(etUserName.getText().toString());
                userBean.setEmail(etEmail.getText().toString());
                userBean.setPsw(etPsw.getText().toString());
                break;
        }
    }

    @Override
    public void naveToAfterRegister(BaseResponse response) {
        if (response.getCode().equals("1")) {
            readyGo(MainActivity.class);
            finish();
        } else {
            ToastUtils.showLongToast(mContext, response.getMessage());
        }
    }
}
