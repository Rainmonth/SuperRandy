package com.rainmonth.image.api;

import com.rainmonth.common.BuildConfig;
import com.rainmonth.image.base.ApiTestHelper;
import com.rainmonth.image.base.ApiTestObserver;
import com.rainmonth.image.base.BaseApiTest;
import com.rainmonth.image.base.LogRule;
import com.rainmonth.image.mvp.model.bean.PhotoBean;
import com.socks.library.KLog;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowLog;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static org.junit.Assert.assertEquals;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 23)
public class UPhotoApiTest extends BaseApiTest {

    @Rule
    public LogRule logRule = new LogRule();

    @Before
    public void setUp() {
        ShadowLog.stream = System.out;
        initRxJava2();
    }

    @Test
    public void getPhotos() {
        ApiTestHelper.createApiClass(UPhotoApi.class)
                .getPhotos(1, 10, "latest")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiTestObserver<List<PhotoBean>>() {

                    @Override
                    public void onNext(List<PhotoBean> photoBeanList) {
                        KLog.e("Randy", "next");
                        KLog.e("Randy", "size=" + photoBeanList.size());
                        assertEquals(10, photoBeanList.size());
                    }
                });
    }

    @Test
    public void getDailyPhoto() {
        ApiTestHelper.createApiClass(UPhotoApi.class, Consts.NO_API_BASE_URL)
                .getDailyPhoto()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiTestObserver<PhotoBean>() {

                    @Override
                    public void onNext(PhotoBean photoBean) {
                        KLog.e("Randy", "next");
                        KLog.e("Randy", "size=" + photoBean.getUrls().getFull());
//                        assertEquals(10, collectionBeans.size());
                    }
                });

    }
}