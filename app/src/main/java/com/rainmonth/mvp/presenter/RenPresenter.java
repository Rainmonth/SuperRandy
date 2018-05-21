package com.rainmonth.mvp.presenter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rainmonth.common.base.mvp.BasePresenter;
import com.rainmonth.common.base.mvp.BaseResponse;
import com.rainmonth.mvp.model.bean.ArticleBean;
import com.rainmonth.mvp.model.bean.ArticleGroupBean;
import com.rainmonth.mvp.model.bean.BannerBean;
import com.rainmonth.mvp.view.RenFragmentView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

/**
 * Created by RandyZhang on 16/7/5.
 */
public class RenPresenter extends BasePresenter<RenFragmentView, Response<BaseResponse>> implements IRenPresenter {
    private RenFragmentView renFragmentView = null;
    //    private IRenFragmentModel renFragmentModel = null;
    private String requestUrl = "";//用以区分不同接口，方便对不同接口做不同处理

    public RenPresenter(RenFragmentView renFragmentView) {
        super(renFragmentView);
        if (null == renFragmentView) {
            throw new IllegalArgumentException("View should not be null");
        }
        this.renFragmentView = renFragmentView;
//        renFragmentModel = new RenFragmentModel();
    }

    @Override
    public void getContentList() {
        requestUrl = "getContentList";
//        renFragmentModel.getRenContentList(this);
//        mSubscription = renFragmentModel.getRenContentList(this);
    }

    public List<ArticleGroupBean> getContentListFake() {
//        return renFragmentModel.getRenContentListFake();
        return new ArrayList<>();
    }

    @Override
    public void getHomeBanner() {
        requestUrl = "Banner/getHomeBanner";
//        renFragmentModel.getHomeBannerList(this);
//        mSubscription = renFragmentModel.getHomeBannerList(this);
    }

    public List<BannerBean> getHomeBannerFake() {
//        return renFragmentModel.getHomeBannerListFake();
        return new ArrayList<>();
    }

    @Override
    public void navToDetail(ArticleBean articleBean) {
        renFragmentView.navToDetail(articleBean);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void beforeRequest() {
        super.beforeRequest();
        mView.toast("提示");
    }

    @Override
    public void requestError(String msg) {
        super.requestError(msg);
    }

    @Override
    public void requestComplete() {
        super.requestComplete();
    }

    @Override
    public void requestSuccess(Response<BaseResponse> data) {
        super.requestSuccess(data);
        if ("Banner/getHomeBanner".equals(requestUrl)) {
//            List<BannerBean> bannerBeanList = (List<BannerBean>) data.body();
            List<BannerBean> bannerInfoList = new ArrayList<BannerBean>();
            /**
             * 1、如果有网络，则请求网络数据；
             * 2、没有网络，则读取本地缓存数据
             */
            String jsonStr = "{\"id\":\"10001\",\"type\":\"1\",\"title\":\"活动主页一\",\"url\":\"https://www.baidu.com\",\"banner_thumb_url\":\"http://pic2.cxtuku.com/00/02/31/b945758fd74d.jpg\"}";
            Gson gson = new Gson();
            for (int i = 0; i < 5; i++) {
                BannerBean bannerInfo = gson.fromJson(jsonStr, BannerBean.class);
                bannerInfoList.add(bannerInfo);
            }
            mView.initHomeBanners(bannerInfoList);
        }
        if ("getContentList".equals(requestUrl)) {
            List<ArticleGroupBean> articleBeanList;
            String articleListStr = "[{\"type\":\"0\",\"type_name\":\"热门推荐\",\"list\":[{\"id\":\"10011\",\"type\":\"1\",\"type_name\":\"行走的力量\",\"title\":\"行走\",\"summarize\":\"you can you up,no can no bb\",\"thumb_url\":\"http://okzwnw2rn.bkt.clouddn.com/image/bannerren_bg_walk.jpg\",\"url\":\"https://www.baidu.com\",\"publish_time\":\"2017-02-07\",\"author\":\"Randy Zhang\",\"like_num\":\"0\",\"view_num\":\"0\",\"collect_num\":\"0\"},{\"id\":\"10021\",\"type\":\"2\",\"type_name\":\"分享的力量\",\"title\":\"分享\",\"summarize\":\"you can you up,no can no bb\",\"thumb_url\":\"http://okzwnw2rn.bkt.clouddn.com/image/bannerren_bg_share.jpg\",\"url\":\"https://www.baidu.com\",\"publish_time\":\"2017-02-07\",\"author\":\"Randy Zhang\",\"like_num\":\"0\",\"view_num\":\"0\",\"collect_num\":\"0\"},{\"id\":\"10031\",\"type\":\"3\",\"type_name\":\"阅读的力量\",\"title\":\"阅读\",\"summarize\":\"you can you up,no can no bb\",\"thumb_url\":\"http://okzwnw2rn.bkt.clouddn.com/image/bannerren_bg_read.jpg\",\"url\":\"https://www.baidu.com\",\"publish_time\":\"2017-02-07\",\"author\":\"Randy Zhang\",\"like_num\":\"0\",\"view_num\":\"0\",\"collect_num\":\"0\"},{\"id\":\"10041\",\"type\":\"4\",\"type_name\":\"音乐的力量\",\"title\":\"音乐\",\"summarize\":\"you can you up,no can no bb\",\"thumb_url\":\"http://okzwnw2rn.bkt.clouddn.com/image/bannerren_bg_sing.jpg\",\"url\":\"https://www.baidu.com\",\"publish_time\":\"2017-02-07\",\"author\":\"Randy Zhang\",\"like_num\":\"0\",\"view_num\":\"0\",\"collect_num\":\"0\"}]},{\"type\":\"1\",\"type_name\":\"行走的力量\",\"list\":[{\"id\":\"10011\",\"type\":\"1\",\"type_name\":\"行走的力量\",\"title\":\"话语\",\"summarize\":\"you can you up,no can no bb\",\"thumb_url\":\"http://okzwnw2rn.bkt.clouddn.com/image/bannerren_bg_walk.jpg\",\"url\":\"https://www.baidu.com\",\"publish_time\":\"2017-02-07\",\"author\":\"Randy Zhang\",\"like_num\":\"0\",\"view_num\":\"0\",\"collect_num\":\"0\"},{\"id\":\"10012\",\"type\":\"1\",\"type_name\":\"行走的力量\",\"title\":\"话语\",\"summarize\":\"you can you up,no can no bb\",\"thumb_url\":\"http://okzwnw2rn.bkt.clouddn.com/image/bannerren_bg_walk.jpg\",\"url\":\"https://www.baidu.com\",\"publish_time\":\"2017-02-07\",\"author\":\"Randy Zhang\",\"like_num\":\"0\",\"view_num\":\"0\",\"collect_num\":\"0\"},{\"id\":\"10013\",\"type\":\"1\",\"type_name\":\"行走的力量\",\"title\":\"话语\",\"summarize\":\"you can you up,no can no bb\",\"thumb_url\":\"http://okzwnw2rn.bkt.clouddn.com/image/bannerren_bg_walk.jpg\",\"url\":\"https://www.baidu.com\",\"publish_time\":\"2017-02-07\",\"author\":\"Randy Zhang\",\"like_num\":\"0\",\"view_num\":\"0\",\"collect_num\":\"0\"},{\"id\":\"10014\",\"type\":\"1\",\"type_name\":\"行走的力量\",\"title\":\"话语\",\"summarize\":\"you can you up,no can no bb\",\"thumb_url\":\"http://okzwnw2rn.bkt.clouddn.com/image/bannerren_bg_walk.jpg\",\"url\":\"https://www.baidu.com\",\"publish_time\":\"2017-02-07\",\"author\":\"Randy Zhang\",\"like_num\":\"0\",\"view_num\":\"0\",\"collect_num\":\"0\"}]},{\"type\":\"2\",\"type_name\":\"分享的力量\",\"list\":[{\"id\":\"10021\",\"type\":\"2\",\"type_name\":\"分享的力量\",\"title\":\"话语\",\"summarize\":\"you can you up,no can no bb\",\"thumb_url\":\"http://okzwnw2rn.bkt.clouddn.com/image/bannerren_bg_walk.jpg\",\"url\":\"https://www.baidu.com\",\"publish_time\":\"2017-02-07\",\"author\":\"Randy Zhang\",\"like_num\":\"0\",\"view_num\":\"0\",\"collect_num\":\"0\"},{\"id\":\"10022\",\"type\":\"2\",\"type_name\":\"分享的力量\",\"title\":\"话语\",\"summarize\":\"you can you up,no can no bb\",\"thumb_url\":\"http://okzwnw2rn.bkt.clouddn.com/image/bannerren_bg_walk.jpg\",\"url\":\"https://www.baidu.com\",\"publish_time\":\"2017-02-07\",\"author\":\"Randy Zhang\",\"like_num\":\"0\",\"view_num\":\"0\",\"collect_num\":\"0\"},{\"id\":\"10023\",\"type\":\"2\",\"type_name\":\"分享的力量\",\"title\":\"话语\",\"summarize\":\"you can you up,no can no bb\",\"thumb_url\":\"http://okzwnw2rn.bkt.clouddn.com/image/bannerren_bg_walk.jpg\",\"url\":\"https://www.baidu.com\",\"publish_time\":\"2017-02-07\"},{\"id\":\"10024\",\"type\":\"2\",\"type_name\":\"分享的力量\",\"title\":\"话语\",\"summarize\":\"you can you up,no can no bb\",\"thumb_url\":\"http://okzwnw2rn.bkt.clouddn.com/image/bannerren_bg_walk.jpg\",\"url\":\"https://www.baidu.com\",\"publish_time\":\"2017-02-07\",\"author\":\"Randy Zhang\",\"like_num\":\"0\",\"view_num\":\"0\",\"collect_num\":\"0\"}]},{\"type\":\"3\",\"type_name\":\"阅读的力量\",\"list\":[{\"id\":\"10031\",\"type\":\"3\",\"type_name\":\"阅读的力量\",\"title\":\"话语\",\"summarize\":\"you can you up,no can no bb\",\"thumb_url\":\"http://okzwnw2rn.bkt.clouddn.com/image/bannerren_bg_walk.jpg\",\"url\":\"https://www.baidu.com\",\"publish_time\":\"2017-02-07\"},{\"id\":\"10032\",\"type\":\"3\",\"type_name\":\"阅读的力量\",\"title\":\"话语\",\"summarize\":\"you can you up,no can no bb\",\"thumb_url\":\"http://okzwnw2rn.bkt.clouddn.com/image/bannerren_bg_walk.jpg\",\"url\":\"https://www.baidu.com\",\"publish_time\":\"2017-02-07\",\"author\":\"Randy Zhang\",\"like_num\":\"0\",\"view_num\":\"0\",\"collect_num\":\"0\"},{\"id\":\"10033\",\"type\":\"3\",\"type_name\":\"阅读的力量\",\"title\":\"话语\",\"summarize\":\"you can you up,no can no bb\",\"thumb_url\":\"http://okzwnw2rn.bkt.clouddn.com/image/bannerren_bg_walk.jpg\",\"url\":\"https://www.baidu.com\",\"publish_time\":\"2017-02-07\",\"author\":\"Randy Zhang\",\"like_num\":\"0\",\"view_num\":\"0\",\"collect_num\":\"0\"},{\"id\":\"10034\",\"type\":\"3\",\"type_name\":\"阅读的力量\",\"title\":\"话语\",\"summarize\":\"you can you up,no can no bb\",\"thumb_url\":\"http://okzwnw2rn.bkt.clouddn.com/image/bannerren_bg_walk.jpg\",\"url\":\"https://www.baidu.com\",\"publish_time\":\"2017-02-07\",\"author\":\"Randy Zhang\",\"like_num\":\"0\",\"view_num\":\"0\",\"collect_num\":\"0\"}]},{\"type\":\"4\",\"type_name\":\"音乐的力量\",\"list\":[{\"id\":\"10041\",\"type\":\"4\",\"type_name\":\"音乐的力量\",\"title\":\"话语\",\"summarize\":\"you can you up,no can no bb\",\"thumb_url\":\"http://okzwnw2rn.bkt.clouddn.com/image/bannerren_bg_walk.jpg\",\"url\":\"https://www.baidu.com\",\"publish_time\":\"2017-02-07\",\"author\":\"Randy Zhang\",\"like_num\":\"0\",\"view_num\":\"0\",\"collect_num\":\"0\"},{\"id\":\"10042\",\"type\":\"4\",\"type_name\":\"音乐的力量\",\"title\":\"话语\",\"summarize\":\"you can you up,no can no bb\",\"thumb_url\":\"http://okzwnw2rn.bkt.clouddn.com/image/bannerren_bg_walk.jpg\",\"url\":\"https://www.baidu.com\",\"publish_time\":\"2017-02-07\",\"author\":\"Randy Zhang\",\"like_num\":\"0\",\"view_num\":\"0\",\"collect_num\":\"0\"},{\"id\":\"10043\",\"type\":\"4\",\"type_name\":\"音乐的力量\",\"title\":\"话语\",\"summarize\":\"you can you up,no can no bb\",\"thumb_url\":\"http://okzwnw2rn.bkt.clouddn.com/image/bannerren_bg_walk.jpg\",\"url\":\"https://www.baidu.com\",\"publish_time\":\"2017-02-07\",\"author\":\"Randy Zhang\",\"like_num\":\"0\",\"view_num\":\"0\",\"collect_num\":\"0\"},{\"id\":\"10044\",\"type\":\"4\",\"type_name\":\"音乐的力量\",\"title\":\"话语\",\"summarize\":\"you can you up,no can no bb\",\"thumb_url\":\"http://okzwnw2rn.bkt.clouddn.com/image/bannerren_bg_walk.jpg\",\"url\":\"https://www.baidu.com\",\"publish_time\":\"2017-02-07\",\"author\":\"Randy Zhang\",\"like_num\":\"0\",\"view_num\":\"0\",\"collect_num\":\"0\"}]}]";
            Gson gson = new Gson();

            articleBeanList = gson.fromJson(articleListStr, new TypeToken<List<ArticleGroupBean>>() {
            }.getType());
            mView.initContentList(articleBeanList);
        }
    }
}
