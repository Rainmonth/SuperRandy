package com.rainmonth.player.base.player;

import com.rainmonth.player.base.player.exo.Exo2PlayerManager;
import com.rainmonth.player.base.player.ijk.IjkPlayerManager;

/**
 * 播放内核工厂
 */
public class PlayerFactory {

    private static Class<? extends IPlayerManager> sPlayerManager;

    public static void setPlayManager(Class<? extends IPlayerManager> playManager) {
        sPlayerManager = playManager;
    }

    public static IPlayerManager getPlayManager() {
        if (sPlayerManager == null) {
            sPlayerManager = Exo2PlayerManager.class;
        }
        try {
            return sPlayerManager.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

}
