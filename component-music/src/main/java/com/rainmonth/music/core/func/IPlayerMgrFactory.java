package com.rainmonth.music.core.func;

/**
 * @author 张豪成
 * @date 2019-12-05 20:35
 */
public interface IPlayerMgrFactory {
    /**
     * 创建IPlayerManager实例
     *
     * @return 播放器实例
     */
    IPlayerManager createPlayerMgr();
}
