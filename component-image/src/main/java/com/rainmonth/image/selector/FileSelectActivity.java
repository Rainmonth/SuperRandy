package com.rainmonth.image.selector;

import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.rainmonth.common.base.BaseActivity;
import com.rainmonth.common.di.component.AppComponent;
import com.rainmonth.common.http.imageloader.glide.GlideImageConfig;
import com.rainmonth.common.thread.SrThreadService;
import com.rainmonth.common.utils.ComponentUtils;
import com.rainmonth.image.R;
import com.rainmonth.image.selector.bean.MediaFileBean;
import com.rainmonth.image.selector.task.callback.IMediaTaskCallback;
import com.rainmonth.image.selector.task.impl.MediaLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * 文件选择页面
 * - 支持图片选择
 * - 支持视频选择
 * - 支持apk文件选择
 *
 * @author 张豪成
 * @date 2019-05-24 16:36
 */
public class FileSelectActivity extends BaseActivity {

    TextView tvLoadMedia;
    RecyclerView rvMediaFiles;

    private BaseQuickAdapter<MediaFileBean, BaseViewHolder> mAdapter;
    private GridLayoutManager mGridLayoutManager;

    private List<MediaFileBean> mMediaFileList = new ArrayList<>();

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    public void initToolbar(int colorResId) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.image_activity_file_select;
    }

    @Override
    protected void initViewsAndEvents() {
        tvLoadMedia = findViewById(R.id.tv_load_media);
        rvMediaFiles = findViewById(R.id.rv_media_files);

        MediaLoader mediaLoader = new MediaLoader();
        IMediaTaskCallback callback = mediaFileBeans -> {
            // 处理加载的媒体文件列表
            getSafeHandler().post(() -> {
                onMediaListLoaded(mediaFileBeans);
            });
        };
        tvLoadMedia.setOnClickListener(v -> {
            try {

                SrThreadService.get().executeDaemonTask(() -> {
                    mediaLoader.loadAudios(mContext.getContentResolver(), callback);
                }, "loadMedia");


                mediaLoader.printTableInfo(mContext, MediaLoader.TABLE_VIDEO);
            } catch (Exception e) {
                Log.e("Randy", e.getMessage());
            }
        });
        mGridLayoutManager = new GridLayoutManager(mContext, 4);
        mAdapter = new BaseQuickAdapter<MediaFileBean, BaseViewHolder>(R.layout.image_rv_item_media_files, mMediaFileList) {
            @Override
            protected void convert(@NonNull BaseViewHolder baseViewHolder, MediaFileBean mediaFileBean) {
                ImageView ivFile = baseViewHolder.getView(R.id.iv_image);

                if (mediaFileBean != null) {
                    ComponentUtils.getAppComponent().imageLoader().loadImage(mContext,
                            GlideImageConfig.builder()
                                    .url(mediaFileBean.path)
                                    .imageView(ivFile).build());
                }
            }
        };
        rvMediaFiles.setLayoutManager(mGridLayoutManager);
        rvMediaFiles.setAdapter(mAdapter);
    }

    private void onMediaListLoaded(List<MediaFileBean> mediaFileBeans) {
        mMediaFileList.clear();
        mMediaFileList.addAll(mediaFileBeans);
        mAdapter.notifyDataSetChanged();
    }

}
