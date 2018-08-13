package com.rainmonth.image.api;

import com.rainmonth.image.BuildConfig;
import com.rainmonth.image.base.ApiTestHelper;
import com.rainmonth.image.base.ApiTestObserver;
import com.rainmonth.image.base.BaseApiTest;
import com.rainmonth.image.mvp.model.bean.CollectionBean;
import com.rainmonth.image.mvp.model.bean.PhotoBean;
import com.rainmonth.image.mvp.model.bean.SiteBean;
import com.rainmonth.image.mvp.model.bean.UserBean;
import com.socks.library.KLog;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowLog;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static junit.framework.Assert.assertEquals;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, manifest = Config.NONE, sdk = 23)
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

    @Test
    public void getUserLikePhotos() {
        ApiTestHelper.createApiClass(UUserApi.class)
                .getUserLikePhotos("charlesdeluvio", 1, 10, "latest")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiTestObserver<List<PhotoBean>>() {
                    @Override
                    public void onNext(List<PhotoBean> photoBeans) {
                        KLog.d("Randy", photoBeans.get(0).getUrls());
                        assertEquals(11, photoBeans.size());
                    }
                });
    }

    @Test
    public void getUserPersonalSite() {
        ApiTestHelper.createApiClass(UUserApi.class)
                .getUserPersonalSite("charlesdeluvio")
                .subscribeOn((Schedulers.io()))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiTestObserver<SiteBean>() {
                    @Override
                    public void onNext(SiteBean siteBean) {
                        KLog.d("Randy", siteBean.getUrl());
                        assertEquals("www.rainmonth.cn", siteBean.getUrl());
                    }
                });
    }

    @Test
    public void getUserCollections() {
        ApiTestHelper.createApiClass(UUserApi.class)
                .getUserCollections("charlesdeluvio", 1, 10)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiTestObserver<List<CollectionBean>>() {
                    @Override
                    public void onNext(List<CollectionBean> collectionBeans) {
                        KLog.d("Randy", collectionBeans.size());
                        assertEquals(10, collectionBeans.size());
                    }
                });
    }

    @Test
    public void getUserPhotos() {
        ApiTestHelper.createApiClass(UUserApi.class)
                .getUserPhotos("charlesdeluvio", 1, 10, "latest",
                        false, "days", 30)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiTestObserver<List<PhotoBean>>() {
                    @Override
                    public void onNext(List<PhotoBean> photoBeans) {
                        KLog.d("Randy", photoBeans.size() + "" +
                                photoBeans.get(0));
                        assertEquals(10, photoBeans.size());
                    }
                });

    }

    @Test
    public void getUserStatistics() {
        ApiTestHelper.createApiClass(UUserApi.class)
                .getUserStatistics("charlesdeluvio", "days", 20)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiTestObserver<String>() {
                    @Override
                    public void onNext(String s) {

                    }
                });
    }
}