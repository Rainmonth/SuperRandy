package com.rainmonth.activity;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.GsonBuilder;
import com.rainmonth.R;
import com.rainmonth.service.UserService;
import com.rainmonth.utils.ToastUtils;
import com.rainmonth.utils.http.Api;
import com.rainmonth.utils.http.ServiceFactory;
import com.rainmonth.utils.http.UserLoginResponse;

import butterknife.Bind;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        // todo to be delete
        etUserName.setText("15601949629");
        etPsw.setText("m123456");


    }

    @OnClick({R.id.iv_user_avatar, R.id.tv_login, R.id.tv_no_account, R.id.iv_qq, R.id.iv_sina})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_user_avatar:

                break;
            case R.id.tv_login:
                Call<UserLoginResponse> call = ServiceFactory.createService(Api.baseUrl, UserService.class).login(etUserName.getText().toString(), etPsw.getText().toString());
                call.enqueue(new Callback<UserLoginResponse>() {
                    @Override
                    public void onResponse(Call<UserLoginResponse> call, Response<UserLoginResponse> response) {
                        UserLoginResponse userLoginResponse = response.body();
                        Log.i(Tag, new GsonBuilder().create().toJson(userLoginResponse));
                        if (userLoginResponse.getCode().equals("1")) {
                            readyGo(MainActivity.class);
                            finish();
                        } else {
                            ToastUtils.showLongToast(mContext, userLoginResponse.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(Call<UserLoginResponse> call, Throwable t) {
                        Log.e("result", "response----失败");
                    }
                });

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
