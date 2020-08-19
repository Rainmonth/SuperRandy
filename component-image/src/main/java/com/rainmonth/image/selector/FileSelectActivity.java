package com.rainmonth.image.selector;

import android.view.View;
import android.widget.TextView;

import com.rainmonth.common.base.BaseActivity;
import com.rainmonth.common.di.component.AppComponent;
import com.rainmonth.common.utils.PermissionUtils;
import com.rainmonth.image.R;
import com.rainmonth.image.selector.task.impl.MediaLoader;

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
        TextView tvLoadMedia = findViewById(R.id.tv_load_media);
        tvLoadMedia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaLoader mediaLoader = new MediaLoader();
//                mediaLoader.load(mContext, null);
//                mediaLoader.loadVideos(mContext.getContentResolver(), null);
                mediaLoader.loadAudios(mContext.getContentResolver(), null);

                mediaLoader.printTableInfo(mContext, MediaLoader.TABLE_VIDEO);
            }
        });
    }

}
