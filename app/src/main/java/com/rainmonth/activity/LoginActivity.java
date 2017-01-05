package com.rainmonth.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.rainmonth.R;
import com.rainmonth.base.mvp.BaseResponse;
import com.rainmonth.base.ui.activity.BaseActivity;
import com.rainmonth.bean.UserLoginBean;
import com.rainmonth.library.base.BaseAppCompatActivity;
import com.rainmonth.library.eventbus.EventCenter;
import com.rainmonth.library.utils.NetworkUtils;
import com.rainmonth.presenter.ILoginPresenter;
import com.rainmonth.presenter.impl.LoginPresenterImpl;
import com.rainmonth.service.UserService;
import com.rainmonth.utils.ToastUtils;
import com.rainmonth.utils.http.Api;
import com.rainmonth.utils.http.ServiceFactory;
import com.rainmonth.utils.http.UserLoginResponse;
import com.rainmonth.view.ILoginView;
import com.socks.library.KLog;

import butterknife.Bind;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    public void initToolbar() {
        mToolbar.setLogo(R.drawable.ic_launcher);
        mToolbar.setTitle("登录");
        mToolbar.setBackgroundResource(R.color.transparent);
    }

    @OnClick({R.id.iv_user_avatar, R.id.tv_login, R.id.tv_no_account, R.id.iv_qq, R.id.iv_sina})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_user_avatar:

                break;
            case R.id.tv_login:
//                mPresenter.login(etUserName.getText().toString(), etPsw.getText().toString());
                ServiceFactory.getRetrofit(Api.baseUrl).create(UserService.class).loginRx(etUserName.getText().toString(), etPsw.getText().toString(), "");
                Call<BaseResponse<UserLoginBean>> loginCall = ServiceFactory.createService(Api.baseUrl, UserService.class).loginRx(etUserName.getText().toString(), etPsw.getText().toString(), "");
                loginCall.enqueue(new Callback<BaseResponse<UserLoginBean>>() {
                    @Override
                    public void onResponse(Call<BaseResponse<UserLoginBean>> call, Response<BaseResponse<UserLoginBean>> response) {
                        ToastUtils.showLongToast(mContext, "接口访问成功 ->" + response.body().getMessage());
                    }

                    @Override
                    public void onFailure(Call<BaseResponse<UserLoginBean>> call, Throwable t) {
                        ToastUtils.showLongToast(mContext, "接口访问失败");
                    }
                });
                break;
            case R.id.tv_no_account:
                Call<BaseResponse<Object>> logoutCall = ServiceFactory.createService(Api.baseUrl, UserService.class).logoutRx();
                logoutCall.enqueue(new Callback<BaseResponse<Object>>() {
                    @Override
                    public void onResponse(Call<BaseResponse<Object>> call, Response<BaseResponse<Object>> response) {
                        ToastUtils.showLongToast(mContext, "接口访问成功 ->" + response.body().getMessage());
                        getSharedPreferences("cookie_sp", Context.MODE_PRIVATE).getString("cookie", "");
                        KLog.e("cookie", getSharedPreferences("cookie_sp", Context.MODE_PRIVATE).getString("cookie", ""));
                        getSharedPreferences("cookie_sp", Context.MODE_PRIVATE).edit().putString("cookie", "").commit();
                        KLog.e("cookie", getSharedPreferences("cookie_sp", Context.MODE_PRIVATE).getString("cookie", ""));
                    }

                    @Override
                    public void onFailure(Call<BaseResponse<Object>> call, Throwable t) {
                        ToastUtils.showLongToast(mContext, "接口访问失败");
                    }
                });
                break;
            case R.id.iv_qq:
                Call<BaseResponse<Object>> isEscCall = ServiceFactory.createService(Api.baseUrl, UserService.class).isEsc();
                isEscCall.enqueue(new Callback<BaseResponse<Object>>() {
                    @Override
                    public void onResponse(Call<BaseResponse<Object>> call, Response<BaseResponse<Object>> response) {
                        ToastUtils.showLongToast(mContext, "是否存管账户接口访问成功 ->" + new Gson().toJson(response.body().getData()));
                        KLog.e("cookie", getSharedPreferences("cookie_sp", Context.MODE_PRIVATE).getString("cookie", ""));
                    }

                    @Override
                    public void onFailure(Call<BaseResponse<Object>> call, Throwable t) {
                        ToastUtils.showLongToast(mContext, "是否存管账户接口访问失败");
                        KLog.e("cookie", getSharedPreferences("cookie_sp", Context.MODE_PRIVATE).getString("cookie", ""));
                    }
                });
                break;
            case R.id.iv_sina:
                Call<BaseResponse<Object>> getAccountCall = ServiceFactory.createService(Api.baseUrl, UserService.class).getAccountInfo();
                getAccountCall.enqueue(new Callback<BaseResponse<Object>>() {
                    @Override
                    public void onResponse(Call<BaseResponse<Object>> call, Response<BaseResponse<Object>> response) {
                        ToastUtils.showLongToast(mContext, "获取账户信息接口访问成功 ->" + new Gson().toJson(response.body().getData()));
                        KLog.e("cookie", getSharedPreferences("cookie_sp", Context.MODE_PRIVATE).getString("cookie", ""));
                    }

                    @Override
                    public void onFailure(Call<BaseResponse<Object>> call, Throwable t) {
                        ToastUtils.showLongToast(mContext, "获取账户信息接口访问失败");
                        KLog.e("cookie", getSharedPreferences("cookie_sp", Context.MODE_PRIVATE).getString("cookie", ""));
                    }
                });
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
        etUserName.setText("15601949622");
        etPsw.setText("m123456");
        mPresenter = new LoginPresenterImpl(this);
    }

    @Override
    protected void onNetworkConnected(NetworkUtils.NetType type) {

    }

    @Override
    protected void onNetworkDisConnected() {

    }

    @Override
    protected boolean isApplyStatusBarTranslucency() {
        return false;
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
