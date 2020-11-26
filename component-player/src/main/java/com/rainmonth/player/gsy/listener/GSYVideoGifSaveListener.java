package com.rainmonth.player.gsy.listener;

import java.io.File;

/**
 * Gif图创建的监听
 */
public interface GSYVideoGifSaveListener {

    void process(int curPosition, int total);

    void result(boolean success, File file);
}
