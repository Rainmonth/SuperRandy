package com.rainmonth.image.api;

import com.rainmonth.common.BuildConfig;
import com.rainmonth.common.utils.RxUtils;
import com.rainmonth.image.base.ApiTestHelper;
import com.rainmonth.image.base.ApiTestObserver;
import com.rainmonth.image.base.BaseApiTest;
import com.rainmonth.image.base.LogRule;
import com.rainmonth.image.mvp.model.bean.CollectionBean;
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

import static junit.framework.Assert.assertEquals;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = 23)
public class UCollectionApiTest extends BaseApiTest {

    @Rule
    public LogRule logRule = new LogRule();

    @Before
    public void setUp() {
        ShadowLog.stream = System.out;
        initRxJava2();
    }

    @Test
    public void getFeaturedCollection() {
        ApiTestHelper.createApiClass(UCollectionApi.class)
                .getFeaturedCollections(1, 10)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiTestObserver<List<CollectionBean>>() {
                    @Override
                    public void onNext(List<CollectionBean> collectionBeans) {
                        KLog.e("Randy", "next");
                        KLog.e("Randy", "size=" + collectionBeans.size());
                        assertEquals(11, collectionBeans.size());
                    }
                });

    }

    @Test
    public void getCuratedCollection() {
        ApiTestHelper.createApiClass(UCollectionApi.class)
                .getCuratedCollections(1, 10)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiTestObserver<List<CollectionBean>>() {
                    @Override
                    public void onNext(List<CollectionBean> collectionBeans) {
                        KLog.e("Randy", "next");
                        KLog.e("Randy", "size=" + collectionBeans.size());
                        assertEquals(11, collectionBeans.size());
                    }
                });

    }

    @Test
    public void getCuratedCollectionPhotos() {
        ApiTestHelper.createApiClass(UCollectionApi.class)
                .getCuratedCollectionPhotos(1922729, 1, 10)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiTestObserver<List<PhotoBean>>() {
                    @Override
                    public void onNext(List<PhotoBean> photoBeans) {
                        KLog.e("Randy", "next");
                        KLog.e("Randy", "size=" + photoBeans.size());
                        assertEquals(10, photoBeans.size());
                    }
                });
    }

    @Test
    public void getCollectionPhotos() {
        ApiTestHelper.createApiClass(UCollectionApi.class)
                .getCollectionPhotos(1922729, 1, 10)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiTestObserver<List<PhotoBean>>() {
                    @Override
                    public void onNext(List<PhotoBean> photoBeans) {
                        KLog.e("Randy", "next");
                        KLog.e("Randy", "size=" + photoBeans.size());
                        assertEquals(10, photoBeans.size());
                    }
                });
    }

    @Test
    public void getCollectionDetailInfo() {
        ApiTestHelper.createApiClass(UCollectionApi.class)
                .getCollectionDetailInfo(1922729)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiTestObserver<CollectionBean>() {

                    @Override
                    public void onNext(CollectionBean collectionBean) {
                        KLog.e("Randy", "next");
                        KLog.e("Randy", ";id=" + collectionBean.getId() +
                                "desc=" + collectionBean.getDescription());
                        assertEquals(1922728, collectionBean.getId());
                    }
                });
    }

    @Test
    public void addCollection() {
    }

    @Test
    public void addCollection1() {
    }

    @Test
    public void addPhotoToCollection() {
    }

    @Test
    public void deleteCollection() {
    }

    @Test
    public void deletePhotoFromCollection() {
    }

    @Test
    public void updateCollection() {
    }

    @Test
    public void getRelatedCollections() {
        ApiTestHelper.createApiClass(UCollectionApi.class)
                .getRelatedCollections(1922729)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiTestObserver<List<CollectionBean>>() {

                    @Override
                    public void onNext(List<CollectionBean> collectionBeans) {
                        KLog.e("Randy", "next");
                        KLog.e("Randy", "size=" + collectionBeans.size());
                        assertEquals(10, collectionBeans.size());
                    }
                });

    }
}