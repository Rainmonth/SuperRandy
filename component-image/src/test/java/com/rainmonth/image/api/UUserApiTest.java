package com.rainmonth.image.api;

import com.rainmonth.image.BuildConfig;
import com.rainmonth.image.base.ApiTestHelper;
import com.rainmonth.image.base.ApiTestObserver;
import com.rainmonth.image.base.BaseApiTest;
import com.rainmonth.image.mvp.model.bean.UserBean;
import com.socks.library.KLog;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowLog;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static junit.framework.Assert.assertEquals;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 23)
public class UUserApiTest extends BaseApiTest {

//    @Rule
//    public LogRule logRule = new LogRule();

    @Before
    public void setUp() {
        ShadowLog.stream = System.out;
        initRxJava2();
    }

    @Test
    public void getUserInfo() {
        ApiTestHelper.createApiClass(UUserApi.class)
                .getUserInfo("charlesdeluvio", 1000, 1000)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiTestObserver<UserBean>() {
                    @Override
                    public void onNext(UserBean userBean) {
                        KLog.d("Randy", userBean.getName());
                        assertEquals("charlesdeluvio", userBean.getName() + 1);
                    }
                });
    }
}