package com.rainmonth.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rainmonth.base.mvp.BaseSchedulerTransformer;
import com.rainmonth.base.mvp.BaseSubscriber;
import com.rainmonth.bean.ArticleBean;
import com.rainmonth.bean.BannerBean;
import com.rainmonth.service.BannerService;
import com.rainmonth.utils.http.Api;
import com.rainmonth.utils.http.RequestCallback;
import com.rainmonth.utils.http.ServiceFactory;

import java.util.List;

import retrofit2.Response;
import rx.Subscription;

/**
 * Created by RandyZhang on 16/7/5.
 */
public class RenFragmentModel implements IRenFragmentModel {
    @Override
    public Subscription getRenContentList(RequestCallback callback) {
        return ServiceFactory.createService(Api.baseUrl, BannerService.class).getContentList()
                .compose(new BaseSchedulerTransformer<Response<List<ArticleBean>>>())
                .subscribe(new BaseSubscriber<>(callback));
    }

    @Override
    public Subscription getHomeBannerList(RequestCallback callback) {
        return ServiceFactory.createService(Api.baseUrl, BannerService.class).getHomeBannerList()
                .compose(new BaseSchedulerTransformer<Response<List<BannerBean>>>())
                .subscribe(new BaseSubscriber<>(callback));
    }

    @Override
    public List<ArticleBean> getRenContentListFake() {
        List<ArticleBean> articleBeanList;
        String articleListStr = "[{\"type\":\"0\",\"type_name\":\"热门推荐\",\"list\":[{\"id\":\"10011\",\"type\":\"1\",\"type_name\":\"行走的力量\",\"title\":\"行走\",\"summarize\":\"you can you up,no can no bb\",\"thumb_url\":\"http://okzwnw2rn.bkt.clouddn.com/image/bannerren_bg_walk.jpg\",\"url\":\"https://www.baidu.com\",\"publish_time\":\"2017-02-07\",\"author\":\"Randy Zhang\",\"like_num\":\"0\",\"view_num\":\"0\",\"collect_num\":\"0\"},{\"id\":\"10021\",\"type\":\"2\",\"type_name\":\"分享的力量\",\"title\":\"分享\",\"summarize\":\"you can you up,no can no bb\",\"thumb_url\":\"http://okzwnw2rn.bkt.clouddn.com/image/bannerren_bg_share.jpg\",\"url\":\"https://www.baidu.com\",\"publish_time\":\"2017-02-07\",\"author\":\"Randy Zhang\",\"like_num\":\"0\",\"view_num\":\"0\",\"collect_num\":\"0\"},{\"id\":\"10031\",\"type\":\"3\",\"type_name\":\"阅读的力量\",\"title\":\"阅读\",\"summarize\":\"you can you up,no can no bb\",\"thumb_url\":\"http://okzwnw2rn.bkt.clouddn.com/image/bannerren_bg_read.jpg\",\"url\":\"https://www.baidu.com\",\"publish_time\":\"2017-02-07\",\"author\":\"Randy Zhang\",\"like_num\":\"0\",\"view_num\":\"0\",\"collect_num\":\"0\"},{\"id\":\"10041\",\"type\":\"4\",\"type_name\":\"音乐的力量\",\"title\":\"音乐\",\"summarize\":\"you can you up,no can no bb\",\"thumb_url\":\"http://okzwnw2rn.bkt.clouddn.com/image/bannerren_bg_sing.jpg\",\"url\":\"https://www.baidu.com\",\"publish_time\":\"2017-02-07\",\"author\":\"Randy Zhang\",\"like_num\":\"0\",\"view_num\":\"0\",\"collect_num\":\"0\"}]},{\"type\":\"1\",\"type_name\":\"行走的力量\",\"list\":[{\"id\":\"10011\",\"type\":\"1\",\"type_name\":\"行走的力量\",\"title\":\"话语\",\"summarize\":\"you can you up,no can no bb\",\"thumb_url\":\"http://okzwnw2rn.bkt.clouddn.com/image/bannerren_bg_walk.jpg\",\"url\":\"https://www.baidu.com\",\"publish_time\":\"2017-02-07\",\"author\":\"Randy Zhang\",\"like_num\":\"0\",\"view_num\":\"0\",\"collect_num\":\"0\"},{\"id\":\"10012\",\"type\":\"1\",\"type_name\":\"行走的力量\",\"title\":\"话语\",\"summarize\":\"you can you up,no can no bb\",\"thumb_url\":\"http://okzwnw2rn.bkt.clouddn.com/image/bannerren_bg_walk.jpg\",\"url\":\"https://www.baidu.com\",\"publish_time\":\"2017-02-07\",\"author\":\"Randy Zhang\",\"like_num\":\"0\",\"view_num\":\"0\",\"collect_num\":\"0\"},{\"id\":\"10013\",\"type\":\"1\",\"type_name\":\"行走的力量\",\"title\":\"话语\",\"summarize\":\"you can you up,no can no bb\",\"thumb_url\":\"http://okzwnw2rn.bkt.clouddn.com/image/bannerren_bg_walk.jpg\",\"url\":\"https://www.baidu.com\",\"publish_time\":\"2017-02-07\",\"author\":\"Randy Zhang\",\"like_num\":\"0\",\"view_num\":\"0\",\"collect_num\":\"0\"},{\"id\":\"10014\",\"type\":\"1\",\"type_name\":\"行走的力量\",\"title\":\"话语\",\"summarize\":\"you can you up,no can no bb\",\"thumb_url\":\"http://okzwnw2rn.bkt.clouddn.com/image/bannerren_bg_walk.jpg\",\"url\":\"https://www.baidu.com\",\"publish_time\":\"2017-02-07\",\"author\":\"Randy Zhang\",\"like_num\":\"0\",\"view_num\":\"0\",\"collect_num\":\"0\"}]},{\"type\":\"2\",\"type_name\":\"分享的力量\",\"list\":[{\"id\":\"10021\",\"type\":\"2\",\"type_name\":\"分享的力量\",\"title\":\"话语\",\"summarize\":\"you can you up,no can no bb\",\"thumb_url\":\"http://okzwnw2rn.bkt.clouddn.com/image/bannerren_bg_walk.jpg\",\"url\":\"https://www.baidu.com\",\"publish_time\":\"2017-02-07\",\"author\":\"Randy Zhang\",\"like_num\":\"0\",\"view_num\":\"0\",\"collect_num\":\"0\"},{\"id\":\"10022\",\"type\":\"2\",\"type_name\":\"分享的力量\",\"title\":\"话语\",\"summarize\":\"you can you up,no can no bb\",\"thumb_url\":\"http://okzwnw2rn.bkt.clouddn.com/image/bannerren_bg_walk.jpg\",\"url\":\"https://www.baidu.com\",\"publish_time\":\"2017-02-07\",\"author\":\"Randy Zhang\",\"like_num\":\"0\",\"view_num\":\"0\",\"collect_num\":\"0\"},{\"id\":\"10023\",\"type\":\"2\",\"type_name\":\"分享的力量\",\"title\":\"话语\",\"summarize\":\"you can you up,no can no bb\",\"thumb_url\":\"http://okzwnw2rn.bkt.clouddn.com/image/bannerren_bg_walk.jpg\",\"url\":\"https://www.baidu.com\",\"publish_time\":\"2017-02-07\"},{\"id\":\"10024\",\"type\":\"2\",\"type_name\":\"分享的力量\",\"title\":\"话语\",\"summarize\":\"you can you up,no can no bb\",\"thumb_url\":\"http://okzwnw2rn.bkt.clouddn.com/image/bannerren_bg_walk.jpg\",\"url\":\"https://www.baidu.com\",\"publish_time\":\"2017-02-07\",\"author\":\"Randy Zhang\",\"like_num\":\"0\",\"view_num\":\"0\",\"collect_num\":\"0\"}]},{\"type\":\"3\",\"type_name\":\"阅读的力量\",\"list\":[{\"id\":\"10031\",\"type\":\"3\",\"type_name\":\"阅读的力量\",\"title\":\"话语\",\"summarize\":\"you can you up,no can no bb\",\"thumb_url\":\"http://okzwnw2rn.bkt.clouddn.com/image/bannerren_bg_walk.jpg\",\"url\":\"https://www.baidu.com\",\"publish_time\":\"2017-02-07\"},{\"id\":\"10032\",\"type\":\"3\",\"type_name\":\"阅读的力量\",\"title\":\"话语\",\"summarize\":\"you can you up,no can no bb\",\"thumb_url\":\"http://okzwnw2rn.bkt.clouddn.com/image/bannerren_bg_walk.jpg\",\"url\":\"https://www.baidu.com\",\"publish_time\":\"2017-02-07\",\"author\":\"Randy Zhang\",\"like_num\":\"0\",\"view_num\":\"0\",\"collect_num\":\"0\"},{\"id\":\"10033\",\"type\":\"3\",\"type_name\":\"阅读的力量\",\"title\":\"话语\",\"summarize\":\"you can you up,no can no bb\",\"thumb_url\":\"http://okzwnw2rn.bkt.clouddn.com/image/bannerren_bg_walk.jpg\",\"url\":\"https://www.baidu.com\",\"publish_time\":\"2017-02-07\",\"author\":\"Randy Zhang\",\"like_num\":\"0\",\"view_num\":\"0\",\"collect_num\":\"0\"},{\"id\":\"10034\",\"type\":\"3\",\"type_name\":\"阅读的力量\",\"title\":\"话语\",\"summarize\":\"you can you up,no can no bb\",\"thumb_url\":\"http://okzwnw2rn.bkt.clouddn.com/image/bannerren_bg_walk.jpg\",\"url\":\"https://www.baidu.com\",\"publish_time\":\"2017-02-07\",\"author\":\"Randy Zhang\",\"like_num\":\"0\",\"view_num\":\"0\",\"collect_num\":\"0\"}]},{\"type\":\"4\",\"type_name\":\"音乐的力量\",\"list\":[{\"id\":\"10041\",\"type\":\"4\",\"type_name\":\"音乐的力量\",\"title\":\"话语\",\"summarize\":\"you can you up,no can no bb\",\"thumb_url\":\"http://okzwnw2rn.bkt.clouddn.com/image/bannerren_bg_walk.jpg\",\"url\":\"https://www.baidu.com\",\"publish_time\":\"2017-02-07\",\"author\":\"Randy Zhang\",\"like_num\":\"0\",\"view_num\":\"0\",\"collect_num\":\"0\"},{\"id\":\"10042\",\"type\":\"4\",\"type_name\":\"音乐的力量\",\"title\":\"话语\",\"summarize\":\"you can you up,no can no bb\",\"thumb_url\":\"http://okzwnw2rn.bkt.clouddn.com/image/bannerren_bg_walk.jpg\",\"url\":\"https://www.baidu.com\",\"publish_time\":\"2017-02-07\",\"author\":\"Randy Zhang\",\"like_num\":\"0\",\"view_num\":\"0\",\"collect_num\":\"0\"},{\"id\":\"10043\",\"type\":\"4\",\"type_name\":\"音乐的力量\",\"title\":\"话语\",\"summarize\":\"you can you up,no can no bb\",\"thumb_url\":\"http://okzwnw2rn.bkt.clouddn.com/image/bannerren_bg_walk.jpg\",\"url\":\"https://www.baidu.com\",\"publish_time\":\"2017-02-07\",\"author\":\"Randy Zhang\",\"like_num\":\"0\",\"view_num\":\"0\",\"collect_num\":\"0\"},{\"id\":\"10044\",\"type\":\"4\",\"type_name\":\"音乐的力量\",\"title\":\"话语\",\"summarize\":\"you can you up,no can no bb\",\"thumb_url\":\"http://okzwnw2rn.bkt.clouddn.com/image/bannerren_bg_walk.jpg\",\"url\":\"https://www.baidu.com\",\"publish_time\":\"2017-02-07\",\"author\":\"Randy Zhang\",\"like_num\":\"0\",\"view_num\":\"0\",\"collect_num\":\"0\"}]}]";
        Gson gson = new Gson();

        articleBeanList = gson.fromJson(articleListStr, new TypeToken<List<ArticleBean>>() {
        }.getType());

        return articleBeanList;
    }

    @Override
    public List<BannerBean> getHomeBannerListFake() {
        List<BannerBean> bannerBeanList;
        /**
         * 1、如果有网络，则请求网络数据；
         * 2、没有网络，则读取本地缓存数据
         */
        String jsonStr = "[{\"id\":\"10001\",\"type\":\"1\",\"title\":\"端午节活动\",\"url\":\"https://www.baidu.com\",\"banner_thumb_url\":\"http://okzwnw2rn.bkt.clouddn.com/image/banner/ren_bg_sport.jpg\"},{\"id\":\"10001\",\"type\":\"1\",\"title\":\"儿童节活动\",\"url\":\"https://www.baidu.com\",\"banner_thumb_url\":\"http://okzwnw2rn.bkt.clouddn.com/image/banner/ren_bg_game.jpg\"},{\"id\":\"10001\",\"type\":\"1\",\"title\":\"七夕情人节活动\",\"url\":\"https://www.baidu.com\",\"banner_thumb_url\":\"http://okzwnw2rn.bkt.clouddn.com/image/banner/ren_bg_sing.jpg\"},{\"id\":\"10001\",\"type\":\"1\",\"title\":\"中秋节活动\",\"url\":\"https://www.baidu.com\",\"banner_thumb_url\":\"http://okzwnw2rn.bkt.clouddn.com/image/banner/ren_bg_walk.jpg\"}]";
        Gson gson = new Gson();

        bannerBeanList = gson.fromJson(jsonStr, new TypeToken<List<BannerBean>>() {
        }.getType());

        return bannerBeanList;
    }
}
