package com.rainmonth.mvp.ui.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.rainmonth.R;
import com.rainmonth.common.adapter.ListViewDataAdapter;
import com.rainmonth.common.adapter.ViewHolderBase;
import com.rainmonth.common.adapter.ViewHolderCreator;
import com.rainmonth.common.base.BaseLazyFragment;
import com.rainmonth.common.di.component.AppComponent;
import com.rainmonth.common.eventbus.EventCenter;
import com.rainmonth.di.component.DaggerRanComponent;
import com.rainmonth.di.module.RanModule;
import com.rainmonth.mvp.contract.RanContract;
import com.rainmonth.mvp.model.bean.RanContentBean;
import com.rainmonth.mvp.presenter.RanPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by RandyZhang on 16/6/30.
 */
public class RanFragment extends BaseLazyFragment<RanPresenter> implements RanContract.View {

    @BindView(R.id.lv_content)
    ListView lvContent;
    private ListViewDataAdapter<RanContentBean> mRanContentListAdapter = null;

    @Override
    public void onFirstUserVisible() {

    }

    @Override
    public int getContentViewLayoutID() {
        return R.layout.fragment_ran;
    }

    @Override
    protected void onEventComing(EventCenter eventCenter) {

    }

    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }

    @Override
    public void onUserVisible() {

    }

    @Override
    public void onUserInvisible() {

    }

    @Override
    protected void setupFragmentComponent(AppComponent appComponent) {
        DaggerRanComponent.builder()
                .appComponent(appComponent)
                .ranModule(new RanModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    protected void initViewsAndEvents(View view) {
        mPresenter.initialize();
    }

    @Override
    public void initViews(List<RanContentBean> ranContentBeanList) {
        mRanContentListAdapter = new ListViewDataAdapter<>(new ViewHolderCreator<RanContentBean>() {
            @Override
            public ViewHolderBase<RanContentBean> createViewHolder(int position) {
                return new ViewHolderBase<RanContentBean>() {
                    ImageView ivAlbumFirstImage;
                    TextView tvAlbumDescription;
                    TextView tvAlbumAuthor;
                    TextView tvAlbumPublishTime;
                    TextView tvAlbumLikeNum;
                    TextView tvAlbumTotalNum;
                    TextView tvAlbumType;

                    @Override
                    public View createView(LayoutInflater layoutInflater) {
                        View convertView = layoutInflater.inflate(R.layout.adapter_ran_lv_content_item, null);
                        ivAlbumFirstImage = ButterKnife.findById(convertView, R.id.iv_album_first_image);
                        tvAlbumDescription = ButterKnife.findById(convertView, R.id.tv_album_des);
                        tvAlbumAuthor = ButterKnife.findById(convertView, R.id.tv_album_author);
                        tvAlbumPublishTime = ButterKnife.findById(convertView, R.id.tv_album_publish_time);
                        tvAlbumLikeNum = ButterKnife.findById(convertView, R.id.tv_album_like_num);
                        tvAlbumTotalNum = ButterKnife.findById(convertView, R.id.tv_album_total_num);
                        tvAlbumType = ButterKnife.findById(convertView, R.id.tv_album_type);
                        return convertView;
                    }

                    @Override
                    public void showData(int position, RanContentBean itemData) {
                        if (null != itemData) {
                            ivAlbumFirstImage.setImageResource(itemData.getAlbumFirstImageResId());
                            tvAlbumDescription.setText(itemData.getAlbumDescription());
                            tvAlbumAuthor.setText(itemData.getAlbumAuthor());
                            tvAlbumPublishTime.setText(itemData.getAlbumPublishTime());
                            tvAlbumLikeNum.setText(itemData.getAlbumLikeNum() + "人喜欢");
                            tvAlbumTotalNum.setText("共" + itemData.getAlbumTotalNum() + "张图片");
                            tvAlbumType.setText(itemData.getAlbumType());
                        }
                    }
                };
            }
        });

        mRanContentListAdapter.getDataList().addAll(ranContentBeanList);
        lvContent.setAdapter(mRanContentListAdapter);
        lvContent.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                RanContentBean ranContentBean = mRanContentListAdapter.getDataList().get(position);
                if (null != ranContentBean) {
                    mPresenter.navToDetail(ranContentBean);
                }
            }
        });
    }

    @Override
    public void navToDetail(RanContentBean ranContentBean) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
