package com.rainmonth.player.activity.other.video;

import android.os.Build;

import com.rainmonth.common.base.BaseTabViewPagerActivity;
import com.rainmonth.player.activity.other.video.fragment.Camera2Fragment;
import com.rainmonth.player.activity.other.video.fragment.CameraFragment;
import com.rainmonth.player.activity.other.video.fragment.CameraXFragment;

/**
 * 比较 Camera 和  Camera2
 * <p>
 * 获取视频数据
 *
 * @author RandyZhang
 * @date 2020/12/21 4:38 PM
 */
public class VideoGetDataExampleActivity extends BaseTabViewPagerActivity {

    @Override
    public void bindFragmentsAndTitles() {
        if (isSupportCamera2()) {
            fragments.add(new Camera2Fragment());
        } else {
            fragments.add(new CameraFragment());
        }
        fragments.add(new CameraXFragment());
        if (isSupportCamera2()) {
            titles.add("Camera2");
        } else {
            titles.add("Camera");
        }
        titles.add("CameraX");
    }

    private boolean isSupportCamera2() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }
}
