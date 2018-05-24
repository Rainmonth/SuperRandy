package com.rainmonth.mvp.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.rainmonth.R;
import com.rainmonth.mvp.contract.RanContract;
import com.rainmonth.mvp.model.bean.RanContentBean;
import com.rainmonth.common.adapter.ListViewDataAdapter;
import com.rainmonth.common.adapter.ViewHolderBase;
import com.rainmonth.common.adapter.ViewHolderCreator;
import com.rainmonth.common.base.BaseLazyFragment;
import com.rainmonth.common.eventbus.EventCenter;
import com.rainmonth.mvp.presenter.RanPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by RandyZhang on 16/6/30.
 */
public class RanFragment extends BaseLazyFragment implements RanContract.View {

    @BindView(R.id.lv_content)
    ListView lvContent;
    private RanPresenter mRanPresenter = null;
    private ListViewDataAdapter<RanContentBean> mRanContentListAdapter = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);

//        mRanPresenter = new RanPresenter(this);
        mRanPresenter.initialize();
        return rootView;
    }

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
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    protected void initViewsAndEvents(View view) {

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
                // todo nav to detail activity
                RanContentBean ranContentBean = mRanContentListAdapter.getDataList().get(position);
                if (null != ranContentBean) {
                    mRanPresenter.navToDetail(ranContentBean);
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

    @Override
    public void toast(String msg) {

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }
}
