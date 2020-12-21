package com.rainmonth.player.base.player;

import com.rainmonth.player.base.player.exo.Exo2PlayerManager;
import com.rainmonth.player.base.player.ijk.IjkPlayerManager;
import com.rainmonth.player.base.player.sys.SystemPlayerManager;
import com.rainmonth.player.utils.Debugger;

import java.security.InvalidKeyException;

/**
 * 播放内核工厂
 */
public class PlayerFactory {
    private static final String TAG = PlayerFactory.class.getSimpleName();

    private static final int CORE_TYPE_SYS = 0;     // 系统内核
    private static final int CORE_TYPE_EXO = 1;     // EXO内核
    private static final int CORE_TYPE_IJK = 2;     // IJK内核

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
            Debugger.printStackTrace(TAG, e);
        } catch (IllegalAccessException e) {
            Debugger.printStackTrace(TAG, e);
        }
        return null;
    }

    public static IPlayerManager getPlayerManager(int coreType) {
        if (coreType == CORE_TYPE_SYS) {
            sPlayerManager = SystemPlayerManager.class;
        } else if (coreType == CORE_TYPE_IJK) {
            sPlayerManager = IjkPlayerManager.class;
        } else if (coreType == CORE_TYPE_EXO) {
            sPlayerManager = Exo2PlayerManager.class;
        } else {
            throw new IllegalArgumentException("Core type with: " + coreType + " is not supported now!");
        }
        try {
            return sPlayerManager.newInstance();
        } catch (InstantiationException e) {
            Debugger.printStackTrace(TAG, e);
        } catch (IllegalAccessException e) {
            Debugger.printStackTrace(TAG, e);
        }
        return null;
    }

}
