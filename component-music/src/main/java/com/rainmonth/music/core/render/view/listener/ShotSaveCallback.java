package com.rainmonth.music.core.render.view.listener;

import java.io.File;

/**
 * @author 张豪成
 * @date 2019-12-19 19:50
 */
public interface ShotSaveCallback {
    void onShotSaveResult(boolean isSuccess, File saveFile);
}
