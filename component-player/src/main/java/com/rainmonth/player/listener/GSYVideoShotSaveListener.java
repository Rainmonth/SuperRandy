package com.rainmonth.player.listener;


import java.io.File;

/**
 * 截屏保存结果
 */
public interface GSYVideoShotSaveListener {
    void result(boolean success, File file);
}
