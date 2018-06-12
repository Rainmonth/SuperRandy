package com.rainmonth.mvp.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rainmonth.api.BannerService;
import com.rainmonth.common.base.mvp.BaseModel;
import com.rainmonth.common.di.scope.FragmentScope;
import com.rainmonth.common.integration.IRepositoryManager;
import com.rainmonth.mvp.contract.RenContract;
import com.rainmonth.mvp.model.bean.ArticleGroupBean;
import com.rainmonth.mvp.model.bean.BannerBean;
import com.rainmonth.common.http.Result;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import retrofit2.Response;

/**
 * 荏Fragment数据获取
 * Created by RandyZhang on 2018/5/31.
 */
@FragmentScope
public class RenModel extends BaseModel implements RenContract.Model {
    @Inject
    public RenModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Flowable<Result<List<BannerBean>>> getBannerList(int page, int pageSize, int type) {
        return mRepositoryManager.obtainRetrofitService(BannerService.class).
                getHomeBannerList(page, pageSize, type)
                .map(new Function<Response<Result<List<BannerBean>>>, Result<List<BannerBean>>>() {
                    @Override
                    public Result<List<BannerBean>> apply(Response<Result<List<BannerBean>>> resultResponse) throws Exception {
                        return resultResponse.body();
                    }
                });
    }

    @Override
    public List<ArticleGroupBean> getArticleList() {
        List<ArticleGroupBean> articleGroupBeanList;
        Gson gson = new Gson();

        String articleListStr = "[{\"type\":\"0\",\"type_name\":\"热门推荐\",\"list\":[{\"id\":\"10011\",\"type\":\"1\",\"type_name\":\"行走的力量\",\"title\":\"行走\",\"summarize\":\"you can you up,no can no bb\",\"thumb_url\":\"http://okzwnw2rn.bkt.clouddn.com/image/bannerren_bg_walk.jpg\",\"url\":\"https://www.baidu.com\",\"publish_time\":\"2017-02-07\",\"author\":\"Randy Zhang\",\"like_num\":\"0\",\"view_num\":\"0\",\"collect_num\":\"0\"},{\"id\":\"10021\",\"type\":\"2\",\"type_name\":\"分享的力量\",\"title\":\"分享\",\"summarize\":\"you can you up,no can no bb\",\"thumb_url\":\"http://okzwnw2rn.bkt.clouddn.com/image/bannerren_bg_share.jpg\",\"url\":\"https://www.baidu.com\",\"publish_time\":\"2017-02-07\",\"author\":\"Randy Zhang\",\"like_num\":\"0\",\"view_num\":\"0\",\"collect_num\":\"0\"},{\"id\":\"10031\",\"type\":\"3\",\"type_name\":\"阅读的力量\",\"title\":\"阅读\",\"summarize\":\"you can you up,no can no bb\",\"thumb_url\":\"http://okzwnw2rn.bkt.clouddn.com/image/bannerren_bg_read.jpg\",\"url\":\"https://www.baidu.com\",\"publish_time\":\"2017-02-07\",\"author\":\"Randy Zhang\",\"like_num\":\"0\",\"view_num\":\"0\",\"collect_num\":\"0\"},{\"id\":\"10041\",\"type\":\"4\",\"type_name\":\"音乐的力量\",\"title\":\"音乐\",\"summarize\":\"you can you up,no can no bb\",\"thumb_url\":\"http://okzwnw2rn.bkt.clouddn.com/image/bannerren_bg_sing.jpg\",\"url\":\"https://www.baidu.com\",\"publish_time\":\"2017-02-07\",\"author\":\"Randy Zhang\",\"like_num\":\"0\",\"view_num\":\"0\",\"collect_num\":\"0\"}]},{\"type\":\"1\",\"type_name\":\"行走的力量\",\"list\":[{\"id\":\"10011\",\"type\":\"1\",\"type_name\":\"行走的力量\",\"title\":\"话语\",\"summarize\":\"you can you up,no can no bb\",\"thumb_url\":\"http://okzwnw2rn.bkt.clouddn.com/image/bannerren_bg_walk.jpg\",\"url\":\"https://www.baidu.com\",\"publish_time\":\"2017-02-07\",\"author\":\"Randy Zhang\",\"like_num\":\"0\",\"view_num\":\"0\",\"collect_num\":\"0\"},{\"id\":\"10012\",\"type\":\"1\",\"type_name\":\"行走的力量\",\"title\":\"话语\",\"summarize\":\"you can you up,no can no bb\",\"thumb_url\":\"http://okzwnw2rn.bkt.clouddn.com/image/bannerren_bg_walk.jpg\",\"url\":\"https://www.baidu.com\",\"publish_time\":\"2017-02-07\",\"author\":\"Randy Zhang\",\"like_num\":\"0\",\"view_num\":\"0\",\"collect_num\":\"0\"},{\"id\":\"10013\",\"type\":\"1\",\"type_name\":\"行走的力量\",\"title\":\"话语\",\"summarize\":\"you can you up,no can no bb\",\"thumb_url\":\"http://okzwnw2rn.bkt.clouddn.com/image/bannerren_bg_walk.jpg\",\"url\":\"https://www.baidu.com\",\"publish_time\":\"2017-02-07\",\"author\":\"Randy Zhang\",\"like_num\":\"0\",\"view_num\":\"0\",\"collect_num\":\"0\"},{\"id\":\"10014\",\"type\":\"1\",\"type_name\":\"行走的力量\",\"title\":\"话语\",\"summarize\":\"you can you up,no can no bb\",\"thumb_url\":\"http://okzwnw2rn.bkt.clouddn.com/image/bannerren_bg_walk.jpg\",\"url\":\"https://www.baidu.com\",\"publish_time\":\"2017-02-07\",\"author\":\"Randy Zhang\",\"like_num\":\"0\",\"view_num\":\"0\",\"collect_num\":\"0\"}]},{\"type\":\"2\",\"type_name\":\"分享的力量\",\"list\":[{\"id\":\"10021\",\"type\":\"2\",\"type_name\":\"分享的力量\",\"title\":\"话语\",\"summarize\":\"you can you up,no can no bb\",\"thumb_url\":\"http://okzwnw2rn.bkt.clouddn.com/image/bannerren_bg_walk.jpg\",\"url\":\"https://www.baidu.com\",\"publish_time\":\"2017-02-07\",\"author\":\"Randy Zhang\",\"like_num\":\"0\",\"view_num\":\"0\",\"collect_num\":\"0\"},{\"id\":\"10022\",\"type\":\"2\",\"type_name\":\"分享的力量\",\"title\":\"话语\",\"summarize\":\"you can you up,no can no bb\",\"thumb_url\":\"http://okzwnw2rn.bkt.clouddn.com/image/bannerren_bg_walk.jpg\",\"url\":\"https://www.baidu.com\",\"publish_time\":\"2017-02-07\",\"author\":\"Randy Zhang\",\"like_num\":\"0\",\"view_num\":\"0\",\"collect_num\":\"0\"},{\"id\":\"10023\",\"type\":\"2\",\"type_name\":\"分享的力量\",\"title\":\"话语\",\"summarize\":\"you can you up,no can no bb\",\"thumb_url\":\"http://okzwnw2rn.bkt.clouddn.com/image/bannerren_bg_walk.jpg\",\"url\":\"https://www.baidu.com\",\"publish_time\":\"2017-02-07\"},{\"id\":\"10024\",\"type\":\"2\",\"type_name\":\"分享的力量\",\"title\":\"话语\",\"summarize\":\"you can you up,no can no bb\",\"thumb_url\":\"http://okzwnw2rn.bkt.clouddn.com/image/bannerren_bg_walk.jpg\",\"url\":\"https://www.baidu.com\",\"publish_time\":\"2017-02-07\",\"author\":\"Randy Zhang\",\"like_num\":\"0\",\"view_num\":\"0\",\"collect_num\":\"0\"}]},{\"type\":\"3\",\"type_name\":\"阅读的力量\",\"list\":[{\"id\":\"10031\",\"type\":\"3\",\"type_name\":\"阅读的力量\",\"title\":\"话语\",\"summarize\":\"you can you up,no can no bb\",\"thumb_url\":\"http://okzwnw2rn.bkt.clouddn.com/image/bannerren_bg_walk.jpg\",\"url\":\"https://www.baidu.com\",\"publish_time\":\"2017-02-07\"},{\"id\":\"10032\",\"type\":\"3\",\"type_name\":\"阅读的力量\",\"title\":\"话语\",\"summarize\":\"you can you up,no can no bb\",\"thumb_url\":\"http://okzwnw2rn.bkt.clouddn.com/image/bannerren_bg_walk.jpg\",\"url\":\"https://www.baidu.com\",\"publish_time\":\"2017-02-07\",\"author\":\"Randy Zhang\",\"like_num\":\"0\",\"view_num\":\"0\",\"collect_num\":\"0\"},{\"id\":\"10033\",\"type\":\"3\",\"type_name\":\"阅读的力量\",\"title\":\"话语\",\"summarize\":\"you can you up,no can no bb\",\"thumb_url\":\"http://okzwnw2rn.bkt.clouddn.com/image/bannerren_bg_walk.jpg\",\"url\":\"https://www.baidu.com\",\"publish_time\":\"2017-02-07\",\"author\":\"Randy Zhang\",\"like_num\":\"0\",\"view_num\":\"0\",\"collect_num\":\"0\"},{\"id\":\"10034\",\"type\":\"3\",\"type_name\":\"阅读的力量\",\"title\":\"话语\",\"summarize\":\"you can you up,no can no bb\",\"thumb_url\":\"http://okzwnw2rn.bkt.clouddn.com/image/bannerren_bg_walk.jpg\",\"url\":\"https://www.baidu.com\",\"publish_time\":\"2017-02-07\",\"author\":\"Randy Zhang\",\"like_num\":\"0\",\"view_num\":\"0\",\"collect_num\":\"0\"}]},{\"type\":\"4\",\"type_name\":\"音乐的力量\",\"list\":[{\"id\":\"10041\",\"type\":\"4\",\"type_name\":\"音乐的力量\",\"title\":\"话语\",\"summarize\":\"you can you up,no can no bb\",\"thumb_url\":\"http://okzwnw2rn.bkt.clouddn.com/image/bannerren_bg_walk.jpg\",\"url\":\"https://www.baidu.com\",\"publish_time\":\"2017-02-07\",\"author\":\"Randy Zhang\",\"like_num\":\"0\",\"view_num\":\"0\",\"collect_num\":\"0\"},{\"id\":\"10042\",\"type\":\"4\",\"type_name\":\"音乐的力量\",\"title\":\"话语\",\"summarize\":\"you can you up,no can no bb\",\"thumb_url\":\"http://okzwnw2rn.bkt.clouddn.com/image/bannerren_bg_walk.jpg\",\"url\":\"https://www.baidu.com\",\"publish_time\":\"2017-02-07\",\"author\":\"Randy Zhang\",\"like_num\":\"0\",\"view_num\":\"0\",\"collect_num\":\"0\"},{\"id\":\"10043\",\"type\":\"4\",\"type_name\":\"音乐的力量\",\"title\":\"话语\",\"summarize\":\"you can you up,no can no bb\",\"thumb_url\":\"http://okzwnw2rn.bkt.clouddn.com/image/bannerren_bg_walk.jpg\",\"url\":\"https://www.baidu.com\",\"publish_time\":\"2017-02-07\",\"author\":\"Randy Zhang\",\"like_num\":\"0\",\"view_num\":\"0\",\"collect_num\":\"0\"},{\"id\":\"10044\",\"type\":\"4\",\"type_name\":\"音乐的力量\",\"title\":\"话语\",\"summarize\":\"you can you up,no can no bb\",\"thumb_url\":\"http://okzwnw2rn.bkt.clouddn.com/image/bannerren_bg_walk.jpg\",\"url\":\"https://www.baidu.com\",\"publish_time\":\"2017-02-07\",\"author\":\"Randy Zhang\",\"like_num\":\"0\",\"view_num\":\"0\",\"collect_num\":\"0\"}]}]";
        articleGroupBeanList = gson.fromJson(articleListStr, new TypeToken<List<ArticleGroupBean>>() {
        }.getType());

        return articleGroupBeanList;
    }
}
