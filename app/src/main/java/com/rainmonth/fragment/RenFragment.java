package com.rainmonth.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.rainmonth.R;
import com.rainmonth.base.ui.adapter.ListViewDataAdapter;
import com.rainmonth.base.ui.adapter.ViewHolderBase;
import com.rainmonth.base.ui.adapter.ViewHolderCreator;
import com.rainmonth.base.ui.fragment.BaseLazyFragment;
import com.rainmonth.bean.RenContentInfo;
import com.rainmonth.presenter.impl.RenFragmentPresenter;
import com.rainmonth.view.RenFragmentView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by RandyZhang on 16/6/30.
 */
public class RenFragment extends BaseLazyFragment implements RenFragmentView {

    @Bind(R.id.lv_content)
    ListView lvContent;

    private RenFragmentPresenter renFragmentPresenter = null;
    private ListViewDataAdapter<RenContentInfo> mRenContentListAdapter = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);

        renFragmentPresenter = new RenFragmentPresenter(mContext, this);
        renFragmentPresenter.initialize();
        return rootView;
    }

    @Override
    public void onFirstUserVisible() {

    }

    @Override
    public int getContentViewLayoutID() {
        return R.layout.fragment_ren;
    }

    @Override
    public void onUserVisible() {

    }

    @Override
    public void onUserInvisible() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void initViews(List<RenContentInfo> renContentInfoList) {
        mRenContentListAdapter = new ListViewDataAdapter<RenContentInfo>(new ViewHolderCreator<RenContentInfo>() {
            @Override
            public ViewHolderBase<RenContentInfo> createViewHolder(int position) {
                return new ViewHolderBase<RenContentInfo>() {
                    ImageView ivBg;
                    //                    TextView tvTagType;
                    TextView tvTagName;
                    TextView tvTitle;

                    @Override
                    public View createView(LayoutInflater layoutInflater) {
                        View convertView = layoutInflater.inflate(R.layout.adapter_ren_lv_content_item, null);
                        ivBg = ButterKnife.findById(convertView, R.id.iv_bg);
                        tvTagName = ButterKnife.findById(convertView, R.id.tv_tag_name);
                        tvTitle = ButterKnife.findById(convertView, R.id.tv_title);
                        return convertView;
                    }

                    @Override
                    public void showData(int position, RenContentInfo itemData) {
                        ivBg.setImageResource(itemData.getImageResId());
                        tvTagName.setText(itemData.getTagName());
                        tvTitle.setText(itemData.getTitle());

                    }
                };
            }
        });
        mRenContentListAdapter.getDataList().addAll(renContentInfoList);
        lvContent.setAdapter(mRenContentListAdapter);
        lvContent.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // todo nav to detail activity
                RenContentInfo renContentInfo = mRenContentListAdapter.getDataList().get(position);
                if (null != renContentInfo) {
                    renFragmentPresenter.navToDetail(renContentInfo);
                }
            }
        });
    }

    @Override
    public void navToDetail(RenContentInfo xunNavigationInfo) {
        // todo 进入二级界面
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
