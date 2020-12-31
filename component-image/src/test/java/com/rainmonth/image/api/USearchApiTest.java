package com.rainmonth.image.api;

import com.rainmonth.image.BuildConfig;
import com.rainmonth.image.base.ApiTestHelper;
import com.rainmonth.image.base.ApiTestObserver;
import com.rainmonth.image.base.BaseApiTest;
import com.rainmonth.image.mvp.model.bean.CollectionBean;
import com.rainmonth.image.mvp.model.bean.PhotoBean;
import com.rainmonth.image.mvp.model.bean.UserBean;
import com.rainmonth.image.mvp.model.bean.SearchResult;
import com.rainmonth.utils.log.LogUtils;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowLog;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static junit.framework.Assert.assertEquals;

/**
 * Created by RandyZhang on 2018/8/14.
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, manifest = Config.NONE, sdk = 23)
public class USearchApiTest extends BaseApiTest {

    @Before
    public void setUp() {
        ShadowLog.stream = System.out;
        initRxJava2();
    }

    @Test
    public void searchUser() throws Exception {
        ApiTestHelper.createApiClass(USearchApi.class)
                .searchUser("charlesdeluvio", 1, 10)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiTestObserver<SearchResult<UserBean>>() {
                    @Override
                    public void onNext(SearchResult<UserBean> userBeanSearchResult) {
                        LogUtils.d("Randy", userBeanSearchResult.getTotal_pages());
                        assertEquals(10, userBeanSearchResult
                                .getResults().size() + 1);

                    }
                });
    }

    @Test
    public void searchPhotos() throws Exception {
        ApiTestHelper.createApiClass(USearchApi.class)
                .searchPhotos("rain", 1, 10, "", "landscape")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiTestObserver<SearchResult<PhotoBean>>() {
                    @Override
                    public void onNext(SearchResult<PhotoBean> photoBeanSearchResult) {
                        LogUtils.d("Randy", photoBeanSearchResult.getTotal_pages());
                        assertEquals(10, photoBeanSearchResult
                                .getResults().size() + 1);

                    }
                });
    }

    @Test
    public void searchCollections() throws Exception {
        ApiTestHelper.createApiClass(USearchApi.class)
                .searchCollections("rain", 1, 10)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiTestObserver<SearchResult<CollectionBean>>() {
                    @Override
                    public void onNext(SearchResult<CollectionBean> collectionBeanSearchResult) {
                        LogUtils.d("Randy", collectionBeanSearchResult.getTotal_pages());
                        assertEquals(10, collectionBeanSearchResult
                                .getResults().size() + 1);

                    }
                });
    }

}